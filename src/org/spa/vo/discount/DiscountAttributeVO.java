package org.spa.vo.discount;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Ivy 2016-5-13
 */
public class DiscountAttributeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long discountAttributeKeyId;

    private String reference;

    private String name;

    private String description;

    private String value;

    public Long getDiscountAttributeKeyId() {
        return discountAttributeKeyId;
    }

    public void setDiscountAttributeKeyId(Long discountAttributeKeyId) {
        this.discountAttributeKeyId = discountAttributeKeyId;
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
