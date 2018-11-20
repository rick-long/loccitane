package com.spa.plugin.discount;

import java.io.Serializable;
import java.math.BigDecimal;

import org.spa.model.discount.DiscountRule;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOption;

/**
 * 
 * @author Ivy on 2016-6-17
 *
 */
public class DiscountItemAdapter implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 应用的折扣规则
    private DiscountRule discountRule;

    private DiscountAdapter discountAdapter;
    
    private ProductOption productOption;
    
    private Category category;
    
    private BigDecimal qty;
    
    private BigDecimal price;
    
    private BigDecimal amount;
    
    private BigDecimal discountValue = new BigDecimal("0");

    
    public DiscountItemAdapter() {
    }

    public DiscountItemAdapter(ProductOption productOption, Integer qty, Double price) {
        super();
        this.productOption = productOption;
        this.qty = new BigDecimal(qty.toString());
        this.price = new BigDecimal(price.toString());
    }

    public Category getCategory() {
		return category;
	}
    public void setCategory(Category category) {
		this.category = category;
	}
    public DiscountAdapter getDiscountAdapter() {
        return discountAdapter;
    }

    public DiscountRule getDiscountRule() {
        return discountRule;
    }

    public void setDiscountRule(DiscountRule discountRule) {
        this.discountRule = discountRule;
    }

    public void setDiscountAdapter(DiscountAdapter discountAdapter) {
        this.discountAdapter = discountAdapter;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }
}
