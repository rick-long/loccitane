package org.spa.service.product;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.Category;
import org.spa.vo.category.CategoryAddVO;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface CategoryService extends BaseDao<Category>{
	
	public void saveOrUpdateCategory(CategoryAddVO categoryAddVO);
	public List<Category> getCategoriesByParentCategoryRef(String parentCategoryRef,Long companyId);
	public List<Category> getCategoriesByParentCategoryId(Long id,Long companyId);
	public List<Long> getLowestCategoriesByCategory(Long categoryId,List<Long> list);
	
	public List<Long> getAllChildrenByCategory(List<Long> allChildrens,Long categoryId);

	public Category getByReference(String reference);
    
    public Category getByReference(String reference,Long companyId);
}
