package org.spa.vo.product;

import org.spa.model.product.ProductOption;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ProductOptionListVO extends Page<ProductOption> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String category;

    /**
     * 查询关键字
     */
	private String key;

    private Long supplierId;

	private String isActive;

    private Long memberId;
    
    private Long shopId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getShopId() {
		return shopId;
	}
    public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
    public Long getMemberId() {
		return memberId;
	}
    public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

}
