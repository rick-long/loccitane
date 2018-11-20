package com.spa.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.company.Company;
import org.spa.model.order.OrderSurvey;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.user.User;
import org.spa.service.order.OrderSurveyService;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.spa.constant.CommonConstant;
import com.spa.email.EmailAddress;
import com.spa.thread.ReviewThread;

public class ReviewDelayScheduleJob extends QuartzJobBean{
	private static int EMAIL_JOB_DELAY_START =2;//发送时间设置
	
	public ReviewDelayScheduleJob() {
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println("-----run ReviewDelayScheduleJob----");
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		PurchaseOrder purchaseOrder =(PurchaseOrder) dataMap.get("purchaseOrder");
		User member =(User)dataMap.get("user");
		Company company = (Company)dataMap.get("company");
		String urlRoot = (String)dataMap.get("urlRoot");
		OrderSurveyService orderSurveyService = (OrderSurveyService) dataMap.get("orderSurveyService");
		
		OrderSurvey orderSurvey = orderSurveyService.getOrderSurveyByOrderId(purchaseOrder.getId());
		if(orderSurvey !=null && (!orderSurvey.getStatus().equals("ACTIVING"))){
			return;
		}
		System.out.println("-----run ReviewDelayScheduleJob----run ReviewThread------");
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("company", company);
		parameterMap.put("urlRoot",urlRoot);
		parameterMap.put("purchaseOrder",purchaseOrder);
		parameterMap.put("user",member);
		parameterMap.put("emailAddress",new EmailAddress(member.getEmail(),member.getFullName()));
		parameterMap.put("templateType", CommonConstant.SEND_THANK_YOU_EMAIL);
		parameterMap.put("emailJobDelayStart", EMAIL_JOB_DELAY_START);
		ReviewThread.getInstance(parameterMap).start();
		
		if(orderSurvey !=null){
			orderSurvey.setLastUpdatedBy(member.getUsername());
			orderSurvey.setLastUpdated(new Date());
			orderSurvey.setStatus("SENT_EMAIL");
			orderSurveyService.saveOrUpdate(orderSurvey);
		}
	}
}
