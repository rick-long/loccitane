package org.spa.vo.common;

import java.beans.PropertyEditorSupport;

import org.spa.vo.product.PoItemVO;

public class PoItemCustomEditor extends PropertyEditorSupport{
	
	public void setAsText(String text) {
		PoItemVO pi = new PoItemVO();
        //you have to create a FieldValue object from the string text 
        //which is the one which comes from the form
        //and then setting the value with setValue() method
        setValue(pi);
    }
}
