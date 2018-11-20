package com.spa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.utils.TokenUtil;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 防止重复提交拦截器 
 * 客户端如有form_token，表示需要做重复提交验证
 *
 * @author Ivy on 2016-5-17
 * @see {@link TokenInterceptor}
 */
public class TokenInterceptor implements HandlerInterceptor {

    final protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 客户端提交了token， 需要做二次提交验证
        String clientToken = request.getParameter(TokenUtil.FORM_TOKEN);
        if (StringUtils.isNotBlank(clientToken)) {
            // 验证通过
            boolean isSuccess = TokenUtil.remove(request, clientToken);
            if (!isSuccess) {
                logger.warn("please don't repeat submit,CLIENT_FORM_TOKEN:{}, url:{}", clientToken, request.getServletPath());
                AjaxForm ajaxForm = AjaxFormHelper.error().addAlertError("Duplicate submit error!");
                response.setHeader("Content-Type", "application/json;charset=UTF-8"); // 设置返回json类型的数据
                response.getWriter().println(JSON.toJSONString(ajaxForm));
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO Auto-generated method stub
       
    }

}
