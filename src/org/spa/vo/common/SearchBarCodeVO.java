package org.spa.vo.common;

import java.io.Serializable;

/**
 * Created by wz832 on 2018/6/15.
 */
public class SearchBarCodeVO implements Serializable{

    private String barCode;

    private Long productOptionId;

    private String displayName;

    private boolean flag = true;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
