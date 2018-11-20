package org.spa.daoHibenate.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.dao.order.PurchaseOrderDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.PurchaseOrder;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.user.ClientViewVO;
import org.springframework.stereotype.Repository;

import com.spa.constant.CommonConstant;

@Repository
public class PurchaseOrderDaoHibernate  extends BaseDaoHibernate<PurchaseOrder> implements PurchaseOrderDao {

	private static final Logger log = LoggerFactory.getLogger(PurchaseOrderDaoHibernate.class);
	
	@Override
	public List<ClientViewVO> getSalesAnalysisByClientAndShop(final Map parameters) {
		Date startDate = (Date)parameters.get("startDate");
		Date endDate = (Date)parameters.get("endDate");
		Long companyId = (Long)parameters.get("companyId");
		String prodType = (String)parameters.get("prodType");
		Long userId = (Long)parameters.get("userId");
		
//		System.out.println(" search commission analysis for prodType " + prodType);
		
		StringBuffer hql = new  StringBuffer();
		hql.append("select shop.name ");
		hql.append(", prod.prod_type ");
		hql.append(", sum(pi.amount) ");
		hql.append(", count(pi.id) ");
		hql.append(" from " );
		hql.append(" PURCHASE_ORDER po ");
		hql.append(", PURCHASE_ITEM pi ");
		hql.append(", PRODUCT_OPTION prod_opt ");
		hql.append(", PRODUCT prod ");
		hql.append(", SHOP shop ");
		hql.append(" where po.is_active = 1");
		hql.append(" and po.status = 'COMPLETED'");
		hql.append(" and po.id = pi.purchase_order_id");
		hql.append(" and pi.product_option_id = prod_opt.id");
		hql.append(" and prod_opt.product_id = prod.id");
		hql.append(" and po.shop_id = shop.id  ");
		
		if(companyId!=null){
			hql.append(" and po.company_id = "+companyId);
		}	
		if (startDate != null) {
			hql.append(" and po.purchase_date >= "+"'"+DateUtil.toString(DateUtil.getFirstMinuts(startDate), "yyyy-MM-dd HH:mm:ss")+"'");
		}
		if (endDate != null) {
			hql.append(" and po.purchase_date <= "+"'"+DateUtil.toString(DateUtil.getLastMinuts(endDate), "yyyy-MM-dd HH:mm:ss")+"'");
		}
		if(prodType!=null){
			hql.append(" and prod.prod_type = "+"'"+prodType+"'");
		}
		if(userId!=null){
			hql.append(" and po.user_id = "+userId);
		}
		hql.append(" group by  shop.id, prod.prod_type  order by shop.reference, prod.prod_type ");
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(hql.toString());
    	
    	List<ClientViewVO> results = new ArrayList<ClientViewVO>();
    	
    	List<Object[]> list = query.list();  
    	for(Object[] object : list){    
    		ClientViewVO vo = new ClientViewVO();
    		vo.setProdType((String)object[1]);
    		vo.setShopName((String)object[0]);
    		vo.setTotalSalesValue((Double)object[2]);
    		vo.setTotalTreatments((new BigDecimal((BigInteger)object[3]).intValue()));
    		results.add(vo);
    	}
		
		return results;
	}
	
	/*select sum(pi.effective_value) as totalNum from purchase_item pi
	LEFT JOIN purchase_order po ON po.id=pi.purchase_order_id
	LEFT JOIN product_option pro on pro.id=pi.product_option_id
	LEFT JOIN product prod on prod.id = pro.product_id
	where po.is_active = 1
	and po.status ='COMPLETED'
	and po.purchase_date >='2017-01-01 00:00:00'
	and po.purchase_date <='2017-01-31 23:59:59'
	and po.shop_id =18
	and po.company_id =1
	and prod.prod_type ='CA-TREATMENT';*/
	
	@Override
	public Double getTotalAmountByProdType(final Date startDate,final Date endDate,final Long shopId,final Long companyId,final String status,final String prodType){
		Double totalService=0d;
		
		StringBuffer hql = new  StringBuffer();
		hql.append("select sum(pi.effective_value) as totalEffectiveVal from purchase_item pi ");
		hql.append(" LEFT JOIN purchase_order po ON po.id=pi.purchase_order_id");
		hql.append(" LEFT JOIN product_option pro on pro.id=pi.product_option_id");
		hql.append(" LEFT JOIN product prod on prod.id = pro.product_id");
		hql.append(" where po.is_active = 1");
		if(StringUtils.isNotBlank(status)){
			hql.append(" and po.status ='").append(status).append("'");
		}
		if(startDate !=null){
			hql.append(" and po.purchase_date >='").append(DateUtil.toString(DateUtil.getFirstMinuts(startDate), "yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if(endDate !=null){
			hql.append(" and po.purchase_date <='").append(DateUtil.toString(DateUtil.getFirstMinuts(endDate), "yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if(shopId !=null){
			hql.append(" and po.shop_id = ").append(shopId);
		}
		if(companyId !=null){
			hql.append(" and po.company_id = ").append(companyId);
		}
		hql.append(" and prod.prod_type ='").append(prodType).append("'");
		
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(hql.toString());
    	
		query.addScalar("totalEffectiveVal", StandardBasicTypes.DOUBLE);
    	
		if(query.uniqueResult() !=null){
			totalService = (Double)query.uniqueResult();
		}
    	log.debug("total effective value :" + totalService +" for "+prodType);
    	
		return totalService;
	}
	
	@Override
	public Double getTotalPackageByFilters(final Date startDate,final Date endDate, final Long shopId, final Long companyId) {
		
		Double totalPackage=0d;
		
		StringBuffer hql = new  StringBuffer();
		hql.append("select sum(pi.amount) as totalAmount ");
		hql.append(" from purchase_item pi");
		hql.append(" LEFT JOIN purchase_order po ON po.id=pi.purchase_order_id");
		hql.append(" where po.is_active = 1");
		hql.append(" and po.status ='COMPLETED'");
		if(startDate !=null){
			hql.append(" and po.purchase_date >='").append(DateUtil.toString(DateUtil.getFirstMinuts(startDate), "yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if(endDate !=null){
			hql.append(" and po.purchase_date <='").append(DateUtil.toString(DateUtil.getFirstMinuts(endDate), "yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if(shopId !=null){
			hql.append(" and po.shop_id = ").append(shopId);
		}
		if(companyId !=null){
			hql.append(" and po.company_id = ").append(companyId);
		}
		hql.append(" and pi.prepaid_top_up_transaction_id is not null");
		
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(hql.toString());
    	
		query.addScalar("totalAmount", StandardBasicTypes.DOUBLE);
    	
		if(query.uniqueResult() !=null){
			totalPackage = (Double)query.uniqueResult();
		}
    	log.debug("total pachages amount :" + totalPackage);
    	
		return totalPackage;
	}

	/*select sum(pi.amount) as totalAmount from purchase_item pi
	LEFT JOIN purchase_order po ON po.id=pi.purchase_order_id
	where po.is_active = 1
	and po.status ='COMPLETED'
	and po.purchase_date >='2017-01-01 00:00:00'
	and po.purchase_date <='2017-01-31 23:59:59'
	and po.shop_id =18
	and po.company_id =1*/
	@Override
	public Double getTotalRevenueByFilters(final Date startDate, final Date endDate, final Long shopId, final Long companyId) {
		
		Double totalRevenue=0d;
		
		StringBuffer hql = new  StringBuffer();
		hql.append("select sum(pi.amount) as totalAmount ");
		hql.append(" from purchase_item pi");
		hql.append(" LEFT JOIN purchase_order po ON po.id=pi.purchase_order_id");
		hql.append(" where po.is_active = 1");
		hql.append(" and po.status ='COMPLETED'");
		if(startDate !=null){
			hql.append(" and po.purchase_date >='").append(DateUtil.toString(DateUtil.getFirstMinuts(startDate), "yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if(endDate !=null){
			hql.append(" and po.purchase_date <='").append(DateUtil.toString(DateUtil.getFirstMinuts(endDate), "yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if(shopId !=null){
			hql.append(" and po.shop_id = ").append(shopId);
		}
		if(companyId !=null){
			hql.append(" and po.company_id = ").append(companyId);
		}
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(hql.toString());
    	
		query.addScalar("totalAmount", StandardBasicTypes.DOUBLE);
    	
		if(query.uniqueResult() !=null){
			totalRevenue = (Double)query.uniqueResult();
		}
    	log.debug("total revenue :" + totalRevenue);
    	
		return totalRevenue;
	}

	@Override
	public List<PurchaseOrder> getPurchaseOrdersByIds(Long[] piIds) {
		String idsp="";
		int idx=1;
		for(Long id : piIds){
			idsp += id+",";
			if(idx == piIds.length){
				idsp += id;
			}else{
				idsp += id+",";
			}
			idx++;
		}
		String sql="select * from  PURCHASE_ORDER  where id in ("+idsp+") order by id desc ";
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.addEntity(PurchaseOrder.class);
    	List<PurchaseOrder> results = (List<PurchaseOrder>) query.list();  
		return results;
	}
	
	@Override
	public Long getCountOrdersByFilters(final String fromDate,final String endDate,final String orderStatus,final Long userId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select COUNT(po.id) as countNumber from PURCHASE_ORDER po ");
		hql.append(" where po.is_active=1");
		if(fromDate !=null){
			hql.append(" and po.purchase_date >=").append("'").append(fromDate).append("'");
		}
		if(endDate !=null){
			hql.append(" and po.purchase_date <=").append("'").append(endDate).append("'");
		}
		if(userId !=null){
			hql.append(" and po.user_id= ").append(userId);
		}
		if(StringUtils.isNotBlank(orderStatus)){
			hql.append(" and po.status =").append("'").append(orderStatus).append("'");
		}
		
		hql.append(" group by po.user_id having COUNT( po.id) ");
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
		query.addScalar("countNumber", StandardBasicTypes.LONG);
		Long countNumber = new Long((long) query.uniqueResult());
		
		return countNumber;
	}
	
	@Override
	public Long getCountMembersFromOrdersByFilters(final SalesSearchVO salesSearchVO){
		Long count =0l;
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PurchaseOrder.class);
		detachedCriteria.add(Restrictions.eq("isActive", true));
		detachedCriteria.add(Restrictions.eq("company.id", salesSearchVO.getCompanyId()));
		//from date
		if (StringUtils.isNotBlank(salesSearchVO.getFromDate())) {
			detachedCriteria.add(Restrictions.ge("purchaseDate",DateUtil.stringToDate(salesSearchVO.getFromDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		//to date
		if (StringUtils.isNotBlank(salesSearchVO.getToDate())) {
			detachedCriteria.add(Restrictions.le("purchaseDate",DateUtil.stringToDate(salesSearchVO.getToDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		//shop
		if(salesSearchVO.getShopId() !=null && salesSearchVO.getShopId().longValue()>0){
			detachedCriteria.add(Restrictions.eq("shop.id", salesSearchVO.getShopId()));
		}else{
			if(salesSearchVO.getIsSearchByJob() !=null && salesSearchVO.getIsSearchByJob()){
				
			}else{
				detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
			}
		}
		detachedCriteria.createAlias("user", "u");
		detachedCriteria.add(Restrictions.eq("u.accountType",CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
		
		Long memberCount = (Long) detachedCriteria.setProjection(Projections.countDistinct("user.id")).getExecutableCriteria(getSession()).uniqueResult();
		detachedCriteria.setProjection(null);
		
		count = memberCount + getCountGuestFromOrderByFilters(salesSearchVO);
		return count;
	}
	
	private Long getCountGuestFromOrderByFilters(final SalesSearchVO salesSearchVO){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PurchaseOrder.class);
		detachedCriteria.add(Restrictions.eq("isActive", true));
		detachedCriteria.add(Restrictions.eq("company.id", salesSearchVO.getCompanyId()));
		//from date
		if (StringUtils.isNotBlank(salesSearchVO.getFromDate())) {
			detachedCriteria.add(Restrictions.ge("purchaseDate",DateUtil.stringToDate(salesSearchVO.getFromDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		//to date
		if (StringUtils.isNotBlank(salesSearchVO.getToDate())) {
			detachedCriteria.add(Restrictions.le("purchaseDate",DateUtil.stringToDate(salesSearchVO.getToDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		//shop
		if(salesSearchVO.getShopId() !=null && salesSearchVO.getShopId().longValue()>0){
			detachedCriteria.add(Restrictions.eq("shop.id", salesSearchVO.getShopId()));
		}else{
			if(salesSearchVO.getIsSearchByJob() !=null && salesSearchVO.getIsSearchByJob()){
				
			}else{
				detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
			}
		}
		detachedCriteria.createAlias("user", "u");
		detachedCriteria.add(Restrictions.eq("u.accountType",CommonConstant.USER_ACCOUNT_TYPE_GUEST));
		Long guestCount = (Long) detachedCriteria.setProjection(Projections.countDistinct("id")).getExecutableCriteria(getSession()).uniqueResult();
		detachedCriteria.setProjection(null);
		return guestCount;
	}
}
