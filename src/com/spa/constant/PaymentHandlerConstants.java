package com.spa.constant;

public class PaymentHandlerConstants {

//	public static final String PAYPAL_RETURN_HANDLER = "http://local.ssl2.senseoftouch.com.hk:8080/loccitane/front/payment/paypal/afterPaypalLogin";
//	public static final String CANCEL_VALUE = "http://local.ssl2.senseoftouch.com.hk:8080/loccitane/front/payment/paymentCancel.jsp";
	
	public static final String PAYPAL_RETURN_HANDLER = "http://ssl2.senseoftouch.com.hk/front/payment/paypal/afterPaypalLogin";
	public static final String CANCEL_VALUE = "http://ssl2.senseoftouch.com.hk/front/payment/paymentCancel.jsp";
	
	public static final String PAYPAL_API_PASSWORD = "SMGVULXJUR66TJ67";
	public static final String ACCOUT_NAME_VALUE = "neil_api1.senseoftouch.com.hk";
	public static final String PAYPAL_API_SIGNATURE = "A.p-tR-sBYeeC08LWRcblmewClzSAzL9IQ2k3KPpmrDkDD57UiAp6Rvb";
	
	public static final String PAYPAL_API_END_POINT = "https://api-3t.paypal.com/nvp";
	public static final String PAYPAL_REDIRECT_LOGIN_URL = "https://www.paypal.com/webscr?cmd=_express-checkout&locale.x=en_HK&token=";
	
	public static final String STATUS_KEY = "PAYMENTINFO_0_PAYMENTSTATUS";
	public static final String PAYMENT_FEE = "PAYMENTINFO_0_FEEAMT";
	public static final String CALLBACK_AMOUNT_KEY = "PAYMENTINFO_0_AMT";
	public static final String TRANSID_KEY = "PAYMENTINFO_0_TRANSACTIONID";
	public static final String STATUS_OK_VALUE = "Completed";
	public static final String ORDER_REF_KEY = "orderRef";
	public static final String ORDER_REF_2_KEY = "Ref";
	
	public static final String AFFILIATE_ID = "";
	public static final String AMOUNT_KEY = "amount";
	public static final String AUTHORIZATION_CODE_KEY = "AuthId";
	public static final String REMARK = "remark";
	public static final String TYPE_KEY = "testMode";
}
