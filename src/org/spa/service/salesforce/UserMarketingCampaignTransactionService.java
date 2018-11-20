package org.spa.service.salesforce;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.salesforce.UserMarketingCampaignTransaction;

/**
 * Created by Ivy on 2017/10/24.
 */
public interface UserMarketingCampaignTransactionService extends BaseDao<UserMarketingCampaignTransaction>{
	//
	public List<UserMarketingCampaignTransaction> getUserMarketingCampaignTransactions(Long userId, Long marketingCampaignId, Boolean isActive);
	
	public List<UserMarketingCampaignTransaction> getTransactionsBySubCodeFuzzySearch(String code,String subCode);
	
	public List<UserMarketingCampaignTransaction> getUserMarketingCampaignTransactions(Long userId, String code,String subCode);
	
	public String getMarketingCampaignCodes(Long userId,Long marketingCampaignId,Boolean isActive);
	
	public List<UserMarketingCampaignTransaction> getListByFilters(Long userId,String code,Boolean isActive);
}
