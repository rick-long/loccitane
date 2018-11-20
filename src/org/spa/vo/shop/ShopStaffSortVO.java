package org.spa.vo.shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ShopStaffSortVO implements Serializable {
	private  Long shopId;
	List<StaffSortVO> staffSortVOList;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public List<StaffSortVO> getStaffSortVOList() {
		return staffSortVOList;
	}

	public void setStaffSortVOList(List<StaffSortVO> staffSortVOList) {
		this.staffSortVOList = staffSortVOList;
	}
}
