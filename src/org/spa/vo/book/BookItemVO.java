package org.spa.vo.book;

import org.spa.model.book.BookItem;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Room;
import org.spa.model.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BookItemVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    // 临时id
    private Long tempId;

    // 临时parent id
    private Long tempParentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date appointmentTime;

    // 根据productOption的duration算出来
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endAppointmentTime;

    private Integer duration;

    private Long productId;

    private Long productOptionId;

    private ProductOption productOption;

    /**
     * 使用requestTherapistVOs 代替
     */
    //@Deprecated
    //private Long therapistId;

    /**
     * 使用requestTherapistVOs 代替
     */
    //@Deprecated
    //private User therapist;

    private List<User> availableTherapists;

    // 不可用的技师集合
    private List<User> notAvailableTherapists;

    private Long roomId;

    private Room room;

    private String status;

    private BookVO bookVO;

    private Boolean onRequest;

    private Integer guestAmount;

    private Boolean shareRoom;

    private String startTime;
    private String endTime;

    private Boolean isDoubleBooking;
    private Long doubleBookingParentId;
    
    private Long bundleId;
    private Long bundleProductOptionId1;
    private Long bundleProductOptionId2;

    private String appointmentTimeFormat;
    private String appointmentEndTimeFormat;
    private String productName;
    private String therapists;
    
    private Long bookItemIdex;
   
    public Long getBookItemIdex() {
		return bookItemIdex;
	}

	public void setBookItemIdex(Long bookItemIdex) {
		this.bookItemIdex = bookItemIdex;
	}

	/**
     * 请求的技师集合
     */
    private List<RequestTherapistVO> requestTherapistVOs = new ArrayList<>();

    public void setBookVoByBook(BookItem bi) {
        this.appointmentTimeFormat = bi.getAppointmentTimeFormat();
        this.appointmentEndTimeFormat = bi.getAppointmentEndTimeFormat();
        this.productName = bi.getProductName();
        this.duration = bi.getDuration();
        this.therapists = bi.getTherapists();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndTime() {
		return endTime;
	}
    public void setEndTime(String endTime) {
		this.endTime = endTime;
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

   /* public Long getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(Long therapistId) {
        this.therapistId = therapistId;
    }*/

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

   /* public User getTherapist() {
        return therapist;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }*/

    public BookVO getBookVO() {
        return bookVO;
    }

    public void setBookVO(BookVO bookVO) {
        this.bookVO = bookVO;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(Boolean onRequest) {
        this.onRequest = onRequest;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "BookItemVO{" +
                "appointmentTime=" + appointmentTime +
                ", endAppointmentTime=" + endAppointmentTime +
                ", duration=" + duration +
                ", productId=" + productId +
                ", productOptionId=" + productOptionId +
                ", roomId=" + roomId +
                '}';
    }

    public List<RequestTherapistVO> getRequestTherapistVOs() {
        return requestTherapistVOs;
    }

    public void setRequestTherapistVOs(List<RequestTherapistVO> requestTherapistVOs) {
        this.requestTherapistVOs = requestTherapistVOs;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    public Long getTempParentId() {
        return tempParentId;
    }

    public void setTempParentId(Long tempParentId) {
        this.tempParentId = tempParentId;
    }
    
    public Boolean getIsDoubleBooking() {
		return isDoubleBooking;
	}
    public void setIsDoubleBooking(Boolean isDoubleBooking) {
		this.isDoubleBooking = isDoubleBooking;
	}
    public Long getDoubleBookingParentId() {
		return doubleBookingParentId;
	}
    public void setDoubleBookingParentId(Long doubleBookingParentId) {
		this.doubleBookingParentId = doubleBookingParentId;
	}
    public Long getBundleId() {
		return bundleId;
	}
    public void setBundleId(Long bundleId) {
		this.bundleId = bundleId;
	}

    public Long getBundleProductOptionId1() {
        return bundleProductOptionId1;
    }

    public void setBundleProductOptionId1(Long bundleProductOptionId1) {
        this.bundleProductOptionId1 = bundleProductOptionId1;
    }

    public Long getBundleProductOptionId2() {
        return bundleProductOptionId2;
    }

    public void setBundleProductOptionId2(Long bundleProductOptionId2) {
        this.bundleProductOptionId2 = bundleProductOptionId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookItemVO that = (BookItemVO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tempId, that.tempId) &&
                Objects.equals(tempParentId, that.tempParentId) &&
                Objects.equals(appointmentTime, that.appointmentTime) &&
                Objects.equals(endAppointmentTime, that.endAppointmentTime) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(productOptionId, that.productOptionId) &&
                Objects.equals(productOption, that.productOption) &&
                Objects.equals(availableTherapists, that.availableTherapists) &&
                Objects.equals(notAvailableTherapists, that.notAvailableTherapists) &&
                Objects.equals(roomId, that.roomId) &&
                Objects.equals(room, that.room) &&
                Objects.equals(status, that.status) &&
                Objects.equals(bookVO, that.bookVO) &&
                Objects.equals(onRequest, that.onRequest) &&
                Objects.equals(guestAmount, that.guestAmount) &&
                Objects.equals(shareRoom, that.shareRoom) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(isDoubleBooking, that.isDoubleBooking) &&
                Objects.equals(doubleBookingParentId, that.doubleBookingParentId) &&
                Objects.equals(bundleId, that.bundleId) &&
                Objects.equals(bundleProductOptionId1, that.bundleProductOptionId1) &&
                Objects.equals(bundleProductOptionId2, that.bundleProductOptionId2) &&
                Objects.equals(requestTherapistVOs, that.requestTherapistVOs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tempId, tempParentId, appointmentTime, endAppointmentTime, duration, productId, productOptionId, productOption, availableTherapists, notAvailableTherapists, roomId, room, status, bookVO, onRequest, guestAmount, shareRoom, startTime, endTime, isDoubleBooking, doubleBookingParentId, bundleId, bundleProductOptionId1, bundleProductOptionId2, requestTherapistVOs);
    }

    public String getAppointmentTimeFormat() {
        return appointmentTimeFormat;
    }

    public void setAppointmentTimeFormat(String appointmentTimeFormat) {
        this.appointmentTimeFormat = appointmentTimeFormat;
    }

    public String getAppointmentEndTimeFormat() {
        return appointmentEndTimeFormat;
    }

    public void setAppointmentEndTimeFormat(String appointmentEndTimeFormat) {
        this.appointmentEndTimeFormat = appointmentEndTimeFormat;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTherapists() {
        return therapists;
    }

    public void setTherapists(String therapists) {
        this.therapists = therapists;
    }


}
