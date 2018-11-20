package org.spa.vo.app.callback;

import java.math.BigDecimal;
import java.text.ParseException;

import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.utils.DateUtil;

import com.spa.constant.CommonConstant;
import com.spa.plugin.discount.DiscountItemAdapter;
import com.spa.plugin.discount.DiscountItemPlugin;

public class BookingItemCallBackVO {

	private String bookingDate;
	private String bookItemStatus;
	private String room;
	private String therapsit;
	private String productName;
	private String duaration;
	
	private String startTime;
	private String endTime;
	
	private Double price;
	private String priceStr;
	private Double amount;
	private String amountStr;
	
	public BookingItemCallBackVO() {
		 super();
	}
	
	public BookingItemCallBackVO(BookItem bookItem,Book book) {
		this.bookItemStatus = bookItem.getStatus();
		this.room = getRoom(bookItem);
		this.therapsit = getTherapist(bookItem);
		this.productName = bookItem.getProductOption().getProduct().getName();
		this.duaration = bookItem.getProductOption().getMins();
		this.price=bookItem.getProductOption().getFinalPrice(book.getShop().getId());
		this.priceStr = CommonConstant.CURRENCY_TYPE+this.price;
		try {
			this.startTime = DateUtil.dateToString(bookItem.getAppointmentTime(), "HH:mm");
			this.endTime = DateUtil.dateToString(bookItem.getAppointmentEndTime(), "HH:mm");;
			this.bookingDate = DateUtil.dateToString(bookItem.getAppointmentTime(),"yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calDiscount(bookItem, book);
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookItemStatus() {
		return bookItemStatus;
	}

	public void setBookItemStatus(String bookItemStatus) {
		this.bookItemStatus = bookItemStatus;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getTherapsit() {
		return therapsit;
	}

	public void setTherapsit(String therapsit) {
		this.therapsit = therapsit;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDuaration() {
		return duaration;
	}

	public void setDuaration(String duaration) {
		this.duaration = duaration;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAmountStr() {
		return amountStr;
	}
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	private String getRoom(BookItem bi){
		if(bi.getRoom() ==null){
			return "No Room";
		}else{
			return bi.getRoom().getName();
		}
	}
	
	private String getTherapist(BookItem bi){
		if(bi.getTherapistList() !=null && bi.getTherapistList().size()>0){
			return bi.getTherapistNames();
		}else{
			return "No Therapist";
		}
	}
	
	private void calDiscount(BookItem bookItem,Book book){
		 // cal public discount
		Double amount=1 * bookItem.getProductOption().getFinalPrice(book.getShop().getId());
    	DiscountItemAdapter discountItemAdapter=new DiscountItemAdapter();
    	discountItemAdapter.setAmount(new BigDecimal(amount));
    	discountItemAdapter.setProductOption( bookItem.getProductOption());
    	DiscountItemPlugin.process(discountItemAdapter,book.getShop().getId(),book.getUser().getId());
    	Double publicDiscount=discountItemAdapter.getDiscountValue().doubleValue();
    	
        
        
        //online discount
        amount=(bookItem.getPrice() -publicDiscount) * 90 /100;
        
        this.amount = amount;
        this.amountStr=CommonConstant.CURRENCY_TYPE+amount;
	}
}
