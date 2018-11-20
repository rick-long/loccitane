package org.spa.vo.book;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.spa.model.user.User;

/**
 * 显示在book calendar 参数对象
 *
 * @author Ivy 2016-11-2
 */
public class TherapistKeyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DateTime time;

    private User therapist;

    public TherapistKeyVO(DateTime time, User therapist) {
        this.time = time;
        this.therapist = therapist;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public User getTherapist() {
        return therapist;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((therapist == null) ? 0 : therapist.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TherapistKeyVO))
            return false;
        TherapistKeyVO other = (TherapistKeyVO) obj;
        if (therapist == null) {
            if (other.therapist != null)
                return false;
        } else if (!therapist.getId().equals(other.therapist.getId()))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.isEqual(other.time))
            return false;
        return true;
    }

}
