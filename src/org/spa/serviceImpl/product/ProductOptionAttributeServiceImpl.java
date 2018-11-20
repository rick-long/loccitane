package org.spa.serviceImpl.product;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.poa.ProductOptionAtrributeDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.ProductOption;
import org.spa.model.product.ProductOptionAttribute;
import org.spa.service.product.ProductOptionAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ProductOptionAttributeServiceImpl extends BaseDaoHibernate<ProductOptionAttribute> implements ProductOptionAttributeService{

	@Autowired
	public ProductOptionAtrributeDao productOptionAtrributeDao;
	
	@Override
	public ProductOptionAttribute getPOAttrByPoIdAndKeyRefAndCompany(Long productOptionId, String poKeyRef, Long companyId) {
		return productOptionAtrributeDao.getPOAttrByPoIdAndKeyRefAndCompany(productOptionId, poKeyRef, companyId);
	}

	@Override
	public List<ProductOptionAttribute> getProductOptionAttributesByValue(String value,String prodType) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductOptionAttribute.class);
		detachedCriteria.add(Restrictions.eq("isActive", true));
		detachedCriteria.add(Restrictions.eq("value", value));
		detachedCriteria.createAlias("productOption", "po");
		detachedCriteria.createAlias("po.product", "p");
		detachedCriteria.add(Restrictions.eq("p.prodType", prodType));
		return list(detachedCriteria);
	}

	@Override
	public Map<Long, Integer> getDurationByProductOptionIds(Long companyId,List<Long> productOptionIds,List<String> productOptionAttributeStr) {
		if(Objects.nonNull(productOptionIds) && 0 < productOptionIds.size() 
				&& Objects.nonNull(productOptionAttributeStr) && 0 < productOptionAttributeStr.size()) {
            StringBuilder sb = new StringBuilder();
            Integer size = productOptionIds.size();
            for (int i = 0; i < size; i++) {
            	sb.append(productOptionIds.get(i));
            	if(!Objects.equals(i+1, size)) {
            		sb.append(",");
            	}
			}
            StringBuilder attributeSB = new StringBuilder();
            size = productOptionAttributeStr.size();
            for (int i = 0; i < size; i++) {
            	attributeSB.append("'".concat(productOptionAttributeStr.get(i)).concat("'"));
            	if(!Objects.equals(i+1, size)) {
            		attributeSB.append(",");
            	}
			}
			return productOptionAtrributeDao.getDurationByProductOptionIds(companyId,sb.toString(),attributeSB.toString());
		}
		return null;
	}
}
