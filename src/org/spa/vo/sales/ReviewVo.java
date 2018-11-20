package org.spa.vo.sales;

import org.spa.model.order.ReviewRatingShop;
import org.spa.model.order.ReviewRatingTreatment;
import org.spa.model.order.ReviewRatingTreatmentTherapist;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReviewVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long orderId;
	private Long userId;
	private String reviewText;
	private boolean isActive;
	private Date created;
	private String createdBy;
	private Date lastUpdated;
	private String lastUpdatedBy;
	private Integer nps;
	List<ReviewRatingShopVO>reviewRatingShops;
	List<ReviewRatingTreatmentVO>reviewRatingTreatments;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getNps() {
		return nps;
	}

	public void setNps(Integer nps) {
		this.nps = nps;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
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


	public List<ReviewRatingShopVO> getReviewRatingShops() {
		return reviewRatingShops;
	}

	public void setReviewRatingShops(List<ReviewRatingShopVO> reviewRatingShops) {
		this.reviewRatingShops = reviewRatingShops;
	}

	public List<ReviewRatingTreatmentVO> getReviewRatingTreatments() {
		return reviewRatingTreatments;
	}

	public void setReviewRatingTreatments(List<ReviewRatingTreatmentVO> reviewRatingTreatments) {
		this.reviewRatingTreatments = reviewRatingTreatments;
	}
}
