package org.spa.serviceImpl.product;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Category;
import org.spa.service.product.CategoryService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.category.CategoryAddVO;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class CategoryServiceImpl extends BaseDaoHibernate<Category> implements CategoryService{
	
	@Override
	public void saveOrUpdateCategory(CategoryAddVO categoryAddVO) {
		
		String name = categoryAddVO.getName();
		Category category = null;
		if(categoryAddVO.getId() !=null){
			category =get(categoryAddVO.getId());
			category.setLastUpdated(new Date());
			category.setIsActive(categoryAddVO.getActive());
			category.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
		}else{
			category=new Category();
			category.setCreatedBy(WebThreadLocal.getUser().getUsername());
	        category.setCreated(new Date());
	        category.setCompany(WebThreadLocal.getCompany());
	        category.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.CATEGORY_REF_PREFIX));
	        category.setIsActive(true);
		}
         category.setName(name);
         category.setDisplayOrder(categoryAddVO.getDisplayOrder());
         category.setRemarks(categoryAddVO.getRemarks());

		Category parentCategory=get(categoryAddVO.getCategoryId());

         /*Category parentCategory=get(Math.abs(categoryAddVO.getRootCategoryId()));
         if(categoryAddVO.getCategoryId() !=null){
        	 parentCategory=get(categoryAddVO.getCategoryId());
         }*/
         category.setCategory(parentCategory);
         save(category);
	}
	
	@Override
	public List<Category> getCategoriesByParentCategoryRef(String parentCategoryRef,Long companyId) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
		//
		detachedCriteria.add(Restrictions.eq("isActive",true));
		
		if(companyId !=null){
			detachedCriteria.add(Restrictions.eq("company.id",companyId));
		}
		
		Category parentCategory=get("reference", parentCategoryRef);
		if(parentCategory !=null){
			//has parent category reference
			detachedCriteria.add(Restrictions.eq("category.id",parentCategory.getId()));
		}
		//according to company
		detachedCriteria.addOrder(Order.asc("displayOrder"));
		List<Category> categoryList=list(detachedCriteria);
		
		return categoryList;
	}
	
	@Override
	public List<Category> getCategoriesByParentCategoryId(Long id,Long companyId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
		//
		detachedCriteria.add(Restrictions.eq("isActive",true));

		if(companyId !=null){
			detachedCriteria.add(Restrictions.eq("company.id",companyId));
		}

	/*	Category parentCategory=get("reference", parentCategoryRef);*/
		if(id !=null){
			//has parent category reference
			detachedCriteria.add(Restrictions.eq("category.id",id));
		}
		//according to company
		detachedCriteria.addOrder(Order.asc("displayOrder"));
		List<Category> categoryList=list(detachedCriteria);

		return categoryList;
	}
	//获取当前分类下的所有有产品的子分类
	@Override
	public List<Long> getLowestCategoriesByCategory(Long categoryId,List lowestCategories) {
		Category category=get(categoryId);
		Set<Category> children = category.getCategories();
		for (Category c : children) {
			if(c.getCategories().size() > 0){
				getLowestCategoriesByCategory(c.getId(),lowestCategories);
			}else{
				lowestCategories.add(c.getId());
			}
		}
		return lowestCategories;
	}
	//获取当前分类下的所有子分类
	public List<Long> getAllChildrenByCategory(List<Long> allChildrens,Long categoryId) {
		Category category=get(categoryId);
        Set<Category> children = category.getCategories();
        if(children !=null && children.size()>0){
        	for (Category c : children) {
            	allChildrens.add(c.getId());
            	getAllChildrenByCategory(allChildrens,c.getId());
            }
        }
        return allChildrens;
    }

    @Override
    public Category getByReference(String reference) {
        return get("reference", reference);
    }
    
    @Override
    public Category getByReference(String reference,Long companyId) {
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
		//
		detachedCriteria.add(Restrictions.eq("isActive",true));
		
		if(companyId !=null){
			detachedCriteria.add(Restrictions.eq("company.id",companyId));
		}
		if(StringUtils.isNoneBlank(reference)){
			detachedCriteria.add(Restrictions.eq("reference",reference));
		}
		
		List<Category> categoryList=list(detachedCriteria);
		Category category=null;
		if(categoryList !=null && categoryList.size()>0){
			category=categoryList.get(0);
		}
		return category;
    }
}
