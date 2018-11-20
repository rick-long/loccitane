package com.spa.controller.prepaid;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.jxlsBean.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jxls.common.Context;
import org.spa.model.awardRedemption.AwardRedemptionTransaction;
import org.spa.model.order.StaffCommission;
import org.spa.model.payment.Payment;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.product.Category;
import org.spa.model.user.User;
import org.spa.utils.*;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.page.Page;
import org.spa.vo.prepaid.PrepaidAddVO;
import org.spa.vo.prepaid.PrepaidListVO;
import org.spa.vo.prepaid.PrepaidSimpleVO;
import org.spa.vo.sales.OrderItemVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Ivy on 2016/04/11.
 */
@Controller
@RequestMapping("prepaid")
public class PrepaidController extends BaseController {

	public static final String PRINT_VOUCHER_URL="/prepaid/vouchertemplate";
	
	@RequestMapping("toView")
	public String prepaidManagement(Model model) {
		PrepaidListVO prepaidListVO = new PrepaidListVO();
	    model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true,false));
	    
	    //get sub-category under topist category
	    List<Category> parentCategoryList=categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT,WebThreadLocal.getCompany().getId());
	    model.addAttribute("parentCategoryList", parentCategoryList);
	    model.addAttribute("prepaidListVO",prepaidListVO);
		return "prepaid/prepaidManagement";
	}
	
	@RequestMapping("list")
	public String prepaidList(Model model, PrepaidListVO prepaidListVO) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Prepaid.class);
		detachedCriteria.createAlias("prepaidTopUpTransactions", "ptt");
		if (StringUtils.isNotBlank(prepaidListVO.getIsActive())) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(prepaidListVO.getIsActive())));
		}
		//prepaid type
		if (StringUtils.isNotBlank(prepaidListVO.getPrepaidType())) {
			detachedCriteria.add(Restrictions.eq("prepaidType", prepaidListVO.getPrepaidType()));
		}
		//reference
		if (StringUtils.isNotBlank(prepaidListVO.getReference())) {
			detachedCriteria.add(Restrictions.like("reference", prepaidListVO.getReference(), MatchMode.ANYWHERE));
		}
		// treatment tree
		if (StringUtils.isNotBlank(prepaidListVO.getFromDate())) {

			detachedCriteria.add(Restrictions.ge("ptt.topUpDate", DateUtil.getFirstMinuts(DateUtil.stringToDate(prepaidListVO.getFromDate(), "yyyy-MM-dd"))));
		}
		if(StringUtils.isNotBlank(prepaidListVO.getToDate())){
			detachedCriteria.add(Restrictions.le("ptt.topUpDate", DateUtil.getLastMinuts(DateUtil.stringToDate(prepaidListVO.getToDate(), "yyyy-MM-dd"))));
		}
		if(prepaidListVO.getProductOptionId() !=null){
			detachedCriteria.add(Restrictions.eq("ptt.productOption.id", prepaidListVO.getProductOptionId()));
		}
		
		Long categoryId = prepaidListVO.getCategoryId();
		if(categoryId != null){
			List<Long> allChildren = new ArrayList<>();
	        allChildren.add(categoryId);
	        categoryService.getAllChildrenByCategory(allChildren, categoryId);
	        if (allChildren.size() > 0) {
	            detachedCriteria.add(Restrictions.in("ptt.category.id", allChildren));
	        }
		}
		
        
		//member
		if (prepaidListVO.getMemberId()!=null) {
			detachedCriteria.add(Restrictions.eq("user.id", prepaidListVO.getMemberId()));
		}
		
		//shop
		if(prepaidListVO.getShopId() !=null){
			detachedCriteria.add(Restrictions.eq("shop.id", prepaidListVO.getShopId()));
		}
//		else{
//			detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
//		}
		
		Page<Prepaid> prepaidPage = prepaidService.list(detachedCriteria, prepaidListVO.getPageNumber(), prepaidListVO.getPageSize());
		model.addAttribute("page", prepaidPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(prepaidListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "prepaid/prepaidList";
	}
	
	@RequestMapping("toAdd")
	public String toAddPrepaid(Model model,PrepaidAddVO prepaidAddVO) {
		
		initialSelectOptions(model);
		
		initialPrepaidAddVo(prepaidAddVO, model,false,false);
		
		//initial purchase date and expired date
	    model.addAttribute("purchaseDate",new Date());
		try {
			model.addAttribute("expiryDate6M",DateUtil.getLastDayAfterNumOfMonths(new Date(),CommonConstant.PREPAID_EXPIRED_MONTHS_V));
			model.addAttribute("expiryDate12M",DateUtil.getLastDayAfterNumOfMonths(new Date(),CommonConstant.PREPAID_EXPIRED_MONTHS_P));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "prepaid/prepaidAdd";
	}
	
	private AjaxForm validFormHasErrors(PrepaidAddVO prepaidAddVO){
			//any other
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			String  username=prepaidAddVO.getUsername();
			Long memberId=prepaidAddVO.getMemberId();
			if(memberId ==null || (memberId !=null && memberId.longValue()<=0)){
				DetachedCriteria dc = DetachedCriteria.forClass(User.class);
				dc.add(Restrictions.eq("username", username));
				dc.add(Restrictions.eq("enabled", true));
				List<User> memberList=userService.list(dc);
				if(memberList==null || (memberList !=null && memberList.size()==0)){
					errorAjaxForm.addErrorFields(new ErrorField("memberId", I18nUtil.getMessageKey("label.errors.memberId.is.invalidation")));
				}else{
					prepaidAddVO.setMemberId(memberList.get(0).getId());
				}
			}
			
//			Double prepaidValue=prepaidAddVO.getPrepaidValue();
//			Double remainValue=prepaidAddVO.getRemainValue();
//			if(remainValue.doubleValue()>prepaidValue.doubleValue()){
//				errorAjaxForm.addErrorFields(new ErrorField("remainValue", I18nUtil.getMessageKey("label.errors.prepaid.remainValue.morethan")));
//			}
			
			if(prepaidAddVO.getExpiryDate().before(prepaidAddVO.getPurchaseDate())){
				errorAjaxForm.addErrorFields(new ErrorField("expiryDate", I18nUtil.getMessageKey("label.errors.prepaid.expiryDate.before.purchasedate")));
			}
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
			}else{
				return null;
			}
		}
//	}
	
	@RequestMapping("calCommissionRate")
	public String calCommissionRate(PrepaidAddVO prepaidAddVO,Model model) {
			
			String endPrefix=prepaidAddVO.getPrepaidType().replace("_", " ").toLowerCase();
			Double prepaidVal=0d;
			Long id=prepaidAddVO.getId();
			if(id !=null && id.longValue()>0){
				//edit and top up
				Prepaid prepaid=prepaidService.get(prepaidAddVO.getId());
				if(prepaidAddVO.getIsTopUp() !=null && prepaidAddVO.getIsTopUp()){
					prepaidVal=prepaid.getInitValue();
				}
				model.addAttribute("reference", prepaid.getReference());
			}
			String prepaidName="";
			Double initValue=0d;
			if(prepaidAddVO.getPrepaidType().startsWith("CASH")){
				initValue=prepaidVal.doubleValue()+prepaidAddVO.getInitValue().doubleValue();
				prepaidName="$"+initValue.toString()+" "+endPrefix;
			}else{
				initValue=prepaidVal.doubleValue()+prepaidAddVO.getInitValue().doubleValue();
				prepaidName=initValue.toString()+" units "+endPrefix;
			}
			
			model.addAttribute("prepaidName", prepaidName);
			//cal commission
			String commissionRateString ="0";
			Double commissionRate=prepaidService.getCalCommissionRateForPrepaid(prepaidAddVO);
			if(commissionRate !=null && commissionRate.doubleValue() >0){
				commissionRateString = (commissionRate * 100) +" %";
			}

			model.addAttribute("commissionRate", commissionRate);
			model.addAttribute("commissionRateString", commissionRateString);

		return "prepaid/calCommissionRate";
	}
	
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addPrepaid(@Valid PrepaidAddVO prepaidAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			AjaxForm errors=validFormHasErrors(prepaidAddVO);
			if(errors ==null){
				errors=AjaxFormHelper.error();	
			}else{
				return errors;
			}
			Prepaid prepaid = prepaidService.get("reference", prepaidAddVO.getReferenceBackUp());
			if(prepaid !=null){
				errors.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
			}
			if(!errors.getErrorFields().isEmpty()){
				return errors;
				
			}else{
				prepaidService.savePrepaid(prepaidAddVO);
				
				//run FTC Journey
//				Long userId = prepaidAddVO.getMemberId();
//				Long campaignId = 7L;
//				if(prepaidAddVO.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE) || prepaidAddVO.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)){
//					campaignId = 6l;
//				}
//				Map<String, Object> parameterMap = new HashMap<>();
//		        parameterMap.put("campaignId",campaignId);
//				parameterMap.put("userId",userId);
//				RunFTCJourneyThread.getInstance(parameterMap).start();
				
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			}
		}
	}
	
	
	@RequestMapping("toTopUp")
	public String toTopUp(PrepaidAddVO prepaidAddVO,Model model) {
		
		initialSelectOptions(model);
		
		initialPrepaidAddVo(prepaidAddVO, model,true,false);
		
		prepaidAddVO.setIsTopUp(true);
		
		//initial purchase date and expired date
	    model.addAttribute("purchaseDate",new Date());
		try {
			model.addAttribute("expiryDate6M",DateUtil.getLastDayAfterNumOfMonths(new Date(),CommonConstant.PREPAID_EXPIRED_MONTHS_V));
			model.addAttribute("expiryDate12M",DateUtil.getLastDayAfterNumOfMonths(new Date(),CommonConstant.PREPAID_EXPIRED_MONTHS_P));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "prepaid/prepaidTopUp";
	}
	
	@RequestMapping("topUp")
	@ResponseBody
	public AjaxForm topUp(@Valid PrepaidAddVO prepaidAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			AjaxForm errors=validFormHasErrors(prepaidAddVO);
			if(errors ==null){
				errors=AjaxFormHelper.error();	
			}else{
				return errors;
			}
			Prepaid prepaid = prepaidService.get("reference", prepaidAddVO.getReferenceBackUp());
			if(prepaid !=null){
				errors.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
			}
			
			if(!errors.getErrorFields().isEmpty()){
				return errors;
			}else{
				Prepaid p=prepaidService.get(prepaidAddVO.getId());
				prepaidService.savePrepaidTopUpTransaction(p, prepaidAddVO,true);	
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.topup.successfully"));
			}
		}
	}
	
	@RequestMapping("toEdit")
	public String toEdit(PrepaidAddVO prepaidAddVO,Model model) {
		
		initialSelectOptions(model);
		
		initialPrepaidAddVo(prepaidAddVO, model,false,false);
		
		//initial purchase date and expired date
		Prepaid prepaid=prepaidService.get(prepaidAddVO.getId());
		
	    model.addAttribute("purchaseDate",prepaid.getPrepaidTopUpTransactions().iterator().next().getTopUpDate());
		model.addAttribute("expiryDate",prepaid.getPrepaidTopUpTransactions().iterator().next().getExpiryDate());
		
		return "prepaid/prepaidEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm edit(@Valid PrepaidAddVO prepaidAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			AjaxForm errors=validFormHasErrors(prepaidAddVO);
			if(errors ==null){
				errors=AjaxFormHelper.error();	
			}else{
				return errors;
			}
			
			Prepaid prepaid = prepaidService.get(prepaidAddVO.getId());
			if(prepaid !=null && !prepaid.getReference() .equals(prepaidAddVO.getReferenceBackUp())){
				Prepaid p = prepaidService.get("reference", prepaidAddVO.getReferenceBackUp());
				if(p !=null){
					errors.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
				}
			}
			if(!errors.getErrorFields().isEmpty()){
				return errors;
			}else{
				prepaidService.savePrepaid(prepaidAddVO);
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
			}
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public AjaxForm delete(Long prepaidId) {
		Prepaid prepaid=prepaidService.get(prepaidId);
		Set<PrepaidTopUpTransaction> ptutSet=prepaid.getPrepaidTopUpTransactions();
		for(PrepaidTopUpTransaction ptut : ptutSet){
			List<Payment > usedPrepaidPayments=paymentService.getUsedPrepaidTopUpTransactionByDate(ptut.getId(), prepaid.getFirstPrepaidTopUpTransaction().getTopUpDate());
			if(usedPrepaidPayments !=null && usedPrepaidPayments.size()>0){
				return AjaxFormHelper.error(I18nUtil.getMessageKey("label.prepaid.cant.delete"));
			}
		}
		//delete
		prepaidService.deletePrepaid(prepaidId);
		return AjaxFormHelper.success(I18nUtil.getMessageKey("label.delete.successfully"));
	}
	
	@RequestMapping("showTopUpTransaction")
	public String showTopUpTransaction(PrepaidAddVO prepaidAddVO,Model model) {
		Prepaid prepaid=prepaidService.get(prepaidAddVO.getId());
		Set<PrepaidTopUpTransaction> pttSet=prepaid.getPrepaidTopUpTransactions();
		
		List<PrepaidTopUpTransaction> pptList=new ArrayList<>(pttSet);
		model.addAttribute("pptList", pptList);
		
		model.addAttribute("prepaid", prepaid);
		
		return "prepaid/showTopUpTransaction";
	}
	
	@RequestMapping("deleteTopUpTransaction")
	@ResponseBody
	public AjaxForm deleteTopUpTransaction(Long prepaidTopUpTransactionId) {
		PrepaidTopUpTransaction ptut=prepaidTopUpTransactionService.get(prepaidTopUpTransactionId);
		List<Payment > usedPrepaidPayments=paymentService.getUsedPrepaidTopUpTransactionByDate(prepaidTopUpTransactionId, ptut.getTopUpDate());
		if(usedPrepaidPayments !=null && usedPrepaidPayments.size()>0){
			return AjaxFormHelper.error(I18nUtil.getMessageKey("label.prepaid.cant.delete"));
		}
		//delete 
		prepaidService.deletePrepaidTopUpTransaction(prepaidTopUpTransactionId);
		return AjaxFormHelper.success(I18nUtil.getMessageKey("label.delete.successfully"));
	}
	
	
	@RequestMapping("printvoucher")
	public void printVoucher(Long prepaidId,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("prepaidId",prepaidId);
		String downloadFileName = RandomUtil.generateRandomNumberWithDate("V-")+".pdf";
	    try {
	        File downloadFile = PDFUtil.convert(PRINT_VOUCHER_URL, request, map);
	        ServletUtil.download(downloadFile, downloadFileName, response);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@RequestMapping("vouchertemplate")
	public String voucherTemplate(Long prepaidId,String test,Model model) {
		Prepaid prepaid=prepaidService.get(prepaidId);

		if(prepaid.getIsRedeem()){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AwardRedemptionTransaction.class);
			detachedCriteria.add(Restrictions.eq("redeemPrepaid.id",prepaidId));
			AwardRedemptionTransaction awardRedemptionTransaction=awardRedemptionTransactionService.get(detachedCriteria);
			String[] termsList=awardRedemptionTransaction.getAwardRedemption().getValidAt().split(";");
			model.addAttribute("termsList", termsList);
		}


		model.addAttribute("prepaid", prepaid);

		return "prepaid/voucherTemplate";
	}
	
	@RequestMapping("usedHistory")
	public String usedHistory(Long prepaidId,String test,Model model) {
		Prepaid prepaid=prepaidService.get(Long.valueOf(prepaidId));
		model.addAttribute("prepaid", prepaid);
		
		List<Payment> payments=CollectionUtils.getLightWeightList();
		for(PrepaidTopUpTransaction ptt : prepaid.getPrepaidTopUpTransactions()){
			for(Payment payment : ptt.getPayments()){
				payments.add(payment);
			}
		}
		model.addAttribute("payments", payments);
		
		return "prepaid/usedHistory";
	}
	
	private void initialSelectOptions(Model model){
		
//	    model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
		model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, false));
	    
	    //get sub-category under topist category
	    List<Category> parentCategoryList=categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT,WebThreadLocal.getCompany().getId());
	    model.addAttribute("parentCategoryList", parentCategoryList);
	    
//	    //therapist
	    List<User> therapistList=userService.getAvalibleUsersByAccountTypeAndRoleRef(CommonConstant.USER_ACCOUNT_TYPE_STAFF, null);
		model.addAttribute("therapistList", therapistList);
		
		//payment method
		List<PaymentMethod> pmList=paymentMethodService.getActivePaymentMethods(WebThreadLocal.getCompany().getId(), false);
		model.addAttribute("pmList", pmList);
		//payment methods
	}
	
	private void initialPrepaidAddVo(PrepaidAddVO prepaidAddVO,Model model,Boolean isTopUp,Boolean isEditTransaction){
		//therapists
		int numberOfTherapistUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_THERAPIST_USED);
		model.addAttribute("numberOfTherapistUsed",numberOfTherapistUsed);
		
		Map<Integer,KeyAndValueVO> therapistMap=CollectionUtils.getLightWeightMap();
		
		//payment methods
	    int numberOfPMUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_PAYMENT_METHOD_USED);
	    model.addAttribute("numberOfPMUsed",numberOfPMUsed);
	    
	    Map<Integer,KeyAndValueVO> paymentMethodMap=CollectionUtils.getLightWeightMap();
	    
		if(prepaidAddVO.getId() !=null){
			//edit prepaid or top up prepaid
			Prepaid prepaid=prepaidService.get(prepaidAddVO.getId());
			if(prepaid !=null){
				prepaidAddVO.setReference(prepaid.getReference());
				prepaidAddVO.setUsername(prepaid.getUser().getUsername());
				prepaidAddVO.setMemberId(prepaid.getUser().getId());
				prepaidAddVO.setShopId(prepaid.getShop().getId());
				prepaidAddVO.setMember(prepaid.getUser());
				prepaidAddVO.setRemarks(prepaid.getRemarks());
				prepaidAddVO.setPrepaidType(prepaid.getPrepaidType());

				model.addAttribute("prepaidType",prepaid.getPrepaidType());
				model.addAttribute("packageType",prepaid.getPackageType());

				if( prepaid.getFirstPrepaidTopUpTransaction()!=null){
					if(prepaid.getFirstPrepaidTopUpTransaction().getCategory() !=null){
						prepaidAddVO.setCategoryId(prepaid.getFirstPrepaidTopUpTransaction().getCategory().getId());
					}
					if(prepaid.getFirstPrepaidTopUpTransaction().getProductOption() !=null){
						prepaidAddVO.setProductId(prepaid.getFirstPrepaidTopUpTransaction().getProductOption().getProduct().getId());
						prepaidAddVO.setProductOptionId(prepaid.getFirstPrepaidTopUpTransaction().getProductOption().getId());
						prepaidAddVO.setPo(prepaid.getFirstPrepaidTopUpTransaction().getProductOption());
					}

				}


				if(!isTopUp){
					//edit
					prepaidAddVO.setPrepaidName(prepaid.getName());
					prepaidAddVO.setInitValue(prepaid.getInitValue());
					prepaidAddVO.setRemainValue(prepaid.getRemainValue());
					prepaidAddVO.setPrepaidValue(prepaid.getPrepaidValue());

					prepaidAddVO.setIsTransfer(String.valueOf(prepaid.getIsTransfer()));
					prepaidAddVO.setIsFree(prepaid.getIsFree() !=null ? String.valueOf(prepaid.getIsFree()) : "false");

					PrepaidTopUpTransaction ptt = null;
					if(isEditTransaction){
						ptt=prepaidTopUpTransactionService.get(prepaidAddVO.getPtId());
						prepaidAddVO.setIsActive(String.valueOf(ptt.isIsActive()));
					}else{
						ptt=prepaid.getFirstPrepaidTopUpTransaction();
						prepaidAddVO.setIsActive(String.valueOf(prepaid.isIsActive()));
					}
					if(ptt !=null){
						prepaidAddVO.setExtraDiscount(ptt.getExtraDiscount());



						//therapist
						if(ptt.getFirstPurchaseItem() !=null){
							Set<StaffCommission> scSet=ptt.getFirstPurchaseItem().getStaffCommissions();
							if(scSet !=null && scSet.size()>0){
								for(StaffCommission sc : scSet){
									KeyAndValueVO kv=new KeyAndValueVO();
									kv.setKey(String.valueOf(sc.getDisplayOrder()));
									kv.setId(sc.getStaff().getId());
									therapistMap.put(Integer.valueOf(sc.getDisplayOrder()), kv);
								}
							}
						}
						model.addAttribute("therapistMap",therapistMap);
						//payment methods
						if(ptt.getPaymentsWhenBuyPrepaid() !=null){
							for(Payment payment : ptt.getPaymentsWhenBuyPrepaid()){
								KeyAndValueVO kv=new KeyAndValueVO();
								kv.setKey(String.valueOf(payment.getDisplayOrder()));
								kv.setId(payment.getPaymentMethod().getId());
								kv.setValue(String.valueOf(payment.getAmount()));
								paymentMethodMap.put(Integer.valueOf(payment.getDisplayOrder()), kv);
							}
						}
						model.addAttribute("paymentMethodMap",paymentMethodMap);
					}

				}

			}
		}
	}
	
	
    @RequestMapping("suitabledPackagesSelect")
    public String suitabledPackagesSelect(Model model, Long memberId,OrderItemVO orderItemVO,Boolean usingCashPackage) {
    	if(usingCashPackage ==null){
    		usingCashPackage =true;
    	}
    	if(memberId!=null && memberId.longValue()>0){
    		//default:EQUAL
        	String prepaidSuitableOption=PropertiesUtil.getValueByName(CommonConstant.PREPAID_SUITABLE_OPTION);
        	Set<Prepaid> suitablePackages=prepaidService.getSuitablePackagesByFilter(memberId,orderItemVO,prepaidSuitableOption,usingCashPackage);
        	model.addAttribute("suitablePackages", suitablePackages);
    	}else{
    		model.addAttribute("suitablePackages", null);
    	}
    	
    	
        return "prepaid/suitabledPackagesSelect";
    }
    
    @RequestMapping("toEditTopUpTransaction")
	public String toEditTopUpTransaction(PrepaidAddVO prepaidAddVO,Model model) {
    	
    	initialSelectOptions(model);
    	initialPrepaidAddVo(prepaidAddVO, model, false,true);
		PrepaidTopUpTransaction ptut=prepaidTopUpTransactionService.get(prepaidAddVO.getPtId());
		
		model.addAttribute("ptut", ptut);
		model.addAttribute("prepaid", ptut.getPrepaid());
		return "prepaid/editTopUpTransaction";
	}
    
 	@RequestMapping("editTopUpTransaction")
	@ResponseBody
	public AjaxForm EditTopUpTransaction(@Valid PrepaidAddVO prepaidAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			AjaxForm errors=validFormHasErrors(prepaidAddVO);
			if(errors !=null){
				return errors;
			}else{
				Prepaid prepaid=prepaidService.get(prepaidAddVO.getId());
				prepaidService.savePrepaidTopUpTransaction(prepaid, prepaidAddVO, false);
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			}
		}
	}

	@RequestMapping("export")
	public void export( PrepaidListVO prepaidListVO, HttpServletResponse response) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Prepaid.class);
		if (StringUtils.isNotBlank(prepaidListVO.getIsActive())) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(prepaidListVO.getIsActive())));
		}
		//prepaid type
		if (StringUtils.isNotBlank(prepaidListVO.getPrepaidType())) {
			detachedCriteria.add(Restrictions.eq("prepaidType", prepaidListVO.getPrepaidType()));
		}
		//reference
		if (StringUtils.isNotBlank(prepaidListVO.getReference())) {
			detachedCriteria.add(Restrictions.like("reference", prepaidListVO.getReference(), MatchMode.ANYWHERE));
		}
		// treatment tree
		if (StringUtils.isNotBlank(prepaidListVO.getFromDate())) {
			detachedCriteria.createAlias("prepaidTopUpTransactions", "ptt");
			detachedCriteria.add(Restrictions.ge("ptt.topUpDate", DateUtil.getFirstMinuts(DateUtil.stringToDate(prepaidListVO.getFromDate(), "yyyy-MM-dd"))));
		}
		if(StringUtils.isNotBlank(prepaidListVO.getToDate())){
			detachedCriteria.add(Restrictions.le("ptt.topUpDate", DateUtil.getLastMinuts(DateUtil.stringToDate(prepaidListVO.getToDate(), "yyyy-MM-dd"))));
		}
		if(prepaidListVO.getProductOptionId() !=null){
			detachedCriteria.createAlias("prepaidTopUpTransactions", "ptt");
			detachedCriteria.add(Restrictions.eq("ptt.productOption.id", prepaidListVO.getProductOptionId()));
		}
		Long categoryId = prepaidListVO.getCategoryId();
		if(categoryId != null){
			List<Long> allChildren = new ArrayList<>();
			allChildren.add(categoryId);
			categoryService.getAllChildrenByCategory(allChildren, categoryId);
			if (allChildren.size() > 0) {
				detachedCriteria.createAlias("prepaidTopUpTransactions", "ptt");
				detachedCriteria.add(Restrictions.in("ptt.category.id", allChildren));
			}
		}
		//member
		if (prepaidListVO.getMemberId()!=null) {
			detachedCriteria.add(Restrictions.eq("user.id", prepaidListVO.getMemberId()));
		}

		//shop
		if(prepaidListVO.getShopId() !=null){
			detachedCriteria.add(Restrictions.eq("shop.id", prepaidListVO.getShopId()));
		}
		else{
			detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
		}
		List<Prepaid>  items= prepaidService.list(detachedCriteria);


		Context context = new Context();
		List<PrepaidTopUpTransaction> pptList= new ArrayList<>();
		for(Prepaid prepaid: items){
			Set<PrepaidTopUpTransaction> pttSet=prepaid.getPrepaidTopUpTransactions();
			if(pttSet !=null && pttSet.size()>0){
				for (PrepaidTopUpTransaction ppt : pttSet) {
					pptList.add(ppt);
				}
			}

		}

		context.putVar("items", pptList);

		File downloadFile = ExcelUtil.write("prepaidExportTemplate.xls", context);

		ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("prepaid-Details-Report-") + ".xls", response);
	}

	@RequestMapping("toEditExpiryDate")
	public String toEditExpiryDate(PrepaidSimpleVO prepaidSimpleVO,Model model) {
		
		//initial expired date
		PrepaidTopUpTransaction ptt = prepaidTopUpTransactionService.get(prepaidSimpleVO.getPtId());
	    model.addAttribute("expiryDate",ptt.getExpiryDate());
	    model.addAttribute("ptId",ptt.getId());
	    model.addAttribute("prepaid",ptt.getPrepaid());
		return "prepaid/editExpiryDate";
	}
	@RequestMapping("editExpiryDate")
	@ResponseBody
	public AjaxForm editExpiryDate(@Valid PrepaidSimpleVO prepaidSimpleVO) {
		
		PrepaidTopUpTransaction ptt = prepaidTopUpTransactionService.get(prepaidSimpleVO.getPtId());
		ptt.setExpiryDate(prepaidSimpleVO.getExpiryDate());
		prepaidTopUpTransactionService.saveOrUpdate(ptt);
		
		return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
	}
	@RequestMapping("toImportPrepaidGif")
	public String toImportPrepaidGif(Model model, String module){
		ImportDemoVO importDemoVO = new ImportDemoVO();
		importDemoVO.setModule(module);
		model.addAttribute("importDemoVO", importDemoVO);
		return "import/importTemplate";
	}
}
