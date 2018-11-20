package org.spa.vo.app.book;

import org.spa.model.book.BookBatch;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BookRequestVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Long shopId;
    private Long memberId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;

    private Integer guestNum;
    private List<BookItemRequestVO> bookItems;

    private String bookingChannel;
    
    private String remarks;
    private String createBy;
    
    private Boolean sendBookingConfirmation;
    
    private BookBatch bookBatch;
    
    public BookBatch getBookBatch() {
		return bookBatch;
	}
    public void setBookBatch(BookBatch bookBatch) {
		this.bookBatch = bookBatch;
	}
    
    public Boolean getSendBookingConfirmation() {
		return sendBookingConfirmation;
	}
    public void setSendBookingConfirmation(Boolean sendBookingConfirmation) {
		this.sendBookingConfirmation = sendBookingConfirmation;
	}
    
    public String getCreateBy() {
		return createBy;
	}
    public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
    
    public String getRemarks() {
		return remarks;
	}
    public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    public String getBookingChannel() {
		return bookingChannel;
	}
    public void setBookingChannel(String bookingChannel) {
		this.bookingChannel = bookingChannel;
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
    
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    public List<BookItemRequestVO> getBookItems() {
		return bookItems;
	}
    public void setBookItems(List<BookItemRequestVO> bookItems) {
		this.bookItems = bookItems;
	}
    public Integer getGuestNum() {
		return guestNum;
	}
    public void setGuestNum(Integer guestNum) {
		this.guestNum = guestNum;
	}
   
}
