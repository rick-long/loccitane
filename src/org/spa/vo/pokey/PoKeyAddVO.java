package org.spa.vo.pokey;

import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/01/16.
 */
public class PoKeyAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;

	@NotBlank
	private String uiType;
	
	private String initOption;
	@NotNull
	private Long categoryId;
	
	private String defaultValue;
	
	@NotBlank
	private String reference;
	
	private String unit;
	
	private String labelShow;
	
	public String getLabelShow() {
		return labelShow;
	}
	public void setLabelShow(String labelShow) {
		this.labelShow = labelShow;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
