package org.spa.vo.bundle;

import org.spa.model.bundle.ProductBundle;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * @author Ivy 2018-7-13
 */
public class BundleListVO extends Page<ProductBundle> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long shopId;
    private String code;
    private String startTime;
    private String endTime;
    
    private String isActive;

    private Long categoryId;
	private Long productId;

	private String bundleName;
	private String shopName;
	private String description;
	private String productOptionGroup;
	private Double totalBundlePrice;

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

    public String getCode() {
		return code;
	}
    public void setCode(String code) {
		this.code = code;
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

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductOptionGroup() {
		return productOptionGroup;
	}

	public void setProductOptionGroup(String productOptionGroup) {
		this.productOptionGroup = productOptionGroup;
	}

	public Double getTotalBundlePrice() {
		return totalBundlePrice;
	}

	public void setTotalBundlePrice(Double totalBundlePrice) {
		this.totalBundlePrice = totalBundlePrice;
	}
}


