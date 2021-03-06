package org.spa.model;
// Generated 2016-6-7 16:18:10 by Hibernate Tools 4.3.1

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
import javax.persistence.Transient;

import org.spa.model.company.Company;
import org.spa.model.marketing.MktMailShot;
import org.spa.utils.ServletUtil;

/**
 * Document generated by hbm2java
 */
@Entity
@Table(name = "DOCUMENT", catalog = "loccitane")
public class Document implements java.io.Serializable {

    private Long id;
    private Company company;
    private String name;
    private String path;
    private String ownerId;
    private String type;
    private String description;
    private Date expiryDate;

    /**
     * 是否有效.
     */
    private boolean isActive;

    /**
     * 创建时间.
     */
    private Date created;

    /**
     * 创建者.
     */
    private String createdBy;

    /**
     * 更新时间.
     */
    private Date lastUpdated;

    /**
     * 更新者.
     */
    private String lastUpdatedBy;
    private String oldId;
    private Set<MktMailShot> mktMailShots = new HashSet<MktMailShot>(0);

    public Document() {
    }

    public Document(Company company, String name, String path, String type, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
        this.company = company;
        this.name = name;
        this.path = path;
        this.type = type;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Document(Company company, String name, String path, String ownerId, String type, String description, Date expiryDate, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy, String oldId, Set<MktMailShot> mktMailShots) {
        this.company = company;
        this.name = name;
        this.path = path;
        this.ownerId = ownerId;
        this.type = type;
        this.description = description;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.oldId = oldId;
        this.mktMailShots = mktMailShots;
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

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "path", nullable = false)
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "owner_id", length = 45)
    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "type", nullable = false, length = 45)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date", length = 19)
    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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

    @Column(name = "old_id", length = 45)
    public String getOldId() {
        return this.oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    public Set<MktMailShot> getMktMailShots() {
        return this.mktMailShots;
    }

    public void setMktMailShots(Set<MktMailShot> mktMailShots) {
        this.mktMailShots = mktMailShots;
    }

    @Transient
    public String getAbsolutePath() {
        return ServletUtil.getContextPath("") + getPath();
    }
}
