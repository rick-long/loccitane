package org.spa.vo.member;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by william on 2018-8-21.
 */
public class MemberMinipgEditVO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String shopId;
	private Long prepaidId;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	private String addressExtention;

	private String district;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAddressExtention() {
		return addressExtention;
	}

	public void setAddressExtention(String addressExtention) {
		this.addressExtention = addressExtention;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getGender() {
		if(this.gender.equals("1")){
			return "FEMALE";
		}else if(this.gender.equals("0")){
			return "MALE";
		}
		return "";
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

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Long getPrepaidId() {
		return prepaidId;
	}

	public void setPrepaidId(Long prepaidId) {
		this.prepaidId = prepaidId;
	}
}

