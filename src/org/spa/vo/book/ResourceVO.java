package org.spa.vo.book;

import org.spa.model.book.BookItem;
import org.spa.utils.WebThreadLocal;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ivy 2016-3-28
 */
public class ResourceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String viewType;

    private Long resourceId;

    private Long blockId;

    private Integer duration;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    //private Date startAppointmentTime;

    private Long appointmentTimeStamp;

    // 查询resource的开始时间
    private Date startSearchTime;

    // 查询resource的结束时间
    private Date endSearchTime;

    private Long shopId;

    private Long roomId;

    private Long newTherapistId;

    private Long oldTherapistId;

    private Integer guestAmount;

    private Long productId;

    private Long productOptionId;

    private BookItem bookItem;

    private Long bookItemId;

    // 右侧的面板是否隐藏，true是，false否
    private Boolean rightCalendarHide;

    private Date now = new Date();

    private String currentUserName = WebThreadLocal.getUser().getUsername();

    public ResourceVO() {}

    public ResourceVO(BookItem bookItem) {
        this.bookItem = bookItem;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getAppointmentTimeStamp() {
        return appointmentTimeStamp;
    }

    public void setAppointmentTimeStamp(Long appointmentTimeStamp) {
        this.appointmentTimeStamp = appointmentTimeStamp;
    }

    public Date getStartSearchTime() {
        return startSearchTime;
    }

    public void setStartSearchTime(Date startSearchTime) {
        this.startSearchTime = startSearchTime;
    }

    public Date getEndSearchTime() {
        return endSearchTime;
    }

    public void setEndSearchTime(Date endSearchTime) {
        this.endSearchTime = endSearchTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public Integer getGuestAmount() {
        return guestAmount;
    }

    public void setGuestAmount(Integer guestAmount) {
        this.guestAmount = guestAmount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public void setBookItem(BookItem bookItem) {
        this.bookItem = bookItem;
    }

    public Long getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(Long bookItemId) {
        this.bookItemId = bookItemId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getRightCalendarHide() {
        return rightCalendarHide;
    }

    public void setRightCalendarHide(Boolean rightCalendarHide) {
        this.rightCalendarHide = rightCalendarHide;
    }
}
