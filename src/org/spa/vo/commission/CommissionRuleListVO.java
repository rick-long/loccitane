package org.spa.vo.commission;

import org.spa.model.commission.CommissionRule;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class CommissionRuleListVO extends Page<CommissionRule> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long commissionTemplateId;
    private String isActive;
    private Long categoryId;
	private Long productId;
	

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
    public Long getCommissionTemplateId() {
		return commissionTemplateId;
	}
    public void setCommissionTemplateId(Long commissionTemplateId) {
		this.commissionTemplateId = commissionTemplateId;
	}
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

}


