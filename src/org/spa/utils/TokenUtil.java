package org.spa.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ivy on 2016-5-18
 */
@SuppressWarnings("unchecked")
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    public static final String FORM_TOKEN = "form_token"; // 表单提交的token参数名

    public static final String TOKEN_POOL = "TOKEN_POOL"; // 保存所有请求的token

    /**
     * 获取新的token
     * @param pageContext
     * @return
     */
    public static String get(PageContext pageContext) {
        return get((HttpServletRequest)pageContext.getRequest());
    }

    /**
     * 获取新的token
     * 
     * @param request
     * @return
     */
    public static String get(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<String> tokenPool = (List<String>) session.getAttribute(TOKEN_POOL);
        if (tokenPool == null) {
            tokenPool = new ArrayList<>();
            session.setAttribute(TOKEN_POOL, tokenPool);
        }

        String token = UUID.randomUUID().toString();
        tokenPool.add(token); // 保存token 到tokenPool
        logger.debug("New token [{}] for {}.", token, request.getServletPath());
        return token;
    }

    /**
     * 删除token
     * 
     * @param request
     * @param clientToken
     * @return
     */
    public static boolean remove(HttpServletRequest request, String clientToken) {
        HttpSession session = request.getSession(false);
        List<String> tokenPool = (List<String>) session.getAttribute(TOKEN_POOL);
        if (tokenPool.contains(clientToken)) {
            logger.debug("Remove token [{}] for {}.", clientToken, request.getServletPath());
            tokenPool.remove(clientToken); // 删除已经验证的token
            return true;
        }
        return false;
    }
}
