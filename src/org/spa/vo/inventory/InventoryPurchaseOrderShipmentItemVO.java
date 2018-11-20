package org.spa.vo.inventory;

import org.spa.model.product.ProductOption;

import java.io.Serializable;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryPurchaseOrderShipmentItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productOptionId;

    private ProductOption productOption;
    
    private int qty;
    
    private InventoryTransactionVO[] inventoryTransactionVOs;

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public InventoryTransactionVO[] getInventoryTransactionVOs() {
        return inventoryTransactionVOs;
    }

    public void setInventoryTransactionVOs(InventoryTransactionVO[] inventoryTransactionVOs) {
        this.inventoryTransactionVOs = inventoryTransactionVOs;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }
}
