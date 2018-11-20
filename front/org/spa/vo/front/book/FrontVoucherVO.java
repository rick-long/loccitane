package org.spa.vo.front.book;

import java.io.Serializable;

import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;

public class FrontVoucherVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long memberId;
	
	private Long pickUpLocation;
	private Shop shop;//pick up shop
	
	private String pickUpType;
	
	private String additionalEmail;
	
	private String additionalName;
	
	private String additionalMessage;
	
	private String prepaidType;
	
	private String expiryDate;
	
	private String paymentName;
	
	private Double prepaidValue;
	private Long productOptionId;
	private ProductOption po;
	
	private String promotionCode;
	
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public String getPickUpType() {
		return pickUpType;
	}
	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}
	public ProductOption getPo() {
		return po;
	}
	public void setPo(ProductOption po) {
		this.po = po;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getPickUpLocation() {
		return pickUpLocation;
	}
	public void setPickUpLocation(Long pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}
	public String getAdditionalEmail() {
		return additionalEmail;
	}
	public void setAdditionalEmail(String additionalEmail) {
		this.additionalEmail = additionalEmail;
	}
	public String getAdditionalName() {
		return additionalName;
	}
	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}
	public String getAdditionalMessage() {
		return additionalMessage;
	}
	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}
	public String getPrepaidType() {
		return prepaidType;
	}
	public void setPrepaidType(String prepaidType) {
		this.prepaidType = prepaidType;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public Double getPrepaidValue() {
		return prepaidValue;
	}
	public void setPrepaidValue(Double prepaidValue) {
		this.prepaidValue = prepaidValue;
	}
	public Long getProductOptionId() {
		return productOptionId;
	}
	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}
	
	
}
