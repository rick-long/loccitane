package com.spa.jxlsBean.importDemo;

import java.io.Serializable;
import java.util.Date;

/* create by rick 2018-11-12*/
public class PrepaidImportGiftBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String voucherNumber;//vorcher 唯一的关键字

    private String voucherName;
    private String cashValue;//预付值

    private Date issueDate;//预付

    private Date expiredDate;//过期时间

    private String status;//状态

    private String returnError;

    public String getReturnError() {
        return returnError;
    }

    public void setReturnError(String returnError) {
        this.returnError = returnError;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getCashValue() {
        return cashValue;
    }

    public void setCashValue(String cashValue) {
        this.cashValue = cashValue;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }
}
