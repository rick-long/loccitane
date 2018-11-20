package org.spa.service.order;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.ReviewRatingTreatmentTherapist;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentTherapistVO;

import java.util.List;
import java.util.Map;

/**
 * Created by jason 2018-1-21
 */
public interface ReviewRatingTreatmentTherapistService extends BaseDao<ReviewRatingTreatmentTherapist>{
    public  Double getAllStaffStarAvg(SalesSearchVO salesSearchVO);
    public List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO);
    public List<Map<String,Object>>getLow10Avg(SalesSearchVO salesSearchVO);
    public Page<Map<String,Object>> getAllReviewRatingTreatmentTherapistAvg(ReviewRatingTreatmentTherapistVO reviewRatingTreatmentVO);

    public  Double getStaffStarAvgById(Long staffId);
	
}
