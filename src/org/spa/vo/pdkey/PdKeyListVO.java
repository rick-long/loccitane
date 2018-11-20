package org.spa.vo.pdkey;
import java.io.Serializable;
import org.spa.model.product.ProductDescriptionKey;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/01/16.
 */
public class PdKeyListVO extends Page<ProductDescriptionKey> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String isActive;
	
	private Long categoryId;
	
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
	
}
