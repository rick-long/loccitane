package com.spa.plugin.discount;

import org.spa.model.product.ProductOption;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivy on 2018-7-17
 */
public class DiscountAdapter implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long shopId;

    private Long memberId;

    private final List<DiscountItemAdapter> itemAdapters = new ArrayList<>();

    private BigDecimal totalAmount = new BigDecimal(0);

    private BigDecimal totalDiscount = new BigDecimal(0);

    public DiscountAdapter() {
    }

    public DiscountAdapter(Long shopId, Long memberId) {
        super();
        this.shopId = shopId;
        this.memberId = memberId;
    }

    public static DiscountAdapter newInstance(Long shopId, Long memberId) {
        return new DiscountAdapter(shopId, memberId);
    }

    public DiscountAdapter addItem(ProductOption productOption, Integer qty, Double price) {
        DiscountItemAdapter discountItemAdapter = new DiscountItemAdapter(productOption, qty, price);
        discountItemAdapter.setDiscountAdapter(this);
        discountItemAdapter.setAmount(discountItemAdapter.getQty().multiply(discountItemAdapter.getPrice()));
        itemAdapters.add(discountItemAdapter);
        this.totalAmount.add(discountItemAdapter.getAmount());
        return this;
    }

    public List<DiscountItemAdapter> getItemAdapters() {
        return itemAdapters;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
