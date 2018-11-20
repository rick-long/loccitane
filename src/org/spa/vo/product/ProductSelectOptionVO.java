package org.spa.vo.product;

import java.io.Serializable;

/**
 * @author Ivy 2016-3-28
 */
public class ProductSelectOptionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long categoryId;

    private Boolean showAll;

    private String initialValue;

    private String productName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getShowAll() {
        return showAll;
    }

    public void setShowAll(Boolean showAll) {
        this.showAll = showAll;
    }

    public String getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
