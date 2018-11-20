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
import org.spa.model.product.ProductDescriptionKey;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.page.Page;
import org.spa.vo.pdkey.PdKeyAddVO;
import org.spa.vo.pdkey.PdKeyEditVO;
import org.spa.vo.pdkey.PdKeyListVO;
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
@RequestMapping("pdkey")
public class ProductDescriptionKeyController extends BaseController {
	
	@RequestMapping("toView")
	public String pdKeyManagement(Model model,PdKeyListVO pdKeyListVO) {
				
		return "pdkey/pdkeyManagement";
	}
	
	@RequestMapping("list")
	public String pdKeyList(Model model,PdKeyListVO pdKeyListVO) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductDescriptionKey.class);

		String name=pdKeyListVO.getName();
		if (StringUtils.isNotBlank(name)) {
			detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		String isActive=pdKeyListVO.getIsActive();
		if (StringUtils.isNotBlank(isActive)) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
		}
		if(WebThreadLocal.getCompany() !=null){
			detachedCriteria.add(Restrictions.eq("company.id",WebThreadLocal.getCompany().getId()));
		}
		Long categoryId=pdKeyListVO.getCategoryId();
		if(categoryId !=null && categoryId.longValue()>0){
			//choose parent category
			detachedCriteria.createAlias("categories", "pokeycategory");
			detachedCriteria.add(Restrictions.eq("pokeycategory.id", categoryId));
		}
		//page start
		Page<ProductDescriptionKey> poAttrKeyPage = productDescriptionKeyService.list(detachedCriteria, pdKeyListVO.getPageNumber(), pdKeyListVO.getPageSize());
		model.addAttribute("page", poAttrKeyPage);
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(pdKeyListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "pdkey/pdkeyList";
	}
	
	@RequestMapping("toAdd")
	public String toAddPdKey(Model model,PdKeyAddVO pdKeyAddVO) {
		List<Category> parentCategoryList=categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT,WebThreadLocal.getCompany().getId());
		//set the first category selected
		if(parentCategoryList !=null && parentCategoryList.size()>0){
			pdKeyAddVO.setCategoryId(parentCategoryList.get(0).getId());
		}
		model.addAttribute("parentCategoryList", parentCategoryList);
		
		return "pdkey/pdkeyAdd";
	}
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addPdKey(@Valid PdKeyAddVO pdKeyAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			String name=pdKeyAddVO.getName();
			String reference=pdKeyAddVO.getReference();
			
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			Long categoryId=pdKeyAddVO.getCategoryId();
			List<ProductDescriptionKey> pdKeys=productDescriptionKeyService.getPDKListByRefAndCategoryId(reference, categoryId, WebThreadLocal.getCompany().getId());
			if (pdKeys !=null && pdKeys.size()>0) {
				errorAjaxForm.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
			}
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				String uiType=pdKeyAddVO.getUiType();
				String initOption=pdKeyAddVO.getInitOption();
				
				ProductDescriptionKey pdkey=new ProductDescriptionKey();
				pdkey.setName(name);
				pdkey.setReference(pdKeyAddVO.getReference());
				pdkey.setUiType(uiType);
				pdkey.setInitOption(initOption);
				pdkey.setIsActive(true);
				pdkey.setDefaultValue(pdKeyAddVO.getDefaultValue());
				
				Category category=null;
				if(categoryId !=null && categoryId.longValue()>0){
					category=categoryService.get(categoryId);
				}
				pdkey.getCategories().add(category);
				
				pdkey.setCompany(WebThreadLocal.getCompany());
				pdkey.setCreatedBy(WebThreadLocal.getUser().getUsername());
				pdkey.setCreated(new Date());
				productDescriptionKeyService.saveOrUpdate(pdkey);
				
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			}
		}
	}
	@RequestMapping("toEdit")
	public String toEditPdKey(Model model,PdKeyEditVO pdKeyEditVO) {
		
		ProductDescriptionKey pdkey=productDescriptionKeyService.get(pdKeyEditVO.getId());
		pdKeyEditVO.setName(pdkey.getName());
		pdKeyEditVO.setUiType(pdkey.getUiType());
		pdKeyEditVO.setInitOption(pdkey.getInitOption());
		pdKeyEditVO.setIsActive(String.valueOf(pdkey.isIsActive()));
		
		Category currCategory=categoryService.get(pdkey.getCategories().iterator().next().getId());
		pdKeyEditVO.setCategoryId(currCategory.getId());
		
		List<Category> parentCategoryList=categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT,WebThreadLocal.getCompany().getId());
		
		model.addAttribute("parentCategoryList", parentCategoryList);
		
		return "pdkey/pdkeyEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editPdKey(@Valid PdKeyEditVO pdKeyEditVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			ProductDescriptionKey pdkey=productDescriptionKeyService.get(pdKeyEditVO.getId());
			
			String name=pdKeyEditVO.getName();
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				
				String uiType=pdKeyEditVO.getUiType();
				String initOption=pdKeyEditVO.getInitOption();
				
				pdkey.setName(name);
				pdkey.setDefaultValue(pdKeyEditVO.getDefaultValue());
				pdkey.setUiType(uiType);
				pdkey.setInitOption(initOption);
				pdkey.setIsActive(Boolean.valueOf(pdKeyEditVO.getIsActive()));
				
				Long categoryId=pdKeyEditVO.getCategoryId();
				Category category=null;
				if(categoryId !=null && categoryId.longValue()>0){
					category=categoryService.get(categoryId);
				}
				pdkey.getCategories().clear();
				pdkey.getCategories().add(category);
				
				pdkey.setLastUpdated(new Date());
				pdkey.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
				
				productDescriptionKeyService.saveOrUpdate(pdkey);
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
			}
		}
		
	}
}
