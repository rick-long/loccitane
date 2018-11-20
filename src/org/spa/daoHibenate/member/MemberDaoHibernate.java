package org.spa.daoHibenate.member;

import com.spa.constant.CommonConstant;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.joda.time.DateTime;
import org.spa.dao.member.MemberDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.user.User;
import org.spa.vo.page.Page;
import org.spa.vo.user.MemberAdvanceVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivy on 2017/2/28.
 */
@Repository
public class MemberDaoHibernate extends BaseDaoHibernate<User> implements MemberDao {

    @Override
    public Page<User> getMemberList(MemberAdvanceVO advanceVO) {
        switch (advanceVO.getSearchType()) {
            case MemberAdvanceVO.SEARCH_TYPE_VISIT:
                return getMemberVisitList(advanceVO);
            case MemberAdvanceVO.SEARCH_TYPE_NOT_VISIT:
                return getMemberNotVisitList(advanceVO);
            case MemberAdvanceVO.SEARCH_TYPE_SPENT:
                return getMemberSpentList(advanceVO);
        }
        return new Page<>(new ArrayList<>(), 0L, advanceVO.getPageNumber(), advanceVO.getPageSize());
    }
    @Override
    public Page<User> getMemberSpentList(MemberAdvanceVO advanceVO) {
    	Long shopId = advanceVO.getShopId();
        String visitStartTime = new DateTime().minusMonths(advanceVO.getPastMonth()).withTimeAtStartOfDay().toString("yyyy-MM-dd");
        int pageSize = advanceVO.getPageSize();
        int firstResult = (advanceVO.getPageNumber() - 1) * pageSize;
        // count
        StringBuilder countHql = new StringBuilder();
        countHql.append("select count(u.id) from User u where enabled=1 and u.accountType=:accountType")
                .append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:purchaseDate)");
        if(shopId !=null && shopId.longValue()>0){
        	countHql.append(" and u.shop.id = ").append(shopId);
        }
        Query query = getSession().createQuery(countHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("purchaseDate", visitStartTime);
//        System.out.println("query.uniqueResult():" + query.uniqueResult());
        long totalRecords = (long) query.uniqueResult();
        // data
        StringBuilder dataHql = new StringBuilder();
        dataHql.append("select distinct u from User u where enabled=1 and u.accountType=:accountType")
        	.append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:purchaseDate)");
	        if(shopId !=null && shopId.longValue()>0){
	        	dataHql.append(" and u.shop.id = ").append(shopId);
	        }
        	dataHql.append(" order by u.id asc");
        query = getSession().createQuery(dataHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("purchaseDate", visitStartTime);
        query.setFirstResult(firstResult).setMaxResults(pageSize);
        return new Page<>(query.list(), totalRecords, advanceVO.getPageNumber(), pageSize);
    }
    @Override
    public List<User> getAllMemberList(MemberAdvanceVO advanceVO) {
        switch (advanceVO.getSearchType()) {
            case MemberAdvanceVO.SEARCH_TYPE_VISIT:
                return getAllMemberVisitList(advanceVO);
            case MemberAdvanceVO.SEARCH_TYPE_NOT_VISIT:
                return getAllMemberNotVisitList(advanceVO);
        }
        return new ArrayList<>();
    }

    /**
     * 获取visit的分页数据
     *
     * @param advanceVO
     * @return
     */
    @Override
    public Page<User> getMemberVisitList(MemberAdvanceVO advanceVO) {
    	Long shopId = advanceVO.getShopId();
        String visitStartTime = new DateTime().minusMonths(advanceVO.getPastMonth()).withTimeAtStartOfDay().toString("yyyy-MM-dd");
        int pageSize = advanceVO.getPageSize();
        int firstResult = (advanceVO.getPageNumber() - 1) * pageSize;
        // count
        StringBuilder countHql = new StringBuilder();
        countHql.append("select count(u.id) from User u where enabled=1 and u.accountType=:accountType")
                .append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:purchaseDate)");
        if(shopId !=null && shopId.longValue()>0){
        	countHql.append(" and u.shop.id = ").append(shopId);
        }
        Query query = getSession().createQuery(countHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("purchaseDate", visitStartTime);
//        System.out.println("query.uniqueResult():" + query.uniqueResult());
        long totalRecords = (long) query.uniqueResult();
        // data
        StringBuilder dataHql = new StringBuilder();
        dataHql.append("select distinct u from User u where enabled=1 and u.accountType=:accountType")
        	.append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:purchaseDate)");
	        if(shopId !=null && shopId.longValue()>0){
	        	dataHql.append(" and u.shop.id = ").append(shopId);
	        }
        	dataHql.append(" order by u.id asc");
        query = getSession().createQuery(dataHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("purchaseDate", visitStartTime);
        query.setFirstResult(firstResult).setMaxResults(pageSize);
        return new Page<>(query.list(), totalRecords, advanceVO.getPageNumber(), pageSize);
    }

    /**
     * 获取visit 全部数据
     *
     * @param advanceVO
     * @return
     */
    @Override
    public List<User> getAllMemberVisitList(MemberAdvanceVO advanceVO) {
        String visitStartTime = new DateTime().minusMonths(advanceVO.getPastMonth()).withTimeAtStartOfDay().toString("yyyy-MM-dd");
        Long shopId = advanceVO.getShopId();
        // data
        StringBuilder dataHql = new StringBuilder();
        dataHql.append("select distinct u from User u where enabled=1 and u.accountType=:accountType")
                .append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:purchaseDate)");
                if(shopId !=null && shopId.longValue()>0){
                	dataHql.append(" and u.shop.id =").append(shopId);	
            	}
                dataHql.append(" order by u.id asc");
        Query query = getSession().createQuery(dataHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("purchaseDate", visitStartTime);
        return query.list();
    }

    /**
     * 获取 not visit的分页数据
     *
     * @param advanceVO
     * @return
     */
    @Override
    public Page<User> getMemberNotVisitList(MemberAdvanceVO advanceVO) {
    	
    	Long shopId = advanceVO.getShopId();
    	
        String visitStartTime = new DateTime().minusMonths(advanceVO.getPastMonth()).withTimeAtStartOfDay().toString("yyyy-MM-dd");
        String notVisitStartTime = new DateTime(advanceVO.getNotVisitStartDate()).toString("yyyy-MM-dd");
        int pageSize = advanceVO.getPageSize();
        int firstResult = (advanceVO.getPageNumber() - 1) * pageSize;
        // count
        StringBuilder countHql = new StringBuilder();
        countHql.append("select count(u.id) from User u where enabled=1 and u.accountType=:accountType")
        	.append(" and u.id not in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:visitStartTime)")
        	.append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:notVisitStartTime)");
        	if(shopId !=null && shopId.longValue()>0){
        		countHql.append(" and u.shop.id =").append(shopId);	
        	}
        
        Query query = getSession().createQuery(countHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("visitStartTime", visitStartTime);
        query.setString("notVisitStartTime", notVisitStartTime);
        long totalRecords = (long) query.uniqueResult();
        // data
        StringBuilder dataHql = new StringBuilder();
        dataHql.append("select distinct u from User u where enabled=1 and u.accountType=:accountType")
                .append(" and u.id not in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:visitStartTime)")
                .append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:notVisitStartTime)");
		        if(shopId !=null && shopId.longValue()>0){
		        	dataHql.append(" and u.shop.id =").append(shopId);	
		    	}
		        dataHql.append(" order by u.id asc");
        query = getSession().createQuery(dataHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("visitStartTime", visitStartTime);
        query.setString("notVisitStartTime", notVisitStartTime);
        query.setFirstResult(firstResult).setMaxResults(pageSize);
        return new Page<>(query.list(), totalRecords, advanceVO.getPageNumber(), pageSize);
    }

    /**
     * 获取 not visit 全部数据
     *
     * @param advanceVO
     * @return
     */
    @Override
    public List<User> getAllMemberNotVisitList(MemberAdvanceVO advanceVO) {
        String visitStartTime = new DateTime().minusMonths(advanceVO.getPastMonth()).withTimeAtStartOfDay().toString("yyyy-MM-dd");
        String notVisitStartTime = new DateTime(advanceVO.getNotVisitStartDate()).toString("yyyy-MM-dd");
        Long shopId = advanceVO.getShopId();
        // data
        StringBuilder dataHql = new StringBuilder();
        dataHql.append("select distinct u from User u where enabled=1 and u.accountType=:accountType")
                .append(" and u.id not in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:visitStartTime)")
                .append(" and u.id in (select DISTINCT user.id from PurchaseOrder where isActive=1 and purchaseDate >=:notVisitStartTime)");
                if(shopId !=null && shopId.longValue()>0){
		        	dataHql.append(" and u.shop.id =").append(shopId);	
		    	}
        	dataHql.append(" order by u.id asc");
        Query query = getSession().createQuery(dataHql.toString());
        query.setString("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        query.setString("visitStartTime", visitStartTime);
        query.setString("notVisitStartTime", notVisitStartTime);
        return query.list();
    }

    @Override
	public List<User> getUsersByIds(final Long[] uIds) {
		String idsp="";
		int idx=1;
		for(Long id : uIds){
			idsp += id+",";
			if(idx == uIds.length){
				idsp += id;
			}else{
				idsp += id+",";
			}
			idx++;
		}
		String sql="select * from  USER  where id in ("+idsp+") order by id desc ";
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.addEntity(User.class);
    	List<User> results = (List<User>) query.list();  
		return results;
	}

	@Override
	public List<Object[]> getMemberDetailsExportToCSVForSF(final Integer limitNum,final Boolean isDemo) {
		String sql = "";
		if(isDemo){
			sql="select DISTINCT(u.id),u.username,u.full_name,s.name,"
					+"u.first_name,u.last_name,u.gender,u.email,u.date_of_birth"
						+" from USER u left join SHOP s on s.id =u.shop_id "
						+" where u.enabled=1 and u.activing=1"
						+" and u.account_type='MEMBER' and u.username like '%"+CommonConstant.SF_DEMO_DATA_PREFIX+"%' and (u.last_modifier is null or u.last_updated > u.last_modifier) order by u.id limit "+limitNum;
		}else{
			sql="select DISTINCT(u.id),u.username,u.full_name,s.name,"
				+"u.first_name,u.last_name,u.gender,u.email,u.date_of_birth"
					+" from USER u left join SHOP s on s.id =u.shop_id "
					+" where u.enabled=1 and u.activing=1"
					+" and u.account_type='MEMBER' and u.email like '%@%' and (u.last_modifier is null or u.last_updated > u.last_modifier) order by u.id limit "+limitNum;
		}
		Session session = getSession();
    	SQLQuery sqlQuery = session.createSQLQuery(sql);
    	sqlQuery.addScalar("id", LongType.INSTANCE);
    	sqlQuery.addScalar("username", StringType.INSTANCE);
    	sqlQuery.addScalar("full_name", StringType.INSTANCE);
    	sqlQuery.addScalar("name", StringType.INSTANCE);
    	sqlQuery.addScalar("first_name", StringType.INSTANCE);
    	sqlQuery.addScalar("last_name", StringType.INSTANCE);
    	sqlQuery.addScalar("gender", StringType.INSTANCE);
    	sqlQuery.addScalar("email", StringType.INSTANCE);
    	sqlQuery.addScalar("date_of_birth", DateType.INSTANCE);
//    	sqlQuery.addScalar("country", StringType.INSTANCE);
//    	sqlQuery.addScalar("city", StringType.INSTANCE);
//    	sqlQuery.addScalar("district", StringType.INSTANCE);
//    	sqlQuery.addScalar("post_code", StringType.INSTANCE);
//    	sqlQuery.addScalar("address_extention", StringType.INSTANCE);
    	
    	
		List<Object[]> results =(List<Object[]>)sqlQuery.list();
    	return results;
	}

	@Override
	public void updateUserSetLastModifier(final String date,final Boolean demoRecords){
//		String sql="update user set last_modifier =null where account_type='MEMBER' and enabled =1";
		StringBuffer sb =new StringBuffer();
		sb.append("update user set last_modifier =").append("'").append(date).append("'");
		if(demoRecords !=null && demoRecords){
			sb.append(" where username like '%").append(CommonConstant.SF_DEMO_DATA_PREFIX).append("%'");
		}
		Session session = getSession();
		SQLQuery query=session.createSQLQuery(sb.toString());
		query.executeUpdate();
	}
	@Override
	public List<User> getUsersByBirthdayMonth(final Integer month) {
		StringBuffer hql = new StringBuffer();
		hql.append("select * from USER ");
		hql.append(" where account_type='MEMBER'");
		hql.append(" and enabled=1");
		hql.append(" and  DATE_FORMAT(date_of_birth, '%m') =");
		hql.append(month);
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
    	query.addEntity(User.class);
    	List<User> results = (List<User>) query.list();  
		return results;
	}
	@Override
	public List<Long> getMemberIdsNotIn(List<Long> ids) {
		StringBuffer hql = new StringBuffer();
		hql.append("select DISTINCT(id) from USER ");
		hql.append(" where account_type='MEMBER'");
		hql.append(" and enabled=1");
		if(ids !=null && ids.size()>0){
			hql.append(" and id not in (:ids)");
		}
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
		if(ids !=null && ids.size()>0){
			query.setParameterList("ids", ids);
    	}
		query.addScalar("id", LongType.INSTANCE);
    	List<Long> results = (List<Long>) query.list();  
		return results;
	}
}
