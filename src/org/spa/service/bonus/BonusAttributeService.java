package org.spa.service.bonus;

import org.spa.dao.base.BaseDao;
import org.spa.model.bonus.BonusAttribute;

/**
 * @author Ivy 2016-7-25
 */
public interface BonusAttributeService extends BaseDao<BonusAttribute>{
	public BonusAttribute getBonusAttributeByRuleAndKeyRef(String keyRef,Long bonusRuleId);
}
