package org.spa.vo.ajax;

import java.io.Serializable;

/**
 * json 返回具体错误信息类
 * <p/>
 * Created by Ivy on 2016/01/16.
 */
public class ErrorField implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorField(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    /**
     * 错误字段的name
     */
    private String fieldName;

    /**
     * 错误信息
     */
    private String errorMessage;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
