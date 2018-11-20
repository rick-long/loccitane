package org.spa.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.spa.utils.Results;
import org.spa.utils.ServletUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class PassThruFilter extends PassThruAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Subject subject = getSubject(httpRequest, httpResponse);
        PrintWriter out = null ;
        if (subject.isAuthenticated()) {
            return super.onAccessDenied(request, response);
        } else {
            if (ServletUtil.isAjaxRequest(httpRequest)) {
                response.setContentType("application/json; charset=utf-8");
                Map<String, Object> messages = new LinkedHashMap<>();
                messages.put("errorMsg","No access rights");
                JSONObject res = new JSONObject();
                res.put("code", Results.CODE_UNAUTHORIZED);
                res.put("messages", messages);
                out = response.getWriter();
                out.append(res.toString());
                return false;
            }
            return super.onAccessDenied(request, response);
        }
    }
}
