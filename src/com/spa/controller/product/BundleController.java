package com.spa.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.bundle.ProductBundle;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOption;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.bundle.BundleItemVO;
import org.spa.vo.bundle.BundleListVO;
import org.spa.vo.bundle.BundleVO;
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
import java.util.Map;

@Controller
@RequestMapping("bundle")
public class BundleController extends BaseController {


    @RequestMapping("toBundleView")
    public String toBundleView(Model model, BundleListVO bundleListVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("bundleListVO", bundleListVO);

        model.addAttribute("toDate",new Date());
        model.addAttribute("fromDate",new Date());

        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("subCategoryList", subCategoryList);

        return "bundle/bundleManagement";
    }


    @RequestMapping("bundleList")
    public String bundleList(Model model, BundleListVO bundleListVO) {
        
        String isActive = bundleListVO.getIsActive();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductBundle.class);
       
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }

        if (StringUtils.isNotBlank(bundleListVO.getStartTime())) {
            detachedCriteria.add(Restrictions.ge("startTime",
                    DateUtil.stringToDate(bundleListVO.getStartTime() + " 00:00:00", "yyyy-MM-dd HH:mm:ss")));
        }

        if (StringUtils.isNotBlank(bundleListVO.getEndTime())) {
            detachedCriteria.add(Restrictions.le("endTime",
                    DateUtil.stringToDate(bundleListVO.getEndTime() + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }

        if (StringUtils.isNotBlank(bundleListVO.getCode())) {
            detachedCriteria.add(Restrictions.like("code", bundleListVO.getCode(), MatchMode.ANYWHERE));
        }

        Page<ProductBundle> page = bundleService.list(detachedCriteria, bundleListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(bundleListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "bundle/bundleList";

    }

    @RequestMapping("bundleToAdd")
    public String bundleToAdd(Model model, BundleVO bundleVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("bundleVO", bundleVO);
        Date currentTime = new Date();
        model.addAttribute("currentTime",currentTime);
        
        int bundleItemsMax=PropertiesUtil.getIntegerValueByName(CommonConstant.BUNDLE_ITEM_MAX);
		model.addAttribute("bundleItemsMax",bundleItemsMax-1);
        return "bundle/bundleAdd";
    }
    @RequestMapping("bundleConfirm")
    public String bundleConfirm(Model model, BundleVO bundleVO) {
		model.addAttribute("bundleVO",bundleVO);
		Map<Integer,List<ProductOption>> confirmProductOptions = bundleVO.getConfirmProductOptions();
		
		List<BundleItemVO> bundleItems = bundleVO.getBundleItems();
        if(bundleItems != null && bundleItems.size() > 0) {
            for (BundleItemVO itemVo : bundleItems) {
            	if(itemVo.getProductOptionIds() ==null){
            		continue;
            	}
            	List<ProductOption> productOptions = confirmProductOptions.get(itemVo.getGroup());
            	if(productOptions ==null){
            		productOptions = new ArrayList<ProductOption>();
            		confirmProductOptions.put(itemVo.getGroup(), productOptions);
            	}
            	for(Long productOptionId : itemVo.getProductOptionIds()){
            		productOptions.add(productOptionService.get(productOptionId));
            	}
            }
        }
        model.addAttribute("confirmProductOptions",confirmProductOptions);
        return "bundle/bundleConfirm";
    }

    @RequestMapping("bundleSave")
    @ResponseBody
    public AjaxForm bundleSave(@Valid BundleVO bundleVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            bundleVO.setCompanyId(WebThreadLocal.getCompany().getId());
            bundleService.saveOrUpdate(bundleVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }


    @RequestMapping("bundleEdit")
    public String bundleEdit(Model model, BundleVO bundleVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        ProductBundle productBundle = bundleService.get(bundleVO.getId());
        
        model.addAttribute("productBundle", productBundle);
        
        int bundleItemsMax=PropertiesUtil.getIntegerValueByName(CommonConstant.BUNDLE_ITEM_MAX);
        model.addAttribute("bundleItemsMax",bundleItemsMax-1);
		
        return "bundle/bundleEdit";
    }


    @RequestMapping("bundleRemove")
    @ResponseBody
    public AjaxForm bundleRemove(Long id) {
        ProductBundle productBundle=bundleService.get(id);
        if (productBundle != null) {
            try {
                productBundle.setIsActive(false);
                bundleService.saveOrUpdate(productBundle);
            } catch (Exception e) {
                e.printStackTrace();
                return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
            }
        }else{
            return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }
    
    @RequestMapping("bundleSelections")
    public String bundleSelections(Model model, Long bundleId) {
    	if(bundleId !=null){
    		 ProductBundle productBundle = bundleService.get(bundleId);
    	     model.addAttribute("productBundle", productBundle);
    	}
       
        return "bundle/bundleSelections";
    }
}
