package org.spa.vo.inventory;

import org.spa.model.company.Company;
import org.spa.model.inventory.Inventory;
import org.spa.model.shop.Shop;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryTransferVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Company company;

    @NotNull
    private Long inventoryId;

    private Inventory inventory;

    @NotNull
    private Long fromShopId;

    private Shop fromShop;

    @NotNull
    private Long toShopId;

    private Shop toShop;

    private int qty;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;

    private String isActive;

    public Long getFromShopId() {
        return fromShopId;
    }

    public void setFromShopId(Long fromShopId) {
        this.fromShopId = fromShopId;
    }

    public Long getToShopId() {
        return toShopId;
    }

    public void setToShopId(Long toShopId) {
        this.toShopId = toShopId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Shop getFromShop() {
        return fromShop;
    }

    public void setFromShop(Shop fromShop) {
        this.fromShop = fromShop;
    }

    public Shop getToShop() {
        return toShop;
    }

    public void setToShop(Shop toShop) {
        this.toShop = toShop;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}


