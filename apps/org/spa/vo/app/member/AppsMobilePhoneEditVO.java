package org.spa.vo.app.member;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class AppsMobilePhoneEditVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String notification;
	private String mobilePhone;
	private Boolean optedOut;//
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Boolean getOptedOut() {
		return optedOut;
	}

	public void setOptedOut(Boolean optedOut) {
		this.optedOut = optedOut;
	}
}

