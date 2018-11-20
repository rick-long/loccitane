package org.spa.vo.discount;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import org.spa.model.shop.Shop;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Ivy 2016-5-12
 */
public class DiscountTemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Company company;

    @NotBlank
    private String name;

    private String description;

    private String content;

    private DiscountAttributeKeyVO[] discountAttributeKeyVO;

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

    public DiscountAttributeKeyVO[] getDiscountAttributeKeyVO() {
        return discountAttributeKeyVO;
    }

    public void setDiscountAttributeKeyVO(DiscountAttributeKeyVO[] discountAttributeKeyVO) {
        this.discountAttributeKeyVO = discountAttributeKeyVO;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
