package org.spa.vo.shop;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/4/28.
 */
public class ShopVO implements Serializable {

    private Long id;
    private String name;
    private String prefix;
    private String email;
    private String businessPhone;
    private String address;
    private String openingHours;
    private String isOnlineShop;
    private String isBookingShop;
    private String remarks;
    private String isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getIsOnlineShop() {
        return isOnlineShop;
    }

    public void setIsOnlineShop(String isOnlineShop) {
        this.isOnlineShop = isOnlineShop;
    }

    public String getIsBookingShop() {
        return isBookingShop;
    }

    public void setIsBookingShop(String isBookingShop) {
        this.isBookingShop = isBookingShop;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
