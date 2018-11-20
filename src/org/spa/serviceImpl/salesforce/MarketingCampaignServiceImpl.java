package org.spa.serviceImpl.salesforce;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.salesforce.MarketingCampaign;
import org.spa.service.salesforce.MarketingCampaignService;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2017/1024.
 */
@Service
public class MarketingCampaignServiceImpl extends BaseDaoHibernate<MarketingCampaign> implements MarketingCampaignService{

	@Override
	public MarketingCampaign getMarketingCampaignByCodeAndSubCode(String code, String subCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MarketingCampaign.class);
		if(StringUtils.isNotBlank(code)){
			criteria.add(Restrictions.eq("code", code));
		}
		if(StringUtils.isNotBlank(code)){
			criteria.add(Restrictions.eq("subCode", subCode));
		}
		criteria.addOrder(Order.desc("created"));
		List<MarketingCampaign> list =list(criteria);
		if(list !=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
