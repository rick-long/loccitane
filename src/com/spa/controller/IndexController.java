package com.spa.controller;

import com.spa.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.order.Review;
import org.spa.model.user.User;
import org.spa.model.user.UserCode;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.order.ReviewService;
import org.spa.service.user.UserLoginHistoryService;
import org.spa.service.user.UserService;
import org.spa.shiro.authc.LoginAuthenticationFilter;
import org.spa.shiro.realm.UserRealm;
import org.spa.utils.I18nUtil;
import org.spa.utils.NumberUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.sales.ReviewVo;
import org.spa.vo.user.ChangePasswordVO;
import org.spa.vo.user.ForgetPasswordVO;
import org.spa.vo.user.MemberAddVO;
import org.spa.vo.user.ResetPasswordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    public UserService userService;

    @Autowired
    public PurchaseOrderService purchaseOrderService;

    @Autowired
    ReviewService reviewService;
    @Autowired
    UserLoginHistoryService userLoginHistoryService;

    @RequestMapping({"/", "/index", "/index/"})
    public String index(Model model, HttpServletRequest request, String loadingUrl) {
        model.addAttribute("user", WebThreadLocal.getUser());
        model.addAttribute("loadingUrl", loadingUrl);
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        String localeCode = "en_US";
        if (request.getSession() != null && request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) != null) {
            localeCode = request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString();
        }
        model.addAttribute("localeCode", localeCode);
        return "index";
    }

    /**
     * 认证失败错误处理
     *
     * @param request
     * @param model
     */
    private void handleError(HttpServletRequest request, Model model) {
        String exceptionClassName = (String) request.getAttribute(LoginAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (StringUtils.isNotBlank(exceptionClassName)) {
            model.addAttribute("error", "Username or Password Error!");
            logger.warn(exceptionClassName);
        }
    }

    /**
     * shiro认证过后，员工登录入口
     *
     * @param request
     * @param model
     * @return
     * @see UserRealm 查看认证的具体信息
     */
    @RequestMapping("staffLogin")
    public String staffLogin(HttpServletRequest request, Model model) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            handleError(request, model);
            return "staffLogin";
        } else {
            return "redirect:/index";
        }
    }

    /**
     * shiro认证过后，会员登录入口
     *
     * @param request
     * @param model
     * @return
     * @see UserRealm 查看认证的具体信息
     */
    @RequestMapping("memberLogin")
    public String memberLogin(HttpServletRequest request, Model model,String register) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            handleError(request, model);
            Boolean isAfterRegister = Boolean.FALSE;
            if(StringUtils.isNoneBlank(register)){
            	isAfterRegister = Boolean.TRUE;
            }
            model.addAttribute("isAfterRegister",isAfterRegister);
            return "memberLogin";
        } else {
            return "redirect:/front/index";
        }
    }



    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String resetPassword(String code, Model model, HttpServletRequest request) {
        if(StringUtils.isBlank(code)) {
            return "linkInvalid";
        }
        UserCode userCode = userCodeService.getByCode(code, CommonConstant.USER_CODE_TYPE_RESET_PASSWORD);
        if (userCode == null) {
            logger.error("Can not found code:{} in type:{}!", code, CommonConstant.USER_CODE_TYPE_RESET_PASSWORD);
            return "linkInvalid";
        }
        model.addAttribute("code", code);
        return "resetPassword";
    }
    
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public String resetPassword(@Valid ResetPasswordVO resetPasswordVO, BindingResult result, Model model) {
        String code = resetPasswordVO.getCode();
        if(StringUtils.isBlank(code)) {
            return "linkInvalid";
        }
        UserCode userCode = userCodeService.getByCode(code, CommonConstant.USER_CODE_TYPE_RESET_PASSWORD);
        if (userCode == null) {
            logger.error("Can not found code:{} in type:{}!", code, CommonConstant.USER_CODE_TYPE_RESET_PASSWORD);
            return "linkInvalid";
        }
        model.addAttribute("code", code);

        if (result.hasErrors()) {
            model.addAttribute("ajaxForm", AjaxFormHelper.error(result));
            return "resetPassword";
        }
        if (!resetPasswordVO.getPassword().equals(resetPasswordVO.getConfirmPassword())) {
            model.addAttribute("ajaxForm", AjaxFormHelper.error().addErrorFields(new ErrorField("password", "Passwords are not consistent!")));
            return "resetPassword";
        }

        userService.resetPassword(resetPasswordVO);
        return "resetPasswordSuccess";
    }

    @RequestMapping(value = "staffForgetPassword", method = RequestMethod.GET)
    public String staffForgetPassword(Model model) {
        return "staffForgetPassword";
    }

    @RequestMapping(value = "staffForgetPassword", method = RequestMethod.POST)
    public String staffForgetPassword(@Valid ForgetPasswordVO forgetPasswordVO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ajaxForm", AjaxFormHelper.error(result));
            return "staffForgetPassword";
        }

        // check email
        User user = userService.getUserByEmail(forgetPasswordVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_STAFF);
        if (user == null) {
            model.addAttribute("ajaxForm", AjaxFormHelper.error().addErrorFields(new ErrorField("email", "Email not exist!")));
            return "staffForgetPassword";
        }

        forgetPasswordVO.setAccountType(CommonConstant.USER_ACCOUNT_TYPE_STAFF);
        userService.saveResetPassword(forgetPasswordVO);
        
        model.addAttribute("ajaxForm", AjaxFormHelper.success());
        return "staffForgetPasswordSuccess";
    }

    @RequestMapping(value = "memberRegister", method = RequestMethod.GET)
    public String memberRegister(Model model) {
        model.addAttribute("shopList", shopService.getListByCompany(null,true,false,true));
        return "memberRegister";
    }

    @RequestMapping(value = "memberRegister", method = RequestMethod.POST)
    @ResponseBody
    public AjaxForm memberRegister(@Valid MemberAddVO registerVO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        }
        AjaxForm errorAjaxForm=AjaxFormHelper.error();
		//check email
		User memberCheckByEmail=userService.getUserByEmail(registerVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);

		if(memberCheckByEmail !=null){
			errorAjaxForm.addErrorFields(new ErrorField("email", I18nUtil.getMessageKey("label.errors.duplicate")));
		}
        if (registerVO.getPassword().length() < 6 || registerVO.getPassword().length() > 18) {
            errorAjaxForm.addErrorFields(new ErrorField("password", I18nUtil.getMessageKey("label.errors.password.length")+"6-18"));
        }
        // password check confirmPassword
		if(!registerVO.getPassword().equals(registerVO.getConfirmPassword())){
            errorAjaxForm.addErrorFields(new ErrorField("password", I18nUtil.getMessageKey("label.errors.password.mismatch")));
        }

		//check tel#
        if (StringUtils.isBlank(registerVO.getMobilePhone())) {
            errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.home.error")));
        } else if (!NumberUtil.isNumeric(registerVO.getMobilePhone())) {
            errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.phone.should.be.number")));
        } else if(userService.isMobileUsed(WebThreadLocal.getCompany().getId(), registerVO.getMobilePhone())) {
            errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.has.used")));
        }
        if(StringUtils.isBlank(registerVO.getAddressVO().getDistrict())){
            errorAjaxForm.addErrorFields(new ErrorField("addressVO.district", I18nUtil.getMessageKey("label.errors.address.district.required")));
        }
		if(!errorAjaxForm.getErrorFields().isEmpty()){
			return errorAjaxForm;
		}else{
			userService.saveMember(registerVO);
			return AjaxFormHelper.success();
		}
    }
    
    @RequestMapping(value = "memberRegisterSuccess", method = RequestMethod.POST)
    public String memberRegisterSuccess(Model model) {
        return "memberRegisterSuccess";
    }
    
    @RequestMapping(value = "memberForgetPassword", method = RequestMethod.GET)
    public String memberForgetPassword(Model model) {
        return "memberForgetPassword";
    }

    @RequestMapping(value = "memberForgetPassword", method = RequestMethod.POST)
    public String memberForgetPassword(@Valid ForgetPasswordVO forgetPasswordVO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ajaxForm", AjaxFormHelper.error(result));
            return "memberForgetPassword";
        }

        // check email
        User user = userService.getUserByEmail(forgetPasswordVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        if (user == null) {
            model.addAttribute("ajaxForm", AjaxFormHelper.error().addErrorFields(new ErrorField("email", "Email not exist!")));
            return "memberForgetPassword";
        }

        forgetPasswordVO.setAccountType(CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        userService.saveResetPassword(forgetPasswordVO);
        
        model.addAttribute("ajaxForm", AjaxFormHelper.success());
        return "memberForgetPasswordSuccess";
    }
    @RequestMapping(value = "activing/{code}", method = RequestMethod.GET)
    public String memberRegister(Model model, @PathVariable String code) {
        if (StringUtils.isBlank(code)) {
            return "memberLogin";
        }
        UserCode userCode = userCodeService.getByCode(code, CommonConstant.USER_CODE_TYPE_ACTIVATING);
        if (userCode == null) {
            return "memberLogin";
        }
        User user = userCode.getUser();
        user.setActiving(true);
        user.setLastUpdated(new Date());
        user.setLastUpdatedBy("activing-" + code);
        userService.saveOrUpdate(user);
        // 更新user code
        userCode.setType(CommonConstant.USER_CODE_TYPE_USED);
        userCodeService.saveOrUpdate(userCode);
        return "memberActivingSuccess";
    }

    @RequestMapping(value = "toChangePassword")
    public String changePassword(ChangePasswordVO changePasswordVO, Model model) {
        Long userId = changePasswordVO.getUserId();
        if (userId == null) {
            // 修改自己的密码
            changePasswordVO.setUserId(WebThreadLocal.getUser().getId());
        }
        model.addAttribute("changePasswordVO", changePasswordVO);
        return "changePassword";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxForm changePassword(@Valid ChangePasswordVO changePasswordVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        }
        String res = userService.updatePassword(changePasswordVO);
        if (StringUtils.isBlank(res)) {
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        } else {
            return AjaxFormHelper.error().addAlertError(res);
        }
    }

    @RequestMapping("reviewForm/{reference}")
    public String commentForm (Model model,@PathVariable String reference){
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(PurchaseOrder.class);
            detachedCriteria.add(Restrictions.eq("reference", reference));
      PurchaseOrder purchaseOrder= purchaseOrderService.get(detachedCriteria);
        if(purchaseOrder==null){
            logger.error("The Order Does Not Exist.");
            model.addAttribute("status", "not_exist");
            return "reviewSuccessfully";
        }
        List<String>productTypes=CommonConstant.productTypes;
        List<PurchaseItem> purchaseItems=new ArrayList<PurchaseItem>();
        for(PurchaseItem purchaseItem:purchaseOrder.getPurchaseItems()){
             if(productTypes.contains(purchaseItem.getProductOption().getProduct().getProdType())){
                 System.out.println(purchaseItem.getProductOption().getProduct().getProdType());
                 purchaseItems.add(purchaseItem);
              }
        }
        model.addAttribute("purchaseItems",purchaseItems);
        model.addAttribute("purchaseOrder",purchaseOrder);
        return "review";
    }
    @RequestMapping("reviewForm/save")
    public String saveCommentForm (ReviewVo reviewVo, Model model ){
        Review review=reviewService.getReviewByOrderId(reviewVo.getOrderId());
        if(review!=null){
        	model.addAttribute("status","repeat");
        }else{
	        reviewService.saveReview(reviewVo);
	        model.addAttribute("status","success");
        }
        return "reviewSuccessfully";
    }
    
    @RequestMapping(value = "showGoogleMap")
    public String showGoogleMap() {

        return "googleMap";
    }
}
