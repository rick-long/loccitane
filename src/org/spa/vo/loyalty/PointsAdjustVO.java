package org.spa.vo.loyalty;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.user.User;

/**
 * Created by Ivy on 2016/05/17.
 */
public class PointsAdjustVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long memberId;
	private User user;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String action;
	
	@NotNull
	private Double adjustPoints;
	
	@NotNull
	private Integer monthLimit;
	
	private Date earnDate;
	
	private PurchaseOrder purchaseOrder;
	private String earnChannel;
	
	private String remarks;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getEarnChannel() {
		return earnChannel;
	}
	public void setEarnChannel(String earnChannel) {
		this.earnChannel = earnChannel;
	}
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getAdjustPoints() {
		return adjustPoints;
	}

	public void setAdjustPoints(Double adjustPoints) {
		this.adjustPoints = adjustPoints;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
	}

	public Date getEarnDate() {
		return earnDate;
	}

	public void setEarnDate(Date earnDate) {
		this.earnDate = earnDate;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	
}
