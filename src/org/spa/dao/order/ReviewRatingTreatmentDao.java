package org.spa.dao.order;

import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentVO;

import java.util.List;
import java.util.Map;

public interface ReviewRatingTreatmentDao {
    Double getAllSatisfactionLevelStarAvg(SalesSearchVO salesSearchVO);
    Double getAllValueForMoneyStarAvg(SalesSearchVO salesSearchVO);
    List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO);
    List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO);
    Page<Map<String,Object>> getAllReviewRatingTreatmentAvg( ReviewRatingTreatmentVO reviewRatingTreatmentVO);
    Long getCount(SalesSearchVO salesSearchVO,Integer star,Integer starLe);
    Double getSatisfactionLevelStarAvgByTreatmentId(Long treatmentId);
}
