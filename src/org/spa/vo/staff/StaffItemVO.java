package org.spa.vo.staff;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/05/03.
 */
public class StaffItemVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer key;
	
	private Long staffId;
	private String staffName;
	private Boolean requested;
	
	private Double commission;
	private Double extraCommission;

	
	public Double getExtraCommission() {
		return extraCommission;
	}
	public void setExtraCommission(Double extraCommission) {
		this.extraCommission = extraCommission;
	}
	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Boolean getRequested() {
		return requested;
	}

	public void setRequested(Boolean requested) {
		this.requested = requested;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
	
	
}
