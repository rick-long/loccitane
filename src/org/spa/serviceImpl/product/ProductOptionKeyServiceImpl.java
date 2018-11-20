package org.spa.serviceImpl.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.pok.ProductOptionKeyDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOptionKey;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductOptionKeyService;
import org.spa.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ProductOptionKeyServiceImpl extends BaseDaoHibernate<ProductOptionKey> implements ProductOptionKeyService{

	@Autowired
	public ProductOptionKeyDao productOptionKeyDao;
	
	@Autowired
	public CategoryService categoryService;
	
	@Autowired
	public ProductService productService;
	
	@Override
	public List<ProductOptionKey> getPoKeysByProductIdAndCompanyId(String productId,Long companyId) {
		
		List<ProductOptionKey> pokList=new ArrayList<ProductOptionKey>();
		if(StringUtils.isNotBlank(productId)){
			Product product=productService.get(Long.valueOf(productId));
			Long categoryId=product.getCategory().getId();
			Long pCategoryId=product.getCategory().getCategory().getId();
			pokList=productOptionKeyDao.getPOKListOrderByField(categoryId, pCategoryId, companyId);
		}
		return pokList;
	}
	
	@Override
	public List<ProductOptionKey> getPOKListOByRefAndCategoryId(String ref, Long categoryId,Long companyId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ProductOptionKey.class);

		criteria.add(Restrictions.eq("isActive",true));
		if(StringUtils.isNotBlank(ref)){
			criteria.add(Restrictions.eq("reference", ref));
		}
		if(categoryId !=null && categoryId.longValue()>0){
			criteria.createAlias("categories", "pokeycategory");
			criteria.add(Restrictions.eq("pokeycategory.id", categoryId));
		}
		if(companyId !=null && companyId.longValue()>0){
			criteria.add(Restrictions.eq("company.id", companyId));
		}
		List<ProductOptionKey> resultList=list(criteria);
		
		return resultList;
	}

    @Override
    public List<ProductOptionKey> getPOKListOrderByField(Long categoryId, Long pCategoryId, Long companyId) {
    	List<ProductOptionKey> pokList=new ArrayList<ProductOptionKey>();
    	pokList=productOptionKeyDao.getPOKListOrderByField(categoryId, pCategoryId, companyId);
        return pokList;
    }

	@Override
	public List<ProductOptionKey> getPoKeysByCategoryIdAndCompanyId(Long categoryId, Long companyId) {
		
		if(categoryId == null){
			return null;
		}
		Category category=categoryService.get(categoryId);
		Long pCategoryId=category.getCategory().getId();
		List<ProductOptionKey> pokList=productOptionKeyDao.getPOKListOrderByField(categoryId, category.getCategory().getId(), companyId);
		if(pokList==null || pokList.size()==0){
			pokList=getPoKeysByCategoryIdAndCompanyId(pCategoryId, companyId);
		}
		return pokList;
	}
}
