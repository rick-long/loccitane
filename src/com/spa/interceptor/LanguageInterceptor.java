package com.spa.interceptor;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by wz832 on 2018/6/13.
 */
public class LanguageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String language = request.getParameter("language");
        if (language != null&&language.equals("zh")) {
            Locale locale = new Locale("zh", "CN");
            request.getSession()
                    .setAttribute(
                            SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                            locale);
            request.setAttribute("language", language);
        } else if (language != null&&language.equals("en")) {
            Locale locale = new Locale("en", "US");
            request.getSession()
                    .setAttribute(
                            SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                            locale);
            request.setAttribute("language", language);
        } else {
            request.getSession().setAttribute(
                    SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                    LocaleContextHolder.getLocale());
            language = LocaleContextHolder.getLocale().getLanguage();
            request.setAttribute("language", language);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
