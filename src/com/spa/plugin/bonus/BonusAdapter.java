package com.spa.plugin.bonus;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivy on 2016-8-3
 */
public class BonusAdapter implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Double targetAmount;
	private Double totalSales;
	
	private String categoryRef;
	
    private Double bonus;
    
    private Date currentDate;
    
    public BonusAdapter() {
		// 
	}

	public Double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public Double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}

	public String getCategoryRef() {
		return categoryRef;
	}
	public void setCategoryRef(String categoryRef) {
		this.categoryRef = categoryRef;
	}
	
	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
    
}
