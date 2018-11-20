package org.spa.dao.commission;

import org.spa.model.commission.CommissionAttribute;


public interface CommissionAttributeDao {
	
	public CommissionAttribute getCommissionAttributeByRuleAndKeyRef(final String keyRef,final Long commissionRuleId,final Long companyId);
	
}
