package org.spa.vo.pdkey;

import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/01/16.
 */
public class PdKeyAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;

	@NotBlank
	private String uiType;
	
	private String initOption;
	
	@NotBlank
	private String reference;
	
	private String defaultValue;
	@NotNull
	private Long categoryId;
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}

	public String getInitOption() {
		return initOption;
	}

	public void setInitOption(String initOption) {
		this.initOption = initOption;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
