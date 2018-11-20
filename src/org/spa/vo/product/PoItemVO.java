package org.spa.vo.product;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.common.PoItemCustomEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public class PoItemVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long poId;
	
	private List<KeyAndValueVO> poValues;
	
	public Long getPoId() {
		return poId;
	}
	public void setPoId(Long poId) {
		this.poId = poId;
	}
	public List<KeyAndValueVO> getPoValues() {
		return poValues;
	}
	public void setPoValues(List<KeyAndValueVO> poValues) {
		this.poValues = poValues;
	}
	
	@InitBinder
	public void initBinder(final WebDataBinder binder) { 
		PoItemCustomEditor ce=new PoItemCustomEditor();
	    binder.registerCustomEditor(PoItemVO.class,ce);
	}
}
