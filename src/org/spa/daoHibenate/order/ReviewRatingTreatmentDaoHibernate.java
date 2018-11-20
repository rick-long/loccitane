package org.spa.daoHibenate.order;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.order.ReviewRatingTreatmentDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.ReviewRatingTreatment;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingTreatmentVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewRatingTreatmentDaoHibernate extends BaseDaoHibernate<ReviewRatingTreatment> implements ReviewRatingTreatmentDao{
    @Override
    public  Double getAllSatisfactionLevelStarAvg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        StringBuffer hql=new StringBuffer();
        hql.append("SELECT");
        hql.append(" Round(avg(satisfaction_level_star),1)");
        hql.append(" AS satisfaction_level_avg");
        hql.append(" FROM review_rating_treatment rrt ");
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
    public  Double getAllValueForMoneyStarAvg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        StringBuffer hql=new StringBuffer();
        hql.append("SELECT");
        hql.append(" Round(avg(value_for_money_star),1)");
        hql.append(" AS value_for_money_avg");
        hql.append(" FROM review_rating_treatment rrt");
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
        List<Map<String,Object>>reviewRatingTreatmentAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(satisfaction_level_star),1)   AS satisfaction_level_avg,");
        hql.append(" Round(avg(value_for_money_star),1)   AS value_for_money_avg,");
        hql.append(" product_id,");
        hql.append(" p.name");
        hql.append(" FROM product p,review_rating_treatment rrt");
        hql.append(" INNER JOIN review r ON rrt.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" WHERE rrt.product_id=p.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by product_id ");
        hql.append(" ORDER BY   satisfaction_level_avg DESC");
        hql.append(" LIMIT 10");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        List<Object[]> list=query.list();
        for(Object[] object :list){
            Map<String, Object>reviewRatingTreatmentAvg=new HashMap<>();
            reviewRatingTreatmentAvg.put("satisfactionLevelAvg",object[0]);
            reviewRatingTreatmentAvg.put("valueForMoneyAvg",object[1]);
            reviewRatingTreatmentAvg.put("productId",object[2]);
            reviewRatingTreatmentAvg.put("productName",object[3]);
            reviewRatingTreatmentAvgList.add(reviewRatingTreatmentAvg);
        }
        return reviewRatingTreatmentAvgList;
    }
    @Override
    public List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        List<Map<String,Object>>reviewRatingTreatmentAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(satisfaction_level_star),1)   AS satisfaction_level_avg,");
        hql.append(" Round(avg(value_for_money_star),1)   AS value_for_money_avg,");
        hql.append(" product_id,");
        hql.append(" p.name");
        hql.append(" FROM product p,review_rating_treatment rrt");
        hql.append(" INNER JOIN review r ON rrt.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" WHERE rrt.product_id=p.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by product_id ");
        hql.append(" ORDER BY   satisfaction_level_avg ASC");
        hql.append(" LIMIT 10");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        List<Object[]> list=query.list();
        for(Object[] object :list){
            Map<String, Object>reviewRatingTreatmentAvg=new HashMap<>();
            reviewRatingTreatmentAvg.put("satisfactionLevelAvg",object[0]);
            reviewRatingTreatmentAvg.put("valueForMoneyAvg",object[1]);
            reviewRatingTreatmentAvg.put("productId",object[2]);
            reviewRatingTreatmentAvg.put("productName",object[3]);
            reviewRatingTreatmentAvgList.add(reviewRatingTreatmentAvg);
        }
        return reviewRatingTreatmentAvgList;
    }
    @Override
    public Page<Map<String,Object>> getAllReviewRatingTreatmentAvg(ReviewRatingTreatmentVO reviewRatingTreatmentVO){
        String fromDate= reviewRatingTreatmentVO.getFromDate();
        String toDate= reviewRatingTreatmentVO.getToDate();
        int pageSize = reviewRatingTreatmentVO.getPageSize();
        int firstResult = (reviewRatingTreatmentVO.getPageNumber() - 1) * pageSize;
        List<Map<String,Object>>reviewRatingTreatmentAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(satisfaction_level_star),1)   AS satisfaction_level_avg,");
        hql.append(" Round(avg(value_for_money_star),1)   AS value_for_money_avg,");
        hql.append(" product_id,");
        hql.append(" p.name");
        hql.append(" FROM product p,review_rating_treatment r");
        hql.append(" INNER JOIN review r ON rrt.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" WHERE rrt.product_id=p.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by product_id ");
        hql.append(" ORDER BY   satisfaction_level_avg DESC");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        query.setFirstResult(firstResult).setMaxResults(pageSize);
        List<Object[]> list=query.list();
        long totalRecords = (long) list.size();
        for(Object[] object :list){
            Map<String, Object>reviewRatingTreatmentAvg=new HashMap<>();
            reviewRatingTreatmentAvg.put("satisfactionLevelAvg",object[0]);
            reviewRatingTreatmentAvg.put("valueForMoneyAvg",object[1]);
            reviewRatingTreatmentAvg.put("productId",object[2]);
            reviewRatingTreatmentAvg.put("productName",object[3]);
            reviewRatingTreatmentAvgList.add(reviewRatingTreatmentAvg);
        }
        Page<Map<String,Object>>page= new Page(reviewRatingTreatmentAvgList,totalRecords,reviewRatingTreatmentVO.getPageNumber(),pageSize);
        return page;
    }
        @Override
        public Long getCount(SalesSearchVO salesSearchVO,Integer star,Integer starLe){
            String fromDate= salesSearchVO.getFromDate();
            String toDate= salesSearchVO.getToDate();
            StringBuffer hql=new StringBuffer();
            hql.append("SELECT");
            hql.append(" count(*)");
            hql.append(" FROM review_rating_treatment rrt ");
            hql.append(" INNER JOIN review r ON rrt.review_id = r.id ");
            hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
            hql.append(" WHERE po.is_active=1 ");
            if(star!=null){
                hql.append(" and satisfaction_level_star=").append(star);
            }
            if(starLe!=null){
                hql.append(" and satisfaction_level_star<=").append(starLe);
            }
            if(StringUtils.isNotBlank(fromDate)){
                hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
            }
            if(StringUtils.isNotBlank(toDate)){
                hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
            }
            Session session=getSession();
            SQLQuery query = session.createSQLQuery(hql.toString());
            String count="0";
            if(query.uniqueResult()!=null){
                count= query.uniqueResult().toString();
            }
            return Long.valueOf(count);

        }

        @Override
        public  Double getSatisfactionLevelStarAvgByTreatmentId(Long treatmentId){
            StringBuffer hql=new StringBuffer();
            hql.append("SELECT");
            hql.append(" Round(avg(satisfaction_level_star),1)");
            hql.append(" AS satisfaction_level_avg");
            hql.append(" FROM review_rating_treatment rrt ");
            hql.append(" where rrt.is_active=1");
            if(treatmentId!=null){
                hql.append(" and rrt.product_id ="+treatmentId);
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


