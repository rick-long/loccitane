package org.spa.vo.shop;
import java.io.Serializable;
import org.spa.model.shop.Shop;
import org.spa.vo.page.Page;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ShopListVO extends Page<Shop> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String isActive;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
