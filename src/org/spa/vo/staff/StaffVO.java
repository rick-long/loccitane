package org.spa.vo.staff;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.spa.vo.user.AddressVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/01/16.
 */
public class StaffVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
//	
//	@NotBlank
	private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String displayName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Long sysRoleId;

    private String password;

    private String confirmPassword;

    private String gender;

    private String mobilePhone;
    private Boolean enabled;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;

    @NotNull
    private Long[] homeShopIds;

    //tab2
    private Long[] categoryIds;
    private Long[] productIds;
    //tab3
    private Long payrollTemplateId;
    private StaffPayrollAttributeVO[] staffPayrollAttributeVOs;

    private Integer annualLeave; // 年假

    private Integer sickLeave; // 病假

    private AddressVO addressVO;//地址

    @NotNull
    private Long staffGroupForCommission;// for commission cal

    private Boolean hasMPF;

    private String barCode;
    private String pin;

	private boolean showOnApps;
    public Boolean getHasMPF() {
        return hasMPF;
    }

    public void setHasMPF(Boolean hasMPF) {
        this.hasMPF = hasMPF;
    }

    public Long getStaffGroupForCommission() {
        return staffGroupForCommission;
    }

    public void setStaffGroupForCommission(Long staffGroupForCommission) {
        this.staffGroupForCommission = staffGroupForCommission;
    }

    public AddressVO getAddressVO() {
        return addressVO;
    }

    public void setAddressVO(AddressVO addressVO) {
        this.addressVO = addressVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public StaffPayrollAttributeVO[] getStaffPayrollAttributeVOs() {
        return staffPayrollAttributeVOs;
    }

    public void setStaffPayrollAttributeVOs(StaffPayrollAttributeVO[] staffPayrollAttributeVOs) {
        this.staffPayrollAttributeVOs = staffPayrollAttributeVOs;
    }

    public Long getPayrollTemplateId() {
        return payrollTemplateId;
    }

    public void setPayrollTemplateId(Long payrollTemplateId) {
        this.payrollTemplateId = payrollTemplateId;
    }

    public Long[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Long[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Long[] productIds) {
        this.productIds = productIds;
    }

    public Long[] getHomeShopIds() {
        return homeShopIds;
    }

    public void setHomeShopIds(Long[] homeShopIds) {
        this.homeShopIds = homeShopIds;
    }
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public Integer getAnnualLeave() {
        return annualLeave;
    }

    public void setAnnualLeave(Integer annualLeave) {
        this.annualLeave = annualLeave;
    }

    public Integer getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Integer sickLeave) {
        this.sickLeave = sickLeave;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
	public boolean isShowOnApps() {
		return showOnApps;
	}

	public void setShowOnApps(boolean showOnApps) {
		this.showOnApps = showOnApps;
	}
}