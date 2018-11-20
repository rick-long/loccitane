package org.spa.vo.commission;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import org.spa.model.shop.Shop;
import java.io.Serializable;

/**
 * @author Ivy 2016-7-25
 */
public class CommissionTemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Company company;

    private Shop shop;

    @NotBlank
    private String name;

    private String description;

    private String content;

    private CommissionAttributeKeyVO[] commissionAttributeKeyVO;

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
    
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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
    public CommissionAttributeKeyVO[] getCommissionAttributeKeyVO() {
		return commissionAttributeKeyVO;
	}
    public void setCommissionAttributeKeyVO(CommissionAttributeKeyVO[] commissionAttributeKeyVO) {
		this.commissionAttributeKeyVO = commissionAttributeKeyVO;
	}
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
