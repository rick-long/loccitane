package org.spa.service.inventory;

import org.spa.dao.base.BaseDao;
import org.spa.model.inventory.InventoryWarehouse;
import org.spa.vo.inventory.InventoryTransactionVO;

/**
 * Created by Ivy on 2016/3/28.
 */
public interface InventoryWarehouseService extends BaseDao<InventoryWarehouse>{

    public InventoryWarehouse get(Long shopId, Long inventoryId);

    public InventoryWarehouse saveOrUpdate(InventoryTransactionVO inventoryTransactionVO);
    
    public Integer getProductOptionQtyByShop(Long productOptionId,Long shopId);
}
