package org.spa.service.payment;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.payment.PaymentMethod;

/**
 * Created by Ivy on 2016/04/21.
 */
public interface PaymentMethodService extends BaseDao<PaymentMethod>{
	//
	public List<PaymentMethod> getActivePaymentMethods(Long companyId,Boolean isForPrepaid);
	
	public PaymentMethod getPaymentMethodByRef(String ref,Long companyId);
}
