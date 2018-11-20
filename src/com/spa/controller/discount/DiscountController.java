package com.spa.controller.discount;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.discount.DiscountAttribute;
import org.spa.model.discount.DiscountRule;
import org.spa.model.discount.DiscountTemplate;
import org.spa.model.product.Category;
import org.spa.model.shop.Shop;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.discount.DiscountRuleListVO;
import org.spa.vo.discount.DiscountAttributeVO;
import org.spa.vo.discount.DiscountRuleVO;
import org.spa.vo.discount.DiscountTemplateListVO;
import org.spa.vo.discount.DiscountTemplateVO;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivy on 2016-5-12
 */
@Controller
@RequestMapping("discount")
public class DiscountController extends BaseController {

    // discount template ------------------------------------------------------------------------

    @RequestMapping("toView")
    public String discountTemplateManagement(Model model) {
        DiscountTemplateListVO discountTemplateListVO = new DiscountTemplateListVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("discountTemplateListVO",discountTemplateListVO);
        return "discount/discountTemplateManagement";
    }

    @RequestMapping("discountTemplateList")
    public String discountTemplateList(Model model, DiscountTemplateListVO discountTemplateListVO) {
        Long shopId = discountTemplateListVO.getShopId();
        String name = discountTemplateListVO.getName();
        String isActive = discountTemplateListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DiscountTemplate.class);
        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        }
        if (StringUtils.isNoneBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        Page<DiscountTemplate> page = discountTemplateService.list(detachedCriteria, discountTemplateListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(discountTemplateListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "discount/discountTemplateList";
    }

    @RequestMapping("discountTemplateToAdd")
    public String discountTemplateToAdd(Model model, DiscountTemplateVO discountTemplateVO) {
        model.addAttribute("discountTemplateVO", discountTemplateVO);
        return "discount/discountTemplateAdd";
    }

    @RequestMapping("discountTemplateSave")
    @ResponseBody
    public AjaxForm discountTemplateSave(@Valid DiscountTemplateVO discountTemplateVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            discountTemplateVO.setCompany(WebThreadLocal.getCompany());
            discountTemplateService.saveOrUpdate(discountTemplateVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    @RequestMapping("discountTemplateConfirm")
    public String discountTemplateConfirm(Model model, DiscountTemplateVO discountTemplateVO) {
        model.addAttribute("discountTemplateVO", discountTemplateVO);
        return "discount/discountTemplateConfirm";
    }

    @RequestMapping("discountTemplateView")
    public String discountTemplateView(Model model, Long id) {
        DiscountTemplate discountTemplate = discountTemplateService.get(id);
        model.addAttribute("discountTemplate", discountTemplate);
        return "discount/discountTemplateView";
    }

    @RequestMapping("discountTemplateToEdit")
    public String discountTemplateToEdit(Model model, DiscountTemplateVO discountTemplateVO) {
        DiscountTemplate discountTemplate = discountTemplateService.get(discountTemplateVO.getId());
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("discountTemplateVO", discountTemplateVO);
        model.addAttribute("discountTemplate", discountTemplate);
        return "discount/discountTemplateEdit";
    }


    // discount rule -------------------------------------------------------------------------------------------

    @RequestMapping("toRuleView")
    public String discountRuleManagement(Model model, DiscountRuleListVO discounRuleListVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("discountRuleListVO", discounRuleListVO);
        
        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("subCategoryList", subCategoryList);
        
        return "discount/discountRuleManagement";
    }

    @RequestMapping("discountRuleList")
    public String discountRuleList(Model model, DiscountRuleListVO discounRuleListVO) {
        Long shopId = discounRuleListVO.getShopId();
        String isActive = discounRuleListVO.getIsActive();
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DiscountRule.class);
        if (shopId != null) {
        	detachedCriteria.createAlias("shops", "s");
            detachedCriteria.add(Restrictions.eq("s.id", shopId));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        
  		if (StringUtils.isNotBlank(discounRuleListVO.getStartTime())) {
  			detachedCriteria.add(Restrictions.ge("startTime", 
  					DateUtil.stringToDate(discounRuleListVO.getStartTime()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
  		}
  		
  		if (StringUtils.isNotBlank(discounRuleListVO.getEndTime())) {
  			detachedCriteria.add(Restrictions.le("endTime",
  					DateUtil.stringToDate(discounRuleListVO.getEndTime() +" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
  		}
  		
  		if (StringUtils.isNotBlank(discounRuleListVO.getDescription())) {
  			detachedCriteria.add(Restrictions.like("description", discounRuleListVO.getDescription(), MatchMode.ANYWHERE));
  		}
      	
  		if(discounRuleListVO.getProductId() !=null){
			detachedCriteria.createAlias("products", "p");
			detachedCriteria.add(Restrictions.eq("p.id", discounRuleListVO.getProductId()));
		}else if(discounRuleListVO.getCategoryId() !=null){
			List<Long> allChildren = new ArrayList<>();
	        allChildren.add(discounRuleListVO.getCategoryId());
	        categoryService.getAllChildrenByCategory(allChildren, discounRuleListVO.getCategoryId());
	        if (allChildren.size() > 0) {
	        	detachedCriteria.createAlias("categories", "c");
	            detachedCriteria.add(Restrictions.in("c.id", allChildren));
	        }
		}
        Page<DiscountRule> page = discountRuleService.list(detachedCriteria, discounRuleListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(discounRuleListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "discount/discountRuleList";
    }

    @RequestMapping("discountRuleToAdd")
    public String discountRuleToAdd(Model model, DiscountRuleVO discountRuleVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("discountRuleVO", discountRuleVO);
        Date now = new Date();
        discountRuleVO.setStartTime(now);
        discountRuleVO.setEndTime(now);

        DetachedCriteria criteria = DetachedCriteria.forClass(DiscountTemplate.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<DiscountTemplate> templateList = discountTemplateService.list(criteria);
        model.addAttribute("templateList", templateList);
        return "discount/discountRuleAdd";
    }

    @RequestMapping("discountRuleAddAttribute")
    public String discountRuleAddAttribute(Model model, DiscountRuleVO discountRuleVO) {
        DiscountTemplate discountTemplate = discountTemplateService.get(discountRuleVO.getDiscountTemplateId());
        discountRuleVO.setDiscountTemplate(discountTemplate);
        model.addAttribute("discountRuleVO", discountRuleVO);
        DiscountAttributeVO[] attributeVOs = discountTemplate.getDiscountAttributeKeys().stream().map(attribute -> {
            DiscountAttributeVO attributeVO = new DiscountAttributeVO();
            attributeVO.setDiscountAttributeKeyId(attribute.getId());
            attributeVO.setReference(attribute.getReference());
            attributeVO.setName(attribute.getName());
            attributeVO.setDescription(attribute.getDescription());
            attributeVO.setDiscountAttributeKeyId(attribute.getId());
            return attributeVO;
        }).toArray(size -> new DiscountAttributeVO[size]);
        discountRuleVO.setDiscountAttributeVOs(attributeVOs);
        if (discountRuleVO.getId() != null) {
            DiscountRule discountRule = discountRuleService.get(discountRuleVO.getId());
            Set<DiscountAttribute> attributeSet = discountRule.getDiscountAttributes();
            for (DiscountAttributeVO attributeVO : attributeVOs) {
                for (DiscountAttribute attribute : attributeSet) {
                    if (attribute.getDiscountAttributeKey().getId().equals(attributeVO.getDiscountAttributeKeyId())) {
                        attributeVO.setValue(attribute.getValue());
                        break;
                    }
                }
            }
        }
        return "discount/discountRuleAddAttribute";
    }

    @RequestMapping("discountRuleConfirm")
    public String discountRuleConfirm(Model model, DiscountRuleVO discountRuleVO) {
        List<Shop> shopList = new ArrayList<>();
        for(Long shopId : discountRuleVO.getShopIds()) {
            shopList.add(shopService.get(shopId));
        }
        discountRuleVO.setShopList(shopList);
        discountRuleVO.setDiscountTemplate(discountTemplateService.get(discountRuleVO.getDiscountTemplateId()));
        model.addAttribute("discountRuleVO", discountRuleVO);

        return "discount/discountRuleConfirm";
    }

    @RequestMapping("discountRuleSave")
    @ResponseBody
    public AjaxForm discountRuleSave(@Valid DiscountRuleVO discountRuleVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            discountRuleVO.setCompany(WebThreadLocal.getCompany());
            List<Shop> shopList = new ArrayList<>();
            for(Long shopId : discountRuleVO.getShopIds()) {
                shopList.add(shopService.get(shopId));
            }
            discountRuleVO.setShopList(shopList);
            discountRuleVO.setDiscountTemplate(discountTemplateService.get(discountRuleVO.getDiscountTemplateId()));
            discountRuleService.saveOrUpdate(discountRuleVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    @RequestMapping("discountRuleView")
    public String discountRuleView(Model model, Long id) {
        DiscountRule discountRule = discountRuleService.get(id);
        model.addAttribute("discountRule", discountRule);
        return "discount/discountRuleView";
    }

    @RequestMapping("discountRuleToEdit")
    public String discountRuleToEdit(Model model, DiscountRuleVO discountRuleVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        DiscountRule discountRule = discountRuleService.get(discountRuleVO.getId());
        model.addAttribute("discountRuleVO", discountRuleVO);
        model.addAttribute("discountRule", discountRule);
        return "discount/discountRuleEdit";
    }
    
    @RequestMapping("discountRuleRemove")
    @ResponseBody
    public AjaxForm discountRuleRemove(Long id) {
    	DiscountRule discountRule=discountRuleService.get(id);
        if (discountRule != null) {
        	try {
        		discountRule.setIsActive(false);
        		discountRuleService.saveOrUpdate(discountRule);
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