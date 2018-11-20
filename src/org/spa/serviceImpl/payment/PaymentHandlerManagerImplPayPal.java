package org.spa.serviceImpl.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.payment.Payment;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.service.order.PurchaseItemService;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.payment.PaymentHandlerManager;
import org.spa.service.payment.PaymentService;
import org.spa.service.points.LoyaltyPointsTransactionService;
import org.spa.service.prepaid.PrepaidService;
import org.spa.service.prepaid.PrepaidTopUpTransactionService;
import org.spa.utils.SpringUtil;
import org.spa.vo.loyalty.PointsAdjustVO;
import org.springframework.stereotype.Service;

import com.spa.constant.CallbackMappedAttributes;
import com.spa.constant.CommonConstant;
import com.spa.constant.KeyNotFoundException;
import com.spa.constant.PaymentHandlerConstants;
import com.spa.tools.land.MathUtils;

/**
 * Created by IntelliJ IDEA.
 * User: JB
 * Date: Jun 8, 2003
 * Time: 10:34:22 PM
 * To change this template use Options | File Templates.
 */
@Service
public class PaymentHandlerManagerImplPayPal implements PaymentHandlerManager {

    private Log log;
    private PurchaseOrderService purchaseOrderService =(PurchaseOrderService) SpringUtil.getBean("purchaseOrderServiceImpl");
    private PurchaseItemService purchaseItemService =(PurchaseItemService) SpringUtil.getBean("purchaseItemServiceImpl");
    private PaymentService paymentService = (PaymentService) SpringUtil.getBean("paymentServiceImpl");
    private PrepaidService prepaidService = (PrepaidService) SpringUtil.getBean("prepaidServiceImpl");
    private PrepaidTopUpTransactionService prepaidTopUpTransactionService = (PrepaidTopUpTransactionService) SpringUtil.getBean("prepaidTopUpTransactionServiceImpl");
    private LoyaltyPointsTransactionService loyaltyPointsTransactionService = (LoyaltyPointsTransactionService) SpringUtil.getBean("loyaltyPointsTransactionServiceImpl");
    
    public PaymentHandlerManagerImplPayPal() {
    	log = LogFactory.getLog("PAYMENT");
    }

    @Override
    public Map<String,Object> handlePayment(CallbackMappedAttributes cbMap, HttpSession session, boolean byPassAmountCheck) {
    	
    	Map<String,Object> returnMap = new HashMap<String,Object>();
        try {
            // perform business logic to handle payment callback details
        	System.out.println("PaymentHandlerManager handlePayment callback for ... "+ this.getClass().getName());
        	System.out.println("Order Reference = " + cbMap.getOrderRef());
        	System.out.println("Order ID = " + cbMap.getOrder2Ref());
        	System.out.println("PAYPAL PAYMENT Status = " + cbMap.getStatus());
        	System.out.println("PAYPAL Transaction ID = " + cbMap.getTransID());
        	System.out.println("Callback Amount FEE Key = " + cbMap.getCallbackAmountKey());
        	System.out.println("PAYPAL payment fee = " + cbMap.getPaymentFee());
        	System.out.println("Bypass amount checking = " + byPassAmountCheck);
            
            String orderID = cbMap.getOrder2Ref();
            
            PurchaseOrder purchaseOrder =null;
            if (orderID != null && orderID.length() > 0) {
                try {
                	purchaseOrder = purchaseOrderService.get(Long.parseLong(orderID));
                } catch (java.lang.NumberFormatException ex) {
                    log.error("The order id format is incorrect " + orderID);
                    returnMap.put("msg", "The order id format is incorrect " + orderID);
                    return returnMap;
                }
            }
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PurchaseItem.class);
    		detachedCriteria.add(Restrictions.eq("purchaseOrder.id", Long.parseLong(orderID)));
            List<PurchaseItem> purchaseItems =  purchaseItemService.list(detachedCriteria);
            PurchaseItem pi= purchaseItems.iterator().next();
            PrepaidTopUpTransaction ptt =prepaidTopUpTransactionService.get(pi.getBuyPrepaidTopUpTransaction().getId());
        	Payment payment = paymentService.getPaymentsByOrderIdAndDisplayOrderWhenBuyPrepaid(Long.parseLong(orderID),1);
        	
        	System.out.println("----purchaseOrder--payment --"+payment.getId());
            String STATUS_OK_VALUE = PaymentHandlerConstants.STATUS_OK_VALUE;
            payment.setReference(cbMap.getTransID());
            if (!STATUS_OK_VALUE.equals(cbMap.getStatus())) {
            	log.error("The order status is not  Completed --- " + cbMap.getStatus());
            	System.out.println("The order status is not  Completed --- " + cbMap.getStatus());
            	pi.setIsActive(false);
            	purchaseItemService.saveOrUpdate(pi);
            	
            	payment.setIsActive(false);
            	paymentService.saveOrUpdate(payment);
            	
            	ptt.setStatus("PAY_FAILED");
            	prepaidTopUpTransactionService.saveOrUpdate(ptt);
            	returnMap.put("msg", "The order status is not  Completed --- " + cbMap.getStatus());
                return returnMap;
            }
            
            pi.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
            pi.setIsActive(true);
            purchaseItemService.saveOrUpdate(pi);
            
            payment.setStatus(CommonConstant.PAYMENT_STATUS_PAID);
        	payment.setChargeAmount(StringUtils.isNotEmpty(cbMap.getPaymentFee()) ? Double.parseDouble(cbMap.getPaymentFee()) : 0);
        	payment.setIsActive(true);
            paymentService.saveOrUpdate(payment);
            
        	purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        	purchaseOrder.setIsActive(true);
            purchaseOrderService.saveOrUpdate(purchaseOrder);
            
           
            ptt.setStatus(CommonConstant.PREPAID_TOPUP_TRANSACTION_STATUS_NORMAL);
            ptt.setIsActive(true);
            prepaidTopUpTransactionService.saveOrUpdate(ptt);
            
            ptt.getPrepaid().setIsActive(true);
            prepaidService.saveOrUpdate( ptt.getPrepaid());
           
            //
            setLoyaltyPoints(CommonConstant.POINTS_EARN_CHANNEL_PREPAID, purchaseOrder, ptt.getTopUpValue(),CommonConstant.POINTS_ACTION_PLUS);
            returnMap.put("ptt", ptt);
            
            String amt = cbMap.getCallbackAmountKey();
            double tempAmt = 0.0;
            if (amt != null && amt.trim().length() > 0) {
            	tempAmt = Double.parseDouble(amt);
            }
       
            // order with amount less than discount will not reach payment gateway
            double dbAmt = purchaseOrder.getTotalAmount();
            if (dbAmt > 0) {
            	dbAmt = dbAmt - purchaseOrder.getTotalDiscount(); 
                dbAmt = MathUtils.round(dbAmt ,2);
            }
            if (tempAmt > 0) {
            	tempAmt = MathUtils.round(tempAmt ,2);                    	
            }
            
            if (!byPassAmountCheck && tempAmt != dbAmt) {
                log.warn("Problem order encountered by PaymentHandlerManager, order amount and paid amount not equal : " + dbAmt + " | " + tempAmt);
                returnMap.put("msg", "Problem order encountered by PaymentHandlerManager, order amount and paid amount not equal : " + dbAmt + " | " + tempAmt);
                return returnMap;
            } 
        }catch (KeyNotFoundException e1) {
			e1.printStackTrace();
		}
        returnMap.put("msg",PaymentHandlerConstants.STATUS_OK_VALUE);
        return returnMap;
    }
    
    private void setLoyaltyPoints(String earnChannel,PurchaseOrder purchaseOrder,Double earnPoints,String pointAction){
    	PointsAdjustVO spaPointsAdjustVO=new PointsAdjustVO();
		
		spaPointsAdjustVO.setEarnChannel(earnChannel);
		spaPointsAdjustVO.setEarnDate(purchaseOrder.getPurchaseDate());
		
		if(earnChannel.equals(CommonConstant.POINTS_EARN_CHANNEL_PREPAID)){
			
			spaPointsAdjustVO.setMonthLimit(CommonConstant.LOYALTY_POINTS_EXPIRED_MONTHS_BY_PREPAID);
			
		}else if(earnChannel.equals(CommonConstant.POINTS_EARN_CHANNEL_SALES)){
			
			spaPointsAdjustVO.setMonthLimit(CommonConstant.LOYALTY_POINTS_EXPIRED_MONTHS_BY_SALES);
		}
		
		spaPointsAdjustVO.setPurchaseOrder(purchaseOrder);
		spaPointsAdjustVO.setUser(purchaseOrder.getUser());
		
		spaPointsAdjustVO.setAdjustPoints(earnPoints);
		spaPointsAdjustVO.setAction(pointAction);
		
		loyaltyPointsTransactionService.saveLoyaltyPointsTransaction(spaPointsAdjustVO);
    }
}
