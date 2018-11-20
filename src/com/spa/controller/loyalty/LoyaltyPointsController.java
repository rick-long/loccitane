package com.spa.controller.loyalty;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.validation.Valid;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.points.LoyaltyPointsTransaction;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.loyalty.LoyaltyPointsListVO;
import org.spa.vo.loyalty.PointsAdjustVO;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/05/17.
 */
@Controller
@RequestMapping("lp")
public class LoyaltyPointsController extends BaseController {
	
	
	@RequestMapping("toView")
	public String toView(LoyaltyPointsListVO loyaltyPointsListVO){
		
		return "points/loyaltyPointsManagement";
	}
	
	@RequestMapping("list")
	public String list(@Valid LoyaltyPointsListVO loyaltyPointsListVO,BindingResult result,Model model) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LoyaltyPointsTransaction.class);
		//from date
		
		Date fromDate = loyaltyPointsListVO.getFromDate();
		Date toDate = loyaltyPointsListVO.getToDate();
		if (fromDate !=null) {
			detachedCriteria.add(Restrictions.ge("earnDate", DateUtil.getFirstMinuts(fromDate)));
		}
		//to date
		if (toDate !=null) {
			detachedCriteria.add(Restrictions.le("earnDate", DateUtil.getLastMinuts(toDate)));
		}
		
		//member
		if (loyaltyPointsListVO.getMemberId()!=null) {
			detachedCriteria.add(Restrictions.eq("user.id", loyaltyPointsListVO.getMemberId()));
		}
		
		Page<LoyaltyPointsTransaction> lptPage = loyaltyPointsTransactionService.list(detachedCriteria, loyaltyPointsListVO.getPageNumber(), loyaltyPointsListVO.getPageSize());
		model.addAttribute("page", lptPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(loyaltyPointsListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		if (loyaltyPointsListVO.getMemberId()!=null) {
			model.addAttribute("client",loyaltyPointsListVO.getUsername());
			model.addAttribute("remainPoints",loyaltyPointsTransactionService.getRemainLoyaltyPointsByUser(loyaltyPointsListVO.getMemberId(), new Date()));
		}
		return "points/loyaltyPointsList";
	}
	
	@RequestMapping("toAdjust")
	public String toAdjust(PointsAdjustVO pointsAdjustVO){
		
		return "points/loyaltyPointsAdjust";
	}
	
	@RequestMapping("adjust")
	@ResponseBody
	public AjaxForm adjustLoyaltyPoints(@Valid PointsAdjustVO pointsAdjustVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			pointsAdjustVO.setUser(userService.get(pointsAdjustVO.getMemberId()));
			pointsAdjustVO.setEarnChannel(CommonConstant.POINTS_EARN_CHANNEL_MANUAL);
			pointsAdjustVO.setPurchaseOrder(null);
			pointsAdjustVO.setEarnDate(new Date());
			
			loyaltyPointsTransactionService.saveLoyaltyPointsTransaction(pointsAdjustVO);
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			
		}
	}
}
