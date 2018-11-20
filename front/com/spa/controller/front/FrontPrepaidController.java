package com.spa.controller.front;

import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.EmailUtils;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.front.book.FrontVoucherVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Controller
@RequestMapping("front/prepaid")
public class FrontPrepaidController  extends BaseController{
	public static final String PRINT_VOUCHER_URL="/prepaid/vouchertemplate";
	public static final String PAYMENT_PROVIDER_PAYPAL="PayPal";
	public static final String PAYMENT_PROVIDER_ALIPAY="Alipay";
	
	@RequestMapping("voucherAddMain")
    public String voucherAddMain(Model model) {
        return "front/prepaid/voucherAddMain";
    }

	@RequestMapping("voucherToAdd")
    public String voucherToAdd(Model model,String prepaidType) {
		model.addAttribute("prepaidType", prepaidType);
		model.addAttribute("shopList",shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false,false,true));

		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.MONTH, CommonConstant.PREPAID_EXPIRED_MONTHS_V);
		model.addAttribute("expiryDate6M",calendar.getTime());
		
		User loginMember =WebThreadLocal.getUser();
		model.addAttribute("loginMemberEmail", loginMember.getEmail());
        return "front/prepaid/voucherAdd";
    }
	
	@RequestMapping("voucherAdd")
    public String voucherAdd(Model model,HttpServletRequest request,@ModelAttribute("giftVoucherForm") FrontVoucherVO frontVoucherVO,final RedirectAttributes redirectAttrs) {
		
		Long poId = frontVoucherVO.getProductOptionId();
		if(poId !=null){
			ProductOption po =productOptionService.get(poId);
			frontVoucherVO.setPo(po);
			
		}
		
		Long pickUpLocation =frontVoucherVO.getPickUpLocation();
		if(pickUpLocation !=null){
			Shop shop =shopService.get(pickUpLocation);
			frontVoucherVO.setShop(shop);
		}
		User loginMember =WebThreadLocal.getUser();
		frontVoucherVO.setMemberId(loginMember.getId());
		
		model.addAttribute("loginMemberEmail", loginMember.getEmail());
		model.addAttribute("frontVoucherVO", frontVoucherVO);
		
		// promotion code
		request.getSession().removeAttribute("paymentSendConfirm");
		if(frontVoucherVO.getPaymentName().equals(PAYMENT_PROVIDER_PAYPAL)){
			 String token = (String)request.getSession().getAttribute("token");
			 if (StringUtils.isNotEmpty(token)){
				 request.getSession().removeAttribute(token);
             }
			 redirectAttrs.addFlashAttribute("frontVoucherVO", frontVoucherVO);
			 return "redirect:/front/payment/paypal/askToken";
		}
        return "front/prepaid/voucherAddMain";
    }
	@RequestMapping("myPrepaid")
	public String myPrepaid(Model model){
		Long memberId = WebThreadLocal.getUser().getId();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Prepaid.class);
		if (memberId != null) {
			detachedCriteria.add(Restrictions.eq("user.id", memberId));
		}
		List<Prepaid> prepaidList= prepaidService.list(detachedCriteria);
		model.addAttribute("prepaidList", prepaidList);
		return "front/prepaid/myPrepaid";
	}

	@RequestMapping("test")
	 public void test(Model model,HttpServletRequest request) {
	  PrepaidTopUpTransaction prepaidTopUpTransaction =new PrepaidTopUpTransaction();
	  Prepaid prepaid=new Prepaid();
		prepaid.setName("TEST");
		prepaid.setReference("xxxxxx");
		prepaid.setPickUpLocation("test");
		prepaidTopUpTransaction.setExpiryDate( new Date());

	  prepaid.setId((long)2);
	  prepaid.setUser(WebThreadLocal.getUser());
	  prepaid.setPickUpType("friend");
	  prepaid.setAdditionalName("ggsysh");
	  prepaid.setAdditionalEmail("992575164@qq.com");
		prepaid.setAdditionalMessage("DSDSDSD");
	  prepaidTopUpTransaction.setPrepaid(prepaid);
	  prepaidService.sendVoucherNotificationEmail(prepaidTopUpTransaction,request);
	 }

}
