package org.spa.vo.app.callback;

import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrepaidCallBackVO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    private String name;
    private Long id;
    private String shopName;//购买时的店铺
    private String clientName;
    private String reference;
    private List<PrepaidTopUpTransactionCallBackVO>prepaidTopUpTransactionList;
    public PrepaidCallBackVO() {
		super();
	}
    public PrepaidCallBackVO(Prepaid prepaid){
        this.id=prepaid.getId();
        this.shopName=prepaid.getShop().getName();
        this.clientName=prepaid.getUser().getFullName();
        this.reference=prepaid.getReference();
        this.prepaidTopUpTransactionList=getPrepaidTopUpTransactionCallBackVOList(prepaid.getPrepaidTopUpTransactions());
        this.name=prepaid.getName();

    }
    public List<PrepaidTopUpTransactionCallBackVO> getPrepaidTopUpTransactionCallBackVOList(Set<PrepaidTopUpTransaction> prepaidTopUpTransactionList) {
        List<PrepaidTopUpTransactionCallBackVO> prepaidTopUpTransactionCallBackVOList=new ArrayList<>();
      for(PrepaidTopUpTransaction prepaidTopUpTransaction :prepaidTopUpTransactionList){
          PrepaidTopUpTransactionCallBackVO prepaidTopUpTransactionCallBackVO=new PrepaidTopUpTransactionCallBackVO(prepaidTopUpTransaction);
          prepaidTopUpTransactionCallBackVOList.add(prepaidTopUpTransactionCallBackVO);
      }
      return prepaidTopUpTransactionCallBackVOList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<PrepaidTopUpTransactionCallBackVO> getPrepaidTopUpTransactionList() {
        return prepaidTopUpTransactionList;
    }

    public void setPrepaidTopUpTransactionList(List<PrepaidTopUpTransactionCallBackVO> prepaidTopUpTransactionList) {
        this.prepaidTopUpTransactionList = prepaidTopUpTransactionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
