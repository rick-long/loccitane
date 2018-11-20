package org.spa.model.company;
// Generated 2016-3-1 15:00:01 by Hibernate Tools 4.3.1.Final

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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

/**
 * CompanyProperty generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "COMPANY_PROPERTY", catalog = "loccitane")
public class CompanyProperty implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Company company;
	private CompanyPropertyKey companyPropertyKey;
	private String value;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;

	public CompanyProperty() {
	}

	public CompanyProperty(Company company, CompanyPropertyKey companyPropertyKey, boolean isActive) {
		this.company = company;
		this.companyPropertyKey = companyPropertyKey;
		this.isActive = isActive;
	}

	public CompanyProperty(Company company, CompanyPropertyKey companyPropertyKey, String value, boolean isActive,
			Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
		this.company = company;
		this.companyPropertyKey = companyPropertyKey;
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
	@JoinColumn(name = "company_id")
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_property_key_id", nullable = false)
	public CompanyPropertyKey getCompanyPropertyKey() {
		return this.companyPropertyKey;
	}

	public void setCompanyPropertyKey(CompanyPropertyKey companyPropertyKey) {
		this.companyPropertyKey = companyPropertyKey;
	}

	@Column(name = "value")
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
