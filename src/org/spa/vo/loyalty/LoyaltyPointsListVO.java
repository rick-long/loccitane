package org.spa.vo.loyalty;
import java.io.Serializable;
import java.util.Date;

import org.spa.model.points.LoyaltyPointsTransaction;
import org.spa.vo.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Ivy on 2016/05/17.
 */
public class LoyaltyPointsListVO extends Page<LoyaltyPointsTransaction> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long memberId;
	private String username;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;
	
	private Long shopId;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
}
