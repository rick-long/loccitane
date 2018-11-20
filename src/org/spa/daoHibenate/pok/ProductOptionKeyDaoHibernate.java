package org.spa.daoHibenate.pok;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.pok.ProductOptionKeyDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.ProductOptionKey;
import org.springframework.stereotype.Repository;

@Repository
public class ProductOptionKeyDaoHibernate extends BaseDaoHibernate<ProductOptionKey> implements ProductOptionKeyDao{
	
	@Override
	public List<ProductOptionKey> getPOKListOrderByField(final Long categoryId,final Long pCategoryId,final Long companyId) {
		
		
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("select pok1.* from (SELECT pok2.*  FROM PRODUCT_OPTION_KEY pok2 LEFT JOIN PRODUCT_OPTION_KEY_CATEGORY pokc "+ 
								"on pok2.id=pokc.product_option_key_id where pok2.company_id=? and pokc.category_id in(?,?) and pok2.is_active=? "
								+ "ORDER BY FIELD(pokc.category_id,?,?)) pok1 GROUP BY pok1.reference");

		query.addEntity(ProductOptionKey.class);
		

		query.setLong(0, companyId);
		query.setLong(1, categoryId);
		query.setLong(2, pCategoryId);
		query.setBoolean(3, true);
		query.setLong(4, pCategoryId);
		query.setLong(5, categoryId);
		
		List<ProductOptionKey> resultList = query.list();
		return resultList;
	}
}
