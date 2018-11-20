package org.spa.model.bonus;
// Generated 2016-11-2 15:37:29 by Hibernate Tools 4.3.1.Final

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
 * BonusAttributeKey generated by hbm2java
 */
@Entity
@Table(name = "BONUS_ATTRIBUTE_KEY", catalog = "loccitane", uniqueConstraints = @UniqueConstraint(columnNames = {
		"bonus_template_id", "reference" }) )
public class BonusAttributeKey implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private BonusTemplate bonusTemplate;
	private String reference;
	private String name;
	private String description;
	private Integer displayOrder;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;

	private Set<BonusAttribute> bonusAttributes = new HashSet<BonusAttribute>(0);
	
	public BonusAttributeKey() {
	}

	public BonusAttributeKey(BonusTemplate bonusTemplate, String reference, String name, boolean isActive, Date created,
			String createdBy, Date lastUpdated, String lastUpdatedBy) {
		this.bonusTemplate = bonusTemplate;
		this.reference = reference;
		this.name = name;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public BonusAttributeKey(BonusTemplate bonusTemplate, String reference, String name, String description,
			Integer displayOrder, boolean isActive, Date created, String createdBy, Date lastUpdated,Set<BonusAttribute> bonusAttributes,
			String lastUpdatedBy) {
		this.bonusTemplate = bonusTemplate;
		this.reference = reference;
		this.name = name;
		this.description = description;
		this.displayOrder = displayOrder;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.bonusAttributes=bonusAttributes;
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
	@JoinColumn(name = "bonus_template_id", nullable = false)
	public BonusTemplate getBonusTemplate() {
		return this.bonusTemplate;
	}

	public void setBonusTemplate(BonusTemplate bonusTemplate) {
		this.bonusTemplate = bonusTemplate;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bonusAttributeKey")
	public Set<BonusAttribute> getBonusAttributes() {
		return this.bonusAttributes;
	}

	public void setBonusAttributes(Set<BonusAttribute> bonusAttributes) {
		this.bonusAttributes = bonusAttributes;
	}
}