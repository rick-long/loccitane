package org.spa.daoHibenate.poa;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.spa.dao.poa.ProductDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoHibernate extends BaseDaoHibernate<Product> implements ProductDao{
	@Override
	public List<Product> listByCategory(Category category, Boolean isOnline, Long shopId) {
		StringBuilder dataHql = new StringBuilder();
		dataHql.append("SELECT p.* FROM product p ");
		dataHql.append(" LEFT JOIN shop_product sp ON p.id=sp.product_id");
		dataHql.append(" WHERE p.is_active= TRUE ");
		if (category!=null){
			dataHql.append(" and  p.category_id ="+category.getId());
		}
		if (isOnline!=null){
			dataHql.append(" and  p.show_on_apps="+ isOnline);
		}
		if (shopId!=null){
			dataHql.append(" and sp.shop_id="+shopId);
		}
		dataHql.append(" GROUP BY p.id");
		SQLQuery query = getSession().createSQLQuery(dataHql.toString());
		query.addEntity(Product.class);
		return (List<Product>)query.list();
	}
	
	@Override
	public List<Long> getShopIds(final Long productId) {
		StringBuilder dataHql = new StringBuilder();
		dataHql.append("SELECT distinct(shop_id) FROM SHOP_PRODUCT ");
		dataHql.append(" WHERE 1=1 ");
		if (productId!=null){
			dataHql.append(" and  product_id ="+productId);
		}
		SQLQuery query = getSession().createSQLQuery(dataHql.toString());
		query.addScalar("shop_id", StandardBasicTypes.LONG);
		return (List<Long>)query.list();
	}
}
