package org.spa.vo.app.callback;

import org.spa.model.product.Category;

import java.io.Serializable;
import java.util.*;

public class CategoryCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private Long id;
    private String reference;
    private String name;
    List<ProductCallBackVO>productCallBackVOList;
    List<CategoryCallBackVO>categoryChildList;
    public CategoryCallBackVO() {
        super();
    }
    public  CategoryCallBackVO(Category category){
        this.name=category.getName();
        this.id=category.getId();
        this.reference=category.getReference();
    }
    public  CategoryCallBackVO(Category category,boolean subLevel){
    	if(subLevel){
    		 this.name=category.getName()+"*";
    	}
        this.id=category.getId();
        this.reference=category.getReference();
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryCallBackVO> getCategoryChildList() {
        return categoryChildList;
    }

    public void setCategoryChildList(List<CategoryCallBackVO> categoryChildList) {
        this.categoryChildList = categoryChildList;
    }
}
