package org.spa.service.inventory;

import org.spa.dao.base.BaseDao;
import org.spa.model.inventory.Inventory;
import org.spa.vo.inventory.InventoryTransactionVO;

/**
 * Created by Ivy on 2016/3/28.
 */
public interface InventoryService extends BaseDao<Inventory>{

    public Inventory get(Long companyId, Long productOptionId);

    public Inventory save(InventoryTransactionVO inventoryTransactionVO);
}
