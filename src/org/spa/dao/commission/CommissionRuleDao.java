package org.spa.dao.commission;

import java.util.List;

import org.spa.model.commission.CommissionRule;


public interface CommissionRuleDao {
	
	public List<CommissionRule> getCommissionRuleListByFilters(final String userGroupType,final String userGroupModule,final Long staffId,final Long companyId);
	
}
