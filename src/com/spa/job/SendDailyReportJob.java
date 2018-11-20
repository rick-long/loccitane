package com.spa.job;
import com.spa.constant.CommonConstant;
import com.spa.email.EmailAddress;
import com.spa.thread.DailyReportThread;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.company.Company;
import org.spa.model.order.PurchaseItem;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.shop.Shop;
import org.spa.model.shop.StoreEmployee;
import org.spa.model.user.User;
import org.spa.service.order.PurchaseItemService;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.payment.PaymentMethodService;
import org.spa.service.shop.ShopService;
import org.spa.service.shop.StoreEmployeeService;
import org.spa.service.user.UserService;
import org.spa.vo.report.SalesDetailsSummaryVO;
import org.spa.vo.report.SalesSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.*;

public class SendDailyReportJob extends OpenSessionQuartzJobBean{
	@Autowired
	UserService userService;
	@Autowired
	ShopService shopService;
	@Autowired
	StoreEmployeeService storeEmployeeService;
	@Autowired
	PurchaseItemService purchaseItemService;
	
	@Autowired
	PurchaseOrderService purchaseOrderService;
	
	@Autowired
	PaymentMethodService paymentMethodService;
	
	private static final String prefix ="DAILY_";
	
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException{
//		String fromDate = "";//开始时间
//		String toDate = "";//结束时间
//		Calendar fromCalendar = new GregorianCalendar();
//		Date from = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
//		toDate = sdf.format(from);
//		fromCalendar.setTime(from);
//		fromCalendar.add(Calendar.DATE, -1);
//		fromDate = sdf.format(fromCalendar.getTime());
		
		String fromDate = "2018-03-13";//开始时间
		String toDate = "2018-03-14";//结束时间
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoreEmployee.class);
		detachedCriteria.add(Restrictions.eq("isActive", true));
		detachedCriteria.add(Restrictions.like("type",prefix, MatchMode.ANYWHERE));
		List<StoreEmployee> storeEmployees = storeEmployeeService.list(detachedCriteria);
		if(storeEmployees ==null || storeEmployees.size()<=0){
			return;
		}
		
		//发送email
		Company company = new Company();
		company.setId(1l);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("company", company);
		
		List<EmailAddress> emailAddresses = null;
		for (StoreEmployee storeEmployee : storeEmployees) {
			
			Shop shop = storeEmployee.getShop();
			if(shop == null){
				continue;
			}
			if(storeEmployee.getType().equals(CommonConstant.AUTO_REPORT_TYPE_DAILY_SHOP)){
				SalesSearchVO salesSearchVO = new SalesSearchVO();
				salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
				salesSearchVO.setCompanyId(1L);
				salesSearchVO.setRootCategoryId(1L);
				salesSearchVO.setIsSearchByJob(true);
				if (!shop.getReference().equals("HEAD_OFFICE")) {
					salesSearchVO.setShopId(shop.getId());
				}
				salesSearchVO.setFromDate(fromDate);
				salesSearchVO.setToDate(toDate);
				
				// daily total sales
				Double totalRevenue =0d;
				Map<String,Double> paymentAmount=new HashMap<String,Double>();
		        Map<String,Object> returnMap =purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);
		        if(returnMap !=null && returnMap.size()>0){
		        	paymentAmount = (Map<String,Double>)returnMap.get("paymentAmount");
		        	totalRevenue = (Double) returnMap.get("totalRevenue");
		        }
				
				DetachedCriteria dcPM = DetachedCriteria.forClass(PaymentMethod.class);
				dcPM.add(Restrictions.eq("isActive",true));
				dcPM.addOrder(Order.desc("displayOrder"));
				List<PaymentMethod> paymentMethodList = paymentMethodService.list(dcPM);
				Double totalSales =0d;
				Double sumIncome=0d;
				Double sumPrepaid=0d;
				Double sumOtherPrepaid=0d;
				Double cash=0d;
				Double ae=0d;
				Double visa=0d;
				Double uniompay=0d;
				Double eps=0d;
				
				Double packages=0d;
				Double voucher=0d;
				Double hotels=0d;
				Double wings=0d;
				for(PaymentMethod pm : paymentMethodList){
					Double amount = paymentAmount.get(pm.getName());
					if(amount ==null){
						amount = 0d;
					}
					totalSales+=amount;
					if(pm.getReference().equals("CASH") || pm.getReference().equals("CREDIT_CARD") ||pm.getReference().equals("EPS")||pm.getReference().equals("AE")||pm.getReference().equals("UNIONPAY")){
//						imcomeMap.put(pm.getName(), amount);
						if(pm.getReference().equals("CASH")){
							cash += amount;
						}else if(pm.getReference().equals("CREDIT_CARD")){
							visa += amount;
						}else if(pm.getReference().equals("EPS")){
							eps += amount;
						}else if(pm.getReference().equals("AE")){
							ae += amount;
						}else if(pm.getReference().equals("UNIONPAY")){
							uniompay += amount;
						}
						sumIncome +=amount;
					}
					if(pm.getReference().equals("PACKAGE") || pm.getReference().equals("VOUCHER")){

						if(pm.getReference().equals("PACKAGE")){
							packages += amount;
						}else if(pm.getReference().equals("VOUCHER")){
							voucher += amount;
						}
						sumPrepaid +=amount;
					}
					if(pm.getReference().equals("WINGS_II_GUEST")||pm.getReference().equals("HOTEL_GUEST")){
						
						if(pm.getReference().equals("WINGS_II_GUEST")){
							wings += amount;
						}else if(pm.getReference().equals("HOTEL_GUEST")){
							hotels += amount;
						}
						sumOtherPrepaid +=amount;
					}
				}
				parameterMap.put("sumIncome", sumIncome);
				parameterMap.put("sumPrepaid", sumPrepaid);
				parameterMap.put("sumOtherPrepaid", sumOtherPrepaid);
				parameterMap.put("totalSales", totalSales);
				parameterMap.put("totalRevenue", totalRevenue);
				
				parameterMap.put("cash", cash);
				parameterMap.put("visa", visa);
				parameterMap.put("eps", eps);
				parameterMap.put("ae", ae);
				parameterMap.put("uniompay", uniompay);
				
				parameterMap.put("packages", packages);
				parameterMap.put("voucher", voucher);
				parameterMap.put("wings", wings);
				parameterMap.put("hotels", hotels);
				
				parameterMap.put("others", totalSales.doubleValue() - sumIncome.doubleValue() - sumPrepaid.doubleValue()-sumOtherPrepaid.doubleValue());
				//daily breakdown
				List<PurchaseItem> totalPurchaseItems = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
				List<SalesDetailsSummaryVO> summaryList =purchaseItemService.getSalesDetailsSummaryVOListV3(totalPurchaseItems);
				parameterMap.put("summaryList", summaryList);
				
				//number of client
				
				Long numOfClients =purchaseOrderService.getCountMembersFromOrdersByFilters(salesSearchVO);
				parameterMap.put("numOfClients", numOfClients.intValue());
				
				// therapist breakdown
				Map<String,List<SalesDetailsSummaryVO>> staffSummary = new HashMap<String,List<SalesDetailsSummaryVO>>();
				Map<Long,List<PurchaseItem>> staffMap =purchaseItemService.getStaffDetailsSummaryVOList(totalPurchaseItems);
				if(staffMap !=null && staffMap.size()>0){
					for (Map.Entry<Long, List<PurchaseItem>> entry : staffMap.entrySet()) {
						Long staffId = entry.getKey();
						List<SalesDetailsSummaryVO> list =purchaseItemService.getSalesDetailsSummaryVOListV4(entry.getValue());
						staffSummary.put(userService.get(staffId).getDisplayName(), list);
					}
				}
				parameterMap.put("staffSummary", staffSummary);
			}
			
			if(storeEmployee.getEmailAddress() == null){
				continue;
			}
			List<String> emails = Arrays.asList(storeEmployee.getEmailAddress().split(","));
			User staff =null;
			emailAddresses =new ArrayList<EmailAddress>();
			for (String email : emails) {
				
				staff = userService.getUserByEmail(email, CommonConstant.USER_GROUP_TYPE_STAFF);
				if (staff == null) {
					staff = new User();
					staff.setFirstName(email);
					staff.setUsername(email);
					staff.setEmail(email);
					staff.setId(2L);
				}
				emailAddresses.add(new EmailAddress(email, staff.getFullName()));
			}
			
			
			parameterMap.put("fromDate", fromDate);
			parameterMap.put("toDate", toDate);
			parameterMap.put("shopName", shop.getName());
			parameterMap.put("user", staff);
			parameterMap.put("templateType", "DAILY_REPORT");
			parameterMap.put("emailAddresses", emailAddresses);
			DailyReportThread.getInstance(parameterMap).start();
		}
		System.out.println("---SendDailyReportJob-------------end");
	}
}