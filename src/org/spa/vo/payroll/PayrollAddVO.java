package org.spa.vo.payroll;

import org.spa.vo.common.KeyAndValueVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivy on 2016/06/16.
 */
public class PayrollAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long staffId;
	
	private String payrollDate;
	
	private String serviceHours;//if paid by hour salary for part time
	
	private List<KeyAndValueVO> additionalsBfMpf;//before MPF
	
	private List<KeyAndValueVO> additionalsAfMpf;//after mpf
	
	private String remarks;
	
	public String getServiceHours() {
		return serviceHours;
	}
	public void setServiceHours(String serviceHours) {
		this.serviceHours = serviceHours;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getPayrollDate() {
		return payrollDate;
	}
	public void setPayrollDate(String payrollDate) {
		this.payrollDate = payrollDate;
	}
	public List<KeyAndValueVO> getAdditionalsAfMpf() {
		return additionalsAfMpf;
	}
	public void setAdditionalsAfMpf(List<KeyAndValueVO> additionalsAfMpf) {
		this.additionalsAfMpf = additionalsAfMpf;
	}
	public List<KeyAndValueVO> getAdditionalsBfMpf() {
		return additionalsBfMpf;
	}
	public void setAdditionalsBfMpf(List<KeyAndValueVO> additionalsBfMpf) {
		this.additionalsBfMpf = additionalsBfMpf;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
