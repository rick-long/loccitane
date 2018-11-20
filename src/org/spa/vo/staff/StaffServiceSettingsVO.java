package org.spa.vo.staff;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class StaffServiceSettingsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	//tab2
	private Long[] categoryIds;
	private Long[] productIds;
	
   
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long[] getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(Long[] categoryIds) {
		this.categoryIds = categoryIds;
	}
	public Long[] getProductIds() {
		return productIds;
	}
	public void setProductIds(Long[] productIds) {
		this.productIds = productIds;
	}
}