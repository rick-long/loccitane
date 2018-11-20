package org.spa.dao.pok;

import java.util.List;

import org.spa.model.product.ProductOptionKey;


public interface ProductOptionKeyDao {

	public List<ProductOptionKey> getPOKListOrderByField(final Long categoryId,final Long pCategoryId,final Long companyId);
}
