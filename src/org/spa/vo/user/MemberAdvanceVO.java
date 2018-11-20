package org.spa.vo.user;

import java.io.Serializable;
import java.util.Date;

import org.spa.model.user.User;
import org.spa.vo.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Ivy on 2017-2-28
 */
public class MemberAdvanceVO extends Page<User> implements Serializable {

    public static final String SEARCH_TYPE_VISIT = "VISIT";
    public static final String SEARCH_TYPE_NOT_VISIT = "NOT_VISIT";
    public static final String SEARCH_TYPE_SPENT = "SPENT";
    
    private String searchType;

    /**
     * 过去多少月份
     */
    private Integer pastMonth;

    /**
     * 过去 pastMonth 开始算的时间
     */
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date notVisitStartDate;

    private Long shopId; // the client in this shop visit this shop or other shops;
    
    private String startDateSpent;
    private String endDateSpent;
    private Long categoryId;
    private Long productId;
    private Long productOptionId;
    private Double miniSpentAmount;
    
    public Double getMiniSpentAmount() {
		return miniSpentAmount;
	}
    
    public void setMiniSpentAmount(Double miniSpentAmount) {
		this.miniSpentAmount = miniSpentAmount;
	}
    
    public String getStartDateSpent() {
		return startDateSpent;
	}
	public void setStartDateSpent(String startDateSpent) {
		this.startDateSpent = startDateSpent;
	}
	public String getEndDateSpent() {
		return endDateSpent;
	}
	public void setEndDateSpent(String endDateSpent) {
		this.endDateSpent = endDateSpent;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	public Long getShopId() {
		return shopId;
	}
    public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
    
    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public Integer getPastMonth() {
        return pastMonth;
    }

    public void setPastMonth(Integer pastMonth) {
        this.pastMonth = pastMonth;
    }

    public Date getNotVisitStartDate() {
        return notVisitStartDate;
    }

    public void setNotVisitStartDate(Date notVisitStartDate) {
        this.notVisitStartDate = notVisitStartDate;
    }
}
