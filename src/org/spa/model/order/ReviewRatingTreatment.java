package org.spa.model.order;
// Generated 2018-1-18 15:50:45 by Hibernate Tools 4.3.1.Final

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Coment generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "REVIEW_RATING_TREATMENT", catalog = "loccitane")
public class ReviewRatingTreatment implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Review review;
	private ProductOption productOption;
	private Product product;
    private  Integer satisfactionLevelStar;
	private  Integer valueForMoneyStar;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;

	private Set<ReviewRatingTreatmentTherapist> reviewRatingTreatmentTherapists = new HashSet<>(0);

	public ReviewRatingTreatment() {
	}
	public ReviewRatingTreatment(boolean isActive) {
		this.isActive = isActive;
	}

	public ReviewRatingTreatment(long id,Review review, ProductOption productOption,Product product, Integer satisfactionLevelStar, Integer valueForMoneyStar, boolean isActive, Date created, String createdBy,
                                 Date lastUpdated, String lastUpdatedBy) {
		this.id=id;
		this.review = review;
		this.productOption=productOption;
		this.product = product;
		this.satisfactionLevelStar = satisfactionLevelStar;
		this.valueForMoneyStar = valueForMoneyStar;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.lastUpdatedBy = lastUpdatedBy;
/*		this.reviewRatingTreatmentTherapists=reviewRatingTreatmentTherapists;*/
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
	@JoinColumn(name = "review_id",nullable = false )
	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id",nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_option_id",nullable = false)
	public ProductOption getProductOption() {
		return this.productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	@Column(name = "satisfaction_level_star")
	public Integer getSatisfactionLevelStar() {
		return this.satisfactionLevelStar;
	}

	public void setSatisfactionLevelStar(Integer satisfactionLevelStar) {
		this.satisfactionLevelStar = satisfactionLevelStar;
	}
	@Column(name = "value_for_money_star")
	public Integer getValueForMoneyStar() {
		return this.valueForMoneyStar;
	}

	public void setValueForMoneyStar(Integer valueForMoneyStar) {
		this.valueForMoneyStar = valueForMoneyStar;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewRatingTreatment", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ReviewRatingTreatmentTherapist> getReviewRatingTreatmentTherapists() {
		return reviewRatingTreatmentTherapists;
	}
	public void setReviewRatingTreatmentTherapists(Set<ReviewRatingTreatmentTherapist> reviewRatingTreatmentTherapists) {
		this.reviewRatingTreatmentTherapists = reviewRatingTreatmentTherapists;
	}

}
