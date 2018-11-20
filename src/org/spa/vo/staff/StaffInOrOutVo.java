package org.spa.vo.staff;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * created by rick 2018.8.29
 */
public class StaffInOrOutVo {

    /**pin*/
    private String pin;
    /**barcode*/
    private String staffNumber;
    /**店铺id*/
    private String shop;
    /**经度*/
    private String lng;
    /**纬度*/
    private String lat;
    /**
     * 浏览器定位到的当前位置 
     * */
    private String currentAddress;

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
