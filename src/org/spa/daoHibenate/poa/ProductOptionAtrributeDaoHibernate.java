package org.spa.daoHibenate.poa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.spa.dao.poa.ProductOptionAtrributeDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.ProductOptionAttribute;
import org.springframework.stereotype.Repository;

@Repository
public class ProductOptionAtrributeDaoHibernate extends BaseDaoHibernate<ProductOptionAttribute> implements ProductOptionAtrributeDao{
	
	@Override
	public ProductOptionAttribute getPOAttrByPoIdAndKeyRefAndCompany(final Long productOptionId, final String poKeyRef,final Long companyId) {
		
		ProductOptionAttribute poa=null;
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("select poa.* from PRODUCT_OPTION_ATTRIBUTE poa LEFT JOIN PRODUCT_OPTION_KEY pok "
				+ "on poa.product_option_key_id=pok.id "
				+ "where poa.product_option_id=? "
				+ "and pok.reference=? "
				+ "and pok.company_id=?");

		query.addEntity(ProductOptionAttribute.class);
		

		query.setLong(0, productOptionId);
		query.setString(1, poKeyRef);
		query.setLong(2, companyId);
		
		List<ProductOptionAttribute> resultList =query.list() ;
		
		if(resultList !=null && resultList.size()>0){
			poa=resultList.get(0);
		}
		return poa;
	}

	@Override
	public Map<Long, Integer> getDurationByProductOptionIds(Long companyId,String productOptionIds,String productOptionAttributeStr) {
		String sql = "select po.id as productId, SUM(poa.value) as value from PRODUCT_OPTION_ATTRIBUTE poa LEFT JOIN PRODUCT_OPTION_KEY pok " + 
	                 "on poa.product_option_key_id = pok.id left JOIN product_option po on po.id = poa.product_option_id "+
				     "where pok.company_id = ? " + 
	                 " and pok.reference in ("+ productOptionAttributeStr +") " +
				     " and poa.product_option_id in ("+productOptionIds+") GROUP BY poa.product_option_id";
		SQLQuery query = getSession().createSQLQuery(sql).addScalar("productId",StandardBasicTypes.LONG)
				.addScalar("value", StandardBasicTypes.INTEGER);
		query.setLong(0, companyId);
		Map<Long,Integer> map = new HashMap<>();
		Iterator iterators= query.list().iterator();
		while (iterators.hasNext()) {
			Object[] obj = (Object[])iterators.next();
			map.put((Long) obj[0], (Integer) obj[1]);
		}
		return map;
	}
}
