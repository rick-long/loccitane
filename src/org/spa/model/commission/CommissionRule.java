package org.spa.model.commission;
// Generated 2016-7-25 15:13:15 by Hibernate Tools 4.3.1.Final

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.spa.model.company.Company;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.user.UserGroup;
import org.spa.service.commission.CommissionAttributeService;
import org.spa.serviceImpl.commission.CommissionAttributeServiceImpl;
import org.spa.utils.SpringUtil;

import com.spa.constant.CommonConstant;

/**
 * CommissionRule generated by hbm2java
 */
@Entity
@Table(name = "COMMISSION_RULE", catalog = "loccitane")
public class CommissionRule implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private CommissionTemplate commissionTemplate;
	private Company company;
	private String description;
	private boolean isActive;
	private Boolean calTarget; //default is true.
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private Set<CommissionAttribute> commissionAttributes = new HashSet<CommissionAttribute>(0);
	private Set<Category> categories = new HashSet<Category>(0);
	private Set<UserGroup> userGroups = new HashSet<UserGroup>(0);
	private Set<Product> products = new HashSet<Product>(0);
	
	public CommissionRule() {
	}

	public CommissionRule(CommissionTemplate commissionTemplate, Company company, boolean isActive, Date created,
			String createdBy, Date lastUpdated, String lastUpdatedBy) {
		this.commissionTemplate = commissionTemplate;
		this.company = company;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public CommissionRule(CommissionTemplate commissionTemplate, Company company, String description, boolean isActive,
			Date created, String createdBy, Date lastUpdated, String lastUpdatedBy,Set<Product> products,
			Set<CommissionAttribute> commissionAttributes, Set<Category> categories, Set<UserGroup> userGroups) {
		this.commissionTemplate = commissionTemplate;
		this.company = company;
		this.description = description;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.commissionAttributes = commissionAttributes;
		this.categories = categories;
		this.userGroups = userGroups;
		this.products = products;
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
	@JoinColumn(name = "template_id", nullable = false)
	public CommissionTemplate getCommissionTemplate() {
		return this.commissionTemplate;
	}

	public void setCommissionTemplate(CommissionTemplate commissionTemplate) {
		this.commissionTemplate = commissionTemplate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
	@Column(name = "cal_target", nullable = false)
	public Boolean getCalTarget() {
		return calTarget;
	}
	public void setCalTarget(Boolean calTarget) {
		this.calTarget = calTarget;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "commissionRule", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<CommissionAttribute> getCommissionAttributes() {
		return this.commissionAttributes;
	}

	public void setCommissionAttributes(Set<CommissionAttribute> commissionAttributes) {
		this.commissionAttributes = commissionAttributes;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "commission_rule_category", catalog = "loccitane", joinColumns = {
			@JoinColumn(name = "commission_rule_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "category_id", nullable = false, updatable = false) })
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "commission_rule_user_group", catalog = "loccitane", joinColumns = {
			@JoinColumn(name = "commission_rule_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "user_group_id", nullable = false, updatable = false) })
	public Set<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "COMMISSION_RULE_PRODUCT", catalog = "loccitane", joinColumns = { @JoinColumn(name = "commission_rule_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "product_id", nullable = false, updatable = false) })
    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    
	@Transient
    public Double getCommissionRate() {
	 	CommissionAttributeService cas = SpringUtil.getBean(CommissionAttributeServiceImpl.class);
	 	CommissionAttribute attribute =cas.getCommissionAttributeByRuleAndKeyRef(CommonConstant.COMMISSION_ATTRIBUTE_KEY_REF_RATE, getId());
        if (attribute != null) {
            return Double.parseDouble(attribute.getValue());
        }
        return 0d;
    }

	@Transient
	public Double getCommissionExtraRate() {
		CommissionAttributeService cas = SpringUtil.getBean(CommissionAttributeServiceImpl.class);
		CommissionAttribute attribute =cas.getCommissionAttributeByRuleAndKeyRef(CommonConstant.COMMISSION_ATTRIBUTE_KEY_Extra_RATE, getId());
		if (attribute != null) {
			return Double.parseDouble(attribute.getValue());
		}
		return 0d;
	}

	@Transient
	public Double getTargetCommissionRate() {
		CommissionAttributeService cas = SpringUtil.getBean(CommissionAttributeServiceImpl.class);
		CommissionAttribute attribute =cas.getCommissionAttributeByRuleAndKeyRef(CommonConstant.COMMISSION_ATTRIBUTE_KEY_TARGET_RATE, getId());
		if (attribute != null) {
			return Double.parseDouble(attribute.getValue());
		}
		return 0d;
	}

	@Transient
	public Double getTargetCommissionExtraRate() {
		CommissionAttributeService cas = SpringUtil.getBean(CommissionAttributeServiceImpl.class);
		CommissionAttribute attribute =cas.getCommissionAttributeByRuleAndKeyRef(CommonConstant.COMMISSION_ATTRIBUTE_KEY_TARGET_Extra_RATE, getId());
		if (attribute != null) {
			return Double.parseDouble(attribute.getValue());
		}
		return 0d;
	}
}