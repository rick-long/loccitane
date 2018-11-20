package org.spa.model.shop;
// Generated 2016-8-30 16:30:00 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import org.spa.model.company.Company;

/**
 * OutSourceTemplate generated by hbm2java
 */
@Entity
@Table(name = "OUT_SOURCE_TEMPLATE", catalog = "loccitane")
public class OutSourceTemplate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Company company;
	private String name;
	private String description;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private Set<OutSourceAttributeKey> outSourceAttributeKeys = new HashSet<OutSourceAttributeKey>(0);
	private Set<Shop> shops = new HashSet<Shop>(0);
	
	public OutSourceTemplate() {
	}

	public OutSourceTemplate(Company company, String name, boolean isActive) {
		this.company = company;
		this.name = name;
		this.isActive = isActive;
	}

	public OutSourceTemplate(Company company, String name, String description, boolean isActive, Date created,
			String createdBy, Date lastUpdated, String lastUpdatedBy,Set<Shop> shops,
			Set<OutSourceAttributeKey> outSourceAttributeKeys) {
		this.company = company;
		this.name = name;
		this.description = description;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.outSourceAttributeKeys = outSourceAttributeKeys;
		this.shops=shops;
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
	@JoinColumn(name = "company_id", nullable = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "outSourceTemplate",cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<OutSourceAttributeKey> getOutSourceAttributeKeys() {
		return this.outSourceAttributeKeys;
	}

	public void setOutSourceAttributeKeys(Set<OutSourceAttributeKey> outSourceAttributeKeys) {
		this.outSourceAttributeKeys = outSourceAttributeKeys;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "outSourceTemplate")
	public Set<Shop> getShops() {
		return this.shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}
}