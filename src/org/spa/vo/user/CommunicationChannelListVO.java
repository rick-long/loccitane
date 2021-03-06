package org.spa.vo.user;

import java.io.Serializable;

import org.spa.model.user.CommunicationChannel;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/08/1.
 */
public class CommunicationChannelListVO extends Page<CommunicationChannel> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String isActive;
	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
