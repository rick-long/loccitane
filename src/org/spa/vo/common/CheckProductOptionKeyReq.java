package org.spa.vo.common;

import java.io.Serializable;

public class CheckProductOptionKeyReq implements Serializable {

    private String reference;

    private String isActive;

    private String categoryName;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
