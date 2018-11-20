package org.spa.daoHibenate.order;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.order.ReviewDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.Review;
import org.spa.vo.report.SalesSearchVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewDaoHibernate extends BaseDaoHibernate<Review> implements ReviewDao{
@Override
public Long getCount(SalesSearchVO salesSearchVO){
    String fromDate= salesSearchVO.getFromDate();
    String toDate= salesSearchVO.getToDate();
    StringBuffer hql=new StringBuffer();
    hql.append("SELECT");
    hql.append(" count(*)");
    hql.append(" FROM review r INNER JOIN purchase_order po");
    hql.append(" ON r.order_id = po.id ");
    hql.append(" WHERE po.is_active=1 ");
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
    public Long getNpsSum(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        StringBuffer hql=new StringBuffer();
        hql.append("SELECT");
        hql.append(" sum(nps)");
        hql.append(" FROM review r INNER JOIN purchase_order po");
        hql.append(" ON r.order_id = po.id ");
        hql.append(" WHERE po.is_active=1 ");
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
    public List<Map<String,Object>> getListing(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        List<Map<String,Object>>listings=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" r.nps AS r_nps, ");
        hql.append(" r.created AS r_date, ");
        hql.append(" r.created_by AS r_name,");
        hql.append(" r.review_text AS review_text,");
        hql.append(" rrs.customer_service_star AS customer_service_rating,");
        hql.append(" rrs.cleanliness_star AS cleanliness_rating,");
        hql.append(" rrtt.staff_star AS therapist_star ");
        hql.append(" FROM review r LEFT JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" LEFT JOIN review_rating_treatment rrt ON r.id = rrt.review_id");
        hql.append(" LEFT JOIN review_rating_shop rrs ON r.id = rrs.review_id");
        hql.append(" LEFT JOIN review_rating_treatment_therapist rrtt  ON rrt.id = rrtt.review_rating_treatment_id");
        hql.append(" where po.is_active=1");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }

        if(salesSearchVO.getProductOptionId()!=null){

            hql.append(" and rrt.product_option_id= "+ salesSearchVO.getProductOptionId());

        }
        if(salesSearchVO.getShopId()!=null){

            hql.append(" and rrs.shop_id= "+ salesSearchVO.getShopId());

        }
        if(salesSearchVO.getStaffId()!=null){

            hql.append(" and rrtt.staff_id= "+ salesSearchVO.getStaffId());

        }
        hql.append(" ORDER BY rrt.satisfaction_level_star ASC");
        System.out.println(hql.toString());
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        List<Object[]> list=query.list();
        for(Object[] object :list){
            Map<String, Object>listing=new HashMap<>();
            listing.put("nps",object[0]);
            listing.put("date",object[1]);
            listing.put("name",object[2]);
            listing.put("reviewText",object[3]);
            listing.put("customerServiceRating",object[4]);
            listing.put("cleanlinessRating",object[5]);
            listing.put("therapistStar",object[6]);
            listings.add(listing);
        }
        return listings;
    }
}
