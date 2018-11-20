package org.spa.model.bundle;

import org.spa.model.company.Company;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product_bundle", catalog = "loccitane")
public class ProductBundle implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String code;
    private Company company;
    private String name;
    private Date startTime;
    private Date endTime;
    private String description;
    private boolean isActive;
    private Date created;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;
    private Double bundleAmount;

    private Set<Shop> shops = new HashSet<Shop>();
    private Set<ProductBundleProductOption> productBundleProductOptions =new HashSet<ProductBundleProductOption>();

    @Id
    @Column(name = "id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "is_active", nullable = false)
    public boolean isIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Basic
    @Column(name = "created_by", nullable = false, length = 100)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "last_updated", nullable = false)
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Basic
    @Column(name = "last_updated_by", nullable = false, length = 100)
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_bundle_shop", catalog = "loccitane", joinColumns = { @JoinColumn(name = "product_bundle_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "shop_id", nullable = false, updatable = false) })
    public Set<Shop> getShops() {
        return this.shops;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productBundle", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ProductBundleProductOption> getProductBundleProductOptions() {
		return productBundleProductOptions;
	}
    public void setProductBundleProductOptions(Set<ProductBundleProductOption> productBundleProductOptions) {
		this.productBundleProductOptions = productBundleProductOptions;
	}
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "bundle_amount", nullable = false, precision = 0)
    public double getBundleAmount() {
        return bundleAmount;
    }

    public void setBundleAmount(double bundleAmount) {
        this.bundleAmount = bundleAmount;
    }
    
    @Transient
    public int getGroups(){
    	Map<Integer,List<ProductOption>> bundleMap =getBundleDetails();
    	if(bundleMap !=null && bundleMap.size()>0){
    		return bundleMap.keySet().size();
    	}else{
    		return 0;
    	}
    	
    }
    @Transient
    public Map<Integer,List<ProductOption>> getBundleDetails(){
    	Map<Integer,List<ProductOption>> bundleMap =new HashMap<Integer,List<ProductOption>>();
    	for(ProductBundleProductOption pbp : getProductBundleProductOptions()){
    		List<ProductOption> productOptions = bundleMap.get(pbp.getGroups());
    		if(productOptions !=null && productOptions.size()>0){
    			productOptions.add(pbp.getProductOption());
    		}else{
    			productOptions = new ArrayList<ProductOption>();
    			productOptions.add(pbp.getProductOption());
    			bundleMap.put(pbp.getGroups(), productOptions);
    		}
    	}
    	return bundleMap;
    }

    @Transient
    public String getProductOptionGroup(){
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Integer, List<ProductOption>>> setMap = getBundleDetails().entrySet();
        for (Map.Entry<Integer, List<ProductOption>> entry : setMap) {
            Integer key = entry.getKey();
            key+=1;
            sb.append("Group"+key);
            for (ProductOption po : entry.getValue()) {
                sb.append(" "+po.getLabel33()+"/");
            }
        }

        return sb.toString();
    }

    @Transient
    public String getShopName(){

        StringBuilder sb = new StringBuilder();
        for (Shop s : getShops()) {
            sb.append(s.getName()+" ");
        }

        return sb.toString();
    }
}
