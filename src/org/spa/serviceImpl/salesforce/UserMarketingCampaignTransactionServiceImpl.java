package org.spa.serviceImpl.salesforce;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.marketingCampaign.UserMarketingCampaignTransactionDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.salesforce.UserMarketingCampaignTransaction;
import org.spa.service.salesforce.UserMarketingCampaignTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2017/1024.
 */
@Service
public class UserMarketingCampaignTransactionServiceImpl extends BaseDaoHibernate<UserMarketingCampaignTransaction> implements UserMarketingCampaignTransactionService{

	@Autowired
	private UserMarketingCampaignTransactionDao userMarketingCampaignTransactionDao;
	
	@Override
	public List<UserMarketingCampaignTransaction> getUserMarketingCampaignTransactions(Long userId, Long marketingCampaignId, Boolean isActive) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserMarketingCampaignTransaction.class);
		if(userId !=null){
			criteria.add(Restrictions.eq("user.id", userId));
		}
		if(marketingCampaignId !=null){
			criteria.add(Restrictions.eq("marketingCampaign.id", marketingCampaignId));
		}
		if(isActive !=null){
			criteria.add(Restrictions.eq("isActive", isActive));
		}else{
			criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		}
		criteria.addOrder(Order.desc("created"));
		List<UserMarketingCampaignTransaction> list =list(criteria);
		return list;
	}
	@Override
	public String getMarketingCampaignCodes(Long userId, Long marketingCampaignId, Boolean isActive) {
		String code="";
		List<UserMarketingCampaignTransaction> list = getUserMarketingCampaignTransactions(userId, marketingCampaignId, isActive);
			
		if(list !=null && list.size()>0){
			int i =1;
			for(UserMarketingCampaignTransaction t :list){
				if(t.getMarketingCampaign() ==null){
					continue;
				}
				if(StringUtils.isNotBlank(t.getMarketingCampaign().getCode())){
					
					if(i == list.size()){
						code += "#"+t.getMarketingCampaign().getCode()+ (StringUtils.isNotBlank(t.getMarketingCampaign().getSubCode()) ? t.getMarketingCampaign().getSubCode() : "");
					}else{
						code += "#"+t.getMarketingCampaign().getCode()+ (StringUtils.isNotBlank(t.getMarketingCampaign().getSubCode()) ? t.getMarketingCampaign().getSubCode() : "")+",";
					}
				}
				
				i++;
			}
		}
		return code;
	}
	
	@Override
	public List<UserMarketingCampaignTransaction> getListByFilters(Long userId, String code, Boolean isActive) {
		return userMarketingCampaignTransactionDao.getListByFilters(userId, code, isActive);
	}
	
	@Override
	public List<UserMarketingCampaignTransaction> getUserMarketingCampaignTransactions(Long userId, String code,
			String subCode) {
		return userMarketingCampaignTransactionDao.getUserMarketingCampaignTransactions(userId, code, subCode);
	}
	@Override
	public List<UserMarketingCampaignTransaction> getTransactionsBySubCodeFuzzySearch(String code,
			String subCode) {
		return userMarketingCampaignTransactionDao.getTransactionsBySubCodeFuzzySearch(code, subCode);
	}
}
