package org.spa.serviceImpl.order;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.order.ReviewDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.*;
import org.spa.service.order.*;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingShopVO;
import org.spa.vo.sales.ReviewRatingTreatmentTherapistVO;
import org.spa.vo.sales.ReviewRatingTreatmentVO;
import org.spa.vo.sales.ReviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jason 2018-1-21
 */
@Service
public class ReviewServiceImpl extends BaseDaoHibernate<Review> implements ReviewService {

    @Autowired
    ReviewRatingTreatmentService reviewRatingTreatmentService;
    @Autowired
    ReviewDao reviewDao;
    @Autowired
    ReviewRatingTreatmentTherapistService reviewRatingTreatmentTherapistService;

    @Autowired
    ReviewRatingShopService reviewRatingShopService;

    @Autowired
    PurchaseOrderService purchaseOrderService;
    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductOptionService productOptionService;
    @Autowired
    UserService userService;

    @Autowired
    private OrderSurveyService orderSurveyService;

    @Override
   public Long getNpsSum(SalesSearchVO salesSearchVO){

        return reviewDao.getNpsSum(salesSearchVO);
    }

    @Override
    public Long getCount(SalesSearchVO salesSearchVO){
        return reviewDao.getCount(salesSearchVO);
    }
    @Override
    public List<Map<String,Object>> getListing(SalesSearchVO salesSearchVO){
        return reviewDao.getListing(salesSearchVO);
    }
    
    @Override
    public void  saveReview(ReviewVo reviewVo){
        PurchaseOrder purchaseOrder=purchaseOrderService.get(reviewVo.getOrderId());
        Date now= new Date();
        Review review =new Review();
        review.setPurchaseOrder(purchaseOrder);
        review.setUser(purchaseOrder.getUser());
        review.setReviewText(reviewVo.getReviewText());
        review.setCreated(now);
        review.setCreatedBy(purchaseOrder.getUser().getFullName());
        review.setLastUpdated(now);
        review.setLastUpdatedBy(purchaseOrder.getUser().getFullName());
        review.setIsActive(true);
        review.setNps(reviewVo.getNps());
      
        for (ReviewRatingShopVO reviewRatingShopVO:reviewVo.getReviewRatingShops()){
            if(reviewRatingShopVO.getShopId()!=null){
                ReviewRatingShop reviewRatingShop=new ReviewRatingShop();
                reviewRatingShop.setShop(shopService.get(reviewRatingShopVO.getShopId()));
                reviewRatingShop.setCleanlinessStar(reviewRatingShopVO.getCleanlinessStar());
                reviewRatingShop.setCustomerServiceStar(reviewRatingShopVO.getCustomerServiceStar());
                reviewRatingShop.setCreated(now);
                reviewRatingShop.setCreatedBy(purchaseOrder.getUser().getFullName());
                reviewRatingShop.setLastUpdated(now);
                reviewRatingShop.setLastUpdatedBy(purchaseOrder.getUser().getFullName());
                reviewRatingShop.setIsActive(true);
                reviewRatingShop.setReview(review);
                review.getReviewRatingShops().add(reviewRatingShop);
            }

        }
        for(ReviewRatingTreatmentVO reviewRatingTreatmentVO:reviewVo.getReviewRatingTreatments()){
            Date date= new Date();
            ReviewRatingTreatment reviewRatingTreatment=new ReviewRatingTreatment();
            reviewRatingTreatment.setProduct(productService.get(reviewRatingTreatmentVO.getProductId()));
            reviewRatingTreatment.setProductOption(productOptionService.get(reviewRatingTreatmentVO.getProductOptionId()));
            reviewRatingTreatment.setValueForMoneyStar(reviewRatingTreatmentVO.getValueForMoneyStar());
            reviewRatingTreatment.setSatisfactionLevelStar(reviewRatingTreatmentVO.getSatisfactionLevelStar());
            reviewRatingTreatment.setReview(review);
            reviewRatingTreatment.setCreated(date);
            reviewRatingTreatment.setCreatedBy(purchaseOrder.getUser().getFullName());
            reviewRatingTreatment.setIsActive(true);
            reviewRatingTreatment.setLastUpdated(date);
            reviewRatingTreatment.setLastUpdatedBy(purchaseOrder.getUser().getFullName());
            review.getReviewRatingTreatments().add(reviewRatingTreatment);

            for(ReviewRatingTreatmentTherapistVO reviewRatingTreatmentTherapistVO :reviewRatingTreatmentVO.getReviewRatingTreatmentTherapists() ){

                if(reviewRatingTreatmentTherapistVO!=null && reviewRatingTreatmentTherapistVO.getStaffId()!=null){
                    ReviewRatingTreatmentTherapist reviewRatingTreatmentTherapist =new ReviewRatingTreatmentTherapist();
                    reviewRatingTreatmentTherapist.setReviewRatingTreatment(reviewRatingTreatment);
                    reviewRatingTreatmentTherapist.setStaff(userService.get(reviewRatingTreatmentTherapistVO.getStaffId()));
                    reviewRatingTreatmentTherapist.setStaffStar(reviewRatingTreatmentTherapistVO.getStaffStar());
                    reviewRatingTreatmentTherapist.setCreated(date);
                    reviewRatingTreatmentTherapist.setCreatedBy(purchaseOrder.getUser().getFullName());
                    reviewRatingTreatmentTherapist.setLastUpdated(date);
                    reviewRatingTreatmentTherapist.setLastUpdatedBy(purchaseOrder.getUser().getFullName());
                    reviewRatingTreatmentTherapist.setIsActive(true);
                    reviewRatingTreatment.getReviewRatingTreatmentTherapists().add(reviewRatingTreatmentTherapist);
                }

            }
        }
        save(review);
        // change order survey's status to compelted
        OrderSurvey  orderSurvey =orderSurveyService.getOrderSurveyByOrderId(review.getPurchaseOrder().getId());
        orderSurvey.setStatus("COMPLETED");
        orderSurvey.setLastUpdated(now);
        orderSurvey.setLastUpdatedBy(purchaseOrder.getUser().getUsername());
        orderSurveyService.saveOrUpdate(orderSurvey);
    }

    @Override
    public Review getReviewByOrderId(Long orderId){
        if(orderId==null){
            return null;
        }
        DetachedCriteria dc = DetachedCriteria.forClass(Review.class);
            dc.add(Restrictions.eq("purchaseOrder.id", orderId));
        return get(dc);
    }
}
