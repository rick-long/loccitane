package org.spa.service.commission;

import org.spa.dao.base.BaseDao;
import org.spa.model.commission.CommissionAttributeKey;

/**
 * @author Ivy 2016-7-25
 */
public interface CommissionAttributeKeyService extends BaseDao<CommissionAttributeKey>{

	public CommissionAttributeKey getCommissionAttributeKeyByRefAndTemplateId(String keyRef,Long commissionTemplateId);
}
