package org.spa.vo.book;

import org.spa.model.user.User;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/10/31.
 */
public class RequestTherapistVO implements Serializable {

    private Long therapistId;

    private User therapist;

    private Boolean onRequest;

    // 标记那个bookItem发起请求技师的标记
    private Boolean currentRequest;

    /**
     * 是否被block, true is block, false is not.
     */
    private Boolean available;

    public Long getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(Long therapistId) {
        this.therapistId = therapistId;
    }

    public Boolean getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(Boolean onRequest) {
        this.onRequest = onRequest;
    }

    public User getTherapist() {
        return therapist;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }

    public Boolean getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(Boolean currentRequest) {
        this.currentRequest = currentRequest;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
