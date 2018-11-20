package com.spa.springmvc.view;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端的所有页面使用模板嵌套
 * 
 * @author Ivy on 2016-6-27
 *
 */
public class JstlView extends org.springframework.web.servlet.view.JstlView{
    /**
     * 前端的地址前缀
     */
    private static final String FRONT_URL_PREFIX = "/WEB-INF/jsp/front/";
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dispatcherPath = prepareForRendering(request, response);
        // set original view being asked for as a request parameter
        if(dispatcherPath.startsWith(FRONT_URL_PREFIX)) {
            // 添加所有model的参到请求域
            for(Map.Entry<String, Object> entry : model.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            request.setAttribute("partial", dispatcherPath);
            // force everything to be template.jsp
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/front/template.jsp");
            rd.include(request, response);
        } else {
            super.renderMergedOutputModel(model, request, response);
        }
    }
}
