package org.spa.daoHibenate.inventory;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.spa.dao.inventory.InventoryWarehouseDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.inventory.InventoryWarehouse;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryWarehouseDaoHibernate extends BaseDaoHibernate<InventoryWarehouse> implements InventoryWarehouseDao{

	@Override
	public Integer getProductOptionQtyByShop(final Long productOptionId, final Long shopId) {

		Session session = getSession();
		StringBuilder sb=new StringBuilder();
		sb.append("select sum(iw.qty) as cqty from INVENTORY_WAREHOUSE iw LEFT JOIN INVENTORY i on iw.inventory_id = i.id ");
		sb.append("where i.product_option_id= ? ");
		sb.append("and iw.is_active =1 ");
		if(shopId !=null && shopId.longValue()>0){
			sb.append("and iw.shop_id= ? ");
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		
		query.addScalar("cqty", StandardBasicTypes.INTEGER);
		
		query.setLong(0,productOptionId);
		
		if(shopId !=null && shopId.longValue()>0){
			query.setLong(1,shopId );
		}
		return (Integer) query.uniqueResult();
	}
	
	
}
