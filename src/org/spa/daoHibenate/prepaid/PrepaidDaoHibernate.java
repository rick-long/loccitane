package org.spa.daoHibenate.prepaid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.spa.dao.prepaid.PrepaidDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.utils.DateUtil;
import org.spa.vo.report.PrepaidAnalysisVO;
import org.springframework.stereotype.Repository;

import com.spa.constant.CommonConstant;

@Repository
public class PrepaidDaoHibernate extends BaseDaoHibernate<Prepaid> implements PrepaidDao{
	
	@Override
	public List<Prepaid> getSuitablePackagesByFilter(final Long memberId, final Long productOptionId,final String prepaidSuitableOption,final String prodType,final Boolean usingCashPackage) {
		StringBuilder sb=new StringBuilder();
		sb.append(" select p.* from PREPAID p");
		String pttSearchSql = "";
		if(prepaidSuitableOption.equals(CommonConstant.PREPAID_SUITABLE_OPTION_EQUAL)){
			sb.append(" LEFT JOIN PREPAID_TOP_UP_TRANSACTION ptt on ptt.prepaid_id = p.id");
			pttSearchSql = " and ptt.is_active=1 ";
		}
		sb.append("  where p.is_active = 1 and p.member_id = ?").append(pttSearchSql);
		if(usingCashPackage){
			sb.append(" and (p.prepaid_type='"+CommonConstant.PREPAID_TYPE_CASH_PACKAGE+"'");
		}
		if(prodType !=null && productOptionId !=null && !prodType.equals(CommonConstant.CATEGORY_REF_GOODS)){
			if(usingCashPackage){
				sb.append(" or ");
				
			}else{
				sb.append(" and ");
			}
			sb.append(" (p.prepaid_type='"+CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE+"'");
			if(prepaidSuitableOption.equals(CommonConstant.PREPAID_SUITABLE_OPTION_EQUAL)){
				//只获取当前产品的treatment packages
				sb.append(" and ptt.product_option_id = ?");
				sb.append(" )");
			}
		}
		if(usingCashPackage){
			sb.append(" ) order by p.prepaid_type");
		}else{
			sb.append(" order by p.prepaid_type");
		}
		
		Session session = getSession();
		System.out.println("---sb.toString()---"+sb.toString());
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(Prepaid.class);
		query.setLong(0, memberId);
		if(productOptionId !=null && prepaidSuitableOption.equals(CommonConstant.PREPAID_SUITABLE_OPTION_EQUAL)){
			query.setLong(1, productOptionId);
		}
		List<Prepaid> resultList = query.list();
		return resultList;
	}

	@Override
	public List<PrepaidTopUpTransaction> getPrepaidTopUpTransactions(final String finishDate, Long shopId, Long companyId) {
		List<Long> ids =getPrepaidTopUpTransactionsIds(finishDate, shopId, companyId);
		List<PrepaidTopUpTransaction> pttList =new ArrayList<PrepaidTopUpTransaction>();
		if(ids !=null && ids.size()>0){
			Long[] idsLong = ids.toArray(new Long[ids.size()]);
			pttList = getPrepaidTopUpTransactionByIds(idsLong);
		}
		return pttList;
	}
	
	private List<Long> getPrepaidTopUpTransactionsIds(final String finishDate, Long shopId, Long companyId) {
		
		String shopObj = "";
		String shopSql = "";
		if(shopId !=null){
			shopObj ="left join PREPAID p on p.id = ptt.prepaid_id";
			shopSql +=" and p.shop_id ="+shopId;
		}
		String sql = " select DISTINCT(ptt.id) from PREPAID_TOP_UP_TRANSACTION ptt "+shopObj
					+" where 1=1 and ptt.is_active=1" +shopSql;
		if(companyId !=null){
			sql +=" and ptt.company_id = "+companyId;
		}
		if(finishDate !=null){
			sql +=" and ptt.expiry_date >'"+finishDate+"'";
		}
		
		sql +=" order by ptt.id ";
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.addScalar("id", StandardBasicTypes.LONG); 
		List<Long> pttIds = query.list();
		return pttIds;
	}
	
	@Override
	public Map<Long, PrepaidAnalysisVO> getPrepaidOutstandingUsedByHql(String finishDate, Long shopId,Long companyId) {
		
		List<Long> ids =getPrepaidTopUpTransactionsIds(finishDate, shopId, companyId);
		
		String sql = " select ptt.*,sum(pi.effective_value) as sumEv from PREPAID_TOP_UP_TRANSACTION ptt "
				+" left join PAYMENT pay on pay.prepaid_top_up_transaction_id= ptt.id left join PURCHASE_ITEM pi on pi.id=pay.purchase_item_id "
				+" where 1=1 and pay.prepaid_top_up_transaction_id in(:ids) ";

        sql+=" group by ptt.id order by ptt.top_up_date,ptt.top_up_reference";
		
		Map<Long, PrepaidAnalysisVO> voMap = new HashMap<Long, PrepaidAnalysisVO>();
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if (ids !=null && ids.size() >0) {
			query.setParameterList("ids", ids);
    	}
		query.addEntity(PrepaidTopUpTransaction.class);
		query.addScalar("sumEv", StandardBasicTypes.DOUBLE);
		 
		List<Object[]> objs = query.list();
		if(objs !=null && objs.size()>0){
			for(Object[] obj : objs){
				PrepaidAnalysisVO vo =new PrepaidAnalysisVO();
				PrepaidTopUpTransaction ptt = (PrepaidTopUpTransaction)obj[0];
				vo.setPtt(ptt);
				vo.setAmount((Double)obj[1]);
				vo.setPrepaidType(ptt.getPackageType());
				voMap.put(ptt.getId(), vo);
			}
		}
		return voMap;
	}
	
	@Override
	public List<PrepaidTopUpTransaction> getPrepaidTopUpTransactionByIds(final Long[] pttIds) {
		
		String idsp="";
		int idx=1;
		for(Long id : pttIds){
			idsp += id+",";
			if(idx == pttIds.length){
				idsp += id;
			}else{
				idsp += id+",";
			}
			idx++;
		}
		String sql="select * from  PREPAID_TOP_UP_TRANSACTION  where id in ("+idsp+") order by id desc ";
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.addEntity(PrepaidTopUpTransaction.class);
    	List<PrepaidTopUpTransaction> results = (List<PrepaidTopUpTransaction>) query.list();  
		return results;
	}

	@Override
	public List<PrepaidTopUpTransaction> getExpiringPrepaidsByFilters(final String fromDate,final String endDate,final String prepaidType) {
		StringBuilder sb=new StringBuilder();
		sb.append(" select DISTINCT(ptt.id),p.member_id,ptt.prepaid_type ");
		sb.append(" from PREPAID_TOP_UP_TRANSACTION ptt left join PREPAID p on p.id =ptt.prepaid_id ");
		sb.append(" where ptt.is_active=1 and ptt.remain_value>0 ");
		sb.append(" and ptt.package_type='").append(prepaidType).append("'");
		sb.append(" and ptt.expiry_date >= ").append(fromDate).append("'");
		sb.append(" and ptt.expiry_date <= ").append(endDate).append("'");
		sb.append(" GROUP BY p.member_id ");
		sb.append(" ORDER BY ptt.expiry_date ");
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addScalar("id", StandardBasicTypes.LONG);
		query.addScalar("member_id", StandardBasicTypes.LONG);
		query.addScalar("prepaid_type", StandardBasicTypes.STRING);
		List<PrepaidTopUpTransaction> results = (List<PrepaidTopUpTransaction>) query.list();
		return results;
	}

	@Override
	public Double sumRemainValue(final String fromDate,final String prepaidType,final Long userId) {
		
		String sql = "select sum(ptt.remain_value) as sumRV from PREPAID_TOP_UP_TRANSACTION ptt "+
				" left join PREPAID p on p.id = ptt.prepaid_id where ptt.is_active=1";
				if(StringUtils.isNoneBlank(prepaidType)){
					sql+=" and ptt.prepaid_type='"+prepaidType+"'";
				}
				if(StringUtils.isNoneBlank(fromDate)){
					sql+=" and ptt.expiry_date >='"+fromDate+"'";
				}
				if(userId !=null && userId.longValue()>0){
					sql+=" and p.member_id ="+userId;
				}
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.addScalar("sumRV", StandardBasicTypes.DOUBLE);
		 
		Double remainValue = (Double)query.uniqueResult();
		if(remainValue !=null){
			return remainValue;
		}
		return 0d;
	}
	
	public  Set<Long> getUserIdsForPackageExpiryJourney(String prepaidType,String fromDate,String toDate,Integer remainUnits){
		String sql = "select DISTINCT(p.member_id) from PREPAID p "+
				" left join PREPAID_TOP_UP_TRANSACTION ptt on p.id = ptt.prepaid_id where ptt.is_active=1 and p.is_active=1";
		if(StringUtils.isNoneBlank(prepaidType)){
			sql+=" and ptt.prepaid_type='"+prepaidType+"'";
		}
		if(StringUtils.isNoneBlank(fromDate)){
			sql+=" and ptt.expiry_date >'"+fromDate+"'";
		}
		if(StringUtils.isNoneBlank(toDate)){
			sql+=" and ptt.expiry_date <='"+toDate+"'";
		}
		if(remainUnits !=null){
			sql+=" and ptt.remain_value = "+remainUnits;
		}
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		System.out.println("sql---"+sql);
		query.addScalar("member_id", StandardBasicTypes.LONG);
		List<Long> results = (List<Long>) query.list();
		return new HashSet<Long>(results);
	}
	public  Set<Long> getUserIdsForPackageEngagementJourney(final String fromDateTrans,final String toDateTrans,final String fromDateNoTrans,final String toDateNoTrans,final String type){
		
		Date now =new Date();
		String hasTransSql ="select DISTINCT(user_id),count(id) as counts from purchase_order where is_active=1 and company_id=1 ";
		if(StringUtils.isNoneBlank(fromDateTrans)){
			hasTransSql+=" and purchase_date >='"+fromDateTrans+"'";
		}
		if(StringUtils.isNoneBlank(toDateTrans)){
			hasTransSql+=" and purchase_date <'"+toDateTrans+"'";
		}
		hasTransSql+=" GROUP BY user_id having counts > 0";
		Session session = getSession();
		SQLQuery hasTransQuery = session.createSQLQuery(hasTransSql);
//		System.out.println("hasTransSql---"+hasTransSql);
		hasTransQuery.addScalar("user_id", StandardBasicTypes.LONG);
		List<Long> hasTransIds = (List<Long>) hasTransQuery.list();
		if(hasTransIds == null || hasTransIds.size()==0){
			return null;
		}
		//
		String noTransSql ="select DISTINCT(user_id),count(id) as counts from purchase_order where is_active=1 and company_id=1 ";
		if(StringUtils.isNoneBlank(fromDateNoTrans)){
			noTransSql+=" and purchase_date >='"+fromDateNoTrans+"'";
		}
		if(StringUtils.isNoneBlank(toDateNoTrans)){
			noTransSql+=" and purchase_date <'"+toDateNoTrans+"'";
		}
		noTransSql+=" GROUP BY user_id having counts >0";
		SQLQuery noTransQuery = session.createSQLQuery(noTransSql);
//		System.out.println("noTransSql---"+noTransSql);
		noTransQuery.addScalar("user_id", StandardBasicTypes.LONG);
		List<Long> noTransIds = (List<Long>) noTransQuery.list();
		if(noTransIds !=null && noTransIds.size()>0){
			for(Long id : noTransIds){
				if(hasTransIds.contains(id)){
					hasTransIds.remove(id);
				}
			}
		}
		if(hasTransIds == null || hasTransIds.size()==0){
			return null;
		}
		
		String packageSql = "select DISTINCT(p.member_id) from PREPAID p "+
				" left join PREPAID_TOP_UP_TRANSACTION ptt on p.id = ptt.prepaid_id where ptt.is_active=1 and p.is_active=1";
				packageSql+=" and (ptt.prepaid_type='CASH_PACKAGE' or ptt.prepaid_type='TREATMENT_PACKAGE')";
				try {
					packageSql+=" and ptt.expiry_date >='"+DateUtil.dateToString(now, "yyyy-MM-dd")+"'";
				} catch (ParseException e) {
					e.printStackTrace();
				}
		packageSql+=" and ptt.remain_value >0 ";
		if(type.equals("A")){
			packageSql+=" and p.member_id in (:hasTransIds)";
		}
		SQLQuery packageQuery = session.createSQLQuery(packageSql);
//		System.out.println("package sql---"+packageSql);
		packageQuery.addScalar("member_id", StandardBasicTypes.LONG);
		if(type.equals("A")){
			packageQuery.setParameterList("hasTransIds", hasTransIds);
		}
		List<Long> memberIds = (List<Long>) packageQuery.list();
		if(type.equals("B")){
			for(Long id : memberIds){
				if(hasTransIds.contains(id)){
					hasTransIds.remove(id);
				}
			}
			return new HashSet<Long>(hasTransIds);
		}else{
			return new HashSet<Long>(memberIds);
		}
	}
/**	select DISTINCT(p.member_id),count(ptt.id) from prepaid p 
	left join prepaid_top_up_transaction ptt on ptt.prepaid_id=p.id
	where p.is_active=1 and ptt.is_active=1
	and ptt.expiry_date >='2018-03-15' 
	and ptt.expiry_date <='2018-05-15'
	and ptt.prepaid_type='CASH_PACKAGE'
	and ptt.remain_value >0
	and p.member_id !=3
	GROUP BY p.member_id
	having count(ptt.id) >=1; **/

	@Override
	public Set<Long> getMemberIdsByExpiryPrepaid(final String fromDate,final String toDate,final String prepaidType,final Boolean hasRemaining) {
		
		String sql ="select DISTINCT(p.member_id) from prepaid p left join prepaid_top_up_transaction ptt on ptt.prepaid_id=p.id where p.is_active=1 ";
		if(fromDate !=null){
			sql+=" and ptt.expiry_date >='"+fromDate+"'";
		}
		if(toDate !=null){
			sql+=" and ptt.expiry_date <='"+toDate+"'";
		}
		if(hasRemaining !=null){
			if(hasRemaining){
				sql+=" and ptt.remain_value >0";
			}else{
				sql+=" and ptt.remain_value <=0";
			}
		}else{
			sql+=" and ptt.is_active=1";
		}
		sql+=" and p.member_id !=3 GROUP BY p.member_id having count(ptt.id) >=1";
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
//		System.out.println("getMemberIdsByExpiryPrepaid sql---"+sql);
		query.addScalar("member_id", StandardBasicTypes.LONG);
		List<Long> memberIds = (List<Long>) query.list();
		Set set =new HashSet(memberIds);
		return set;
	}

	@Override
	public List<Prepaid> getPrepaidByMemberId(Long member, Long companyId) {
		StringBuilder sb=new StringBuilder();
		sb.append(" select *");
		sb.append(" from PREPAID");
		sb.append(" where is_active=1 and member_id =").append(member);
		sb.append(" and company_id =").append(companyId);
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(Prepaid.class);
		List<Prepaid> results = (List<Prepaid>) query.list();
		return results;
	}
}
