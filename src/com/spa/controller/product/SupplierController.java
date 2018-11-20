package com.spa.controller.product;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.product.Supplier;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.supplier.SupplierListVO;
import org.spa.vo.supplier.SupplierVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
@RequestMapping("supplier")
public class SupplierController extends BaseController {
	
	@RequestMapping("toView")
	public String supplierManagement(Model model, SupplierListVO supplierListVO) {
		
		return "supplier/supplierManagement";
	}
	
	@RequestMapping("list")
	public String supplierList(Model model, SupplierListVO supplierListVO) {

		String name=supplierListVO.getName();
		String isActive=supplierListVO.getIsActive();

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Supplier.class);

		if (StringUtils.isNotBlank(name)) {
			detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(isActive)) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
		}
		if(WebThreadLocal.getCompany() !=null){
			detachedCriteria.add(Restrictions.eq("company.id",WebThreadLocal.getCompany().getId()));
		}
		//page start
		Page<Supplier> supplierPage = supplierService.list(detachedCriteria, supplierListVO.getPageNumber(),supplierListVO.getPageSize());
		model.addAttribute("page", supplierPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(supplierListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "supplier/supplierList";
	}
	
	@RequestMapping("toAdd")
	public String toAddSupplier(SupplierVO supplierAddVO) {
		return "supplier/supplierAdd";
	}
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addSupplier(@Valid SupplierVO supplierVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			
			supplierService.saveOrUpdateSupplier(supplierVO);
			
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
		}
		
	}
	@RequestMapping("toEdit")
	public String toEditSupplier(SupplierVO supplierVO,Model model) {
		Supplier supplier=supplierService.get(supplierVO.getId());
		model.addAttribute("supplier", supplier);
		return "supplier/supplierEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editSupplier(@Valid SupplierVO supplierVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			
			supplierService.saveOrUpdateSupplier(supplierVO);
			
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
		}
	}
	
	@RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
		Supplier supplier=supplierService.get(id);
        if (supplier != null) {
        	try {
        		supplier.setIsActive(false);
        		supplierService.saveOrUpdate(supplier);
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
