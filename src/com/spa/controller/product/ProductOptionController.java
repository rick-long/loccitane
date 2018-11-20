package com.spa.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.*;
import org.spa.model.company.Company;
import org.spa.model.product.*;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.I18nUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.Results;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.page.Page;
import org.spa.vo.product.AssignShopVO;
import org.spa.vo.product.ProductOptionListVO;
import org.spa.vo.product.ProductOptionQuickSearchVo;
import org.spa.vo.product.SupernumeraryPriceAddVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
@RequestMapping("po")
public class ProductOptionController extends BaseController {
	
	@RequestMapping("toAddSupernumeraryPrice")
	public String toAddSupernumeraryPrice(Model model,SupernumeraryPriceAddVO supernumeraryPriceAddVO) {
		
		Long poId=supernumeraryPriceAddVO.getProductOptionId();
		if(poId !=null && poId.longValue()>0){
			ProductOption po=productOptionService.get(poId);
			model.addAttribute("poRef", po.getReference());
			model.addAttribute("pname", po.getProduct().getName());
			
			ProductOptionAttribute poa=productOptionAttributeService.getPOAttrByPoIdAndKeyRefAndCompany(poId, CommonConstant.PRODUCT_OPTION_KEY_PRICE_REF, WebThreadLocal.getCompany().getId());
			supernumeraryPriceAddVO.setOriginalPrice(Double.valueOf(poa.getValue()));
			
			Map<Long,KeyAndValueVO> kvMap=new HashMap<>();
			
			Set pospSet=po.getProductOptionSupernumeraryPrices();
			if(pospSet !=null && pospSet.size()>0){
				
				Iterator<ProductOptionSupernumeraryPrice> sa_it=pospSet.iterator();
				while(sa_it.hasNext()){
					ProductOptionSupernumeraryPrice posp=sa_it.next();
					KeyAndValueVO kv=new KeyAndValueVO();
					kv.setKey(posp.getShop().getId().toString());
					kv.setValue(posp.getAdditionalPrice().toString());
					kv.setId(posp.getId());
					
					kvMap.put(posp.getShop().getId(), kv);

				}
			}
			model.addAttribute("kvMap", kvMap);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
		List<Shop> shopList=shopService.getActiveListByRefAndCompany(criteria, null, WebThreadLocal.getCompany().getId());
		model.addAttribute("shopList", shopList);
		
		return "productOption/supernumeraryPrice";
	}
    @RequestMapping("toProductSelectShop")
    public String toProductSelectShop(Model model,SupernumeraryPriceAddVO supernumeraryPriceAddVO) {
       Product product= productService.get(supernumeraryPriceAddVO.getProductId());
        DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
        List<Shop> shopList=shopService.getActiveListByRefAndCompany(criteria, null, WebThreadLocal.getCompany().getId());
        List<Long> shopIds=new ArrayList<>();
        for(Shop shop: product.getShops()){
            shopIds.add(shop.getId());
        }
        model.addAttribute("shopIds", shopIds);
        model.addAttribute("product", product);
        model.addAttribute("shopList", shopList);
        return "productOption/productSelectShop";
    }
    @RequestMapping("productSelectShopSave")
    @ResponseBody
    public AjaxForm productSelectShopSave(AssignShopVO assignShopVO) {
        try {
            productService.assignShops(assignShopVO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));

    }
	@RequestMapping("addSupernumeraryPrice")
	@ResponseBody
	public AjaxForm addSupernumeraryPrice(@Valid SupernumeraryPriceAddVO supernumeraryPriceAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			productOptionSupernumeraryPriceService.saveProductOptionSupernumeraryPrice(supernumeraryPriceAddVO);
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
		}
	}
	
	@RequestMapping("productOptionSelectOptions")
    public String productOptionSelectOptions(Model model, Long productId,Boolean showAll,String initialValue) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductOption.class);
        detachedCriteria.add(Restrictions.eq("product.id", productId));
        detachedCriteria.add(Restrictions.eq("isActive",true));
        
        List<ProductOption> productOptionList = productOptionService.list(detachedCriteria);
        model.addAttribute("productOptionList", productOptionList);
        if(showAll !=null && showAll){
        	model.addAttribute("showAll", showAll);
        }
        if(StringUtils.isNotBlank(initialValue)){
        	model.addAttribute("initialValue", initialValue);
        }
        return "productOption/productOptionSelectOptions";
    }

    @RequestMapping("selectOptionJson")
    @ResponseBody
    public List<SelectOptionVO> selectOptionJson(String productName,String prodType) {
        if("CA-TREATMENT".equals(prodType)){
            List<ProductOption> productOptions = productOptionService.findProductOptionLikeByCode(productName);
            if(productOptions != null && productOptions.size() >0){
                List<SelectOptionVO> collect = productOptions.stream().map(item -> new SelectOptionVO(item.getId(), item.getLabel33())).collect(Collectors.toList());
                return collect;
            }
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductOption.class);
        Company company = WebThreadLocal.getCompany();
        detachedCriteria.createAlias("product", "p");
        if (company != null) {
            detachedCriteria.add(Restrictions.eq("p.company.id", company.getId()));
        }
        if (StringUtils.isNoneBlank(productName)) {
            detachedCriteria.add(Restrictions.like("p.name", productName, MatchMode.ANYWHERE));
        }
        detachedCriteria.add(Restrictions.eq("p.isActive", true));
        if(StringUtils.isNoneBlank(prodType)){
        	detachedCriteria.add(Restrictions.eq("p.prodType", prodType));
        }
        
        detachedCriteria.addOrder(Order.asc("p.name"));
        List<ProductOption> list = productOptionService.list(detachedCriteria);

        List<SelectOptionVO> selectOptionVOList = list.stream().map(item -> new SelectOptionVO(item.getId(), item.getLabel33())).collect(Collectors.toList());
        return selectOptionVOList;
    }

    @RequestMapping({"quickSearch", "quickSearchForBook", "quickSearchForSingleBook", "quickSearchForInventory"})
    public String quickSearch(Model model, ProductOptionListVO productOptionListVO, HttpServletRequest request) {
        model.addAttribute("productOptionListVO", productOptionListVO);
        String url = request.getServletPath();
        return url.replace("po", "productOption");
    }


    @RequestMapping("quickSearchForInventoryList")
    public String quickSearchForInventoryList(Model model, ProductOptionListVO productOptionListVO, HttpServletRequest request) {
        String key = productOptionListVO.getKey();
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductOption.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.createAlias("product", "p");
        criteria.add(Restrictions.eq("p.company.id", WebThreadLocal.getCompany().getId()));
        
        criteria.createAlias("p.category", "c");
        criteria.createAlias("c.category", "pc");
        criteria.add(Restrictions.eq("pc.reference",CommonConstant.CATEGORY_REF_GOODS));
        
        Long supplierId = productOptionListVO.getSupplierId();
        if(supplierId != null) {
            criteria.add(Restrictions.eq("p.supplier.id", supplierId));
        }
        if (StringUtils.isNotBlank(key)) {
            criteria.createAlias("productOptionAttributes", "pa");
            Disjunction disjunction = Restrictions.disjunction();
            String[] keys = key.trim().split(" ");
            for (String keyValue : keys) {
                try {
                    Integer.parseInt(keyValue);
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                } catch (Exception e) {
                    System.out.println("not a number:" + keyValue);
                    disjunction.add(Restrictions.like("p.name", keyValue, MatchMode.ANYWHERE));
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                }
            }
            criteria.add(disjunction);
            criteria.addOrder(Order.asc("p.name"));
            Page<ProductOption> page = productOptionService.list(criteria, productOptionListVO.getPageNumber(), productOptionListVO.getPageSize());
            model.addAttribute("page", page);
        }
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(productOptionListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("productOptionListVO", productOptionListVO);
        String url = request.getServletPath();
        return url.replace("po", "productOption");
    }
    
    @RequestMapping("quickSearchForBookList")
    public String quickSearchForBookList(Model model, ProductOptionListVO productOptionListVO, HttpServletRequest request) {
        String key = productOptionListVO.getKey();
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductOption.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.createAlias("product", "p");
        criteria.add(Restrictions.eq("p.company.id", WebThreadLocal.getCompany().getId()));
        
        criteria.createAlias("p.category", "c");
        criteria.createAlias("c.category", "pc");
        criteria.add(Restrictions.eq("pc.reference",CommonConstant.CATEGORY_REF_TREATMENT));
        
        Long supplierId = productOptionListVO.getSupplierId();
        if(supplierId != null) {
            criteria.add(Restrictions.eq("p.supplier.id", supplierId));
        }
        if (StringUtils.isNotBlank(key)) {
            criteria.createAlias("productOptionAttributes", "pa");
            Disjunction disjunction = Restrictions.disjunction();
            String[] keys = key.trim().split(" ");
            for (String keyValue : keys) {
                try {
                    Integer.parseInt(keyValue);
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                } catch (Exception e) {
                    System.out.println("not a number:" + keyValue);
                    disjunction.add(Restrictions.like("p.name", keyValue, MatchMode.ANYWHERE));
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                }
            }
            criteria.add(disjunction);
            criteria.addOrder(Order.asc("p.name"));
            Page<ProductOption> page = productOptionService.list(criteria, productOptionListVO.getPageNumber(), productOptionListVO.getPageSize());
            model.addAttribute("page", page);
        }
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(productOptionListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("productOptionListVO", productOptionListVO);
        model.addAttribute("memberId", productOptionListVO.getMemberId());
		model.addAttribute("shopId", productOptionListVO.getShopId());
		
        return "productOption/quickSearchForBookList";
    }
    
    @RequestMapping("quickSearchList")
    public String quickSearchList(Model model, ProductOptionListVO productOptionListVO, HttpServletRequest request) {
        String key = productOptionListVO.getKey();
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductOption.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.createAlias("product", "p");
        criteria.add(Restrictions.eq("p.company.id", WebThreadLocal.getCompany().getId()));

        if (StringUtils.isNotEmpty(productOptionListVO.getCategory())) {
            criteria.createAlias("p.category", "c");
            Category parent = categoryService.getByReference(productOptionListVO.getCategory());
            if(parent != null) {
                List<Long> allCategoryIds = new ArrayList<>();
                allCategoryIds = categoryService.getAllChildrenByCategory(allCategoryIds, parent.getId());
                allCategoryIds.add(parent.getId());
                if(allCategoryIds.size() > 0) {
                    System.out.println("ids:" + allCategoryIds);
                    criteria.add(Restrictions.in("c.id", allCategoryIds));
                }
            }

        }

        Long supplierId = productOptionListVO.getSupplierId();
        if(supplierId != null) {
            criteria.add(Restrictions.eq("p.supplier.id", supplierId));
        }
        if (StringUtils.isNotBlank(key)) {
            criteria.createAlias("productOptionAttributes", "pa");
            Disjunction disjunction = Restrictions.disjunction();
            String[] keys = key.trim().split(" ");
            for (String keyValue : keys) {
                try {
                    Integer.parseInt(keyValue);
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                } catch (Exception e) {
                    System.out.println("not a number:" + keyValue);
                    disjunction.add(Restrictions.like("p.name", keyValue, MatchMode.ANYWHERE));
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                }
            }
            criteria.add(disjunction);
            criteria.addOrder(Order.asc("p.name"));
            Page<ProductOption> page = productOptionService.list(criteria, productOptionListVO.getPageNumber(), productOptionListVO.getPageSize());
            model.addAttribute("page", page);
        }
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(productOptionListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("productOptionListVO", productOptionListVO);
        String url = request.getServletPath();
        return url.replace("po", "productOption");
    }

    @RequestMapping("quickSearchForSingleBookList")
    public String quickSearchForSingleBookList(Model model, ProductOptionListVO productOptionListVO, HttpServletRequest request) {
        String key = productOptionListVO.getKey();
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductOption.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.createAlias("product", "p");
        criteria.add(Restrictions.eq("p.company.id", WebThreadLocal.getCompany().getId()));

        criteria.createAlias("p.category", "c");
        criteria.createAlias("c.category", "pc");
        criteria.add(Restrictions.eq("pc.reference",CommonConstant.CATEGORY_REF_TREATMENT));

        Long supplierId = productOptionListVO.getSupplierId();
        if(supplierId != null) {
            criteria.add(Restrictions.eq("p.supplier.id", supplierId));
        }
        if (StringUtils.isNotBlank(key)) {
            criteria.createAlias("productOptionAttributes", "pa");
            Disjunction disjunction = Restrictions.disjunction();
            String[] keys = key.trim().split(" ");
            for (String keyValue : keys) {
                try {
                    Integer.parseInt(keyValue);
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                } catch (Exception e) {
                    System.out.println("not a number:" + keyValue);
                    disjunction.add(Restrictions.like("p.name", keyValue, MatchMode.ANYWHERE));
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                }
            }
            criteria.add(disjunction);
            criteria.addOrder(Order.asc("p.name"));
            Page<ProductOption> page = productOptionService.list(criteria, productOptionListVO.getPageNumber(), productOptionListVO.getPageSize());
            model.addAttribute("page", page);
        }
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(productOptionListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("productOptionListVO", productOptionListVO);
        model.addAttribute("memberId", productOptionListVO.getMemberId());
        model.addAttribute("shopId", productOptionListVO.getShopId());
        return "productOption/quickSearchForSingleBookList";
    }

    @RequestMapping("quickSearchForSales")
    public String quickSearchForSales(Model model, ProductOptionListVO productOptionListVO, HttpServletRequest request) {
        model.addAttribute("productOptionListVO", productOptionListVO);
        
        int numberOfTherapistUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_THERAPIST_USED);
		model.addAttribute("numberOfTherapistUsed",numberOfTherapistUsed);
		
        return "productOption/quickSearchForSales";
    }

    @RequestMapping("quickSearchForSalesList")
    public String quickSearchForSalesList(Model model, ProductOptionListVO productOptionListVO, HttpServletRequest request) {
        String key = productOptionListVO.getKey();
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductOption.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.createAlias("product", "p");
        criteria.add(Restrictions.eq("p.company.id", WebThreadLocal.getCompany().getId()));
        Long supplierId = productOptionListVO.getSupplierId();
        if(supplierId != null) {
            criteria.add(Restrictions.eq("p.supplier.id", supplierId));
        }
        if (StringUtils.isNotBlank(key)) {
            criteria.createAlias("productOptionAttributes", "pa");
            Disjunction disjunction = Restrictions.disjunction();
            String[] keys = key.trim().split(" ");
            for (String keyValue : keys) {
                try {
                    Integer.parseInt(keyValue);
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                } catch (Exception e) {
                    disjunction.add(Restrictions.like("p.name", keyValue, MatchMode.ANYWHERE));
                    disjunction.add(Restrictions.like("pa.value", keyValue, MatchMode.ANYWHERE));
                }
            }
            criteria.add(disjunction);
            criteria.addOrder(Order.asc("p.name"));
            Page<ProductOption> page = productOptionService.list(criteria, productOptionListVO.getPageNumber(), productOptionListVO.getPageSize());
            model.addAttribute("page", page);
        }
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(productOptionListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("productOptionListVO", productOptionListVO);
        
        int numberOfTherapistUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_THERAPIST_USED);
		model.addAttribute("numberOfTherapistUsed",numberOfTherapistUsed);
		
		//therapist
	    List<User> therapistList=userService.getAvalibleUsersByAccountTypeAndRoleRef(CommonConstant.USER_ACCOUNT_TYPE_STAFF, CommonConstant.STAFF_ROLE_REF_THERAPIST);
		model.addAttribute("therapistList", therapistList);
		
		model.addAttribute("memberId", productOptionListVO.getMemberId());
		model.addAttribute("shopId", productOptionListVO.getShopId());
		
        return "productOption/quickSearchForSalesList";
    }
    @RequestMapping("rerurnPOPrice")
   	@ResponseBody
   	public Double rerurnPOPrice(Long poId,Long shopId) {
       	
   		ProductOption po =productOptionService.get(poId);
   		Double poPrice=po.getFinalPrice(shopId);
   		return poPrice;
   	}
    
    @RequestMapping("rerurnPoName")
   	@ResponseBody
   	public String rerurnPoName(Long poId) {
       	
   		ProductOption po =productOptionService.get(poId);
   		String poName=po.getLabel3();
   		return poName;
   	}

    @RequestMapping("searchProductOptionList")
    @ResponseBody
    public Results searchProductOptionList(String code){
        List<ProductOptionQuickSearchVo> objects = new ArrayList<>();
        if(StringUtils.isNotBlank(code)){
            List<ProductOption> options = productOptionService.searchProductOptionList(code);
            for(ProductOption productOption : options){
                ProductOptionQuickSearchVo quickSearchVo = new ProductOptionQuickSearchVo();
                quickSearchVo.setId(productOption.getId());
                if(StringUtils.isNotBlank(productOption.getCode())){
                    quickSearchVo.setTreatmentCode(productOption.getCode());
                }else{
                    quickSearchVo.setTreatmentCode("");
                }

                if(StringUtils.isNotBlank(productOption.getProduct().getName())){
                    quickSearchVo.setTreatmentName(productOption.getProduct().getName());
                }else{
                    quickSearchVo.setTreatmentName("");
                }
                quickSearchVo.setDuration(productOption.getDuration());
                quickSearchVo.setProductId(productOption.getProduct().getId());
               if( StringUtils.isNotBlank(productOption.getPrice())){
                   quickSearchVo.setPrice(productOption.getPrice());
               }else{
                   quickSearchVo.setPrice("");
               }
                quickSearchVo.setProcessTime(productOption.getProcessTime());
                objects.add(quickSearchVo);
            }
        }
        Results instance = Results.getInstance();
        instance.setCode(Results.CODE_SUCCESS).addMessage("successMsg", objects);
        return instance;
    }
}
