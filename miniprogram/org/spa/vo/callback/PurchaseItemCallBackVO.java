package org.spa.vo.callback;

import com.spa.constant.CommonConstant;
import org.spa.model.order.PurchaseItem;
import org.spa.utils.DateUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class PurchaseItemCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long purchaseItemId;

    private String productName;
    private String price;
    private Double priceValue;
    private String shopName;
    private String mins;
    private String status;
    private String therapist;
    private Double totalDiscount;
    private Double effectiveValue;

    public PurchaseItemCallBackVO() {
        super();
    }

    public PurchaseItemCallBackVO(PurchaseItem purchaseItem){

        this.purchaseItemId = purchaseItem.getId();
        this.price = CommonConstant.CURRENCY_TYPE + purchaseItem.getPrice();
        this.priceValue = purchaseItem.getPrice();
        this.status = purchaseItem.getStatus();
        this.therapist = purchaseItem.getTherapist();
        this.totalDiscount = purchaseItem.getDiscountValue();
        this.effectiveValue = purchaseItem.getEffectiveValue();
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTherapist() {
        return therapist;
    }

    public void setTherapist(String therapist) {
        this.therapist = therapist;
    }

    public Long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getEffectiveValue() {
        return effectiveValue;
    }

    public void setEffectiveValue(Double effectiveValue) {
        this.effectiveValue = effectiveValue;
    }
}
