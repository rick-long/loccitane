package org.spa.daoHibenate.order;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.order.ReviewRatingTreatmentTherapistDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.ReviewRatingTreatmentTherapist;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentTherapistVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewRatingTreatmentTherapistDaoHibernate extends BaseDaoHibernate<ReviewRatingTreatmentTherapist> implements ReviewRatingTreatmentTherapistDao{

    @Override
    public  Double  getAllStaffStarAvg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        StringBuffer hql=new StringBuffer();
        hql.append("SELECT");
        hql.append(" Round(avg(staff_star),1)");
        hql.append(" AS staff_star_avg");
        hql.append(" FROM review_rating_treatment_therapist rrtt");
        hql.append(" INNER JOIN review_rating_treatment rrt ON rrtt.review_rating_treatment_id=rrt.id ");
        hql.append(" INNER JOIN review r ON rrt.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" where po.is_active=1");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        Session session=getSession();
        SQLQuery query = session.createSQLQuery(hql.toString());
        String avg="0";
        if(query.uniqueResult()!=null){
            avg= query.uniqueResult().toString();

        }
        return Double.valueOf(avg);
    }



    @Override
    public List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        List<Map<String,Object>>reviewRatingTreatmentTherapistAvgAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(staff_star),1)   AS staff_star_avg,");
        hql.append(" staff_id,");
        hql.append(" first_name");
        hql.append(" FROM user u, review_rating_treatment_therapist rrtt");
        hql.append(" INNER JOIN review_rating_treatment rrt ON rrtt.review_rating_treatment_id=rrt.id ");
        hql.append(" INNER JOIN review r ON rrt.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append("  WHERE rrtt.staff_id=u.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by staff_id ");
        hql.append("  ORDER BY   staff_star_avg DESC");
        hql.append(" LIMIT 10");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        List<Object[]> list=query.list();
        for(Object[] object :list){
            Map<String, Object>reviewRatingTreatmentTherapistAvg=new HashMap<>();
            reviewRatingTreatmentTherapistAvg.put("staffStarAvg",object[0]);
            reviewRatingTreatmentTherapistAvg.put("staffId",object[1]);
            reviewRatingTreatmentTherapistAvg.put("staffName",object[2]);
            reviewRatingTreatmentTherapistAvgAvgList.add(reviewRatingTreatmentTherapistAvg);
        }
        return reviewRatingTreatmentTherapistAvgAvgList;
    }
    @Override
    public List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        List<Map<String,Object>>reviewRatingTreatmentTherapistAvgAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(staff_star),1)   AS staff_star_avg,");
        hql.append(" staff_id,");
        hql.append(" first_name");
        hql.append(" FROM user u, review_rating_treatment_therapist rrtt");
        hql.append(" INNER JOIN review_rating_treatment rrt ON rrtt.review_rating_treatment_id=rrt.id ");
        hql.append(" INNER JOIN review r ON rrt.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append("  WHERE rrtt.staff_id=u.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by staff_id ");
        hql.append("  ORDER BY   staff_star_avg ASC");
        hql.append(" LIMIT 10");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        List<Object[]> list=query.list();
        for(Object[] object :list){
            Map<String, Object>reviewRatingTreatmentTherapistAvg=new HashMap<>();
            reviewRatingTreatmentTherapistAvg.put("staffStarAvg",object[0]);
            reviewRatingTreatmentTherapistAvg.put("staffId",object[1]);
            reviewRatingTreatmentTherapistAvg.put("staffName",object[2]);
            reviewRatingTreatmentTherapistAvgAvgList.add(reviewRatingTreatmentTherapistAvg);
        }
        return reviewRatingTreatmentTherapistAvgAvgList;
    }

    @Override
    public Page<Map<String,Object>> getAllReviewRatingTreatmentTherapistAvg(ReviewRatingTreatmentTherapistVO reviewRatingTreatmentTherapistVO){
        String fromDate= reviewRatingTreatmentTherapistVO.getFromDate();
        String toDate= reviewRatingTreatmentTherapistVO.getToDate();
        int pageSize = reviewRatingTreatmentTherapistVO.getPageSize();
        int firstResult = (reviewRatingTreatmentTherapistVO.getPageNumber() - 1) * pageSize;
        List<Map<String,Object>>reviewRatingTreatmentTherapistAvgAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(staff_star),1)   AS staff_star_avg,");
        hql.append(" staff_id,");
        hql.append(" first_name");
        hql.append(" FROM user u, review_rating_treatment_therapist  rrtt ");
        hql.append(" INNER JOIN review_rating_treatment rrt ON rrtt.review_rating_treatment_id=rrt.id ");
        hql.append(" INNER JOIN review r ON rrt.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append("  WHERE rrtt.staff_id=u.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by staff_id ");
        hql.append("  ORDER BY   staff_star_avg DESC");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        query.setFirstResult(firstResult).setMaxResults(pageSize);
        List<Object[]> list=query.list();
        long totalRecords = (long) list.size();
        for(Object[] object :list){
            Map<String, Object>reviewRatingTreatmentTherapistAvg=new HashMap<>();
            reviewRatingTreatmentTherapistAvg.put("staffStarAvg",object[0]);
            reviewRatingTreatmentTherapistAvg.put("staffId",object[1]);
            reviewRatingTreatmentTherapistAvg.put("staffName",object[2]);
            reviewRatingTreatmentTherapistAvgAvgList.add(reviewRatingTreatmentTherapistAvg);
        }
        Page<Map<String,Object>>page= new Page(reviewRatingTreatmentTherapistAvgAvgList,totalRecords,reviewRatingTreatmentTherapistVO.getPageNumber(),pageSize);
        return page;
    }

    @Override
    public  Double  getStaffStarAvgById(Long staffId){
        StringBuffer hql=new StringBuffer();
        hql.append("SELECT");
        hql.append(" Round(avg(staff_star),1)");
        hql.append(" AS staff_star_avg");
        hql.append(" FROM review_rating_treatment_therapist rrtt");
        hql.append(" where rrtt.is_active=1");
        if(staffId!=null){
            hql.append(" and rrtt.staff_id="+staffId);
        }

        Session session=getSession();
        SQLQuery query = session.createSQLQuery(hql.toString());
        String avg="0";
        if(query.uniqueResult()!=null){
            avg= query.uniqueResult().toString();

        }
        return Double.valueOf(avg);
    }
}
