package org.spa.vo.report;

import java.io.Serializable;
public class DailyReportVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shopName;
	private Long shopId;
	private Double sales;
	private Double revenue;
	private SalesDetailsSummaryVO treatmentSummaryVo;
	private SalesDetailsSummaryVO packageSummaryVo;
	private SalesDetailsSummaryVO voucherSummaryVo;
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Double getSales() {
		return sales;
	}
	public void setSales(Double sales) {
		this.sales = sales;
	}
	public Double getRevenue() {
		return revenue;
	}
	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}
	public SalesDetailsSummaryVO getPackageSummaryVo() {
		return packageSummaryVo;
	}
	public void setPackageSummaryVo(SalesDetailsSummaryVO packageSummaryVo) {
		this.packageSummaryVo = packageSummaryVo;
	}
	public SalesDetailsSummaryVO getTreatmentSummaryVo() {
		return treatmentSummaryVo;
	}
	public void setTreatmentSummaryVo(SalesDetailsSummaryVO treatmentSummaryVo) {
		this.treatmentSummaryVo = treatmentSummaryVo;
	}
	public SalesDetailsSummaryVO getVoucherSummaryVo() {
		return voucherSummaryVo;
	}
	public void setVoucherSummaryVo(SalesDetailsSummaryVO voucherSummaryVo) {
		this.voucherSummaryVo = voucherSummaryVo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
