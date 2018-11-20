package org.spa.vo.app.callback;

import org.spa.model.product.ProductOption;
import org.spa.serviceImpl.order.ReviewRatingTreatmentServiceImpl;
import org.spa.utils.SpringUtil;

import com.spa.constant.CommonConstant;

import java.io.Serializable;
import java.util.List;

public class ProductOptionCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private Long productOptionId;
    private Long productId;
    private String reference;
    private String productName;
    private String price;
    private Double priceValue;
    private String mins;
    private String endTime;
    private Double starAvg;
    private  Integer capacity;
    private String currency;
    List<ProductCallBackVO>productCallBackVOList;
    public ProductOptionCallBackVO() {
        super();
    }
    public ProductOptionCallBackVO(ProductOption productOption,Long shopId){
        this.productName=productOption.getProduct().getName();
        this.productOptionId=productOption.getId();
        this.reference=productOption.getReference();
        this.mins=productOption.getMins();
        this.price=CommonConstant.CURRENCY_TYPE+productOption.getFinalPrice(shopId);
        this.priceValue=productOption.getFinalPrice(shopId);
        this.productId=productOption.getProduct().getId();
        this.starAvg=getStarAvg(productOption.getProduct().getId());
        this.capacity=productOption.getCapacity();
        this.currency=CommonConstant.CURRENCY_TYPE;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public Double getStarAvg() {
        return starAvg;
    }

    public void setStarAvg(Double starAvg) {
        starAvg = starAvg;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    private  Double getStarAvg(Long staffId){
        Double StarAvg=0.0;
        ReviewRatingTreatmentServiceImpl rrts = SpringUtil.getBean(ReviewRatingTreatmentServiceImpl.class);
        if(rrts.getSatisfactionLevelStarAvgByTreatmentId(staffId)!=null){
            StarAvg=rrts.getSatisfactionLevelStarAvgByTreatmentId(staffId);
        }
        return StarAvg;
    }
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
    
}
