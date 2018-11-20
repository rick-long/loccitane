package org.spa.vo.shop;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ShopAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;
	
	private String businessPhone;
	
	private String address;
	
	private String isOnline;
	private String showOnlineBooking;

	private Long outSourceTemplateId;
	
	private String remarks;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String reference;
	
	@NotBlank
	private String prefix;
	
	private List<OpeningHoursVO> openingHoursList;
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getAddress() {
		return address;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getShowOnlineBooking() {
		return showOnlineBooking;
	}

	public void setShowOnlineBooking(String showOnlineBooking) {
		this.showOnlineBooking = showOnlineBooking;
	}
}
