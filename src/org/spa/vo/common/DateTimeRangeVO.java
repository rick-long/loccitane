package org.spa.vo.common;

import org.joda.time.DateTime;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by Ivy on 2016/8/15.
 */
public class DateTimeRangeVO implements Serializable{

    private DateTime start;

    private DateTime end;

    public DateTimeRangeVO() {}

    public DateTimeRangeVO(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    @Transient
    public String startToString() {
        return start.toString("yyyy-MM-dd HH:mm");
    }

    @Transient
    public String endToString() {
        return end.toString("yyyy-MM-dd HH:mm");
    }
}
