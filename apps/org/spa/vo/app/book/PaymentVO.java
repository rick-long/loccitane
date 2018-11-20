package org.spa.vo.app.book;

public class PaymentVO {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String bookingNumber;
    
    public Long getId() {
 	return id;
 }
    public void setId(Long id) {
 	this.id = id;
 }
    public String getBookingNumber() {
 	   return bookingNumber;
    }
    public void setBookingNumber(String bookingNumber) {
 	   this.bookingNumber = bookingNumber;
    }
}
