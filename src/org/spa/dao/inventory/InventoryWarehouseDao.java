package org.spa.dao.inventory;


public interface InventoryWarehouseDao {

	public Integer getProductOptionQtyByShop(final Long productOptionId,final Long shopId);
}
