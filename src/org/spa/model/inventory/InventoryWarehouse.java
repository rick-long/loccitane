package org.spa.model.inventory;
// Generated 2016-5-6 16:50:54 by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.spa.model.company.Company;
import org.spa.model.shop.Shop;

/**
 * InventoryWarehouse generated by hbm2java
 */
@Entity
@Table(name = "INVENTORY_WAREHOUSE", catalog = "loccitane", uniqueConstraints = @UniqueConstraint(columnNames = { "shop_id", "inventory_id" }) )
public class InventoryWarehouse implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private Company company;
    private Inventory inventory;
    private Shop shop;
    private int qty;
    private boolean isActive;
    private Date created;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;

    private String oldId;

    @Column(name = "old_id", length = 45)
    public String getOldId() {
        return this.oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public InventoryWarehouse() {
    }

    public InventoryWarehouse(long id, Company company, Inventory inventory, Shop shop, int qty, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
        this.id = id;
        this.company = company;
        this.inventory = inventory;
        this.shop = shop;
        this.qty = qty;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Column(name = "qty", nullable = false)
    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
