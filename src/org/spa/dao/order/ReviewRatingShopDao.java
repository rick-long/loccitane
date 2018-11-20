package org.spa.dao.order;

import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingShopVO;

import java.util.List;
import java.util.Map;

public interface ReviewRatingShopDao {

     Double getAllShopCustomerServicesAvg(SalesSearchVO salesSearchVO);

     Double getAllShopCleanlinessAvg(SalesSearchVO salesSearchVO);

     Page<Map<String,Object>> getAllReviewRatingShopAvg(ReviewRatingShopVO reviewRatingShopVO);

     List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO);

     List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO);

}
