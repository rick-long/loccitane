package org.spa.vo.app.callback;

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
    private String productName;
    private String amountStr;
    private Double amount;
    private String mins;
    private String status;
    private String therapist;
    public PurchaseItemCallBackVO() {
        super();
    }

    public PurchaseItemCallBackVO(PurchaseItem purchaseItem){

       this.productName=purchaseItem.getProductOption().getProduct().getName();
       this.amountStr= CommonConstant.CURRENCY_TYPE+purchaseItem.getAmount();
       this.amount=purchaseItem.getAmount();
/*       this.shopName=purchaseItem.getPurchaseOrder().getShop().getName();*/
       this.mins= purchaseItem.getProductOption().getMins();
       this.status=purchaseItem.getStatus();
      this.therapist=purchaseItem.getTherapist();
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

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

/*    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }*/

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
}
