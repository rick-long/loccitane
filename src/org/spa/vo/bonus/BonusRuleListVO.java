package org.spa.vo.bonus;
import org.spa.model.bonus.BonusRule;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class BonusRuleListVO extends Page<BonusRule> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bonusTemplateId;
    private String isActive;

    public Long getBonusTemplateId() {
		return bonusTemplateId;
	}
    public void setBonusTemplateId(Long bonusTemplateId) {
		this.bonusTemplateId = bonusTemplateId;
	}
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

}


