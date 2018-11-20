package org.spa.vo.bundle;

import java.io.Serializable;

/**
 * @author Ivy 2018-7-30
 */
public class BundleItemVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long[] productOptionIds;
    private Integer group;
    
    public Integer getGroup() {
		return group;
	}
    public void setGroup(Integer group) {
		this.group = group;
	}
    public Long[] getProductOptionIds() {
		return productOptionIds;
	}
    public void setProductOptionIds(Long[] productOptionIds) {
		this.productOptionIds = productOptionIds;
	}
}
