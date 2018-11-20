package org.spa.vo.commission;

import org.spa.model.commission.CommissionTemplate;
import org.spa.model.company.Company;
import org.spa.vo.common.SelectOptionVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class CommissionRuleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Company company;

    @NotNull
    private Long commissionTemplateId;
    private CommissionTemplate commissionTemplate;

    private String description;

    private SelectOptionVO[] categoryVOs;

    private SelectOptionVO[] userGroupVOs;

    private SelectOptionVO[] productVOs;

    private CommissionAttributeVO[] commissionAttributeVOs;

    private String calTarget;

    private Boolean isActive;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCalTarget() {
        return calTarget;
    }

    public void setCalTarget(String calTarget) {
        this.calTarget = calTarget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SelectOptionVO[] getCategoryVOs() {
        return categoryVOs;
    }

    public SelectOptionVO[] getProductVOs() {
        return productVOs;
    }

    public void setProductVOs(SelectOptionVO[] productVOs) {
        this.productVOs = productVOs;
    }

    public void setCategoryVOs(SelectOptionVO[] categoryVOs) {
        this.categoryVOs = categoryVOs;
    }

    public SelectOptionVO[] getUserGroupVOs() {
        return userGroupVOs;
    }

    public void setUserGroupVOs(SelectOptionVO[] userGroupVOs) {
        this.userGroupVOs = userGroupVOs;
    }

    public CommissionAttributeVO[] getCommissionAttributeVOs() {
        return commissionAttributeVOs;
    }

    public void setCommissionAttributeVOs(CommissionAttributeVO[] commissionAttributeVOs) {
        this.commissionAttributeVOs = commissionAttributeVOs;
    }

    public CommissionTemplate getCommissionTemplate() {
        return commissionTemplate;
    }

    public void setCommissionTemplate(CommissionTemplate commissionTemplate) {
        this.commissionTemplate = commissionTemplate;
    }

    public Long getCommissionTemplateId() {
        return commissionTemplateId;
    }

    public void setCommissionTemplateId(Long commissionTemplateId) {
        this.commissionTemplateId = commissionTemplateId;
    }
}
