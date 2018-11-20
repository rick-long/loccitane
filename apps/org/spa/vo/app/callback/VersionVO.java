package org.spa.vo.app.callback;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/08/01.
 */
public class VersionVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String os;
	private Double versionCode;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Double getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Double versionCode) {
		this.versionCode = versionCode;
	}
}
