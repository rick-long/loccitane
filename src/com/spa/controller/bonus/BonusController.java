package com.spa.controller.bonus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.bonus.BonusAttribute;
import org.spa.model.bonus.BonusRule;
import org.spa.model.bonus.BonusTemplate;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.bonus.BonusAttributeVO;
import org.spa.vo.bonus.BonusRuleListVO;
import org.spa.vo.bonus.BonusRuleVO;
import org.spa.vo.bonus.BonusTemplateListVO;
import org.spa.vo.bonus.BonusTemplateVO;
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
 * @author Ivy 2016-11-3
 */
@Controller
@RequestMapping("bonus")
public class BonusController extends BaseController {

    // bonus template ------------------------------------------------------------------------

    @RequestMapping("toView")
    public String bonusTemplateManagement(Model model, BonusTemplateListVO bonusTemplateListVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "bonus/bonusTemplateManagement";
    }

    @RequestMapping("bonusTemplateList")
    public String bonusTemplateList(Model model, BonusTemplateListVO bonusTemplateListVO) {
        String name = bonusTemplateListVO.getName();
        String isActive = bonusTemplateListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BonusTemplate.class);
        
        if (StringUtils.isNoneBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        
        if(WebThreadLocal.getCompany() !=null){
        	detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        
        Page<BonusTemplate> page = bonusTemplateService.list(detachedCriteria, bonusTemplateListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(bonusTemplateListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "bonus/bonusTemplateList";
    }

    @RequestMapping("bonusTemplateToAdd")
    public String bonusTemplateToAdd(Model model, BonusTemplateVO bonusTemplateVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("bonusTemplateVO", bonusTemplateVO);
        return "bonus/bonusTemplateAdd";
    }

    @RequestMapping("bonusTemplateConfirm")
    public String bonusTemplateConfirm(Model model, BonusTemplateVO bonusTemplateVO) {
        model.addAttribute("bonusTemplateVO", bonusTemplateVO);
        return "bonus/bonusTemplateConfirm";
    }
    
    @RequestMapping("bonusTemplateSave")
    @ResponseBody
    public AjaxForm bonusTemplateSave(@Valid BonusTemplateVO bonusTemplateVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            bonusTemplateVO.setCompany(WebThreadLocal.getCompany());
            bonusTemplateService.saveOrUpdate(bonusTemplateVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    @RequestMapping("bonusTemplateView")
    public String bonusTemplateView(Model model, Long id) {
        BonusTemplate bonusTemplate = bonusTemplateService.get(id);
        model.addAttribute("bonusTemplate", bonusTemplate);
        return "bonus/bonusTemplateView";
    }

    @RequestMapping("bonusTemplateToEdit")
    public String bonusTemplateToEdit(Model model, BonusTemplateVO bonusTemplateVO) {
        BonusTemplate bonusTemplate = bonusTemplateService.get(bonusTemplateVO.getId());
        model.addAttribute("bonusTemplate", bonusTemplate);
        return "bonus/bonusTemplateEdit";
    }

    @RequestMapping("bonusTemplateSelectList")
    public String bonusTemplateSelectList(Long initialValue, Boolean showAll, Model model) {
    	DetachedCriteria dc = DetachedCriteria.forClass(BonusTemplate.class);
        List<BonusTemplate> list = bonusTemplateService.getActiveListByRefAndCompany(dc, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("list", list);
        model.addAttribute("initialValue", initialValue);
        model.addAttribute("showAll", showAll);
        return "bonus/bonusTemplateSelectList";
    }

    // bonus rule -------------------------------------------------------------------------------------------

    @RequestMapping("toRuleView")
    public String bonusRuleManagement(Model model, BonusRuleListVO bonusRuleListVO) {
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BonusTemplate.class);
        model.addAttribute("bonusTemplateList", bonusTemplateService.getActiveListByRefAndCompany(detachedCriteria, null, WebThreadLocal.getCompany().getId()));
        return "bonus/bonusRuleManagement";
    }

    @RequestMapping("bonusRuleList")
    public String bonusRuleList(Model model, BonusRuleListVO bonusRuleListVO) {
        String isActive = bonusRuleListVO.getIsActive();
        Long bonusTemplateId=bonusRuleListVO.getBonusTemplateId();
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BonusRule.class);
        
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        if (bonusTemplateId !=null) {
            detachedCriteria.add(Restrictions.eq("bonusTemplate.id", bonusTemplateId));
        }
        if(WebThreadLocal.getCompany() !=null){
        	detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        Page<BonusRule> page = bonusRuleService.list(detachedCriteria,bonusRuleListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(bonusRuleListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "bonus/bonusRuleList";
    }

    @RequestMapping("bonusRuleToAdd")
    public String bonusRuleToAdd(Model model, BonusRuleVO bonusRuleVO) {
    	model.addAttribute("userGroupType", CommonConstant.USER_GROUP_TYPE_STAFF);
    	model.addAttribute("userGroupModule", CommonConstant.USER_GROUP_MODULE_COMMISSION);
        return "bonus/bonusRuleAdd";
    }

    @RequestMapping("bonusRuleAddAttribute")
    public String bonusRuleAddAttribute(Model model, BonusRuleVO bonusRuleVO) {
        BonusTemplate bonusTemplate = bonusTemplateService.get(bonusRuleVO.getBonusTemplateId());
        bonusRuleVO.setBonusTemplate(bonusTemplate);
        model.addAttribute("bonusRuleVO", bonusRuleVO);
        BonusAttributeVO[] attributeVOs = bonusTemplate.getBonusAttributeKeys().stream().map(attribute -> {
            BonusAttributeVO attributeVO = new BonusAttributeVO();
            attributeVO.setBonusAttributeKeyId(attribute.getId());
            attributeVO.setReference(attribute.getReference());
            attributeVO.setName(attribute.getName());
            attributeVO.setDescription(attribute.getDescription());
            attributeVO.setBonusAttributeKeyId(attribute.getId());
            return attributeVO;
        }).toArray(size -> new BonusAttributeVO[size]);
        bonusRuleVO.setBonusAttributeVOs(attributeVOs);
        if (bonusRuleVO.getId() != null) {
            BonusRule bonusRule = bonusRuleService.get(bonusRuleVO.getId());
            Set<BonusAttribute> attributeSet = bonusRule.getBonusAttributes();
            for (BonusAttributeVO attributeVO : attributeVOs) {
                for (BonusAttribute attribute : attributeSet) {
                    if (attribute.getBonusAttributeKey().getId().equals(attributeVO.getBonusAttributeKeyId())) {
                        attributeVO.setValue(attribute.getValue());
                        break;
                    }
                }
            }
        }
        return "bonus/bonusRuleAddAttribute";
    }

    @RequestMapping("bonusRuleConfirm")
    public String bonusRuleConfirm(Model model, BonusRuleVO bonusRuleVO) {
        bonusRuleVO.setBonusTemplate(bonusTemplateService.get(bonusRuleVO.getBonusTemplateId()));
        model.addAttribute("bonusRuleVO", bonusRuleVO);

        return "bonus/bonusRuleConfirm";
    }

    @RequestMapping("bonusRuleSave")
    @ResponseBody
    public AjaxForm bonusRuleSave(@Valid BonusRuleVO bonusRuleVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            bonusRuleVO.setCompany(WebThreadLocal.getCompany());
            bonusRuleVO.setBonusTemplate(bonusTemplateService.get(bonusRuleVO.getBonusTemplateId()));
            bonusRuleService.saveOrUpdate(bonusRuleVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    @RequestMapping("bonusRuleView")
    public String bonusRuleView(Model model, Long id) {
        BonusRule bonusRule = bonusRuleService.get(id);
        model.addAttribute("bonusRule", bonusRule);
        return "bonus/bonusRuleView";
    }

    @RequestMapping("bonusRuleToEdit")
    public String bonusRuleToEdit(Model model, BonusRuleVO bonusRuleVO) {
        BonusRule bonusRule = bonusRuleService.get(bonusRuleVO.getId());
        model.addAttribute("bonusRuleVO", bonusRuleVO);
        model.addAttribute("bonusRule", bonusRule);
        
        model.addAttribute("userGroupType", CommonConstant.USER_GROUP_TYPE_STAFF);
    	model.addAttribute("userGroupModule", CommonConstant.USER_GROUP_MODULE_COMMISSION);
    	
        return "bonus/bonusRuleEdit";
    }



}