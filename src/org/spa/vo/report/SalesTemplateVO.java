package org.spa.vo.report;

import java.io.Serializable;

import org.spa.model.order.PurchaseItem;
import org.spa.vo.page.Page;

public class SalesTemplateVO extends Page<PurchaseItem> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reference;
	private String shopName;
	private String date;
	private String clientName;
	private String hotelGuest;
	private String email;
	/*private String categry;*/
	private String product;
	private String therapist;
	
	private Integer qty;
	private Double itemAmount;
	
	private Double effectiveValue;
	private Double discount;
	private Double packageVal;
	private Double voucherVal;
	private Double fullPrice;
	private Double costOfProduct;
	private String payment;
	
	private String requested;

	
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getHotelGuest() {
		return hotelGuest;
	}

	public void setHotelGuest(String hotelGuest) {
		this.hotelGuest = hotelGuest;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getCategry() {
//		return categry;
//	}
//
//	public void setCategry(String categry) {
//		this.categry = categry;
//	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getTherapist() {
		return therapist;
	}

	public void setTherapist(String therapist) {
		this.therapist = therapist;
	}

	public Double getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Double getEffectiveValue() {
		return effectiveValue;
	}

	public void setEffectiveValue(Double effectiveValue) {
		this.effectiveValue = effectiveValue;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getPackageVal() {
		return packageVal;
	}

	public void setPackageVal(Double packageVal) {
		this.packageVal = packageVal;
	}

	public Double getVoucherVal() {
		return voucherVal;
	}

	public void setVoucherVal(Double voucherVal) {
		this.voucherVal = voucherVal;
	}

	public Double getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(Double fullPrice) {
		this.fullPrice = fullPrice;
	}

	public Double getCostOfProduct() {
		return costOfProduct;
	}

	public void setCostOfProduct(Double costOfProduct) {
		this.costOfProduct = costOfProduct;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getRequested() {
		return requested;
	}

	public void setRequested(String requested) {
		this.requested = requested;
	}
	
	
	
}
