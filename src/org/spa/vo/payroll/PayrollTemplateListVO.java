package org.spa.vo.payroll;

import java.io.Serializable;

import org.spa.model.payroll.PayrollTemplate;
import org.spa.vo.page.Page;

/**
 * @author Ivy 2016-8-19
 */
public class PayrollTemplateListVO extends Page<PayrollTemplate> implements Serializable {

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


