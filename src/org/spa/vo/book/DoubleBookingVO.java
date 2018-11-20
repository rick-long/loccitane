package org.spa.vo.book;

public class DoubleBookingVO {
    private Long[] bookItemIds;
    private String status;
    
    public Long[] getBookItemIds() {
		return bookItemIds;
	}
    public void setBookItemIds(Long[] bookItemIds) {
		this.bookItemIds = bookItemIds;
	}
    public String getStatus() {
		return status;
	}
    public void setStatus(String status) {
		this.status = status;
	}

    
}
