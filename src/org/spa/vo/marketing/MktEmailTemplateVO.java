package org.spa.vo.marketing;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-6-3
 */
public class MktEmailTemplateVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

	@NotBlank
	private String type;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    private Boolean isActive;

    private Company company;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
