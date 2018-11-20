package org.spa.vo.product;
import java.io.Serializable;
import org.spa.model.product.Product;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ProductListVO extends Page<Product> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String isActive;
	private Long categoryId;
	private Long brandId;
	private Long supplierId;
	private String showOnline;
	private String barcode;

	private String productOptionLabel;
	private String categoryLabel;
	private String description;
	private String brandName;
	
	public String getShowOnline() {
		return showOnline;
	}
	public void setShowOnline(String showOnline) {
		this.showOnline = showOnline;
	}
	
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProductOptionLabel() {
		return productOptionLabel;
	}

	public void setProductOptionLabel(String productOptionLabel) {
		this.productOptionLabel = productOptionLabel;
	}

	public String getCategoryLabel() {
		return categoryLabel;
	}

	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
