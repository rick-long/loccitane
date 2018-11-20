package org.spa.vo.app.callback;

import com.spa.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.shop.Address;
import org.spa.model.shop.Phone;
import org.spa.model.user.User;
import org.spa.serviceImpl.loyalty.UserLoyaltyLevelServiceImpl;
import org.spa.utils.DateUtil;
import org.spa.utils.NumberUtil;
import org.spa.utils.SpringUtil;
import org.spa.vo.user.AddressVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MemberCallBackVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String fullName;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String mobilePhone;
    private String notification;
    private String lastModifier;
    private Boolean optedOut;
    private String divaLevel;
    private String divaExpiryDate;
    private Double balance;
    private String ShopName;
    private Long   shopId;
    private Double remainLoyaltyPoints;
    private String addressExtention;
    private String district;
    private String dateOfBirth;
    private String token;

    public MemberCallBackVO() {
		super();
	}
    public  MemberCallBackVO (User user){
        this.id=user.getId();
        this.userName=user.getUsername();
        this.fullName=user.getFullName();
        this.email=user.getEmail();
        this.dateOfBirth=dateToString(user.getDateOfBirth(),"yyyy-MM-dd");
        this.notification=user.getNotification();
        this.ShopName=user.getShop().getName();
        this.lastModifier=dateToString(user.getLastModifier(),"yyyy-MM-dd");
        this.optedOut=user.getOptedOut();
        this.divaLevel=user.getCurrentLoyaltyLevel().getName();
        this.balance=user.getRemainValueOfCashPackage();
        this.divaExpiryDate=getDivaExpiryDateFormat(user);
        this.addressExtention=getAddresse(user.getAddresses()).getAddressExtention();
        this.district=getAddresse(user.getAddresses()).getDistrict();
        this.remainLoyaltyPoints= remainLoyaltyPoints(user.getRemainLoyaltyPoints());
        this.mobilePhone=byTypeGetMobilePhone(user.getPhones());
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.shopId=user.getShop().getId();
        if(user.getGender().equals("FEMALE")){
            this.gender="1";
        }else{
            this.gender="0";
        }
    }
    private Double remainLoyaltyPoints(Double remainLoyaltyPoints ){
        remainLoyaltyPoints= NumberUtil.mathRoundHalfUp(2,remainLoyaltyPoints);
        return remainLoyaltyPoints;
    }
    public AddressVO getAddresse(Set<Address> addresses){
        AddressVO addressVO=new AddressVO();
      if(addresses!=null&&addresses.size()>0 ){
         for(Address address:addresses){
             addressVO.setAddressExtention(address.getAddressExtention());
             addressVO.setDistrict(address.getDistrict());
             continue;
         }
      }

        return addressVO;
    }

    private String byTypeGetMobilePhone(Set<Phone> phones){
        String mobilePhone="";
        if(phones!=null&&phones.size()>0){
            for(Phone phone: phones){
                if(phone.getType().equals(CommonConstant.PHONE_TYPE_MOBILE)){
                    mobilePhone=phone.getNumber();
                }
            }
        }
    return mobilePhone;
    }
    private String getDivaExpiryDateFormat(User user){
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

    private String  dateToString(Date date,String format ){
        String dateTime="";
        try {
            dateTime=DateUtil.dateToString(date,format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }


    public Boolean getOptedOut() {
        return optedOut;
    }

    public void setOptedOut(Boolean optedOut) {
        this.optedOut = optedOut;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public Double getRemainLoyaltyPoints() {
        return remainLoyaltyPoints;
    }

    public void setRemainLoyaltyPoints(Double remainLoyaltyPoints) {
        this.remainLoyaltyPoints = remainLoyaltyPoints;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddressExtention() {
        return addressExtention;
    }

    public void setAddressExtention(String addressExtention) {
        this.addressExtention = addressExtention;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
