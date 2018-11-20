package org.spa.vo.book;

import java.io.Serializable;
import java.util.Date;

import org.spa.model.book.Book;
import org.spa.vo.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Ivy 2016-3-28
 */
public class BookListVO extends Page<Book> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String isActive;
    private String status;

    private Long memberId;
    private String username;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fromDate;
    private String toDate;

    private Long shopId;

    private Long newAppointmentTime;

    private Integer duration;

    private Long resourceId;

    private Long newTherapistId;

    private Long oldTherapistId;

    private Long roomId;

    private Integer guestAmount;

    private Long productId;

    private Long productOptionId;

    private String therapistName;

    private String treatmentName;

    private String bookingChannel;
    
    public String getBookingChannel() {
		return bookingChannel;
	}
	public void setBookingChannel(String bookingChannel) {
		this.bookingChannel = bookingChannel;
	}
	
    public String getTherapistName() {
        return therapistName;
    }

    public void setTherapistName(String therapistName) {
        this.therapistName = therapistName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
    public String getFromDate() {
		return fromDate;
	}
    public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
    public String getToDate() {
		return toDate;
	}
    public void setToDate(String toDate) {
		this.toDate = toDate;
	}

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getNewAppointmentTime() {
        return newAppointmentTime;
    }

    public void setNewAppointmentTime(Long newAppointmentTime) {
        this.newAppointmentTime = newAppointmentTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


