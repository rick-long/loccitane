package org.spa.vo.payroll;

import java.io.Serializable;

import org.spa.model.payroll.StaffPayroll;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/06/16.
 */
public class PayrollListVO extends Page<StaffPayroll> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long staffId;
	
	private Long shopId;
	
	private String payrollDate;
	
	private Boolean isActive;

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getPayrollDate() {
		return payrollDate;
	}
	public void setPayrollDate(String payrollDate) {
		this.payrollDate = payrollDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
}
