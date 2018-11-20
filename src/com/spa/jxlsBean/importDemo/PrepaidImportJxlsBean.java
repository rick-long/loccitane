package com.spa.jxlsBean.importDemo;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ivy on 2018/08/27.
 */
public class PrepaidImportJxlsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String shopName;//店铺名称

	@NotBlank
	private String clientUsername;//用户名称

	private String prepaidType;

	@NotBlank
	private String prepaidName;//面值

	@NotBlank
	private String prepaidNumber;//对应值

	private String prepaidValue;

	private String initialValue;

	@NotBlank
	private String remainValue;//剩余金额

	@NotBlank
	private String purchaseDate;//

	@NotBlank
	private String expiryDate;//过期时间

	private String commissionRate;

	private String productOption;

	private String payment1;//(1:Cash:1000) / Cash is the name of payment method

	private String payment2;// same as payment1.

	private String payment3;// same as payment1.

	private String therapists1;//(1:Anna)

	private String therapists2;

	private String therapists3;

	private String remarks;

	private String topUp;

	private String free;

	private String status;

	private String returnError;

	public String getReturnError() {
		return returnError;
	}

	public void setReturnError(String returnError) {
		this.returnError = returnError;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getClientUsername() {
		return clientUsername;
	}

	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}

	public String getPrepaidType() {
		return prepaidType;
	}

	public void setPrepaidType(String prepaidType) {
		this.prepaidType = prepaidType;
	}

	public String getPrepaidName() {
		return prepaidName;
	}

	public void setPrepaidName(String prepaidName) {
		this.prepaidName = prepaidName;
	}

	public String getPrepaidNumber() {
		return prepaidNumber;
	}

	public void setPrepaidNumber(String prepaidNumber) {
		this.prepaidNumber = prepaidNumber;
	}

	public String getPrepaidValue() {
		return prepaidValue;
	}

	public void setPrepaidValue(String prepaidValue) {
		this.prepaidValue = prepaidValue;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public String getRemainValue() {
		return remainValue;
	}

	public void setRemainValue(String remainValue) {
		this.remainValue = remainValue;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getProductOption() {
		return productOption;
	}

	public void setProductOption(String productOption) {
		this.productOption = productOption;
	}

	public String getPayment1() {
		return payment1;
	}

	public void setPayment1(String payment1) {
		this.payment1 = payment1;
	}

	public String getPayment2() {
		return payment2;
	}

	public void setPayment2(String payment2) {
		this.payment2 = payment2;
	}

	public String getPayment3() {
		return payment3;
	}

	public void setPayment3(String payment3) {
		this.payment3 = payment3;
	}

	public String getTherapists1() {
		return therapists1;
	}

	public void setTherapists1(String therapists1) {
		this.therapists1 = therapists1;
	}

	public String getTherapists2() {
		return therapists2;
	}

	public void setTherapists2(String therapists2) {
		this.therapists2 = therapists2;
	}

	public String getTherapists3() {
		return therapists3;
	}

	public void setTherapists3(String therapists3) {
		this.therapists3 = therapists3;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTopUp() {
		return topUp;
	}

	public void setTopUp(String topUp) {
		this.topUp = topUp;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
