package org.spa.vo.app.callback;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	@JsonProperty("OBJECT_TYPE")
	private String objectType;
	// account
	@JsonProperty("Diva Number")
	private String accountName;
	
	@JsonProperty("Register Home Shop")
	private String regestrationLocation;
	
	//contact object
	@JsonProperty("First Name")
	private String firstName;
	
	@JsonProperty("Last Name")
	private String lastName;
	
	@JsonProperty("Email")
	private String email;
	
	@JsonProperty("Mobile Phone")
	private String mobilePhone;
	
	@JsonProperty("Home Phone")
	private String homePhone;
	
	@JsonProperty("Business Phone")
	private String phone;
	
	@JsonProperty("Province")
	private String mailingState;
	
	@JsonProperty("City")
	private String mailingCity;
	
	@JsonProperty("Country")
	private String mailingCountry;
	
	@JsonProperty("Address")
	private String mailingStreet;
	
	@JsonProperty("Post Code")
	private String mailingPostalCode;
	
	@JsonProperty("Gender")
	private String gender;
	
	@JsonProperty("Birthday")
	private String birthdate;
	
	@JsonProperty("Enabled")
	private String enabled;
	
	@JsonProperty("Active")
	private String active;
	
	@JsonProperty("Diva Level")
	private String divaLevel;

	@JsonProperty("Fire Time")
	private String triggerTime;
	
	@JsonProperty("Operation Enum")
	private String operationEnum;
	
	public String getOperationEnum() {
		return operationEnum;
	}
	public void setOperationEnum(String operationEnum) {
		this.operationEnum = operationEnum;
	}
	public String getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
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

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRegestrationLocation() {
		return regestrationLocation;
	}

	public void setRegestrationLocation(String regestrationLocation) {
		this.regestrationLocation = regestrationLocation;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMailingState() {
		return mailingState;
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}

	public String getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	public String getMailingCountry() {
		return mailingCountry;
	}

	public void setMailingCountry(String mailingCountry) {
		this.mailingCountry = mailingCountry;
	}

	public String getMailingStreet() {
		return mailingStreet;
	}

	public void setMailingStreet(String mailingStreet) {
		this.mailingStreet = mailingStreet;
	}

	public String getMailingPostalCode() {
		return mailingPostalCode;
	}

	public void setMailingPostalCode(String mailingPostalCode) {
		this.mailingPostalCode = mailingPostalCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDivaLevel() {
		return divaLevel;
	}

	public void setDivaLevel(String divaLevel) {
		this.divaLevel = divaLevel;
	}
}
