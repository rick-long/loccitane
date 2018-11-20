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
import org.spa.model.product.Brand;
import org.spa.utils.I18nUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.brand.BrandAddVO;
import org.spa.vo.brand.BrandEditVO;
import org.spa.vo.brand.BrandListVO;
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
 * Created by Ivy on 2016/01/16.
 */
@Controller
@RequestMapping("brand")
public class BrandController extends BaseController {

	
	@RequestMapping("toView")
	public String brandManagement(Model model,BrandListVO brandListVO) {
		
		return "brand/brandManagement";
	}
	
	@RequestMapping("list")
	public String brandList(Model model, BrandListVO brandListVO) {

		String name=brandListVO.getName();
		String isActive=brandListVO.getIsActive();

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Brand.class);

		if (StringUtils.isNotBlank(name)) {
			detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(isActive)) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
		}
		if(WebThreadLocal.getCompany() !=null){
			detachedCriteria.add(Restrictions.eq("company.id",WebThreadLocal.getCompany().getId()));
		}
		//page start
		//brandListVO.setPageSize(1);
		Page<Brand> brandPage = brandService.list(detachedCriteria, brandListVO.getPageNumber(), brandListVO.getPageSize());
		model.addAttribute("page", brandPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(brandListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "brand/brandList";
	}
	
	@RequestMapping("toAdd")
	public String toAddBrand(BrandAddVO brandAddVO) {
		return "brand/brandAdd";
	}
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addBrand(@Valid BrandAddVO brandAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			String name=brandAddVO.getName();
			Brand brand=new Brand();
			brand.setName(name);
			brand.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.BRAND_REF_PREFIX));
			brand.setForCashPackage(Boolean.valueOf(brandAddVO.getForCashPackage()));
			brand.setIsActive(true);
			brand.setCreated(new Date());
			brand.setCompany(WebThreadLocal.getCompany());
			brand.setCreatedBy(WebThreadLocal.getUser().getUsername());
			brandService.save(brand);
			
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
		}
	}
	@RequestMapping("toEdit")
	public String toEditBrand(BrandEditVO brandEditVO,Model model) {
		
		Brand brand=brandService.get(brandEditVO.getId());
		brandEditVO.setName(brand.getName());
		brandEditVO.setIsActive(String.valueOf(brand.isIsActive()));
		brandEditVO.setForCashPackage(brand.getForCashPackage());
		
		return "brand/brandEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editBrand(@Valid BrandEditVO brandEditVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			
			Brand brand=brandService.get(brandEditVO.getId());
			String name=brandEditVO.getName();
			String isActive=brandEditVO.getIsActive();
			
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				brand.setName(name);
				brand.setIsActive(Boolean.valueOf(isActive));
				brand.setForCashPackage(Boolean.valueOf(brandEditVO.getForCashPackage()));
				brand.setLastUpdated(new Date());
				brand.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
				brandService.update(brand);
				
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
			}
		}
	}
	
	@RequestMapping("suitabledPackageTypes")
    public String suitabledPackageTypes(Model model, String prepaidType,String packageTypeInit) {
    	if(prepaidType.equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)){
    		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Brand.class);
    		
    		detachedCriteria.add(Restrictions.eq("isActive", true));
    		detachedCriteria.add(Restrictions.eq("forCashPackage", true));
    		detachedCriteria.add(Restrictions.eq("company.id",WebThreadLocal.getCompany().getId()));
    		
    		List<Brand> suitabledPackageTypes=brandService.list(detachedCriteria);
    		model.addAttribute("suitabledPackageTypes", suitabledPackageTypes);
    		model.addAttribute("packageTypeInit", packageTypeInit);
    		
    	}
        return "brand/suitabledPackageTypes";
    }
	
	@RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
    	Brand brand=brandService.get(id);
        if (brand != null) {
        	try {
        		brand.setIsActive(false);
        		brandService.saveOrUpdate(brand);
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
