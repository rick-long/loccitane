package org.spa.vo.loyalty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Ivy on 2016/05/17.
 */
public class AwardRedemptionAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank
	private String name;
	private String description;
	private String redeemType;
	private String redeemChannel;
	
	private Long productOptionId;
	private String productName;
	
	@NotNull
	private double requiredLp;
	
	private double requiredAmount;


	private Double beWorth;
	
	@NotBlank
	private String startDate;
	private String endDate;
	
	private Boolean isActive;

	private String validAt;
	
	public Double getBeWorth() {
		return beWorth;
	}
	public void setBeWorth(Double beWorth) {
		this.beWorth = beWorth;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Long getProductOptionId() {
		return productOptionId;
	}
	
	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRedeemType() {
		return redeemType;
	}

	public void setRedeemType(String redeemType) {
		this.redeemType = redeemType;
	}

	public String getRedeemChannel() {
		return redeemChannel;
	}

	public void setRedeemChannel(String redeemChannel) {
		this.redeemChannel = redeemChannel;
	}

	public double getRequiredLp() {
		return requiredLp;
	}

	public void setRequiredLp(double requiredLp) {
		this.requiredLp = requiredLp;
	}

	public double getRequiredAmount() {
		return requiredAmount;
	}

	public void setRequiredAmount(double requiredAmount) {
		this.requiredAmount = requiredAmount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getValidAt() {
		return validAt;
	}

	public void setValidAt(String validAt) {
		this.validAt = validAt;
	}
}
