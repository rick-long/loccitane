package org.spa.vo.sales;

import java.io.Serializable;

public class SpendingSummaryVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prodType;
	private Double value;
	private Double oneMonth;
	private Double threeMonth;
	private Double sixMonth;
	private Double twelveMonth;
	private Double twentyFourMonth;
	private Double allMonth;
	public SpendingSummaryVO() {
		// TODO Auto-generated constructor stub
	}
	public SpendingSummaryVO(String prodType, Double oneMonth, Double threeMonth, Double sixMonth, Double twelveMonth,Double twentyFourMonth,Double allMonth) {
		super();
		this.prodType = prodType;
		this.oneMonth = oneMonth;
		this.threeMonth = threeMonth;
		this.sixMonth = sixMonth;
		this.twelveMonth = twelveMonth;
		this.twentyFourMonth = twentyFourMonth;
		this.allMonth=allMonth;
	}
	
	
	public Double getOneMonth() {
		return oneMonth;
	}
	public void setOneMonth(Double oneMonth) {
		this.oneMonth = oneMonth;
	}
	public Double getThreeMonth() {
		return threeMonth;
	}
	public void setThreeMonth(Double threeMonth) {
		this.threeMonth = threeMonth;
	}
	public Double getSixMonth() {
		return sixMonth;
	}
	public void setSixMonth(Double sixMonth) {
		this.sixMonth = sixMonth;
	}
	public Double getTwelveMonth() {
		return twelveMonth;
	}
	public void setTwelveMonth(Double twelveMonth) {
		this.twelveMonth = twelveMonth;
	}
	public Double getTwentyFourMonth() {
		return twentyFourMonth;
	}
	public void setTwentyFourMonth(Double twentyFourMonth) {
		this.twentyFourMonth = twentyFourMonth;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getAllMonth() {
		return allMonth;
	}

	public void setAllMonth(Double allMonth) {
		this.allMonth = allMonth;
	}
}
