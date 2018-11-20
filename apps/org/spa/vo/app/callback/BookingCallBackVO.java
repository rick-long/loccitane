package org.spa.vo.app.callback;
import com.spa.constant.CommonConstant;
import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookingCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String shopRef;
	private String shopPrefix;
    private String bookingRef;
    private String bookingCreateDate;
	private String appointmentDate;
    private String status;
    private String category;
    private String shopName;
    private String shopPhone;
    
    private Integer guestNum;
    
	private Double priceTotal;
	private String priceTotalStr;
	
	private Double amountTotal;
	private String amountTotalStr;
	private String currency;
    private List<BookingItemCallBackVO> bookingItemCallBacks;


    public BookingCallBackVO() {
        super();
    }
    
    public BookingCallBackVO(Book book){
    	this.bookingRef = book.getReference();
    	try {
    		this.appointmentDate= DateUtil.dateToString(book.getAppointmentTime(), "yyyy-MM-dd");
			this.bookingCreateDate = DateUtil.dateToString(book.getCreated(), "yyyy-MM-dd HH:mm");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.shopRef=book.getShop().getReference();
    	this.shopName =book.getShop().getName();
    	this.shopPhone = book.getShop().getPhoneNumber();
    	this.currency =I18nUtil.getMessageKey("label.currency");
    	this.bookingItemCallBacks = initBookingItems(book);
    	this.guestNum=book.getGuestAmount();
		this.status=book.getStatus();
		this.category=getParentCategory(book.getBookItems());
		this.shopPrefix=book.getShop().getPrefix();

    }
    
    public List<BookingItemCallBackVO> getBookingItemCallBacks() {
		return bookingItemCallBacks;
	}
    public void setBookingCreateDate(String bookingCreateDate) {
		this.bookingCreateDate = bookingCreateDate;
	}
    public String getBookingCreateDate() {
		return bookingCreateDate;
	}
    public void setBookingItemCallBacks(List<BookingItemCallBackVO> bookingItemCallBacks) {
		this.bookingItemCallBacks = bookingItemCallBacks;
	}
    public String getBookingRef() {
		return bookingRef;
	}
    public void setBookingRef(String bookingRef) {
		this.bookingRef = bookingRef;
	}
    public void setShopName(String shopName) {
		this.shopName = shopName;
	}
    public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}
    public String getShopName() {
		return shopName;
	}
    public String getShopPhone() {
		return shopPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
	}

	public String getPriceTotalStr() {
		return priceTotalStr;
	}
	public void setPriceTotalStr(String priceTotalStr) {
		this.priceTotalStr = priceTotalStr;
	}
	public Double getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}
	public String getAmountTotalStr() {
		return amountTotalStr;
	}
	public void setAmountTotalStr(String amountTotalStr) {
		this.amountTotalStr = amountTotalStr;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getShopPrefix() {
		return shopPrefix;
	}

	public void setShopPrefix(String shopPrefix) {
		this.shopPrefix = shopPrefix;
	}

	private  String getParentCategory(Set<BookItem> getBookItems){
    	String  category="";
		if(getBookItems!=null&&getBookItems.size()>0){
          for(BookItem bookItem:getBookItems){

			  category=  bookItem.getProductOption().getProduct().getCategory().getCategory().getName();
			  break;
		  }
		}
return category;
	}

	private List<BookingItemCallBackVO> initBookingItems(Book book){
		Double priceTotal=0d;
		Double amountTotal =0d;
    	List<BookingItemCallBackVO> list= new ArrayList<BookingItemCallBackVO>();
    	for(BookItem bi : book.getBookItems()){
    		BookingItemCallBackVO itemvo =new BookingItemCallBackVO(bi,book);
			priceTotal += itemvo.getPrice();
			amountTotal += itemvo.getAmount();
    		list.add(itemvo);
    	}
		this.priceTotal=priceTotal;
		this.priceTotalStr= CommonConstant.CURRENCY_TYPE+priceTotal;
		this.amountTotal =amountTotal;
		this.amountTotalStr=CommonConstant.CURRENCY_TYPE+amountTotal;
    	return list ;
    }
}
