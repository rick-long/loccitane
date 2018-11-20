package org.spa.vo.report;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;

import com.spa.constant.CommonConstant;
import com.spa.tools.land.MathUtils;

public class PrepaidAnalysisVO {

	private String prepaidType;
	private Integer soldCount;
	private Double soldSubTotal;
	private Double usedCount;
	private Double usedSubTotal;
	private Integer count;
	private Double amount;
	private Double initalUnit;
	private Double remainUnit;
	private Long productOptionId;
	private Long shopId;
	private Long prepaidId;
	private Prepaid prepaid;
	private Long prepaidTopUpTransactionId;
	private PrepaidTopUpTransaction ptt;
	
	public Long getPrepaidTopUpTransactionId() {
		return prepaidTopUpTransactionId;
	}
	public void setPrepaidTopUpTransactionId(Long prepaidTopUpTransactionId) {
		this.prepaidTopUpTransactionId = prepaidTopUpTransactionId;
	}
	public PrepaidTopUpTransaction getPtt() {
		return ptt;
	}
	public void setPtt(PrepaidTopUpTransaction ptt) {
		this.ptt = ptt;
	}
	
	public PrepaidAnalysisVO() {
		super();
	
	}
	
	public PrepaidAnalysisVO(String prepaidType, Double usedCount) {
		super();
		this.prepaidType = prepaidType;
		this.usedCount = usedCount;
	}

	public PrepaidAnalysisVO(String prepaidType, Double amount, Double initalUnit, Double remainUnit, Long productOptonId, Long shopId) {
		super();
		this.prepaidType = prepaidType;
		this.amount = amount;
		this.initalUnit = initalUnit;
		this.remainUnit = remainUnit;
		this.productOptionId = productOptonId;
		this.shopId = shopId;
	}
	
	
	public PrepaidAnalysisVO(Double amount, Long prepaidId, Double soldSubTotal, String prepaidType) {
		super();
		this.prepaidType = prepaidType;
		this.soldSubTotal = soldSubTotal;
		this.prepaidId = prepaidId;
		this.amount = amount;
	}
	
	public PrepaidAnalysisVO(Prepaid prepaid, Double amount) {
		super();
		this.prepaid = prepaid;
		this.amount = amount;
	}

	public Long getPrepaidId() {
		return prepaidId;
	}

	public void setPrepaidId(Long prepaidId) {
		this.prepaidId = prepaidId;
	}

	
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getInitalUnit() {
		return initalUnit;
	}
	public void setInitalUnit(Double initalUnit) {
		this.initalUnit = initalUnit;
	}
	public String getPrepaidType() {
		return prepaidType;
	}
	public void setPrepaidType(String prepaidType) {
		this.prepaidType = prepaidType;
	}
	public Double getRemainUnit() {
		return remainUnit;
	}
	public void setRemainUnit(Double remainUnit) {
		this.remainUnit = remainUnit;
	}
	public Integer getSoldCount() {
		return soldCount;
	}
	public void setSoldCount(Integer soldCount) {
		this.soldCount = soldCount;
	}
	public Double getSoldSubTotal() {
		return soldSubTotal;
	}
	public void setSoldSubTotal(Double soldSubTotal) {
		this.soldSubTotal = soldSubTotal;
	}
	public Double getUsedCount() {
		return usedCount;
	}
	public void setUsedCount(Double usedCount) {
		this.usedCount = usedCount;
	}
	public Double getUsedSubTotal() {
		return usedSubTotal;
	}
	public void setUsedSubTotal(Double usedSubTotal) {
		this.usedSubTotal = usedSubTotal;
	}
	
	
	public Long getProductOptionId() {
		return productOptionId;
	}

	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Prepaid getPrepaid() {
		return prepaid;
	}

	public void setPrepaid(Prepaid prepaid) {
		this.prepaid = prepaid;
	}
	
	public Double getUnusedAmount(){
		double unusedAmount = 0;
		if (ptt.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER) 
			|| ptt.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)  ){			
			unusedAmount = ptt.getRemainValue();
		} 
//		else if (ptt.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)) {
//			unusedAmount = ptt.getTopUpInitValue();
//		}  
		else  {
			unusedAmount = (ptt.getTopUpValue() / ptt.getTopUpInitValue()) * ptt.getRemainValue();
			unusedAmount = MathUtils.round(unusedAmount, 2);
		}		
		if (unusedAmount<0){
			unusedAmount = 0;
		}
		
		return new Double(unusedAmount);
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("prepaidType", prepaidType)
				.append("soldCount", soldCount).append("soldSubTotal",
						soldSubTotal).append("usedCount", usedCount).append(
						"usedSubTotal", usedSubTotal).append("count", count)
				.append("amount", amount).append("initalUnit", initalUnit)
				.append("remainUnit", remainUnit).append("productOptionId",
						productOptionId).append("shopId", shopId).append(
						"prepaidId", prepaidId).toString();
	}

	
	
}
