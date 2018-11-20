package com.spa.jxlsBean.importDemo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import com.spa.annotation.ShopAnnotation;

/**
 * Created by Ivy on 2016/11/7.
 */
public class ProductImportJxlsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String categoryName;
	private String treatmentName;
	private String treatmentCode;
	private String price;
	private String duration;
	private String processTime;
	private String capacity;
	
	@ShopAnnotation(shopName = "Flagship Spa L'Occitane")
	private String additionalPriceShop_1;
	
	@ShopAnnotation(shopName = "L'OCCITANE Well-Being Lounge")
	private String additionalPriceShop_2;
	
	private String status;

	private String returnError;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTreatmentName() {
		return treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public String getTreatmentCode() {
		return treatmentCode;
	}

	public void setTreatmentCode(String treatmentCode) {
		this.treatmentCode = treatmentCode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getAdditionalPriceShop_1() {
		return additionalPriceShop_1;
	}

	public void setAdditionalPriceShop_1(String additionalPriceShop_1) {
		this.additionalPriceShop_1 = additionalPriceShop_1;
	}

	public String getAdditionalPriceShop_2() {
		return additionalPriceShop_2;
	}

	public void setAdditionalPriceShop_2(String additionalPriceShop_2) {
		this.additionalPriceShop_2 = additionalPriceShop_2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturnError() {
		return returnError;
	}

	public void setReturnError(String returnError) {
		this.returnError = returnError;
	}

}
