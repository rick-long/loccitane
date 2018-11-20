package org.spa.dao.order;

import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentTherapistVO;
import org.spa.vo.sales.ReviewRatingTreatmentVO;

import java.util.List;
import java.util.Map;

public interface ReviewRatingTreatmentTherapistDao {
    Double getAllStaffStarAvg(SalesSearchVO salesSearchVO);
    List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO);
    List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO);
    Page<Map<String,Object>> getAllReviewRatingTreatmentTherapistAvg(ReviewRatingTreatmentTherapistVO reviewRatingTreatmentTherapistVO);

    Double getStaffStarAvgById(Long staffId);

}
