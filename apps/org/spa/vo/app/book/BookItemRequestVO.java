package org.spa.vo.app.book;

import org.spa.model.product.ProductOption;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Ivy 2018-3-2
 */
public class BookItemRequestVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long memberId;
    private Long shopId;

    private Shop shop;

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
     * 当guest amount > 1时为true,表示需要在bookItem选择技师。
     */
    private Boolean requireSelectTherapist;

    private List<User> availableTherapists;

    // 不可用的技师集合
    private List<User> notAvailableTherapists;

    private Long roomId;
    private String therapistIds;

    private Room room;

    private Boolean onRequest;

    private String status;

    public String getTherapistIds() {
        return therapistIds;
    }

    public void setTherapistIds(String therapistIds) {
        this.therapistIds = therapistIds;
    }

    public Long getMemberId() {
		return memberId;
	}
    public void setMemberId(Long memberId) {
		this.memberId = memberId;
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
}
