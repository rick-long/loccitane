package org.spa.vo.discount;

import org.spa.model.discount.DiscountRule;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * @author Ivy 2016-5-13
 */
public class DiscountRuleListVO extends Page<DiscountRule> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long shopId;
    private String description;
    private String startTime;
    private String endTime;
    
    private String isActive;

    private Long categoryId;
	private Long productId;
	

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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

    public String getDescription() {
		return description;
	}
    public void setDescription(String description) {
		this.description = description;
	}
    public String getStartTime() {
		return startTime;
	}
    public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
    public String getEndTime() {
		return endTime;
	}
    public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}


