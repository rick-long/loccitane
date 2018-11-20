package org.spa.vo.loyalty;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.user.User;

/**
 * Created by Ivy on 2016/05/17.
 */
public class UserLoyaltyLevelVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LoyaltyLevel loyaltyLevel;
	@NotNull
	private Long loyaltyLevelId;
	
	private User user;
	@NotNull
	private Long memberId;

	private String username;
	
	@NotNull
	private Integer monthLimit;
	
	private Date joinDate;
	private String remarks;
	
	private Boolean sendNotificationEmail;
	
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getSendNotificationEmail() {
		return sendNotificationEmail;
	}
	public void setSendNotificationEmail(Boolean sendNotificationEmail) {
		this.sendNotificationEmail = sendNotificationEmail;
	}
	public LoyaltyLevel getLoyaltyLevel() {
		return loyaltyLevel;
	}
	public void setLoyaltyLevel(LoyaltyLevel loyaltyLevel) {
		this.loyaltyLevel = loyaltyLevel;
	}
	public Long getLoyaltyLevelId() {
		return loyaltyLevelId;
	}
	public void setLoyaltyLevelId(Long loyaltyLevelId) {
		this.loyaltyLevelId = loyaltyLevelId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Integer getMonthLimit() {
		return monthLimit;
	}
	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
