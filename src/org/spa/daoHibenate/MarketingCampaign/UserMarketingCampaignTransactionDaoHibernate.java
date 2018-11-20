package org.spa.daoHibenate.MarketingCampaign;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.marketingCampaign.UserMarketingCampaignTransactionDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.salesforce.UserMarketingCampaignTransaction;
import org.springframework.stereotype.Repository;

@Repository
public class UserMarketingCampaignTransactionDaoHibernate extends BaseDaoHibernate<UserMarketingCampaignTransaction>  implements UserMarketingCampaignTransactionDao {

	@Override
	public List<UserMarketingCampaignTransaction> getListByFilters(final Long userId,final String code,final Boolean isActive) {
		StringBuffer hql = new StringBuffer();
		
		hql.append("select umct.* from USER_MARKETING_CAMPAIGN_TRANSACTION umct");
		if(StringUtils.isNotBlank(code)){
			hql.append(" left join MARKETING_CAMPAIGN mc on mc.id = umct.marketing_campaign_id");
		}
		hql.append(" where 1=1");
		if(userId !=null){
			hql.append(" and umct.user_id=").append(userId);
		}
		if(isActive !=null){
			hql.append(" and umct.is_active = ").append(isActive);
		}else{
			hql.append(" and umct.is_active = 1");
		}
		if(StringUtils.isNotBlank(code)){
			hql.append(" and mc.code=").append(code);
		}
		hql.append(" order by umct.created desc ");
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
    	query.addEntity(UserMarketingCampaignTransaction.class);
    	List<UserMarketingCampaignTransaction> results = (List<UserMarketingCampaignTransaction>) query.list();  
		return results;
	}

	@Override
	public List<UserMarketingCampaignTransaction> getUserMarketingCampaignTransactions(Long userId, String code,
			String subCode) {
		StringBuffer hql = new StringBuffer();
		
		hql.append("select umct.* from USER_MARKETING_CAMPAIGN_TRANSACTION umct");
		if(StringUtils.isNotBlank(code)){
			hql.append(" left join MARKETING_CAMPAIGN mc on mc.id = umct.marketing_campaign_id");
		}
		hql.append(" where umct.is_active = 1");
		if(userId !=null){
			hql.append(" and umct.user_id=").append(userId);
		}
		if(StringUtils.isNotBlank(code)){
			hql.append(" and mc.code = '").append(code).append("'");
		}
		if(StringUtils.isNotBlank(subCode)){
			hql.append(" and (mc.sub_code = '").append(subCode).append("' or mc.sub_code = '").append("-").append(subCode).append("')");
		}
		hql.append(" order by umct.created desc ");
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
//		System.out.println("hql.toString()----"+hql.toString());
    	query.addEntity(UserMarketingCampaignTransaction.class);
    	List<UserMarketingCampaignTransaction> results = (List<UserMarketingCampaignTransaction>) query.list();  
		return results;
	}

	@Override
	public List<UserMarketingCampaignTransaction> getTransactionsBySubCodeFuzzySearch(final String code,
			final String subCode) {
		StringBuffer hql = new StringBuffer();
		
		hql.append("select umct.* from USER_MARKETING_CAMPAIGN_TRANSACTION umct");
		if(StringUtils.isNotBlank(code)){
			hql.append(" left join MARKETING_CAMPAIGN mc on mc.id = umct.marketing_campaign_id");
		}
		hql.append(" where umct.is_active = 1");
		
		if(StringUtils.isNotBlank(code)){
			hql.append(" and mc.code = '").append(code).append("'");
		}
		if(StringUtils.isNotBlank(subCode)){
			hql.append(" and mc.sub_code like '%").append(subCode).append("%'");
		}
		hql.append(" order by umct.created desc ");
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
		System.out.println("hql.toString()----"+hql.toString());
    	query.addEntity(UserMarketingCampaignTransaction.class);
    	List<UserMarketingCampaignTransaction> results = (List<UserMarketingCampaignTransaction>) query.list();  
		return results;
	}

}
