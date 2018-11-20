package org.spa.vo.app.callback;

import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.utils.DateUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class PrepaidTopUpTransactionCallBackVO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    private String name;
    private String treatment;
    private String shopName;//充值时的店铺
    private String reference;
    private String topUpDate;
    private String expiryDate;
    private double prepaidValue;
    private double initValue;
    private double remainValue;
    private String prepaidType;//充值时的类型
    private  String isActive;
    public PrepaidTopUpTransactionCallBackVO() {
		super();
	}
    public PrepaidTopUpTransactionCallBackVO(PrepaidTopUpTransaction prepaidTopUpTransaction){
        this.name=prepaidTopUpTransaction.getPrepaid().getName();
        this.shopName=prepaidTopUpTransaction.getPrepaid().getShop().getName();
        this.reference=prepaidTopUpTransaction.getPrepaid().getReference();
        this.topUpDate=dateToString(prepaidTopUpTransaction.getTopUpDate(),"yyyy-MM-hh");
        this.expiryDate=dateToString(prepaidTopUpTransaction.getExpiryDate(),"yyyy-MM-hh");
        this.prepaidValue=prepaidTopUpTransaction.getTopUpValue();
        this.initValue=prepaidTopUpTransaction.getTopUpInitValue();
        this.remainValue=prepaidTopUpTransaction.getRemainValue();
        this.prepaidType=prepaidTopUpTransaction.getPrepaidType();
       if(prepaidTopUpTransaction.getProductOption()!=null){
           this.treatment=prepaidTopUpTransaction.getProductOption().getLabel33();
       }
        this.isActive=prepaidTopUpTransaction.getIsActiveYOrN();

    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTopUpDate() {
        return topUpDate;
    }

    public void setTopUpDate(String topUpDate) {
        this.topUpDate = topUpDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getPrepaidValue() {
        return prepaidValue;
    }

    public void setPrepaidValue(double prepaidValue) {
        this.prepaidValue = prepaidValue;
    }

    public double getInitValue() {
        return initValue;
    }

    public void setInitValue(double initValue) {
        this.initValue = initValue;
    }

    public double getRemainValue() {
        return remainValue;
    }

    public void setRemainValue(double remainValue) {
        this.remainValue = remainValue;
    }

    public String getPrepaidType() {
        return prepaidType;
    }

    public void setPrepaidType(String prepaidType) {
        this.prepaidType = prepaidType;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    private String  dateToString(Date date, String format ){
        String dateTime="";
        try {
            dateTime= DateUtil.dateToString(date,format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
