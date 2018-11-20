package org.spa.vo.shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ShopRoomSortVO implements Serializable {
	private  Long shopId;
	List<RoomSortVO> roomSortVOList;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public List<RoomSortVO> getRoomSortVOList() {
		return roomSortVOList;
	}

	public void setRoomSortVOList(List<RoomSortVO> roomSortVOList) {
		this.roomSortVOList = roomSortVOList;
	}
}
