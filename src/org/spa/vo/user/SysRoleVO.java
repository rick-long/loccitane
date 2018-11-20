package org.spa.vo.user;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-5-25
 */
public class SysRoleVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

	@NotBlank
	private String name;

    @NotBlank
    private String reference;

    private Boolean isActive;

    private Long[] sysResourceIds;

    private Company company;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long[] getSysResourceIds() {
        return sysResourceIds;
    }

    public void setSysResourceIds(Long[] sysResourceIds) {
        this.sysResourceIds = sysResourceIds;
    }
}
