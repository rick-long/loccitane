package org.spa.vo.app.callback;

import java.io.Serializable;

public class OrderCalAmtCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
    private String currency;
    private String shopAmtStr;
    
    private String onlineAmtStr;

	private Double shopAmt;
	private Double onlineAmt;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getShopAmtStr() {
		return shopAmtStr;
	}
	public void setShopAmtStr(String shopAmtStr) {
		this.shopAmtStr = shopAmtStr;
	}
	public String getOnlineAmtStr() {
		return onlineAmtStr;
	}
	public void setOnlineAmtStr(String onlineAmtStr) {
		this.onlineAmtStr = onlineAmtStr;
	}
	public Double getShopAmt() {
		return shopAmt;
	}
	public void setShopAmt(Double shopAmt) {
		this.shopAmt = shopAmt;
	}
	public Double getOnlineAmt() {
		return onlineAmt;
	}
	public void setOnlineAmt(Double onlineAmt) {
		this.onlineAmt = onlineAmt;
	}
	
	
    
}
