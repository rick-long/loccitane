package org.spa.vo.inventory;

import java.io.Serializable;

/**
 * @author Ivy 2016-3-28
 */
public class TransactionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private InventoryTransactionVO[] inventoryTransactionVOs;

    public InventoryTransactionVO[] getInventoryTransactionVOs() {
        return inventoryTransactionVOs;
    }

    public void setInventoryTransactionVOs(InventoryTransactionVO[] inventoryTransactionVOs) {
        this.inventoryTransactionVOs = inventoryTransactionVOs;
    }
}
