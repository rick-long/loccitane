package org.spa.dao.marketingCampaign;

import java.util.List;

import org.spa.model.salesforce.UserMarketingCampaignTransaction;

public interface UserMarketingCampaignTransactionDao {

	public List<UserMarketingCampaignTransaction> getListByFilters(final Long userId,final String code,final Boolean isActive);
	
	public List<UserMarketingCampaignTransaction> getUserMarketingCampaignTransactions(Long userId, String code,String subCode);
	
	public List<UserMarketingCampaignTransaction> getTransactionsBySubCodeFuzzySearch(final String code,final String subCode);
}
