package org.spa.vo.bonus;

import org.spa.model.bonus.BonusTemplate;
import org.spa.model.company.Company;
import org.spa.vo.common.SelectOptionVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class BonusRuleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Company company;

    @NotNull
    private Long bonusTemplateId;
    private BonusTemplate bonusTemplate;

    private String description;

    private SelectOptionVO[] categoryVOs;

    private SelectOptionVO[] userGroupVOs;

    private BonusAttributeVO[] bonusAttributeVOs;

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

    public void setCategoryVOs(SelectOptionVO[] categoryVOs) {
        this.categoryVOs = categoryVOs;
    }

   public SelectOptionVO[] getUserGroupVOs() {
	   return userGroupVOs;
   }
   public void setUserGroupVOs(SelectOptionVO[] userGroupVOs) {
	   this.userGroupVOs = userGroupVOs;
   }
    public BonusAttributeVO[] getBonusAttributeVOs() {
		return bonusAttributeVOs;
	}
    public void setBonusAttributeVOs(BonusAttributeVO[] bonusAttributeVOs) {
		this.bonusAttributeVOs = bonusAttributeVOs;
	}
    public BonusTemplate getBonusTemplate() {
		return bonusTemplate;
	}
    public void setBonusTemplate(BonusTemplate bonusTemplate) {
		this.bonusTemplate = bonusTemplate;
	}
    public Long getBonusTemplateId() {
		return bonusTemplateId;
	}
    public void setBonusTemplateId(Long bonusTemplateId) {
		this.bonusTemplateId = bonusTemplateId;
	}
}
