package org.spa.dao.pdk;

import java.util.List;

import org.spa.model.product.ProductDescriptionKey;


public interface ProductDescriptionKeyDao {

	public List<ProductDescriptionKey> getPdKListOrderByField(final Long categoryId,final Long pCategoryId,final Long companyId);
}
