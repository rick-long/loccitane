package org.spa.vo.report;

import java.io.Serializable;

public class SummaryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double treatmentRevenue;
	private Double productRevenue;
	private Double packagesRevenue;
	private Double vouchersRevenue;
	private Double hairsalonRevenue;
	
	private Integer numOfClients;
	
	public Double getHairsalonRevenue() {
		return hairsalonRevenue;
	}
	public void setHairsalonRevenue(Double hairsalonRevenue) {
		this.hairsalonRevenue = hairsalonRevenue;
	}
	public Double getVouchersRevenue() {
		return vouchersRevenue;
	}
	public void setVouchersRevenue(Double vouchersRevenue) {
		this.vouchersRevenue = vouchersRevenue;
	}
	public Integer getNumOfClients() {
		return numOfClients;
	}
	public void setNumOfClients(Integer numOfClients) {
		this.numOfClients = numOfClients;
	}
	
	public Double getPackagesRevenue() {
		return packagesRevenue;
	}
	public void setPackagesRevenue(Double packagesRevenue) {
		this.packagesRevenue = packagesRevenue;
	}
	public Double getProductRevenue() {
		return productRevenue;
	}
	public void setProductRevenue(Double productRevenue) {
		this.productRevenue = productRevenue;
	}
	public Double getTreatmentRevenue() {
		return treatmentRevenue;
	}
	public void setTreatmentRevenue(Double treatmentRevenue) {
		this.treatmentRevenue = treatmentRevenue;
	}
	
}
