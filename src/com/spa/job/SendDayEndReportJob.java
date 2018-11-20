package com.spa.job;
import com.spa.constant.CommonConstant;
import com.spa.email.Attachment;
import com.spa.email.EmailAddress;
import com.spa.jxlsBean.ExcelUtil;
import com.spa.thread.DayEndReportThread;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jxls.common.Context;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.company.Company;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.shop.StoreEmployee;
import org.spa.model.user.User;
import org.spa.service.order.PurchaseItemService;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.shop.ShopService;
import org.spa.service.shop.StoreEmployeeService;
import org.spa.service.user.UserService;
import org.spa.utils.*;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.report.SalesTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SendDayEndReportJob extends OpenSessionQuartzJobBean{
	public static final String PRINT_CUSTOMER_URL=PropertiesUtil.getValueByName("WEB_URL")+"report/customeReportTemplateForDayEndReport";//
	
	public static final String PRINT_REVENUE_URL=PropertiesUtil.getValueByName("WEB_URL")+"report/revenueByShopReportTemplate";//
	
	public static final String PRINT_SALES_SUMMARY_URL=PropertiesUtil.getValueByName("WEB_URL")+"report/salesSummaryByShopTemplate";//
	
	
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
	
	private static final String prefix ="DAY_END_";
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException{
		String fromDate = "";//开始时间
		String toDate = "";//结束时间
		Calendar fromCalendar = new GregorianCalendar();
		Date from = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		toDate = sdf.format(from);
		fromCalendar.setTime(from);
		fromCalendar.add(Calendar.DATE, -1);
		fromDate = sdf.format(fromCalendar.getTime());
		
		System.out.println("-----SendDayEndReportJob-------------start::"+fromDate +"--to date---"+toDate);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoreEmployee.class);
		detachedCriteria.add(Restrictions.eq("isActive", true));
		detachedCriteria.add(Restrictions.like("type",prefix, MatchMode.ANYWHERE));
		List<StoreEmployee> storeEmployees = storeEmployeeService.list(detachedCriteria);
		if(storeEmployees ==null || storeEmployees.size()<=0){
			return;
		}
		
		List<EmailAddress> emailAddresses = null;
		List<Attachment> attachments = null;
		Attachment attachment2 =null;
		Attachment attachment  = null;
		Attachment attachment3  = null;
		Attachment attachment4 =null;
		for (StoreEmployee storeEmployee : storeEmployees) {
			
			Shop shop = storeEmployee.getShop();
			if(shop == null){
				continue;
			}
			attachments = new ArrayList<Attachment>();
			if(storeEmployee.getType().equals(CommonConstant.AUTO_REPORT_TYPE_DAY_END_REVENUE)){
				
				Map<String, Object> map = new HashMap<>();
				String revenueByShopReportName = RandomUtil.generateRandomNumberWithDate("Revenue-Report-"+shop.getName()+"-") + ".pdf";
				File revenueByShopReportFile =null;
				try {
					revenueByShopReportFile = PDFUtil.convert(PRINT_REVENUE_URL, map);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String revenueByShopReportPath = revenueByShopReportFile.getPath();
				attachment3 = Attachment.getInstance(revenueByShopReportPath);
				attachment3.setName(revenueByShopReportName);
				attachments.add(attachment3);
				
			}else{
			//Custom Report Pdf 生成 start
			Map<String, Object> map = new HashMap<>();
			map.put("rootCategoryId", 1L);
			
			map.put("shopId", shop.getId());
			if (storeEmployee.getType().equals(CommonConstant.AUTO_REPORT_TYPE_DAY_END_ORDINARY)) {
				map.put("isLanlordReport", false);
			} else {
				map.put("isLanlordReport", true);
			}
			String customerReportName = RandomUtil.generateRandomNumberWithDate("Custom-Report-"+shop.getName()+"-") + ".pdf";
			File customerReportFile =null;
			try {
				customerReportFile = PDFUtil.convert(PRINT_CUSTOMER_URL, map);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String customerReportPath = customerReportFile.getPath();
			attachment2 = Attachment.getInstance(customerReportPath);
			attachment2.setName(customerReportName);
			attachments.add(attachment2);
			//Custom Report Pdf 生成 end
			
			// Sales summary by shop start
			if (shop.getReference().equals("HEAD_OFFICE")) {
				Map<String, Object> map2 = new HashMap<>();
				String salesSummaryReportName = RandomUtil.generateRandomNumberWithDate("Sales Summary By Shop -") + ".pdf";
				File salesSummaryReportFile =null;
				try {
					salesSummaryReportFile = PDFUtil.convert(PRINT_SALES_SUMMARY_URL, map2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String salesSummaryReportPath = salesSummaryReportFile.getPath();
				attachment4 = Attachment.getInstance(salesSummaryReportPath);
				attachment4.setName(salesSummaryReportName);
				attachments.add(attachment4);
			}
			// Sales summary by shop end
			
			//生成 Sales Details Report export start
			if (storeEmployee.getType().equals(CommonConstant.AUTO_REPORT_TYPE_DAY_END_ORDINARY)){
				
				//purchase item details
				SalesSearchVO salesSearchVO = new SalesSearchVO();
				salesSearchVO.setRootCategoryId(1L);
				salesSearchVO.setIsSearchByJob(true);
				
				salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
				if (!shop.getReference().equals("HEAD_OFFICE")) {
					salesSearchVO.setShopId(shop.getId());
				}
				salesSearchVO.setFromDate(fromDate);
				salesSearchVO.setToDate(toDate);
				List<PurchaseItem> items = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
//				System.out.println("items size -----"+items.size());
				List<SalesTemplateVO> voList = new ArrayList<SalesTemplateVO>();
				SalesTemplateVO vo = null;
				PurchaseOrder po = null;
				ProductOption pro = null;
				for (PurchaseItem item : items) {
					po = item.getPurchaseOrder();
					pro = item.getProductOption();
					vo = new SalesTemplateVO();
					vo.setReference(po.getReference());
					vo.setShopName(po.getShop().getName());
					try {
						vo.setDate(DateUtil.dateToString(po.getPurchaseDate(), "yyyy-MM-dd"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
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
					vo.setFullPrice(pro != null ? pro.getOriginalPrice() : 0d);
					vo.setCostOfProduct(pro != null ? pro.getCostOfProduct() : 0d);
					vo.setPayment(item.getPurchaseOrder().getPaymentMethodsAndAmount2());
					vo.setRequested(item.getIsRequested() ? "Y" : "N");
					voList.add(vo);
				}
				Context context = new Context();
				context.putVar("volist", voList);

				File salesExportFile = ExcelUtil.write("salesExportTemplate.xls", context);
				String salesExportName = RandomUtil.generateRandomNumberWithDate("Sales-Details-"+shop.getName()+"-") + ".xls";
				String salesDetailsExportPath = salesExportFile.getPath();

				attachment = Attachment.getInstance(salesDetailsExportPath);
				attachment.setName(salesExportName);
				attachments.add(attachment);
			}
			//生成 Sales Details Report export end
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
			
			//发送email
			Company company = new Company();
			company.setId(1l);
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("company", company);
			parameterMap.put("fromDate", fromDate);
			parameterMap.put("toDate", toDate);
			parameterMap.put("shopName", shop.getName());
			parameterMap.put("user", staff);
			parameterMap.put("attachments", attachments);
			parameterMap.put("templateType", "DAY_END_REPORT");
			parameterMap.put("emailAddresses", emailAddresses);
			DayEndReportThread.getInstance(parameterMap).start();
		}
		System.out.println("---SendDayEndReportJob-------------end");
	}
}