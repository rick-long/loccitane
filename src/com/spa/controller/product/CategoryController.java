package com.spa.controller.product;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.company.Company;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.RoomTreatments;
import org.spa.model.staff.StaffTreatments;
import org.spa.utils.I18nUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.category.CategoryAddVO;
import org.spa.vo.category.CategoryListVO;
import org.spa.vo.category.CategoryNoteVO;
import org.spa.vo.category.CategorySelectVO;
import org.spa.vo.common.SelectOptionVO;
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
@RequestMapping("category")
public class CategoryController extends BaseController {
	
    @RequestMapping("toView")
    public String categoryManagement(Model model, CategoryListVO categoryListVO) {
    	
        //get the subcategory under topist category
        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("subCategoryList", subCategoryList);
        return "category/categoryManagement";
    }

    @RequestMapping("list")
    public String categoryList(Model model, CategoryListVO categoryListVO) {

        String name = categoryListVO.getName();
        String isActive = categoryListVO.getIsActive();

        Long categoryId = categoryListVO.getCategoryId();
        Long rootCategoryId=categoryListVO.getRootCategoryId();
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);

        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }

        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
        }
        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        if (rootCategoryId !=null) {
        	List<Long> allChildrens=new ArrayList<>();
        	List<Long> allChildrenCategories=null;
        	if(categoryId !=null){
        		allChildrenCategories=categoryService.getAllChildrenByCategory(allChildrens, categoryId);
        		if(allChildrenCategories !=null && allChildrenCategories.size()>1){
        			detachedCriteria.add(Restrictions.in("id",allChildrenCategories));
        		}else{
        			detachedCriteria.add(Restrictions.eq("category.id", categoryId));
        		}
        	}else{
        		allChildrenCategories=categoryService.getAllChildrenByCategory(allChildrens, rootCategoryId);
        		if(allChildrenCategories !=null && allChildrenCategories.size()>1){
        			detachedCriteria.add(Restrictions.in("id",allChildrenCategories));
        		}else{
        			detachedCriteria.add(Restrictions.eq("category.id", rootCategoryId));
        		}
        	}
        }
        detachedCriteria.addOrder(Order.asc("displayOrder"));

        //page start
        Page<Category> categoryPage = categoryService.list(detachedCriteria, categoryListVO.getPageNumber(), categoryListVO.getPageSize());
        model.addAttribute("page", categoryPage);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(categoryListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "category/categoryList";
    }

    @RequestMapping("toAdd")
    public String toAddCategory(Model model, CategoryAddVO categoryAddVO) {
    	
        //get sub-category under topist category
        Category category = categoryService.get(1l);
        if(category == null){
            throw new RuntimeException("top level is not exist");
        }
        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        subCategoryList.add(category);
        model.addAttribute("subCategoryList", subCategoryList);

        return "category/categoryAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm addCategory(@Valid CategoryAddVO categoryAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            categoryService.saveOrUpdateCategory(categoryAddVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }

    }

    @RequestMapping("toEdit")
    public String toEditCategory(Model model, CategoryAddVO categoryAddVO) {

        Category category = categoryService.get(categoryAddVO.getId());
        categoryAddVO.setName(category.getName());
        categoryAddVO.setDisplayOrder(category.getDisplayOrder());
        categoryAddVO.setRootCategoryId(category.getTheTopestCategoryUnderRoot().getId());
        categoryAddVO.setCategoryId(category.getCategory().getId());
        categoryAddVO.setRemarks(category.getRemarks());
        categoryAddVO.setActive(category.isIsActive());
        model.addAttribute("categoryAddVO",categoryAddVO);
        Category top = categoryService.get(1l);
        if(category == null){
            throw new RuntimeException("top level is not exist");
        }
        //get sub-category under topist category
        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        subCategoryList.add(top);
        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("secondCategoryId", category.getTheTopestCategoryUnderRoot().getId());
        return "category/categoryEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm editCategory(@Valid CategoryAddVO categoryAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
           categoryService.saveOrUpdateCategory(categoryAddVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }

    }

    /**
     * 获取当前目录id的children集合
     *
     * @param id
     * @return
     */
   /* @RequestMapping("getCategoryNodes")
    @ResponseBody
    public List<CategoryNoteVO> getCategoryNodes(Long id, Long roomId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        if (id != null) {
            detachedCriteria.add(Restrictions.eq("category.id", id));
        } else {
            detachedCriteria.add(Restrictions.isNull("category.id"));
        }

        detachedCriteria.addOrder(Order.asc("displayOrder"));
        Category category = categoryService.get(detachedCriteria);
        List<CategoryNoteVO> categoryNoteVOs = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        // 设置选项中
        if (roomId != null) {
            Room room = roomService.get(roomId);
            categoryIds = room.getAllCategoryIds();
        }
        if (category != null) {
            CategoryNoteVO categoryNoteVO = getTreeVO(category, categoryIds);
            categoryNoteVOs.add(categoryNoteVO);
        }


        return categoryNoteVOs;
    }*/

   /* private CategoryNoteVO getTreeVO(Category category, List<Long> categoryIds) {
        CategoryNoteVO vo = CategoryNoteVO.getInstance(category, null);
        vo.setChecked(categoryIds.contains(vo.getId())); // 设置是否选中
        Set<Category> children = category.getCategories();
        for (Category c : children) {
            vo.getChildren().add(getTreeVO(c, categoryIds));
        }
        return vo;
    }*/

    @RequestMapping("getSimpleCategoryNodes")
    @ResponseBody
    public List<CategoryNoteVO> getSimpleCategoryNodes(Long id, Long roomId, Long staffId) {
        Category category;
        List<CategoryNoteVO> categoryNoteVOs = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        List<Long> treatmentIds = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        if (roomId != null) {
            for (RoomTreatments roomTreatments : roomService.get(roomId).getRoomTreatmentses()) {
                if (roomTreatments.getCategory() != null) {
                    categoryIds.add(roomTreatments.getCategory().getId());
                } else if (roomTreatments.getProduct() != null) {
                    treatmentIds.add(roomTreatments.getProduct().getId());
                    productList.add(roomTreatments.getProduct());
                }
            }
        } else if (staffId != null) {
            for (StaffTreatments treatments : userService.get(staffId).getStaffTreatmentses()) {
                if (treatments.getCategory() != null) {
                    categoryIds.add(treatments.getCategory().getId());
                } else if (treatments.getProduct() != null) {
                    treatmentIds.add(treatments.getProduct().getId());
                    productList.add(treatments.getProduct());
                }
            }
        }

        if (id != null) {
            category = categoryService.get(id);
            // 有子目录，显示子目录
            if (category != null && category.getCategories().size() > 0) {
                for (Category c : category.getSortedChilden()) {
                    if(c.isIsActive()) {
                        categoryNoteVOs.add(CategoryNoteVO.getInstance(c, categoryIds, productList));
                    }
                }
            } else {
                // 显示该目录的产品
                List<Product> list = productService.listByCategory(category,null);
                for (Product product : list) {
                    if(product.isIsActive()) {
                        categoryNoteVOs.add(CategoryNoteVO.getInstance(product, treatmentIds));
                    }
                }
            }
        } else {
            // 显示根目录
        	List<Category> categoryList=new ArrayList<Category>();
        	Category categoryT = categoryService.getByReference(CommonConstant.CATEGORY_REF_TREATMENT);
        	categoryList.add(categoryT);
        	
        	Boolean selectedHairsalon = PropertiesUtil.getBooleanValueByName(CommonConstant.CATEGORY_REF_HAIRSALON_FOR_SELECTED);
        	if(selectedHairsalon){
        		Category categoryHS = categoryService.getByReference(CommonConstant.CATEGORY_REF_HAIRSALON);
            	categoryList.add(categoryHS);
        	}
        	
            for(Category c : categoryList){
            	CategoryNoteVO categoryNoteVO = CategoryNoteVO.getInstance(c, categoryIds, productList);
                categoryNoteVOs.add(categoryNoteVO);
            }
        }

        return categoryNoteVOs;
    }

    @RequestMapping("getCategoryForCommission")
    @ResponseBody
    public List<CategoryNoteVO> getCategoryForCommission(Long id, Long commissionRuleId) {
        Category category;
        List<CategoryNoteVO> categoryNoteVOs = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        if (id != null) {
            category = categoryService.get(id);
            // 显示子目录
            if (category != null && category.getCategories().size() > 0) {
                for (Category c : category.getSortedChilden()) {
                    categoryNoteVOs.add(CategoryNoteVO.getInstance(c, categoryIds));
                }
            }
        } else {
            // 显示根目录
            category = categoryService.getByReference(CommonConstant.CATEGORY_REF_TREATMENT);
            CategoryNoteVO categoryNoteVO = CategoryNoteVO.getInstance(category, categoryIds);
            categoryNoteVOs.add(categoryNoteVO);
        }

        return categoryNoteVOs;
    }

    //this is for category search form right box to left box
    @RequestMapping("selectOptionJson")
    @ResponseBody
    public List<SelectOptionVO> selectOptionJson(CategorySelectVO categorySelectVO) {
    	
    	Long rootId = categorySelectVO.getRootId() == null ? 1L : categorySelectVO.getRootId();
    	List<Long> allChildrens=new ArrayList<Long>();
    	
    	categoryService.getAllChildrenByCategory(allChildrens,rootId);
    	allChildrens.add(rootId);
    	
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        Company company = WebThreadLocal.getCompany();
        if (company != null) {
            detachedCriteria.add(Restrictions.eq("company.id", company.getId()));
        }
        
        if (StringUtils.isNoneBlank(categorySelectVO.getDisplayName())) {
            detachedCriteria.add(Restrictions.like("name", categorySelectVO.getDisplayName(), MatchMode.ANYWHERE));
        }
        
        if(allChildrens !=null && allChildrens.size()>0){
        	detachedCriteria.add(Restrictions.in("id", allChildrens));
        }
        
        detachedCriteria.add(Restrictions.eq("isActive", true));
        
        detachedCriteria.addOrder(Order.asc("id"));
        List<Category> list = categoryService.list(detachedCriteria);

        List<SelectOptionVO> selectOptionVOList = list.stream().map(category -> new SelectOptionVO(category.getId(), category.getFullName())).collect(Collectors.toList());
        return selectOptionVOList;
    }

    //this search is for category drop down
   /* @Deprecated
    @RequestMapping("categorySelect")
    public String categorySelect(Model model, CategorySelectVO categorySelectVO) {
        Long rootId = categorySelectVO.getRootId() == null ? 1L : categorySelectVO.getRootId();
        List<Category> categories = categoryService.get(rootId).getSortedChilden();
        model.addAttribute("categories", categories);
        model.addAttribute("categorySelectVO", categorySelectVO);
        Long categoryId = categorySelectVO.getCategoryId();
        Long productId = categorySelectVO.getProductId();
        Long productOptionId = categorySelectVO.getProductOptionId();
        if(categoryId != null) {
            categorySelectVO.setDisplayName(categoryService.get(categoryId).getFullName());
        } else if(productId != null) {
            categorySelectVO.setDisplayName(productService.get(productId).getFullName());
        } else if(productOptionId != null) {
            categorySelectVO.setDisplayName(productOptionService.get(productOptionId).getLabel2());
        }
        return "category/categorySelect";
    }*/
    @RequestMapping("categorySelect2")
    public String categorySelect2(Model model, CategorySelectVO categorySelectVO) {
        Long rootId = categorySelectVO.getRootId() == null ? 1L : categorySelectVO.getRootId();
        if(categoryService.get(Math.abs(rootId)) ==null){
        	return "category/categorySelect2";
        }

        List<Category> categories = new ArrayList<>();
        if(rootId > 0){
            List<Category> cateList = categoryService.get(rootId).getSortedChilden();
            categories.addAll(cateList);
        }else {
            Category categorie = categoryService.get(Math.abs(categorySelectVO.getRootId()));
            categories.add(categorie);
        }
        Long rootId1 = categorySelectVO.getRootId1();
        if(rootId1 !=null){
        	List<Category> categories1 = categoryService.get(rootId1).getSortedChilden();
        	categories.addAll(categories1);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("categorySelectVO", categorySelectVO);
        Long categoryId = categorySelectVO.getCategoryId();
        Long productId = categorySelectVO.getProductId();
        Long productOptionId = categorySelectVO.getProductOptionId();
        if(categoryId != null) {
            categorySelectVO.setDisplayName(categoryService.get(categoryId).getFullNameExceptProdType());
        } else if(productId != null) {
            categorySelectVO.setDisplayName(productService.get(productId).getFullName2());
        } else if(productOptionId != null) {
            categorySelectVO.setDisplayName(productOptionService.get(productOptionId).getLabel33());
            categorySelectVO.setDuration(productOptionService.get(productOptionId).getDuration().toString());
            categorySelectVO.setProcessTime(productOptionService.get(productOptionId).getProcessTime().toString());
        }
        if(StringUtils.isBlank(categorySelectVO.getLevel())) {
            categorySelectVO.setLevel("category");
        }
        return "category/categorySelect2";
    }

    @RequestMapping("categoryMenu")
    public String categoryMenu(Model model, CategorySelectVO categorySelectVO) {
        Long rootId = categorySelectVO.getRootId();
        if ("productOption".equals(categorySelectVO.getLevel())) {
            List<ProductOption> productOptions = productService.get(rootId).getSortedProductOptions();
            model.addAttribute("productOptions", productOptions);
        } else if ("product".equals(categorySelectVO.getLevel())) {
            List<Product> products = categoryService.get(rootId).getSortedProducts();
            List<Product> removeProducts = new ArrayList<Product>();
            if(categorySelectVO.getIsOnline() !=null && categorySelectVO.getIsOnline()){
            	for(Product p : products){
            		List<Long> pids = productService.getShopIds(p.getId());
            		if(!pids.contains(categorySelectVO.getShopId())){
            			removeProducts.add(p);
            		}
            	}
            	if(removeProducts !=null && removeProducts.size()>0){
            		products.removeAll(removeProducts);
	            }
            }
            model.addAttribute("products", products);
        } else {
            List<Category> categories = categoryService.get(rootId).getSortedChilden();
            if(categorySelectVO.getIsOnline() !=null && categorySelectVO.getIsOnline()){
	            List<Category> removes = new ArrayList<Category>();
	            for(Category category : categories){
	                List<Product>productList=productService.listByCategory(category,true,categorySelectVO.getShopId());
	                if(productList.size() == 0){
	                	removes.add(category);
	                }
	            }
	            if(removes !=null && removes.size()>0){
	            	categories.removeAll(removes);
	            }
            }
            model.addAttribute("categories", categories);
        }

        model.addAttribute("categorySelectVO", categorySelectVO);
        return "category/categoryMenu";
    }

    @RequestMapping("categoryProductTree")
    public String categoryProductTree(String dataUrl, Model model) {
        model.addAttribute("dataUrl", dataUrl);
        return "category/categoryProductTree";
    }
    
    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
    	Category category=categoryService.get(id);
        if (category != null) {
        	try {
        		category.setIsActive(false);
        		categoryService.saveOrUpdate(category);
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
