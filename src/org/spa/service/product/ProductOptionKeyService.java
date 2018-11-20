package org.spa.service.product;

import java.util.List;
import org.spa.dao.base.BaseDao;
import org.spa.model.product.ProductOptionKey;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ProductOptionKeyService extends BaseDao<ProductOptionKey>{
	
	public List<ProductOptionKey> getPoKeysByProductIdAndCompanyId(String productId,Long companyId);
	
	public List<ProductOptionKey> getPOKListOByRefAndCategoryId(String ref, Long categoryId,Long companyId);

    public List<ProductOptionKey> getPOKListOrderByField(Long categoryId, Long pCategoryId, Long companyId);
    
    public List<ProductOptionKey> getPoKeysByCategoryIdAndCompanyId(Long categoryId, Long companyId);
}
