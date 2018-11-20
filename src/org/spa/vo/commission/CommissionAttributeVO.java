package org.spa.vo.commission;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class CommissionAttributeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long commissionAttributeKeyId;

    private String reference;

    private String name;

    private String description;

    private String value;

   public Long getCommissionAttributeKeyId() {
	   return commissionAttributeKeyId;
   }
   
   public void setCommissionAttributeKeyId(Long commissionAttributeKeyId) {
	   this.commissionAttributeKeyId = commissionAttributeKeyId;
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
