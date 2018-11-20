package org.spa.shiro.authc;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.user.UserLoginHistoryService;
import org.spa.service.user.UserService;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.spa.constant.CommonConstant;

/**
 * login authentication filter
 * 登录认证filter
 *
 * @author Ivy on 2016-5-23
 * @see FormAuthenticationFilter
 */
public class LoginAuthenticationFilter extends AuthenticatingFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLoginHistoryService userLoginHistoryService;

    //TODO - complete JavaDoc

    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";

    public static final String DEFAULT_USERNAME_PARAM = "username";
    public static final String DEFAULT_PASSWORD_PARAM = "password";
    public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";
    public static final String DEFAULT_ACCOUNT_TYPE_PARAM = "accountType"; // 登录类型参数

    private static final Logger log = LoggerFactory.getLogger(LoginAuthenticationFilter.class);

    private String usernameParam = DEFAULT_USERNAME_PARAM;
    private String passwordParam = DEFAULT_PASSWORD_PARAM;
    private String rememberMeParam = DEFAULT_REMEMBER_ME_PARAM;

    private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

    public LoginAuthenticationFilter() {
        setLoginUrl(DEFAULT_LOGIN_URL);
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        String previous = getLoginUrl();
        if (previous != null) {
            this.appliedPaths.remove(previous);
        }
        super.setLoginUrl(loginUrl);
        if (log.isTraceEnabled()) {
            log.trace("Adding login url to applied paths.");
        }
        this.appliedPaths.put(getLoginUrl(), null);
    }

    public String getUsernameParam() {
        return usernameParam;
    }

    /**
     * Sets the request parameter name to look for when acquiring the username.  Unless overridden by calling this
     * method, the default is <code>username</code>.
     *
     * @param usernameParam the name of the request param to check for acquiring the username.
     */
    public void setUsernameParam(String usernameParam) {
        this.usernameParam = usernameParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    /**
     * Sets the request parameter name to look for when acquiring the password.  Unless overridden by calling this
     * method, the default is <code>password</code>.
     *
     * @param passwordParam the name of the request param to check for acquiring the password.
     */
    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }

    public String getRememberMeParam() {
        return rememberMeParam;
    }

    /**
     * Sets the request parameter name to look for when acquiring the rememberMe boolean value.  Unless overridden
     * by calling this method, the default is <code>rememberMe</code>.
     * <p>
     * RememberMe will be <code>true</code> if the parameter value equals any of those supported by
     * {@link org.apache.shiro.web.util.WebUtils#isTrue(javax.servlet.ServletRequest, String) WebUtils.isTrue(request,value)}, <code>false</code>
     * otherwise.
     *
     * @param rememberMeParam the name of the request param to check for acquiring the rememberMe boolean value.
     */
    public void setRememberMeParam(String rememberMeParam) {
        this.rememberMeParam = rememberMeParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String ajaxHeader = servletRequest.getHeader("X-Requested-With");
            if (StringUtils.isEmpty(ajaxHeader)) {
                ajaxHeader = servletRequest.getHeader("x-requested-with");
            }

            if ("XMLHttpRequest".equalsIgnoreCase(ajaxHeader)) {
                PrintWriter out = response.getWriter();
                AjaxForm ajaxForm = AjaxFormHelper.custom(AjaxFormHelper.CODE_TIMEOUT, "Session Timeout, Login again!");
                ajaxForm.setForward(servletRequest.getContextPath() + getLoginUrl());
                out.println(JSON.toJSONString(ajaxForm));
            } else {
                // 非ajax请求，重定向首页
                HttpServletResponse servletResponse = (HttpServletResponse) response;
                servletResponse.sendRedirect(servletResponse.encodeRedirectURL(servletRequest.getContextPath() + getLoginUrl()));
            }

            //saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
     * This default implementation merely returns <code>true</code> if the request is an HTTP <code>POST</code>,
     * <code>false</code> otherwise. Can be overridden by subclasses for custom login submission detection behavior.
     *
     * @param request  the incoming ServletRequest
     * @param response the outgoing ServletResponse.
     * @return <code>true</code> if the request is an HTTP <code>POST</code>, <code>false</code> otherwise.
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
    }

    /**
     * 返回自定义的loginToken
     */
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String accountType = getAccountType(request);
        return new LoginToken(username, password, rememberMe, host, accountType);
    }

    protected boolean isRememberMe(ServletRequest request) {
        return WebUtils.isTrue(request, getRememberMeParam());
    }

    /**
     * 认证成功后保存用户的对象到session中
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        User user = ((LoginToken) token).getUser();
        String userName = user.getUsername();
        String accountType = user.getAccountType();
        log.debug("Save login info into session username:{}, accountType:{}", userName, accountType);
        Set<String> roleSet = userService.getSysRoles(userName, accountType);
        Set<String> permissionSet = userService.getPermissions(userName, accountType);
        user.setRoleSet(roleSet);
        user.setPermissionSet(permissionSet);
        log.debug("roleSet:{}", roleSet);
        log.debug("permissionSet:{}", permissionSet);
        Hibernate.initialize(user.getStaffHomeShops()); // 初始化home shop
        subject.getSession().setAttribute(CommonConstant.CURRENT_LOGIN_USER, user);
        Set<Shop> staffHomeShops = user.getStaffHomeShops();
        Iterator<Shop> iterator = staffHomeShops.iterator();
        Shop shop = null;
        if (iterator.hasNext()) {
            shop = iterator.next();
        }
        subject.getSession().setAttribute("currentShop", shop);
        // saveOrUpdate login history
        userLoginHistoryService.save(user, request.getRemoteAddr());
        // 转到成功页面
        //issueSuccessRedirect(request, response);
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
        //we handled the success redirect directly, prevent the chain from continuing:
        return false;
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        setFailureAttribute(request, e);
        //login failed, let request continue back to the login page:
        return true;
    }

    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String className = ae.getClass().getName();
        request.setAttribute(getFailureKeyAttribute(), className);
    }

    protected String getUsername(ServletRequest request) {
        return WebUtils.getCleanParam(request, getUsernameParam());
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, getPasswordParam());
    }

    /**
     * 获取登录类型参数值
     *
     * @param request
     * @return
     */
    protected String getAccountType(ServletRequest request) {
        return WebUtils.getCleanParam(request, DEFAULT_ACCOUNT_TYPE_PARAM);
    }

}
