package org.spa.vo.user;

import org.spa.model.user.UserGroup;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-5-30
 */
public class UserGroupListVO extends Page<UserGroup> implements Serializable {


	private static final long serialVersionUID = 1L;

	private String name;
	private String isActive;
	private String type;
	private String module;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
}
