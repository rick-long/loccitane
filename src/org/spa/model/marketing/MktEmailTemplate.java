package org.spa.model.marketing;
// Generated 2016-5-31 17:46:31 by Hibernate Tools 4.3.1

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

import org.spa.model.company.Company;

/**
 * MktEmailTemplate generated by hbm2java
 */
@Entity
@Table(name = "MKT_EMAIL_TEMPLATE", catalog = "loccitane")
public class MktEmailTemplate implements java.io.Serializable {

    private Long id;
    private Company company;
    private String type;
    private String subject;
    private String content;
    private String documentId;
    private boolean isActive;
    private Date created;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;

    public MktEmailTemplate() {
    }

    public MktEmailTemplate(Company company, String type, String subject, String content, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
        this.company = company;
        this.type = type;
        this.subject = subject;
        this.content = content;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public MktEmailTemplate(Company company, String type, String subject, String content, String documentId, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
        this.company = company;
        this.type = type;
        this.subject = subject;
        this.content = content;
        this.documentId = documentId;
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
    @JoinColumn(name = "company_id", nullable = false)
    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name = "type", nullable = false, length = 45)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "subject", nullable = false)
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "content", nullable = false, length = 65535)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "document_id", length = 45)
    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

}