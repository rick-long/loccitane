package org.spa.vo.discount;

import org.spa.model.company.Company;
import org.spa.model.discount.DiscountTemplate;
import org.spa.model.shop.Shop;
import org.spa.vo.common.SelectOptionVO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ivy 2016-5-13
 */
public class DiscountRuleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private Company company;

    private List<Long> shopIds = new ArrayList<>();

    private List<Shop> shopList = new ArrayList<>();

    @NotNull
    private Long discountTemplateId;

    private DiscountTemplate discountTemplate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String description;

    private SelectOptionVO[] categoryVOs;

    private SelectOptionVO[] productVOs;

    private SelectOptionVO[] userGroupIds;
    
    private SelectOptionVO[] loyaltyGroupIds;

    private DiscountAttributeVO[] discountAttributeVOs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Long> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public Long getDiscountTemplateId() {
        return discountTemplateId;
    }

    public void setDiscountTemplateId(Long discountTemplateId) {
        this.discountTemplateId = discountTemplateId;
    }

    public DiscountTemplate getDiscountTemplate() {
        return discountTemplate;
    }

    public void setDiscountTemplate(DiscountTemplate discountTemplate) {
        this.discountTemplate = discountTemplate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SelectOptionVO[] getCategoryVOs() {
        return categoryVOs;
    }

    public void setCategoryVOs(SelectOptionVO[] categoryVOs) {
        this.categoryVOs = categoryVOs;
    }

    public SelectOptionVO[] getProductVOs() {
        return productVOs;
    }

    public void setProductVOs(SelectOptionVO[] productVOs) {
        this.productVOs = productVOs;
    }

    public SelectOptionVO[] getUserGroupIds() {
        return userGroupIds;
    }

    public void setUserGroupIds(SelectOptionVO[] userGroupIds) {
        this.userGroupIds = userGroupIds;
    }

    public SelectOptionVO[] getLoyaltyGroupIds() {
		return loyaltyGroupIds;
	}
    public void setLoyaltyGroupIds(SelectOptionVO[] loyaltyGroupIds) {
		this.loyaltyGroupIds = loyaltyGroupIds;
	}
    
    public DiscountAttributeVO[] getDiscountAttributeVOs() {
        return discountAttributeVOs;
    }

    public void setDiscountAttributeVOs(DiscountAttributeVO[] discountAttributeVOs) {
        this.discountAttributeVOs = discountAttributeVOs;
    }
}
