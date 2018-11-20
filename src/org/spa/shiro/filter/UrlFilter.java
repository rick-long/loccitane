package org.spa.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.spa.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.user.User;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 基于URL约定的 Authorization Filter
 *
 * @author Ivy
 */
public class UrlFilter extends AuthorizationFilter {

    final protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        // 员工用户才需要url权限过来，其他的返回false
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //String[] perms = (String[]) mappedValue;
        String url = servletRequest.getServletPath();
        if (CommonConstant.USER_ACCOUNT_TYPE_MEMBER.equals(WebThreadLocal.getUser().getAccountType())) {
            if (url.startsWith("/front/")) {
                return true;
            }
            logger.error("staff cannot access member page：{}", url);
            return false;
        }

        if(!CommonConstant.USER_ACCOUNT_TYPE_STAFF.equals(WebThreadLocal.getUser().getAccountType())) {
            return false;
        }
        Subject subject = getSubject(request, response);

        // 首页可访问
        if ("/".equals(url) || "/index".equals(url) || "/index/".equals(url)) {
            return true;
        }
        String[] urlParts = url.substring(1).split("/");
        String permit = StringUtils.join(urlParts, ":");

        boolean isPermitted = true;
        if (!subject.isPermitted(permit)) {
            isPermitted = false;
        }
        // logger.debug("urlFilter:{}, isAccessAllowed:{}, permission:{}", new Object[]{url, isPermitted, permit});
        if(!isPermitted) {
            logger.error("No permission for url:{},permission:{}", url, permit);
        } else {
            logger.debug("urlFilter:{}, isAccessAllowed:{}, permission:{}", new Object[]{url, isPermitted, permit});
        }
        return isPermitted;
        //return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String ajaxHeader = servletRequest.getHeader("X-Requested-With");
            if (StringUtils.isEmpty(ajaxHeader)) {
                ajaxHeader = servletRequest.getHeader("x-requested-with");
            }

            User currentLoginUser = WebThreadLocal.getUser();
            boolean isAdmin = currentLoginUser != null && currentLoginUser.getRoleSet().contains(CommonConstant.STAFF_ROLE_REF_ADMIN);

            if ("XMLHttpRequest".equalsIgnoreCase(ajaxHeader)) {
                PrintWriter out = response.getWriter();
                AjaxForm ajaxForm = AjaxFormHelper.error();
                ajaxForm.setStatusCode(AjaxFormHelper.CODE_ACCESS_DENIED);

                if (isAdmin) {
                    ajaxForm.setMessage("Access Denied:" + servletRequest.getServletPath());
                } else {
                    ajaxForm.setMessage("Access Denied!");
                }
                out.println(JSON.toJSONString(ajaxForm));
            } else {
                // 非ajax请求，unauthorizedUrl
                if (org.apache.shiro.util.StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    //WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    if (isAdmin) {
                        out.println("You do not have permission to access:" + servletRequest.getServletPath());
                    } else {
                        out.println("You do not have permission to access!");
                    }
                }
            }
        }
        return false;
    }
}
