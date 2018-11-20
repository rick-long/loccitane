package com.spa.controller.front;

import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.awardRedemption.AwardRedemption;
import org.spa.model.awardRedemption.AwardRedemptionTransaction;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.points.LoyaltyPointsTransaction;
import org.spa.model.shop.Address;
import org.spa.model.shop.Phone;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.*;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.loyalty.AwardRedeemVO;
import org.spa.vo.user.ChangePasswordVO;
import org.spa.vo.user.MemberEditVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("front")
public class FrontController extends BaseController{

    @RequestMapping({"/", "/index", "/index/"})
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("user", WebThreadLocal.getUser());
        return "front/index";
    }
    
    @RequestMapping({"/profile"})
    public String profile(Model model, HttpServletRequest request) {
    	
    	List<Shop> shopList=shopService.getListByCompany(null,true,false,true);
		model.addAttribute("shopList", shopList);
		
    	User member = userService.get(WebThreadLocal.getUser().getId());
        model.addAttribute("member", member);
        
        if(member !=null && member.getAddresses() !=null && member.getAddresses().size()>0){
			Address addr = member.getAddresses().iterator().next();
			model.addAttribute("address", addr.getAddressExtention());
			model.addAttribute("district", addr.getDistrict());
			model.addAttribute("country", addr.getCountry());
		}
		if(member !=null && member.getPhones() !=null && member.getPhones().size()>0){
			Iterator<Phone> it=member.getPhones().iterator();
			while(it.hasNext()){
				Phone phone=it.next();
				if(phone.getType().equals(CommonConstant.PHONE_TYPE_HOME)){
					model.addAttribute("homePhone", phone.getNumber());
				}else if(phone.getType().equals(CommonConstant.PHONE_TYPE_MOBILE)){
					model.addAttribute("mobilePhone",phone.getNumber());
				}
			}
		}
        return "front/profile/profile";
    }
    
    @RequestMapping({"/saveProfile"})
    @ResponseBody
    public AjaxForm saveProfile(@Valid MemberEditVO registerVO, BindingResult result, Model model) {
    	
    	if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        }


    	User member=userService.get(Long.valueOf(registerVO.getId()));

		AjaxForm errorAjaxForm=AjaxFormHelper.error();
		//check email
		if(!member.getEmail().equals(registerVO.getEmail())){
			User memberCheckByEmail=userService.getUserByEmail(registerVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
			if(memberCheckByEmail !=null){
				errorAjaxForm.addErrorFields(new ErrorField("email", I18nUtil.getMessageKey("label.errors.duplicate")));
			}
		}

		if (StringUtils.isNotBlank(registerVO.getNewPassword())&&StringUtils.isNotBlank(registerVO.getConfirmPassword())&&registerVO.getNewPassword().equals(registerVO.getConfirmPassword())){
			registerVO.setPassword(EncryptUtil.SHA1(registerVO.getNewPassword()));
		}else{
			registerVO.setPassword(member.getPassword());
		}
		//check tel#
        if (StringUtils.isBlank(registerVO.getMobilePhone())) {
            errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.home.error")));
        } else if (!NumberUtil.isNumeric(registerVO.getMobilePhone())) {
            errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.phone.should.be.number")));
        } else if(userService.isMobileUsed(WebThreadLocal.getCompany().getId(), registerVO.getMobilePhone(),registerVO.getId())) {
            errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.has.used")));
        }
        
        if(StringUtils.isBlank(registerVO.getAddressVO().getDistrict())){
            errorAjaxForm.addErrorFields(new ErrorField("addressVO.district", I18nUtil.getMessageKey("label.errors.address.district.required")));
        }
		if(!errorAjaxForm.getErrorFields().isEmpty()){
			return errorAjaxForm;
		}else{
			userService.updateMember(registerVO);
			return AjaxFormHelper.success();
		}
    }
    @RequestMapping("toMyLoyalty")
   	public String toMyLoyalty(AwardRedeemVO awardRedeemVO, Model model, HttpSession httpSession){
		String redeemCode=UUID.randomUUID().toString();
		httpSession.setAttribute("redeemCode", redeemCode);
    	User member = WebThreadLocal.getUser();
   		awardRedeemVO.setMemberId(member.getId());
		DetachedCriteria ulldc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
		ulldc.add(Restrictions.eq("user.id", member.getId()));
        Date llexpiryDate=null;
		List<UserLoyaltyLevel> ullListTrue=userLoyaltyLevelService.list(ulldc);
       if (ullListTrue!=null&&ullListTrue.size()>0){
		llexpiryDate=ullListTrue.get(0).getExpiryDate();
		}
		model.addAttribute("llexpiryDate",llexpiryDate);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LoyaltyPointsTransaction.class);
		detachedCriteria.add(Restrictions.eq("user.id", WebThreadLocal.getUser().getId()));
		List<LoyaltyPointsTransaction> lptList = loyaltyPointsTransactionService.list(detachedCriteria);
		model.addAttribute("lptList",lptList);
		DetachedCriteria orderCriteria = DetachedCriteria.forClass(PurchaseOrder.class);
		orderCriteria.add(Restrictions.eq("user.id", WebThreadLocal.getUser().getId()));
		List<PurchaseOrder> orderList = purchaseOrderService.list(orderCriteria);
		model.addAttribute("orderList",orderList);
   		Date now=new Date();
   		model.addAttribute("member",member);
		model.addAttribute("now",now);
   		DetachedCriteria dc = DetachedCriteria.forClass(AwardRedemption.class);
   	    model.addAttribute("awardRedemptions", awardRedemptionService.getActiveListByRefAndCompany(dc, null,null));
   	    DetachedCriteria dct = DetachedCriteria.forClass(AwardRedemptionTransaction.class);
   	    dct.add(Restrictions.eq("redeemMember.id", member.getId()));
   	    dct.add(Restrictions.eq("isActive", true));
   	    model.addAttribute("awardRedemptionTransactions", awardRedemptionTransactionService.list(dct));
		model.addAttribute("redeemCode", redeemCode);
   		return "front/myLoyalty/myLoyalty";
   	}
   	
   	@RequestMapping("redeem")
	@ResponseBody
   	public Map<String,String> redeem(@Valid AwardRedeemVO awardRedeemVO,Model model,HttpServletRequest request,HttpSession httpSession) {
		Map<String,String> maps=new HashMap<String, String>();
		String redeemCode=(String) httpSession.getAttribute("redeemCode");
		if(redeemCode.equals(awardRedeemVO.getRedeemCode())){
		try {
			   httpSession.removeAttribute("redeemCode");
				Long prepaidId=awardRedemptionTransactionService.saveAwardRedemptionTransaction(awardRedeemVO);
				if(prepaidId!=null){
					prepaidService.sendVoucherNotificationEmail(prepaidId,request);
				}
				maps.put("status","succeed");


		} catch (Exception e) {
			e.printStackTrace();
			maps.put("status","no");
		}
		}
		return maps;
   	}
    @RequestMapping("toChangePwd")
    public String toChangePwd(ChangePasswordVO changePasswordVO, Model model) {
    	User member = userService.get(WebThreadLocal.getUser().getId());
        if (member != null) {
            // 修改自己的密码
            changePasswordVO.setUserId(member.getId());
            changePasswordVO.setCheckPassword(false);
        }
        model.addAttribute("changePasswordVO", changePasswordVO);
        return "front/changePwd";
    }

    @RequestMapping("changePwd")
    @ResponseBody
    public AjaxForm changePwd(@Valid ChangePasswordVO changePasswordVO, BindingResult result) {
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
    
    @RequestMapping("download/passbook")
    public void downloadpassbook(String username,HttpServletResponse response) {
    	
    	 String url = "http://ssl2.senseoftouch.com.hk/passbook/passbook?userName="+username;
//    	 String url = "http://local.ssl2.senseoftouch.com.hk:8080/SSLPassBook/passbook?userName="+username;
         HttpClient httpClient = new HttpClient();
         httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
         PostMethod postMethod = new PostMethod(url) {
             public String getRequestCharSet() {
                 return "UTF-8";
             }
         };
         try {
			httpClient.executeMethod(postMethod);
			Header header = postMethod.getResponseHeader("Content-disposition");
			if(header ==null){
				return;
			}
			String[] fileNameString= header.getValue().split("=");
			String fileNames=fileNameString[1];
			String fileName =fileNames.substring(1, fileNames.length()-1);
			System.out.println("------downloadpassbook  fileName-------"+fileName);
//			File file =new File("/Users/liusiping/Documents/workspace/ssl2_wp/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SSLPassBook/WEB-INF/pass/"+fileName);
			File file =new File("/opt/apache-tomcat-8.5.9/webapps/passbook/WEB-INF/pass/"+fileName);
			ServletUtil.download(file, fileName, response);
		
//			
			postMethod.releaseConnection();
			
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}
