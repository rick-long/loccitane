package org.spa.vo.report;

import java.io.Serializable;
import java.util.Date;

public class CustomerReportVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Date date;
    private Double sumAmt;
    private Double sumQty;
    private Double sumCommiVal;
    private Double sumBonusCommi;
    private Double sumExtraCommission;
    private Double sumTargetExtraCommission;

    private String staff;
    private String categoryName;

    private Double pencentageOfCommi;

    private Double percentageOfSales;

    private Double percentageOfUnits;

    public Double getSumExtraCommission() {
        return sumExtraCommission;
    }

    public void setSumExtraCommission(Double sumExtraCommission) {
        this.sumExtraCommission = sumExtraCommission;
    }

    public Double getSumTargetExtraCommission() {
        return sumTargetExtraCommission;
    }

    public void setSumTargetExtraCommission(Double sumTargetExtraCommission) {
        this.sumTargetExtraCommission = sumTargetExtraCommission;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(Double sumAmt) {
        this.sumAmt = sumAmt;
    }

    public Double getSumQty() {
        return sumQty;
    }

    public void setSumQty(Double sumQty) {
        this.sumQty = sumQty;
    }

    public Double getSumCommiVal() {
        return sumCommiVal;
    }

    public void setSumCommiVal(Double sumCommiVal) {
        this.sumCommiVal = sumCommiVal;
    }

    public Double getSumBonusCommi() {
        return sumBonusCommi;
    }

    public void setSumBonusCommi(Double sumBonusCommi) {
        this.sumBonusCommi = sumBonusCommi;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public Double getPencentageOfCommi() {
        return pencentageOfCommi;
    }

    public void setPencentageOfCommi(Double pencentageOfCommi) {
        this.pencentageOfCommi = pencentageOfCommi;
    }

    public Double getPercentageOfSales() {
        return percentageOfSales;
    }

    public void setPercentageOfSales(Double percentageOfSales) {
        this.percentageOfSales = percentageOfSales;
    }

    public Double getPercentageOfUnits() {
        return percentageOfUnits;
    }

    public void setPercentageOfUnits(Double percentageOfUnits) {
        this.percentageOfUnits = percentageOfUnits;
    }


}
