package com.spa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.service.DocumentService;
import org.spa.service.awardRedemption.AwardRedemptionService;
import org.spa.service.awardRedemption.AwardRedemptionTransactionService;
import org.spa.service.bonus.BonusAttributeKeyService;
import org.spa.service.bonus.BonusAttributeService;
import org.spa.service.bonus.BonusRuleService;
import org.spa.service.bonus.BonusTemplateService;
import org.spa.service.book.BlockService;
import org.spa.service.book.BookBatchService;
import org.spa.service.book.BookItemService;
import org.spa.service.book.BookService;
import org.spa.service.bundle.BundleService;
import org.spa.service.commission.CommissionAttributeKeyService;
import org.spa.service.commission.CommissionAttributeService;
import org.spa.service.commission.CommissionRuleService;
import org.spa.service.commission.CommissionTemplateService;
import org.spa.service.common.CommonService;
import org.spa.service.company.CompanyPropertyService;
import org.spa.service.company.CompanyService;
import org.spa.service.discount.DiscountAttributeKeyService;
import org.spa.service.discount.DiscountRuleService;
import org.spa.service.discount.DiscountTemplateService;
import org.spa.service.inventory.*;
import org.spa.service.loyalty.LoyaltyLevelService;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.service.marketing.MktChannelService;
import org.spa.service.marketing.MktEmailTemplateService;
import org.spa.service.marketing.MktMailShotService;
import org.spa.service.order.OrderSurveyService;
import org.spa.service.order.PurchaseItemService;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.order.StaffCommissionService;
import org.spa.service.outSource.OutSourceAttributeKeyService;
import org.spa.service.outSource.OutSourceTemplateService;
import org.spa.service.payment.PaymentMethodService;
import org.spa.service.payment.PaymentService;
import org.spa.service.payroll.PayrollAttributeKeyService;
import org.spa.service.payroll.PayrollService;
import org.spa.service.payroll.PayrollTemplateService;
import org.spa.service.points.LoyaltyPointsTransactionService;
import org.spa.service.points.SpaPointsTransactionService;
import org.spa.service.prepaid.PrepaidService;
import org.spa.service.prepaid.PrepaidTopUpTransactionService;
import org.spa.service.privilege.SysResourceService;
import org.spa.service.privilege.SysRoleService;
import org.spa.service.product.*;
import org.spa.service.push.PushService;
import org.spa.service.salesforce.ImportTriggerHistoryService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.RoomTreatmentService;
import org.spa.service.shop.ShopService;
import org.spa.service.staticPage.StaticPagesService;
import org.spa.service.user.*;
import org.spa.service.version.VersionControlService;
import org.spa.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by Ivy on 2016/01/16.
 */
@Transactional
public class BaseController {

	final protected Logger logger = LoggerFactory.getLogger(getClass());
    final protected Date firstDayOfYear = DateUtil.getFirstDayOfYear();
    final protected Date lastDayOfYear = DateUtil.getLastDayOfYear();

	@Autowired
	public StaticPagesService staticPagesService;

    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected ShopService shopService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserCodeService userCodeService;

    @Autowired
    protected UserGroupService userGroupService;

    @Autowired
    protected UserFamilyDetailsService userFamilyDetailsService;

    @Autowired
    protected SysRoleService sysRoleService;

    @Autowired
    protected SysResourceService sysResourceService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductOptionService productOptionService;

    @Autowired
    protected SupplierService supplierService;

    @Autowired
    protected InventoryService inventoryService;

    @Autowired
    protected InventoryPurchaseOrderService inventoryPurchaseOrderService;

    @Autowired
    protected InventoryTransactionService inventoryTransactionService;

    @Autowired
    protected InventoryPurchaseOrderShipmentService inventoryPurchaseOrderShipmentService;

    @Autowired
    protected InventoryWarehouseService inventoryWarehouseService;
    
    @Autowired
    protected DiscountTemplateService discountTemplateService;

    @Autowired
    protected DiscountRuleService discountRuleService;

    @Autowired
    protected DiscountAttributeKeyService discountAttributeKeyService;

    @Autowired
    protected BookService bookService;

    @Autowired
    protected BookItemService bookItemService;

    @Autowired
    protected RoomService roomService;

    @Autowired
    protected LoyaltyLevelService loyaltyLevelService;

    @Autowired
    protected LoyaltyPointsTransactionService loyaltyPointsTransactionService;
    
    @Autowired
    protected SpaPointsTransactionService spaPointsTransactionService;
    
    @Autowired
    protected UserLoyaltyLevelService userLoyaltyLevelService;
    
    @Autowired
    protected AwardRedemptionService awardRedemptionService;
    
    @Autowired
    protected AwardRedemptionTransactionService awardRedemptionTransactionService;

    @Autowired
    protected MktChannelService mktChannelService;

    @Autowired
    protected MktMailShotService mktMailShotService;

    @Autowired
    protected MktEmailTemplateService mktEmailTemplateService;

    @Autowired
    protected PayrollService payrollService;
    
    @Autowired
	public PrepaidService prepaidService;
    
    @Autowired
	public PaymentMethodService paymentMethodService;
	
	@Autowired
	public StaffCommissionService staffCommissionService;
	
	@Autowired
	public BrandService brandService;
	
	@Autowired
	public ProductOptionKeyService productOptionKeyService;
	
	@Autowired
	public ProductDescriptionKeyService productDescriptionKeyService;

	@Autowired
	public ProductDescriptionService productDescriptionService;
	
	@Autowired
	public ProductOptionAttributeService productOptionAttributeService;
	
	@Autowired
	public PurchaseOrderService purchaseOrderService;
	
	@Autowired
	public ProductOptionSupernumeraryPriceService productOptionSupernumeraryPriceService;

    @Autowired
    protected BlockService blockService;
    
    @Autowired
    protected CompanyPropertyService companyPropertyService;
    
    @Autowired
    protected CommissionTemplateService commissionTemplateService;
    
    @Autowired
    protected CommissionAttributeService commissionAttributeService;
    
    @Autowired
    protected CommissionAttributeKeyService commissionAttributeKeyService;
    
    @Autowired
    protected CommissionRuleService commissionRuleService;
    
    @Autowired
    protected CommunicationChannelService communicationChannelService;
    
    @Autowired
    protected PayrollTemplateService payrollTemplateService;
    
    @Autowired
    protected PayrollAttributeKeyService payrollAttributeKeyService;
    
    @Autowired
    protected ConsentFormService consentFormService;
    
    @Autowired
    protected ConsentFormUserService consentFormUserService;
    
    @Autowired
    protected OutSourceTemplateService outSourceTemplateService;
    
    @Autowired
    protected OutSourceAttributeKeyService OutSourceAttributeKeyService;
    
    @Autowired
    protected BonusTemplateService bonusTemplateService;
    
    @Autowired
    protected BonusAttributeService bonusAttributeService;
    
    @Autowired
    protected BonusAttributeKeyService bonusAttributeKeyService;
    
    @Autowired
    protected BonusRuleService bonusRuleService;

    @Autowired
    protected DocumentService documentService;
    
    @Autowired
    protected PaymentService paymentService;
    
    @Autowired
    protected PrepaidTopUpTransactionService prepaidTopUpTransactionService;
    
    @Autowired
    protected PurchaseItemService purchaseItemService;
    
    @Autowired 
    protected ImportTriggerHistoryService importTriggerHistoryService;
    
    @Autowired
    protected OrderSurveyService orderSurveyService;
    @Autowired
    protected VersionControlService versionControlService;
    @Autowired
    protected  StaffHomeShopDetailsService staffHomeShopDetailsService;
    @Autowired
    protected PushService pushService;
    @Autowired
    protected CommonService commonService;
    @Autowired
    protected BundleService bundleService;
    @Autowired
    protected  StaffInOrOutService staffInOrOutService;
    @Autowired
    protected BookBatchService bookBatchService;
    
    @Autowired
    protected RoomTreatmentService roomTreatmentService;
	/**
	 * 请求返回的具体地址页面,比如请求users/listUser, 导航到的地址是/users/listUser.jsp页面
	 *
	 * @param name
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "{name}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPage(@PathVariable String name) {
		String redirectUrl = "/" + name; // 默认导航到根目录
		RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
		if (requestMapping != null) {
			String[] values = requestMapping.value();
			if (values != null && values.length > 0 && !"/".equals(values[0])) {
				redirectUrl = values[0] + "/" + name;
			}
		}
		return new ModelAndView(redirectUrl);
	}

	// 用于网页静态化
	// 请求的url格式为static/xxxxx
	// 需要另外实现一个mapping crawler/xxxxx的controller用于处理抓静态化html
	// 例：某一页面内<c:import url="/static/test"/>
	// 那就需要一个controller处理crawler/test并返回jsp
	// 静态化更新的逻辑为job周期性自动更新，但对于每一个页面都存在不再自动更新的日期，超过此日期则不会用job去更新，只在请求的时候才更新
	// 此设计是考虑到网页越来越的情况下对于旧的页面继续维护会消耗不必要的资源
	/*@RequestMapping(value = "staticPage*//**", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getStaticPage(HttpServletRequest request) {
		String url = null;
		if (request.getServerPort() == 80) {
			url = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		} else {
			url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
					+ request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		}

		System.out.println(url);
		// String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		// url = url.substring(url.indexOf("static/") + 7, url.length());
		Map<String, String[]> paraMap = request.getParameterMap();
		String parameter = null;
		if (paraMap.size() > 0) {
			Map<String, String[]> sortMap = new TreeMap<String, String[]>(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			Iterator<Entry<String, String[]>> it = paraMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String[]> entry = it.next();
				String key = entry.getKey();
				String[] value = entry.getValue();
				if("_".equals(key)){
					continue;
				}
				Arrays.sort(value);
				sortMap.put(key, value);
			}
			Iterator<Entry<String, String[]>> sortedIt = sortMap.entrySet().iterator();
			StringBuilder para = new StringBuilder();
			while (sortedIt.hasNext()) {
				Entry<String, String[]> entry = sortedIt.next();
				String key = entry.getKey();
				String[] value = entry.getValue();
				for (String v : value) {
					para.append(key);
					para.append("=");
					para.append(v);
					para.append("&");
				}
			}
			parameter = para.substring(0, para.length() - 1);
		}

		StaticPages page = staticPagesService.getPage(url, parameter);
		if (page == null) {
			page = staticPagesService.crawler(url, parameter, null);
		} else {
			if (page.getStopAutoUpdateDate() != null && new Date().after(page.getStopAutoUpdateDate())) {
				Long now = Clock.systemDefaultZone().millis() / 1000;
				Calendar cal = Calendar.getInstance();
				cal.setTime(page.getDate());
				Long saveDate = cal.getTimeInMillis() / 1000;
				if (now - saveDate > StaticPagesConstant.EFFECTIVE_SECONDS) {
					page = staticPagesService.crawler(url, parameter, page);
				}
			}
		}
		return page.getContent() == null ? "" : page.getContent();
	}*/

}
