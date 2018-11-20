package org.spa.vo.shop;

import java.io.Serializable;
/**
 * Created by Ivy on 2016/01/16.
 */
public class OpeningHoursVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String closeTime;
	
	private String openTime;
	private String dayOfWeek;
	private Long id;
	private String isOnlineBooking;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getIsOnlineBooking() {
		return isOnlineBooking;
	}

	public void setIsOnlineBooking(String isOnlineBooking) {
		this.isOnlineBooking = isOnlineBooking;
	}
}
