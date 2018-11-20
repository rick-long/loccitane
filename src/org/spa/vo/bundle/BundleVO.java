package org.spa.vo.bundle;

import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * @author Ivy 2018-7-31
 */
public class BundleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private String name;

    private Double bundleAmount;

    private Long companyId;

    private List<Long> shopIds = new ArrayList<>();

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String description;

    private Boolean isActive;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    private List<BundleItemVO> bundleItems;
    private Map<Integer, List<ProductOption>> confirmProductOptions = new HashMap<Integer, List<ProductOption>>();

    public Map<Integer, List<ProductOption>> getConfirmProductOptions() {
        return confirmProductOptions;
    }

    public void setConfirmProductOptions(Map<Integer, List<ProductOption>> confirmProductOptions) {
        this.confirmProductOptions = confirmProductOptions;
    }

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

    public List<Long> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public List<BundleItemVO> getBundleItems() {
        return bundleItems;
    }

    public void setBundleItems(List<BundleItemVO> bundleItems) {
        this.bundleItems = bundleItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBundleAmount() {
        return bundleAmount;
    }

    public void setBundleAmount(Double bundleAmount) {
        this.bundleAmount = bundleAmount;
    }
}
