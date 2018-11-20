package org.spa.vo.inventory;

import org.spa.model.inventory.InventoryTransaction;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryTransactionListVO extends Page<InventoryTransaction> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long shopId;

    private Long brandId;
    
    private Long productOptionId;
    private Long categoryId;
    
    private String productName;

    private String transactionType;

    private String isActive;
    private String fromDate;


    private String toDate;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getBrandId() {
		return brandId;
	}
    public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
    
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

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public Long getCategoryId() {
		return categoryId;
	}
    public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}


