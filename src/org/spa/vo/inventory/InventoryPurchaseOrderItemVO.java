package org.spa.vo.inventory;

import org.spa.model.product.ProductOption;

import java.io.Serializable;

/**
 * @author Ivy 2016-3-28
 */
public class InventoryPurchaseOrderItemVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Integer qty;

    private Long productOptionId;

    private ProductOption productOption;

    private Double total;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
