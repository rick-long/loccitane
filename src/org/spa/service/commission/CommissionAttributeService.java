package org.spa.service.commission;

import org.spa.dao.base.BaseDao;
import org.spa.model.commission.CommissionAttribute;

/**
 * @author Ivy 2016-7-25
 */
public interface CommissionAttributeService extends BaseDao<CommissionAttribute>{
	public CommissionAttribute getCommissionAttributeByRuleAndKeyRef(String keyRef,Long commissionRuleId);
}
