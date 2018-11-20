package com.spa.plugin.discount;

public class DiscountTarget{

	private Double targetAmount;
	private Integer targetQty;
	
	public DiscountTarget() {
	}
	public Double getTargetAmount() {
		return targetAmount;
	}
	public Integer getTargetQty() {
		return targetQty;
	}
	public void setTargetAmount(Double targetAmount) {
		this.targetAmount = targetAmount;
	}
	public void setTargetQty(Integer targetQty) {
		this.targetQty = targetQty;
	}
}
