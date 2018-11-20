package org.spa.vo.user;

import java.io.Serializable;

public class ClientViewVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shopName;
	private String prodType;
	private Double totalSalesValue;
	private Integer totalTreatments;
	
	public ClientViewVO() {
		// TODO Auto-generated constructor stub
	}
	public ClientViewVO(String shopName, String prodType, Double totalSalesValue,Integer totalTreatments) {
		super();
		this.shopName = shopName;
		this.prodType = prodType;
		this.totalSalesValue = totalSalesValue;
		this.totalTreatments = totalTreatments;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getTotalTreatments() {
		return totalTreatments;
	}

	public void setTotalTreatments(Integer totalTreatments) {
		this.totalTreatments = totalTreatments;
	}
	public Double getTotalSalesValue() {
		return totalSalesValue;
	}
	public void setTotalSalesValue(Double totalSales) {
		this.totalSalesValue = totalSales;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
}
