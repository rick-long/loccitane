package org.spa.vo.brand;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class BrandAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;
	
	private Boolean forCashPackage;
	
	public Boolean getForCashPackage() {
		return forCashPackage;
	}
	public void setForCashPackage(Boolean forCashPackage) {
		this.forCashPackage = forCashPackage;
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
	
	
}
