package org.spa.vo.product;
import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.product.ProductOptionKey;
import org.spa.vo.common.KeyAndValueVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivy on 2016/01/16.
 */
public class PoItemEditVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private Long productOptionId;
	
	@NotBlank
	private String poRef ;
	
	private List<ProductOptionKey> pokeyList;
	
	private Map<Long,KeyAndValueVO> kvMap;
	
	public Long getProductOptionId() {
		return productOptionId;
	}
	public void setProductOptionId(Long productOptionId) {
		this.productOptionId = productOptionId;
	}
	public List<ProductOptionKey> getPokeyList() {
		return pokeyList;
	}
	public void setPokeyList(List<ProductOptionKey> pokeyList) {
		this.pokeyList = pokeyList;
	}
	public Map<Long, KeyAndValueVO> getKvMap() {
		return kvMap;
	}
	public void setKvMap(Map<Long, KeyAndValueVO> kvMap) {
		this.kvMap = kvMap;
	}
	
	public String getPoRef() {
		return poRef;
	}
	public void setPoRef(String poRef) {
		this.poRef = poRef;
	}
}
