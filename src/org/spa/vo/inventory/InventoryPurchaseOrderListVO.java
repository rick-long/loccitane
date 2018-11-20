package org.spa.vo.inventory;

import org.spa.model.inventory.InventoryPurchaseOrder;
import org.spa.vo.page.Page;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryPurchaseOrderListVO extends Page<InventoryPurchaseOrder> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long supplierId;

    private String status;

    private String isActive;
    
    private String reference;
	private String date;
	private String deliveryNoteNumber;
	private String expectedDeliveryDate;
	
	

    public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDeliveryNoteNumber() {
		return deliveryNoteNumber;
	}

	public void setDeliveryNoteNumber(String deliveryNoteNumber) {
		this.deliveryNoteNumber = deliveryNoteNumber;
	}

	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


