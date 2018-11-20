package org.spa.vo.book;

import java.io.Serializable;
import org.spa.model.book.BookBatch;
import org.spa.vo.page.Page;

/**
 * @author Ivy 2018-9-13
 */
public class BookBatchListVO extends Page<BookBatch> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String batchNumber;
    
    private Long memberId;
    private String username;

    private Long shopId;
    
    private String fromDate;
    private String toDate;
    
    public String getBatchNumber() {
		return batchNumber;
	}
    public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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
    
    
}


