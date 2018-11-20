package org.spa.service.inventory;

import org.spa.dao.base.BaseDao;
import org.spa.model.inventory.InventoryPurchaseOrderShipment;
import org.spa.vo.inventory.InventoryPurchaseOrderShipmentVO;

/**
 * Created by Ivy on 2016/3/28.
 */
public interface InventoryPurchaseOrderShipmentService extends BaseDao<InventoryPurchaseOrderShipment>{

    public void save(InventoryPurchaseOrderShipmentVO shipmentVO);
}
