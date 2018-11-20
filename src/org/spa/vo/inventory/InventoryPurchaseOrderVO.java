package org.spa.vo.inventory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryPurchaseOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Long supplierId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String deliveryNoteNumber;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedDeliveryDate;
    
    private String remarks;
    
    private InventoryPurchaseOrderItemVO[] inventoryPurchaseOrderItemVOs;

    private String state;

    private List<Long> shopList;
    private String  token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDeliveryNoteNumber() {
        return deliveryNoteNumber;
    }

    public void setDeliveryNoteNumber(String deliveryNoteNumber) {
        this.deliveryNoteNumber = deliveryNoteNumber;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public InventoryPurchaseOrderItemVO[] getInventoryPurchaseOrderItemVOs() {
        return inventoryPurchaseOrderItemVOs;
    }

    public void setInventoryPurchaseOrderItemVOs(InventoryPurchaseOrderItemVO[] inventoryPurchaseOrderItemVOs) {
        this.inventoryPurchaseOrderItemVOs = inventoryPurchaseOrderItemVOs;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Long> getShopList() {
        return shopList;
    }

    public void setShopList(List<Long> shopList) {
        this.shopList = shopList;
    }
}
