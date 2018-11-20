package org.spa.vo.report;

import java.io.Serializable;

import org.spa.model.order.PurchaseItem;
import org.spa.vo.page.Page;

public class SalesSearchVO extends Page<PurchaseItem> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long memberId;
    private String username;
    private String fromDate;
    private String toDate;
    private String fullName;
    private Long shopId;
    private Long paymentMethodId;
    private Long staffId;

    private Long rootCategoryId;
    private String prodType;
    private Long categoryId;
    private Long[] lowestCategoriesByCategoryIds;
    private Long productId;
    private Long productOptionId;

    private Long companyId;

    private String status;

    /*private Boolean isLanlordReport;*/

    private Boolean isSearchByJob;

    private String reviewDate;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getIsSearchByJob() {
        return isSearchByJob;
    }

    public void setIsSearchByJob(Boolean isSearchByJob) {
        this.isSearchByJob = isSearchByJob;
    }

   /* public Boolean getIsLanlordReport() {
        return isLanlordReport;
    }

    public void setIsLanlordReport(Boolean isLanlordReport) {
        this.isLanlordReport = isLanlordReport;
    }*/

    public Long getRootCategoryId() {
        return rootCategoryId;
    }

    public void setRootCategoryId(Long rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public Long[] getLowestCategoriesByCategoryIds() {
        return lowestCategoriesByCategoryIds;
    }

    public void setLowestCategoriesByCategoryIds(Long[] lowestCategoriesByCategoryIds) {
        this.lowestCategoriesByCategoryIds = lowestCategoriesByCategoryIds;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
