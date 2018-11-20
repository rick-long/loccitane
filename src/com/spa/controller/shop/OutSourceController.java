package com.spa.controller.shop;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.shop.OutSourceTemplate;
import org.spa.model.shop.Shop;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.shop.OutSourceTemplateListVO;
import org.spa.vo.shop.OutSourceTemplateVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/06/27.
 */
@Controller
@RequestMapping("outSource")
public class OutSourceController extends BaseController {
	
	@RequestMapping("toTemplateView")
    public String outSourceTemplateManagement(Model model, OutSourceTemplateListVO outSourceTemplateListVO) {
        return "outSource/outSourceTemplateManagement";
    }

    @RequestMapping("outSourceTemplateList")
    public String outSourceTemplateList(Model model, OutSourceTemplateListVO outSourceTemplateListVO) {
        String name = outSourceTemplateListVO.getName();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutSourceTemplate.class);
        detachedCriteria.add(Restrictions.eq("isActive",true));
        if (StringUtils.isNoneBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        
        if(WebThreadLocal.getCompany() !=null){
        	detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        
        Page<OutSourceTemplate> page = outSourceTemplateService.list(detachedCriteria, outSourceTemplateListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(outSourceTemplateListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "outSource/outSourceTemplateList";
    }

    @RequestMapping("outSourceTemplateToAdd")
    public String outSourceTemplateToAdd(Model model, OutSourceTemplateListVO outSourceTemplateVO) {
        model.addAttribute("outSourceTemplateVO", outSourceTemplateVO);
        return "outSource/outSourceTemplateAdd";
    }

    @RequestMapping("outSourceTemplateConfirm")
    public String outSourceTemplateConfirm(Model model, OutSourceTemplateVO outSourceTemplateVO) {
        model.addAttribute("outSourceTemplateVO", outSourceTemplateVO);
        return "outSource/outSourceTemplateConfirm";
    }
    
    @RequestMapping("outSourceTemplateSave")
    @ResponseBody
    public AjaxForm outSourceTemplateSave(@Valid OutSourceTemplateVO outSourceTemplateVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            outSourceTemplateVO.setCompany(WebThreadLocal.getCompany());
            outSourceTemplateService.saveOrUpdate(outSourceTemplateVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }
    
    @RequestMapping("outSourceTemplateToEdit")
    public String outSourceTemplateToEdit(Model model, OutSourceTemplateVO outSourceTemplateVO) {
    	OutSourceTemplate outSourceTemplate = outSourceTemplateService.get(outSourceTemplateVO.getId());
        model.addAttribute("outSourceTemplate", outSourceTemplate);
        return "outSource/outSourceTemplateEdit";
    }

    @RequestMapping("outSourceTemplateSelectList")
    public String outSourceTemplateSelectList(Long initialValue, Boolean showAll, Model model) {
    	DetachedCriteria dc = DetachedCriteria.forClass(OutSourceTemplate.class);
        List<OutSourceTemplate> list = outSourceTemplateService.getActiveListByRefAndCompany(dc, null, WebThreadLocal.getCompany().getId());
        if(initialValue !=null && initialValue.longValue()>0){
        	//
        }else{
        	if(list !=null && list.size()>0){
        		initialValue=list.get(0).getId();
        	}
        }
        model.addAttribute("list", list);
        model.addAttribute("initialValue", initialValue);
        model.addAttribute("showAll", showAll);
        return "outSource/outSourceTemplateSelectList";
    }
    
    
    @RequestMapping("isOutSource")
    @ResponseBody
    public Boolean isOutSource(Long shopId) {
    	Shop shop=shopService.get(shopId);
    	if(shop.getOutSourceTemplate() !=null){
    		return true;
    	}
    	return false;
    }
}
