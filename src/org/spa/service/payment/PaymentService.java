package org.spa.service.payment;

import java.util.Date;
import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.payment.Payment;

/**
 * Created by Ivy on 2016/04/21.
 */
public interface PaymentService extends BaseDao<Payment>{
	//
	public List<Payment> getUsedPrepaidTopUpTransaction(Long prepaidTopUpTransactionId);
	
	public List<Payment> getUsedPrepaidTopUpTransactionByDate(Long prepaidTopUpTransactionId,Date date);
	
	public Payment getPaymentsByOrderIdAndDisplayOrderWhenBuyPrepaid(Long purchaseOrderId,int displayOrder);
	
	public boolean sendPaymentConfirmEmail(Long companyId, String orderRef);
}
