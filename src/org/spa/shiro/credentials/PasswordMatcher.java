package org.spa.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.shiro.authc.LoginToken;
import org.spa.utils.DigestUtil;
import org.spa.utils.EncryptUtil;

/**
 * 密码凭证匹配类
 * 
 * @author Ivy
 *
 */
public class PasswordMatcher implements CredentialsMatcher {

    final protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        LoginToken loginToken = (LoginToken) token;
        Object infoCredentials = info.getCredentials();
        String password = new String(loginToken.getPassword());
        logger.info("password:{}, infoCredentials:{}", password, infoCredentials);
        // 使用新的密码方法匹配，如果不匹配，兼容使用旧系统的加密方法匹配
        return infoCredentials.equals(EncryptUtil.SHA1(password)) || infoCredentials.equals(DigestUtil.digest(password));
    }
}
