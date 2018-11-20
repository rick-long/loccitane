package org.spa.service.product;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.ProductOptionSupernumeraryPrice;
import org.spa.vo.product.SupernumeraryPriceAddVO;

import java.util.List;
import java.util.Set;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ProductOptionSupernumeraryPriceService extends BaseDao<ProductOptionSupernumeraryPrice>{
	
	public void saveProductOptionSupernumeraryPrice(SupernumeraryPriceAddVO supernumeraryPriceAddVO);
	
	public ProductOptionSupernumeraryPrice getProductOptionSupernumeraryPriceByShopAndPO(Long productOptionId,Long shopId);
	public List<ProductOptionSupernumeraryPrice> getProductOptionSupernumeraryPrices(Long productOptionId,List<Long> shopId);
}
