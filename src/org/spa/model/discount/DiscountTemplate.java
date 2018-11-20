package org.spa.model.discount;
// Generated 2016-5-12 11:49:54 by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.spa.model.company.Company;
import org.spa.model.shop.Shop;

/**
 * DiscountTemplate generated by hbm2java
 */
@Entity
@Table(name = "DISCOUNT_TEMPLATE", catalog = "loccitane")
public class DiscountTemplate implements java.io.Serializable {

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
    private Set<DiscountRule> discountRules = new HashSet<DiscountRule>(0);
    private Set<DiscountAttributeKey> discountAttributeKeys = new HashSet<DiscountAttributeKey>(0);

    public DiscountTemplate() {
    }

    public DiscountTemplate(Company company, Shop shop, String name, String content, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
        this.company = company;
        this.name = name;
        this.content = content;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public DiscountTemplate(Company company, Shop shop, String name, String description, String content, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy, Set<DiscountRule> discountRules, Set<DiscountAttributeKey> discountAttributeKeys) {
        this.company = company;
        this.name = name;
        this.description = description;
        this.content = content;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.discountRules = discountRules;
        this.discountAttributeKeys = discountAttributeKeys;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discountTemplate")
    public Set<DiscountRule> getDiscountRules() {
        return this.discountRules;
    }

    public void setDiscountRules(Set<DiscountRule> discountRules) {
        this.discountRules = discountRules;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discountTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "displayOrder asc")
    public Set<DiscountAttributeKey> getDiscountAttributeKeys() {
        return this.discountAttributeKeys;
    }

    public void setDiscountAttributeKeys(Set<DiscountAttributeKey> discountAttributeKeys) {
        this.discountAttributeKeys = discountAttributeKeys;
    }

}