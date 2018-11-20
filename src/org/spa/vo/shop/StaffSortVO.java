package org.spa.vo.shop;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class StaffSortVO  implements Serializable {
	private Long id;
	private  Integer sort;
	private  Long staffId;
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
