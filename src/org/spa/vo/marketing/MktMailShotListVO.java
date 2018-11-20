package org.spa.vo.marketing;

import java.io.Serializable;

import org.spa.model.marketing.MktMailShot;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016-6-1
 */
public class MktMailShotListVO extends Page<MktMailShot> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String isActive;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
