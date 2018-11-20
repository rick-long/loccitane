package com.spa.jxlsBean.importDemo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ivy on 2016/11/7.
 */
public class StaffImportJxlsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String homeShop;

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String displayName;

	@NotBlank
	@Email
	private String email;

	private String mobile;

	private String roleType;

	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dob;

	private String gender;

	private String pin;

	private String staffNumber;

	private Date joinDate;

	private String employmentType;

	private String standardSalary;

	private String goodsTarget;

	private String treatmentTarget;

	private String commissionGroup;

	private String allowOnlineBooking;

	private String status;

	private String returnError;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getHomeShop() {
		return homeShop;
	}

	public void setHomeShop(String homeShop) {
		this.homeShop = homeShop;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getStandardSalary() {
		return standardSalary;
	}

	public void setStandardSalary(String standardSalary) {
		this.standardSalary = standardSalary;
	}

	public String getGoodsTarget() {
		return goodsTarget;
	}

	public void setGoodsTarget(String goodsTarget) {
		this.goodsTarget = goodsTarget;
	}

	public String getTreatmentTarget() {
		return treatmentTarget;
	}

	public void setTreatmentTarget(String treatmentTarget) {
		this.treatmentTarget = treatmentTarget;
	}

    public String getCommissionGroup() {
        return commissionGroup;
    }

    public void setCommissionGroup(String commissionGroup) {
        this.commissionGroup = commissionGroup;
    }

    public String getAllowOnlineBooking() {
		return allowOnlineBooking;
	}

	public void setAllowOnlineBooking(String allowOnlineBooking) {
		this.allowOnlineBooking = allowOnlineBooking;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturnError() {
		return returnError;
	}

	public void setReturnError(String returnError) {
		this.returnError = returnError;
	}
}
