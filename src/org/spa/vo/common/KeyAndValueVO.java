package org.spa.vo.common;

import java.io.Serializable;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
/**
 * Created by Ivy on 2016/03/23.
 */
public class KeyAndValueVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//key id(staff attribute key.id/ product description key.id .....)
	private String key;
    //value (staff attribute.value/product description.value .......)
    private String value;
	//attribute id(staff attribute.id / product description.id .......)
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@InitBinder
	public void initBinder(final WebDataBinder binder) { 
		KeyAndValueCustomEditor ce=new KeyAndValueCustomEditor();
	    binder.registerCustomEditor(KeyAndValueVO.class,ce);
	}
}
