package org.spa.model.discount;
// Generated 2016-5-12 11:49:54 by Hibernate Tools 4.3.1

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
 * DiscountAttributeKey generated by hbm2java
 */
@Entity
@Table(name = "DISCOUNT_ATTRIBUTE_KEY", catalog = "loccitane", uniqueConstraints = @UniqueConstraint(columnNames = { "discount_template_id", "reference" }) )
public class DiscountAttributeKey implements java.io.Serializable {

    private Long id;
    private DiscountTemplate discountTemplate;
    private String reference;
    private String name;
    private String description;
    private Integer displayOrder;
    private boolean isActive;
    private Date created;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;
    private Set<DiscountAttribute> discountAttributes = new HashSet<DiscountAttribute>(0);

    public DiscountAttributeKey() {
    }

    public DiscountAttributeKey(DiscountTemplate discountTemplate, String reference, String name, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
        this.discountTemplate = discountTemplate;
        this.reference = reference;
        this.name = name;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public DiscountAttributeKey(DiscountTemplate discountTemplate, String reference, String name, String description, Integer displayOrder, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy, Set<DiscountAttribute> discountAttributes) {
        this.discountTemplate = discountTemplate;
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.displayOrder = displayOrder;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.discountAttributes = discountAttributes;
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
    @JoinColumn(name = "discount_template_id", nullable = false)
    public DiscountTemplate getDiscountTemplate() {
        return this.discountTemplate;
    }

    public void setDiscountTemplate(DiscountTemplate discountTemplate) {
        this.discountTemplate = discountTemplate;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discountAttributeKey")
    public Set<DiscountAttribute> getDiscountAttributes() {
        return this.discountAttributes;
    }

    public void setDiscountAttributes(Set<DiscountAttribute> discountAttributes) {
        this.discountAttributes = discountAttributes;
    }

}
