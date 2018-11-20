package org.spa.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Results implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int CODE_SUCCESS = 200;
    public static final int CODE_PARAMETER_ERROR= 201;
    public static final int CODE_SERVER_ERROR = 500;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final String TYPE_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String TYPE_ARGUMENT_ERROR = "ARGUMENT_ERROR";

    public static Results getInstance() {
        return new Results();
    }

    public static Results getInstance(BindingResult bindingResult) {
        Results results = new Results();
        if (bindingResult != null && bindingResult.hasErrors()) {
            results.setCode(CODE_SERVER_ERROR);
            for (FieldError error : bindingResult.getFieldErrors()) {
                results.addMessage(error.getField(), error.getDefaultMessage());
            }
        }
        return results;
    }

    private int code = CODE_SUCCESS;
    private String type;
    private String path;

    private Map<String, Object> messages = new LinkedHashMap<>();

    public boolean hasError() {
        return this.code != CODE_SUCCESS;
    }

    public Results addMessage(String name, Object value) {
        messages.put(name, value);
        return this;
    }

    public int getCode() {
        return code;
    }
    public Results setCode(int code) {
        this.code = code;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getMessages() {
        return messages;
    }
    public void setMessages(Map<String, Object> messages) {
        this.messages = messages;
    }

}
