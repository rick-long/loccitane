package org.spa.vo.pokey;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/01/16.
 */
public class PoKeyEditVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String isActive;

	@NotBlank
	private String uiType;
	
	private String initOption;
	
	@NotNull
	private Long categoryId;
	
	private String defaultValue;

    private String unit;
	
    private String labelShow;
	
	public String getLabelShow() {
		return labelShow;
	}
	public void setLabelShow(String labelShow) {
		this.labelShow = labelShow;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
