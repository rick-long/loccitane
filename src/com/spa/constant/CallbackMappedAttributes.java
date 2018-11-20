package com.spa.constant;


/**
 * Created by IntelliJ IDEA.
 * User: JB
 * Date: Jun 8, 2003
 * Time: 10:09:33 PM
 * To change this template use Options | File Templates.
 */
public class CallbackMappedAttributes extends StringMappedAttributes {

    public String getAffiliateID() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.AFFILIATE_ID);
    }

    public String getAmountKey() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.AMOUNT_KEY);
    }

    public String getCallbackAmountKey() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.CALLBACK_AMOUNT_KEY);
    }

    public String getAuthorizationCodeKey() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.AUTHORIZATION_CODE_KEY);
    }

    public String getOrderRef() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.ORDER_REF_KEY);
    }

    public String getOrder2Ref() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.ORDER_REF_2_KEY);
    }

    public String getRemark() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.REMARK);
    }

    public String getStatus() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.STATUS_KEY);
    }

    public String getTransID() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.TRANSID_KEY);
    }

    public String getTypeKey() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.TYPE_KEY);
    }
    
    public String getPaymentFee() throws KeyNotFoundException {
        return get(PaymentHandlerConstants.PAYMENT_FEE);
    }

}
