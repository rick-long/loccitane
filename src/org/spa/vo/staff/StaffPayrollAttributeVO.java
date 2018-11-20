package org.spa.vo.staff;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Ivy 2016-8-19
 */
public class StaffPayrollAttributeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long payrollAttributeKeyId;

    private String reference;

    private String name;

    private String description;

    private String value;

    public Long getPayrollAttributeKeyId() {
    	return payrollAttributeKeyId;
	}
	public void setPayrollAttributeKeyId(Long payrollAttributeKeyId) {
		this.payrollAttributeKeyId = payrollAttributeKeyId;
	}

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
