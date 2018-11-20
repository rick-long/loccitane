package org.spa.vo.user;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/8/28.
 */
public class ConsentFormUserSignVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@NotNull
	private Long userId;
	private String userFullName;
	
	private List<ConsentFormUserVO> consentFormUserVOs;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	
	public List<ConsentFormUserVO> getConsentFormUserVOs() {
		return consentFormUserVOs;
	}
	public void setConsentFormUserVOs(List<ConsentFormUserVO> consentFormUserVOs) {
		this.consentFormUserVOs = consentFormUserVOs;
	}
}
