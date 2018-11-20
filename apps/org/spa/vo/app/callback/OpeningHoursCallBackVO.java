package org.spa.vo.app.callback;

import org.spa.model.shop.OpeningHours;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class OpeningHoursCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private String closeTime;
    private String openTime;
    Map<String, String> morningMap;
    Map<String, String> afternoonMap;
    Map<String, String> eveningMap;

    public OpeningHoursCallBackVO() {
        super();
    }
    public  OpeningHoursCallBackVO(OpeningHours openingHours){
        this.closeTime=openingHours.getCloseTime();
        this.openTime=openingHours.getOpenTime();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Map<String, String> getMorningMap() {
        return morningMap;
    }

    public void setMorningMap(Map<String, String> morningMap) {
        this.morningMap = morningMap;
    }

    public Map<String, String> getAfternoonMap() {
        return afternoonMap;
    }

    public void setAfternoonMap(Map<String, String> afternoonMap) {
        this.afternoonMap = afternoonMap;
    }

    public Map<String, String> getEveningMap() {
        return eveningMap;
    }

    public void setEveningMap(Map<String, String> eveningMap) {
        this.eveningMap = eveningMap;
    }
}
