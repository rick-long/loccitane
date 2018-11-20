package org.spa.daoHibenate.pdk;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.pdk.ProductDescriptionKeyDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.ProductDescriptionKey;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDescriptionKeyDaoHibernate extends BaseDaoHibernate<ProductDescriptionKey> implements ProductDescriptionKeyDao{
	
	@Override
	public List<ProductDescriptionKey> getPdKListOrderByField(final Long categoryId,final Long pCategoryId,final Long companyId) {
		
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("select pdk1.* from (SELECT pdk2.*  FROM PRODUCT_DESCRIPTION_KEY pdk2 LEFT JOIN PRODUCT_DESCRIPTION_KEY_CATEGORY pdkc "
								+ "on pdk2.id=pdkc.product_description_key_id where pdk2.company_id=? and pdkc.category_id in (?,?) and pdk2.is_active=? "
								+ "ORDER BY FIELD(pdkc.category_id,?,?)) pdk1 GROUP BY pdk1.reference");

		query.addEntity(ProductDescriptionKey.class);
		
		query.setLong(0, companyId);
		query.setLong(1, categoryId);
		query.setLong(2, pCategoryId);
		query.setBoolean(3, true);
		query.setLong(4, pCategoryId);
		query.setLong(5, categoryId);
		
		List<ProductDescriptionKey> resultList = query.list();
		return resultList;
	}
}
