package org.spa.vo.shop;

import java.io.Serializable;

import org.spa.model.shop.Room;
import org.spa.vo.page.Page;

/**
 * @author Ivy 2016-3-28
 */
public class RoomListVO extends Page<Room> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String isActive;
    private Long shopId;
    
    public Long getShopId() {
		return shopId;
	}
    public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
