package org.spa.service.product;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.ProductOptionAttribute;

import java.util.List;
import java.util.Map;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ProductOptionAttributeService extends BaseDao<ProductOptionAttribute>{
	
	public ProductOptionAttribute getPOAttrByPoIdAndKeyRefAndCompany(Long productOptionId,String poKeyRef,Long companyId);

    List<ProductOptionAttribute> getProductOptionAttributesByValue(String value,String prodType);
    
    Map<Long,Integer> getDurationByProductOptionIds(Long companyId,List<Long> productOptionIds,List<String> productOptionAttributeStr);
}
