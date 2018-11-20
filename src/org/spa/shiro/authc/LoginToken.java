package org.spa.shiro.authc;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.spa.model.user.User;

/**
 * 提供给formFilter认证的token
 * 
 * @author Ivy on 2016-5-23
 * @see UsernamePasswordToken
 *
 */
public class LoginToken extends UsernamePasswordToken{

    /**
     * 登录类型
     */
    private String accountType;

    private User user; // 认证成功后保存的用户对象
    
    public LoginToken() {}

    public LoginToken(String username, String password, boolean rememberMe, String host, String accountType) {
        super(username, password, rememberMe, host);
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
