package org.spa.dao.poa;

import org.spa.model.product.Category;
import org.spa.model.product.Product;

import java.util.List;

public interface ProductDao {
	public List<Product> listByCategory(Category category, Boolean isOnline, Long shopId);
	public List<Long> getShopIds(final Long productId);
}
