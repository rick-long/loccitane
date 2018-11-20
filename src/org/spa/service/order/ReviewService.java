package org.spa.service.order;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.Review;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewVo;

import java.util.List;
import java.util.Map;

/**
 * Created by jason 2018-1-21
 */
public interface ReviewService extends BaseDao<Review>{

   void saveReview(ReviewVo reviewVo);

    Review getReviewByOrderId(Long orderId);

    Long getCount(SalesSearchVO salesSearchVO);
    Long getNpsSum(SalesSearchVO salesSearchVO);

    List<Map<String,Object>> getListing(SalesSearchVO salesSearchVO);

	
}
