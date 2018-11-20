package org.spa.vo.marketing;

import org.spa.model.marketing.MktChannel;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-6-1
 */
public class MktChannelListVO extends Page<MktChannel> implements Serializable {

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
