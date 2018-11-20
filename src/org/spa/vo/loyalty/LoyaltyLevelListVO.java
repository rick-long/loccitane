package org.spa.vo.loyalty;
import java.io.Serializable;

import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/05/17.
 */
public class LoyaltyLevelListVO extends Page<LoyaltyLevel> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private Boolean isActive;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
