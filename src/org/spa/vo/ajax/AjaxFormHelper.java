package org.spa.vo.ajax;
import org.springframework.validation.BindingResult;

/**
 * AjaxForm 工具类， 快速获取一个AjaxForm对象
 * <p/>
 * Created by Ivy on 2016/01/16.
 */
public class AjaxFormHelper {

    /**
     * 成功状态码
     */
    public static final int CODE_SUCCESS = 200;

    /**
     * 失败状态码
     */
    public static final int CODE_ERROR = 300;

    /**
     *  超时状态码
     */
    public static final int CODE_TIMEOUT = 301;
    
    /**
     *  Access Denied状态码
     */
    public static final int CODE_ACCESS_DENIED = 401;

    /**
     * 成功默认返回的消息
     */
    public static final String MSG_SUCCESS = "Success.";

    /**
     * 失败默认返回的消息
     */
    public static final String MSG_ERROR = "Failed！";

    /**
     * 成功默认返回的ajaxForm
     *
     * @return {message:MSG_SUCCESS, closeCurrent:true}
     */
    public static AjaxForm success() {
        return custom(CODE_SUCCESS, MSG_SUCCESS, false, null);
    }

    public static AjaxForm success(String message) {
        return custom(CODE_SUCCESS, message, false, null);
    }

    /**
     * 失败默认返回的ajaxForm
     *
     * @return {message:MSG_ERROR, closeCurrent:false}
     */
    public static AjaxForm error() {
        return custom(CODE_ERROR, MSG_ERROR, false, null);
    }

    public static AjaxForm error(String message) {
        return custom(CODE_ERROR, message, false, null);
    }

    public static AjaxForm error(BindingResult result) {
        return custom(CODE_ERROR, null, false, result);
    }

    /**
     * 自定义返回的ajaxForm
     *
     * @param statusCode 状态码
     * @param message    返回的信息
     * @return {message: message, closeCurrent:closeCurrent}
     */
    public static AjaxForm custom(int statusCode, String message) {
        return custom(statusCode, message, null, null);
    }

    /**
     * 自定义返回的ajaxForm
     *
     * @param statusCode   状态码
     * @param message      返回的信息
     * @param closeCurrent 是否关闭
     * @return {message: message, closeCurrent:closeCurrent}
     */
    public static AjaxForm custom(int statusCode, String message, Boolean closeCurrent) {
        return custom(statusCode, message, closeCurrent, null);
    }

    /**
     * 自定义返回的ajaxForm
     *
     * @param statusCode   状态码
     * @param message      返回的信息
     * @param closeCurrent 是否关闭
     * @param result       hibernate valid 的结果集
     * @return {message: message, closeCurrent:closeCurrent}
     */
    public static AjaxForm custom(int statusCode, String message, Boolean closeCurrent, BindingResult result) {
        return new AjaxForm(statusCode, message, closeCurrent, result);
    }

}
