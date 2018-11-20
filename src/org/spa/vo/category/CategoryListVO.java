package org.spa.vo.category;
import java.io.Serializable;
import org.spa.model.product.Category;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/01/16.
 */
public class CategoryListVO extends Page<Category> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String isActive;
	private Long categoryId;
	private Long rootCategoryId;

	private String parentCategory;
	private String displayOrder;
	private String remarks;
	
	public Long getRootCategoryId() {
		return rootCategoryId;
	}
	public void setRootCategoryId(Long rootCategoryId) {
		this.rootCategoryId = rootCategoryId;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
