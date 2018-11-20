package org.spa.vo.product;
import org.spa.vo.common.KeyAndValueVO;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivy on 2016/04/07.
 */
public class SupernumeraryPriceAddVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long productOptionId;

	private Long productId;

	private Double originalPrice;
	
	private List<KeyAndValueVO> shopPriceList;
	
	public Long getProductOptionId() {
		return productOptionId;
	}
	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}
	public List<KeyAndValueVO> getShopPriceList() {
		return shopPriceList;
	}
	public void setShopPriceList(List<KeyAndValueVO> shopPriceList) {
		this.shopPriceList = shopPriceList;
	}
	public Double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
