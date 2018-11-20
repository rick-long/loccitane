package org.spa.serviceImpl.product;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.pdk.ProductDescriptionKeyDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Category;
import org.spa.model.product.ProductDescriptionKey;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductDescriptionKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ProductDescriptionKeyServiceImpl extends BaseDaoHibernate<ProductDescriptionKey> implements ProductDescriptionKeyService{

	@Autowired
	public CategoryService categoryService;
	
	@Autowired
	public ProductDescriptionKeyDao productDescriptionKeyDao;
	
	public List<ProductDescriptionKey> getPdKeysByCategoryIdAndCompanyId(Long categoryId,Long companyId) {
		
		if(categoryId == null){
			return null;
		}
		Category category=categoryService.get(categoryId);
		Long pCategoryId=category.getCategory().getId();
		List<ProductDescriptionKey> pdkList=productDescriptionKeyDao.getPdKListOrderByField(categoryId, pCategoryId, companyId);
		if(pdkList==null || pdkList.size()==0){
			pdkList=getPdKeysByCategoryIdAndCompanyId(pCategoryId, companyId);
		}
		return pdkList;
	}
	
	@Override
	public List<ProductDescriptionKey> getPDKListByRefAndCategoryId(String ref, Long categoryId, Long companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductDescriptionKey.class);

		criteria.add(Restrictions.eq("isActive",true));
		if(StringUtils.isNotBlank(ref)){
			criteria.add(Restrictions.like("reference", ref, MatchMode.ANYWHERE));
		}
		if(categoryId !=null && categoryId.longValue()>0){
			criteria.createAlias("categories", "pdkeycategory");
			criteria.add(Restrictions.eq("pdkeycategory.id", categoryId));
		}
		if(companyId !=null && companyId.longValue()>0){
			criteria.add(Restrictions.eq("company.id", companyId));
		}
		List<ProductDescriptionKey> resultList=list(criteria);
		
		return resultList;
	}
	
}
