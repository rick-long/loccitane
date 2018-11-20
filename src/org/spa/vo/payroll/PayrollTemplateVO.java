package org.spa.vo.payroll;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import org.spa.model.shop.Shop;
import java.io.Serializable;

/**
 * @author Ivy 2016-8-19
 */
public class PayrollTemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Company company;

    private Shop shop;

    @NotBlank
    private String name;

    private String description;

    private String content;

    private PayrollAttributeKeyVO[] payrollAttributeKeyVO;

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
    public PayrollAttributeKeyVO[] getPayrollAttributeKeyVO() {
		return payrollAttributeKeyVO;
	}
    public void setPayrollAttributeKeyVO(PayrollAttributeKeyVO[] payrollAttributeKeyVO) {
		this.payrollAttributeKeyVO = payrollAttributeKeyVO;
	}
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
