package org.spa.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.user.User;
import org.spa.service.user.UserService;
import org.spa.shiro.authc.LoginToken;
import org.spa.utils.WebThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm，从数据库获取user的role和permissions
 */
public class UserRealm extends AuthorizingRealm {

    final protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    /**
     * 用户认证
     *
     * @param token 自定义的LoginToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo call:" + token.getClass());
        LoginToken loginToken = (LoginToken) token;
        String userName = (String) loginToken.getPrincipal();
        String accountType = loginToken.getAccountType();
        User user = userService.getLoginUserByAccountWithType(userName, accountType);
        logger.debug("Authentication user:{}, accountType:{}", userName, accountType);
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        loginToken.setUser(user); // 保存登录用户对象
       
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * 设置user所拥有的权限集合
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = WebThreadLocal.getUser();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(user.getRoleSet());
        authorizationInfo.setStringPermissions(user.getPermissionSet());
        return authorizationInfo;
    }


}
