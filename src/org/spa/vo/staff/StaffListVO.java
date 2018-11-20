package org.spa.vo.staff;

import java.io.Serializable;
import org.spa.model.user.User;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/03/30.
 */
public class StaffListVO extends Page<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;
	private String username;
	private String enabled;
	private Long sysRoleId;
	private Long homeShopId;

	private String homeShopName;
	private String roleName;
	private String loginAccount;
	private String displayName;
	private String mobilePhone;
	private String emailString;
	private String gender;
	private String dateOfBirth;
	private String joinDate;
	private String isEnabled;
	private String supportOnlineBooking;

	public Long getHomeShopId() {
		return homeShopId;
	}
	public void setHomeShopId(Long homeShopId) {
		this.homeShopId = homeShopId;
	}
	public Long getSysRoleId() {
		return sysRoleId;
	}
	public void setSysRoleId(Long sysRoleId) {
		this.sysRoleId = sysRoleId;
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

	public String getHomeShopName() {
		return homeShopName;
	}

	public void setHomeShopName(String homeShopName) {
		this.homeShopName = homeShopName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmailString() {
		return emailString;
	}

	public void setEmailString(String emailString) {
		this.emailString = emailString;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getSupportOnlineBooking() {
		return supportOnlineBooking;
	}

	public void setSupportOnlineBooking(String supportOnlineBooking) {
		this.supportOnlineBooking = supportOnlineBooking;
	}
}
