package org.spa.vo.common;

import java.io.Serializable;

/**
 * Created by wz832 on 2018/6/14.
 */
public class SearchBarCodeReq implements Serializable {

    private String barCode;//条形码

    private Long productOptionId;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }
}
