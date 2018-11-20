package org.spa.vo.callback;

import com.spa.constant.CommonConstant;
import org.spa.model.order.PurchaseOrder;
import org.spa.utils.DateUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

public class PurchaseOrderListCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private Long purchaseOrderId;
	private String orderCreateDate;
    private String orderRef;
    private String shopName;
    private String shopRef;
    private String shopPhone;
    private Double priceTotal;
    private String totalAmount;

    private List<PaymentCallBackVO> pcbList = new ArrayList<>();
    private List<PurchaseItemCallBackVO> picbList = new ArrayList<>();

    public PurchaseOrderListCallBackVO(PurchaseOrder purchaseOrder) {
        this.purchaseOrderId = purchaseOrder.getId();
        this.orderCreateDate = dateToString(purchaseOrder.getPurchaseDate(), "yyyy-MM-dd HH:mm:ss");
        this.orderRef = purchaseOrder.getReference();
        this.shopPhone = purchaseOrder.getShop().getPhoneNumber();
        this.shopName = purchaseOrder.getShop().getName();
        this.priceTotal = purchaseOrder.getTotalAmount();
        this.shopRef = purchaseOrder.getShop().getReference();
        this.totalAmount = CommonConstant.CURRENCY_TYPE + purchaseOrder.getTotalAmount();

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

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(String orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getShopRef() {
        return shopRef;
    }

    public void setShopRef(String shopRef) {
        this.shopRef = shopRef;
    }

    public List<PaymentCallBackVO> getPcbList() {
        return pcbList;
    }

    public void setPcbList(List<PaymentCallBackVO> pcbList) {
        this.pcbList = pcbList;
    }

    public List<PurchaseItemCallBackVO> getPicbList() {
        return picbList;
    }

    public void setPicbList(List<PurchaseItemCallBackVO> picbList) {
        this.picbList = picbList;
    }
}
