package org.spa.vo.app.callback;

import com.spa.constant.CommonConstant;
import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PurchaseOrderCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private String category;
	private boolean payOnline;
	private String orderCreateDate;
    private String orderRef;
    private String shopName;
    private String shopRef;
    private String shopPrefix;

    private String shopPhone;
    private Double totalAmount;
    private String totalAmountStr;
    private String currency;
    List<PurchaseItemCallBackVO>orderItemList;
    public PurchaseOrderCallBackVO() {
        super();
    }
    public PurchaseOrderCallBackVO(PurchaseOrder purchaseOrder){
        this.orderCreateDate=dateToString(purchaseOrder.getCreated(),"yyyy-MM-dd HH:mm:ss");
       this.orderRef=purchaseOrder.getReference();
       this.shopPhone=purchaseOrder.getShop().getPhoneNumber();
       this.shopName= purchaseOrder.getShop().getName();
       this.totalAmount=purchaseOrder.getTotalAmount();
       this.payOnline=getPayOnline(purchaseOrder.getBook());
       this.shopRef=purchaseOrder.getShop().getReference();
       this.category=iniCategory(purchaseOrder.getPurchaseItems());
        this.currency = I18nUtil.getMessageKey("label.currency");
        this.shopPrefix=purchaseOrder.getShop().getPrefix();
        this.totalAmountStr= CommonConstant.CURRENCY_TYPE+purchaseOrder.getTotalAmount();
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountStr() {
        return totalAmountStr;
    }

    public void setTotalAmountStr(String totalAmountStr) {
        this.totalAmountStr = totalAmountStr;
    }

    public boolean getPayOnline() {
        return payOnline;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPayOnline(boolean payOnline) {
        this.payOnline = payOnline;
    }

    public String getShopRef() {
        return shopRef;
    }

    public void setShopRef(String shopRef) {
        this.shopRef = shopRef;
    }

    public String getShopPrefix() {
        return shopPrefix;
    }

    public void setShopPrefix(String shopPrefix) {
        this.shopPrefix = shopPrefix;
    }

    public List<PurchaseItemCallBackVO> getOrderItemList() {
        return orderItemList;
    }


    public void setOrderItemList(List<PurchaseItemCallBackVO> orderItemList) {
        this.orderItemList = orderItemList;
    }

    private Boolean getPayOnline(Book book){
        if(book!=null){
            if(book.getStatus().equals(CommonConstant.BOOK_STATUS_PAID)){
                return true;
            }
        }
        return false;
    }
    private String iniBookAppointmentDate(Book book){
        String date=" ";
        if(book!=null&&book.getAppointmentTime()!=null){
        date=dateToString(book.getAppointmentTime(),"yyyy-MM-dd");
        }
        return date;
    }
    private String iniCategory(Set<PurchaseItem> purchaseItems){
        String category="";
        if(purchaseItems!=null&&purchaseItems.size()>0){
            for (PurchaseItem purchaseItem:purchaseItems){
                if(purchaseItem.getProductOption()!=null&& purchaseItem.getProductOption().getBookItems()!=null&&purchaseItem.getProductOption().getBookItems().size()>0){
                    for( BookItem bookItem:purchaseItem.getProductOption().getBookItems()){
                        category= bookItem.getProductOption().getProduct().getCategory().getCategory().getName();
                        break;
                    }
                }
            }
        }
        return category;
    }
}
