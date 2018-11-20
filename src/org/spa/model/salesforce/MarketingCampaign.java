package org.spa.model.salesforce;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MARKETING_CAMPAIGN", catalog = "loccitane")
public class MarketingCampaign implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String code;//7,19,16....
	private String subCode;//A/B
	private String name;
	private String createdBy;
	private Date created;
	
	private Set<UserMarketingCampaignTransaction> userMarketingCampaignTransactions =new HashSet<UserMarketingCampaignTransaction>();
	
	public MarketingCampaign(){
	}
	
	public MarketingCampaign(String subCode,String code,String name,String createdBy, Date created,Set<UserMarketingCampaignTransaction> userMarketingCampaignTransactions) {
		this.subCode=subCode;
		this.code=code;
		this.name=name;
		this.createdBy = createdBy;
		this.created = created;
		this.userMarketingCampaignTransactions=userMarketingCampaignTransactions;
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
	
	@Column(name = "sub_code", length = 100)
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	
	@Column(name = "code",nullable = false,length = 100)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "name", length = 255)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "marketingCampaign")
	public Set<UserMarketingCampaignTransaction> getUserMarketingCampaignTransactions() {
		return userMarketingCampaignTransactions;
	}
	public void setUserMarketingCampaignTransactions(
			Set<UserMarketingCampaignTransaction> userMarketingCampaignTransactions) {
		this.userMarketingCampaignTransactions = userMarketingCampaignTransactions;
	}
}
