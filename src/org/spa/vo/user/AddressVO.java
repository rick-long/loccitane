package org.spa.vo.user;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/08/01.
 */
public class AddressVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String addressExtention;
	private String street;
	private String postCode;
	private String city;
	private String country;
    private String district;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddressExtention() {
		return addressExtention;
	}
	public void setAddressExtention(String addressExtention) {
		this.addressExtention = addressExtention;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
