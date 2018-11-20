package org.spa.vo.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/01/16.
 */
public class MemberEditVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String password;
	private String newPassword;
	private String confirmPassword;
	
	private String username;
	
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;
	
	@NotBlank
	private String enabled;
	
	@NotBlank
	@Email
	private String email;
	
	private String mobilePhone;
	
	private String homePhone;
	private String gender;

	@NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	private List<String> notification;
	
	private String preferredContact;
	
	private String preferredTherapistId;

	private String preferredTherapistId1;
	private String preferredTherapistId2;
	private String preferredTherapistId3;

	private String preferredShop;
	private String preferredRoom;
	
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
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	public List<String> getNotification() {
		return notification;
	}
	public void setNotification(List<String> notification) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPreferredTherapistId1() {
		return preferredTherapistId1;
	}

	public void setPreferredTherapistId1(String preferredTherapistId1) {
		this.preferredTherapistId1 = preferredTherapistId1;
	}

	public String getPreferredTherapistId2() {
		return preferredTherapistId2;
	}

	public void setPreferredTherapistId2(String preferredTherapistId2) {
		this.preferredTherapistId2 = preferredTherapistId2;
	}

	public String getPreferredTherapistId3() {
		return preferredTherapistId3;
	}

	public void setPreferredTherapistId3(String preferredTherapistId3) {
		this.preferredTherapistId3 = preferredTherapistId3;
	}

	public String getPreferredShop() {
		return preferredShop;
	}

	public void setPreferredShop(String preferredShop) {
		this.preferredShop = preferredShop;
	}

	public String getPreferredRoom() {
		return preferredRoom;
	}

	public void setPreferredRoom(String preferredRoom) {
		this.preferredRoom = preferredRoom;
	}
}
