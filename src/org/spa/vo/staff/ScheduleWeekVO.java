package org.spa.vo.staff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ScheduleWeekVO implements Serializable {

    private Date startDate;

    private Date endDate;

    /**
     * 一周的计划集合
     */
    private List<ScheduleDayVO> scheduleDayVOList = new ArrayList<>();

    private Long staffId;

    private Long shopId;

    private Long weekStartMillis;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<ScheduleDayVO> getScheduleDayVOList() {
        return scheduleDayVOList;
    }

    public void setScheduleDayVOList(List<ScheduleDayVO> scheduleDayVOList) {
        this.scheduleDayVOList = scheduleDayVOList;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getWeekStartMillis() {
        return weekStartMillis;
    }

    public void setWeekStartMillis(Long weekStartMillis) {
        this.weekStartMillis = weekStartMillis;
    }
}