package org.spa.vo.app.member;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.spa.vo.user.AddressVO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ivy on 2016/01/16.
 */
public class AppsMemberAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fullName;
	private String username;
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@Email
	private String email;
	
	private String password;
	
	private String confirmPassword;
	
	private String gender;

	private String mobilePhone;
	
	private String homePhone;
	
	@NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	private String notification;
	
	private String preferredContact;
	
	private String preferredTherapistId;
	
	@NotBlank
	private String shopId;
	
	private String remarks;
	
	private AddressVO addressVO;
	
	private String airmilesMembershipNumber;
	
	private Long communicationChannelId;
	
	private Boolean optedOut;
	
	private Long[] usergroupsIds;
	public Long[] getUsergroupsIds() {
		return usergroupsIds;
	}
	public void setUsergroupsIds(Long[] usergroupsIds) {
		this.usergroupsIds = usergroupsIds;
	}
	
	public Boolean getOptedOut() {
		return optedOut;
	}
	public void setOptedOut(Boolean optedOut) {
		this.optedOut = optedOut;
	}
	
	public Long getCommunicationChannelId() {
		return communicationChannelId;
	}
	public void setCommunicationChannelId(Long communicationChannelId) {
		this.communicationChannelId = communicationChannelId;
	}
	
	public String getAirmilesMembershipNumber() {
		return airmilesMembershipNumber;
	}
	public void setAirmilesMembershipNumber(String airmilesMembershipNumber) {
		this.airmilesMembershipNumber = airmilesMembershipNumber;
	}
	
	public AddressVO getAddressVO() {
		return addressVO;
	}
	public void setAddressVO(AddressVO addressVO) {
		this.addressVO = addressVO;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public String getHomePhone() {
		return homePhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getPreferredContact() {
		return preferredContact;
	}
	public void setPreferredContact(String preferredContact) {
		this.preferredContact = preferredContact;
	}
	public String getPreferredTherapistId() {
		return preferredTherapistId;
	}
	public void setPreferredTherapistId(String preferredTherapistId) {
		this.preferredTherapistId = preferredTherapistId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
