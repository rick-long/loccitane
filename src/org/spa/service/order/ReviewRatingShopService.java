package org.spa.service.order;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.ReviewRatingShop;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingShopVO;

import java.util.List;
import java.util.Map;

/**
 * Created by jason 2018-1-21
 */
public interface ReviewRatingShopService extends BaseDao<ReviewRatingShop>{
    public  Double getAllShopCustomerServicesAvg(SalesSearchVO salesSearchVO);
    public  Double getAllShopCleanlinessAvg(SalesSearchVO salesSearchVO);


    public List<Map<String,Object>>getTop10Avg(SalesSearchVO salesSearchVO);
    public List<Map<String,Object>>getLow10Avg(SalesSearchVO salesSearchVO);
    public Page<Map<String,Object>> getAllReviewRatingShopAvg(ReviewRatingShopVO reviewRatingShopVO);





}
