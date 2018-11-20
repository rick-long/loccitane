package org.spa.model.order;
// Generated 2016-8-30 16:30:00 by Hibernate Tools 4.3.1.Final

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

import org.spa.model.shop.OutSourceAttributeKey;

/**
 * PurchaseOrderOutSourceAttribute generated by hbm2java
 */
@Entity
@Table(name = "PURCHASE_ORDER_OUT_SOURCE_ATTRIBUTE", catalog = "loccitane")
public class PurchaseOrderOutSourceAttribute implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private OutSourceAttributeKey outSourceAttributeKey;
	private PurchaseOrder purchaseOrder;
	private String value;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;

	public PurchaseOrderOutSourceAttribute() {
	}

	public PurchaseOrderOutSourceAttribute(OutSourceAttributeKey outSourceAttributeKey, PurchaseOrder purchaseOrder,
			String value, boolean isActive) {
		this.outSourceAttributeKey = outSourceAttributeKey;
		this.purchaseOrder = purchaseOrder;
		this.value = value;
		this.isActive = isActive;
	}

	public PurchaseOrderOutSourceAttribute(OutSourceAttributeKey outSourceAttributeKey, PurchaseOrder purchaseOrder,
			String value, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
		this.outSourceAttributeKey = outSourceAttributeKey;
		this.purchaseOrder = purchaseOrder;
		this.value = value;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
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
	@JoinColumn(name = "out_source_attribute_key_id", nullable = false)
	public OutSourceAttributeKey getOutSourceAttributeKey() {
		return this.outSourceAttributeKey;
	}

	public void setOutSourceAttributeKey(OutSourceAttributeKey outSourceAttributeKey) {
		this.outSourceAttributeKey = outSourceAttributeKey;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_order_id", nullable = false)
	public PurchaseOrder getPurchaseOrder() {
		return this.purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	@Column(name = "value", nullable = false)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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

}
