package com.spa.controller.sales;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.order.OrderSurvey;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.prepaid.PrepaidAddVO;
import org.spa.vo.sales.SurveyListVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.email.EmailAddress;
import com.spa.thread.ReviewThread;

/**
 * Created by Ivy on 2018/02/25.
 */
@Controller
@RequestMapping("survey")
public class SurveyController extends BaseController {
	
	private static final int EMAIL_JOB_DELAY_START =2;//发送时间设置
	
	@RequestMapping("toView")
	public String toView(Model model) {
		SurveyListVO surveyListVO = new SurveyListVO();
		model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true,true));
	    model.addAttribute("surveyListVO",surveyListVO);
	    model.addAttribute("fromDate",new Date());
	    
		return "sales/surveyManagement";
	}
	
	@RequestMapping("list")
	public String listSurvey(Model model, SurveyListVO surveyListVO) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OrderSurvey.class);
		
		detachedCriteria.add(Restrictions.eq("isActive", true));
		//from date
		if (StringUtils.isNotBlank(surveyListVO.getFromDate())) {
			detachedCriteria.add(Restrictions.ge("orderCompletedDate", 
					DateUtil.stringToDate(surveyListVO.getFromDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
			detachedCriteria.add(Restrictions.le("orderCompletedDate",
					DateUtil.stringToDate(surveyListVO.getFromDate() +" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		
		//shop
		detachedCriteria.createAlias("purchaseOrder", "po");
		if(surveyListVO.getShopId() !=null && surveyListVO.getShopId().longValue()>0){
			detachedCriteria.add(Restrictions.eq("po.shop.id", surveyListVO.getShopId()));
		}else{
			detachedCriteria.add(Restrictions.in("po.shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
		}
		detachedCriteria.addOrder(Order.asc("status"));
		Page<OrderSurvey> orderPage = orderSurveyService.list(detachedCriteria, surveyListVO.getPageNumber(), surveyListVO.getPageSize());
		model.addAttribute("page", orderPage);
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(surveyListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "sales/surveyList";
	}
	
	
	@RequestMapping("entryEmail")
	public String entryEmail(Model model,Long orderSurveyId) {
		
	    model.addAttribute("orderSurveyId",orderSurveyId);
		
		return "sales/surveyEntryEmail";
	}
	
	@RequestMapping("send")
    @ResponseBody
    public AjaxForm send(Long orderSurveyId,String emailAddress) {
        if (orderSurveyId != null) {
        	
        	OrderSurvey orderSurvey = orderSurveyService.get(orderSurveyId);
        	String urlRoot=WebThreadLocal.getUrlRoot();
			User member= orderSurvey.getPurchaseOrder().getUser();
			Map<String, Object> parameterMap = new HashMap<>();
			 
			parameterMap.put("company", WebThreadLocal.getCompany());
			parameterMap.put("urlRoot",urlRoot);
			parameterMap.put("emailJobDelayStart", EMAIL_JOB_DELAY_START);
			parameterMap.put("purchaseOrder",orderSurvey.getPurchaseOrder());
			parameterMap.put("user",member);
			if(StringUtils.isNoneBlank(emailAddress)){
				parameterMap.put("emailAddress",new EmailAddress(emailAddress,member.getFullName()));
			}else{
				parameterMap.put("emailAddress",new EmailAddress(member.getEmail(),member.getFullName()));
			}
			parameterMap.put("templateType", CommonConstant.SEND_THANK_YOU_EMAIL);
			ReviewThread.getInstance(parameterMap).start();
			
        	
        	orderSurvey.setStatus("SENT_EMAIL");
        	orderSurvey.setLastUpdated(new Date());
            orderSurvey.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
        	orderSurveyService.saveOrUpdate(orderSurvey);
        }
        return AjaxFormHelper.success("success");
    }

	@RequestMapping("notSend")
    @ResponseBody
    public AjaxForm notSend(Long orderSurveyId) {
        if (orderSurveyId != null) {
        	OrderSurvey orderSurvey = orderSurveyService.get(orderSurveyId);
        	orderSurvey.setStatus("OBSTINATE");
        	orderSurvey.setLastUpdated(new Date());
            orderSurvey.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
        	orderSurveyService.saveOrUpdate(orderSurvey);
        }
        return AjaxFormHelper.success("success");
    }
}
