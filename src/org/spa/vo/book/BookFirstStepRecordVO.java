package org.spa.vo.book;

import java.io.Serializable;

import org.spa.model.product.ProductOption;

/**
 * @author Ivy 2017-1-17
 */
public class BookFirstStepRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productOptionId;

    private ProductOption productOption;

    private Integer guestAmount;

    private Boolean shareRoom;

    private String startTime;

    private String endTime;

    private int displayOrder;

    private Long bundleId;
    
    public Long getBundleId() {
		return bundleId;
	}
    public void setBundleId(Long bundleId) {
		this.bundleId = bundleId;
	}
    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Integer getGuestAmount() {
        return guestAmount;
    }

    public void setGuestAmount(Integer guestAmount) {
        this.guestAmount = guestAmount;
    }

    public Boolean getShareRoom() {
        return shareRoom;
    }

    public void setShareRoom(Boolean shareRoom) {
        this.shareRoom = shareRoom;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
