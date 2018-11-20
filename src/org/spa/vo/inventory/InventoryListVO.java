package org.spa.vo.inventory;

import org.spa.model.inventory.Inventory;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryListVO extends Page<Inventory> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long shopId;

    private Long brandId;
    
    private String productName;

    private int qty = -1;
    private Long categoryId;
    private Long productOptionId;

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
		return brandId;
	}
    public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
    
    private String isActive;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}


