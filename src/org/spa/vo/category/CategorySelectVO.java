package org.spa.vo.category;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-7-14
 */
public class CategorySelectVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 初始化菜单的根目录
    private Long rootId;
    private Long rootId1;
    private Long categoryId;
    private Long productId;
    private Long productOptionId;
    private String displayName;
    private String selectable;
    private String level; // 级别
    private Integer deep; // 菜单深度
    private Boolean isOnline;
    private String duration;
    private String processTime;
    
    private Long shopId;
    
    public String getDuration() {
		return duration;
	}
    public void setDuration(String duration) {
		this.duration = duration;
	}
    public String getProcessTime() {
		return processTime;
	}
    public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}
    public Long getShopId() {
		return shopId;
	}
    public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
    public Boolean getIsOnline() {
		return isOnline;
	}
    public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
    public Long getRootId1() {
		return rootId1;
	}
    public void setRootId1(Long rootId1) {
		this.rootId1 = rootId1;
	}
    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getSelectable() {
        return selectable;
    }

    public void setSelectable(String selectable) {
        this.selectable = selectable;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
