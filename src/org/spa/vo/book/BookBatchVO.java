package org.spa.vo.book;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.spa.model.user.User;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Ivy 2018-9-6
 */
public class BookBatchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long memberId;
    
    private Long shopId;

    private String therapistId;

    private User therapist;

    private Long productOptionId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date repeatStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date repeatEndDate;

    private String repeatStartTime;

    private String repeatEndTime;

    private List<Integer> days;

    private List<Integer> weeks;

    private List<Integer> months;

    private String cronExpression;

    private String repeatType;

    private String remarks;

    private String updateType;
    
    
    public Long getMemberId() {
		return memberId;
	}
    public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
    public Long getProductOptionId() {
		return productOptionId;
	}
    public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public String getTherapistId() {
        return therapistId;
    }

    public User getTherapist() {
        return therapist;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Date getRepeatStartDate() {
        return repeatStartDate;
    }

    public void setRepeatStartDate(Date repeatStartDate) {
        this.repeatStartDate = repeatStartDate;
    }

    public Date getRepeatEndDate() {
        return repeatEndDate;
    }

    public void setRepeatEndDate(Date repeatEndDate) {
        this.repeatEndDate = repeatEndDate;
    }

    public String getRepeatStartTime() {
        return repeatStartTime;
    }

    public void setRepeatStartTime(String repeatStartTime) {
        this.repeatStartTime = repeatStartTime;
    }

    public String getRepeatEndTime() {
        return repeatEndTime;
    }

    public void setRepeatEndTime(String repeatEndTime) {
        this.repeatEndTime = repeatEndTime;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public void setMonths(List<Integer> months) {
        this.months = months;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
