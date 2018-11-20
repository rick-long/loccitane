package com.spa.controller.app;

import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.spa.model.user.User;
import org.spa.service.user.UserLoginHistoryService;
import org.spa.shiro.authc.LoginToken;
import org.spa.utils.EmailUtils;
import org.spa.utils.I18nUtil;
import org.spa.utils.NumberUtil;
import org.spa.utils.Results;
import org.spa.vo.app.callback.MemberCallBackVO;
import org.spa.vo.app.callback.MemberVO;
import org.spa.vo.app.callback.VersionVO;
import org.spa.vo.app.member.AppsForgetPasswordVO;
import org.spa.vo.app.member.AppsMemberAddVO;
import org.spa.vo.user.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("apps")

//@CrossOrigin(origins = "*", maxAge = 3600)
public class AppsLoginController extends BaseController {
    @Autowired
    UserLoginHistoryService userLoginHistoryService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Results login(@RequestBody MemberVO memberAPIVO, HttpServletRequest request) {
        Results results = Results.getInstance();
        String username = memberAPIVO.getUsername();
        String password = memberAPIVO.getPassword();
        LoginToken token=new LoginToken(username, password,false, null, CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        } catch (UnknownAccountException e) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        }  catch (AccountException e) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        }
        if (!subject.isAuthenticated()) {

            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        }

        User loginer = userService.getUserByEmail(username, CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        subject.getSession().setTimeout(7 * 24 * 60 * 60 * 1000);//7天有效期
     /*
    /*   if (loginer == null || !loginer.getPassword().equals(EncryptUtil.SHA1(password))) {
        return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
    }*/
        MemberCallBackVO callback= new MemberCallBackVO(loginer);
        callback.setToken("JSESSIONID="+subject.getSession().getId());
        subject.getSession().setAttribute(CommonConstant.CURRENT_LOGIN_USER, loginer);
        userLoginHistoryService.save(loginer, request.getRemoteAddr());
        System.out.println("---cal  api/apps ---login--diva level--"+callback.getDivaLevel());
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", callback);
    }
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Results register(@RequestBody AppsMemberAddVO registerVO) {
        Results results = Results.getInstance();
       User user= userService.getUserByEmail(registerVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
       if(user!=null){
           if (StringUtils.isBlank(registerVO.getLastName())){
               return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Email duplicate value!");
           }
       }
        if (StringUtils.isBlank(registerVO.getFirstName())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "First name  required");
        }
        if (StringUtils.isBlank(registerVO.getLastName())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Last name  required");
        }
        //check email
        if(!EmailUtils.checkEmailAddressFormat(registerVO.getEmail())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Email format error");
        }
        User memberCheckByEmail=userService.getUserByEmail(registerVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        if(memberCheckByEmail !=null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "The mailbox has been registered.");
        }
        if (registerVO.getPassword().length() < 6 || registerVO.getPassword().length() > 18) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", I18nUtil.getMessageKey("The length of password should be 6-18"));
        }
        // password check confirmPassword
        if(!registerVO.getPassword().equals(registerVO.getConfirmPassword())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", I18nUtil.getMessageKey("Passwords are not consistent!"));
        }
        //check tel#
        if (StringUtils.isBlank(registerVO.getMobilePhone())) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone  required");
        } else if (!NumberUtil.isNumeric(registerVO.getMobilePhone())) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone should be number.");
        } else if(userService.isMobileUsed(1l, registerVO.getMobilePhone())) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone has been used.");
        }
        if(StringUtils.isBlank(registerVO.getShopId())){
            registerVO.setShopId("19");
        }
        AddressVO addressVO=new AddressVO();
        addressVO.setAddressExtention("HK");
        addressVO.setDistrict("HK");
        registerVO.setGender("FEMALE");
        registerVO.setDateOfBirth(new Date());
        registerVO.setAddressVO(addressVO);
        registerVO.setNotification("EMAIL");
        registerVO.setOptedOut(true);
        userService.saveAppsMember(registerVO);
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "success");
    }
    @RequestMapping(value = "forgetPwd", method = RequestMethod.POST)
    public Results forgetPassword(@RequestBody AppsForgetPasswordVO appsForgetPasswordVO, HttpServletRequest request) {
        String url=request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort();
        Results results = Results.getInstance();
        // check email
        User user = userService.getUserByEmail(appsForgetPasswordVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        if (user == null) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Email not exist!");
        }
        appsForgetPasswordVO.setAccountType(CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        userService.saveAppsResetPassword(appsForgetPasswordVO,url);
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "success");
    }
    @RequestMapping(value = "checkVersion", method = RequestMethod.POST)
    public Results checkVersion(@RequestBody VersionVO versionVO, HttpServletRequest request) {
        Results results = Results.getInstance();
        if(StringUtils.isBlank(versionVO.getOs())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "os required");
        }
        if(versionVO.getVersionCode()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Version Code required");
        }
        Map<String,Object> version=new HashMap<>();
            version =versionControlService.getVersionControlTopOne(versionVO.getOs());
            if(version!=null&&version.size()>0){
                if(versionVO.getVersionCode()>=(Double)version.get("versionCode")){
                    version.put("update",false);
                    return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", version);
                }
                version.put("update",true);
            }
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", version);
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public Results test() {


        Results results = Results.getInstance();
        return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Access without permission");
    }
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    @ResponseBody
    public Results logout() {
        Results results = Results.getInstance();
        Subject subject = SecurityUtils.getSubject();
       /* .removeClient((String) subject.getSession().getId());*/
        subject.logout();
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "success");
    }

}
