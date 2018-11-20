package com.spa.controller.api;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.user.User;
import org.spa.serviceImpl.loyalty.UserLoyaltyLevelServiceImpl;
import org.spa.utils.DateUtil;
import org.spa.utils.SpringUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class MemberAppsApiVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String username ;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private String email;
    private boolean enabled;
    private String accountType;
    private Date joinDate;
    private Date created;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;
    private String preferredContact;
    private Date dateOfBirth;
    private String notification;
    private Boolean activing;
    private String remarks;
    private String airmilesMembershipNumber;
    private String displayName;
    private Boolean hasMPF;
    private Long oldId;
    private Date lastModifier;
    private Date lastSFUpdated;
    private Boolean optedOut;
    private String divaLevel;
    private String divaExpiryDate;
    private Double balance;
    
    
    MemberAppsApiVo (User user){
    this.username=user.getUsername();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.fullName=user.getFullName();
        this.email=user.getEmail();
        this.gender=user.getGender();
        this.enabled=user.isEnabled();
        this.accountType=user.getAccountType();
        this.joinDate=user.getJoinDate();
        this.created=user.getCreated();
        this.createdBy=user.getCreatedBy();
        this.lastUpdated=user.getLastUpdated();
        this.lastUpdatedBy=user.getLastUpdatedBy();
        this.preferredContact=user.getPreferredContact();
        this.dateOfBirth=user.getDateOfBirth();
        this.notification=user.getNotification();
        this.activing=user.getActiving();
        this.remarks=user.getRemarks();
        this.airmilesMembershipNumber=user.getAirmilesMembershipNumber();
        this.displayName=user.getDisplayName();
        this.hasMPF=user.getHasMPF();
        this.oldId=user.getOldId();
        this.lastModifier=user.getLastModifier();
        this.lastSFUpdated=user.getLastSFUpdated();
        this.optedOut=user.getOptedOut();
        this.divaLevel=user.getCurrentLoyaltyLevel().getName();
        
        this.balance=user.getRemainValueOfCashPackage();
        this.divaExpiryDate=getDivaExpiryDateFormat(user);
    }

    public String getDivaExpiryDateFormat(User user){
    	String expiryDateFormat ="N/A";
    	if(user.getCurrentLoyaltyLevel() !=null){
    		DetachedCriteria ulldc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
    		ulldc.add(Restrictions.eq("user.id", user.getId()));
    		ulldc.add(Restrictions.eq("isActive", true));
    		List<UserLoyaltyLevel> ullListTrue=SpringUtil.getBean(UserLoyaltyLevelServiceImpl.class).list(ulldc);
    		Date expiryDate=ullListTrue.get(0).getExpiryDate();
    		if(expiryDate !=null){
    			try {
    				expiryDateFormat =DateUtil.dateToString(expiryDate, "yyyy-MM-dd");
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return expiryDateFormat;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getPreferredContact() {
        return preferredContact;
    }

    public void setPreferredContact(String preferredContact) {
        this.preferredContact = preferredContact;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Boolean getActiving() {
        return activing;
    }

    public void setActiving(Boolean activing) {
        this.activing = activing;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAirmilesMembershipNumber() {
        return airmilesMembershipNumber;
    }

    public void setAirmilesMembershipNumber(String airmilesMembershipNumber) {
        this.airmilesMembershipNumber = airmilesMembershipNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getHasMPF() {
        return hasMPF;
    }

    public void setHasMPF(Boolean hasMPF) {
        this.hasMPF = hasMPF;
    }

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }

    public Date getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(Date lastModifier) {
        this.lastModifier = lastModifier;
    }

    public Date getLastSFUpdated() {
        return lastSFUpdated;
    }

    public void setLastSFUpdated(Date lastSFUpdated) {
        this.lastSFUpdated = lastSFUpdated;
    }

    public Boolean getOptedOut() {
        return optedOut;
    }

    public void setOptedOut(Boolean optedOut) {
        this.optedOut = optedOut;
    }
    
    public Double getBalance() {
		return balance;
	}
    public void setBalance(Double balance) {
		this.balance = balance;
	}
    public String getDivaLevel() {
		return divaLevel;
	}
    public void setDivaLevel(String divaLevel) {
		this.divaLevel = divaLevel;
	}
    
    public String getDivaExpiryDate() {
		return divaExpiryDate;
	}
    public void setDivaExpiryDate(String divaExpiryDate) {
		this.divaExpiryDate = divaExpiryDate;
	}
}
