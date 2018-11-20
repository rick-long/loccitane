package org.spa.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Order implements Serializable {

    /** identifier field */
    private long companyId;

    /** identifier field */
    private long id;

    /** persistent field */
    private String orderRef;

    /** persistent field */
    private long customerId;

    /** persistent field */
    private long shipmentId;

    /** nullable persistent field */
    private long couponId;

    /** persistent field */
    private String status;// order status

    /** nullable persistent field */
    private String paymentRef;

    /** nullable persistent field */
    private String paymentStatus;

    private String paymentMethod;

    /** persistent field */
    private double amount;
    
    /** not include shipping charge */
    private double subTotal;

    /** persistent field */
    private double shipCharge;

    /** nullable persistent field */
    private double discountValue;

    /** nullable persistent field */
    private double tax;

    /** persistent field */
    private boolean isRefunded;

    /** nullable persistent field */
    private String specialInstructions;

    /** nullable persistent field */
    private String remarks;

    /** persistent field */
    private java.util.Date lastUpdated;

    /** persistent field */
    private String lastUpdatedBy;

    /** persistent field */
    private java.util.Date created;

    /** persistent field */
    private String createdBy;
    
    private boolean isActive;

    public double getSubTotal() {
		return (amount - shipCharge);
	}
    
    public double chargeAmount;

	/** full constructor */
    public Order(long companyId, java.lang.String orderRef, long customerId, long shipmentId, long couponId, java.lang.String status, java.lang.String paymentRef, java.lang.String paymentStatus, double amount, double shipCharge, double discountValue, double tax, boolean isRefunded, java.lang.String specialInstructions, java.lang.String remarks, java.util.Date lastUpdated, java.lang.String lastUpdatedBy, java.util.Date created, java.lang.String createdBy) {
    	this.companyId = companyId;
        this.orderRef = orderRef;
        this.customerId = customerId;
        this.shipmentId = shipmentId;
        this.couponId = couponId;
        this.status = status;
        this.paymentRef = paymentRef;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.shipCharge = shipCharge;
        this.subTotal = (amount - shipCharge);
        this.discountValue = discountValue;
        this.tax = tax;
        this.isRefunded = isRefunded;
        this.specialInstructions = specialInstructions;
        this.remarks = remarks;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.created = created;
        this.createdBy = createdBy;
        
    }

    /** default constructor */
    public Order() {
    }

    /** minimal constructor */
    public Order(long companyId, java.lang.String orderRef, long customerId, long shipmentId, java.lang.String status, double amount, double shipCharge, boolean isRefunded, java.util.Date lastUpdated, java.lang.String lastUpdatedBy, java.util.Date created, java.lang.String createdBy) {
    	this.companyId = companyId;
        this.orderRef = orderRef;
        this.customerId = customerId;
        this.shipmentId = shipmentId;
        this.status = status;
        this.amount = amount;
        this.shipCharge = shipCharge;
        this.subTotal = (amount - shipCharge);        
        this.isRefunded = isRefunded;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.created = created;
        this.createdBy = createdBy;
    }

    public long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public java.lang.String getOrderRef() {
        return this.orderRef;
    }

    public void setOrderRef(java.lang.String orderRef) {
        this.orderRef = orderRef;
    }
    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public long getShipmentId() {
        return this.shipmentId;
    }

    public void setShipmentId(long shipmentId) {
        this.shipmentId = shipmentId;
    }
    public long getCouponId() {
        return this.couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }
    public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    public java.lang.String getPaymentRef() {
        return this.paymentRef;
    }

    public void setPaymentRef(java.lang.String paymentRef) {
        this.paymentRef = paymentRef;
    }
    public java.lang.String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(java.lang.String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getShipCharge() {
        return this.shipCharge;
    }

    public void setShipCharge(double shipCharge) {
        this.shipCharge = shipCharge;
    }
    public double getDiscountValue() {
        return this.discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }
    public double getTax() {
        return this.tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
    public boolean isIsRefunded() {
        return this.isRefunded;
    }

    public void setIsRefunded(boolean isRefunded) {
        this.isRefunded = isRefunded;
    }
    public java.lang.String getSpecialInstructions() {
        return this.specialInstructions;
    }

    public void setSpecialInstructions(java.lang.String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    public java.lang.String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(java.lang.String remarks) {
        this.remarks = remarks;
    }
    public java.util.Date getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(java.util.Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public java.lang.String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public java.util.Date getCreated() {
        return this.created;
    }

    public void setCreated(java.util.Date created) {
        this.created = created;
    }
    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
	

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Order) ) return false;
        Order castOther = (Order) other;
        return new EqualsBuilder()
            .append(this.id, castOther.id)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }

}
