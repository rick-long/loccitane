package org.spa.vo.commission;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class CommissionAttributeKeyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String reference;

    @NotBlank
    private String name;

    private String description;

    private Integer displayOrder;

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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
