package com.spa.controller.product;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.product.Brand;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductDescriptionKey;
import org.spa.model.product.ProductOptionKey;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.page.Page;
import org.spa.vo.product.ProductAddVO;
import org.spa.vo.product.ProductEditVO;
import org.spa.vo.product.ProductListVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/04/16.
 */
@Controller
@RequestMapping("hairsalon")
public class HairSalonController extends BaseController {
	
    @RequestMapping("toView")
    public String hairsalonManagement(Model model, ProductListVO hairsalonListVO) {
        return "hairsalon/hairsalonManagement";
    }

    @RequestMapping("list")
    public String hairsalonList(Model model, ProductListVO hairsalonListVO) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);

        String name = hairsalonListVO.getName();
        String isActive = hairsalonListVO.getIsActive();
        
        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
        }

        Long categoryId = hairsalonListVO.getCategoryId();
        if (categoryId == null) {
            Category category=categoryService.get("reference", CommonConstant.CATEGORY_REF_HAIRSALON);
            categoryId = category.getId();
        }


        List<Long> allChildren = new ArrayList<>();
        allChildren.add(categoryId);
        categoryService.getAllChildrenByCategory(allChildren, categoryId);
        if(allChildren.size() > 0) {
            detachedCriteria.add(Restrictions.in("category.id", allChildren));
        }

        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        //page start
        Page<Product> hairsalonPage = productService.list(detachedCriteria, hairsalonListVO.getPageNumber(), hairsalonListVO.getPageSize());
        model.addAttribute("page", hairsalonPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(hairsalonListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "hairsalon/hairsalonList";
    }

    @RequestMapping("toAdd")
    public String toAddProduct(Model model, ProductAddVO hairsalonAddVO) {

        //set default brand to be selected
        Brand defaultBrand = brandService.get("reference", CommonConstant.DEFAULT_BRAND_REF);
        hairsalonAddVO.setBrandId(defaultBrand.getId());

        return "hairsalon/hairsalonAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm addProduct(@Valid ProductAddVO hairsalonAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Product hairsalon = null;
            try {
                hairsalon = productService.saveProduct(hairsalonAddVO);
            } catch (Exception e) {
                AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.product.option"));
            }
            AjaxForm successAjaxForm = AjaxFormHelper.success();
            successAjaxForm.addErrorFields(new ErrorField("hairsalonId", hairsalon.getId().toString()));
            successAjaxForm.addErrorFields(new ErrorField("success", I18nUtil.getMessageKey("label.add.successfully")));
            return successAjaxForm;
        }
    }

    @RequestMapping("toEdit")
    public String toEditProduct(Model model, ProductEditVO hairsalonEditVO) {

        //set default brand to be selected
        Long id = hairsalonEditVO.getId();
        if (id != null && id > 0) {
            Product hairsalon = productService.get(id);
            hairsalonEditVO.setBrandId(hairsalon.getBrand().getId());

            hairsalonEditVO.setName(hairsalon.getName());

            hairsalonEditVO.setCategoryId(hairsalon.getCategory().getId());

            hairsalonEditVO.setId(hairsalon.getId());

        }
        return "hairsalon/hairsalonEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm editProduct(@Valid ProductEditVO hairsalonEditVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Product hairsalon = null;
            try {
                hairsalon = productService.updateProduct(hairsalonEditVO);
            } catch (Exception e) {
                AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.product.option"));
            }
            AjaxForm successAjaxForm = AjaxFormHelper.success();
            successAjaxForm.addErrorFields(new ErrorField("hairsalonId", hairsalon.getId().toString()));
            successAjaxForm.addErrorFields(new ErrorField("success", I18nUtil.getMessageKey("label.add.successfully")));
            return successAjaxForm;
        }
    }

    @RequestMapping("addPdkeyAndPokey")
    public String addPdkeyAndPokey(ProductAddVO hairsalonAddVO, Model model) {
        Long companyId = WebThreadLocal.getCompany().getId();
        Long categoryId = hairsalonAddVO.getCategoryId();
       
        List<ProductDescriptionKey> pdkList = productDescriptionKeyService.getPdKeysByCategoryIdAndCompanyId(categoryId, companyId);
        List<ProductOptionKey> pokList= productOptionKeyService.getPoKeysByCategoryIdAndCompanyId(categoryId, companyId);
      
        model.addAttribute("pdkList", pdkList);
        model.addAttribute("pokList", pokList);
        
        return "hairsalon/addPdkeyAndPokey";
    }

    @RequestMapping("editPdkeyAndPokey")
    public String editPdkeyAndPokey(ProductEditVO hairsalonEditVO, Model model) {
        Long companyId = WebThreadLocal.getCompany().getId();
        Long categoryId = hairsalonEditVO.getCategoryId();
        
        List<ProductDescriptionKey> pdkList = productDescriptionKeyService.getPdKeysByCategoryIdAndCompanyId(categoryId, companyId);
        List<ProductOptionKey> pokList= productOptionKeyService.getPoKeysByCategoryIdAndCompanyId(categoryId, companyId);
      
        model.addAttribute("pdkList", pdkList);
        model.addAttribute("pokList", pokList);
        
        Product hairsalon = productService.get(hairsalonEditVO.getId());
        model.addAttribute("product", hairsalon);
        return "hairsalon/editPdkeyAndPokey";
    }

    @RequestMapping("hairsalonConfirm")
    public String hairsalonConfirm(ProductAddVO hairsalonAddVO, Model model) {
        Category category = categoryService.get(hairsalonAddVO.getCategoryId());
        model.addAttribute("category", category);
        
        addPdkeyAndPokey(hairsalonAddVO, model);
        model.addAttribute("productVO", hairsalonAddVO);
        return "hairsalon/hairsalonConfirm";
    }

    @RequestMapping("editConfirm")
    public String editConfirm(ProductEditVO hairsalonEditVO, Model model) {
        Category category = categoryService.get(hairsalonEditVO.getCategoryId());

        model.addAttribute("category", category);

        editPdkeyAndPokey(hairsalonEditVO, model);
        model.addAttribute("productVO", hairsalonEditVO);
        return "hairsalon/hairsalonConfirm";
    }
}
