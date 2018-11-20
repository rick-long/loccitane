package org.spa.model.company;
// Generated 2016-3-31 15:50:45 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.user.ConsentFormUser;
import org.spa.model.bonus.BonusRule;
import org.spa.model.bonus.BonusTemplate;
import org.spa.model.book.Book;
import org.spa.model.commission.CommissionRule;
import org.spa.model.commission.CommissionTemplate;
import org.spa.model.inventory.Inventory;
import org.spa.model.inventory.InventoryPurchaseOrder;
import org.spa.model.inventory.InventoryTransaction;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.payment.Payment;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.payroll.PayrollTemplate;
import org.spa.model.payroll.StaffPayroll;
import org.spa.model.product.Brand;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductDescription;
import org.spa.model.product.ProductDescriptionKey;
import org.spa.model.product.ProductOptionKey;
import org.spa.model.product.ProductOptionSupernumeraryPrice;
import org.spa.model.product.Supplier;
import org.spa.model.shop.Address;
import org.spa.model.shop.OpeningHours;
import org.spa.model.shop.OutSourceTemplate;
import org.spa.model.shop.Phone;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.ConsentForm;
import org.spa.model.user.User;

/**
 * Company generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "COMPANY", catalog = "loccitane")
public class Company implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String code;
	private String domain;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private Set<Address> addresses = new HashSet<Address>(0);
	private Set<Product> products = new HashSet<Product>(0);
	private Set<ProductDescriptionKey> productDescriptionKeys = new HashSet<ProductDescriptionKey>(0);
	private Set<Shop> shops = new HashSet<Shop>(0);
	private Set<Brand> brands = new HashSet<Brand>(0);
	private Set<Category> categories = new HashSet<Category>(0);
	private Set<ProductDescription> productDescriptions = new HashSet<ProductDescription>(0);
	private Set<InventoryPurchaseOrder> inventoryPurchaseOrders = new HashSet<InventoryPurchaseOrder>(0);
	private Set<Phone> phones = new HashSet<Phone>(0);
	private Set<OpeningHours> openingHourses = new HashSet<OpeningHours>(0);
	private Set<CompanyProperty> companyProperties = new HashSet<CompanyProperty>(0);
	private Set<InventoryTransaction> inventoryTransactions = new HashSet<InventoryTransaction>(0);
	private Set<Supplier> suppliers = new HashSet<Supplier>(0);
	private Set<Inventory> inventories = new HashSet<Inventory>(0);
	private Set<ProductOptionKey> productOptionKeys = new HashSet<ProductOptionKey>(0);
	private Set<Room> rooms = new HashSet<Room>(0);
	private Set<User> users = new HashSet<User>(0);
	private Set<ProductOptionSupernumeraryPrice> productOptionSupernumeraryPrices = new HashSet<ProductOptionSupernumeraryPrice>(0);
	private Set<Prepaid> prepaids = new HashSet<Prepaid>(0);
	private Set<PrepaidTopUpTransaction> prepaidTopUpTransactions = new HashSet<PrepaidTopUpTransaction>(0);
	private Set<Payment> payments = new HashSet<Payment>(0);
	private Set<PurchaseOrder> purchaseOrders = new HashSet<PurchaseOrder>(0);
	private Set<PaymentMethod> paymentMethods = new HashSet<PaymentMethod>(0);
	
	private Set<CommissionRule> commissionRules = new HashSet<CommissionRule>(0);
	private Set<CommissionTemplate> commissionTemplates = new HashSet<CommissionTemplate>(0);
	
	private Set<PayrollTemplate> payrollTemplates = new HashSet<PayrollTemplate>(0);
	private Set<StaffPayroll> staffPayrolls = new HashSet<StaffPayroll>(0);
	
	private Set<ConsentForm> consentForms = new HashSet<ConsentForm>(0);
	private Set<ConsentFormUser> consentFormUsers = new HashSet<ConsentFormUser>(0);
	
	private Set<OutSourceTemplate> outSourceTemplates = new HashSet<OutSourceTemplate>(0);
	
	private Set<BonusTemplate> bonusTemplates = new HashSet<BonusTemplate>(0);
	
	private Set<BonusRule> bonusRules = new HashSet<BonusRule>(0);
	
	private Set<Book> books = new HashSet<Book>(0);
	
	public Company() {
	}

	public Company(boolean isActive) {
		this.isActive = isActive;
	}

	public Company(String name, String code, String domain, boolean isActive, Date created, String createdBy,
			Date lastUpdated, String lastUpdatedBy, Set<Address> addresses, Set<Product> products,Set<OutSourceTemplate> outSourceTemplates,
			Set<ProductDescriptionKey> productDescriptionKeys, Set<Shop> shops, Set<Brand> brands, Set<Category> categories,
			Set<ProductDescription> productDescriptions, Set<InventoryPurchaseOrder> inventoryPurchaseOrders,
			Set<Phone> phones, Set<OpeningHours> openingHourses,Set<PayrollTemplate> payrollTemplates,
			Set<CompanyProperty> companyProperties, Set<InventoryTransaction> inventoryTransactions, Set<Supplier> suppliers,
			Set<Inventory> inventories, Set<ProductOptionKey> productOptionKeys, Set<Room> rooms, Set<User> users,
			Set<ProductOptionSupernumeraryPrice> productOptionSupernumeraryPrices, Set<Prepaid> prepaids,Set<Payment> payments,
			Set<PurchaseOrder> purchaseOrders,Set<PaymentMethod> paymentMethods,Set<PrepaidTopUpTransaction> prepaidTopUpTransactions,
			Set<CommissionRule> commissionRules,Set<CommissionTemplate> commissionTemplates,Set<StaffPayroll> staffPayrolls,
			Set<ConsentForm> consentForms,Set<ConsentFormUser> consentFormUsers,Set<BonusTemplate> bonusTemplates,Set<BonusRule> bonusRules,Set<Book> books) {
		this.name = name;
		this.code = code;
		this.domain = domain;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.addresses = addresses;
		this.products = products;
		this.productDescriptionKeys = productDescriptionKeys;
		this.shops = shops;
		this.brands = brands;
		this.categories = categories;
		this.productDescriptions = productDescriptions;
		this.inventoryPurchaseOrders = inventoryPurchaseOrders;
		this.phones = phones;
		this.openingHourses = openingHourses;
		this.companyProperties = companyProperties;
		this.inventoryTransactions = inventoryTransactions;
		this.suppliers = suppliers;
		this.inventories = inventories;
		this.productOptionKeys = productOptionKeys;
		this.rooms = rooms;
		this.users = users;
		this.productOptionSupernumeraryPrices = productOptionSupernumeraryPrices;
		this.prepaids=prepaids;
		this.purchaseOrders=purchaseOrders;
		this.payments=payments;
		this.paymentMethods=paymentMethods;
		this.prepaidTopUpTransactions=prepaidTopUpTransactions;
		this.commissionRules=commissionRules;
		this.commissionTemplates=commissionTemplates;
		this.payrollTemplates=payrollTemplates;
		this.staffPayrolls=staffPayrolls;
		this.consentForms=consentForms;
		this.consentFormUsers=consentFormUsers;
		this.outSourceTemplates=outSourceTemplates;
		this.bonusTemplates=bonusTemplates;
		this.bonusRules=bonusRules;
		this.books=books;
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

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 45)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "domain", length = 100)
	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<ProductDescriptionKey> getProductDescriptionKeys() {
		return this.productDescriptionKeys;
	}

	public void setProductDescriptionKeys(Set<ProductDescriptionKey> productDescriptionKeys) {
		this.productDescriptionKeys = productDescriptionKeys;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<ConsentForm> getConsentForms() {
		return this.consentForms;
	}

	public void setConsentForms(Set<ConsentForm> consentForms) {
		this.consentForms = consentForms;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Shop> getShops() {
		return this.shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Brand> getBrands() {
		return this.brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<ProductDescription> getProductDescriptions() {
		return this.productDescriptions;
	}

	public void setProductDescriptions(Set<ProductDescription> productDescriptions) {
		this.productDescriptions = productDescriptions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<InventoryPurchaseOrder> getInventoryPurchaseOrders() {
		return this.inventoryPurchaseOrders;
	}

	public void setInventoryPurchaseOrders(Set<InventoryPurchaseOrder> inventoryPurchaseOrders) {
		this.inventoryPurchaseOrders = inventoryPurchaseOrders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Phone> getPhones() {
		return this.phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<OpeningHours> getOpeningHourses() {
		return this.openingHourses;
	}

	public void setOpeningHourses(Set<OpeningHours> openingHourses) {
		this.openingHourses = openingHourses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<CompanyProperty> getCompanyProperties() {
		return this.companyProperties;
	}

	public void setCompanyProperties(Set<CompanyProperty> companyProperties) {
		this.companyProperties = companyProperties;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<InventoryTransaction> getInventoryTransactions() {
		return this.inventoryTransactions;
	}

	public void setInventoryTransactions(Set<InventoryTransaction> inventoryTransactions) {
		this.inventoryTransactions = inventoryTransactions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Supplier> getSuppliers() {
		return this.suppliers;
	}

	public void setSuppliers(Set<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Inventory> getInventories() {
		return this.inventories;
	}

	public void setInventories(Set<Inventory> inventories) {
		this.inventories = inventories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<ProductOptionKey> getProductOptionKeys() {
		return this.productOptionKeys;
	}

	public void setProductOptionKeys(Set<ProductOptionKey> productOptionKeys) {
		this.productOptionKeys = productOptionKeys;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<ProductOptionSupernumeraryPrice> getProductOptionSupernumeraryPrices() {
		return this.productOptionSupernumeraryPrices;
	}

	public void setProductOptionSupernumeraryPrices(
			Set<ProductOptionSupernumeraryPrice> productOptionSupernumeraryPrices) {
		this.productOptionSupernumeraryPrices = productOptionSupernumeraryPrices;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Prepaid> getPrepaids() {
		return this.prepaids;
	}

	public void setPrepaids(Set<Prepaid> prepaids) {
		this.prepaids = prepaids;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<PurchaseOrder> getPurchaseOrders() {
		return this.purchaseOrders;
	}

	public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<PaymentMethod> getPaymentMethods() {
		return this.paymentMethods;
	}

	public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<PrepaidTopUpTransaction> getPrepaidTopUpTransactions() {
		return prepaidTopUpTransactions;
	}
	public void setPrepaidTopUpTransactions(Set<PrepaidTopUpTransaction> prepaidTopUpTransactions) {
		this.prepaidTopUpTransactions = prepaidTopUpTransactions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<CommissionRule> getCommissionRules() {
		return this.commissionRules;
	}

	public void setCommissionRules(Set<CommissionRule> commissionRules) {
		this.commissionRules = commissionRules;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<StaffPayroll> getStaffPayrolls() {
		return staffPayrolls;
	}
	public void setStaffPayrolls(Set<StaffPayroll> staffPayrolls) {
		this.staffPayrolls = staffPayrolls;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<CommissionTemplate> getCommissionTemplates() {
		return commissionTemplates;
	}
	public void setCommissionTemplates(Set<CommissionTemplate> commissionTemplates) {
		this.commissionTemplates = commissionTemplates;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<PayrollTemplate> getPayrollTemplates() {
		return this.payrollTemplates;
	}

	public void setPayrollTemplates(Set<PayrollTemplate> payrollTemplates) {
		this.payrollTemplates = payrollTemplates;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<ConsentFormUser> getConsentFormUsers() {
		return this.consentFormUsers;
	}

	public void setConsentFormUsers(Set<ConsentFormUser> consentFormUsers) {
		this.consentFormUsers = consentFormUsers;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<OutSourceTemplate> getOutSourceTemplates() {
		return this.outSourceTemplates;
	}

	public void setOutSourceTemplates(Set<OutSourceTemplate> outSourceTemplates) {
		this.outSourceTemplates = outSourceTemplates;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<BonusTemplate> getBonusTemplates() {
		return bonusTemplates;
	}
	public void setBonusTemplates(Set<BonusTemplate> bonusTemplates) {
		this.bonusTemplates = bonusTemplates;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<BonusRule> getBonusRules() {
		return bonusRules;
	}
	public void setBonusRules(Set<BonusRule> bonusRules) {
		this.bonusRules = bonusRules;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Book> getBooks() {
		return books;
	}
	
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
}
