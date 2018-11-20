package org.spa.model.commission;
// Generated 2016-7-25 15:13:15 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * CommissionAttributeKey generated by hbm2java
 */
@Entity
@Table(name = "COMMISSION_ATTRIBUTE_KEY", catalog = "loccitane", uniqueConstraints = @UniqueConstraint(columnNames = {
		"commission_template_id", "reference" }) )
public class CommissionAttributeKey implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private CommissionTemplate commissionTemplate;
	private String reference;
	private String name;
	private String description;
	private Integer displayOrder;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private Set<CommissionAttribute> commissionAttributes = new HashSet<CommissionAttribute>(0);

	public CommissionAttributeKey() {
	}

	public CommissionAttributeKey(CommissionTemplate commissionTemplate, String reference, String name,
			boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
		this.commissionTemplate = commissionTemplate;
		this.reference = reference;
		this.name = name;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public CommissionAttributeKey(CommissionTemplate commissionTemplate, String reference, String name,
			String description, Integer displayOrder, boolean isActive, Date created, String createdBy,
			Date lastUpdated, String lastUpdatedBy, Set<CommissionAttribute> commissionAttributes) {
		this.commissionTemplate = commissionTemplate;
		this.reference = reference;
		this.name = name;
		this.description = description;
		this.displayOrder = displayOrder;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.commissionAttributes = commissionAttributes;
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
	@JoinColumn(name = "commission_template_id", nullable = false)
	public CommissionTemplate getCommissionTemplate() {
		return this.commissionTemplate;
	}

	public void setCommissionTemplate(CommissionTemplate commissionTemplate) {
		this.commissionTemplate = commissionTemplate;
	}

	@Column(name = "reference", nullable = false, length = 50)
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "display_order")
	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "is_active", nullable = false)
	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "created_by", nullable = false, length = 100)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated", nullable = false, length = 19)
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "last_updated_by", nullable = false, length = 100)
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "commissionAttributeKey")
	public Set<CommissionAttribute> getCommissionAttributes() {
		return this.commissionAttributes;
	}

	public void setCommissionAttributes(Set<CommissionAttribute> commissionAttributes) {
		this.commissionAttributes = commissionAttributes;
	}

}
