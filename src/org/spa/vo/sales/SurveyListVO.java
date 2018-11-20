package org.spa.vo.sales;

import java.io.Serializable;

import org.spa.model.order.OrderSurvey;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2018/01/25.
 */
public class SurveyListVO extends Page<OrderSurvey> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String fromDate;
	
	private Long shopId;

	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
}
