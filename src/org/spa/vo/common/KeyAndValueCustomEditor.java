package org.spa.vo.common;

import java.beans.PropertyEditorSupport;

public class KeyAndValueCustomEditor extends PropertyEditorSupport{
	
	public void setAsText(String text) {
		KeyAndValueVO kv = new KeyAndValueVO();
        //you have to create a FieldValue object from the string text 
        //which is the one which comes from the form
        //and then setting the value with setValue() method
        setValue(kv);
    }
}
