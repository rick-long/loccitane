package org.spa.vo.book;

import org.joda.time.DateTime;
import org.spa.model.book.BookItem;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 显示在book calendar 参数对象
 *
 * @author Ivy 2016-11-2
 */
public class ViewVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String viewType;

    private Long appointmentTimeStamp;

    private Long shopId;

    private Shop shop;

    private Long roomId;

    private DateTime startTime;

    private DateTime endTime;

    private Long newTherapistId;

    private Long oldTherapistId;

    private Long bookItemId;

    private Boolean rightCalendarHide;

    private DateTime todayTime;

    public Long getAppointmentTimeStamp() {
        return appointmentTimeStamp;
    }

    public void setAppointmentTimeStamp(Long appointmentTimeStamp) {
        this.appointmentTimeStamp = appointmentTimeStamp;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getNewTherapistId() {
        return newTherapistId;
    }

    public void setNewTherapistId(Long newTherapistId) {
        this.newTherapistId = newTherapistId;
    }

    public Long getOldTherapistId() {
        return oldTherapistId;
    }

    public void setOldTherapistId(Long oldTherapistId) {
        this.oldTherapistId = oldTherapistId;
    }

    public Long getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(Long bookItemId) {
        this.bookItemId = bookItemId;
    }

    public Boolean getRightCalendarHide() {
        return rightCalendarHide;
    }

    public void setRightCalendarHide(Boolean rightCalendarHide) {
        this.rightCalendarHide = rightCalendarHide;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public DateTime getTodayTime() {
        return DateTime.now();
    }
}
