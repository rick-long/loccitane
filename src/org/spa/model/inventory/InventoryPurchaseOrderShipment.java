package org.spa.model.inventory;
// Generated 2016-5-9 12:05:26 by Hibernate Tools 4.3.1

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

/**
 * InventoryPurchaseOrderShipment generated by hbm2java
 */
@Entity
@Table(name = "INVENTORY_PURCHASE_ORDER_SHIPMENT", catalog = "loccitane")
public class InventoryPurchaseOrderShipment implements java.io.Serializable {

    private Long id;
    private InventoryPurchaseOrder inventoryPurchaseOrder;
    private String deliveryNumber;
    private Date deliveryDate;
    private boolean isActive;
    private Date created;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;
    private Set<InventoryPurchaseOrderShipmentItem> inventoryPurchaseOrderShipmentItems = new HashSet<InventoryPurchaseOrderShipmentItem>(0);

    public InventoryPurchaseOrderShipment() {
    }

    public InventoryPurchaseOrderShipment(InventoryPurchaseOrder inventoryPurchaseOrder, String deliveryNumber, Date deliveryDate, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
        this.inventoryPurchaseOrder = inventoryPurchaseOrder;
        this.deliveryNumber = deliveryNumber;
        this.deliveryDate = deliveryDate;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public InventoryPurchaseOrderShipment(InventoryPurchaseOrder inventoryPurchaseOrder, String deliveryNumber, Date deliveryDate, boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy, Set<InventoryPurchaseOrderShipmentItem> inventoryPurchaseOrderShipmentItems) {
        this.inventoryPurchaseOrder = inventoryPurchaseOrder;
        this.deliveryNumber = deliveryNumber;
        this.deliveryDate = deliveryDate;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.inventoryPurchaseOrderShipmentItems = inventoryPurchaseOrderShipmentItems;
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
    @JoinColumn(name = "inventory_purchase_order_id", nullable = false)
    public InventoryPurchaseOrder getInventoryPurchaseOrder() {
        return this.inventoryPurchaseOrder;
    }

    public void setInventoryPurchaseOrder(InventoryPurchaseOrder inventoryPurchaseOrder) {
        this.inventoryPurchaseOrder = inventoryPurchaseOrder;
    }

    @Column(name = "delivery_number", nullable = false, length = 50)
    public String getDeliveryNumber() {
        return this.deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date", nullable = false, length = 19)
    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryPurchaseOrderShipment", cascade = CascadeType.ALL)
    @OrderBy(value = "id ASC")
    public Set<InventoryPurchaseOrderShipmentItem> getInventoryPurchaseOrderShipmentItems() {
        return this.inventoryPurchaseOrderShipmentItems;
    }

    public void setInventoryPurchaseOrderShipmentItems(Set<InventoryPurchaseOrderShipmentItem> inventoryPurchaseOrderShipmentItems) {
        this.inventoryPurchaseOrderShipmentItems = inventoryPurchaseOrderShipmentItems;
    }

}