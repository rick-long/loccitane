package org.spa.model.order;
// Generated 2016-4-21 11:33:53 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.spa.model.user.User;

/**
 * StaffCommission generated by hbm2java
 */
@Entity
@Table(name = "STAFF_COMMISSION", catalog = "loccitane")
public class StaffCommission implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private PurchaseItem purchaseItem;
	private User staff;
	private double commissionRate;
	private double commissionValue;
	
	
	private double extraCommissionRate;//for some special treatment to cal the extra commission
	private double extraCommission;
	
	private double targetCommissionRate;//It will be added when reach the monthly target
	private double targetCommission;//It will be added when reach the monthly target
	private double targetExtraCommissionRate;//It will be added when reach the monthly target
	private double targetExtraCommission;//It will be added when reach the monthly target
	
	private double amount;
	private Double effectiveValue;
	
	
	
	
	private Date purchaseDate;
	private boolean isRequested;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private int displayOrder;
	
    private String oldId;

    @Column(name = "old_id", length = 45)
    public String getOldId() {
        return this.oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

	public StaffCommission() {
	}

	public StaffCommission(PurchaseItem purchaseItem, User staff, double commissionRate, double amount,
			double commissionValue, Date purchaseDate, boolean isRequested, boolean isActive) {
		this.purchaseItem = purchaseItem;
		this.staff = staff;
		this.commissionRate = commissionRate;
		this.amount = amount;
		this.commissionValue = commissionValue;
		this.purchaseDate = purchaseDate;
		this.isRequested = isRequested;
		this.isActive = isActive;
	}

	public StaffCommission(PurchaseItem purchaseItem, User staff, double commissionRate, double amount,
			double commissionValue, Date purchaseDate, boolean isRequested, boolean isActive, Date created,
			String createdBy, Date lastUpdated, String lastUpdatedBy,int displayOrder,double bonusCommissionRate) {
		this.purchaseItem = purchaseItem;
		this.staff = staff;
		this.commissionRate = commissionRate;
		this.amount = amount;
		this.commissionValue = commissionValue;
		this.purchaseDate = purchaseDate;
		this.isRequested = isRequested;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.displayOrder=displayOrder;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_item_id", nullable = false)
	public PurchaseItem getPurchaseItem() {
		return this.purchaseItem;
	}

	public void setPurchaseItem(PurchaseItem purchaseItem) {
		this.purchaseItem = purchaseItem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id", nullable = false)
	public User getStaff() {
		return staff;
	}
	public void setStaff(User staff) {
		this.staff = staff;
	}

	@Column(name = "commission_rate", nullable = false, precision = 22, scale = 0)
	public double getCommissionRate() {
		return this.commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	@Column(name = "amount", nullable = false, precision = 22, scale = 0)
	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "effective_value", nullable = false, precision = 22, scale = 0)
	public Double getEffectiveValue() {
		return effectiveValue;
	}
	public void setEffectiveValue(Double effectiveValue) {
		this.effectiveValue = effectiveValue;
	}
	
	@Column(name = "commission_value", nullable = false, precision = 22, scale = 0)
	public double getCommissionValue() {
		return this.commissionValue;
	}

	public void setCommissionValue(double commissionValue) {
		this.commissionValue = commissionValue;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "purchase_date", nullable = false, length = 19)
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Column(name = "is_requested", nullable = false)
	public boolean isIsRequested() {
		return this.isRequested;
	}

	public void setIsRequested(boolean isRequested) {
		this.isRequested = isRequested;
	}
	
	@Column(name = "extra_commission_rate", nullable = false, precision = 22, scale = 0)
	public double getExtraCommissionRate() {
		return extraCommissionRate;
	}
	public void setExtraCommissionRate(double extraCommissionRate) {
		this.extraCommissionRate = extraCommissionRate;
	}
	@Column(name = "extra_commission", nullable = false, precision = 22, scale = 0)
	public double getExtraCommission() {
		return extraCommission;
	}
	public void setExtraCommission(double extraCommission) {
		this.extraCommission = extraCommission;
	}
	
	@Column(name = "is_active", nullable = false)
	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "created_by", length = 100)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated", length = 19)
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "last_updated_by", length = 100)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Column(name = "display_order", nullable = false)
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	@Column(name = "target_commission",nullable = false,precision = 22, scale = 0)
	public double getTargetCommission() {
		return targetCommission;
	}
	
	public void setTargetCommission(double targetCommission) {
		this.targetCommission = targetCommission;
	}
	
	@Column(name = "target_commission_rate",nullable = false,precision = 22, scale = 0)
	public double getTargetCommissionRate() {
		return targetCommissionRate;
	}
	public void setTargetCommissionRate(double targetCommissionRate) {
		this.targetCommissionRate = targetCommissionRate;
	}
	
	@Column(name = "target_extra_commission",nullable = false,precision = 22, scale = 0)
	public double getTargetExtraCommission() {
		return targetExtraCommission;
	}
	public void setTargetExtraCommission(double targetExtraCommission) {
		this.targetExtraCommission = targetExtraCommission;
	}
	
	@Column(name = "target_extra_commission_rate",nullable = false,precision = 22, scale = 0)
	public double getTargetExtraCommissionRate() {
		return targetExtraCommissionRate;
	}
	public void setTargetExtraCommissionRate(double targetExtraCommissionRate) {
		this.targetExtraCommissionRate = targetExtraCommissionRate;
	}
}