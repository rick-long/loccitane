package com.spa.plugin.commission;

import java.io.Serializable;
import java.math.BigDecimal;

import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;

/**
 * @author ivy on 2016-8-3
 */
public class CommissionAdapter implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal effectiveValue = new BigDecimal(0);
	
	
	private BigDecimal commissionRate = new BigDecimal(0);
	private BigDecimal commission = new BigDecimal(0);
	private BigDecimal extraCommissionRate = new BigDecimal(0);
    private BigDecimal extraCommission = new BigDecimal(0);
    
    private BigDecimal targetCommissionRate = new BigDecimal(0);
	private BigDecimal targetCommission = new BigDecimal(0);
	private BigDecimal targetExtraCommissionRate = new BigDecimal(0);
    private BigDecimal targetExtraCommission = new BigDecimal(0);
    
    private Long staffId;
    
    private Category category;
    
    private Product product;
    private ProductOption productOption;
    
    public BigDecimal getTargetCommission() {
		return targetCommission;
	}
    public void setTargetCommission(BigDecimal targetCommission) {
		this.targetCommission = targetCommission;
	}
    public BigDecimal getTargetCommissionRate() {
		return targetCommissionRate;
	}
    public void setTargetCommissionRate(BigDecimal targetCommissionRate) {
		this.targetCommissionRate = targetCommissionRate;
	}
    public BigDecimal getTargetExtraCommission() {
		return targetExtraCommission;
	}
    public void setTargetExtraCommission(BigDecimal targetExtraCommission) {
		this.targetExtraCommission = targetExtraCommission;
	}
    public BigDecimal getTargetExtraCommissionRate() {
		return targetExtraCommissionRate;
	}
    public void setTargetExtraCommissionRate(BigDecimal targetExtraCommissionRate) {
		this.targetExtraCommissionRate = targetExtraCommissionRate;
	}
    
    public ProductOption getProductOption() {
		return productOption;
	}
    public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}
    
    public CommissionAdapter() {
		// 
	}
    
    public Product getProduct() {
		return product;
	}
    public void setProduct(Product product) {
		this.product = product;
	}
    
    public Long getStaffId() {
		return staffId;
	}
    public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
    public BigDecimal getCommission() {
		return commission;
	}
    public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
    public Category getCategory() {
		return category;
	}
    public void setCategory(Category category) {
		this.category = category;
	}
    public BigDecimal getEffectiveValue() {
		return effectiveValue;
	}
    public void setEffectiveValue(BigDecimal effectiveValue) {
		this.effectiveValue = effectiveValue;
	}
    public BigDecimal getCommissionRate() {
		return commissionRate;
	}
    public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}
    public BigDecimal getExtraCommission() {
		return extraCommission;
	}
    public void setExtraCommission(BigDecimal extraCommission) {
		this.extraCommission = extraCommission;
	}
    public BigDecimal getExtraCommissionRate() {
		return extraCommissionRate;
	}
    public void setExtraCommissionRate(BigDecimal extraCommissionRate) {
		this.extraCommissionRate = extraCommissionRate;
	}
}
