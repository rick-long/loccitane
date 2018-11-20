package com.spa.controller.front;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpException;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.service.payment.PaymentHandlerManager;
import org.spa.utils.DateUtil;
import org.spa.utils.HtmlUtil;
import org.spa.utils.SpringUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.front.book.FrontVoucherVO;
import org.spa.vo.prepaid.PrepaidAddVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spa.constant.CallbackMappedAttributes;
import com.spa.constant.CommonConstant;
import com.spa.constant.KeyNotFoundException;
import com.spa.constant.PaymentHandlerConstants;
import com.spa.controller.BaseController;

@Controller
@RequestMapping("/front/payment")
public class FrontPaymentController extends BaseController{
	
	private static final Long PAYMENT_METHOD_ONLINE=6L;
	
	@RequestMapping("paypal/askToken")
    public String askToken(HttpServletRequest request,@ModelAttribute("frontVoucherVO") FrontVoucherVO frontVoucherVO,final RedirectAttributes redirectAttrs) {
		
	    Double amount =frontVoucherVO.getPrepaidValue();
	    String descriptions="";
        String prepaidType = frontVoucherVO.getPrepaidType();
        if (CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER.equals(prepaidType)) {     
             Long productOptionId = frontVoucherVO.getProductOptionId();
             ProductOption productOption = productOptionService.get(productOptionId);
             descriptions = "Treatment Voucher for " + productOption.getLabel6();
        } else if (CommonConstant.PREPAID_TYPE_CASH_VOUCHER.equals(prepaidType)) {
             String currencySymbol = "$";
             descriptions = currencySymbol + amount + " Cash Voucher";
        }
        String returnURL = PaymentHandlerConstants.PAYPAL_RETURN_HANDLER;
     	String cancelURL = PaymentHandlerConstants.CANCEL_VALUE;
     	
     	// this is real account
     	String gv_APIPassword = PaymentHandlerConstants.PAYPAL_API_PASSWORD;
      	String gv_APIUserName = PaymentHandlerConstants.ACCOUT_NAME_VALUE;
      	String gv_APISignature = PaymentHandlerConstants.PAYPAL_API_SIGNATURE;
      	String gv_APIEndpoint = PaymentHandlerConstants.PAYPAL_API_END_POINT;
      	
      	String doMethod = "SetExpressCheckout";        	
      	
        String nvpstr = "&PAYMENTREQUEST_0_AMT="+amount+"&PAYMENTACTION=SALES&ReturnUrl="
                 + returnURL+ "&CANCELURL=" + cancelURL + "&PAYMENTREQUEST_0_CURRENCYCODE=HKD";
        nvpstr += "&L_PAYMENTREQUEST_0_NAME0="+descriptions+"&L_PAYMENTREQUEST_0_QTY0=1&L_PAYMENTREQUEST_0_AMT0="+amount;//

        String encodedData = "METHOD="+doMethod+"&VERSION=115.0&PWD=" + gv_APIPassword
                 + "&USER=" + gv_APIUserName + "&SIGNATURE=" + gv_APISignature
                 + nvpstr;

        String url = gv_APIEndpoint+"?"+encodedData;

//        System.out.println("paypal/askToken url  = "+url);
     	Map paramterMap =HtmlUtil.getParameterMap(encodedData.toString());
//     	System.out.println("paypal/askToken---paramterMap::"+paramterMap);
     	
     	String responseString="";
		try {
			responseString = getPostConnection(gv_APIEndpoint,paramterMap );
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
     	
     	HashMap nvp = setResponseStringToMap(responseString);    		
 		
 		String ack = (String)nvp.get("ACK");
     	String nvpToken = (String)nvp.get("TOKEN");	
     	System.out.println("paypal/askToken---ack::"+ack);
     	
     	if ("Success".equalsIgnoreCase(ack) || "SuccessWithWarning".equals(ack)) {
   	
         	if (frontVoucherVO!=null) {
         		request.getSession().setAttribute("token", nvpToken);
         		request.getSession().setAttribute(nvpToken, frontVoucherVO);
         	}
     		
         	String PAYPAL_URL = PaymentHandlerConstants.PAYPAL_REDIRECT_LOGIN_URL;    		
     		String payPalURL = PAYPAL_URL + nvpToken;
     		request.getSession().setAttribute("paypmentSendConfirm", Boolean.FALSE);
     		return "redirect:"+payPalURL;
     	} else {
     		return "front/payment/paymentFail";
     	}
    }
	
	@RequestMapping("paypal/afterPaypalLogin")
    public String afterPaypalLogin(Model model,HttpServletRequest request,final RedirectAttributes redirectAttrs) {
		
		Map returnMap = request.getParameterMap(); 
		String token = ((String[])returnMap.get("token"))[0];
    	String payerId = ((String[])returnMap.get("PayerID"))[0];
    	System.out.println("FrontPaymentController afterPaypalLogin......token..."+token+"--payerId---"+payerId);
    	
    	FrontVoucherVO frontVoucherVO =(FrontVoucherVO) request.getSession().getAttribute(token);
		
		PrepaidAddVO prepaidAddVO =new PrepaidAddVO();
		prepaidAddVO.setIsOnline(true);
		prepaidAddVO.setPrepaidType(frontVoucherVO.getPrepaidType());
		prepaidAddVO.setPrepaidValue(frontVoucherVO.getPrepaidValue());
		prepaidAddVO.setShopId(19L);
		prepaidAddVO.setMember(WebThreadLocal.getUser());
		prepaidAddVO.setPurchaseDate(DateUtil.getFirstMinuts(new Date()));
		prepaidAddVO.setExpiryDate(DateUtil.getFirstMinuts(DateUtil.stringToDate(frontVoucherVO.getExpiryDate(), "yyyy-MM-dd")));
		Double initValue=1d;
		Long poId = frontVoucherVO.getProductOptionId();
		if(poId !=null){
			prepaidAddVO.setProductOptionId(poId);
		}else{
			initValue=frontVoucherVO.getPrepaidValue().doubleValue();
		}
		prepaidAddVO.setInitValue(initValue);
		prepaidAddVO.setPrepaidName(getPrepaidDescription(poId,initValue));
		
		Long pickUpLocation =frontVoucherVO.getPickUpLocation();
		if(pickUpLocation !=null){
			Shop shop =shopService.get(pickUpLocation);
			prepaidAddVO.setPickUpLocation(shop.getName());
			frontVoucherVO.setShop(shop);
		}
		String pickUpType = frontVoucherVO.getPickUpType();
//		System.out.println("FrontPrepaidController---afterPaypalLogin ---pickUpLocation="+pickUpLocation+"---PickUpType="+frontVoucherVO.getPickUpType()
//		+"---getAdditionalEmail ---"+frontVoucherVO.getAdditionalEmail()+"---getAdditionalMessage---"+frontVoucherVO.getAdditionalMessage()+"---getAdditionalMessage=----"+frontVoucherVO.getAdditionalMessage());
		prepaidAddVO.setPickUpType(pickUpType);
		 if(pickUpType.equals("friend")){
			 prepaidAddVO.setAdditionalEmail(frontVoucherVO.getAdditionalEmail());
			 prepaidAddVO.setAdditionalMessage(frontVoucherVO.getAdditionalMessage());
			 prepaidAddVO.setAdditionalName(frontVoucherVO.getAdditionalName());
        }
		
		List<KeyAndValueVO> paymentMethods = new ArrayList<KeyAndValueVO>();
		KeyAndValueVO kv=new KeyAndValueVO();
		kv.setKey(String.valueOf(1));
		kv.setId(PAYMENT_METHOD_ONLINE);
		kv.setValue(String.valueOf(frontVoucherVO.getPrepaidValue()));
		paymentMethods.add(kv);
		prepaidAddVO.setPaymentMethods(paymentMethods);
		//promotion code
		
		//
		prepaidAddVO.setPaymentStatus(CommonConstant.PAYMENT_STATUS_UNPAID);
		prepaidAddVO.setOrderStatus(CommonConstant.ORDER_STATUS_PENDING);
		prepaidAddVO.setStatus(CommonConstant.PREPAID_TOPUP_TRANSACTION_STATUS_PAY_PENDING);
		
		Prepaid prepaidNew = prepaidService.returnPrepaid(prepaidAddVO);
		prepaidNew.setIsActive(false);
		prepaidService.saveOrUpdate(prepaidNew);
		
		PurchaseItem pi =purchaseItemService.getPIByPrepaidId(prepaidNew.getId());
		PurchaseOrder po =pi.getPurchaseOrder();
		po.setIsActive(false);
		purchaseOrderService.saveOrUpdate(po);
		
		if(request.getSession().getAttribute("prepaidValue") !=null) {
			request.getSession().removeAttribute("prepaidValue");
        }
		return confirmPayment(model, request, po, token, payerId);
		
	}
	
    public String confirmPayment(Model model,HttpServletRequest request,PurchaseOrder order,String sessionToken,String payerId) {	
		
		System.out.println("FrontPaymentController confirmPayment.........");
		
		HttpSession session = request.getSession();
		Double amount = order.getTotalAmount();
		System.out.println(" total amount "+ amount);
		
			
		
//		System.out.println(" sessionToken "+ sessionToken);
//		System.out.println(" payerId "+ payerId);
		
		
		// this is real account
		String gv_APIPassword = PaymentHandlerConstants.PAYPAL_API_PASSWORD;
	 	String gv_APIUserName = PaymentHandlerConstants.ACCOUT_NAME_VALUE;
	 	String gv_APISignature = PaymentHandlerConstants.PAYPAL_API_SIGNATURE;
	 	String gv_APIEndpoint = PaymentHandlerConstants.PAYPAL_API_END_POINT;   	
	 	
	 	String doMethod = "DoExpressCheckoutPayment";        	
	    
	    String nvpstr = "&PAYMENTREQUEST_0_AMT="+amount+"&PAYMENTACTION=SALE&PAYMENTREQUEST_0_CURRENCYCODE=HKD"
	            + "&TOKEN="+sessionToken+"&PAYERID="+payerId;
	
	
	    String encodedData = "METHOD="+doMethod+"&VERSION=115.0&PWD=" + gv_APIPassword
	            + "&USER=" + gv_APIUserName + "&SIGNATURE=" + gv_APISignature
	            + nvpstr;
	
	    String url = gv_APIEndpoint+"?"+encodedData;
		 
	    	String responseString="";
			try {
				responseString = getConnection(url);
			} catch (HttpException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	
	    	HashMap nvp = setResponseStringToMap(responseString);
	    	
	        String ack = (String)nvp.get("ACK");
	    	String nvpToken = (String)nvp.get("TOKEN");
//	    	System.out.println(" ack: "+ ack+"---nvpToken---"+nvpToken);
	    	if (("Success".equalsIgnoreCase(ack) || "SuccessWithWarning".equals(ack))) {
	    		//check payment result integrity
	    		if (sessionToken.equals(nvpToken)){
	    			
	    			String STATUS_KEY = PaymentHandlerConstants.STATUS_KEY; //payment status
	    			String PAYMENT_FEE =PaymentHandlerConstants.PAYMENT_FEE; //payment fee
	    			String CALLBACK_AMOUNT_KEY = PaymentHandlerConstants.CALLBACK_AMOUNT_KEY; //amount
	    			String TRANSID_KEY = PaymentHandlerConstants.TRANSID_KEY; // paypal tran id
	    			String STATUS_OK_VALUE = PaymentHandlerConstants.STATUS_OK_VALUE;
	
//	                System.out.println("STATUS_KEY:"+STATUS_KEY+","+nvp.get(STATUS_KEY));
//	                System.out.println("PAYMENT_FEE:"+PAYMENT_FEE+","+nvp.get(PAYMENT_FEE));
//	                System.out.println("CALLBACK_AMOUNT_KEY:"+CALLBACK_AMOUNT_KEY+","+nvp.get(CALLBACK_AMOUNT_KEY));
//	                System.out.println("STATUS_OK_VALUE:"+STATUS_OK_VALUE+","+nvp.get(STATUS_OK_VALUE));
//	                System.out.println("TRANSID_KEY:"+TRANSID_KEY+","+nvp.get(TRANSID_KEY));
	    	        
	    	        CallbackMappedAttributes cbMap = new CallbackMappedAttributes();
	    			
	    	        cbMap.put(PaymentHandlerConstants.ORDER_REF_KEY, order.getReference()); // purchase order ref
	    	        cbMap.put(PaymentHandlerConstants.ORDER_REF_2_KEY, order.getId().toString()); // purchase order ref    	        
	                cbMap.put(PaymentHandlerConstants.STATUS_KEY, (String)nvp.get(STATUS_KEY)); //payment status
	    	        cbMap.put(PaymentHandlerConstants.PAYMENT_FEE, (String)nvp.get(PAYMENT_FEE));//payment Fee
	    	        cbMap.put(PaymentHandlerConstants.CALLBACK_AMOUNT_KEY, (String)nvp.get(CALLBACK_AMOUNT_KEY)); //amount
	    	        cbMap.put(PaymentHandlerConstants.TRANSID_KEY, (String)nvp.get(TRANSID_KEY)); //transaction id
	    	        
	    	        
	    			//set RealPath    	        
	    	        String path = session.getServletContext().getRealPath("/");
	    	        
	    	        
	    			PaymentHandlerManager manager = (PaymentHandlerManager) SpringUtil.getBean("paymentHandlerManagerImplPayPal");
	    			Map<String,Object> returnMap = manager.handlePayment(cbMap, session, true);
		    		try {
	                    System.out.println("STATUS_OK_VALUE:"+STATUS_OK_VALUE+",cbMap.getStatus():"+cbMap.getStatus());
	                    String msg =(String)returnMap.get("msg");
						if (STATUS_OK_VALUE.equals(msg)) {
							 System.out.println("Pay by Paypal successful!");						
							removeSendConfirmSession(session);
							
							//send voucher email
							PrepaidTopUpTransaction ptt =(PrepaidTopUpTransaction)returnMap.get("ptt");
							prepaidService.sendVoucherNotificationEmail(ptt, request);
							
							if(ptt.getPrepaid().getPickUpType().equals("friend")){
								prepaidService.sendVoucherConfirmEmail(ptt, request);
							}
							
							// send payment confirm email
//							paymentService.sendPaymentConfirmEmail(order.getCompany().getId(), order.getReference());
							
							return "front/payment/paymentSucessfull";
	                        
						}else {
//	                        System.out.println("STATUS_KEY:" + cbMap.getStatus());
	                        return "front/payment/paymentFail";
	                    }
					} catch (KeyNotFoundException e) {
						e.printStackTrace(System.err);
						logger.error("Exception occurs during Payment Provider callback handling", e);
						removeSendConfirmSession(session);
						return "front/payment/paymentFail";
					}
	    		} else {
	    			removeSendConfirmSession(session);
	    			return "front/payment/paymentFail";
	    		}
	    	} else {
	    		removeSendConfirmSession(session);
	    		return "front/payment/paymentFail";
	    	}
	}
	private String getPostConnection(String url, Map parameterMap) throws HttpException, IOException {
		// use httpclient to send connection		
		HttpClient httpClient = new HttpClient();
    	PostMethod postMethod = new PostMethod(url);
    	postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
    	
    	NameValuePair[] data = new NameValuePair[parameterMap.size()];
    	
    	Iterator itr = parameterMap.keySet().iterator();
    	int i=0;
    	while(itr.hasNext()){
    		String key =(String)itr.next();
    		String value =(String)parameterMap.get(key);
    		data[i] = new NameValuePair();
    		data[i].setName(key);
    		data[i].setValue(value);
    		i++;
    	}
    	postMethod.setRequestBody(data);
    	int statusCode = httpClient.executeMethod(postMethod);      
    	
//    	System.out.println("getPostConnection statusCode  = "+statusCode);        	
    	String responseString = postMethod.getResponseBodyAsString();        	
//    	System.out.println("getPostConnection  responseString  = "+responseString);        	
    	postMethod.releaseConnection(); 
    	
    	return responseString;
	}
	private HashMap setResponseStringToMap(String responseString){	
		//logger.debug("======================NVP Values ============");
		HashMap nvp = new HashMap(); 
		StringTokenizer stTok = new StringTokenizer( responseString, "&");
		while (stTok.hasMoreTokens())
		{
			StringTokenizer stInternalTokenizer = new StringTokenizer( stTok.nextToken(), "=");
			if (stInternalTokenizer.countTokens() == 2)
			{
				String key = URLDecoder.decode( stInternalTokenizer.nextToken());
				String value = URLDecoder.decode( stInternalTokenizer.nextToken());
				nvp.put( key.toUpperCase(), value );
				
//				System.out.println(" >>>>>> nvp  KEYS "+ key + " / value : "+ nvp.get(key));
			}
		}
		
		return nvp;
	}	
	
	private String getConnection(String url) throws HttpException, IOException {
		// use httpclient to send connection		
		HttpClient httpClient = new HttpClient();    
		//** comment out the get method
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
    	int statusCode = httpClient.executeMethod(getMethod);      
    	
    	System.out.println("statusCode  = "+statusCode);        	
    	String responseString = getMethod.getResponseBodyAsString();        	
    	System.out.println("responseString  = "+responseString);        	
    	getMethod.releaseConnection();     	
    	
    	return responseString;
	}
	
	private void removeSendConfirmSession(HttpSession session){
		session.removeAttribute("paymentSendConfirm");
	}
	
	private String getPrepaidDescription(Long productOptionId,Double initValue){
		String prepaidName="";
		if(productOptionId !=null){
			ProductOption po =productOptionService.get(productOptionId);
			prepaidName="Treatment Voucher for "+po.getProduct().getName();
		}else{
			prepaidName="$"+initValue.toString()+" Cash Voucher";
		}
		return prepaidName;
	}
}