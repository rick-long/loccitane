package com.spa.controller.product;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOptionKey;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.page.Page;
import org.spa.vo.pokey.PoKeyAddVO;
import org.spa.vo.pokey.PoKeyEditVO;
import org.spa.vo.pokey.PoKeyListVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
@RequestMapping("pokey")
public class PoKeyController extends BaseController {
	
	@RequestMapping("toView")
	public String poKeyManagement(Model model,PoKeyListVO poKeyListVO) {
				
		return "pokey/pokeyManagement";
	}
	
	
	@RequestMapping("list")
	public String poKeyList(Model model,PoKeyListVO poKeyListVO) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductOptionKey.class);

		String name=poKeyListVO.getName();
		if (StringUtils.isNotBlank(name)) {
			detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		String isActive=poKeyListVO.getIsActive();
		if (StringUtils.isNotBlank(isActive)) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
		}
		if(WebThreadLocal.getCompany() !=null){
			detachedCriteria.add(Restrictions.eq("company.id",WebThreadLocal.getCompany().getId()));
		}
		Long categoryId=poKeyListVO.getCategoryId();
		if(categoryId !=null && categoryId.longValue()>0){
			//choose parent category
			detachedCriteria.createAlias("categories", "pokeycategory");
			detachedCriteria.add(Restrictions.eq("pokeycategory.id", categoryId));
		}
		
		//page start
		Page<ProductOptionKey> poAttrKeyPage = productOptionKeyService.list(detachedCriteria, poKeyListVO.getPageNumber(), poKeyListVO.getPageSize());
		model.addAttribute("page", poAttrKeyPage);
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(poKeyListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "pokey/pokeyList";
	}
	
	@RequestMapping("toAdd")
	public String toAddPoKey(Model model,PoKeyAddVO poKeyAddVO) {

		List<Category> parentCategoryList=categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT,WebThreadLocal.getCompany().getId());
		//set the first category selected
		if(parentCategoryList !=null && parentCategoryList.size()>0){
			poKeyAddVO.setCategoryId(parentCategoryList.get(0).getId());
		}
		model.addAttribute("parentCategoryList", parentCategoryList);
		
		return "pokey/pokeyAdd";
	}
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addPoKey(@Valid PoKeyAddVO poKeyAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			String name=poKeyAddVO.getName();
			String reference=poKeyAddVO.getReference();
			
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			Long categoryId=poKeyAddVO.getCategoryId();
			
			List<ProductOptionKey> pokList=productOptionKeyService.getPOKListOByRefAndCategoryId(reference, categoryId, WebThreadLocal.getCompany().getId());
			if (pokList !=null && pokList.size()>0) {
				errorAjaxForm.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
			}
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				String uiType=poKeyAddVO.getUiType();
				String initOption=poKeyAddVO.getInitOption();
				
				ProductOptionKey pokey=new ProductOptionKey();
				pokey.setName(name);
				pokey.setReference(poKeyAddVO.getReference());
				pokey.setUiType(uiType);
				pokey.setInitOption(initOption);
				pokey.setDefaultValue(poKeyAddVO.getDefaultValue());
				pokey.setIsActive(true);
                pokey.setUnit(poKeyAddVO.getUnit());
                pokey.setLabelShow(Boolean.parseBoolean(poKeyAddVO.getLabelShow()));
                
				Category category=null;
				if(categoryId !=null && categoryId.longValue()>0){
					category=categoryService.get(categoryId);
				}
				pokey.getCategories().add(category);
				pokey.setCompany(WebThreadLocal.getCompany());
				
				pokey.setCreatedBy(WebThreadLocal.getUser().getUsername());
				pokey.setCreated(new Date());
				productOptionKeyService.saveOrUpdate(pokey);
				
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			}
		}
	}
	@RequestMapping("toEdit")
	public String toEditPoKey(Model model,PoKeyEditVO poKeyEditVO) {
		
		ProductOptionKey pokey=productOptionKeyService.get(poKeyEditVO.getId());
		poKeyEditVO.setName(pokey.getName());
		poKeyEditVO.setUiType(pokey.getUiType());
		poKeyEditVO.setInitOption(pokey.getInitOption());
		poKeyEditVO.setIsActive(String.valueOf(pokey.isIsActive()));
		poKeyEditVO.setDefaultValue(pokey.getDefaultValue());
        poKeyEditVO.setUnit(pokey.getUnit());
        poKeyEditVO.setLabelShow(pokey.getLabelShow().toString());
        
        Category currCategory=categoryService.get(pokey.getCategories().iterator().next().getId());
        poKeyEditVO.setCategoryId(currCategory.getId());
		List<Category> parentCategoryList=categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT,WebThreadLocal.getCompany().getId());
		
		model.addAttribute("parentCategoryList", parentCategoryList);
		return "pokey/pokeyEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editPoKey(@Valid PoKeyEditVO poKeyEditVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			ProductOptionKey pokey=productOptionKeyService.get(poKeyEditVO.getId());
			
			String name=poKeyEditVO.getName();
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				
				String uiType=poKeyEditVO.getUiType();
				String initOption=poKeyEditVO.getInitOption();
				
				pokey.setName(name);
				pokey.setUiType(uiType);
				pokey.setInitOption(initOption);
				pokey.setIsActive(Boolean.valueOf(poKeyEditVO.getIsActive()));
				pokey.setDefaultValue(poKeyEditVO.getDefaultValue());
                pokey.setUnit(poKeyEditVO.getUnit());
                pokey.setLabelShow(Boolean.parseBoolean(poKeyEditVO.getLabelShow()));
                
				Long categoryId=poKeyEditVO.getCategoryId();
				Category category=null;
				if(categoryId !=null && categoryId.longValue()>0){
					category=categoryService.get(categoryId);
				}
				pokey.getCategories().clear();
				pokey.getCategories().add(category);
				pokey.setCompany(WebThreadLocal.getCompany());
				
				productOptionKeyService.saveOrUpdate(pokey);
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
			}
		}
		
	}
	
}
