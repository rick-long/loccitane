package com.spa.controller.sales;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jxls.common.Context;
import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.model.bundle.ProductBundle;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.OutSourceTemplate;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.PDFUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.ServletUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.page.Page;
import org.spa.vo.report.SalesTemplateVO;
import org.spa.vo.sales.OrderListVO;
import org.spa.vo.sales.OrderVO;
import org.spa.vo.shop.OutSourceAttributeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.jxlsBean.ExcelUtil;
import com.spa.thread.RunFTCJourneyThread;

/**
 * Created by Ivy on 2016/04/11.
 */
@Controller
@RequestMapping("sales")
public class SalesController extends BaseController {
	
	public static final String PRINT_INVOICE_URL = "/sales/invoiceTemplate";
	public static final String USED_PREPAID_MAP = "USED_PREPAID_MAP";
	public static final String USED_PREPAID_UNIT_MAP = "USED_PREPAID_UNIT_MAP";
	
	@RequestMapping("toView")
	public String orderManagement(Model model) {
		OrderListVO orderListVO = new OrderListVO();
		model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true,true));
	    
	    DetachedCriteria dcPM = DetachedCriteria.forClass(PaymentMethod.class);
	    model.addAttribute("paymentMethodList",paymentMethodService.getActiveListByRefAndCompany(dcPM, null, WebThreadLocal.getCompany().getId()));
	  	model.addAttribute("orderListVO",orderListVO);
	    model.addAttribute("fromDate",new Date());
	    
		return "sales/salesManagement";
	}
	
	@RequestMapping("listOrder")
	public String listOrder(Model model, OrderListVO orderListVO) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PurchaseOrder.class);
		
		detachedCriteria.add(Restrictions.eq("isActive", true));
		detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
		//from date
		if (StringUtils.isNotBlank(orderListVO.getFromDate())) {
			detachedCriteria.add(Restrictions.ge("purchaseDate",
					DateUtil.stringToDate(orderListVO.getFromDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		//to date
		if (StringUtils.isNotBlank(orderListVO.getToDate())) {
			detachedCriteria.add(Restrictions.le("purchaseDate",
					DateUtil.stringToDate(orderListVO.getToDate() +" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		//reference
		if (StringUtils.isNotBlank(orderListVO.getReference())) {
			detachedCriteria.add(Restrictions.like("reference", orderListVO.getReference(), MatchMode.ANYWHERE));
		}
		//member
		if (orderListVO.getMemberId()!=null) {
			detachedCriteria.add(Restrictions.eq("user.id", orderListVO.getMemberId()));
		}
		
		//shop
		if(orderListVO.getShopId() !=null && orderListVO.getShopId().longValue()>0){
			detachedCriteria.add(Restrictions.eq("shop.id", orderListVO.getShopId()));
		}else{
			detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
		}
		
		// payment method id
		if(orderListVO.getPaymentMethodId() !=null && orderListVO.getPaymentMethodId().longValue()>0){
			PaymentMethod pm=paymentMethodService.get(orderListVO.getPaymentMethodId());
			if(pm.getReference().equals("PACKAGE") || pm.getReference().equals("VOUCHER")){
				detachedCriteria.createAlias("purchaseItems", "pi");
				detachedCriteria.createAlias("pi.payments", "pay");
			}else{
				detachedCriteria.createAlias("payments", "pay");
			}
			detachedCriteria.add(Restrictions.eq("pay.paymentMethod.id", orderListVO.getPaymentMethodId()));
		}
		// staff
		if(orderListVO.getStaffId()!=null && orderListVO.getStaffId().longValue()>0){
			detachedCriteria.createAlias("purchaseItems", "pi");
			detachedCriteria.createAlias("pi.staffCommissions", "sc");
			detachedCriteria.add(Restrictions.eq("sc.staff.id", orderListVO.getStaffId()));
		}
		
		detachedCriteria.addOrder(Order.asc("purchaseDate"));
		
		List<PurchaseOrder> orderList=purchaseOrderService.list(detachedCriteria);
		Double totalCommission=0d;
		Double grossRevenue=0d;
		Double totalRevenue=0d;
        Map<String,Double> paymentAmount=new HashMap<String,Double>();
        Map<String,Object> returnMap =purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByOrderList(orderList,orderListVO.getStaffId());
        if(returnMap !=null && returnMap.size()>0){
        	totalCommission = (Double) returnMap.get("totalCommission");
        	grossRevenue = (Double) returnMap.get("grossRevenue");
        	paymentAmount = (Map<String,Double>)returnMap.get("paymentAmount");
        	totalRevenue = (Double) returnMap.get("totalRevenue");
        }
		model.addAttribute("totalCommission", totalCommission);
		model.addAttribute("grossRevenue", grossRevenue);
		model.addAttribute("totalRevenue", totalRevenue);
		model.addAttribute("paymentAmount", paymentAmount);
		
		Page<PurchaseOrder> orderPage = purchaseOrderService.list(detachedCriteria, orderListVO.getPageNumber(), orderListVO.getPageSize());
		model.addAttribute("page", orderPage);
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(orderListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "sales/salesList";
	}
	
	@RequestMapping("showItemDetails")
	public String showItemDetails(Long purchaseOrderId,Model model) {
		PurchaseOrder purchaseOrder=purchaseOrderService.get(purchaseOrderId);
		Set<PurchaseItem> itemSet=purchaseOrder.getPurchaseItems();
		
		List<PurchaseItem> itemList=new ArrayList<>(itemSet);
		model.addAttribute("itemList", itemList);
		
		return "sales/showItemDetail";
	}
	
	@RequestMapping("salesToCheckOut")
	public String salesToCheckOut(Model model,OrderVO orderVO,HttpSession httpSession) {
		
		httpSession.removeAttribute(USED_PREPAID_MAP);
		httpSession.removeAttribute(USED_PREPAID_UNIT_MAP);
		
	    model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
	  	
	    model.addAttribute("purchaseDate",new Date());
	    
	    //therapist
	    int numberOfTherapistUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_THERAPIST_USED);
		model.addAttribute("numberOfTherapistUsed",numberOfTherapistUsed);
	    
		//payment methods
	    int numberOfPMUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_PAYMENT_METHOD_USED);
	    model.addAttribute("numberOfPMUsed",numberOfPMUsed);
	    
	    List<PaymentMethod> pmList=paymentMethodService.getActivePaymentMethods(WebThreadLocal.getCompany().getId(), false);
	    model.addAttribute("pmList", pmList);
	  	
	    model.addAttribute("index",0);
	   
	   return "sales/salesToCheckOut";
	}
	
	@RequestMapping("bookToCheckout")
	public String bookToCheckout(OrderVO orderVO,Model model,HttpSession httpSession) {

		httpSession.removeAttribute(USED_PREPAID_MAP);
		httpSession.removeAttribute(USED_PREPAID_UNIT_MAP);
		
		Book book=bookService.get(orderVO.getBookId());
		List<BookItem> commonBookItems = new ArrayList<BookItem>();
		Map<ProductBundle,List<BookItem>> bundles = new HashMap<ProductBundle,List<BookItem>>();
		for(BookItem bi : book.getBookItems()){
			if(bi.getBundleId() !=null){
				ProductBundle pb = bundleService.get(bi.getBundleId());
				if(bundles.get(pb) !=null){
					bundles.get(pb).add(bi);
				}else{
					List<BookItem> biList = new ArrayList<BookItem>(); 
					biList.add(bi);
					bundles.put(pb, biList);
				}
			}else{
				commonBookItems.add(bi);
			}
		}
		model.addAttribute("book", book);
		model.addAttribute("commonBookItems", commonBookItems);
		model.addAttribute("bundles", bundles);
		if (orderVO.getCheckOutStatus() != null) {
			model.addAttribute("isNoShowStatus", orderVO.getCheckOutStatus());
		}
		
		//therapist
	    int numberOfTherapistUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_THERAPIST_USED);
		model.addAttribute("numberOfTherapistUsed",numberOfTherapistUsed);
		
		//payment methods
	    int numberOfPMUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_PAYMENT_METHOD_USED);
	    model.addAttribute("numberOfPMUsed",numberOfPMUsed);
	    
	    List<PaymentMethod> pmList=paymentMethodService.getActivePaymentMethods(WebThreadLocal.getCompany().getId(), false);
	    model.addAttribute("pmList", pmList);
	    model.addAttribute(orderVO);

	   return "sales/bookToCheckout";
	}
	
	@RequestMapping("bookItemToCheckout")
	public String bookItemToCheckout(Long bookItemId,OrderVO orderVO,Model model,HttpSession httpSession) {
		
		httpSession.removeAttribute(USED_PREPAID_MAP);
		httpSession.removeAttribute(USED_PREPAID_UNIT_MAP);
		
		BookItem bookItem=bookItemService.get(bookItemId);
		model.addAttribute("bookItem", bookItem);
		model.addAttribute("book", bookItem.getBook());
		
		//payment methods
	    int numberOfPMUsed=PropertiesUtil.getIntegerValueByName(CommonConstant.NUM_OF_PAYMENT_METHOD_USED);
	    model.addAttribute("numberOfPMUsed",numberOfPMUsed);
	    
	    List<PaymentMethod> pmList=paymentMethodService.getActivePaymentMethods(WebThreadLocal.getCompany().getId(), false);
	    model.addAttribute("pmList", pmList);
	  
		return "sales/bookItemToCheckout";
	}
	
	@RequestMapping("addBundleCartItems")
	public String addBundleCartItems(OrderVO orderVO,Model model,HttpSession httpSession) {
		
		model.addAttribute("orderVO",purchaseOrderService.checkBundleOrder(orderVO,0,httpSession));
		model.addAttribute("shopId", orderVO.getShopId());
		
		return "sales/cartItemsForBundle";
	}
	@RequestMapping("addCartItems")
	public String addCartItems(OrderVO orderVO,int idxId,Model model,HttpSession httpSession) {
		
		model.addAttribute("orderVO",purchaseOrderService.checkOrderItem(orderVO,idxId,httpSession));
		model.addAttribute("shopId", orderVO.getShopId());
		return "sales/cartItems";
	}
	
	@RequestMapping("addTipsItems")
	public String addTipsItems(Double amount,Long staffId,int idxId,Model model) {
		model.addAttribute("amount", amount);
		if(staffId !=null){
			User staff=userService.get(staffId);
			model.addAttribute("staffName", staff.getDisplayName());
		}
		model.addAttribute("staffId", staffId);
		model.addAttribute("idxId", idxId);
		return "sales/cartItemsForTips";
	}
	
	@RequestMapping("checkout")
	@ResponseBody
	public AjaxForm checkout(@Valid OrderVO orderVO,BindingResult result,HttpSession httpSession, HttpServletRequest request,HttpServletResponse response) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			//
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			if((orderVO.getItemVOs()==null || (orderVO.getItemVOs()!=null && orderVO.getItemVOs().size()==0))
					&& orderVO.getTipsItemVOs()==null || (orderVO.getTipsItemVOs()!=null && orderVO.getTipsItemVOs().size()==0)){

				errorAjaxForm.addErrorFields(new ErrorField("Cart items ", I18nUtil.getMessageKey("label.empty")));
			}
			Double finalAmount = orderVO.getSubTotal();
			Double totalPayment=0d;
			for(KeyAndValueVO kv : orderVO.getPaymentMethods()){
				String value=kv.getValue();
				if(StringUtils.isNotBlank(value)){
					totalPayment +=Double.valueOf(value);
				}
			}
			if(finalAmount.longValue() != totalPayment.longValue()){
				errorAjaxForm.addErrorFields(new ErrorField("Payment Amount "," is not equal Total Amount of shopping cart!"));
			}
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
			}else{

				Long orderId=purchaseOrderService.savePurchaseOrder(orderVO);
				//check whether need to run rating schedule
				Boolean isRunningRatingSchedule = PropertiesUtil.getBooleanValueByName("is_running_rating_schedule");
				if(isRunningRatingSchedule !=null && isRunningRatingSchedule){
					PurchaseOrder purchaseOrder=purchaseOrderService.get(orderId);
					if(purchaseOrder.getUser().isMember()){
						purchaseOrderService.sendThankYouEmail(purchaseOrder);
					}
				}

				//run FTC Journey
//				Long userId = orderVO.getMemberId();
//				Long campaignId =7L;
//				Map<String, Object> parameterMap = new HashMap<>();
//		        parameterMap.put("campaignId",campaignId);
//				parameterMap.put("userId",userId);
//				RunFTCJourneyThread.getInstance(parameterMap).start();
//				httpSession.removeAttribute(USED_PREPAID_MAP);
//				httpSession.removeAttribute(USED_PREPAID_UNIT_MAP);

                if(orderVO.getPrintInvoice()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("id",orderId);
                    String downloadFileName = RandomUtil.generateRandomNumberWithDate("INVOICE-")+".pdf";
                    try {
                        File downloadFile = PDFUtil.convert(PRINT_INVOICE_URL, request, map);
                        ServletUtil.download(downloadFile, downloadFileName, response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			}
		}
	}
	
	@RequestMapping("print")
	public void print(Long id,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("id",id);
		String downloadFileName = RandomUtil.generateRandomNumberWithDate("INVOICE-")+".pdf";
	    try {
	        File downloadFile = PDFUtil.convert(PRINT_INVOICE_URL, request, map);
	        ServletUtil.download(downloadFile, downloadFileName, response);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
					 
	@RequestMapping("invoiceTemplate")
	public String invoiceTemplate(Long id,String test,Model model) {
		PurchaseOrder po=purchaseOrderService.get(id);
		model.addAttribute("po", po);
		model.addAttribute("staff", WebThreadLocal.getUser().getFullName());
		
		return "sales/invoiceTemplate";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public AjaxForm delete(Long id) {
		if(id !=null){
			try {
				purchaseOrderService.removePurchaseOrder(id);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.delete.failed"));
			}
		}else{
			return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
		}
		return AjaxFormHelper.success(I18nUtil.getMessageKey("label.delete.successfully"));
	}
	
	@RequestMapping("removeItem")
	public void removeItem(String voucherRef,Long packageId,Double prepaidPaidAmt,HttpSession httpSession) {
		Prepaid prepaid =null;
		if(StringUtils.isNoneBlank(voucherRef)){
			prepaid = prepaidService.get("reference", voucherRef);
		}else if(packageId !=null){
			prepaid = prepaidService.get(packageId);
		}
		
		if(prepaidPaidAmt.doubleValue()>0){
			if(prepaid.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE) || prepaid.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER)){
				Map<String,Double> usedPrepaidMap = (Map<String,Double>) httpSession.getAttribute(USED_PREPAID_MAP);
				Double usedPrepaidForSession = usedPrepaidMap.get(prepaid.getReference());
				usedPrepaidForSession = usedPrepaidForSession - prepaidPaidAmt;
				usedPrepaidMap.put(prepaid.getReference(), usedPrepaidForSession);
			}else{
				Map<String,Double> usedPrepaidUnitMap = (Map<String,Double>) httpSession.getAttribute(USED_PREPAID_UNIT_MAP);
				Double usedPrepaidUnitForSession = usedPrepaidUnitMap.get(prepaid.getReference());
				usedPrepaidUnitForSession = usedPrepaidUnitForSession - 1;
				usedPrepaidUnitMap.put(prepaid.getReference(), usedPrepaidUnitForSession);
			}
			
		}
	}

	@RequestMapping("outSourceAttribute")
    public String outSourceAttribute(Model model, OrderVO orderVO) {
		Long shopId=orderVO.getShopId();
		Shop shop=shopService.get(shopId);
        OutSourceTemplate outSourceTemplate = shop.getOutSourceTemplate();
        
        
        OutSourceAttributeVO[] attributeVOs = outSourceTemplate.getOutSourceAttributeKeys().stream().map(attribute -> {
        	OutSourceAttributeVO attributeVO = new OutSourceAttributeVO();
            attributeVO.setOutSourceAttributeKeyId(attribute.getId());
            attributeVO.setReference(attribute.getReference());
            attributeVO.setName(attribute.getName());
            attributeVO.setDescription(attribute.getDescription());
            return attributeVO;
        }).toArray(size -> new OutSourceAttributeVO[size]);
        
        orderVO.setOutSourceAttributeVOs(attributeVOs);

        model.addAttribute("orderVO", orderVO);
        
        return "sales/outSourceAttribute";
    }
	
	@RequestMapping("export")
	public void export(OrderListVO orderListVO,HttpServletResponse response)throws ParseException {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PurchaseItem.class);
		detachedCriteria.createAlias("purchaseOrder", "order");
		detachedCriteria.add(Restrictions.eq("order.company.id", WebThreadLocal.getCompany().getId()));
		detachedCriteria.add(Restrictions.eq("order.isActive", true));
		//from date
		if (StringUtils.isNotBlank(orderListVO.getFromDate())) {
			
			detachedCriteria.add(Restrictions.ge("order.purchaseDate", 
					DateUtil.stringToDate(orderListVO.getFromDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		}
		//to date
		if (StringUtils.isNotBlank(orderListVO.getToDate())) {
			detachedCriteria.add(Restrictions.le("order.purchaseDate",
					DateUtil.stringToDate(orderListVO.getToDate() +" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		//reference
		if (StringUtils.isNotBlank(orderListVO.getReference())) {
			detachedCriteria.add(Restrictions.like("order.reference", orderListVO.getReference(), MatchMode.ANYWHERE));
		}
		//member
		if (orderListVO.getMemberId()!=null) {
			detachedCriteria.add(Restrictions.eq("order.user.id", orderListVO.getMemberId()));
		}
		
		//shop
		if(orderListVO.getShopId() !=null && orderListVO.getShopId().longValue()>0){
			detachedCriteria.add(Restrictions.eq("order.shop.id", orderListVO.getShopId()));
		}else{
			detachedCriteria.add(Restrictions.in("order.shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
		}
		
		// payment method id
		if(orderListVO.getPaymentMethodId() !=null && orderListVO.getPaymentMethodId().longValue()>0){
			PaymentMethod pm=paymentMethodService.get(orderListVO.getPaymentMethodId());
			if(pm.getReference().equals("PACKAGE") || pm.getReference().equals("VOUCHER")){
				detachedCriteria.createAlias("payments", "pay");
			}else{
				detachedCriteria.createAlias("order.payments", "pay");
			}
			detachedCriteria.add(Restrictions.eq("pay.paymentMethod.id", orderListVO.getPaymentMethodId()));
		}
		// staff
		if(orderListVO.getStaffId()!=null && orderListVO.getStaffId().longValue()>0){
			detachedCriteria.createAlias("staffCommissions", "sc");
			detachedCriteria.add(Restrictions.eq("sc.staff.id", orderListVO.getStaffId()));
		}
		
		detachedCriteria.addOrder(Order.asc("order.purchaseDate"));
		
		List<PurchaseItem> items=purchaseItemService.list(detachedCriteria);

		List<SalesTemplateVO> voList = new ArrayList<SalesTemplateVO>();
		SalesTemplateVO vo =null;
		 System.out.println(" export items size ---"+items.size()+"---"+new Date());

		PurchaseOrder po =null;
		ProductOption pro =null;
		//int i=1;
		for(PurchaseItem item : items ){
			po = item.getPurchaseOrder();
			pro = item.getProductOption();
			vo = new SalesTemplateVO();
			vo.setReference(po.getReference());
			vo.setShopName(po.getShop().getName());
			vo.setDate(DateUtil.dateToString(po.getPurchaseDate(), "yyyy-MM-dd"));
			vo.setClientName(po.getUser().getFullName());
			vo.setHotelGuest(po.getHotelGuest());
			vo.setEmail(po.getUser().getEmail());
			vo.setProduct(item.getPurchaseItemNames3());
			vo.setTherapist(item.getTherapistAndCommission2());

			vo.setQty(item.getQty());
			vo.setItemAmount(item.getAmount());
			vo.setEffectiveValue(item.getEffectiveValue());
			vo.setDiscount(item.getDiscountValue());
			vo.setPackageVal(item.getPackagePaid());
			vo.setVoucherVal(item.getVoucherPaid());
    //    	vo.setFullPrice(pro !=null ? pro.getOriginalPrice() : 0d);
			vo.setCostOfProduct(pro !=null ? pro.getCostOfProduct() :0d);
			vo.setPayment(item.getPurchaseOrder().getPaymentMethodsAndAmount2());

			vo.setRequested(item.getIsRequested() ? "Y" :"N");
      	//System.out.println("---i----"+i+"---"+new Date());
			voList.add(vo);
      // 	i++;
		}
       System.out.println(" export volist size ---"+voList.size()+"---"+new Date());
		
		Context context = new Context();
				context.putVar("volist", voList);
        File downloadFile = ExcelUtil.write("salesExportTemplate.xls", context);
        
        ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Sales-Export-") + ".xls", response);
	}
}
