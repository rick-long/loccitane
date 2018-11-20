package org.spa.serviceImpl.order;

import org.spa.dao.order.ReviewRatingShopDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.ReviewRatingShop;
import org.spa.service.order.ReviewRatingShopService;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author jason 2018-1-21
 */
@Service
public class ReviewRatingShopServiceImpl extends BaseDaoHibernate<ReviewRatingShop> implements ReviewRatingShopService{
	@Autowired
    ReviewRatingShopDao reviewRatingShopDao;
    @Override
    public  Double getAllShopCustomerServicesAvg(SalesSearchVO salesSearchVO){
        return reviewRatingShopDao.getAllShopCustomerServicesAvg(salesSearchVO);
    }
    @Override
    public  Double getAllShopCleanlinessAvg(SalesSearchVO salesSearchVO){
        return reviewRatingShopDao.getAllShopCleanlinessAvg(salesSearchVO);
    }
    @Override
    public List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO){

        return reviewRatingShopDao.getTop10Avg(salesSearchVO);
    }

    @Override
    public List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO){

        return reviewRatingShopDao.getLow10Avg( salesSearchVO);
    }
    @Override
    public Page<Map<String,Object>> getAllReviewRatingShopAvg(ReviewRatingShopVO reviewRatingShopVO){

       return reviewRatingShopDao.getAllReviewRatingShopAvg(reviewRatingShopVO);
    }




}
