package org.spa.vo.shop;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ShopEditVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@NotBlank
	private String name;
	
	@NotBlank
	private String isActive;

	private Long id;
	
	private String isOnline;
	private String showOnlineBooking;
	private Long outSourceTemplateId;
	
	private String remarks;
	
	private String businessPhone;
	
	private String address;
	
	private List<OpeningHoursVO> openingHoursList;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String prefix;
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getOutSourceTemplateId() {
		return outSourceTemplateId;
	}
	public void setOutSourceTemplateId(Long outSourceTemplateId) {
		this.outSourceTemplateId = outSourceTemplateId;
	}
	public List<OpeningHoursVO> getOpeningHoursList() {
		return openingHoursList;
	}
	public void setOpeningHoursList(List<OpeningHoursVO> openingHoursList) {
		this.openingHoursList = openingHoursList;
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

	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getShowOnlineBooking() {
		return showOnlineBooking;
	}

	public void setShowOnlineBooking(String showOnlineBooking) {
		this.showOnlineBooking = showOnlineBooking;
	}
}
