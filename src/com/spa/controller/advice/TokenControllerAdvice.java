package com.spa.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.spa.utils.TokenUtil;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 当返回AjaxForm 判断是否出现错误, 如有有错误，重新分配token，并且写入ajaxForm返回。
 * 
 * @author Ivy on 2016-5-18
 */
@ControllerAdvice
public class TokenControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("converterType:" + converterType);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // TODO Auto-generated method stub
        if (body instanceof AjaxForm) {
            AjaxForm ajaxForm = (AjaxForm) body;
            // 出现错误，重新生成token并且返回
            if (AjaxFormHelper.CODE_ERROR == ajaxForm.getStatusCode()) {
                HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
                ajaxForm.setForm_token(TokenUtil.get(servletRequest));
            }
            return ajaxForm;
        }
        return body;
    }

}
