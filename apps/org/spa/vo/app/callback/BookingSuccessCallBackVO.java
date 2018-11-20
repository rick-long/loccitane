package org.spa.vo.app.callback;


import java.io.Serializable;
import java.util.List;

public class BookingSuccessCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	 private String shopPrefix;
	private String shopName;
	    private String shopPhone;
	    private Integer guestNum;
	    private String shopRef;
	    private String bookingNumber;
	    private String totalPrice;
	    private String date;
	    
	    private  List<BookingItemSucessCallBackVO> bookingItems;
	    
	    public BookingSuccessCallBackVO() {
	        super();
	    }
	    public String getShopName() {
			return shopName;
		}
	    public void setShopName(String shopName) {
			this.shopName = shopName;
		}
	    public String getShopPhone() {
			return shopPhone;
		}
	    public void setShopPhone(String shopPhone) {
			this.shopPhone = shopPhone;
		}
	    public List<BookingItemSucessCallBackVO> getBookingItems() {
			return bookingItems;
		}
	    public void setBookingItems(List<BookingItemSucessCallBackVO> bookingItems) {
			this.bookingItems = bookingItems;
		}
	    public Integer getGuestNum() {
			return guestNum;
		}
	    public void setGuestNum(Integer guestNum) {
			this.guestNum = guestNum;
		}
	    public String getShopRef() {
			return shopRef;
		}
	    public void setShopRef(String shopRef) {
			this.shopRef = shopRef;
		}
		public String getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(String totalPrice) {
			this.totalPrice = totalPrice;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getBookingNumber() {
			return bookingNumber;
		}
	    public void setBookingNumber(String bookingNumber) {
			this.bookingNumber = bookingNumber;
		}

	public String getShopPrefix() {
		return shopPrefix;
	}

	public void setShopPrefix(String shopPrefix) {
		this.shopPrefix = shopPrefix;
	}
}
