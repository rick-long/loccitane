package org.spa.daoHibenate.order;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.dao.order.StaffCommissionDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.StaffCommission;
import org.spa.utils.DateUtil;
import org.springframework.stereotype.Repository;

@Repository
public class StaffCommissionDaoHibernate extends BaseDaoHibernate<StaffCommission> implements StaffCommissionDao{
	
	private static final Logger log = LoggerFactory.getLogger(StaffCommissionDaoHibernate.class);
	
	@Override
	public void deleteStaffCommissionsByPurchaseItem(final Long purchaseItemId){
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("delete STAFF_COMMISSION sc where sc.purchase_item_id = "+purchaseItemId);
		Transaction tx=session.beginTransaction();
		query.executeUpdate();
		tx.commit();
	}

	@Override
	public Double[] sumStaffCommissionByFilter(Map<String, Object> filterMap,List<Long> categoryIdList) {
		
    	String sql = getStaffCommissionBySQL(filterMap,Boolean.TRUE,categoryIdList);
    	
    	Session session = getSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	
		query.addScalar("sumComm", StandardBasicTypes.DOUBLE);
		query.addScalar("sumSales", StandardBasicTypes.DOUBLE);
    	
    	Object[] objects = (Object[])query.uniqueResult();
    	log.debug("total commission :" + objects[0]);
    	
    	Double[] sum = null;
    	if (objects!=null && objects.length>0) {
    		sum = new Double[objects.length];
    		for (int i =0; i<objects.length; i++) {
    			sum[i] = (Double)objects[i];
            }
    	}
    	return sum;
	}
	
	@Override
	public List<StaffCommission> getStaffCommissionsByFilter(Map<String, Object> filterMap,List<Long> categoryIdList) {
		
		String sql = getStaffCommissionBySQL(filterMap,Boolean.FALSE,categoryIdList);
	    	
    	Session session = getSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.addEntity(StaffCommission.class);
    	
    	List<StaffCommission> resultList = query.list();
    	
		return resultList;
	}
	
	/*******  sales
	SELECT STRAIGHT_JOIN sum(sc.commission_value) as sumComm,sum(sc.amount) as sumSales
	FROM STAFF_COMMISSION sc
	LEFT JOIN PURCHASE_ITEM pi ON sc.purchase_item_id = pi.id
	LEFT JOIN PRODUCT_OPTION opt ON pi.product_option_id = opt.id
	LEFT JOIN PRODUCT p ON opt.product_id = p.id
	LEFT JOIN CATEGORY c ON p.category_id=c.id
	LEFT JOIN USER u ON sc.staff_id=u.id
	where 1=1
	and sc.staff_id=11
	and sc.purchase_date >='2016-06-01 00:00:00'
	and sc.purchase_date <= '2016-06-21 23:59:59' 
	and sc.is_active = 1  
	and c.id in();
	and u.company_id=1;

	 **/
	
	/*******  package
	SELECT STRAIGHT_JOIN sum(sc.commission_value) as sumComm,sum(sc.amount) as sumSales
	FROM STAFF_COMMISSION sc
	LEFT JOIN PURCHASE_ITEM pi ON sc.purchase_item_id = pi.id
	LEFT JOIN USER u ON sc.staff_id=u.id
	where 1=1
	and pi.prepaid_top_up_transaction_id is not null
	and sc.staff_id=11
	and sc.purchase_date >='2016-06-01 00:00:00'
	and sc.purchase_date <= '2016-06-21 23:59:59' 
	and sc.is_active = 1  
	and u.company_id=1;

	 **/
	private String getStaffCommissionBySQL(final Map filterMap,final Boolean isGetSum,List<Long> categoryIdList) {
		Boolean isPackageSales=false;
		if(filterMap.get("isPackageSales") !=null && (Boolean)filterMap.get("isPackageSales")){
			isPackageSales=true;
		}
		String fromDate="";
		if((Date)filterMap.get("fromDate") !=null){
			fromDate=DateUtil.toString((Date)filterMap.get("fromDate"),"yyyy-MM-dd HH:mm:ss") ;
		}
		
		String toDate ="";
		if((Date)filterMap.get("toDate") !=null){
			toDate=DateUtil.toString((Date)filterMap.get("toDate"),"yyyy-MM-dd HH:mm:ss") ;
		}
    	
    	Long companyId = (Long)filterMap.get("companyId");
    	Long staffId = (Long)filterMap.get("staffId");
    	
    	
    	StringBuffer selectSQL = new StringBuffer();
    	
    	if(isGetSum.booleanValue()){
    		selectSQL.append("SELECT STRAIGHT_JOIN sum(sc.commission_value) as sumComm,sum(sc.amount) as sumSales ");
    	}else{
    		selectSQL.append("select sc.* ");
    	}
    	selectSQL.append("FROM STAFF_COMMISSION sc ");
    	if(categoryIdList !=null && categoryIdList.size()>0 ){
    		selectSQL.append("LEFT JOIN PURCHASE_ITEM pi ON sc.purchase_item_id = pi.id ");
        	selectSQL.append("LEFT JOIN PRODUCT_OPTION opt ON pi.product_option_id = opt.id ");
        	selectSQL.append("LEFT JOIN PRODUCT p ON opt.product_id = p.id ");
        	selectSQL.append("LEFT JOIN CATEGORY c ON p.category_id=c.id ");
    	}
    	if(isPackageSales){
    		selectSQL.append("LEFT JOIN PURCHASE_ITEM pi ON sc.purchase_item_id = pi.id ");
    	}
    	if(companyId !=null){
    		selectSQL.append("LEFT JOIN USER u ON sc.staff_id=u.id ");
    	}
    	selectSQL.append("where sc.is_active=1 ");
    	
    	if(isPackageSales){
    		selectSQL.append(" and pi.prepaid_top_up_transaction_id is not null");
    	}
    	if(staffId !=null){
    		selectSQL.append(" and sc.staff_id="+staffId);
    	}
    	if(categoryIdList !=null && categoryIdList.size()>0 ){
    		selectSQL.append(" and c.id in(");
    		int index=1;
    		for(Long id : categoryIdList){
    			if(index == categoryIdList.size()){
    				selectSQL.append(id);
    			}else{
    				selectSQL.append(id+",");
    			}
    			index++;
    		}
    		selectSQL.append(") ");
    	}
    	if(StringUtils.isNotBlank(fromDate)){
    		selectSQL.append(" and sc.purchase_date >="+"'"+fromDate+"'");
    	}
    	if(StringUtils.isNotBlank(toDate)){
    		selectSQL.append(" and sc.purchase_date <="+"'"+toDate+"'");
    	}
    	if(companyId !=null){
    		selectSQL.append(" and u.company_id ="+companyId);
    	}
//    	System.out.println("sql:----"+selectSQL.toString());
    	return selectSQL.toString();
    }
}
