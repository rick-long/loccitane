package com.spa.controller.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.user.ConsentForm;
import org.spa.model.user.ConsentFormUser;
import org.spa.model.user.User;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.user.ConsentFormListVO;
import org.spa.vo.user.ConsentFormUserListVO;
import org.spa.vo.user.ConsentFormUserSignVO;
import org.spa.vo.user.ConsentFormVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/08/29.
 */
@Controller
@RequestMapping("consentForm")
public class ConsentFormController extends BaseController {

    @RequestMapping("toView")
    public String management(Model model,ConsentFormListVO ConsentFormListVO) {
        return "consentForm/consentFormManagement";
    }

    @RequestMapping("list")
    public String list(Model model, ConsentFormListVO consentFormListVO) {

        String name = consentFormListVO.getName();
        String isActive=consentFormListVO.getIsActive();
		
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConsentForm.class);

        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
		}
        
        detachedCriteria.add(Restrictions.eq("company.id",WebThreadLocal.getCompany().getId()));
        //page start
        Page<ConsentForm> ConsentFormPage = consentFormService.list(detachedCriteria, consentFormListVO.getPageNumber(), consentFormListVO.getPageSize());
        model.addAttribute("page", ConsentFormPage);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(consentFormListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "consentForm/consentFormList";
    }

    @RequestMapping("toAdd")
    public String toAdd(ConsentFormVO consentFormVO, Model model) {
        return "consentForm/consentFormAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm add(@Valid ConsentFormVO consentFormVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
        	consentFormService.saveOrUpdate(consentFormVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toEdit")
    public String toEdit(ConsentFormVO consentFormVO, Model model) {
        ConsentForm consentForm = consentFormService.get(consentFormVO.getId());
        consentFormVO.setRemarks(consentForm.getRemarks());
        consentFormVO.setName(consentForm.getName());
        consentFormVO.setActive(consentForm.isIsActive());
        model.addAttribute("consentFormVO",consentFormVO);
        return "consentForm/consentFormEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm edit(@Valid ConsentFormVO consentFormVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            consentFormService.saveOrUpdate(consentFormVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }

    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(ConsentFormVO consentFormVO) {
    	ConsentForm cf=consentFormService.get(consentFormVO.getId());
        if (cf != null) {
        	try {
        		cf.setIsActive(false);
        		consentFormService.saveOrUpdate(cf);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }
    
    @RequestMapping("toSign")
    public String toSign(ConsentFormUserSignVO consentFormUserSignVO, Model model) {
    	
    	// user
    	if(consentFormUserSignVO.getUserId() !=null && consentFormUserSignVO.getUserId()>0){
    		//
    	}else{
    		return null;
    	}
    	model.addAttribute("userId", consentFormUserSignVO.getUserId());
    	
    	//shop list
		model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
		
    	//consent form list
    	DetachedCriteria uldc = DetachedCriteria.forClass(ConsentForm.class);
		List<ConsentForm> consentFormList=consentFormService.getActiveListByRefAndCompany(uldc, null, WebThreadLocal.getCompany().getId());
		model.addAttribute("consentFormList", consentFormList);
		
		User user=userService.get(consentFormUserSignVO.getUserId());
		model.addAttribute("userFullName", user.getFullName());
		
        return "consentForm/consentFormSign";
    }

    @RequestMapping("sign")
    @ResponseBody
    public AjaxForm sign(@Valid ConsentFormUserSignVO consentFormUserSignVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
        	consentFormUserService.saveOrUpdate(consentFormUserSignVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }
    
    @RequestMapping("listUserConsentForm")
    public String listUserConsentForm(Model model, ConsentFormUserListVO consentFormUserListVO) {

        Long userId = consentFormUserListVO.getUserId();
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConsentFormUser.class);

        if (userId !=null) {
            detachedCriteria.add(Restrictions.eq("user.id", userId));
        }
        detachedCriteria.add(Restrictions.eq("isActive",true));
        detachedCriteria.add(Restrictions.eq("company.id",WebThreadLocal.getCompany().getId()));
        //page start
        Page<ConsentFormUser> ConsentFormPage = consentFormUserService.list(detachedCriteria, consentFormUserListVO.getPageNumber(), consentFormUserListVO.getPageSize());
        model.addAttribute("page", ConsentFormPage);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(consentFormUserListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "consentForm/consentFormList";
    }
    
}

