package org.spa.vo.book;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.spa.model.shop.Room;
import org.spa.model.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import com.spa.constant.CommonConstant;

/**
 * @author Ivy 2016-7-6
 */
public class BlockVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long shopId;

    private Long therapistId;

    private User therapist;

    private Long roomId;

    private Room room;

    private Long startTimeStamp;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDateTime;

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

    private String type;

    private String repeatType;

    private String remarks;

    private String updateType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public Long getTherapistId() {
        return therapistId;
    }

    public User getTherapist() {
        return therapist;
    }

    public String getType() {
        return type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public void setTherapistId(Long therapistId) {
        this.therapistId = therapistId;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
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

    public String validate() {
        String res = "";
        if (CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(getRepeatType())) {
            if (getStartDateTime() == null || getEndDateTime() == null) {
                res = "Start Date Time or End Date Time can not be null!";
            } else if (!getEndDateTime().after(getStartDateTime())) {
                res = "End date time must after start date time!";
            }
        } else {
            if (getRepeatStartDate() == null || getRepeatEndDate() == null) {
                res = "Repeat Start Date or Repeat End Date can not be null!";
            } else if (getRepeatEndDate().before(getRepeatStartDate())) {
                res = "Repeat End Date must after Repeat Start Date!";
            }

            if (StringUtils.isBlank(getRepeatStartTime()) || StringUtils.isBlank(getRepeatEndTime())) {
                res = "Repeat Start Time or Repeat End Time can not be null!";
            } else {
                String[] startTimes = getRepeatStartTime().split(":");
                String[] endTimes = getRepeatEndTime().split(":");
                int startHour = Integer.parseInt(startTimes[0]);
                int endHour = Integer.parseInt(endTimes[0]);
                if (startHour > endHour || ((startHour == endHour) && Integer.parseInt(startTimes[1]) >= Integer.parseInt(endTimes[1]))) {
                    res = "Repeat End Time must after Repeat Start Time!";
                }
            }
        }
        return res;
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
