package org.spa.service.payroll;

import org.spa.dao.base.BaseDao;
import org.spa.model.payroll.PayrollAttributeKey;

/**
 * @author Ivy 2016-8-25
 */
public interface PayrollAttributeKeyService extends BaseDao<PayrollAttributeKey>{

	public PayrollAttributeKey getPayrollAttributeKeyByRefAndTemplateId(String keyRef,Long commissionTemplateId);
}
