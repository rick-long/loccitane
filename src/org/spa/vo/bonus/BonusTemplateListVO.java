package org.spa.vo.bonus;

import java.io.Serializable;

import org.spa.model.bonus.BonusTemplate;
import org.spa.vo.page.Page;

/**
 * @author Ivy 2016-7-25
 */
public class BonusTemplateListVO extends Page<BonusTemplate> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String isActive;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


