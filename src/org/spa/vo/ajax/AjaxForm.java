package org.spa.vo.ajax;

import org.apache.commons.collections.map.HashedMap;
import org.spa.utils.I18nUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 框架ajax请求需要返回的参数
 * <p/>
 * Created by Ivy on 2016/01/16.
 */
public class AjaxForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AjaxForm(int statusCode, String message, Boolean closeCurrent,
                    BindingResult result) {
        this.statusCode = statusCode;
        this.message = message;
        this.closeCurrent = closeCurrent;

        // 设置errorFields
        if (result != null && result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                String fieldName = error.getField();
                // 使用于跨域对比field，详见ChangePWVO。isEqual_cNewPW
                if (fieldName.startsWith("equal_")) {
                    fieldName = fieldName.replace("equal_", "");
                }
                errorFields.add(new ErrorField(fieldName, I18nUtil.getMessageKey(error
                        .getDefaultMessage())));
            }
        }
    }

    private int statusCode; // int 必选。状态码(ok = 200, error = 300, timeout =
    // 301)，可以在BJUI.init时配置三个参数的默认值。
    private String message = ""; // string 可选。信息内容。
    private String tabid; // string 可选。待刷新navtab id，多个id以英文逗号分隔开，当前的navtab
    // id不需要填写，填写后可能会导致当前navtab重复刷新。
    private String dialogid; // string 可选。待刷新dialog
    // id，多个id以英文逗号分隔开，请不要填写当前的dialog
    // id，要控制刷新当前dialog，请设置dialog中表单的reload参数。
    private String divid; // string 可选。待刷新div id，多个id以英文逗号分隔开，请不要填写当前的div
    // id，要控制刷新当前div，请设置该div中表单的reload参数。
    private Boolean closeCurrent; // boolean 可选。是否关闭当前窗口(navtab或dialog)。
    private String forward; // string 可选。跳转到某个url。
    private String forwardConfirm; // string 可选。跳转url前的确认提示信息。
    private String form_token;
    /**
     * 错误字段集合
     */
    private List<ErrorField> errorFields = new ArrayList<ErrorField>();

    /**
     * 弹窗错误信息
     */
    private String alertErrorMsg;

    private Map<String, Object> otherMessages = new HashMap<>();

    public int getStatusCode() {
        return statusCode;
    }

    public AjaxForm setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxForm setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTabid() {
        return tabid;
    }

    public AjaxForm setTabid(String tabid) {
        this.tabid = tabid;
        return this;
    }

    public String getDialogid() {
        return dialogid;
    }

    public AjaxForm setDialogid(String dialogid) {
        this.dialogid = dialogid;
        return this;
    }

    public String getDivid() {
        return divid;
    }

    public AjaxForm setDivid(String divid) {
        this.divid = divid;
        return this;
    }

    public Boolean isCloseCurrent() {
        return closeCurrent;
    }

    public AjaxForm setCloseCurrent(Boolean closeCurrent) {
        this.closeCurrent = closeCurrent;
        return this;
    }

    public String getForward() {
        return forward;
    }

    public AjaxForm setForward(String forward) {
        this.forward = forward;
        return this;
    }

    public String getForwardConfirm() {
        return forwardConfirm;
    }

    public AjaxForm setForwardConfirm(String forwardConfirm) {
        this.forwardConfirm = forwardConfirm;
        return this;
    }

    public List<ErrorField> getErrorFields() {
        return errorFields;
    }

    public AjaxForm setErrorFields(List<ErrorField> errorFields) {
        this.errorFields = errorFields;
        return this;
    }

    public AjaxForm addErrorFields(ErrorField errorField) {
        errorFields.add(errorField);
        return this;
    }

    public AjaxForm addAlertError(String errorMessage) {
        this.alertErrorMsg = errorMessage;
        return this;
    }

    public String getAlertErrorMsg() {
        return alertErrorMsg;
    }

    public AjaxForm setAlertErrorMsg(String alertErrorMsg) {
        this.alertErrorMsg = alertErrorMsg;
        return this;
    }

    public String getForm_token() {
        return form_token;
    }

    public AjaxForm setForm_token(String form_token) {
        this.form_token = form_token;
        return this;
    }

    public Map<String, Object> getOtherMessages() {
        return otherMessages;
    }

    public void setOtherMessages(Map<String, Object> otherMessages) {
        this.otherMessages = otherMessages;
    }
}
