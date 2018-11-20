package org.spa.vo.user;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-5-25
 */
public class UserGroupVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

	@NotBlank
	private String name;

    private String remarks;

    private String type;
    
    private String module;

    private Boolean isActive;
    
    private Long[] userIds;

//    private boolean isActive = false;

    private Company company;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public String getModule() {
		return module;
	}
    public void setModule(String module) {
		this.module = module;
	}
    public String getType() {
		return type;
	}
    public void setType(String type) {
		this.type = type;
	}
//    public boolean getIsActive() {
//        return isActive;
//    }
//
//    public void setIsActive(boolean isActive) {
//        this.isActive = isActive;
//    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
}
