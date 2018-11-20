package org.spa.vo.brand;

import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class BrandEditVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Boolean forCashPackage;
		
	@NotBlank
	private String name;
	
	@NotBlank
	private String isActive;

	public Boolean getForCashPackage() {
		return forCashPackage;
	}
	public void setForCashPackage(Boolean forCashPackage) {
		this.forCashPackage = forCashPackage;
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
	
	
}
