package org.spa.model.salesforce;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.spa.model.user.User;

@Entity
@Table(name = "USER_MARKETING_CAMPAIGN_TRANSACTION", catalog = "loccitane")
public class UserMarketingCampaignTransaction implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
private MarketingCampaign marketingCampaign;//营销活动
	private User user;
	private Boolean isActive;
	private String createdBy;
	private Date created;
	
	public UserMarketingCampaignTransaction(){
	}
	
	public UserMarketingCampaignTransaction(User user,MarketingCampaign marketingCampaign,Boolean isActive,String createdBy, Date created) {
		this.user = user;
		this.marketingCampaign = marketingCampaign;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.created = created;
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
	@JoinColumn(name = "marketing_campaign_id", nullable = false)
	public MarketingCampaign getMarketingCampaign() {
		return marketingCampaign;
	}
	public void setMarketingCampaign(MarketingCampaign marketingCampaign) {
		this.marketingCampaign = marketingCampaign;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
