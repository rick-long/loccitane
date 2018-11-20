package org.spa.vo.prepaid;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.product.ProductOption;
import org.spa.model.user.User;
import org.spa.vo.common.KeyAndValueVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/01/16.
 */
public class PrepaidAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long ptId;
	
	@NotNull
	private Long memberId;//会员id
	@NotBlank
	private String username;//用户名
	private User member;//会员用户
	
	private String reference;//关键字
	private String referenceBackUp;// filling the voucher reference  备份
	
	private String ptRef;//prepaid
	
	private Long categoryId;//
	private Long productId;//产品表
	private Long productOptionId;//产品服务id
	private ProductOption po;//产品服务
	
	private Double extraDiscount;//特别折扣
	
	@NotBlank
	private String prepaidType;//预付类型
	
	private Long shopId;//商店id
	
	@NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date purchaseDate;//预付时间
	
	@NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate;//过期时间
	
	@NotNull
	private Double prepaidValue;//预付金额
	
	private Double initValue;//预付类型的数量
	
	private Double remainValue;//剩余金额
	
	private List<KeyAndValueVO> therapists;
	
	@NotNull
	private List<KeyAndValueVO> paymentMethods;
	
	private String remarks;

	private String ptRemarks;
	
	private String prepaidName;
	
	private Double commissionRate;
	
	private String isTransfer;//转移
	
	private String isFree;
	
	
	private String packageType;
	
	private Boolean isAllCompanyUse;
	
	private Boolean isTopUp;//是否充值
	
	private Boolean isRedeem;//兑换
	
	private Boolean isOnline;//通过在线购买
	
	private Double effectiveValueForCommission; // for staff comiision amount
	
	private String isActive;
	
	private String paymentStatus;// for online voucher
	private String orderStatus;// for online voucher
	private String status;// ptt's status for online voucher 
	
	private String pickUpType;
	private String pickUpLocation;
	
	private String additionalEmail;
	private String additionalName;
	private String additionalMessage;

	private String barCode;
	private boolean isAppend;

	private Boolean importPrepaids;

	public Boolean getImportPrepaids() {
		return importPrepaids;
	}

	public void setImportPrepaids(Boolean importPrepaids) {
		this.importPrepaids = importPrepaids;
	}

	public boolean getIsAppend() {
		return isAppend;
	}

	public void setIsAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}

	public String getAdditionalEmail() {
		return additionalEmail;
	}
	public void setAdditionalEmail(String additionalEmail) {
		this.additionalEmail = additionalEmail;
	}
	public String getAdditionalName() {
		return additionalName;
	}
	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}
	public String getAdditionalMessage() {
		return additionalMessage;
	}
	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}
	public String getPickUpLocation() {
		return pickUpLocation;
	}
	public void setPickUpLocation(String pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}
	public String getPickUpType() {
		return pickUpType;
	}
	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
	public Double getEffectiveValueForCommission() {
		return effectiveValueForCommission;
	}
	public void setEffectiveValueForCommission(Double effectiveValueForCommission) {
		this.effectiveValueForCommission = effectiveValueForCommission;
	}
	
	public Boolean getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	public Boolean getIsRedeem() {
		return isRedeem;
	}
	public void setIsRedeem(Boolean isRedeem) {
		this.isRedeem = isRedeem;
	}
	public String getReferenceBackUp() {
		return referenceBackUp;
	}
	public void setReferenceBackUp(String referenceBackUp) {
		this.referenceBackUp = referenceBackUp;
	}
	
	public String getPtRef() {
		return ptRef;
	}
	public void setPtRef(String ptRef) {
		this.ptRef = ptRef;
	}
	public Long getPtId() {
		return ptId;
	}
	public void setPtId(Long ptId) {
		this.ptId = ptId;
	}
	public Boolean getIsTopUp() {
		return isTopUp;
	}
	public void setIsTopUp(Boolean isTopUp) {
		this.isTopUp = isTopUp;
	}
	public ProductOption getPo() {
		return po;
	}
	public void setPo(ProductOption po) {
		this.po = po;
	}
	public User getMember() {
		return member;
	}
	public void setMember(User member) {
		this.member = member;
	}
	public Boolean getIsAllCompanyUse() {
		return isAllCompanyUse;
	}
	public void setIsAllCompanyUse(Boolean isAllCompanyUse) {
		this.isAllCompanyUse = isAllCompanyUse;
	}
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getIsTransfer() {
		return isTransfer;
	}
	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}
	public Double getExtraDiscount() {
		return extraDiscount;
	}
	public void setExtraDiscount(Double extraDiscount) {
		this.extraDiscount = extraDiscount;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getPrepaidName() {
		return prepaidName;
	}
	public void setPrepaidName(String prepaidName) {
		this.prepaidName = prepaidName;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Long getMemberId() {
		return memberId;
	}

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductOptionId() {
		return productOptionId;
	}
	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getPrepaidType() {
		return prepaidType;
	}

	public void setPrepaidType(String prepaidType) {
		this.prepaidType = prepaidType;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getPrepaidValue() {
		return prepaidValue;
	}

	public void setPrepaidValue(Double prepaidValue) {
		this.prepaidValue = prepaidValue;
	}

	public Double getRemainValue() {
		return remainValue;
	}

	public void setRemainValue(Double remainValue) {
		this.remainValue = remainValue;
	}
	public List<KeyAndValueVO> getTherapists() {
		return therapists;
	}
	public void setTherapists(List<KeyAndValueVO> therapists) {
		this.therapists = therapists;
	}

	public List<KeyAndValueVO> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<KeyAndValueVO> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	
	public Double getInitValue() {
		return initValue;
	}
	public void setInitValue(Double initValue) {
		this.initValue = initValue;
	}
	public String getIsFree() {
		return isFree;
	}
	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}
	
	public String getPtRemarks() {
		return ptRemarks;
	}
	public void setPtRemarks(String ptRemarks) {
		this.ptRemarks = ptRemarks;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
}
