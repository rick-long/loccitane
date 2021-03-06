package org.spa.model.order;
// Generated 2016-4-21 11:33:53 by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.spa.model.book.Book;
import org.spa.model.company.Company;
import org.spa.model.inventory.InventoryTransaction;
import org.spa.model.payment.Payment;
import org.spa.model.points.LoyaltyPointsTransaction;
import org.spa.model.points.SpaPointsTransaction;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.NumberUtil;

/**
 * PurchaseOrder generated by hbm2java
 */
@Entity
@Table(name = "PURCHASE_ORDER", catalog = "loccitane")
public class PurchaseOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Company company;
	private Book book;
	private Shop shop;
	private User user;
	private String reference;
	private Date purchaseDate;
	private double totalAmount;
	private Double totalDiscount;
	private Double taxAmount;
	private String status;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private String remarks;
	private Boolean showRemarksInInvoice;
	private Boolean isHotelGuest;
	
	private Set<Payment> payments = new HashSet<Payment>(0);
	private Set<PurchaseItem> purchaseItems = new HashSet<PurchaseItem>(0);
	private Set<LoyaltyPointsTransaction> loyaltyPointsTransactions = new HashSet<LoyaltyPointsTransaction>(0);
	private Set<SpaPointsTransaction> spaPointsTransactions = new HashSet<SpaPointsTransaction>(0);
	
	private Set<InventoryTransaction> inventoryTransactions = new HashSet<InventoryTransaction>(0);
	
	private Set<PurchaseOrderOutSourceAttribute> purchaseOrderOutSourceAttributes = new HashSet<PurchaseOrderOutSourceAttribute>(
			0);

	private Set<Review> reviews = new HashSet<Review>(0);
	
	private Set<OrderSurvey> orderSurveys = new HashSet<OrderSurvey>(0);
	
	
    private String oldId;

    @Column(name = "old_id", length = 45)
    public String getOldId() {
        return this.oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }
	public PurchaseOrder() {
	}

	public PurchaseOrder(long id, Company company, Shop shop, User user, String reference, Date purchaseDate,
			double totalAmount, String status, boolean isActive) {
		this.id = id;
		this.company = company;
		this.shop = shop;
		this.user = user;
		this.reference = reference;
		this.purchaseDate = purchaseDate;
		this.totalAmount = totalAmount;
		this.status = status;
		this.isActive = isActive;
	}

	public PurchaseOrder(long id, Company company, Shop shop, User user, String reference, Date purchaseDate,Boolean showRemarksInInvoice,Boolean isHotelGuest,
			double totalAmount, Double totalDiscount, Double taxAmount, String status, boolean isActive, Date created,String remarks,
			String createdBy, Date lastUpdated, String lastUpdatedBy, Set<Payment> payments,Set<PurchaseOrderOutSourceAttribute> purchaseOrderOutSourceAttributes,
			Set<PurchaseItem> purchaseItems,Book book,Set<SpaPointsTransaction> spaPointsTransactions,Set<LoyaltyPointsTransaction> loyaltyPointsTransactions,Set<InventoryTransaction> inventoryTransactions,
			Set<Review> reviews,Set<OrderSurvey> orderSurveys) {
		this.id = id;
		this.company = company;
		this.shop = shop;
		this.user = user;
		this.reference = reference;
		this.purchaseDate = purchaseDate;
		this.totalAmount = totalAmount;
		this.totalDiscount = totalDiscount;
		this.taxAmount = taxAmount;
		this.status = status;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.payments = payments;
		this.purchaseItems = purchaseItems;
		this.book=book;
		this.loyaltyPointsTransactions=loyaltyPointsTransactions;
		this.spaPointsTransactions=spaPointsTransactions;
		this.purchaseOrderOutSourceAttributes=purchaseOrderOutSourceAttributes;
		this.remarks=remarks;
		this.inventoryTransactions=inventoryTransactions;
		this.showRemarksInInvoice=showRemarksInInvoice;
		this.isHotelGuest=isHotelGuest;
		this.reviews=reviews;
		this.orderSurveys=orderSurveys;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id", nullable = false)
	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "reference", nullable = false, length = 45)
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "purchase_date", nullable = false, length = 19)
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Column(name = "total_amount", nullable = false, precision = 22, scale = 0)
	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "total_discount", precision = 22, scale = 0)
	public Double getTotalDiscount() {
		return this.totalDiscount;
	}

	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	@Column(name = "tax_amount", precision = 22, scale = 0)
	public Double getTaxAmount() {
		return this.taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "is_active", nullable = false)
	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "remarks",length = 255)
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder",cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder",cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<PurchaseItem> getPurchaseItems() {
		return this.purchaseItems;
	}

	public void setPurchaseItems(Set<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder",cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<LoyaltyPointsTransaction> getLoyaltyPointsTransactions() {
		return loyaltyPointsTransactions;
	}
	public void setLoyaltyPointsTransactions(Set<LoyaltyPointsTransaction> loyaltyPointsTransactions) {
		this.loyaltyPointsTransactions = loyaltyPointsTransactions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
	public Set<SpaPointsTransaction> getSpaPointsTransactions() {
		return spaPointsTransactions;
	}
	public void setSpaPointsTransactions(Set<SpaPointsTransaction> spaPointsTransactions) {
		this.spaPointsTransactions = spaPointsTransactions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
	public Set<InventoryTransaction> getInventoryTransactions() {
		return inventoryTransactions;
	}
	public void setInventoryTransactions(Set<InventoryTransaction> inventoryTransactions) {
		this.inventoryTransactions = inventoryTransactions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<PurchaseOrderOutSourceAttribute> getPurchaseOrderOutSourceAttributes() {
		return this.purchaseOrderOutSourceAttributes;
	}

	public void setPurchaseOrderOutSourceAttributes(
			Set<PurchaseOrderOutSourceAttribute> purchaseOrderOutSourceAttributes) {
		this.purchaseOrderOutSourceAttributes = purchaseOrderOutSourceAttributes;
	}
	
	@Column(name = "show_remarks_in_invoice")
	public Boolean getShowRemarksInInvoice() {
		return showRemarksInInvoice;
	}
	public void setShowRemarksInInvoice(Boolean showRemarksInInvoice) {
		this.showRemarksInInvoice = showRemarksInInvoice;
	}
	@Column(name = "is_hotel_guest")
	public Boolean getIsHotelGuest() {
		return isHotelGuest;
	}
	public void setIsHotelGuest(Boolean isHotelGuest) {
		this.isHotelGuest = isHotelGuest;
	}
	
//	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Review> getReviews() {
		return reviews;
	}
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<OrderSurvey> getOrderSurveys() {
		return orderSurveys;
	}
	public void setOrderSurveys(Set<OrderSurvey> orderSurveys) {
		this.orderSurveys = orderSurveys;
	}
	
	@Transient
	public String getPaymentMethodsAndAmount(){
		StringBuilder sb = new StringBuilder();
		if(getPayments() !=null){
			for (Payment pay : getPayments()) {
				if(pay.getAmount() >0){
					sb.append(pay.getPaymentMethod().getName());
		            sb.append(": ");
		            sb.append(NumberUtil.mathRoundHalfUp(2, pay.getAmount()));
		            sb.append("<br/>");
				}
	        }
		}
        return sb.toString();
	}
	@Transient
	public String getPaymentMethodsAndAmount2(){
		StringBuilder sb = new StringBuilder();
		if(getPayments() !=null){
			int i=0;
			int size = getPayments().size();
			for (Payment pay : getPayments()) {
				i++;
				if(pay.getAmount() ==0){
					if(size ==i &&  size >1){
						if(sb.length() >1){
							sb.deleteCharAt(sb.lastIndexOf("/"));
						}
					}
					continue;
				}
				sb.append(pay.getPaymentMethod().getName());
	            sb.append(": ");
	            sb.append(NumberUtil.mathRoundHalfUp(2, pay.getAmount()));
	            if(size !=i){
	            	 sb.append(" / ");
	            }
	        }
		}
        return sb.toString();
	}
	
	@Transient
	public int getTotalQty(){
		int totalQty=0;
		for(PurchaseItem item : getPurchaseItems()){
			totalQty=totalQty+item.getQty();
		}
		return totalQty;
	}
	
	@Transient
	public int getTotalTherapists(){
		int totalTherapist=0;
		for(PurchaseItem item : getPurchaseItems()){
			totalTherapist=totalTherapist+item.getStaffCommissions().size();
		}
		return totalTherapist;
	}
	
	@Transient
	public Double getTotalCommission(){
		Double totalComm=0d;
		for(PurchaseItem item : getPurchaseItems()){
			for(StaffCommission sc : item.getStaffCommissions()){
				totalComm=totalComm+sc.getCommissionValue();
			}
		}
		return totalComm;
	}
	
	@Transient
	public Boolean getCanBeRemoved(){
		boolean canBeRemoved=true;
		for(PurchaseItem item : getPurchaseItems()){
			if(item.getBuyPrepaidTopUpTransaction() !=null){
				if(item.getBuyPrepaidTopUpTransaction().getIsUsedTopUpTransaction()){
					canBeRemoved=false;
					break;
				}
				Prepaid prepaid=item.getBuyPrepaidTopUpTransaction().getPrepaid();
				//
				if(prepaid.getPrepaidTopUpTransactions().size()>1 //是充值的记录
						&& prepaid.getFirstPrepaidTopUpTransaction().getFirstPurchaseItem().getId() ==item.getId()){//第一次充值
					canBeRemoved=false;
					break;
				}
			}
		}
		return canBeRemoved;
	}
	
	@Transient
	public String getPurchaseItemNamesTherapist() {
		String purchaseItemNames = "";
		Set s = getPurchaseItems();
		if (s!=null && s.size()>0) {
			Iterator it = s.iterator();
			while (it.hasNext()) {
				PurchaseItem item = (PurchaseItem) it.next();
				if (item.getId() != null && item.getId().longValue() > 0) {
					if (purchaseItemNames!=null && purchaseItemNames.trim().length()>0) {
						purchaseItemNames += " <br/>";
					}
					if( item.getProductOption() !=null){
						purchaseItemNames += item.getProductOption().getLabel3()+ " (" + item.getTherapistNames()+")";
					}else{
						purchaseItemNames += item.getBuyPrepaidTopUpTransaction().getPrepaid().getName()+ " (" + item.getTherapistNames()+")";
					}
					
				}
			}		
		}
		return purchaseItemNames;
	}
	
	@Transient
	public String getHotelGuest(){
		if(getIsHotelGuest() !=null && getIsHotelGuest()){
			return "Y";
		}
		return "N";
	}
	@Transient
	public Double getTotalEffectivevalue(){
    	Double totalEffectiveValue= 0d;
		Set s = getPurchaseItems();
		if (s!=null && s.size()>0) {
			Iterator it = s.iterator();
			while (it.hasNext()) {
				PurchaseItem item = (PurchaseItem) it.next();
				totalEffectiveValue += item.getEffectiveValue();
			}
		}
		return totalEffectiveValue;
	}
}
