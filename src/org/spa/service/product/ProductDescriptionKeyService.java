package org.spa.service.product;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.ProductDescriptionKey;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ProductDescriptionKeyService extends BaseDao<ProductDescriptionKey>{
	
	public List<ProductDescriptionKey> getPDKListByRefAndCategoryId(String ref, Long categoryId,Long companyId);
	
	public List<ProductDescriptionKey> getPdKeysByCategoryIdAndCompanyId(Long categoryId,Long companyId);
}
