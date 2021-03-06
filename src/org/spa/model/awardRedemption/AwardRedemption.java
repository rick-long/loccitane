package org.spa.model.awardRedemption;
// Generated 2016-5-23 14:25:41 by Hibernate Tools 4.3.1.Final

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
import org.spa.model.prepaid.Prepaid;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;

/**
 * AwardRedemption generated by hbm2java
 */
@Entity
@Table(name = "AWARD_REDEMPTION", catalog = "loccitane")
public class AwardRedemption implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private ProductOption productOption;
	private String name;
	private String description;
	private String redeemType;
	private String redeemChannel;
	private double requiredLp;
	private double requiredAmount;
	private double beWorth;
	
	private Date startDate;
	private Date endDate;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;

	private String validAt;

	/*private Set<Shop> shops = new HashSet<Shop>(0);*/
	private Set<AwardRedemptionTransaction> awardRedemptionTransactions = new HashSet<AwardRedemptionTransaction>(0);
	
	public AwardRedemption() {
	}

	public AwardRedemption(String name, double requiredLp, double requiredAmount, Date startDate, boolean isActive) {
		this.name = name;
		this.requiredLp = requiredLp;
		this.requiredAmount = requiredAmount;
		this.startDate = startDate;
		this.isActive = isActive;
	}

	public AwardRedemption(ProductOption productOption, String name, String description, String redeemType,
			String redeemChannel, double requiredLp, double requiredAmount, Date startDate, Date endDate,
			boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy,
			Set<Prepaid> prepaids,Set<Shop> shops,Set<AwardRedemptionTransaction> awardRedemptionTransactions,double beWorth) {
		this.productOption = productOption;
		this.name = name;
		this.description = description;
		this.redeemType = redeemType;
		this.redeemChannel = redeemChannel;
		this.requiredLp = requiredLp;
		this.requiredAmount = requiredAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
		this.awardRedemptionTransactions=awardRedemptionTransactions;
		this.beWorth=beWorth;
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
	@JoinColumn(name = "product_option_id")
	public ProductOption getProductOption() {
		return this.productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	@Column(name = "name", nullable = false, length = 100)
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

	@Column(name = "redeem_type", length = 45)
	public String getRedeemType() {
		return this.redeemType;
	}

	public void setRedeemType(String redeemType) {
		this.redeemType = redeemType;
	}

	@Column(name = "redeem_channel", length = 45)
	public String getRedeemChannel() {
		return this.redeemChannel;
	}

	public void setRedeemChannel(String redeemChannel) {
		this.redeemChannel = redeemChannel;
	}

	@Column(name = "required_lp", nullable = false, precision = 22, scale = 0)
	public double getRequiredLp() {
		return this.requiredLp;
	}

	public void setRequiredLp(double requiredLp) {
		this.requiredLp = requiredLp;
	}

	@Column(name = "required_amount", nullable = false, precision = 22, scale = 0)
	public double getRequiredAmount() {
		return this.requiredAmount;
	}

	public void setRequiredAmount(double requiredAmount) {
		this.requiredAmount = requiredAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false, length = 19)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	/*@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "AWARD_REDEEM_LOCATION_SHOP", catalog = "loccitane", joinColumns = {
			@JoinColumn(name = "AWARD_REDEMPTION_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "SHOP_id", nullable = false, updatable = false) })
	public Set<Shop> getShops() {
		return shops;
	}
	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "awardRedemption")
	public Set<AwardRedemptionTransaction> getAwardRedemptionTransactions() {
		return this.awardRedemptionTransactions;
	}

	public void setAwardRedemptionTransactions(Set<AwardRedemptionTransaction> awardRedemptionTransactions) {
		this.awardRedemptionTransactions = awardRedemptionTransactions;
	}
	@Column(name = "be_worth", precision = 22, scale = 0)
	public double getBeWorth() {
		return beWorth;
	}
	public void setBeWorth(double beWorth) {
		this.beWorth = beWorth;
	}


	@Column(name = "valid_at")
	public String getValidAt() {
		return validAt;
	}

	public void setValidAt(String validAt) {
		this.validAt = validAt;
	}
}
