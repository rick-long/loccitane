package org.spa.vo.callback;

import org.spa.model.payment.Payment;

public class PaymentCallBackVO {
    private static final long serialVersionUID = 1L;
    
    private Long paymentId;
    // payment status
    private String statue;
    private Double amount;
    private String shopName;

    public PaymentCallBackVO(Payment payment) {
        PurchaseOrderListCallBackVO polcbVO = new PurchaseOrderListCallBackVO(payment.getPurchaseOrder());

        this.paymentId = payment.getId();
        this.statue = payment.getStatus();
        this.amount = payment.getAmount();
        this.shopName = polcbVO.getShopName();
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
