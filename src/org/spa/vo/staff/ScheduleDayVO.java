package org.spa.vo.staff;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ScheduleDayVO implements Serializable {

    /**
     * 星期几的名字
     */
    private String name;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date date;

    /**
     * 类型，workDay or Day Off
     */
    private String type;

    /**
     * 一天的事件集合
     */
    private Map<String, ScheduleTimeVO> scheduleTimeVOMap = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, ScheduleTimeVO> getScheduleTimeVOMap() {
        return scheduleTimeVOMap;
    }

    public void setScheduleTimeVOMap(Map<String, ScheduleTimeVO> scheduleTimeVOMap) {
        this.scheduleTimeVOMap = scheduleTimeVOMap;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}