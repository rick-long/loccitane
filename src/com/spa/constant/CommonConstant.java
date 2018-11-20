package com.spa.constant;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.utils.I18nUtil;
import org.spa.vo.common.TreeVO;

import java.util.*;

/*
 * Created by Ivy on 2016/01/16.
 * */
public class CommonConstant {

    final private static Logger logger = LoggerFactory.getLogger(CommonConstant.class);

    public  static final String anyTherapist = "99999999";
    /**
     * 当前登录的用户对象key,保存到session中
     */
    public static final String CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";
    public static final String AUTO_REPORT_TYPE_DAY_END_ORDINARY="DAY_END_ORDINARY";
    public static final String AUTO_REPORT_TYPE_DAY_END_SPECIAL="DAY_END_SPECIAL";
    public static final String AUTO_REPORT_TYPE_DAY_END_REVENUE="DAY_END_REVENUE";
    public static final String AUTO_REPORT_TYPE_DAILY_SHOP="DAILY_SHOP";
    
	// USER accountType 
	public static final String USER_ACCOUNT_TYPE_MEMBER="MEMBER";
	public static final String USER_ACCOUNT_TYPE_GUEST="GUEST";
	public static final String USER_ACCOUNT_TYPE_STAFF="STAFF";
	public static final String USER_ACCOUNT_TYPE_SUPPER_MANAGER="SUPPER_MANAGER";
	
    // staff role reference
    public static final String STAFF_ROLE_REF_ADMIN="ADMIN";
    public static final String STAFF_ROLE_REF_THERAPIST="THERAPIST";
    public static final String STAFF_ROLE_REF_SHOP_MANAGER="SHOP_MANAGER";
    public static final String STAFF_ROLE_REF_RECEPTION="RECEPTION";
    public static final String STAFF_ROLE_REF_SHOP_MANAGER_T="SHOP_MANAGER_T"; // shop manager therapist
    public static final String STAFF_ROLE_REF_RECEPTION_T="RECEPTION_T"; // reception therapist
	
	//root category reference
	public static final String CATEGORY_REF_ROOT="ROOT_CATEGORY";
    public static final String CATEGORY_REF_TREATMENT="CA-TREATMENT";
    public static final String CATEGORY_REF_GOODS="CA-GOODS";
    public static final String CATEGORY_REF_HAIRSALON="CA-HAIRSALON";
    public static final String CATEGORY_REF_TIPS="CA-TIPS";
    public static final String CATEGORY_REF_OTHERS="CA-OTHERS";
    public static final String CATEGORY_REF_PREPAID="PREPAID";
    
    public static final String CATEGORY_REF_HAIRSALON_FOR_SELECTED="CATEGORY_REF_HAIRSALON_FOR_SELECTED";
    
    //product option key
	// product option key"PRICE" "DURATION" "COMMISSION" Ref

    public static final String TYPE_CATEGORY = "CATEGORY";
    public static final String TYPE_PRODUCT = "PRODUCT";

	public static final String PRODUCT_OPTION_KEY_PRICE_REF="PRICE";
	public static final String PRODUCT_OPTION_KEY_COST_REF="COST";
	public static final String PRODUCT_OPTION_KEY_DURATION_REF="DURATION";
    public static final String PRODUCT_OPTION_KEY_PROCESS_TIME_REF="PROCESS_TIME";
    public static final String PRODUCT_OPTION_KEY_CAPACITY_REF="CAPACITY";
	
	//product option
	public static final String PRODUCT_OPTION_FOR_TIPS_REF="PO-TIPS";
	//ref prefix start
	public static final String CATEGORY_REF_PREFIX="CA-";

	public static final String BRAND_REF_PREFIX="BR-";
	
	public static final String DEFAULT_BRAND_REF="BR-DEFAULT";
	
	
	public static final String SUPPLIER_REF_PREFIX="SUP-";
	public static final String DEFAULT_SUPPLIER_REF="SUP-DEFAULT";

	public static final String PRODUCT_REF_PREFIX="P-";
	public static final String PRODUCT_OPTION_REF_PREFIX="PRO-";
	
	public static final String SHOP_REF_PREFIX="SH-";

    public static final String SHOP_ONLINE_REF = "ONLINE";
	
	public static final String STAFF_ATTR_KEY_REF_PREFIX="SAK-";
	
	public static final String USER_GROUP_REF_PREFIX="UG-";
	
    public static final String ROOM_REF_PREFIX="RM";
    
    public static final String PREPAID_REF_PREFIX="PRES";
	
    public static final String PURCHASE_ORDER_REF_PREFIX="O-";
    
    public static final String PURCHASE_ORDER_STATUS_COMPLETED="COMPLETED";
    public static final String PURCHASE_ORDER_STATUS_PENDING="PENDING";
    
    public static final String PREPAID_TOP_UP_REF_PREFIX="PTU-";

    public static final String INVENTORY_PURCHASE_ORDER_REF_PREFIX="IO-";

    public static final String NEWS_LETTER_REF_PREFIX="NL-";
    
	//ref prefix end
	
	//phone type
	public static final String PHONE_TYPE_HOME="HOME";
	public static final String PHONE_TYPE_MOBILE="MOBILE";
	public static final String PHONE_TYPE_BUSINESS="BUSINESS";

	//product option key.reference
    public static final String PRODUCT_OPTION_KEY_REF_DURATION = "DURATION";
    public static final String PRODUCT_OPTION_KEY_REF_PRICE = "PRICE";
    public static final String PRODUCT_OPTION_KEY_REF_CODE = "code";
    
    //product description key.reference
    public static final String PRODUCT_DESCRIPTION_KEY_REF_DESCRIPTION = "DESCRIPTION";
    public static final int PRODUCT_DESCRIPTION_VALUE_SUBSTRING = 40;
    // 時間量化单位
    public static final int TIME_UNIT = 5;
    
    //prepaid defined start
    //prepaid type
    public static final String PREPAID_TYPE_CASH_PACKAGE="CASH_PACKAGE";
    public static final String PREPAID_TYPE_TREATMENT_PACKAGE="TREATMENT_PACKAGE";
    public static final String PREPAID_TYPE_CASH_VOUCHER="CASH_VOUCHER";
    public static final String PREPAID_TYPE_TREATMENT_VOUCHER="TREATMENT_VOUCHER";
    public static final String PREPAID_TYPE_MEMBERSHIP_FEE="MEMBERSHIP_FEE";
    //prepaid package type
    public static final String PREPAID_PACKAGE_TYPE_NORMAL="NORMAL";
    public static final String PREPAID_PACKAGE_TYPE_ELEMIS="ELEMIS";
    
    //prepaid expired date
    public static final int PREPAID_EXPIRED_MONTHS_P=12;
    public static final int PREPAID_EXPIRED_MONTHS_V=6;
    
    //
    public static final String PREPAID_TOPUP_TRANSACTION_STATUS_PAY_PENDING="PAY_PENDING";
    public static final String PREPAID_TOPUP_TRANSACTION_STATUS_NORMAL="NORMAL";
    
    //LOYALTY POINTS EXPIRY MONTHS
    public static final int LOYALTY_POINTS_EXPIRED_MONTHS_BY_PREPAID=12;
    public static final int LOYALTY_POINTS_EXPIRED_MONTHS_BY_SALES=24;
    
    //no.of therapist used in add sales and prepaid
    public static final String NUM_OF_THERAPIST_USED="NUM_OF_THERAPIST_USED";
    
    //no.of payment method used in add sales and prepaid
    public static final String NUM_OF_PAYMENT_METHOD_USED="NUM_OF_PAYMENT_METHOD_USED";
    
    //no.of bundle items max number
    public static final String BUNDLE_ITEM_MAX="BUNDLE_ITEM_MAX";
    
    public static final boolean BUY_PREPAID_HAS_COMMISSION=true;
    
    public static final String CAL_PREPAID_COMMISSION_RUN_CALSSES="com.spa.tools.detector.prepaid.BuyPrepaidCommCalculationDetector";
    
    public static final String CAL_BONUS_RUN_CALSSES="com.spa.tools.detector.payroll.BonusCalculationDetector";
    
    public static final String CAL_HAIR_SALON_SALES_COMMISSION_RUN_CALSSES="com.spa.tools.detector.sales.HairSalonSalesCommissionCalculationDetector";
    //货币类型
    public static final String CURRENCY_TYPE=I18nUtil.getMessageKey("label.unit.currency");

    //时间类型
    public static final String TIME_TO_TYPE=I18nUtil.getMessageKey("label.unit.mins");

    // book 状态定义
    public static final String BOOK_STATUS_PENDING = "PENDING";
    public static final String BOOK_STATUS_CONFIRM = "CONFIRM";
    public static final String BOOK_STATUS_CHECKIN_SERVICE = "CHECKIN_SERVICE";
    public static final String BOOK_STATUS_COMPLETE = "COMPLETE";
    //not show/complete/cancel释放资源表
    public static final String BOOK_STATUS_NOT_SHOW = "NOT_SHOW";
    public static final String BOOK_STATUS_CANCEL = "CANCEL";
    public static final String BOOK_STATUS_WAITING = "WAITING";
    public static final String BOOK_STATUS_RECOVER = "RECOVER";
    public static final String BOOK_STATUS_PAID = "PAID";
    
    public static final List<String> releaseResourceStatusList = new ArrayList<>();
    
    
    public static final double PACKAGE_COMMISSION_RATE_FOR_NEW=0.03;
    public static final double PACKAGE_COMMISSION_RATE_FOR_RENEWAL=0.02;
    
    public static final double TREATMENT_COMMISSION_RATE_FOR_2nd_tier=0.19;
    public static final double GOODS_COMMISSION_RATE_FOR_2nd_tier=0.15;
    
    public static final List<String> bookItemBlockStatusList = new ArrayList<>();

    static {
        // 以下状态释放resource资源
        releaseResourceStatusList.add(CommonConstant.BOOK_STATUS_CANCEL);
        releaseResourceStatusList.add(CommonConstant.BOOK_STATUS_NOT_SHOW);
        releaseResourceStatusList.add(CommonConstant.BOOK_STATUS_WAITING);
        releaseResourceStatusList.add(CommonConstant.BOOK_STATUS_COMPLETE);

        bookItemBlockStatusList.add(CommonConstant.BOOK_STATUS_PENDING);
        bookItemBlockStatusList.add(CommonConstant.BOOK_STATUS_CONFIRM);
        bookItemBlockStatusList.add(CommonConstant.BOOK_STATUS_CHECKIN_SERVICE);
        bookItemBlockStatusList.add(CommonConstant.BOOK_STATUS_COMPLETE);
    }

    public static final List<String> checkOutStatusList = new ArrayList<>();

    static {
        // 以及下状态可以付款
    	checkOutStatusList.add(CommonConstant.BOOK_STATUS_CHECKIN_SERVICE);
    }
    
    // hair salon no need to deduct
    public static final String HAIR_SALON_NO_NEED_TO_DEDUCT_CUT="CA-20161101-3060";
    public static final String HAIR_SALON_NO_NEED_TO_DEDUCT_STYLE="CA-20161101-3061";
    public static final List<String> hairsalonNoNeedToDeductCategotyRefList = new ArrayList<>();
    static {
    	hairsalonNoNeedToDeductCategotyRefList.add(CommonConstant.HAIR_SALON_NO_NEED_TO_DEDUCT_CUT);
    	hairsalonNoNeedToDeductCategotyRefList.add(CommonConstant.HAIR_SALON_NO_NEED_TO_DEDUCT_STYLE);
    }
    public static final Double HAIR_SALON_AMOUNT_DEDUCT=0.1d;
    
    //order status
    public static final String ORDER_STATUS_PENDING = "PENDING";
    public static final String ORDER_STATUS_COMPLETED = "COMPLETED";
    public static final String ORDER_STATUS_CANCEL = "CANCEL";
    
    //payment status
    public static final String PAYMENT_STATUS_PAID = "PAID";
    public static final String PAYMENT_STATUS_UNPAID = "UNPAID";
    
    //Payment method ref
    public static final String PAYMENT_METHOD_REF_NO_PAYMENT="NO_PAYMENT";
    public static final String PAYMENT_METHOD_REF_PACKAGE="PACKAGE";
    public static final String PAYMENT_METHOD_REF_VOUCHER="VOUCHER";

    //spa points and loyalty points
    public static final Boolean WHETHER_EARN_POINTS=true;
   
    //减
    public static final String POINTS_ACTION_MINUS="MINUS";
    public static final String ACTION_DELETE="DELETE";
    //加
    public static final String POINTS_ACTION_PLUS="PLUS";
    public static final String ACTION_ADD="ADD";
    public static final String ACTION_EDIT="EDIT";
    
    public static final String POINTS_EARN_CHANNEL_PREPAID="PREPAID";
    public static final String POINTS_EARN_CHANNEL_SALES="SALES";
    public static final String POINTS_EARN_CHANNEL_MANUAL="MANUAL";
    public static final String POINTS_EARN_CHANNEL_REDEEM="REDEEM";
    
    // page State
    public static final String STATE_ADD = "ADD";
    public static final String STATE_EDIT = "EDIT";

    // 星期
    public static final Map<Integer, String> weekMap = new HashMap<>();
    static {
        weekMap.put(1, "monday");
        weekMap.put(2, "tuesday");
        weekMap.put(3, "wednesday");
        weekMap.put(4, "thursday");
        weekMap.put(5, "friday");
        weekMap.put(6, "saturday");
        weekMap.put(7, "sunday");
    }
   //productType集合
    public static final List<String> productTypes = new ArrayList<>();
    static {
        productTypes.add(CommonConstant.CATEGORY_REF_TREATMENT);
    }

    // INVENTORY_PURCHASE_ORDER 状态定义
    public static final String INVENTORY_PURCHASE_ORDER_STATUS_PENDING = "PENDING";
    public static final String INVENTORY_PURCHASE_ORDER_STATUS_COMPLETE = "COMPLETE";
    public static final String INVENTORY_PURCHASE_ORDER_STATUS_RECEIVING = "RECEIVING";
    public static final String INVENTORY_PURCHASE_ORDER_STATUS_CANCEL = "CANCEL";

    // inventory transaction type
    public static final String INVENTORY_TRANSACTION_TYPE_NEW_STOCK = "NEW_STOCK";
    public static final String INVENTORY_TRANSACTION_TYPE_ADJUSTMENT_ADD = "ADJUSTMENT_ADD";
    public static final String INVENTORY_TRANSACTION_TYPE_ADJUSTMENT_MINUS = "ADJUSTMENT_MINUS";
    public static final String INVENTORY_TRANSACTION_TYPE_SALE = "SALE";
    public static final String INVENTORY_TRANSACTION_TYPE_RETURNED = "RETURNED";
    public static final String INVENTORY_TRANSACTION_TYPE_PURCHASE_ORDER = "PURCHASE_ORDER";
    public static final String INVENTORY_TRANSACTION_TYPE_TRANSFER_ADD = "TRANSFER_ADD";
    public static final String INVENTORY_TRANSACTION_TYPE_TRANSFER_MINUS = "TRANSFER_MINUS";
    public static final String INVENTORY_TRANSACTION_TYPE_REDEEMED = "REDEEMED";

    public static final String INVENTORY_TRANSACTION_DIRECTION_IN = "IN";
    public static final String INVENTORY_TRANSACTION_DIRECTION_OUT = "OUT";

    public static final List<String> TRANSACTION_ADD_LIST = new ArrayList<>();
    public static final List<String> TRANSACTION_MINUS_LIST = new ArrayList<>();
    static {
        // inventory transaction add list
        TRANSACTION_ADD_LIST.add(INVENTORY_TRANSACTION_TYPE_NEW_STOCK);
        TRANSACTION_ADD_LIST.add(INVENTORY_TRANSACTION_TYPE_ADJUSTMENT_ADD);
        TRANSACTION_ADD_LIST.add(INVENTORY_TRANSACTION_TYPE_RETURNED);
        TRANSACTION_ADD_LIST.add(INVENTORY_TRANSACTION_TYPE_PURCHASE_ORDER);
        TRANSACTION_ADD_LIST.add(INVENTORY_TRANSACTION_TYPE_TRANSFER_ADD);

        // inventory transaction minus list
        TRANSACTION_MINUS_LIST.add(INVENTORY_TRANSACTION_TYPE_ADJUSTMENT_MINUS);
        TRANSACTION_MINUS_LIST.add(INVENTORY_TRANSACTION_TYPE_SALE);
        TRANSACTION_MINUS_LIST.add(INVENTORY_TRANSACTION_TYPE_REDEEMED);
        TRANSACTION_MINUS_LIST.add(INVENTORY_TRANSACTION_TYPE_TRANSFER_MINUS);
    }

    public static String getTransactionDirection(String transactionType) {
        if (TRANSACTION_ADD_LIST.contains(transactionType)) {
            return INVENTORY_TRANSACTION_DIRECTION_IN;
        } else if (TRANSACTION_MINUS_LIST.contains(transactionType)) {
            return INVENTORY_TRANSACTION_DIRECTION_OUT;
        } else {
            logger.error("cannot found direction by transaction type:{}", transactionType);
        }
        return "";
    }
    
    //prepaid suitable options
    public static final String PREPAID_SUITABLE_OPTION = "PREPAID_SUITABLE_OPTION";//set in company properties
    public static final String PREPAID_SUITABLE_OPTION_EQUAL = "EQUAL";//Must match treatment exactly
    public static final String PREPAID_SUITABLE_OPTION_ALL_PASS = "ALL_PASS";//any treatment allowed
    public static final String PREPAID_SUITABLE_OPTION_LESS_THAN = "LESS_THAN";//treatment's price <= prepaid 的单一价
   
    /**
     * 上传drl文件的路径
     */
    public static final String UPLOAD_DRL_PATH = "WEB-INF/upload/drl/";

    public static final String UPLOAD_DOCUMENT_PATH = "resources/document/";

    public static final String UPLOAD_DOCUMENT_CONSENT_FORM_PATH = "WEB-INF/upload/CONSENT_FORM";
    
    public static final String DOCUMENT_TYPE_EMAIL_ATTACHMENT = "EMAIL_ATTACHMENT";
    
    public static final String DOCUMENT_TYPE_CONSENT_FORM = "CONSENT_FORM";
    
    
    // award redemption type
    public static final String REDEEM_TYPE_GOODS = "GOODS";
    public static final String REDEEM_TYPE_PREPAID = "PREPAID";
    
    //shop default opening hour and close hour
    public static final String OPENING_HOUR_DEFAULT="09:30";
    public static final String CLOSE_HOUR_DEFAULT="22:00";

    public static final String MAIL_CHANNEL_NEWS_LETTER = "NEWS_LETTER";
    public static final String MAIL_CHANNEL_SYSTEM = "SYSTEM";

    public static final String MAIL_SHOT_TYPE_NEWS_LETTER = "NEWS_LETTER";

    public static final String MAIL_SHOT_STATUS_SENDING = "SENDING";
    public static final String MAIL_SHOT_STATUS_COMPLETE = "COMPLETE";

    // email template type
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_NEWSLETTER = "NEWSLETTER";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_BIRTHDAY = "BIRTHDAY";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_REGISTRATION = "REGISTRATION";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_THANK_YOU_EMAIL = "THANK_YOU_EMAIL";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_THANK_YOU_REDEEM = "THANK_YOU_REDEEM";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_RESET_PASSWORD = "RESET_PASSWORD";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_LOYALTY_LEVEL_UPGRADE = "LOYALTY_LEVEL_UPGRADE";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_LOYALTY_LEVEL_DOWNGRADE = "LOYALTY_LEVEL_DOWNGRADE";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_LOYALTY_LEVEL_EXPIRY = "LOYALTY_LEVEL_EXPIRY";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_INVENTORY = "INVENTORY";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_EMAIL_HEADER = "EMAIL_HEADER";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_EMAIL_END = "EMAIL_END";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_EMAIL_FOOTER = "EMAIL_FOOTER";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_INIT_PASSWORD = "INIT_PASSWORD";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_ONLINE_GIFT_VOUCHER = "ONLINE_GIFT_VOUCHER";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_SEND_FRIEND_ONLINE_GIFT_VOUCHER = "SEND_FRIEND_ONLINE_GIFT_VOUCHER";
    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_PICK_UP_ONLINE_GIFT_VOUCHER = "PICK_UP_ONLINE_GIFT_VOUCHER";
    public static final String EMAIL_TEMPLATE_TYPE_REDEEM  = "EMAIL_TEMPLATE_TYPE_REDEEM";
    public static final String SEND_THANK_YOU_EMAIL  = "SEND_THANK_YOU_EMAIL";

    // add by william -- 2018-8-10
    public static final String SEND_BOOKING_NOTIFICATION_EMAIL  = "SEND_BOOKING_NOTIFICATION_EMAIL";

    /* create by william -- 2018-9-10 */
    public static final String SEND_BOOKING_REMINDER_NOTIFICATION_EMAIL  = "SEND_BOOKING_REMINDER_NOTIFICATION_EMAIL";

    public static final String MARKETING_EMAIL_TEMPLATE_SUBJECT_HEADER="HEADER";
    public static final String MARKETING_EMAIL_TEMPLATE_SUBJECT_CSS="CSS";
    public static final String MARKETING_EMAIL_TEMPLATE_SUBJECT_FOOTER="FOOTER";

    public static final String MARKETING_EMAIL_TEMPLATE_TYPE_ONLINE_GIFT_VOUCHER_SELF = "ONLINE_GIFT_VOUCHER_SELF";
    
    public static final int EMAIL_JOB_DELAY_START = 5; // email job 延时开始(秒)
    public static final int EMAIL_JOB_SEND_INTERVAL = 5; // email job 发送的时间间隔(秒)

    public static final String USER_CODE_TYPE_ACTIVATING = "ACTIVATING";
    public static final String USER_CODE_TYPE_RESET_PASSWORD = "RESET_PASSWORD";
    public static final String USER_CODE_TYPE_USED = "USED";
    
    //payroll
    public static final String PAYROLL_KEY_REF_TARGET_AMOUNT_CA_GOODS="TARGET_AMOUNT_CA-GOODS";
    public static final String PAYROLL_KEY_REF_TARGET_AMOUNT_CA_REATMENT="TARGET_AMOUNT_CA-TREATMENT";
    public static final String PAYROLL_KEY_REF_STANDARD_SALARY="STANDARD_SALARY";
    public static final String PAYROLL_KEY_REF_GM="GUARANTEED_MINIMUM";
    public static final String PAYROLL_KEY_REF_HOUR_SALARY="HOUR_SALARY";
    public static final String PAYROLL_KEY_REF_TARGET_AMOUNT="TARGET_AMOUNT_";
    
    public static final String STAFF_ATTRIBUTE_KEY_COMM_SCHEME="COMMISSION_SCHEME";
    
    public static final String PAYROLL_BONUS_TYPE_PREPAID="PREPAID";
    public static final String PAYROLL_BONUS_TYPE_PREPAID_DISPLAY="Prepaid";
    public static final String PAYROLL_IS_USE_CONTRIBUTE="IS_USE_CONTRIBUTE";
    public static final String PAYROLL_MAX_CONTRIBUTION_AMOUNT="MAX_CONTRIBUTION_AMOUNT";
    public static final String PAYROLL_CONTRIBUTE_RATE="CONTRIBUTE_RATE";
    public static final String PAYROLL_MININUM_CONTRIBUTE_NEEDED_AMOUNT="MININUM_CONTRIBUTE_NEEDED_AMOUNT";
    public static final Integer PAYROLL_HAS_CONTRIBUTION_ATFER_DAYS = 60;
    public static final String TOTAL_WORK_DAYS_IN_A_MONTH="TOTAL_WORK_DAYS_IN_A_MONTH";
    
    //payroll additional type
    public static final String STAFF_PAYROLL_ADDITIONAL_TYPE_BEFORE_MPF="BEFORE_MPF";
    public static final String STAFF_PAYROLL_ADDITIONAL_TYPE_AFTER_MPF="AFTER_MPF";
    
    public static final String BOOKING_CHANNEL_STAFF="STAFF";
    public static final String BOOKING_CHANNEL_MOBILE="MOBILE";
    public static final String BOOKING_CHANNEL_ONLINE="ONLINE";
    
    public static final List<String> PAYROLL_KEY_REF_NOT_FOR_ADDITIONAL_LIST = new ArrayList<>();
    static {
        PAYROLL_KEY_REF_NOT_FOR_ADDITIONAL_LIST.add(PAYROLL_KEY_REF_GM);
    }
    /**
     * 用于获取当前容量到浮动容量范围的房间，
     * 比如获取容量为2的房间的话，
     * 同时会获取 3, 4, 5 这些房间,
     * 优先获取2, 2没有，则获取3 ，以此类推.
     * 如果都没有，则获取组合的房间，比如 1+1(两个容量为1的房间)
     * 如果都没有，则返回更高容量的房间(比如 6, 7, 8 容量的房间)
     * 如果更高的容量的房间都找不到，则房间获取失败
     */
    public static final int ROOM_CAPACITY_RANGE = 3;

    public static final String DEFAULT_OPEN_TIME = "09:30"; // 默认开门时间
    public static final String DEFAULT_CLOSE_TIME = "22:00"; // 默认关门时间

    public static final String ROOM_VIEW = "ROOM_VIEW";
    public static final String THERAPIST_VIEW = "THERAPIST_VIEW";
    
    // USER GROUP TYPE
    public static final String USER_GROUP_TYPE_MEMBER="MEMBER";
    public static final String USER_GROUP_TYPE_STAFF="STAFF";
    public static final List<String> USER_GROUP_TYPE_LIST = new ArrayList<>();
    static {
    	USER_GROUP_TYPE_LIST.add(USER_GROUP_TYPE_MEMBER);
    	USER_GROUP_TYPE_LIST.add(USER_GROUP_TYPE_STAFF);
    }
    // USER GROUP MODULE
    public static final String USER_GROUP_MODULE_COMMISSION="COMMISSION";
    public static final String USER_GROUP_MODULE_MARKETING="MARKETING";
    public static final String USER_GROUP_MODULE_DISCOUNT="DISCOUNT";
    public static final List<String> USER_GROUP_MODULE_LIST = new ArrayList<>();
    public static final List<String> USER_GROUP_MODULE_LIST1 = new ArrayList<>();
    static {
    	USER_GROUP_MODULE_LIST.add(USER_GROUP_MODULE_MARKETING);
    	/*USER_GROUP_MODULE_LIST.add(USER_GROUP_MODULE_COMMISSION);*/
    	USER_GROUP_MODULE_LIST.add(USER_GROUP_MODULE_DISCOUNT);
    }
    static {
        /*USER_GROUP_MODULE_LIST.add(USER_GROUP_MODULE_MARKETING);*/
        USER_GROUP_MODULE_LIST1.add(USER_GROUP_MODULE_COMMISSION);
        /*USER_GROUP_MODULE_LIST.add(USER_GROUP_MODULE_DISCOUNT);*/
    }
    
    //commission attribute key
    public static final String COMMISSION_ATTRIBUTE_KEY_REF_RATE="RATE";
    public static final String COMMISSION_ATTRIBUTE_KEY_Extra_RATE="EXTRA_RATE";
    public static final String COMMISSION_ATTRIBUTE_KEY_TARGET_RATE="TARGET_RATE";
    public static final String COMMISSION_ATTRIBUTE_KEY_TARGET_Extra_RATE="TARGET_EXTRA_RATE";


   //bonus attribute key
    public static final String BONUS_ATTRIBUTE_KEY_REF_TARGET_RATE="TARGET_RATE";
    public static final String BONUS_ATTRIBUTE_KEY_REF_INCENTIVE_RATE="INCENTIVE_RATE";
    public static final String BONUS_ATTRIBUTE_KEY_REF_INCENTIVE_AMOUNT="INCENTIVE_AMOUNT";
    
    //hotel management
    public static final String IS_HOTEL_MANAGEMENT="IS_HOTEL_MANAGEMENT";

    public static final String BLOCK_THERAPIST = "BLOCK_THERAPIST";
    public static final String BLOCK_ROOM = "BLOCK_ROOM";

    public static final String BLOCK_REPEAT_TYPE_NONE = "NONE";
    public static final String BLOCK_REPEAT_TYPE_EVERY_DAY = "EVERY_DAY";
    public static final String BLOCK_REPEAT_TYPE_EVERY_WEEK = "EVERY_WEEK";
    public static final String BLOCK_REPEAT_TYPE_EVERY_MONTH = "EVERY_MONTH";

    public static final String BLOCK_TYPE_BREAK = "BREAK";
    public static final String BLOCK_TYPE_ANNUAL_LEAVE = "ANNUAL_LEAVE"; // 年假
    public static final String BLOCK_TYPE_NO_PAID_LEAVE = "NO_PAID_LEAVE"; // 無薪假
    public static final String BLOCK_TYPE_SICK_LEAVE = "SICK_LEAVE"; // 病假
    public static final String BLOCK_TYPE_MATERNITY_LEAVE = "MATERNITY_LEAVE"; // 產假
    public static final String BLOCK_TYPE_DAY_OFF = "DAY_OFF"; // 正常休假
    public static final String BLOCK_TYPE_BEFORE_WORK_DAY = "BEFORE_WORK_DAY"; // 上班前
    public static final String BLOCK_TYPE_AFTER_WORK_DAY = "AFTER_WORK_DAY"; // 下班后
    public static final String BLOCK_TYPE_LUNCH = "LUNCH"; // 吃饭时间
    public static final String BLOCK_TYPE_RESERVED_FOR_BOOKING = "RESERVED_FOR_BOOKING"; // 预留给booking的时间
    public static final String BLOCK_TYPE_OVERTIME = "OVERTIME"; // 加班时间

    public static final Date BLOCK_NEVER_STOP_DATE = new DateTime(2099, 12, 31, 0, 0).toDate();

    public static final String WORKDAY = "Workday";
    public static final String DAY_OFF = "Day Off";
    public static final String WORK_HOURS = "Work Hours";
    public static final String LUNCH = "Lunch";

    //shop category

    //pokKey
    public static final String POK_SIZE = "SIZE";
    public static final String POK_WEIGHT = "WEIGHT";
    public static final String POK_DURATION = "DURATION";
    public static final String POK_PRICE = "PRICE";
    public static final String POK_PROCESS_TIME = "PROCESS_TIME";



    public static final String BLOCK_PAID_TYPE_NO_PAID = "NO_PAID";
    public static final String BLOCK_PAID_TYPE_PAID = "PAID";

    public static final  String BLOCK_UPDATE_TYPE_ONCE = "ONCE";
    public static final  String BLOCK_UPDATE_TYPE_ALL = "ALL";

    public static final  String BLOCK_ITEM_STATUS_PENDING = "PENDING";
    public static final  String BLOCK_ITEM_STATUS_COMPLETED = "COMPLETED";
    public static final  String BLOCK_ITEM_STATUS_CANCEL = "CANCEL";

    public static final List<String> BLOCK_PAID_LIST = new ArrayList<>();
    public static final List<String> BLOCK_WORK_DAY_LIST = new ArrayList<>();
    public static List<String> BLOCK_HOLIDAY_LIST = new ArrayList<>();
    //public static final List<String> BLOCK_NO_PAID_LIST = new ArrayList<>();
    static {
        // 有薪假列表
        BLOCK_PAID_LIST.add(BLOCK_TYPE_BREAK);
        BLOCK_PAID_LIST.add(BLOCK_TYPE_ANNUAL_LEAVE);
        BLOCK_PAID_LIST.add(BLOCK_TYPE_SICK_LEAVE);
        BLOCK_PAID_LIST.add(BLOCK_TYPE_MATERNITY_LEAVE);

        // 工作日block
        BLOCK_WORK_DAY_LIST.add(BLOCK_TYPE_DAY_OFF);
        BLOCK_WORK_DAY_LIST.add(BLOCK_TYPE_BEFORE_WORK_DAY);
        BLOCK_WORK_DAY_LIST.add(BLOCK_TYPE_AFTER_WORK_DAY);
        BLOCK_WORK_DAY_LIST.add(BLOCK_TYPE_LUNCH);

        // 所有假期列表
        BLOCK_HOLIDAY_LIST.add(BLOCK_TYPE_BREAK);
        BLOCK_HOLIDAY_LIST.add(BLOCK_TYPE_ANNUAL_LEAVE);
        BLOCK_HOLIDAY_LIST.add(BLOCK_TYPE_NO_PAID_LEAVE);
        BLOCK_HOLIDAY_LIST.add(BLOCK_TYPE_SICK_LEAVE);
        BLOCK_HOLIDAY_LIST.add(BLOCK_TYPE_MATERNITY_LEAVE);
    }

    public static final String SF_DEMO_DATA_PREFIX="SF_DEMO_";
    
    //send email details
//    
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
//    public static final String VOUCHER_COPY_EMAIL= "admin@senseoftouch.com.hk";
    
    // marketing campaign code 
    
    /**
     * 获取 block type类型获取block item 的paid type 类型
     *
     * @param blockType
     * @return
     */
    public static String getBlockPaidType(String blockType) {
        if (BLOCK_PAID_LIST.contains(blockType)) {
            return BLOCK_PAID_TYPE_PAID;
        }
        return BLOCK_PAID_TYPE_NO_PAID;
    }

    /**
     * 计算这个时间段的天数，如果小于4小时算0.5天，其他算1天
     *
     * @param start
     * @param end
     * @return
     */
    public static double getHoursRangeToDays(DateTime start, DateTime end) {
        int betweenHours = Hours.hoursBetween(start, end).getHours();
        if (betweenHours < 4) {
            return 0.5D;
        }
        return 1D;
    }

    // 获取booking服务的时间范围数据
    /*public static List<TreeVO> getServiceTimeData(Shop shop, Date currentDate) {
        OpeningHours openingHours = shop.getOpeningHour(currentDate);
        DateTime openTime = openingHours.getOpenTimeObj();
        DateTime closeTime = openingHours.getCloseTimeObj();

        List<TreeVO> data = new ArrayList<>();

        // morning
        TreeVO morning = new TreeVO();
        morning.setDisplayName("Morning");
        int startHour = openTime.getHourOfDay();
        addOneHour(morning, startHour, openTime.getMinuteOfHour(), 60); // 开始一个小时
        for (int i = startHour + 1; i <= 12; i++) {
            addOneHour(morning, i, 0, 60);
        }
        data.add(morning);

        int endHour = closeTime.getHourOfDay();
        // afternoon
        TreeVO afternoon = new TreeVO();
        afternoon.setDisplayName("Afternoon");
        int endAfterNoon = endHour > 17 ? 17 : endHour;
        for (int i = 13; i <= endAfterNoon; i++) {
            addOneHour(afternoon, i, 0, 60);
        }
        data.add(afternoon);

        // evening
        int lastMinute = closeTime.getMinuteOfHour();
        if (!(endHour <= 18 && lastMinute == 0)) {
            TreeVO evening = new TreeVO();
            evening.setDisplayName("Evening");
            for (int i = 18; i <= endHour - 1; i++) {
                addOneHour(evening, i, 0, 60);
            }
            // 最后一个小时
            if(lastMinute > 0) {
                addOneHour(evening, endHour, 0, closeTime.getMinuteOfHour());
            }
            data.add(evening);
        }
        return data;
    }

    private static TreeVO addOneHour(TreeVO parent, int hour, int startMinute, int endMinute) {
        TreeVO treeVO = new TreeVO();
        treeVO.setParent(parent);
        if (hour < 10) {
            treeVO.setDisplayName("0" + hour);
        } else {
            treeVO.setDisplayName(Integer.toString(hour));
        }
        addMinuteData(treeVO, startMinute, endMinute);   // 添加 minute
        parent.getChildren().add(treeVO);
        return treeVO;
    }

    private static void addMinuteData(TreeVO hourTree, int startMinute, int endMinute) {
        for (int i = startMinute; i < endMinute; i += CommonConstant.TIME_UNIT) {
            TreeVO minuteVO = new TreeVO();
            if (i < 10) {
                minuteVO.setDisplayName(hourTree.getDisplayName() + ":0" + i);
            } else {
                minuteVO.setDisplayName(hourTree.getDisplayName() + ":" + i);
            }
            minuteVO.setId(minuteVO.getDisplayName());
            hourTree.getChildren().add(minuteVO);
        }
    }*/

    // 时间选择数据
    public static List<TreeVO> TIME_DATA = new ArrayList<>();

    static {
        // 初始化time multi select data
        TIME_DATA.add(addHourData(I18nUtil.getMessageKey("front.label.morning"), 6, 11));
        TIME_DATA.add(addHourData(I18nUtil.getMessageKey("front.label.afternoon"), 12, 17));
        TIME_DATA.add(addHourData(I18nUtil.getMessageKey("front.label.evening"), 18, 23));
    }

    private static TreeVO addHourData(String displayName, int startHour, int entHour) {
        TreeVO treeVO = new TreeVO();
        treeVO.setParent(null);
        treeVO.setDisplayName(displayName);
        for (int i = startHour; i <= entHour; i++) {
            TreeVO hourVO = new TreeVO();
            hourVO.setParent(treeVO);
            if (i < 10) {
                hourVO.setDisplayName("0" + i);
            } else {
                hourVO.setDisplayName(Integer.toString(i));
            }
            addMinuteData(hourVO);
            treeVO.getChildren().add(hourVO);
        }
        return treeVO;
    }

    private static void addMinuteData(TreeVO hourTree) {
        for (int i = 0; i < 60; i += CommonConstant.TIME_UNIT) {
            TreeVO minuteVO = new TreeVO();
            if (i < 10) {
                minuteVO.setDisplayName(hourTree.getDisplayName() + ":0" + i);
            } else {
                minuteVO.setDisplayName(hourTree.getDisplayName() + ":" + i);
            }
            minuteVO.setId(minuteVO.getDisplayName());
            hourTree.getChildren().add(minuteVO);
        }
    }

    // 区域数据
    public static List<TreeVO> DISTRICT_DATA = new ArrayList<>();

    private static void addDistrictChildren(TreeVO parentVO, String childrenStr) {
        String[] children = childrenStr.split(",");
        for (String child : children) {
            TreeVO treeVO = new TreeVO();
            treeVO.setId(child);
            treeVO.setDisplayName(child);
            treeVO.setParent(parentVO);
            parentVO.getChildren().add(treeVO);
        }
        Collections.sort(parentVO.getChildren(), (e1, e2) -> e1.getDisplayName().compareTo(e2.getDisplayName()));
    }

    static {
        // init district data
        TreeVO all = new TreeVO();
        all.setId("ALL");
        all.setDisplayName("All");
        DISTRICT_DATA.add(all);

        // Overseas
        TreeVO overseas = new TreeVO();
        overseas.setId("Overseas");
        overseas.setDisplayName("Overseas");
        overseas.setParent(null);
        DISTRICT_DATA.add(overseas);

        /*// Unknown
        TreeVO unknown = new TreeVO();
        unknown.setId("Unknown");
        unknown.setDisplayName("Unknown");
        unknown.setParent(null);
        DISTRICT_DATA.add(unknown);*/

        // hong kong
        TreeVO hongKong = new TreeVO();
        hongKong.setId("Hong Kong");
        hongKong.setDisplayName("Hong Kong");
        hongKong.setParent(all);
        all.getChildren().add(hongKong);
        String hongKongChildren = "Aberdeen,Ap Lei Chau,Cyberport,Pok Fu Lam,Repulse Bay,Stanley,Wan Chai,Happy Valley,Causeway Bay,Central,Sheung Wan,Chai Wan,Heng Fa Chuen,Sai Wan Ho,North Point,Shau Kei Wan,Siu Sai Wan,Tai Koo,Tin Hau,Fortree Hill,Admiralty,Sai Ying pun,Kennedy Town,Quarry Bay";
        addDistrictChildren(hongKong, hongKongChildren);

        // Kowloon
        TreeVO kowloon = new TreeVO();
        kowloon.setId("Kowloon");
        kowloon.setDisplayName("Kowloon");
        kowloon.setParent(all);
        all.getChildren().add(kowloon);
        String kowloonChildren = "Tsim Sha Tsui,Yau Ma Tei,Mong Kok,Tai Kok Tsui,Cheung Sha Wan,Sham Shui Po,Lai Chi Kok,Mei Foo,Shek Kip Mei,Kowloon Tong,Wong Tai Sin,Diamond Hill,Choi Hung,Tsz Wan Shan,Lok Fu,Hung Hom,Ho Man Tin,Kowloon City,Oi Man Estate,To Kwa Wan,Kowloon Bay,Kwun Tong,Lam Tin,Ngau Tau Kok,Sau Mau Ping,Yau Tong,Jordan,Prince Edward,Tiu King Leng,Hang Hau,Po Lam,Lohas Park,Olympic";
        addDistrictChildren(kowloon, kowloonChildren);

        // New Territories
        TreeVO newTerritories = new TreeVO();
        newTerritories.setId("New Territories");
        newTerritories.setDisplayName("New Territories");
        newTerritories.setParent(all);
        all.getChildren().add(newTerritories);
        String newTerritoriesChildren = "Fanling,Sha Tau Kok,Sheung Shui,Tsing Yi,Kwai Chung,Kwai Fong,Lai King,Kwai Hing,Yuen Long,Kam Tin,San Tin,Tin Shui Wai,Fo Tan,Ma On Shan,Sha Tin,Sha Tin Wai,Tuen Mun,Tai Po,Tseung Kwan O,Sai Kung,Tsuen Wan,Tai Wai,Tai Po Market,Lo Wu,Lok Ma Chau,Che Hung Temple,City One,Shek Mun,Tai Shui Hang,Heng On,Wu Kai Sha,Siu Hong,Long Ping,Nam Cheong,Tai Wo,University,Clearwater Bay";
        addDistrictChildren(newTerritories, newTerritoriesChildren);

        // Outlying Islands
        TreeVO outlyingIslands = new TreeVO();
        outlyingIslands.setId("Outlying Islands");
        outlyingIslands.setDisplayName("Outlying Islands");
        outlyingIslands.setParent(all);
        all.getChildren().add(outlyingIslands);
        String outlyingIslandsChildren = "Cheung Chau,Discovery Bay,Lamma Island,Mui Wo,Peng Chau,Tai O,Tung Chung,Sunny Bay";
        addDistrictChildren(outlyingIslands, outlyingIslandsChildren);
    }

}
