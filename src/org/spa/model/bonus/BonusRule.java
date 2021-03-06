package org.spa.model.bonus;
// Generated 2016-11-2 17:01:20 by Hibernate Tools 4.3.1.Final

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

import org.spa.model.bonus.BonusAttribute;
import org.spa.model.company.Company;
import org.spa.model.product.Category;
import org.spa.service.bonus.BonusAttributeService;
import org.spa.serviceImpl.bonus.BonusAttributeServiceImpl;
import org.spa.utils.SpringUtil;

import com.spa.constant.CommonConstant;

/**
 * BonusRule generated by hbm2java
 */
@Entity
@Table(name = "BONUS_RULE", catalog = "loccitane")
public class BonusRule implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private BonusTemplate bonusTemplate;
	private Company company;
	private String description;
	private boolean isActive;
	
	private Date startDate;
	private Date endDate;
	
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;

	private Set<BonusAttribute> bonusAttributes = new HashSet<BonusAttribute>(0);
	private Set<Category> categories = new HashSet<Category>(0);
	
	public BonusRule() {
	}

	public BonusRule(BonusTemplate bonusTemplate, Company company, boolean isActive, Date created, String createdBy,
			Date lastUpdated, String lastUpdatedBy) {
		this.bonusTemplate = bonusTemplate;
		this.company = company;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public BonusRule(BonusTemplate bonusTemplate, Company company, String description, boolean isActive, Date created,Date startDate,Date endDate,
			String createdBy, Date lastUpdated, String lastUpdatedBy,Set<BonusAttribute> bonusAttributes,Set<Category> categories) {
		this.bonusTemplate = bonusTemplate;
		this.company = company;
		this.description = description;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.bonusAttributes=bonusAttributes;
		this.categories=categories;
		this.startDate=startDate;
		this.endDate=endDate;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bonusRule", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<BonusAttribute> getBonusAttributes() {
		return this.bonusAttributes;
	}

	public void setBonusAttributes(Set<BonusAttribute> bonusAttributes) {
		this.bonusAttributes = bonusAttributes;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "bonus_rule_category", catalog = "loccitane", joinColumns = {
			@JoinColumn(name = "bonus_rule_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "category_id", nullable = false, updatable = false) })
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false, length = 19)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false, length = 19)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Transient
    public Double getTargetRate() {
	 	BonusAttributeService cas = SpringUtil.getBean(BonusAttributeServiceImpl.class);
	 	BonusAttribute attribute =cas.getBonusAttributeByRuleAndKeyRef(CommonConstant.BONUS_ATTRIBUTE_KEY_REF_TARGET_RATE, getId());
        if (attribute != null) {
            return Double.parseDouble(attribute.getValue());
        }
        return 0d;
    }
	
	@Transient
    public Double getIncentiveRate() {
	 	BonusAttributeService cas = SpringUtil.getBean(BonusAttributeServiceImpl.class);
	 	BonusAttribute attribute =cas.getBonusAttributeByRuleAndKeyRef(CommonConstant.BONUS_ATTRIBUTE_KEY_REF_INCENTIVE_RATE, getId());
        if (attribute != null) {
            return Double.parseDouble(attribute.getValue());
        }
        return 0d;
    }
	
	@Transient
    public Double getIncentiveAmount() {
	 	BonusAttributeService cas = SpringUtil.getBean(BonusAttributeServiceImpl.class);
	 	BonusAttribute attribute =cas.getBonusAttributeByRuleAndKeyRef(CommonConstant.BONUS_ATTRIBUTE_KEY_REF_INCENTIVE_AMOUNT, getId());
        if (attribute != null) {
            return Double.parseDouble(attribute.getValue());
        }
        return 0d;
    }
}
