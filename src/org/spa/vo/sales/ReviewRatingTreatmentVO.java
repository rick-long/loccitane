package org.spa.vo.sales;
// Generated 2018-1-18 15:50:45 by Hibernate Tools 4.3.1.Final

import org.spa.model.order.ReviewRatingTreatment;
import org.spa.vo.page.Page;

import java.util.Date;
import java.util.List;

/**
 * create jason 2018-1-24
 */
public class ReviewRatingTreatmentVO  extends Page<ReviewRatingTreatment> implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long reviewId;
	private Long productOptionId;
	private Long productId;
    private  Integer satisfactionLevelStar;
	private  Integer valueForMoneyStar;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private List<ReviewRatingTreatmentTherapistVO> reviewRatingTreatmentTherapists ;
	private String fromDate;
	private String toDate;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Long getProductOptionId() {
		return productOptionId;
	}

	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getSatisfactionLevelStar() {
		return satisfactionLevelStar;
	}

	public void setSatisfactionLevelStar(Integer satisfactionLevelStar) {
		this.satisfactionLevelStar = satisfactionLevelStar;
	}

	public Integer getValueForMoneyStar() {
		return valueForMoneyStar;
	}

	public void setValueForMoneyStar(Integer valueForMoneyStar) {
		this.valueForMoneyStar = valueForMoneyStar;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public List<ReviewRatingTreatmentTherapistVO> getReviewRatingTreatmentTherapists() {
		return reviewRatingTreatmentTherapists;
	}

	public void setReviewRatingTreatmentTherapists(List<ReviewRatingTreatmentTherapistVO> reviewRatingTreatmentTherapists) {
		this.reviewRatingTreatmentTherapists = reviewRatingTreatmentTherapists;
	}
}
