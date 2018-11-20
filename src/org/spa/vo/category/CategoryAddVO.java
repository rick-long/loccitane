package org.spa.vo.category;

import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/01/16.
 */
public class CategoryAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank
	private String name;
	
	private String displayOrder;
	
	private Long categoryId;

	private Long rootCategoryId;
	
	private String remarks;

	private Boolean isActive;

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getRootCategoryId() {
		return rootCategoryId;
	}
	public void setRootCategoryId(Long rootCategoryId) {
		this.rootCategoryId = rootCategoryId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
}
