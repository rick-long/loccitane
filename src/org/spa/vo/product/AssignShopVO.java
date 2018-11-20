package org.spa.vo.product;

public class AssignShopVO {
    private Long[] shopIds;
    private Long productId;

    public Long[] getShopIds() {
        return shopIds;
    }

    public void setShopIds(Long[] shopIds) {
        this.shopIds = shopIds;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
