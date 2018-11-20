package org.spa.vo.inventory;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryPurchaseOrderShipmentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long inventoryPurchaseOrderId;
   
    @NotBlank
    private String deliveryNumber;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    private InventoryPurchaseOrderShipmentItemVO[] inventoryPurchaseOrderShipmentItemVOs;
    
    public Long getInventoryPurchaseOrderId() {
        return inventoryPurchaseOrderId;
    }

    public void setInventoryPurchaseOrderId(Long inventoryPurchaseOrderId) {
        this.inventoryPurchaseOrderId = inventoryPurchaseOrderId;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public InventoryPurchaseOrderShipmentItemVO[] getInventoryPurchaseOrderShipmentItemVOs() {
        return inventoryPurchaseOrderShipmentItemVOs;
    }

    public void setInventoryPurchaseOrderShipmentItemVOs(InventoryPurchaseOrderShipmentItemVO[] inventoryPurchaseOrderShipmentItemVOs) {
        this.inventoryPurchaseOrderShipmentItemVOs = inventoryPurchaseOrderShipmentItemVOs;
    }
}
