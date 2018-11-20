package org.spa.model.payroll;
// Generated 2016-8-19 17:28:15 by Hibernate Tools 4.3.1.Final

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.spa.model.company.Company;

import com.spa.constant.CommonConstant;

/**
 * PayrollTemplate generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "PAYROLL_TEMPLATE", catalog = "loccitane")
public class PayrollTemplate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Company company;
	private String name;
	private String description;
	private String content;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private Set<PayrollAttributeKey> payrollAttributeKeys = new HashSet<PayrollAttributeKey>(0);

	public PayrollTemplate() {
	}

	public PayrollTemplate(Company company, String name, String content, boolean isActive, Date created,
			String createdBy, Date lastUpdated, String lastUpdatedBy) {
		this.company = company;
		this.name = name;
		this.content = content;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public PayrollTemplate(Company company, String name, String description, String content, boolean isActive,
			Date created, String createdBy, Date lastUpdated, String lastUpdatedBy,
			Set<PayrollAttributeKey> payrollAttributeKeys) {
		this.company = company;
		this.name = name;
		this.description = description;
		this.content = content;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.payrollAttributeKeys = payrollAttributeKeys;
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

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "payrollTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy(value = "displayOrder asc")
	public Set<PayrollAttributeKey> getPayrollAttributeKeys() {
		return this.payrollAttributeKeys;
	}

	public void setPayrollAttributeKeys(Set<PayrollAttributeKey> payrollAttributeKeys) {
		this.payrollAttributeKeys = payrollAttributeKeys;
	}
	
	 @Transient
    public Boolean getServiceHourKeyForPartTime() {
		Boolean isHas=Boolean.FALSE;
		Set<PayrollAttributeKey> keys=getPayrollAttributeKeys();
		if(keys !=null && keys.size()>0){
			for(PayrollAttributeKey key : keys){
				if(key.getReference().equals(CommonConstant.PAYROLL_KEY_REF_HOUR_SALARY)){
					isHas=Boolean.TRUE;
					break;
				}
			}
		}
        return isHas;
    }
}