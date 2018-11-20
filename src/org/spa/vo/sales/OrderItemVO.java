package org.spa.vo.sales;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.product.ProductOption;
import org.spa.vo.staff.StaffItemVO;
import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/05/03.
 */
public class OrderItemVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idxId;
	private Long purchaseOrderId;
	private Long bookItemId;
	
	private String startTime;
	
    private Double price;
    private int duration;
    
    @NotNull
    private Integer qty;
    
    private Double extraDiscount;
    /*
     * allow Admin to update Sales to edit this field only.. 
     * This field should be null by default. Only use this field if this is not null and it will be use for commission calculation if not null.
     * So if null, commission = net value x commission %,If not null, (i.e. Admin added a value in this field), commission = Adjusted Net Value x commission%
     * */
    private Double adjustNetValue;
    
    private Double finalDiscount;// public discount + extra discount
    
    private Double effectiveAmount;//final price * qty
    private Double amount;// effectiveAmount - final discount
    
    /*
     * if a sales paid by prepaid package. netAmount = effectiveAmount * (prepaid face value / prepaid value). 
     * Then use it to cal net commission
     * */
    private Double netAmount;
    
    private Double prepaidPaidAmount;
    private Double finalAmount;//amount - prepaidPaidAmount
    
    @NotNull
	private Long productOptionId;
    private ProductOption productOption;
	
	private Long paidByPackageId;
	private String paidByVoucherRef;
	private List<Prepaid> suitablePackages;//适用的预付
	
	@NotNull
	private List<StaffItemVO> therapists;

	private Boolean notShowCheckOutStatus;
	
	private Long bundleId;
	
	public Double getAdjustNetValue() {
		return adjustNetValue;
	}
	public void setAdjustNetValue(Double adjustNetValue) {
		this.adjustNetValue = adjustNetValue;
	}
	public Long getBundleId() {
		return bundleId;
	}
	public void setBundleId(Long bundleId) {
		this.bundleId = bundleId;
	}
	public Integer getIdxId() {
		return idxId;
	}
	public void setIdxId(Integer idxId) {
		this.idxId = idxId;
	}
	public Long getBookItemId() {
		return bookItemId;
	}
	public void setBookItemId(Long bookItemId) {
		this.bookItemId = bookItemId;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Double getExtraDiscount() {
		return extraDiscount;
	}public void setExtraDiscount(Double extraDiscount) {
		this.extraDiscount = extraDiscount;
	}
	public Double getEffectiveAmount() {
		return effectiveAmount;
	}
	public void setEffectiveAmount(Double effectiveAmount) {
		this.effectiveAmount = effectiveAmount;
	}
	public Double getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}
	public Double getPrepaidPaidAmount() {
		return prepaidPaidAmount;
	}
	public void setPrepaidPaidAmount(Double prepaidPaidAmount) {
		this.prepaidPaidAmount = prepaidPaidAmount;
	}
	public Double getFinalDiscount() {
		return finalDiscount;
	}
	public void setFinalDiscount(Double finalDiscount) {
		this.finalDiscount = finalDiscount;
	}
	
	public ProductOption getProductOption() {
		return productOption;
	}
	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	public Long getProductOptionId() {
		return productOptionId;
	}

	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}
	
	public Long getPaidByPackageId() {
		return paidByPackageId;
	}
	public void setPaidByPackageId(Long paidByPackageId) {
		this.paidByPackageId = paidByPackageId;
	}
	public List<Prepaid> getSuitablePackages() {
		return suitablePackages;
	}
	public void setSuitablePackages(List<Prepaid> suitablePackages) {
		this.suitablePackages = suitablePackages;
	}
	public String getPaidByVoucherRef() {
		return paidByVoucherRef;
	}
	public void setPaidByVoucherRef(String paidByVoucherRef) {
		this.paidByVoucherRef = paidByVoucherRef;
	}
	public List<StaffItemVO> getTherapists() {
		return therapists;
	}

	public void setTherapists(List<StaffItemVO> therapists) {
		this.therapists = therapists;
	}

	public Boolean getNotShowCheckOutStatus() {
		return notShowCheckOutStatus;
	}

	public void setNotShowCheckOutStatus(Boolean notShowCheckOutStatus) {
		this.notShowCheckOutStatus = notShowCheckOutStatus;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
}
