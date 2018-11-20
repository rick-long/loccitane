package org.spa.service.inventory;

import org.spa.dao.base.BaseDao;
import org.spa.model.inventory.InventoryTransaction;
import org.spa.vo.inventory.InventoryTransactionVO;
import org.spa.vo.inventory.InventoryTransferVO;

/**
 * Created by Ivy on 2016/3/28.
 */
public interface InventoryTransactionService extends BaseDao<InventoryTransaction>{

    public void save(InventoryTransactionVO[] inventoryTransactionVOs);

    public void save(InventoryTransactionVO transactionVO);

    public void transfer(InventoryTransferVO inventoryTransferVO);
}
