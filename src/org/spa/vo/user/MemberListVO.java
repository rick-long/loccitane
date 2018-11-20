package org.spa.vo.user;

import java.io.Serializable;
import org.spa.model.user.User;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/01/16.
 */
public class MemberListVO extends Page<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;
	private String username;
	private String fullName;
	private String enabled;
	private Long shopId;
	private Long loyaltyLevelId;
	private Long userGroupId;
	
	public Long getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}
	public Long getLoyaltyLevelId() {
		return loyaltyLevelId;
	}
	public void setLoyaltyLevelId(Long loyaltyLevelId) {
		this.loyaltyLevelId = loyaltyLevelId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
