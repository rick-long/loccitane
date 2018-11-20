package org.spa.dao.poa;

import java.util.Map;

import org.spa.model.product.ProductOptionAttribute;

public interface ProductOptionAtrributeDao {

	public ProductOptionAttribute getPOAttrByPoIdAndKeyRefAndCompany(final Long productOptionId, final String poKeyRef,final Long companyId);
    
	public Map<Long,Integer> getDurationByProductOptionIds(Long companyId,String ProductOptionIds,String productOptionAttributeStr);
}
