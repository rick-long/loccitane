package org.spa.vo.category;

import java.io.Serializable;

/**
 * Created by Ivy on 2018-4-10
 */
public class CategoryVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private String reference;
	private Long id;
    private Long startTime;
	private String name;
    private Long shopId;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
