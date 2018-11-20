package com.spa.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.company.Company;
import org.spa.service.company.CompanyService;
import org.spa.serviceImpl.company.CompanyServiceImpl;
import org.spa.utils.SpringUtil;
import org.spa.utils.WebThreadLocal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * WebThreadLocal 本地线程 变量重置filter
  * Created by Ivy on 2016/01/16.
 */
public class WebThreadLocalResetFilter implements Filter {

    private CompanyService companyService = SpringUtil.getBean(CompanyServiceImpl.class);


    final protected Logger logger = LoggerFactory.getLogger(getClass());

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("WebThreadLocalResetFilter init");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String hostName = httpServletRequest.getServerName();
        Company company = companyService.get("domain", hostName);
        if(company == null) {
            logger.debug("Company domain not found for:{}", hostName);
            return;
        }
        WebThreadLocal.setCompany(company);
        String urlRoot = new StringBuilder(httpServletRequest.getRequestURI().length())
                .append(httpServletRequest.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(httpServletRequest.getContextPath()).toString();
        WebThreadLocal.setUrlRoot(urlRoot);
        chain.doFilter(request, response);
    }

    public void destroy() {}
}
