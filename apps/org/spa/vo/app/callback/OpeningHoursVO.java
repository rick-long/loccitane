package org.spa.vo.app.callback;

import java.io.Serializable;

public class OpeningHoursVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private Long shopId;
    private String datetime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
