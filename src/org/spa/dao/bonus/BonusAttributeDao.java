package org.spa.dao.bonus;

import org.spa.model.bonus.BonusAttribute;


public interface BonusAttributeDao {
	
	public BonusAttribute getBonusAttributeByRuleAndKeyRef(final String keyRef,final Long bonusRuleId,final Long companyId);
	
}
