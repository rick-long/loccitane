package org.spa.vo.sales;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.shop.OutSourceAttributeVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/05/03.
 */
public class OrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull
	private Long memberId;
	
	@NotBlank
	private String username;
	
	@NotNull
	private Long shopId;
	
	private Long bookId;
	private String bookRef;
	
	@NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date purchaseDate;
	
	private List<OrderItemVO> itemVOs;
	
	private List<KeyAndValueVO> tipsItemVOs;
	
	@NotNull
	private List<KeyAndValueVO> paymentMethods;
	
	private String remarks;

	private Boolean showRemarksInInvoice;
	
	private OutSourceAttributeVO[] outSourceAttributeVOs;

	private Double subTotal;
	
	// 导航到那个页面
	private String forward;
	
	private String validPrepaidMessage;// valid prepaid paid
	
	private Boolean isHotelGuest;

	private Boolean printInvoice;

	private Boolean checkOutStatus;
	
	public Boolean getIsHotelGuest() {
		return isHotelGuest;
	}
	public void setIsHotelGuest(Boolean isHotelGuest) {
		this.isHotelGuest = isHotelGuest;
	}
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public Boolean getShowRemarksInInvoice() {
		return showRemarksInInvoice;
	}
	public void setShowRemarksInInvoice(Boolean showRemarksInInvoice) {
		this.showRemarksInInvoice = showRemarksInInvoice;
	}
	public String getValidPrepaidMessage() {
		return validPrepaidMessage;
	}
	public void setValidPrepaidMessage(String validPrepaidMessage) {
		this.validPrepaidMessage = validPrepaidMessage;
	}
	
	public List<KeyAndValueVO> getTipsItemVOs() {
		return tipsItemVOs;
	}
	public void setTipsItemVOs(List<KeyAndValueVO> tipsItemVOs) {
		this.tipsItemVOs = tipsItemVOs;
	}
	public OutSourceAttributeVO[] getOutSourceAttributeVOs() {
		return outSourceAttributeVOs;
	}
	public void setOutSourceAttributeVOs(OutSourceAttributeVO[] outSourceAttributeVOs) {
		this.outSourceAttributeVOs = outSourceAttributeVOs;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookRef() {
		return bookRef;
	}
	public void setBookRef(String bookRef) {
		this.bookRef = bookRef;
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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public List<OrderItemVO> getItemVOs() {
		return itemVOs;
	}

	public void setItemVOs(List<OrderItemVO> itemVOs) {
		this.itemVOs = itemVOs;
	}

	public List<KeyAndValueVO> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<KeyAndValueVO> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public Boolean getPrintInvoice() {
		return printInvoice;
	}

	public void setPrintInvoice(Boolean printInvoice) {
		this.printInvoice = printInvoice;
	}

	public Boolean getCheckOutStatus() {
		return checkOutStatus;
	}

	public void setCheckOutStatus(Boolean checkOutStatus) {
		this.checkOutStatus = checkOutStatus;
	}
}
