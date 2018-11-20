package org.spa.serviceImpl.payment;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.order.PurchaseOrderDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.payment.Payment;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.payment.PaymentService;
import org.spa.service.prepaid.PrepaidTopUpTransactionService;
import org.spa.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/04/21.
 */
@Service
public class PaymentServiceImpl extends BaseDaoHibernate<Payment> implements PaymentService{

	@Autowired
    public PrepaidTopUpTransactionService prepaidTopUpTransactionService;
	
	@Autowired
	public PurchaseOrderService purchaseOrderService;
	
	@Override
	public List<Payment> getUsedPrepaidTopUpTransaction(Long prepaidTopUpTransactionId) {
		PrepaidTopUpTransaction ptut=prepaidTopUpTransactionService.get(prepaidTopUpTransactionId);
		return getUsedPrepaidTopUpTransactionByDate(prepaidTopUpTransactionId, ptut.getTopUpDate());
	}
	
	@Override
	public List<Payment> getUsedPrepaidTopUpTransactionByDate(Long prepaidTopUpTransactionId,Date date) {
		DetachedCriteria dc = DetachedCriteria.forClass(Payment.class);
		dc.add(Restrictions.eq("isActive",true));
		dc.add(Restrictions.eq("redeemPrepaidTopUpTransaction.id",prepaidTopUpTransactionId));
		dc.add(Restrictions.ge("created",date));
		return list(dc);
	}

	@Override
	public Payment getPaymentsByOrderIdAndDisplayOrderWhenBuyPrepaid(Long purchaseOrderId, int displayOrder) {
		DetachedCriteria dc = DetachedCriteria.forClass(Payment.class);
		dc.add(Restrictions.eq("isActive",true));
		dc.add(Restrictions.eq("displayOrder",displayOrder));
		dc.add(Restrictions.eq("purchaseOrder.id",purchaseOrderId));
		List<Payment> paymentList=list(dc);
		
		Payment payment=null;
		if(paymentList !=null && paymentList.size()>0){
			payment=paymentList.get(0);
		}
		return payment;
	}
	
	@Override
	public boolean sendPaymentConfirmEmail(Long companyId, String orderRef){
		
//		PurchaseOrder po = purchaseOrderService.get("reference", orderRef);
//        Set purchaseItems = po.getPurchaseItems();
//        
//        //SendMultipartMail mpMailer = MailerFactory.getMultipartMailer();
//        SendMail mail = MailerFactory.getSimpleMailer();
//
//
//        //ArrayList purchaseArray = (ArrayList) orderDO.getPurchaseList();
//        String subject = companyInfo.getName() + " - Payment Confirmation";
//        
//        VelocityContext context = new VelocityContext();
//        // marco formatter
//        context.put("formatter", new VelocityFormatter());
//        //context.put("store", propertyFinder.getValueByName("STORE_NAME"));
//
//        context.put("store", companyInfo.getName());
//
//        if (glo != null) {
//            context.put("shipmentType", glo.getName());
//        } else {
//            context.put("shipmentType", property.getMessage(shipmentDetail.getShipmentMethod()));
//        }
//        
//        // -- order
//        context.put("orderDate", po.getPurchaseDate());
//        context.put("orderRef", po.getReference());
//
//        // - product info contained in purchaseItem.productOption.product
//        //context.put("productList", productArray);
//
//        context.put("purchaseList", purchaseItems);
//        context.put("orderAmount", po.getTotalAmount());
//        context.put("orderSubTotal", new Double(po.getTotalAmount()));
//        //context.put("tax", payment.getTaxAmount());
//        context.put("tax", po.getTaxAmount());
//        context.put("discount", po.getDiscountValue());
//        context.put("shipCharge", shipmentDetail.getChargeFromCustomer());
//        context.put("remarks", po.getRemarks());
//
//        /***
//         double total = payment.getAmount().doubleValue() 
//         + shipmentDetail.getChargeFromCustomer().doubleValue() 
//         + ((payment.getTaxAmount() != null) ? payment.getTaxAmount().doubleValue() : 0) 
//         - ((po.getDiscountValue() != null) ? po.getDiscountValue().doubleValue() : 0);
//         ****/
//        double total = payment.getAmount().doubleValue();
//
//        /****
//         log.debug("subtotal = "+ subtotal);
//         log.debug("amount = "+ payment.getAmount().doubleValue());
//         log.debug("shipment = "+ shipmentDetail.getChargeFromCustomer().doubleValue());
//         log.debug("tax = "+ ((payment.getTaxAmount() != null) ? payment.getTaxAmount().toString(): "0"));
//         log.debug("discount = "+ ((po.getDiscountValue() != null) ? po.getDiscountValue().toString(): "0"));	
//         log.debug("total = "+ total);
//         ***/
//
//        context.put("totalAmount", new Double(total));
//        context.put("purchaseItemList", purchaseItemList);
//        context.put("productItemList", productItemList);
//        FormAttributeValueHelper formAttributeValueHelper = new FormAttributeValueHelper();
//        formAttributeValueHelper.setProductOptionKeyByRef(productOptionKeyByRef);
//        context.put("formAttributeValueHelper", formAttributeValueHelper);
//        context.put("productOptionKeyByRef", productOptionKeyByRef);
//        context.put("stringUtils", new StringUtils());
//
//        log.debug("Send payment confirmation details mail - Finished building context");
//
//        String content = null;
//        try {
//            String templateName = propertyFinder.getValueByName("PAYMENT_TEMPLATE");
//            String templatePath = WebResourceLocatorHelper.getPhysicalPath("/WEB-INF/template/" + templateName);
//            content = template.mergeTemplate(templatePath, context);
//            log.debug("Send payment confirmation details mail - merged template to produce mail content");
//
//            if (log.isDebugEnabled()) {
//                log.debug("Mail content - " + content);
//                System.out.println("Mail content - " + content);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
//            throw new InternalException();
//        } catch (ChainedException e) {
//            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
//            throw new InternalException();
//        }
//        if (content != null && content.length() > 0) {
//
//            // debug purpose
//            System.out.println(PurchaseManagerImpl.class.getName() + " - sendPaymentConfirmEmail() - content = ");
//            System.out.println(content);
//
//            try {
//                String fromAddr = propertyFinder.getValueByName("ADMIN_EMAIL");
//                String fromName = propertyFinder.getValueByName("ADMIN_NAME");
//                InternetAddress toAddr = new InternetAddress(billing.getUser().getEmail());
//                InternetAddress[] toAddrs = {toAddr};
//                InternetAddress ccAddr = new InternetAddress();
//                ccAddr.setPersonal(fromName);
//                ccAddr.setAddress(fromAddr);
//                InternetAddress[] ccAddrs = {ccAddr};
//
//                //mail.sendMail(fromAddr, fromName, toAddrs, null, null, subject, content);
//                mail.sendMail(fromAddr, fromName, toAddrs, ccAddrs, null /*bccAddrs*/, subject, content);
//
//                log.info("Sent payment confirmation mail to " + billing.getUser().getEmail());
//            } catch (org.vistalab.lego.net.mail.SendMailException e) {
//                log.warn("SendMailException encountered : " + e.getMessage());
//                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
//                throw new InternalException();
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (AddressException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }

//        return rc;
		return true;
    }
}
