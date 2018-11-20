package org.spa.vo.discount;

import java.io.Serializable;

import org.spa.model.discount.DiscountTemplate;
import org.spa.vo.page.Page;

/**
 * @author Ivy 2016-5-12
 */
public class DiscountTemplateListVO extends Page<DiscountTemplate> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long shopId;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


