package com.spa.controller.product;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.company.Company;
import org.spa.model.product.*;
import org.spa.model.shop.Shop;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.page.Page;
import org.spa.vo.product.*;
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
@RequestMapping("product")
public class ProductController extends BaseController {
	
    @RequestMapping("toView")
    public String productManagement(Model model, ProductListVO productListVO) {
    	
        initSelectOptions(model);

        return "product/productManagement";
    }

    @RequestMapping("list")
    public String productList(Model model, ProductListVO productListVO) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);

        String name = productListVO.getName();
        String isActive = productListVO.getIsActive();
        
        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
       
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
        }
        
        Long supplierId = productListVO.getSupplierId();
        if (supplierId !=null && supplierId.longValue()>0) {
        	detachedCriteria.createAlias("suppliers", "supplier");
            detachedCriteria.add(Restrictions.eq("supplier.id", supplierId));
        }
        Long brandId = productListVO.getBrandId();
        if (brandId !=null && brandId.longValue()>0) {
            detachedCriteria.add(Restrictions.eq("brand.id",brandId));
        }

        Long categoryId = productListVO.getCategoryId();
        if (categoryId == null) {
            Category category = categoryService.get("reference", CommonConstant.CATEGORY_REF_GOODS);
            categoryId = category.getId();
        }

        List<Long> allChildren = new ArrayList<>();
        allChildren.add(categoryId);
        categoryService.getAllChildrenByCategory(allChildren, categoryId);
        if (allChildren.size() > 0) {
            detachedCriteria.add(Restrictions.in("category.id", allChildren));
        }

        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }

        if (StringUtils.isNotBlank(productListVO.getBarcode())) {
            detachedCriteria.createAlias("productOptions", "p").add(Restrictions.eq("p.barcode", productListVO.getBarcode()));
        }

        //page start
        Page<Product> productPage = productService.list(detachedCriteria, productListVO.getPageNumber(), productListVO.getPageSize());
        model.addAttribute("page", productPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(productListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "product/productList";
    }

    @RequestMapping("toAdd")
    public String toAddProduct(Model model, ProductAddVO productAddVO) {

        //init select options
        initSelectOptions(model);

        //set default brand to be selected
        Brand defaultBrand = brandService.get("reference", CommonConstant.DEFAULT_BRAND_REF);
        productAddVO.setBrandId(defaultBrand.getId());

        return "product/productAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm addProduct(@Valid ProductAddVO productAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
        	if (productAddVO.getPoItemList() != null && productAddVO.getPoItemList().size() > 0) {
                for (PoItemVO pi : productAddVO.getPoItemList()) {
                	ProductOptionKey pok = null;
                    String poValueInForm = null;
                    for (KeyAndValueVO kv : pi.getPoValues()) {
                    	if(StringUtils.isBlank(kv.getValue())){
                    		continue;
                    	}
                        pok = productOptionKeyService.get(Long.valueOf(kv.getKey()));
                        if ("code".equalsIgnoreCase(pok.getReference())) {
                        	poValueInForm = kv.getValue();
                            break;
                        }
                    }
                    if(poValueInForm !=null){//code
                    	List<ProductOptionAttribute> poaList = productOptionAttributeService.getProductOptionAttributesByValue(poValueInForm,CommonConstant.CATEGORY_REF_GOODS);
                    	if(poaList !=null && poaList.size()>0){
                    		return AjaxFormHelper.error(I18nUtil.getMessageKey("label.product.code.dumplicate"));
                    	}
                    }
                }
            }
            Product product = null;
            try {
                product = productService.saveProduct(productAddVO);
            } catch (Exception e) {
                AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.product.option"));
            }
            AjaxForm successAjaxForm = AjaxFormHelper.success();
            successAjaxForm.addErrorFields(new ErrorField("productId", product.getId().toString()));
            successAjaxForm.addErrorFields(new ErrorField("success", I18nUtil.getMessageKey("label.add.successfully")));
            return successAjaxForm;
        }
    }

    @RequestMapping("toEdit")
    public String toEditProduct(Model model, ProductEditVO productEditVO) {

        //init select options
        initSelectOptions(model);

        //set default brand to be selected
        Long id = productEditVO.getId();
        if (id != null && id > 0) {
            Product product = productService.get(id);
            productEditVO.setBrandId(product.getBrand().getId());
            productEditVO.setName(product.getName());
            productEditVO.setCategoryId(product.getCategory().getId());
            productEditVO.setId(product.getId());
            productEditVO.setActive(product.isIsActive());
            model.addAttribute("productEditVO",productEditVO);
        }
        return "product/productEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm editProduct(@Valid ProductEditVO productEditVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
        	
            if (productEditVO.getPoItemList() != null && productEditVO.getPoItemList().size() > 0) {
                for (PoItemVO pi : productEditVO.getPoItemList()) {
                	ProductOptionKey pok = null;
                    String poValueInForm = null;
                    Long poaIdInForm=null;
                    for (KeyAndValueVO kv : pi.getPoValues()) {
                       
                        if(StringUtils.isBlank(kv.getValue())){
                    		continue;
                    	}
                        pok = productOptionKeyService.get(Long.valueOf(kv.getKey()));
                        if ("code".equalsIgnoreCase(pok.getReference())) {
                        	poValueInForm = kv.getValue();
                            poaIdInForm=kv.getId();
                            break;
                        }
                    }
                    if(poaIdInForm !=null){//code
                    	List<ProductOptionAttribute> poaList = productOptionAttributeService.getProductOptionAttributesByValue(poValueInForm,CommonConstant.CATEGORY_REF_GOODS);
                    	if(poaList !=null && poaList.size()>=2){
                    		return AjaxFormHelper.error(I18nUtil.getMessageKey("label.product.code.dumplicate"));
                    	}else if(poaList !=null && poaList.size()==1){
                    		ProductOptionAttribute poa  = poaList.get(0);
                            if(poa.getId().longValue() != poaIdInForm.longValue() ){
                            	return AjaxFormHelper.error(I18nUtil.getMessageKey("label.product.code.dumplicate"));
                            }
                    	}
                    }
                    
                }
            }
            Product product = null;
            try {
                product = productService.updateProduct(productEditVO);
            } catch (Exception e) {
                AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.product.option"));
            }
            AjaxForm successAjaxForm = AjaxFormHelper.success();
            successAjaxForm.addErrorFields(new ErrorField("productId", product.getId().toString()));
            successAjaxForm.addErrorFields(new ErrorField("success", I18nUtil.getMessageKey("label.add.successfully")));
            return successAjaxForm;
        }
    }

    private void initSelectOptions(Model model) {
        //brand list
        model.addAttribute("brandList", brandService.getBrandsSortBy(WebThreadLocal.getCompany().getId(), true, "name"));

        //supplier list
        DetachedCriteria supplierDC = DetachedCriteria.forClass(Supplier.class);
        List<Supplier> supplierList = supplierService.getActiveListByRefAndCompany(supplierDC, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("supplierList", supplierList);
    }

    @RequestMapping("addPdkeyAndPokey")
    public String addPdkeyAndPokey(ProductAddVO productAddVO, Model model) {
        Long companyId = WebThreadLocal.getCompany().getId();
        Long categoryId = productAddVO.getCategoryId();
       
        List<ProductDescriptionKey> pdkList = productDescriptionKeyService.getPdKeysByCategoryIdAndCompanyId(categoryId, companyId);
        List<ProductOptionKey> pokList= productOptionKeyService.getPoKeysByCategoryIdAndCompanyId(categoryId, companyId);
        
        model.addAttribute("pdkList", pdkList);
        model.addAttribute("pokList", pokList);
        model.addAttribute("pname", productAddVO.getName());
        
        return "product/addPdkeyAndPokey";
    }

    @RequestMapping("editPdkeyAndPokey")
    public String editPdkeyAndPokey(ProductEditVO productEditVO, Model model) {
        Long companyId = WebThreadLocal.getCompany().getId();
        Long categoryId = productEditVO.getCategoryId();
        
        List<ProductDescriptionKey> pdkList = productDescriptionKeyService.getPdKeysByCategoryIdAndCompanyId(categoryId, companyId);
        List<ProductOptionKey> pokList= productOptionKeyService.getPoKeysByCategoryIdAndCompanyId(categoryId, companyId);
        if(pokList != null || pokList.size() > 0){
            Collections.sort(pokList, new Comparator<ProductOptionKey>() {
                @Override
                public int compare(ProductOptionKey o1, ProductOptionKey o2) {
                    if(o1.getId() > o2.getId()){
                        return -1;
                    }
                    if(o1.getId() < o2.getId()){
                        return 1;
                    }
                    return 0;
                }
            });
        }
        model.addAttribute("pdkList", pdkList);
        model.addAttribute("pokList", pokList);
        model.addAttribute("pname", productEditVO.getName());
        Product product = productService.get(productEditVO.getId());
        model.addAttribute("product", product);
        return "product/editPdkeyAndPokey";
    }

    @RequestMapping("productConfirm")
    public String productConfirm(ProductAddVO productAddVO, Model model) {

        Brand brand = brandService.get(productAddVO.getBrandId());
        Category category = categoryService.get(productAddVO.getCategoryId());

        model.addAttribute("brand", brand);
        model.addAttribute("category", category);
        
        addPdkeyAndPokey(productAddVO, model);
        model.addAttribute("productVO", productAddVO);
        return "product/productConfirm";
    }

    @RequestMapping("editConfirm")
    public String editConfirm(ProductEditVO productEditVO, Model model) {
        Brand brand = brandService.get(productEditVO.getBrandId());
        Category category = categoryService.get(productEditVO.getCategoryId());

        model.addAttribute("brand", brand);
        model.addAttribute("category", category);

        editPdkeyAndPokey(productEditVO, model);
        model.addAttribute("productVO", productEditVO);
        return "product/productConfirm";
    }

    //this is product select for drop down
    @RequestMapping("selectOptionJson")
    @ResponseBody
    public List<SelectOptionVO> selectOptionJson(ProductSelectOptionVO productSelectOptionVO) {
        List<ProductOption> productOption = productOptionService.findProductOptionLikeByCode(productSelectOptionVO.getProductName());
        if(productOption != null && productOption.size() > 0){
            List<Product> products = new ArrayList<>();
            List<String> strings = new ArrayList<>();
            for (ProductOption p : productOption){
                boolean contains = strings.contains(p.getId().toString());
                if(contains){
                    continue;
                }else {
                    strings.add(p.getId().toString());
                    products.add(p.getProduct());
                }
            }
            if(products.size() > 0) {
                List<SelectOptionVO> collect = products.stream().map(product -> new SelectOptionVO(product.getId(), product.getName())).collect(Collectors.toList());
                return collect;
            }
        }
        Long categoryId = productSelectOptionVO.getCategoryId() == null ? 1L : productSelectOptionVO.getCategoryId();
        List<Long> lowestCategories = new ArrayList<>();
    	categoryService.getLowestCategoriesByCategory(categoryId,lowestCategories);
    	
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
        Company company = WebThreadLocal.getCompany();
        if (company != null) {
            detachedCriteria.add(Restrictions.eq("company.id", company.getId()));
        }
        
        String productName = productSelectOptionVO.getProductName();
        if(StringUtils.isNoneBlank(productName)) {
            detachedCriteria.add(Restrictions.like("name", productName, MatchMode.ANYWHERE));
        }
        
        if(lowestCategories !=null && lowestCategories.size()>0){
        	detachedCriteria.add(Restrictions.in("category.id", lowestCategories));
        }
        
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.addOrder(Order.asc("id"));
        List<Product> productList = productService.list(detachedCriteria);

        List<SelectOptionVO> selectOptionVOList = productList.stream().map(product -> new SelectOptionVO(product.getId(), product.getName())).collect(Collectors.toList());
        return selectOptionVOList;
    }
    
    @RequestMapping("checkInventory")
	@ResponseBody
	public Integer checkInventory(Long productOptionId,Long shopId) {
    	
		Integer qty= inventoryWarehouseService.getProductOptionQtyByShop(productOptionId, shopId);
		
		if(qty !=null){
			return qty;
		}else{
			return 0;
		}
	}
    
    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
    	Product product=productService.get(id);
        if (product != null) {
        	try {
        		product.setIsActive(false);
        		productService.saveOrUpdate(product);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }

    @RequestMapping("toQuickSearchProduct")
    public String toQuickSearchProduct(Model model, ProductListVO productListVO) {
        return "product/quickSearchProductManagement";
    }

    @RequestMapping("quickSearchProductList")
    public String quickSearchProductList(Model model, ProductListVO productListVO) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);

        String name = productListVO.getName();

        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
            detachedCriteria.add(Restrictions.eq("isActive",true));
        Long categoryId = productListVO.getCategoryId();

        if(categoryId != null){
            List<Long> allChildren = new ArrayList<>();
            allChildren.add(categoryId);
            categoryService.getAllChildrenByCategory(allChildren, categoryId);
            if (allChildren.size() > 0) {
                detachedCriteria.add(Restrictions.in("category.id", allChildren));
            }
        }

        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        //page start
        Page<Product> productPage = productService.list(detachedCriteria, productListVO.getPageNumber(), productListVO.getPageSize());
        model.addAttribute("page", productPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(productListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "product/quickSearchProductList";
    }


}
