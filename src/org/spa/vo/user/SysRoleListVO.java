package org.spa.vo.user;

import java.io.Serializable;

import org.spa.model.privilege.SysRole;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016-5-24
 */
public class SysRoleListVO extends Page<SysRole> implements Serializable {


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
