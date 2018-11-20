package org.spa.vo.shop;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import java.io.Serializable;

/**
 * @author Ivy 2016-8-19
 */
public class OutSourceTemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Company company;

    @NotBlank
    private String name;

    private String description;

    private OutSourceAttributeKeyVO[] outSourceAttributeKeyVO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
  
    public OutSourceAttributeKeyVO[] getOutSourceAttributeKeyVO() {
		return outSourceAttributeKeyVO;
	}
    public void setOutSourceAttributeKeyVO(OutSourceAttributeKeyVO[] outSourceAttributeKeyVO) {
		this.outSourceAttributeKeyVO = outSourceAttributeKeyVO;
	}
}
