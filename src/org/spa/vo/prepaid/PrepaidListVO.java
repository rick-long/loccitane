package org.spa.vo.prepaid;
import java.io.Serializable;
import java.util.Date;

import org.spa.model.prepaid.Prepaid;
import org.spa.model.product.ProductOption;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/04/11.
 */
public class PrepaidListVO extends Page<Prepaid> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reference;
	private Long memberId;
	private String username;
//	private Long categoryId;
	private String prepaidType;
	private Long shopId;
	private String isActive;

	private Long categoryId;
//	private Long productId;
	private Long productOptionId;
	private ProductOption po;

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

	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
//	public Long getProductId() {
//		return productId;
//	}
//	public void setProductId(Long productId) {
//		this.productId = productId;
//	}
	public Long getProductOptionId() {
		return productOptionId;
	}
	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}
	public ProductOption getPo() {
		return po;
	}
	public void setPo(ProductOption po) {
		this.po = po;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPrepaidType() {
		return prepaidType;
	}
	public void setPrepaidType(String prepaidType) {
		this.prepaidType = prepaidType;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

//	public Long getCategoryId() {
//		return categoryId;
//	}
//	public void setCategoryId(Long categoryId) {
//		this.categoryId = categoryId;
//	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
