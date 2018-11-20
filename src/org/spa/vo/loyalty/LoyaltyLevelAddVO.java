package org.spa.vo.loyalty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Ivy on 2016/05/17.
 */
public class LoyaltyLevelAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String reference;
	
	@NotNull
	private Integer discountRequiredSpaPoints;
	
	@NotNull
	private Integer discountMonthLimit;
	
	@NotNull
	private Integer requiredSpaPoints;
	
	@NotNull
	private Integer monthLimit;
	
	
	private Integer rank;

	private Boolean isActive;
	
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getRequiredSpaPoints() {
		return requiredSpaPoints;
	}

	public void setRequiredSpaPoints(Integer requiredSpaPoints) {
		this.requiredSpaPoints = requiredSpaPoints;
	}

	public Integer getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
	}

	public Integer getDiscountMonthLimit() {
		return discountMonthLimit;
	}
	public void setDiscountMonthLimit(Integer discountMonthLimit) {
		this.discountMonthLimit = discountMonthLimit;
	}
	public Integer getDiscountRequiredSpaPoints() {
		return discountRequiredSpaPoints;
	}
	public void setDiscountRequiredSpaPoints(Integer discountRequiredSpaPoints) {
		this.discountRequiredSpaPoints = discountRequiredSpaPoints;
	}
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
}
