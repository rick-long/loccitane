package com.spa.controller.loyalty;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.validation.Valid;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.points.SpaPointsTransaction;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.loyalty.PointsAdjustVO;
import org.spa.vo.loyalty.SpaPointsListVO;
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
@RequestMapping("sp")
public class SpaPointsController extends BaseController {
	
	@RequestMapping("toView")
	public String toView(SpaPointsListVO spaPointsListVO){
		
		return "points/spaPointsManagement";
	}
	
	@RequestMapping("list")
	public String list(@Valid SpaPointsListVO spaPointsListVO,BindingResult result,Model model) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SpaPointsTransaction.class);
		
		Date now=new Date();
		//from date
		Date fromDate = spaPointsListVO.getFromDate();
		Date toDate = spaPointsListVO.getToDate();
		if (fromDate ==null) {
			fromDate = now;
		}
		//to date
		if (toDate ==null) {
			toDate = now;
		}
		detachedCriteria.add(Restrictions.ge("earnDate", DateUtil.getFirstMinuts(fromDate)));
		detachedCriteria.add(Restrictions.le("earnDate", DateUtil.getLastMinuts(toDate)));
		
		//member
		if (spaPointsListVO.getMemberId()!=null) {
			detachedCriteria.add(Restrictions.eq("user.id", spaPointsListVO.getMemberId()));
		}
		
		Page<SpaPointsTransaction> sptPage = spaPointsTransactionService.list(detachedCriteria, spaPointsListVO.getPageNumber(), spaPointsListVO.getPageSize());
		model.addAttribute("page", sptPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(spaPointsListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		if (spaPointsListVO.getMemberId()!=null) {
			model.addAttribute("client",spaPointsListVO.getUsername());
			model.addAttribute("remainPoints",spaPointsTransactionService.getRemainSpaPointsByUser(spaPointsListVO.getMemberId(), new Date()));
		}
		return "points/spaPointsList";
	}
	
	@RequestMapping("toAdjust")
	public String toAdjust(PointsAdjustVO pointsAdjustVO){
		
		return "points/spaPointsAdjust";
	}
	
	@RequestMapping("adjust")
	@ResponseBody
	public AjaxForm adjust(@Valid PointsAdjustVO pointsAdjustVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			pointsAdjustVO.setUser(userService.get(pointsAdjustVO.getMemberId()));
			pointsAdjustVO.setEarnChannel(CommonConstant.POINTS_EARN_CHANNEL_MANUAL);
			pointsAdjustVO.setPurchaseOrder(null);
			pointsAdjustVO.setEarnDate(new Date());
			spaPointsTransactionService.saveSpaPointsTransaction(pointsAdjustVO,true);
			
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			
		}
	}
}
