package org.spa.vo.staff;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ScheduleTimeVO implements Serializable {

    private String name;

    private String startTime;

    private String endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}