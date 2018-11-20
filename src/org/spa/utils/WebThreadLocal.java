package org.spa.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.company.Company;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/01/16.
 */
public class WebThreadLocal {

    private static boolean isDebugMode = false;
    
	private static final Logger logger = LoggerFactory.getLogger(WebThreadLocal.class);

    private static ThreadLocal<Company> companyThreadLocal = new ThreadLocal<Company>();

    private static ThreadLocal<String> urlRootThreadLocal = new ThreadLocal<String>();

    public static String getUrlRoot() {
        return urlRootThreadLocal.get();
    }

    public static void setUrlRoot(String domain) {
        urlRootThreadLocal.set(domain);
    }

    /**
     * 设置current shop
     * @param shop
     */
    public static void setCurrentShop(Shop shop){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("currentShop",shop);
    }

    /**
	 * 获取登录的用户信息
	 *
	 * @return
	 */
    public static User getUser() {
        if (isDebugMode) {
            logger.debug("get user in debug mode");
            org.spa.model.user.User user = new org.spa.model.user.User();
            user.setId(0L);
            user.setUsername("DATA_TRANSFER");
            return user;
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return (User) session.getAttribute(CommonConstant.CURRENT_LOGIN_USER);
    }

	/**
	 * 获取当前的用户的店铺信息
	 *
	 * @return
	 */
	public static Shop getHomeShop(){
		/*Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (obj instanceof String) {
			logger.warn("cannot get principal from SecurityContextHolder, return null shop instead.");
			return null;
		} else {
			return ((LoginUser) obj).getHomeShop();
		}*/
	    return getUser().getStaffHomeShops().iterator().next();
	}

	public static List<Long> getHomeShopIdsByLoginStaff(){
		List<Long> shopIds=new ArrayList<Long>();
		for(Shop shop : getUser().getStaffHomeShops()){
			shopIds.add(shop.getId());
		}
	    return shopIds;
	}
	
    /**
     * For member user
     * @return
     */
    public static Company getHomeCompany(){
        return getUser().getShop().getCompany();
    }
	
	/**
	 * 获取当前的公司信息
	 *
	 * @return
	 */
	public static Company getCompany() {
		//return getUser().getCompany();
        return companyThreadLocal.get();
	}

    public static void setCompany(Company company) {
        companyThreadLocal.set(company);
    }

    public static boolean isDebugMode() {
        return isDebugMode;
    }

    public static void setDebugMode(boolean isDebugMode) {
        WebThreadLocal.isDebugMode = isDebugMode;
    }

	/*public static boolean isAnyGranted(String... roles) {
		Set<String> grantedRoles = authoritiesToRoles();
		List<String> expectOneOfRoles = Arrays.asList(roles);
		for (String grantedRole : grantedRoles) {
			if (expectOneOfRoles.contains(grantedRole)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAllGranted(String... roles) {
		Set<String> grantedRoles = authoritiesToRoles();
		List<String> requiredRoles = Arrays.asList(roles);
		if (grantedRoles.containsAll(requiredRoles)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotGranted(String... roles){
		Set<String> grantedRoles = authoritiesToRoles();
		List<String> expectNoneOfRoles = Arrays.asList(roles);
		for (String grantedRole : grantedRoles) {
			if (expectNoneOfRoles.contains(grantedRole)) {
				return false;
			}
		}
		return true;
	}

	private static Set<String> authoritiesToRoles() {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authSet = null;
		if (null == currentUser) {
			return new HashSet<String>();
		}
		authSet = currentUser.getAuthorities();
		Set<String> target = new HashSet<String>();
		for (GrantedAuthority authority : authSet) {
			if (null == authority.getAuthority()) {
				throw new IllegalArgumentException("Cannot process GrantedAuthority objects which return null from getAuthority() - attempting to process " + authority.toString());
			}
			target.add(authority.getAuthority());
		}
		return target;
	}*/
    
    
}
