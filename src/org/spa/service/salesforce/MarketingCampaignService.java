package org.spa.service.salesforce;

import org.spa.dao.base.BaseDao;
import org.spa.model.salesforce.MarketingCampaign;

/**
 * Created by Ivy on 2017/10/24.
 */
public interface MarketingCampaignService extends BaseDao<MarketingCampaign>{
	//
	public MarketingCampaign getMarketingCampaignByCodeAndSubCode(String code,String subCode);
}
