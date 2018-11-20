package org.spa.service.inventory;

import org.spa.dao.base.BaseDao;
import org.spa.model.inventory.InventoryPurchaseOrder;
import org.spa.vo.inventory.InventoryPurchaseOrderVO;

/**
 * Created by Ivy on 2016/3/28.
 */
public interface InventoryPurchaseOrderService extends BaseDao<InventoryPurchaseOrder>{

    public void saveOrUpdate(InventoryPurchaseOrderVO inventoryPurchaseOrderVO);

    public void updateStatus(InventoryPurchaseOrder inventoryPurchaseOrder);

}
