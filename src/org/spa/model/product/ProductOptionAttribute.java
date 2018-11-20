package org.spa.model.product;
// Generated 2016-3-31 15:50:45 by Hibernate Tools 4.3.1.Final

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ProductOptionAttribute generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "PRODUCT_OPTION_ATTRIBUTE", catalog = "loccitane")
public class ProductOptionAttribute implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private ProductOption productOption;
	private ProductOptionKey productOptionKey;
	private String value;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;

	public ProductOptionAttribute() {
	}

	public ProductOptionAttribute(ProductOption productOption, ProductOptionKey productOptionKey, boolean isActive) {
		this.productOption = productOption;
		this.productOptionKey = productOptionKey;
		this.isActive = isActive;
	}

	public ProductOptionAttribute(ProductOption productOption, ProductOptionKey productOptionKey, String value,
			boolean isActive, Date created, String createdBy, Date lastUpdated, String lastUpdatedBy) {
		this.productOption = productOption;
		this.productOptionKey = productOptionKey;
		this.value = value;
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
	@JoinColumn(name = "product_option_id", nullable = false)
	public ProductOption getProductOption() {
		return this.productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_option_key_id", nullable = false)
	public ProductOptionKey getProductOptionKey() {
		return this.productOptionKey;
	}

	public void setProductOptionKey(ProductOptionKey productOptionKey) {
		this.productOptionKey = productOptionKey;
	}

	@Column(name = "value")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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

}