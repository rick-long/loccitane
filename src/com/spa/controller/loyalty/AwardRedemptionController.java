package com.spa.controller.loyalty;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.awardRedemption.AwardRedemption;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.loyalty.AwardRedemptionAddVO;
import org.spa.vo.loyalty.AwardRedemptionListVO;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/05/17.
 */
@Controller
@RequestMapping("awardRedemption")
public class AwardRedemptionController extends BaseController {
	
	@RequestMapping("toView")
	public String awardRedemption(Model model,AwardRedemptionListVO awardRedemptionListVO) {
		return "awardRedemption/awardRedemptionManagement";
	}
	

	@RequestMapping("list")
	public String awardRedemptionList(Model model,AwardRedemptionListVO awardRedemptionListVO){
		
		String name=awardRedemptionListVO.getName();
		Boolean isActive=awardRedemptionListVO.getIsActive();
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AwardRedemption.class);
		
		
		if(StringUtils.isNotBlank(name)){
			detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if(isActive !=null){
			detachedCriteria.add(Restrictions.eq("isActive", isActive));
		}
		
		Page<AwardRedemption> awardRedemptionPage =awardRedemptionService.list(detachedCriteria, awardRedemptionListVO.getPageNumber(), awardRedemptionListVO.getPageSize());
		model.addAttribute("page", awardRedemptionPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(awardRedemptionListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		
		return "awardRedemption/awardRedemptionList";
	}

	@RequestMapping("toAdd")
	public String toAddAwardRedemption(AwardRedemptionAddVO awardRedemptionAddVO, Model model) {
		
		model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));

		model.addAttribute("requiredLp", 0d);
		model.addAttribute("requiredAmount", 0d);

		model.addAttribute("startDate", new Date());

		return "awardRedemption/awardRedemptionAdd";
	}
	
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addAwardRedemption(@Valid AwardRedemptionAddVO awardRedemptionAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			DetachedCriteria dc = DetachedCriteria.forClass(AwardRedemption.class);
			dc.add(Restrictions.eq("isActive", true));
			dc.add(Restrictions.eq("name", awardRedemptionAddVO.getName()));
			List<AwardRedemption> list=awardRedemptionService.list(dc);
			
			if (list !=null && list.size()>0) {
				errorAjaxForm.addErrorFields(new ErrorField("name", I18nUtil.getMessageKey("label.errors.duplicate")));
			}
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
			}else{
				awardRedemptionService.saveAwardRedemption(awardRedemptionAddVO);
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			}
		}
	}
	
	@RequestMapping("toEdit")
	public String toEditAwardRedemption(AwardRedemptionAddVO awardRedemptionAddVO,Model model){
		Long id=awardRedemptionAddVO.getId();
		AwardRedemption ar=awardRedemptionService.get(id);
		if(ar !=null){
			awardRedemptionAddVO.setDescription(ar.getDescription());
			awardRedemptionAddVO.setName(ar.getName());
			
			try {
				awardRedemptionAddVO.setEndDate(DateUtil.dateToString(ar.getEndDate(),"yyyy-MM-dd"));
				awardRedemptionAddVO.setStartDate(DateUtil.dateToString(ar.getStartDate(),"yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(ar.getProductOption() !=null){
				awardRedemptionAddVO.setProductName(ar.getProductOption().getProduct().getName());
				awardRedemptionAddVO.setProductOptionId(ar.getProductOption().getId());
			}
			
			awardRedemptionAddVO.setRedeemChannel(ar.getRedeemChannel());
			awardRedemptionAddVO.setRedeemType(ar.getRedeemType());
			awardRedemptionAddVO.setRequiredAmount(ar.getRequiredAmount());
			awardRedemptionAddVO.setRequiredLp(ar.getRequiredLp());
			awardRedemptionAddVO.setIsActive(ar.isIsActive());
			awardRedemptionAddVO.setValidAt(ar.getValidAt());
			awardRedemptionAddVO.setBeWorth(ar.getBeWorth());
		}
	    model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
	    
		return "awardRedemption/awardRedemptionEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editAwardRedemption(@Valid AwardRedemptionAddVO awardRedemptionAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			String name=awardRedemptionAddVO.getName();
			AwardRedemption ar=awardRedemptionService.get(awardRedemptionAddVO.getId());
			if(!name.equals(ar.getName())){
				DetachedCriteria dc = DetachedCriteria.forClass(AwardRedemption.class);
				dc.add(Restrictions.eq("isActive", true));
				dc.add(Restrictions.eq("name", awardRedemptionAddVO.getName()));
				List<AwardRedemption> list=awardRedemptionService.list(dc);
				if (list !=null && list.size()>0) {
					errorAjaxForm.addErrorFields(new ErrorField("name", I18nUtil.getMessageKey("label.errors.duplicate")));
				}
			}
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
			}else{
				awardRedemptionService.saveAwardRedemption(awardRedemptionAddVO);
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
			}
		}
	}
	
	@RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
		AwardRedemption ar=awardRedemptionService.get(id);
        if (ar != null) {
        	try {
        		ar.setIsActive(false);
        		awardRedemptionService.saveOrUpdate(ar);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }
}
