package org.spa.vo.app.callback;

import org.spa.model.shop.Shop;

import java.io.Serializable;

public class VoucherValueCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private String name;
    private double price;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
