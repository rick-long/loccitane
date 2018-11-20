package com.spa.controller.commission;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.commission.CommissionAttribute;
import org.spa.model.commission.CommissionRule;
import org.spa.model.commission.CommissionTemplate;
import org.spa.model.product.Category;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.commission.CommissionAttributeVO;
import org.spa.vo.commission.CommissionRuleListVO;
import org.spa.vo.commission.CommissionRuleVO;
import org.spa.vo.commission.CommissionTemplateListVO;
import org.spa.vo.commission.CommissionTemplateVO;
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
 * @author Ivy 2016-7-25
 */
@Controller
@RequestMapping("commission")
public class CommissionController extends BaseController {

    // commission template ------------------------------------------------------------------------

    @RequestMapping("toView")
    public String commissionTemplateManagement(Model model, CommissionTemplateListVO commissionTemplateListVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "commission/commissionTemplateManagement";
    }

    @RequestMapping("commissionTemplateList")
    public String commissionTemplateList(Model model, CommissionTemplateListVO commissionTemplateListVO) {
        String name = commissionTemplateListVO.getName();
        String isActive = commissionTemplateListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CommissionTemplate.class);
        
        if (StringUtils.isNoneBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        
        if(WebThreadLocal.getCompany() !=null){
        	detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        
        Page<CommissionTemplate> page = commissionTemplateService.list(detachedCriteria, commissionTemplateListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(commissionTemplateListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "commission/commissionTemplateList";
    }

    @RequestMapping("commissionTemplateToAdd")
    public String commissionTemplateToAdd(Model model, CommissionTemplateVO commissionTemplateVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("commissionTemplateVO", commissionTemplateVO);
        return "commission/commissionTemplateAdd";
    }

    @RequestMapping("commissionTemplateConfirm")
    public String commissionTemplateConfirm(Model model, CommissionTemplateVO commissionTemplateVO) {
        model.addAttribute("commissionTemplateVO", commissionTemplateVO);
        return "commission/commissionTemplateConfirm";
    }
    
    @RequestMapping("commissionTemplateSave")
    @ResponseBody
    public AjaxForm commissionTemplateSave(@Valid CommissionTemplateVO commissionTemplateVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            commissionTemplateVO.setCompany(WebThreadLocal.getCompany());
            commissionTemplateService.saveOrUpdate(commissionTemplateVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    @RequestMapping("commissionTemplateView")
    public String commissionTemplateView(Model model, Long id) {
        CommissionTemplate commissionTemplate = commissionTemplateService.get(id);
        model.addAttribute("commissionTemplate", commissionTemplate);
        return "commission/commissionTemplateView";
    }

    @RequestMapping("commissionTemplateToEdit")
    public String commissionTemplateToEdit(Model model, CommissionTemplateVO commissionTemplateVO) {
        CommissionTemplate commissionTemplate = commissionTemplateService.get(commissionTemplateVO.getId());
        model.addAttribute("commissionTemplate", commissionTemplate);
        return "commission/commissionTemplateEdit";
    }

    @RequestMapping("commissionTemplateSelectList")
    public String commissionTemplateSelectList(Long initialValue, Boolean showAll, Model model) {
    	DetachedCriteria dc = DetachedCriteria.forClass(CommissionTemplate.class);
        List<CommissionTemplate> list = commissionTemplateService.getActiveListByRefAndCompany(dc, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("list", list);
        model.addAttribute("initialValue", initialValue);
        model.addAttribute("showAll", showAll);
        return "commission/commissionTemplateSelectList";
    }

    // commission rule -------------------------------------------------------------------------------------------

    @RequestMapping("toRuleView")
    public String commissionRuleManagement(Model model, CommissionRuleListVO commissionRuleListVO) {
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CommissionTemplate.class);
        model.addAttribute("commissionTemplateList", commissionTemplateService.getActiveListByRefAndCompany(detachedCriteria, null, WebThreadLocal.getCompany().getId()));
       
        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("subCategoryList", subCategoryList);
        
        return "commission/commissionRuleManagement";
    }

    @RequestMapping("commissionRuleList")
    public String commissionRuleList(Model model, CommissionRuleListVO commissionRuleListVO) {
        String isActive = commissionRuleListVO.getIsActive();
        Long commissionTemplateId=commissionRuleListVO.getCommissionTemplateId();
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CommissionRule.class);
        
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        if (commissionTemplateId !=null) {
            detachedCriteria.add(Restrictions.eq("commissionTemplate.id", commissionTemplateId));
        }
        if(WebThreadLocal.getCompany() !=null){
        	detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        
        if(commissionRuleListVO.getProductId() !=null){
			detachedCriteria.createAlias("products", "p");
			detachedCriteria.add(Restrictions.eq("p.id", commissionRuleListVO.getProductId()));
		}else if(commissionRuleListVO.getCategoryId() !=null){
			List<Long> allChildren = new ArrayList<>();
	        allChildren.add(commissionRuleListVO.getCategoryId());
	        categoryService.getAllChildrenByCategory(allChildren, commissionRuleListVO.getCategoryId());
	        if (allChildren.size() > 0) {
	        	detachedCriteria.createAlias("categories", "c");
	            detachedCriteria.add(Restrictions.in("c.id", allChildren));
	        }
		}
        
        Page<CommissionRule> page = commissionRuleService.list(detachedCriteria,commissionRuleListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(commissionRuleListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "commission/commissionRuleList";
    }

    @RequestMapping("commissionRuleToAdd")
    public String commissionRuleToAdd(Model model, CommissionRuleVO commissionRuleVO) {
    	model.addAttribute("userGroupType", CommonConstant.USER_GROUP_TYPE_STAFF);
    	model.addAttribute("userGroupModule", CommonConstant.USER_GROUP_MODULE_COMMISSION);
        return "commission/commissionRuleAdd";
    }

    @RequestMapping("commissionRuleAddAttribute")
    public String commissionRuleAddAttribute(Model model, CommissionRuleVO commissionRuleVO) {
        CommissionTemplate commissionTemplate = commissionTemplateService.get(commissionRuleVO.getCommissionTemplateId());
        commissionRuleVO.setCommissionTemplate(commissionTemplate);
        model.addAttribute("commissionRuleVO", commissionRuleVO);
        CommissionAttributeVO[] attributeVOs = commissionTemplate.getCommissionAttributeKeys().stream().map(attribute -> {
            CommissionAttributeVO attributeVO = new CommissionAttributeVO();
            attributeVO.setCommissionAttributeKeyId(attribute.getId());
            attributeVO.setReference(attribute.getReference());
            attributeVO.setName(attribute.getName());
            attributeVO.setDescription(attribute.getDescription());
            attributeVO.setCommissionAttributeKeyId(attribute.getId());
            return attributeVO;
        }).toArray(size -> new CommissionAttributeVO[size]);
        commissionRuleVO.setCommissionAttributeVOs(attributeVOs);
        if (commissionRuleVO.getId() != null) {
            CommissionRule commissionRule = commissionRuleService.get(commissionRuleVO.getId());
            Set<CommissionAttribute> attributeSet = commissionRule.getCommissionAttributes();
            for (CommissionAttributeVO attributeVO : attributeVOs) {
                for (CommissionAttribute attribute : attributeSet) {
                    if (attribute.getCommissionAttributeKey().getId().equals(attributeVO.getCommissionAttributeKeyId())) {
                        attributeVO.setValue(attribute.getValue());
                        break;
                    }
                }
            }
        }
        return "commission/commissionRuleAddAttribute";
    }

    @RequestMapping("commissionRuleConfirm")
    public String commissionRuleConfirm(Model model, CommissionRuleVO commissionRuleVO) {
        commissionRuleVO.setCommissionTemplate(commissionTemplateService.get(commissionRuleVO.getCommissionTemplateId()));
        model.addAttribute("commissionRuleVO", commissionRuleVO);

        return "commission/commissionRuleConfirm";
    }

    @RequestMapping("commissionRuleSave")
    @ResponseBody
    public AjaxForm commissionRuleSave(@Valid CommissionRuleVO commissionRuleVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            commissionRuleVO.setCompany(WebThreadLocal.getCompany());
            commissionRuleVO.setCommissionTemplate(commissionTemplateService.get(commissionRuleVO.getCommissionTemplateId()));
            commissionRuleService.saveOrUpdate(commissionRuleVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    @RequestMapping("commissionRuleView")
    public String commissionRuleView(Model model, Long id) {
        CommissionRule commissionRule = commissionRuleService.get(id);
        model.addAttribute("commissionRule", commissionRule);
        return "commission/commissionRuleView";
    }

    @RequestMapping("commissionRuleToEdit")
    public String commissionRuleToEdit(Model model, CommissionRuleVO commissionRuleVO) {
        CommissionRule commissionRule = commissionRuleService.get(commissionRuleVO.getId());
        model.addAttribute("commissionRuleVO", commissionRuleVO);
        model.addAttribute("commissionRule", commissionRule);
        model.addAttribute("userGroupType", CommonConstant.USER_GROUP_TYPE_STAFF);
    	model.addAttribute("userGroupModule", CommonConstant.USER_GROUP_MODULE_COMMISSION);
    	
        return "commission/commissionRuleEdit";
    }

    @RequestMapping("commissionRuleRemove")
    @ResponseBody
    public AjaxForm commissionRuleRemove(Long id) {
    	CommissionRule commissionRule=commissionRuleService.get(id);
        if (commissionRule != null) {
        	try {
        		commissionRule.setIsActive(false);
        		commissionRuleService.saveOrUpdate(commissionRule);
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

