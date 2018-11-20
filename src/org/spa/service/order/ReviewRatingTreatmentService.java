package org.spa.service.order;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.ReviewRatingTreatment;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentVO;
import org.spa.vo.sales.ReviewStatisticalVO;

import java.util.List;
import java.util.Map;

/**
 * Created by jason 2018-1-21
 */
public interface ReviewRatingTreatmentService extends BaseDao<ReviewRatingTreatment>{
    public  Double getAllSatisfactionLevelStarAvg(SalesSearchVO salesSearchVO);
    public  Double getValueForMoneyStarAvg(SalesSearchVO salesSearchVO);

    public List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO);
    public List<Map<String,Object>>getLow10Avg(SalesSearchVO salesSearchVO);
    public Page<Map<String,Object>> getAllReviewRatingTreatmentAvg(ReviewRatingTreatmentVO reviewRatingTreatmentVO);
    public ReviewStatisticalVO getStatistical(SalesSearchVO salesSearchVO);

	
    public  Double getSatisfactionLevelStarAvgByTreatmentId(Long treatmentId);
}
