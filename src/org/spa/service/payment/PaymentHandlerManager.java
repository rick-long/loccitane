package org.spa.service.payment;


import java.util.Map;

import javax.servlet.http.HttpSession;

import com.spa.constant.CallbackMappedAttributes;

public interface PaymentHandlerManager {
    public Map<String,Object> handlePayment(CallbackMappedAttributes cbMap, HttpSession session, boolean byPassAmountCheck);

}
