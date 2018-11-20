package org.spa.vo.supplier;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by Ivy on 2016/01/16.
 */
public class SupplierVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @NotBlank
    private String name;

    private Long id;

    private String contactPerson;

    private String contactEmail;

    private String contactTel;

    private Boolean isActive;

    private Long[] productIds;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Long[] productIds) {
        this.productIds = productIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
}
