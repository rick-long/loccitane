package org.spa.serviceImpl.order;

import org.spa.dao.order.ReviewRatingTreatmentTherapistDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.ReviewRatingTreatmentTherapist;
import org.spa.service.order.ReviewRatingTreatmentTherapistService;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentTherapistVO;
import org.spa.vo.sales.ReviewRatingTreatmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author jason 2018-1-21
 */
@Service
public class ReviewRatingTreatmentTherapistServiceImpl extends BaseDaoHibernate<ReviewRatingTreatmentTherapist> implements ReviewRatingTreatmentTherapistService {
    @Autowired
    ReviewRatingTreatmentTherapistDao reviewRatingTreatmentTherapistDao;
    @Override
    public  Double getAllStaffStarAvg(SalesSearchVO salesSearchVO){

        return reviewRatingTreatmentTherapistDao.getAllStaffStarAvg(salesSearchVO);
    }
    @Override
    public List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO){

        return reviewRatingTreatmentTherapistDao.getTop10Avg(salesSearchVO);
    }

    @Override
    public List<Map<String,Object>>getLow10Avg(SalesSearchVO salesSearchVO){
        return reviewRatingTreatmentTherapistDao.getLow10Avg(salesSearchVO);
    }

    @Override
    public Page<Map<String,Object>> getAllReviewRatingTreatmentTherapistAvg(ReviewRatingTreatmentTherapistVO reviewRatingTreatmentTherapistVO){

        return reviewRatingTreatmentTherapistDao.getAllReviewRatingTreatmentTherapistAvg(reviewRatingTreatmentTherapistVO);
    }

    @Override
    public  Double getStaffStarAvgById(Long staffId){
        return reviewRatingTreatmentTherapistDao.getStaffStarAvgById(staffId);
    }
}
