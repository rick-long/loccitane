package org.spa.vo.front.book;

import org.spa.model.product.ProductOption;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.vo.book.RequestTherapistVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Ivy 2016-6-16
 */
public class FrontBookItemVO implements Serializable {

    private Long id;

    private Long parentId;

    private Long shopId;

    private Shop shop;

    private Integer guestAmount;

    private Boolean shareRoom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;

    private Long timestamp; // 具体时间截

    private Date appointmentTime;

    private Date endAppointmentTime;

    private Integer duration;

    private Long productOptionId;

    private ProductOption productOption;

    private String productName;

    /**
     * 记录选择的技师的所有信息
     */
    private List<RequestTherapistVO> therapistVOS;

    private List<String> therapistInfo;

    //private List<User> therapistList;

    /**
     * 当guest amount > 1时为true,表示需要在bookItem选择技师。
     */
    private Boolean requireSelectTherapist;

    private List<User> availableTherapists;

    // 不可用的技师集合
    private List<User> notAvailableTherapists;

    private Long roomId;

    private Room room;

    private Boolean onRequest;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getGuestAmount() {
        return guestAmount;
    }

    public void setGuestAmount(Integer guestAmount) {
        this.guestAmount = guestAmount;
    }

    public Boolean getShareRoom() {
        return shareRoom;
    }

    public void setShareRoom(Boolean shareRoom) {
        this.shareRoom = shareRoom;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public List<String> getTherapistInfo() {
        return therapistInfo;
    }

    public void setTherapistInfo(List<String> therapistInfo) {
        this.therapistInfo = therapistInfo;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(Boolean onRequest) {
        this.onRequest = onRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Date getEndAppointmentTime() {
        return endAppointmentTime;
    }

    public void setEndAppointmentTime(Date endAppointmentTime) {
        this.endAppointmentTime = endAppointmentTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

   /* public List<User> getTherapistList() {
        return therapistList;
    }

    public void setTherapistList(List<User> therapistList) {
        this.therapistList = therapistList;
    }*/

    public List<User> getAvailableTherapists() {
        return availableTherapists;
    }

    public void setAvailableTherapists(List<User> availableTherapists) {
        this.availableTherapists = availableTherapists;
    }

    public List<User> getNotAvailableTherapists() {
        return notAvailableTherapists;
    }

    public void setNotAvailableTherapists(List<User> notAvailableTherapists) {
        this.notAvailableTherapists = notAvailableTherapists;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getRequireSelectTherapist() {
        return requireSelectTherapist;
    }

    public void setRequireSelectTherapist(Boolean requireSelectTherapist) {
        this.requireSelectTherapist = requireSelectTherapist;
    }

    public List<RequestTherapistVO> getTherapistVOS() {
        return therapistVOS;
    }

    public void setTherapistVOS(List<RequestTherapistVO> therapistVOS) {
        this.therapistVOS = therapistVOS;
    }
}
