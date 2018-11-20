package org.spa.serviceImpl.order;

import com.spa.constant.CommonConstant;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.spa.dao.order.PurchaseItemDao;
import org.spa.dao.order.ReviewRatingTreatmentDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.order.ReviewRatingTreatment;
import org.spa.service.order.*;
import org.spa.utils.DateUtil;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentTherapistVO;
import org.spa.vo.sales.ReviewRatingTreatmentVO;
import org.spa.vo.sales.ReviewStatisticalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * @author jason 2018-1-21
 */
@Service
public class ReviewRatingTreatmentServiceImpl extends BaseDaoHibernate<ReviewRatingTreatment> implements ReviewRatingTreatmentService {
  @Autowired
    ReviewRatingTreatmentDao reviewRatingTreatmentDao;
  @Autowired
  ReviewRatingTreatmentTherapistService reviewRatingTreatmentTherapistService;
  @Autowired
  PurchaseOrderService purchaseOrderService;
  @Autowired
  PurchaseItemDao purchaseItemDao;
  @Autowired
  ReviewRatingShopService reviewRatingShopService;
  @Autowired
  ReviewService reviewService;
    @Override
    public  Double getAllSatisfactionLevelStarAvg(SalesSearchVO salesSearchVO){
        return reviewRatingTreatmentDao.getAllSatisfactionLevelStarAvg(salesSearchVO);
    }
    @Override
    public  Double getValueForMoneyStarAvg(SalesSearchVO salesSearchVO){
        return reviewRatingTreatmentDao.getAllValueForMoneyStarAvg(salesSearchVO);
    }
    @Override
    public List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO){

        return reviewRatingTreatmentDao.getTop10Avg(salesSearchVO);
    }

    @Override
    public List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO){

        return reviewRatingTreatmentDao.getLow10Avg(salesSearchVO);
    }
    @Override
    public Page<Map<String,Object>> getAllReviewRatingTreatmentAvg(ReviewRatingTreatmentVO reviewRatingTreatmentVO){

        return reviewRatingTreatmentDao.getAllReviewRatingTreatmentAvg(reviewRatingTreatmentVO);
    }

    @Override
    public  ReviewStatisticalVO getStatistical(SalesSearchVO salesSearchVO){
        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        salesSearchVO.setProdType(CommonConstant.CATEGORY_REF_TREATMENT);
        Long reviewCount=reviewService.getCount(salesSearchVO);//review数
        Long npsSum=reviewService.getNpsSum(salesSearchVO);//npsSum
        double nps=0l;
        Long npsTotal=reviewCount*10;
        if(npsTotal!=0){
            double npsF=(double)npsSum*100/npsTotal;//nps
            BigDecimal b1 = new BigDecimal(npsF);
            nps = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Long reviewRatingTreatmentCount=reviewRatingTreatmentDao.getCount(salesSearchVO,null,null);//评论数
        Long zcp=reviewRatingTreatmentDao.getCount(salesSearchVO,null,3);//中 差评数
        Long badReviewCount=reviewRatingTreatmentDao.getCount(salesSearchVO,null,2);// 差评数
        Long highPraiseSum= reviewRatingTreatmentCount-zcp;//好评

        long  orderCount= purchaseItemDao.getPurchaseItemIdOfSalesDetailsCount(salesSearchVO,true,false);//总订单数
        long notReview=0;
        double highPraisePercentage=0.0;
        long badReviewPercentage=0;
        if (orderCount!=0){
            double f=(double)highPraiseSum*100/orderCount;
            BigDecimal b = new BigDecimal(f);
            highPraisePercentage = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if (orderCount!=0){
            badReviewPercentage=badReviewCount*100/orderCount;
        }
        if (orderCount!=0 && orderCount-reviewRatingTreatmentCount>0){
            Long a=orderCount-reviewRatingTreatmentCount;
            notReview=a*100/orderCount;//未提交百分比
        }
        Double customerServicesAvg=reviewRatingShopService.getAllShopCustomerServicesAvg(salesSearchVO);//shopcustomerService平均分
        Double shopCleanlinessAvg=reviewRatingShopService.getAllShopCleanlinessAvg(salesSearchVO);//shopCleanliness平均分
        Double satisfactionLevelStarAvg =reviewRatingTreatmentDao.getAllSatisfactionLevelStarAvg(salesSearchVO);
        Double valueForMoneyStarAvg = reviewRatingTreatmentDao.getAllValueForMoneyStarAvg(salesSearchVO);
        Double staffStarAvg =reviewRatingTreatmentTherapistService.getAllStaffStarAvg(salesSearchVO);
        long reviewStar5=0;//5星百分比评论数
        long reviewStar4=0;//4星百分比评论数
        long reviewStar3=0;//3星百分比评论数
        long reviewStar2=0;//2星百分比评论数
        long reviewStar1=0;//1星百分比评论数
        if(reviewRatingTreatmentCount!=0){

            reviewStar5=reviewRatingTreatmentDao.getCount(salesSearchVO,5,null)*100/reviewRatingTreatmentCount;//5星百分比评论数
             reviewStar4=reviewRatingTreatmentDao.getCount(salesSearchVO,4,null)*100/reviewRatingTreatmentCount;//4星百分比评论数
             reviewStar3=reviewRatingTreatmentDao.getCount(salesSearchVO,3,null)*100/reviewRatingTreatmentCount;//3星百分比评论数
             reviewStar2=reviewRatingTreatmentDao.getCount(salesSearchVO,2,null)*100/reviewRatingTreatmentCount;//2星百分比评论数
             reviewStar1=reviewRatingTreatmentDao.getCount(salesSearchVO,1,null)*100/reviewRatingTreatmentCount;//1星百分比评论数
        }

        ReviewStatisticalVO reviewStatisticalVO=new ReviewStatisticalVO();
        reviewStatisticalVO.setReviewRatingTreatmentCount(reviewRatingTreatmentCount);
        reviewStatisticalVO.setBadReviewCount(badReviewCount);
        reviewStatisticalVO.setOrderCount(orderCount);
        reviewStatisticalVO.setNotReview(notReview);
        reviewStatisticalVO.setCustomerServicesAvg(customerServicesAvg);
        reviewStatisticalVO.setShopCleanlinessAvg(shopCleanlinessAvg);
        reviewStatisticalVO.setReviewStar1(reviewStar1);
        reviewStatisticalVO.setReviewStar2(reviewStar2);
        reviewStatisticalVO.setReviewStar3(reviewStar3);
        reviewStatisticalVO.setReviewStar4(reviewStar4);
        reviewStatisticalVO.setReviewStar5(reviewStar5);
        reviewStatisticalVO.setHighPraiseSum(highPraiseSum);
        reviewStatisticalVO.setHighPraisePercentage(highPraisePercentage);
        reviewStatisticalVO.setBadReviewPercentage(badReviewPercentage);
        reviewStatisticalVO.setSatisfactionLevelStarAvg(satisfactionLevelStarAvg);
        reviewStatisticalVO.setValueForMoneyStarAvg(valueForMoneyStarAvg);
        reviewStatisticalVO.setStaffStarAvg(staffStarAvg);
        reviewStatisticalVO.setNps(nps);
        return reviewStatisticalVO;

    }
    @Override
    public  Double getSatisfactionLevelStarAvgByTreatmentId(Long treatmentId){
        return reviewRatingTreatmentDao.getSatisfactionLevelStarAvgByTreatmentId(treatmentId);
    }
}
