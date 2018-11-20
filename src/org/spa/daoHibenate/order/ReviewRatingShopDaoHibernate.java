package org.spa.daoHibenate.order;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.order.ReviewRatingShopDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.ReviewRatingShop;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.ReviewRatingShopVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewRatingShopDaoHibernate extends BaseDaoHibernate<ReviewRatingShop> implements ReviewRatingShopDao{
   @Override
    public  Double getAllShopCustomerServicesAvg(SalesSearchVO salesSearchVO){
       String fromDate= salesSearchVO.getFromDate();
       String toDate= salesSearchVO.getToDate();
        StringBuffer hql=new StringBuffer();
        hql.append("SELECT");
        hql.append(" Round(avg(customer_service_star),1)");
        hql.append(" AS customer_service_avg");
        hql.append(" FROM review_rating_shop rrs ");
        hql.append(" INNER JOIN review r ON rrs.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" where po.is_active=1");

       if(salesSearchVO!=null&& StringUtils.isNotBlank(fromDate)){
           hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
       }
       if(salesSearchVO!=null&& StringUtils.isNotBlank(toDate)){
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
    public  Double getAllShopCleanlinessAvg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        StringBuffer hql=new StringBuffer();
        hql.append("SELECT");
        hql.append(" Round(avg(cleanliness_star),1)");
        hql.append(" AS cleanliness_avg");
        hql.append(" FROM review_rating_shop rrs ");
        hql.append(" INNER JOIN review r ON rrs.review_id=r.id ");
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
    public List<Map<String,Object>> getTop10Avg(SalesSearchVO salesSearchVO ){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        List<Map<String,Object>>reviewRatingShopAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(customer_service_star),1)   AS customer_service_avg,");
        hql.append(" Round(avg(cleanliness_star),1)   AS cleanliness_avg,rrs.shop_id,");
        hql.append(" s.name");
        hql.append(" FROM shop s, review_rating_shop rrs");
        hql.append(" INNER JOIN review r ON  rrs.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" WHERE rrs.shop_id=s.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by rrs.shop_id");
        hql.append(" ORDER BY   customer_service_avg DESC");
        hql.append(" LIMIT 10");
        System.out.println(hql);
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        List<Object[]> list=query.list();
        for(Object[] object :list){
            Map<String, Object>reviewRatingShopAvg=new HashMap<>();
            reviewRatingShopAvg.put("customerServiceStar",object[0]);
            reviewRatingShopAvg.put("cleanlinessStar",object[1]);
            reviewRatingShopAvg.put("shopId",object[2]);
            reviewRatingShopAvg.put("shopName",object[3]);
            reviewRatingShopAvgList.add(reviewRatingShopAvg);
        }
        return reviewRatingShopAvgList;
    }

    @Override
    public List<Map<String,Object>> getLow10Avg(SalesSearchVO salesSearchVO){
        String fromDate= salesSearchVO.getFromDate();
        String toDate= salesSearchVO.getToDate();
        List<Map<String,Object>>reviewRatingShopAvgList=new ArrayList<>();
        StringBuffer hql=new StringBuffer();
        hql.append(" SELECT");
        hql.append(" Round(avg(customer_service_star),1)   AS customer_service_avg,");
        hql.append(" Round(avg(cleanliness_star),1)   AS cleanliness_avg,rrs.shop_id,");
        hql.append(" s.name");
        hql.append(" FROM shop s, review_rating_shop rrs");
        hql.append(" INNER JOIN review r ON rrs.review_id=r.id ");
        hql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        hql.append(" WHERE rrs.shop_id=s.id");
        if(StringUtils.isNotBlank(fromDate)){
            hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            hql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        hql.append(" group by rrs.shop_id");
        hql.append(" ORDER BY   customer_service_avg ASC");
        hql.append(" LIMIT 10");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(hql.toString());
        List<Object[]> list=query.list();
        for(Object[] object :list){
            Map<String, Object>reviewRatingShopAvg=new HashMap<>();
            reviewRatingShopAvg.put("customerServiceStar",object[0]);
            reviewRatingShopAvg.put("cleanlinessStar",object[1]);
            reviewRatingShopAvg.put("shopId",object[2]);
            reviewRatingShopAvg.put("shopName",object[3]);
            reviewRatingShopAvgList.add(reviewRatingShopAvg);
        }
        return reviewRatingShopAvgList;
    }
    @Override

    public Page<Map<String,Object>>getAllReviewRatingShopAvg(ReviewRatingShopVO reviewRatingShopVO){
        String fromDate= reviewRatingShopVO.getFromDate();
        String toDate= reviewRatingShopVO.getToDate();
        int pageSize = reviewRatingShopVO.getPageSize();
        int firstResult = (reviewRatingShopVO.getPageNumber() - 1) * pageSize;
        List<Map<String,Object>>reviewRatingShopAvgList=new ArrayList<>();
        StringBuffer dataHql=new StringBuffer();
        dataHql.append(" SELECT");
        dataHql.append(" Round(avg(customer_service_star),1)   AS customer_service_avg,");
        dataHql.append(" Round(avg(cleanliness_star),1)  AS cleanliness_avg,rrs.shop_id,");
        dataHql.append(" s.name");
        dataHql.append(" FROM shop s,review_rating_shop rrs");
        dataHql.append(" INNER JOIN review r ON review_id=r.id ");
        dataHql.append(" INNER  JOIN purchase_order po ON r.order_id = po.id");
        dataHql.append(" WHERE rrs.shop_id=s.id");
        if(StringUtils.isNotBlank(fromDate)){
            dataHql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
        }
        if(StringUtils.isNotBlank(toDate)){
            dataHql.append(" and po.purchase_date <=").append("'").append(toDate).append("'");
        }
        dataHql.append(" group by rrs.shop_id");
        dataHql.append(" ORDER BY customer_service_avg DESC");
        Session session=getSession();
        SQLQuery query=session.createSQLQuery(dataHql.toString());
        query.setFirstResult(firstResult).setMaxResults(pageSize);
        List<Object[]> list=query.list();
        long totalRecords = (long) list.size();
        for(Object[] object :list){
            Map<String, Object>reviewRatingShopAvg=new HashMap<>();
            reviewRatingShopAvg.put("customerServiceStar",object[0]);
            reviewRatingShopAvg.put("cleanlinessStar",object[1]);
            reviewRatingShopAvg.put("shopId",object[2]);
            reviewRatingShopAvg.put("shopName",object[3]);
            reviewRatingShopAvgList.add(reviewRatingShopAvg);
        }
     Page<Map<String,Object>>page= new Page(reviewRatingShopAvgList,totalRecords,reviewRatingShopVO.getPageNumber(),pageSize);
        return page;
    }


}
