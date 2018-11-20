package org.spa.vo.bonus;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class BonusTemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Company company;

    @NotBlank
    private String name;

    private String description;

    private String content;

    private BonusAttributeKeyVO[] bonusAttributeKeyVO;

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
    
    public BonusAttributeKeyVO[] getBonusAttributeKeyVO() {
		return bonusAttributeKeyVO;
	}
    public void setBonusAttributeKeyVO(BonusAttributeKeyVO[] bonusAttributeKeyVO) {
		this.bonusAttributeKeyVO = bonusAttributeKeyVO;
	}
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
