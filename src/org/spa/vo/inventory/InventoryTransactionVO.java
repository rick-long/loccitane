package org.spa.vo.inventory;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import org.spa.model.order.PurchaseItem;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryTransactionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    public InventoryTransactionVO() {
    }

    private Company company;

    @NotNull
    private Long shopId;

    private Shop shop;

    @NotNull
    private Long productOptionId;

    private ProductOption productOption;

    private int qty;

    @NotBlank
    private String direction;

    @NotBlank
    private String transactionType;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;

    private String remarks;

    private PurchaseItem item;
    
    public PurchaseItem getItem() {
		return item;
	}
    public void setItem(PurchaseItem item) {
		this.item = item;
	}
    
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
