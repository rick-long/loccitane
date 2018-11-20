package org.spa.serviceImpl.prepaid;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.spa.email.EmailAddress;
import com.spa.jxlsBean.importDemo.PrepaidImportGiftBean;
import com.spa.thread.CashVoucherThread;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.prepaid.PrepaidDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.company.Company;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.order.StaffCommission;
import org.spa.model.payment.Payment;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOption;
import org.spa.model.product.ProductOptionAttribute;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.service.order.PurchaseItemService;
import org.spa.service.order.PurchaseOrderService;
import org.spa.service.order.StaffCommissionService;
import org.spa.service.payment.PaymentMethodService;
import org.spa.service.payment.PaymentService;
import org.spa.service.points.LoyaltyPointsTransactionService;
import org.spa.service.prepaid.PrepaidService;
import org.spa.service.prepaid.PrepaidTopUpTransactionService;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductOptionAttributeService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.*;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.loyalty.PointsAdjustVO;
import org.spa.vo.prepaid.PrepaidAddVO;
import org.spa.vo.sales.OrderItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;
import com.spa.jxlsBean.ExcelUtil;
import com.spa.jxlsBean.importDemo.PrepaidImportJxlsBean;

import static com.spa.constant.CommonConstant.*;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class PrepaidServiceImpl extends BaseDaoHibernate<Prepaid> implements PrepaidService {

    public static final int time_range_expring_package = 2;
    public static final String USED_PREPAID_MAP = "USED_PREPAID_MAP";
    public static final String USED_PREPAID_UNIT_MAP = "USED_PREPAID_UNIT_MAP";
    public static final String PRINT_VOUCHER_URL = "/prepaid/vouchertemplate";
    @Autowired
    public CategoryService categoryService;

    @Autowired
    public ProductOptionService productOptionService;

    @Autowired
    public ShopService shopService;

    @Autowired
    public UserService userService;

    @Autowired
    public PrepaidTopUpTransactionService prepaidTopUpTransactionService;

    @Autowired
    public PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseItemService purchaseItemService;

    @Autowired
    public StaffCommissionService staffCommissionService;

    @Autowired
    public PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    public PrepaidDao prepaidDao;

    @Autowired
    public LoyaltyPointsTransactionService loyaltyPointsTransactionService;

    @Autowired
    public UserLoyaltyLevelService userLoyaltyLevelService;

    @Autowired
    public ProductOptionAttributeService productOptionAttributeService;


    private static final Logger logger = Logger.getLogger(CollectionUtils.class);

    @Override
    public Double getCalCommissionRateForPrepaid(PrepaidAddVO prepaidAddVO) {

        Boolean buyPrepaidHasCommission = CommonConstant.BUY_PREPAID_HAS_COMMISSION;

        if (prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_CASH_VOUCHER)
                || prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
            return 0d;
        }

        Double commissionRate =  CommonConstant.PACKAGE_COMMISSION_RATE_FOR_NEW;

        Long memberId = prepaidAddVO.getMemberId();
        User member = userService.get(memberId);

        if (buyPrepaidHasCommission && member.isMember()) {
//


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = calendar.getTime();
            calendar.add(Calendar.YEAR, -2);
            Date pass2Years = calendar.getTime();

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PurchaseOrder.class);

            detachedCriteria.add(Restrictions.eq("isActive", true));
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));

            detachedCriteria.add(Restrictions.ge("purchaseDate",pass2Years));

            detachedCriteria.add(Restrictions.le("purchaseDate",today));
            detachedCriteria.add(Restrictions.eq("user.id", memberId));
            detachedCriteria.add(Restrictions.eq("shop.id", prepaidAddVO.getShopId()));

            List<PurchaseOrder> orderList=purchaseOrderService.list(detachedCriteria);
//System.out.println("pass2Years ---"+pass2Years+"---today---"+today+"--orderList----"+orderList.size());
            if(orderList !=null && orderList.size()>0){
                commissionRate = CommonConstant.PACKAGE_COMMISSION_RATE_FOR_RENEWAL;
//                    System.out.println("prepaidAddVO.getPtId() ---"+prepaidAddVO.getPtId()+"---prepaidAddVO.getPtId()---"+prepaidAddVO.getPtId()+
//                            "--prepaidAddVO.getIsTopUp()----"+prepaidAddVO.getIsTopUp());
                if((prepaidAddVO.getId() !=null || prepaidAddVO.getPtId() !=null) && orderList.size()==1){
                    if(prepaidAddVO.getIsTopUp() !=null && prepaidAddVO.getIsTopUp()){

                    }else{
                        commissionRate = CommonConstant.PACKAGE_COMMISSION_RATE_FOR_NEW;
                    }
                }
            }

        }
        return commissionRate;
    }

    @Override
    public Prepaid returnPrepaid(PrepaidAddVO prepaidAddVO) {
        Date now = new Date();
        String loginUser = WebThreadLocal.getUser().getUsername();

        String prepaidType = prepaidAddVO.getPrepaidType();
        Prepaid prepaid = null;
        if (prepaidAddVO.getId() != null && prepaidAddVO.getId().longValue() > 0) {
            prepaid = get(prepaidAddVO.getId());
            prepaid.setLastUpdated(now);
            prepaid.setLastUpdatedBy(loginUser);
            if (prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_CASH_VOUCHER) || prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
                prepaid.setReference(prepaidAddVO.getReferenceBackUp());
            }
            prepaid.setIsActive(Boolean.parseBoolean(prepaidAddVO.getIsActive()));
        } else {
            prepaid = new Prepaid();
            prepaid.setIsOnline(false);
            prepaid.setIsRedeem(false);

            if (prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_CASH_VOUCHER) || prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
                prepaid.setReference(prepaidAddVO.getReferenceBackUp());
            } else {
                if(StringUtils.isNotBlank(prepaidAddVO.getReferenceBackUp())){
                    prepaid.setReference(prepaidAddVO.getReferenceBackUp());
                }else {
                    prepaid.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_REF_PREFIX));
                }
            }
            if (prepaidAddVO.getIsRedeem() != null && prepaidAddVO.getIsRedeem()) {
                prepaid.setIsRedeem(prepaidAddVO.getIsRedeem());
                prepaid.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_REF_PREFIX));
            }
            if (prepaidAddVO.getIsOnline() != null && prepaidAddVO.getIsOnline()) {
                prepaid.setIsOnline(prepaidAddVO.getIsOnline());
                prepaid.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_REF_PREFIX));
            }
            prepaid.setIsActive(true);
            prepaid.setCreated(now);
            prepaid.setCreatedBy(loginUser);
            prepaid.setCompany(WebThreadLocal.getCompany());
        }
        prepaid.setName(prepaidAddVO.getPrepaidName());
        prepaid.setRemarks(prepaidAddVO.getRemarks());

        prepaid.setPrepaidType(prepaidAddVO.getPrepaidType());

        prepaid.setPackageType(null);
        prepaid.setIsTransfer(null);
        prepaid.setIsFree(null);

        prepaid.setAdditionalEmail(prepaidAddVO.getAdditionalEmail());
        prepaid.setAdditionalMessage(prepaidAddVO.getAdditionalMessage());
        prepaid.setAdditionalName(prepaidAddVO.getAdditionalName());

        if (prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_CASH_PACKAGE)) {
            if (StringUtils.isNotBlank(prepaidAddVO.getPackageType())) {
                prepaid.setPackageType(prepaidAddVO.getPackageType());
            }
        }
        Double prepaidValue = prepaidAddVO.getPrepaidValue();
        Double initValue = prepaidAddVO.getInitValue();

        if (prepaidType.equals(PREPAID_TYPE_TREATMENT_PACKAGE) || prepaidType.equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
            if (prepaidType.equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
                if (StringUtils.isNotBlank(prepaidAddVO.getIsTransfer())) {
                    prepaid.setIsTransfer(Boolean.valueOf(prepaidAddVO.getIsTransfer()));
                }
                if (StringUtils.isNotBlank(prepaidAddVO.getIsFree())) {
                    prepaid.setIsFree(Boolean.valueOf(prepaidAddVO.getIsFree()));
                }
                initValue = 1d;
            }
        }
        prepaid.setPrepaidValue(prepaidValue);
        prepaid.setInitValue(initValue);
        if (prepaidAddVO.getId() != null && prepaidAddVO.getId().longValue() > 0) {
            prepaid.setRemainValue(prepaidAddVO.getRemainValue());
        } else {
            prepaid.setRemainValue(initValue);
        }

        if (prepaidAddVO.getIsAllCompanyUse() != null) {
            prepaid.setIsAllCompanyUse(prepaidAddVO.getIsAllCompanyUse());
        } else {
            prepaid.setIsAllCompanyUse(true);
        }

        if (prepaidAddVO.getShopId() != null) {
            Shop shop = shopService.get(prepaidAddVO.getShopId());
            prepaid.setShop(shop);
        }

        if (prepaidAddVO.getMember() != null) {
            prepaid.setUser(prepaidAddVO.getMember());
        } else {
            User member = userService.get(prepaidAddVO.getMemberId());
            prepaid.setUser(member);
        }

        prepaid.setPickUpType(prepaidAddVO.getPickUpType());
        prepaid.setPickUpLocation(prepaidAddVO.getPickUpLocation());

        saveOrUpdate(prepaid);

        //saveOrUpdate top up transaction
        savePrepaidTopUpTransaction(prepaid, prepaidAddVO, false);

        return prepaid;
    }

    public void savePrepaidTopUpTransaction(Prepaid prepaid, PrepaidAddVO prepaidAddVO, Boolean isTopUp) {
        Date now = new Date();
        String loginUser = WebThreadLocal.getUser().getUsername();

        String packageType = null;
        Category category = null;
        ProductOption po = null;

        Double prepaidValue = prepaidAddVO.getPrepaidValue();
        Double initValue = prepaidAddVO.getInitValue();

        String prepaidType = prepaidAddVO.getPrepaidType();
        if (prepaidType.equals(PREPAID_TYPE_TREATMENT_PACKAGE) || prepaidType.equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
            if (prepaidAddVO.getPo() != null) {
                po = prepaidAddVO.getPo();
                category = po.getProduct().getCategory();
            } else {
                if (prepaidAddVO.getCategoryId() != null && prepaidAddVO.getCategoryId().longValue() > 0) {
                    category = categoryService.get(prepaidAddVO.getCategoryId());
                }
                if (prepaidAddVO.getProductOptionId() != null && prepaidAddVO.getProductOptionId().longValue() > 0) {
                    po = productOptionService.get(prepaidAddVO.getProductOptionId());
                }
            }

            if (prepaidType.equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
                initValue = 1d;
            }

        } else {
            if (prepaidType.equals(PREPAID_TYPE_CASH_PACKAGE)) {
                if (StringUtils.isNotBlank(prepaidAddVO.getPackageType())) {
                    packageType = prepaidAddVO.getPackageType();
                }
            }
        }
        PrepaidTopUpTransaction ptt = null;
        Boolean isEdit = false;
        if (isTopUp) {
            //充值prepaid
            //将所有有效的transaction，没有过期的remain value 累加到prepaid的remain value上
            //将所有有效的transaction的top upp value累加到prepaid的prepaid value上
            prepaid.setName(prepaidAddVO.getPrepaidName());
            prepaid.setPrepaidValue(prepaid.getPrepaidValue().doubleValue() + prepaidValue.doubleValue());
            prepaid.setInitValue(prepaid.getInitValue().doubleValue() + initValue.doubleValue());
            prepaid.setRemainValue(prepaid.getRemainValue().doubleValue() + initValue.doubleValue());
            saveOrUpdate(prepaid);

            ptt = new PrepaidTopUpTransaction();
            ptt.setIsActive(true);
            ptt.setPrepaid(prepaid);
            ptt.setCompany(WebThreadLocal.getCompany());
            ptt.setTopUpReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_TOP_UP_REF_PREFIX));
            ptt.setCreated(now);
            ptt.setIsRoot(false);
            ptt.setCreatedBy(loginUser);

        } else {
            //添加或者编辑 prepaid
            if (prepaidAddVO.getId() != null && prepaidAddVO.getId().longValue() > 0) {
                //编辑

                if (prepaidAddVO.getPtId() != null && prepaidAddVO.getPtId().longValue() > 0) {
                    //编辑 prepaid top up transaction
                    ptt = prepaidTopUpTransactionService.get(prepaidAddVO.getPtId());

                    Double calPrepaidVal = prepaid.getPrepaidValue() - ptt.getTopUpValue() + prepaidValue;
                    Double calInitVal = prepaid.getInitValue() - ptt.getTopUpInitValue() + initValue;
                    Double calRemainVal = prepaid.getRemainValue() - ptt.getRemainValue() + prepaidAddVO.getRemainValue();
                    String endPrefix = prepaidAddVO.getPrepaidType().replace("_", " ").toLowerCase();

                    String prepaidName = "";
                    if (prepaidAddVO.getPrepaidType().startsWith("CASH")) {
                        prepaidName = "$" + calPrepaidVal.toString() + endPrefix;
                    } else {
                        prepaidName = calInitVal.toString() + " units " + endPrefix;
                    }
                    prepaid.setName(prepaidName);

                    prepaid.setPrepaidValue(calPrepaidVal);
                    prepaid.setInitValue(calInitVal);
                    prepaid.setRemainValue(calRemainVal);
                    saveOrUpdate(prepaid);


                } else {
                    //编辑prepaid
                    ptt = prepaidTopUpTransactionService.get("prepaid.id", prepaidAddVO.getId());
                }
                isEdit = true;
                ptt.setLastUpdated(now);
                ptt.setLastUpdatedBy(loginUser);
                ptt.setIsActive(Boolean.parseBoolean(prepaidAddVO.getIsActive()));

            } else {
                //添加prepaid
                ptt = new PrepaidTopUpTransaction();
                ptt.setIsActive(true);
                ptt.setIsRoot(true);
                ptt.setPrepaid(prepaid);
                ptt.setCompany(WebThreadLocal.getCompany());
                ptt.setTopUpReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_TOP_UP_REF_PREFIX));
                ptt.setCreated(now);
                ptt.setCreatedBy(loginUser);
            }
        }
        //status
        if (StringUtils.isNoneEmpty(prepaidAddVO.getStatus())) {
            ptt.setStatus(prepaidAddVO.getStatus());
        } else {
            ptt.setStatus(CommonConstant.PREPAID_TOPUP_TRANSACTION_STATUS_NORMAL);
        }
        ptt.setExtraDiscount(prepaidAddVO.getExtraDiscount());

        if (isTopUp) {
            String[] packages = new String[2];
            packages[0] = PREPAID_TYPE_CASH_PACKAGE;
            packages[1] = PREPAID_TYPE_TREATMENT_PACKAGE;
//			Long memberId = prepaid.getUser().getId();
            DetachedCriteria pttDC = DetachedCriteria.forClass(PrepaidTopUpTransaction.class);
//			pttDC.createAlias("prepaid", "p");
//			pttDC.add(Restrictions.eq("p.user.id",memberId));
            pttDC.add(Restrictions.eq("prepaid.id", prepaid.getId()));
            pttDC.add(Restrictions.eq("isActive", true));
            pttDC.add(Restrictions.gt("remainValue", 0d));
            pttDC.add(Restrictions.in("prepaidType", packages));

            List<PrepaidTopUpTransaction> pttList = prepaidTopUpTransactionService.list(pttDC);
            if (pttList != null && pttList.size() > 0) {
                for (PrepaidTopUpTransaction pttold : pttList) {
                    pttold.setExpiryDate(DateUtil.getLastMinuts(prepaidAddVO.getExpiryDate()));
                    pttold.setLastUpdated(now);
                    pttold.setLastUpdatedBy(loginUser);
                    prepaidTopUpTransactionService.saveOrUpdate(pttold);
                }
            }
        }
        ptt.setExpiryDate(DateUtil.getLastMinuts(prepaidAddVO.getExpiryDate()));
        ptt.setTopUpDate(prepaidAddVO.getPurchaseDate());

        ptt.setCommissionRate((prepaidAddVO.getCommissionRate() != null ? prepaidAddVO.getCommissionRate() : 0d));

        ptt.setTopUpValue(prepaidValue);

        if (!isTopUp && (prepaidAddVO.getId() != null && prepaidAddVO.getId().longValue() > 0)) {
            ptt.setRemainValue(prepaidAddVO.getRemainValue() != null ? prepaidAddVO.getRemainValue() : 0);
        } else {
            if(prepaidAddVO.getImportPrepaids() !=null && prepaidAddVO.getImportPrepaids()){
                ptt.setRemainValue(prepaidAddVO.getRemainValue().doubleValue());
            }else {
                ptt.setRemainValue(initValue);
            }
        }

        ptt.setTopUpInitValue(initValue);

        ptt.setCategory(category);
        ptt.setProductOption(po);

        //top up/edit 时 可能shop 和prepaid的shop不一致
        if (prepaidAddVO.getShopId() != null) {
            Shop shop = shopService.get(prepaidAddVO.getShopId());
            ptt.setShop(shop);
        }

        ptt.setPackageType(packageType);
        ptt.setPrepaidType(prepaidType);

        ptt.setRemarks(prepaidAddVO.getRemarks());


        if (StringUtils.isNoneBlank(prepaidAddVO.getPtRemarks())) {
            ptt.setRemarks(prepaidAddVO.getPtRemarks());
        }
        prepaidTopUpTransactionService.saveOrUpdate(ptt);

        //saveOrUpdate order and item
        savePurchaseOrder(ptt, prepaidAddVO, isEdit);

    }

    private void savePurchaseOrder(PrepaidTopUpTransaction ptt, PrepaidAddVO prepaidAddVO, Boolean isEdit) {
        Date now = new Date();
        String loginUser = WebThreadLocal.getUser().getUsername();

        // cal discount
//		double publicDiscount=0;
//		double finalDiscount=publicDiscount;
        double finalDiscount = 0;
        double extraDiscount = 0;
        if (ptt.getExtraDiscount() != null) {
            extraDiscount = ptt.getExtraDiscount().doubleValue();
        }
        finalDiscount += extraDiscount;

        prepaidAddVO.setEffectiveValueForCommission(ptt.getTopUpValue());
        Double effectiveValue = ptt.getTopUpValue() - finalDiscount;
        Double amount = effectiveValue;

        PurchaseOrder order = null;
        PurchaseItem item = null;
        if (isEdit) {
            item = ptt.getPurchaseItems().iterator().next();
            order = item.getPurchaseOrder();
            order.setLastUpdated(now);
            order.setLastUpdatedBy(loginUser);
        } else {
            order = new PurchaseOrder();
            order.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PURCHASE_ORDER_REF_PREFIX));
            if (StringUtils.isNotBlank(prepaidAddVO.getOrderStatus())) {
                order.setStatus(prepaidAddVO.getOrderStatus());
            } else {
                order.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
            }

            order.setCompany(WebThreadLocal.getCompany());
            order.setCreated(now);
            order.setCreatedBy(loginUser);
            order.setIsActive(true);
        }

        order.setPurchaseDate(ptt.getTopUpDate());

        order.setTotalAmount(amount);
        order.setTotalDiscount(finalDiscount);

        order.setShop(ptt.getShop());
        order.setUser(ptt.getPrepaid().getUser());

        purchaseOrderService.save(order);

        //set items
        if (isEdit) {
            item = ptt.getPurchaseItems().iterator().next();
            item.setLastUpdated(now);
            item.setLastUpdatedBy(loginUser);
        } else {
            item = new PurchaseItem();
            item.setQty(1);
            //set bought prepaid
            item.setBuyPrepaidTopUpTransaction(ptt);

            item.setStatus(order.getStatus());
            item.setIsActive(true);
            item.setCreated(now);
            item.setCreatedBy(loginUser);
        }
        item.setPurchaseOrder(order);
        item.setPrice(ptt.getTopUpValue());
        item.setEffectiveValue(effectiveValue);
        item.setAmount(amount);
        item.setDiscountValue(finalDiscount);
        item.setExtraDiscountValue(extraDiscount);

        purchaseItemService.save(item);

        //set payments
        setPayments(item, prepaidAddVO, isEdit);

        String pointsAction = "";
        String earnChannel = "";
        if (!ptt.getPrepaid().getIsRedeem()) {// it is not a redeem record
            earnChannel = CommonConstant.POINTS_EARN_CHANNEL_PREPAID;
            pointsAction = CommonConstant.POINTS_ACTION_PLUS;
            if (!ptt.getPrepaid().getIsOnline()) {// this is not a online record
                //set commission (There is no commission when buy a Pre-paid)
                setStaffCommission(item, prepaidAddVO, isEdit);

                //earn spa points and review loyalty level and hide by Ivy on 2018-10-26
//                if (isEdit) {
//                    userLoyaltyLevelService.reviewLoyaltyLevelV2(CommonConstant.ACTION_EDIT, order.getUser().getId(), ptt.getPrepaidType(), 0d);
//                } else {
//                    userLoyaltyLevelService.reviewLoyaltyLevelV2(CommonConstant.ACTION_ADD, order.getUser().getId(), ptt.getPrepaidType(), 0d);
//                }
            }// this is a online record

        } else {//this is a redeem record
            pointsAction = CommonConstant.POINTS_ACTION_MINUS;
            earnChannel = CommonConstant.POINTS_EARN_CHANNEL_REDEEM;
        }
        //set loyalty points
        if (!ptt.getPrepaid().getIsOnline() && !ptt.getPrepaid().getIsRedeem()) {
            // it is not a redeem and not a online record
            setLoyaltyPoints(earnChannel, order, ptt.getTopUpValue(), pointsAction);
        }

    }

    private void setLoyaltyPoints(String earnChannel, PurchaseOrder purchaseOrder, Double earnPoints, String pointAction) {

        PointsAdjustVO spaPointsAdjustVO = new PointsAdjustVO();

        spaPointsAdjustVO.setEarnChannel(earnChannel);
        spaPointsAdjustVO.setEarnDate(purchaseOrder.getPurchaseDate());

        if (earnChannel.equals(CommonConstant.POINTS_EARN_CHANNEL_PREPAID)) {

            spaPointsAdjustVO.setMonthLimit(CommonConstant.LOYALTY_POINTS_EXPIRED_MONTHS_BY_PREPAID);

        } else if (earnChannel.equals(CommonConstant.POINTS_EARN_CHANNEL_SALES)) {

            spaPointsAdjustVO.setMonthLimit(CommonConstant.LOYALTY_POINTS_EXPIRED_MONTHS_BY_SALES);
        }

        spaPointsAdjustVO.setPurchaseOrder(purchaseOrder);
        spaPointsAdjustVO.setUser(purchaseOrder.getUser());

        spaPointsAdjustVO.setAdjustPoints(earnPoints);
        spaPointsAdjustVO.setAction(pointAction);

        loyaltyPointsTransactionService.saveLoyaltyPointsTransaction(spaPointsAdjustVO);
    }

    private void setPayments(PurchaseItem item, PrepaidAddVO prepaidAddVO, Boolean isEdit) {

        Double effectiveValueForCommission = prepaidAddVO.getEffectiveValueForCommission();
        List<KeyAndValueVO> paymentMethods = prepaidAddVO.getPaymentMethods();
        if (paymentMethods != null && paymentMethods.size() > 0) {
            Payment payment = null;
            Double totalAmount = 0d;
            for (KeyAndValueVO kv : paymentMethods) {
                if (kv != null && kv.getKey() != null) {
                    String key = kv.getKey();
                    payment = paymentService.getPaymentsByOrderIdAndDisplayOrderWhenBuyPrepaid(item.getPurchaseOrder().getId(), Integer.valueOf(key));
                    if (kv.getId() != null && kv.getId().longValue() > 0) {
                        PaymentMethod pm = paymentMethodService.get(kv.getId());
                        if (payment == null) {
                            payment = new Payment();
                            payment.setCompany(WebThreadLocal.getCompany());
                            payment.setIsActive(true);
                            payment.setCreated(item.getCreated());
                            payment.setCreatedBy(item.getCreatedBy());
                            payment.setPurchaseOrder(item.getPurchaseOrder());
                            payment.setDisplayOrder(Integer.valueOf(key));
                        } else {
                            payment.setLastUpdated(item.getLastUpdated());
                            payment.setLastUpdatedBy(item.getLastUpdatedBy());
                        }

                        Double amount = 0D;
                        String value = kv.getValue();
                        if (StringUtils.isNotBlank(value)) {
                            amount = Double.valueOf(value);
                        }
                        if (pm.getReference().equals(CommonConstant.PAYMENT_METHOD_REF_NO_PAYMENT)) {
                            // no need to add the amount
                            effectiveValueForCommission = effectiveValueForCommission - amount;
                        } else {
                            totalAmount = totalAmount + amount;
                        }
                        payment.setAmount(amount);
                        payment.setPaymentMethod(pm);
                        if (StringUtils.isNoneEmpty(prepaidAddVO.getPaymentStatus())) {
                            payment.setStatus(prepaidAddVO.getPaymentStatus());
                        } else {
                            payment.setStatus(CommonConstant.PAYMENT_STATUS_PAID);
                        }
                        paymentService.saveOrUpdate(payment);
                    } else {
                        if (payment != null) {
                            //delete
                            paymentService.delete(payment.getId());
                        }
                    }
                }
            }
//			System.out.println("totalAmount---"+totalAmount+"--effectiveValueForCommission-"+effectiveValueForCommission);
            //reset item amount
            item.setAmount(totalAmount);

            //reset sc amount
            int therapistSize = item.getStaffCommissions().size();
            for (StaffCommission sc : item.getStaffCommissions()) {
                sc.setAmount(totalAmount / therapistSize);
                staffCommissionService.saveOrUpdate(sc);
            }
//			item.setPrice(totalAmount);
            purchaseItemService.saveOrUpdate(item);

            PurchaseOrder order = item.getPurchaseOrder();
            //reset order amount
            order.setTotalAmount(totalAmount);
            purchaseOrderService.saveOrUpdate(order);

            prepaidAddVO.setEffectiveValueForCommission(effectiveValueForCommission);
        }
    }

    private void setStaffCommission(PurchaseItem item, PrepaidAddVO prepaidAddVO, Boolean isEdit) {
        Double effectiveValueForCommission = prepaidAddVO.getEffectiveValueForCommission();
        //saveOrUpdate staff commission
        List<KeyAndValueVO> therapists = prepaidAddVO.getTherapists();
        if (therapists != null && therapists.size() > 0) {
            int therapistSize = 0;
            for (KeyAndValueVO kv : therapists) {
                if (kv.getId() != null && kv.getId().longValue() > 0) {
                    therapistSize++;
                }
            }

            item.getStaffCommissions().clear();

            StaffCommission sc = null;
            for (KeyAndValueVO kv : therapists) {
                if (kv != null && kv.getKey() != null) {
                    String displayOrder = kv.getKey();
                    sc = staffCommissionService.getStaffCommissionByItemAndDispalyOrder(item.getId(), Integer.valueOf(displayOrder));

                    Long staffId = kv.getId();
                    if (staffId != null && staffId.longValue() > 0) {
                        if (sc != null) {
                            sc.setLastUpdated(item.getLastUpdated());
                            sc.setLastUpdatedBy(item.getLastUpdatedBy());
                        } else {
                            sc = new StaffCommission();
                            sc.setIsActive(true);
                            sc.setIsRequested(false);
                            sc.setCreated(item.getCreated());
                            sc.setCreatedBy(item.getCreatedBy());
                            sc.setDisplayOrder(Integer.valueOf(displayOrder));
                            sc.setPurchaseItem(item);
                        }
                        double amount = effectiveValueForCommission / therapistSize;
                        double effectiveVal = item.getEffectiveValue() / therapistSize;
                        double commissionRate = prepaidAddVO.getCommissionRate();
                        double commission = amount * commissionRate;
                        sc.setAmount(amount);
                        sc.setEffectiveValue(effectiveVal);
                        sc.setCommissionRate(commissionRate);
                        sc.setCommissionValue(commission);
                        sc.setPurchaseDate(prepaidAddVO.getPurchaseDate());
                        sc.setStaff(userService.get(staffId));

                        staffCommissionService.saveOrUpdate(sc);
                    } else {
                        if (sc != null) {
                            //delete
                            staffCommissionService.delete(sc.getId());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void deletePrepaid(Long prepaidId) {
        Prepaid prepaid = get(prepaidId);
        //top up transaction
        Set ptt = prepaid.getPrepaidTopUpTransactions();
        if (ptt != null && ptt.size() > 0) {
            Iterator<PrepaidTopUpTransaction> it = ptt.iterator();
            while (it.hasNext()) {
                deletePrepaidTopUpTransaction(it.next().getId());
            }
        }
        delete(prepaidId);
    }

    @Override
    public void deletePrepaidTopUpTransaction(Long prepaidTopUpTransactionId) {
        PrepaidTopUpTransaction ptt = prepaidTopUpTransactionService.get(prepaidTopUpTransactionId);
        PurchaseItem item = ptt.getFirstPurchaseItem();
        PurchaseOrder order = item.getPurchaseOrder();

        // staff commission
        item.getStaffCommissions().clear();

        //delete item
        order.getPurchaseItems().clear();

        //loyalty points
        order.getLoyaltyPointsTransactions().clear();

        //payments
        order.getPayments().clear();

        //delete purchase order discount/production code

//		//member upgrade/downgrade level plugin

//        userLoyaltyLevelService.reviewLoyaltyLevelV2(CommonConstant.ACTION_DELETE, order.getUser().getId(), ptt.getPrepaidType(), ptt.getRemainValue());

        //delete purchase order
        purchaseOrderService.delete(order.getId());

        //review prepaid
        Prepaid prepaid = ptt.getPrepaid();
        String endPrefix = ptt.getPrepaidType().replace("_", " ").toLowerCase();
        Double calPrepaidVal = prepaid.getPrepaidValue() - ptt.getTopUpValue();
        Double calInitVal = prepaid.getInitValue() - ptt.getTopUpInitValue();
        String prepaidName = "";
        if (ptt.getPrepaidType().startsWith("CASH")) {
            prepaidName = "$" + calPrepaidVal.toString() + endPrefix;
        } else {
            prepaidName = calInitVal.toString() + " units " + endPrefix;
        }
        prepaid.setName(prepaidName);
        prepaid.setPrepaidValue(prepaid.getPrepaidValue().doubleValue() - ptt.getTopUpValue());
        prepaid.setInitValue(prepaid.getInitValue().doubleValue() - ptt.getTopUpInitValue());
        prepaid.setRemainValue(prepaid.getRemainValue().doubleValue() - ptt.getRemainValue());
        saveOrUpdate(prepaid);

        //delete
        prepaidTopUpTransactionService.delete(ptt);

    }

    @Override
    public Set<Prepaid> getSuitablePackagesByFilter(Long memberId, OrderItemVO orderItemVO, String prepaidSuitableOption, Boolean usingCashPackage) {

        String prodType = null;
        if (orderItemVO.getProductOptionId() != null) {
            prodType = productOptionService.get(orderItemVO.getProductOptionId()).getProductType();
        }
        List<Prepaid> prepaids = prepaidDao.getSuitablePackagesByFilter(memberId, orderItemVO.getProductOptionId(), prepaidSuitableOption, prodType, usingCashPackage);
        //
        if (prodType != null && !prodType.equals(CommonConstant.CATEGORY_REF_GOODS) && orderItemVO.getProductOptionId() != null) {
            Long currentCompanyId = WebThreadLocal.getCompany().getId();
            for (Prepaid prepaid : prepaids) {
                if(prepaid.getRemainingValueByAvailableTransaction() <=0){
                    prepaids.remove(prepaid);
                    continue;
                }
                if (prepaidSuitableOption.equals(CommonConstant.PREPAID_SUITABLE_OPTION_LESS_THAN)
                        && (orderItemVO.getAmount() != null && orderItemVO.getAmount().longValue() > 0)) {

                    Double avargeVal = prepaid.getPrepaidValue().doubleValue() / prepaid.getInitValue().doubleValue();
                    Double totalVal = avargeVal.doubleValue() * orderItemVO.getQty();
                    if (orderItemVO.getAmount() > totalVal.doubleValue()) {
                        prepaids.remove(prepaid);
                    }
                }
                if (prepaid.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_PACKAGE)
                        && (currentCompanyId.longValue() != prepaid.getCompany().getId().longValue())
                        && !prepaid.getIsAllCompanyUse()) {
                    //如果添加的treatment package是一个只能在自己公司使用的，当在其它公司时采用预付付款时， 需要过滤掉此prepaid
                    prepaids.remove(prepaid);
                }
            }
        }

        Set<Prepaid> prepaidSet = new HashSet(prepaids);
        return prepaidSet;
    }

    @Override
    public void reviewPrepaidWhenPaidByPrepaid(OrderItemVO orderItemVO, Long prepaidId, Long purchaseItemId) {

        Prepaid prepaid = get(prepaidId);
        PurchaseItem item = purchaseItemService.get(purchaseItemId);

        Company company = item.getPurchaseOrder().getCompany();

        Double itemAmount = item.getAmount();
        Double totalQty = new Double(item.getQty());

        logger.debug("reviewPrepaidWhenPaidByPrepaid start item amount ----" + itemAmount);

        //payment method
        String paymentMethodRef = CommonConstant.PAYMENT_METHOD_REF_VOUCHER;
        if (prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_PACKAGE)
                || prepaid.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_PACKAGE)) {
            paymentMethodRef = CommonConstant.PAYMENT_METHOD_REF_PACKAGE;
        }
        PaymentMethod pm = paymentMethodService.getPaymentMethodByRef(paymentMethodRef, company.getId());

        boolean isBreak = false;
        double prepaidRemainValue = prepaid.getRemainValue().doubleValue();
        double usedAmount = 0d;
        //review prepaid top up transactions
        List<PrepaidTopUpTransaction> ptts = prepaidTopUpTransactionService.getActivePrepaidTopUpTransactionsByPrepaid(prepaid);
        for (PrepaidTopUpTransaction ptt : ptts) {
            //cash package or cash voucher
            if (prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_PACKAGE)
                    || prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_VOUCHER)) {
                logger.debug("start cal cash itemAmount{},ptt.getRemainValue{}," + itemAmount + "," + ptt.getRemainValue());
                if (itemAmount <= ptt.getRemainValue()) {
                    usedAmount = itemAmount;
                    prepaidRemainValue = prepaidRemainValue - usedAmount;
                    ptt.setRemainValue(ptt.getRemainValue() - usedAmount);
                    itemAmount = 0d;
                    isBreak = true;
                } else {
                    usedAmount = ptt.getRemainValue();
                    prepaidRemainValue = prepaidRemainValue - usedAmount;
                    itemAmount = itemAmount - usedAmount;
                    ptt.setRemainValue(0d);
                }
                logger.debug("end cal cash itemAmount{},ptt.getRemainValue{}," + itemAmount + "," + ptt.getRemainValue());
            } else {
                //treatment package or treatment voucher
                Double remainUnit = ptt.getRemainValue();
                if (totalQty == 0d) {
                    break;
                }
                if (remainUnit == 0) {
                    continue;
                }
                //单次的平均值
                logger.debug("start cal treatment itemAmount{},ptt.getRemainValue{}," + itemAmount + "," + ptt.getRemainValue());
                double prepaidValueForOneUnit = ptt.getTopUpValue() / ptt.getTopUpInitValue();
                if (totalQty > remainUnit) {
                    usedAmount += prepaidValueForOneUnit * remainUnit;
                    ptt.setRemainValue(0d);
                    prepaidRemainValue = prepaidRemainValue - remainUnit;
                    totalQty = totalQty - remainUnit;
                    itemAmount = totalQty * item.getPrice();
                } else {
                    usedAmount += prepaidValueForOneUnit * totalQty;
                    prepaidRemainValue = prepaidRemainValue - totalQty;
                    ptt.setRemainValue(ptt.getRemainValue() - totalQty);
                    totalQty = 0d;
                    itemAmount = 0d;
                    isBreak = true;
                }
            }
            logger.debug("end cal treatment itemAmount{},ptt.getRemainValue{}," + itemAmount + "," + ptt.getRemainValue());
            if (ptt.getRemainValue() <= 0) {
                ptt.setIsActive(false);
            }

            prepaidTopUpTransactionService.saveOrUpdate(ptt);
            //saveOrUpdate payment
            Payment payment = new Payment();
            payment.setAmount(usedAmount);
            payment.setPaymentMethod(pm);
            payment.setCompany(company);
            payment.setIsActive(true);
            payment.setPurchaseOrder(item.getPurchaseOrder());
            payment.setPurchaseItem(item);
            payment.setRedeemPrepaidTopUpTransaction(ptt);

            payment.setCreated(item.getCreated());
            payment.setCreatedBy(item.getCreatedBy());
            payment.setStatus(CommonConstant.PAYMENT_STATUS_PAID);
            payment.setDisplayOrder(1);

            paymentService.saveOrUpdate(payment);

            if (isBreak) {
                break;
            }
        }

        if (prepaid.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_PACKAGE)) {
            orderItemVO.setEffectiveAmount(usedAmount);
            item.setEffectiveValue(usedAmount);
        }
        //review prepaid
        prepaid.setRemainValue(prepaidRemainValue);
        if (prepaidRemainValue <= 0 &&
                (prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_VOUCHER)
                        || prepaid.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_VOUCHER))) {
            prepaid.setIsActive(false);
        }

        //re-cal item amount
        item.setAmount(itemAmount);
        logger.debug("reviewPrepaidWhenPaidByPrepaid end item amount {},prepaid.remainValue" + itemAmount + "," + prepaidRemainValue);
    }

    @Override
    public void checkingPrepaidWhenPaidByPrepaid(Long prepaidId, OrderItemVO orderItemVO, HttpSession httpSession) {
        Prepaid prepaid = get(prepaidId);
        Double itemAmount = orderItemVO.getAmount();

        Double price = orderItemVO.getPrice();
        Double totalQty = new Double(orderItemVO.getQty());
        Double totalRemainValue = prepaid.getRemainValue();

        Double prepaidFinalPaid = 0d;
        Double prepaidFinalPaidUnit = 0d;

        Double usedPrepaidForSession = 0d;
        Double usedPrepaidUnitForSession = 0d;


        Map<String, Double> usedPrepaidMap = (Map<String, Double>) httpSession.getAttribute(USED_PREPAID_MAP);
        if (usedPrepaidMap == null) {
            usedPrepaidMap = new HashMap<String, Double>();
        }
        if (usedPrepaidMap.get(prepaid.getReference()) != null) {
            usedPrepaidForSession = usedPrepaidMap.get(prepaid.getReference());
        }


        Map<String, Double> usedPrepaidUnitMap = (Map<String, Double>) httpSession.getAttribute(USED_PREPAID_UNIT_MAP);
        if (usedPrepaidUnitMap == null) {
            usedPrepaidUnitMap = new HashMap<String, Double>();
        }

        if (usedPrepaidUnitMap.get(prepaid.getReference()) != null) {
            usedPrepaidUnitForSession = usedPrepaidUnitMap.get(prepaid.getReference());
        }

        //review prepaid top up transactions
        List<PrepaidTopUpTransaction> ptts = prepaidTopUpTransactionService.getActivePrepaidTopUpTransactionsByPrepaid(prepaid);
        Boolean isSamePo = Boolean.FALSE;
        for (PrepaidTopUpTransaction ptt : ptts) {
            //cash package or cash voucher
            if (totalQty == 0d) {
                break;
            }
            if (ptt.getRemainValue() == 0) {
                continue;
            }

            if (prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_PACKAGE)
                    || prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_VOUCHER)) {
                Double remainVal = ptt.getRemainValue() - usedPrepaidForSession;
                if (remainVal < 0) {
                    prepaidFinalPaid = ptt.getRemainValue();
                    continue;
                }
                if (itemAmount <= remainVal) {
                    prepaidFinalPaid = itemAmount;
//					itemAmount=0d;
                    break;
                } else {
                    prepaidFinalPaid += remainVal;
//					itemAmount=itemAmount -prepaidFinalPaid;
                }
            } else {
                //treatment package or treatment voucher
                //单次的平均值
                double prepaidValueForOneUnit = ptt.getTopUpValue() / ptt.getTopUpInitValue();

                Double remainUnit = totalRemainValue - usedPrepaidUnitForSession;

                if (remainUnit <= 0) {
                    break;
                }

                if (totalQty > remainUnit) {
                    prepaidFinalPaidUnit += ptt.getRemainValue();
                    prepaidFinalPaid += prepaidValueForOneUnit * ptt.getRemainValue();

                    totalQty -= ptt.getRemainValue();
                    itemAmount = totalQty * price;
                } else {
                    prepaidFinalPaidUnit += totalQty;
                    prepaidFinalPaid += prepaidValueForOneUnit * totalQty;

                    totalQty = 0d;

                    if (!isSamePo) {// 如果是相同的po，则不需要补价
                        itemAmount = 0d;
                    } else {// 如果是不同的PO
                        if (price.doubleValue() > prepaidValueForOneUnit) {//如果单价大于prepaid 的平均价，则需要补价
                            itemAmount = price.doubleValue() - prepaidValueForOneUnit;
                        } else {//单价小于等于prepaid 的平均价，则不需要补价
                            itemAmount = 0d;
                        }
                    }
                    break;
                }
            }
        }
        //review prepaid
        if (prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_PACKAGE)
                || prepaid.getPrepaidType().equals(PREPAID_TYPE_CASH_VOUCHER)) {
            orderItemVO.setPrepaidPaidAmount(prepaidFinalPaid);
            orderItemVO.setFinalAmount(itemAmount - prepaidFinalPaid);

            usedPrepaidMap.put(prepaid.getReference(), usedPrepaidForSession + prepaidFinalPaid);
            httpSession.setAttribute(USED_PREPAID_MAP, usedPrepaidMap);

        } else {
            orderItemVO.setPrepaidPaidAmount(prepaidFinalPaid);
            orderItemVO.setFinalAmount(itemAmount);
            // if paid by treatment package then the effective value is the value of prepaidFinalPaid
            if (prepaid.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_PACKAGE)) {
                orderItemVO.setEffectiveAmount(prepaidFinalPaid);
            }
            usedPrepaidUnitMap.put(prepaid.getReference(), usedPrepaidUnitForSession + prepaidFinalPaidUnit);
            httpSession.setAttribute(USED_PREPAID_UNIT_MAP, usedPrepaidUnitMap);
        }

    }

    @Override
    public void savePrepaid(PrepaidAddVO prepaidAddVO) {
        returnPrepaid(prepaidAddVO);
    }

    @Override
    public void sendVoucherNotificationEmail(PrepaidTopUpTransaction ptt, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        map.put("prepaidId", ptt.getPrepaid().getId());
        String attachmentName = RandomUtil.generateRandomNumberWithDate("Gift Voucher-") + ".pdf";
        try {
            String attachmentPath = "";
            File downloadFile = PDFUtil.convert(PRINT_VOUCHER_URL, request, map);
          /*   ServletUtil.upload(downloadFile,downloadFileName);
             pdfUrl= WebThreadLocal.getUrlRoot()+"/download/"+downloadFileName;*/
            attachmentPath = downloadFile.getPath();
            User member = WebThreadLocal.getUser();
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("company", WebThreadLocal.getCompany());
            parameterMap.put("attachmentName", attachmentName);
            parameterMap.put("attachmentPath", attachmentPath);
            parameterMap.put("user", member);
            parameterMap.put("prepaid", ptt.getPrepaid());
            try {
                parameterMap.put("expiryDate", DateUtil.dateToString(ptt.getExpiryDate(), "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//	        System.out.println("ptt.getPrepaid().getPickUpType()--"+ptt.getPrepaid().getPickUpType());
            if (ptt.getPrepaid().getPickUpType().equals("friend")) {
                parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_SEND_FRIEND_ONLINE_GIFT_VOUCHER);
                parameterMap.put("emailAddress", new EmailAddress(ptt.getPrepaid().getAdditionalEmail(), ptt.getPrepaid().getAdditionalName()));
                CashVoucherThread.getInstance(parameterMap).start();

            } else if (ptt.getPrepaid().getPickUpType().equals("email")) {
                parameterMap.put("emailAddress", new EmailAddress(member.getEmail(), member.getFullName()));
                parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_ONLINE_GIFT_VOUCHER);
                CashVoucherThread.getInstance(parameterMap).start();

            } else {
                parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_PICK_UP_ONLINE_GIFT_VOUCHER);
                parameterMap.put("emailAddress", new EmailAddress(member.getEmail(), member.getFullName()));
                CashVoucherThread.getInstance(parameterMap).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVoucherNotificationEmail(Long prepaidId, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        map.put("prepaidId", prepaidId);
        String attachmentName = RandomUtil.generateRandomNumberWithDate("Gift Voucher-") + ".pdf";
        try {
            String attachmentPath = "";
            File downloadFile = PDFUtil.convert(PRINT_VOUCHER_URL, request, map);
            attachmentPath = downloadFile.getPath();
		/*	ServletUtil.upload(downloadFile,downloadFileName);
			pdfUrl= WebThreadLocal.getUrlRoot()+"/download/"+downloadFileName;*/
            User member = WebThreadLocal.getUser();
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("company", WebThreadLocal.getCompany());
            parameterMap.put("attachmentName", attachmentName);
            parameterMap.put("attachmentPath", attachmentPath);
            parameterMap.put("user", member);
            parameterMap.put("emailAddress", new EmailAddress(member.getEmail(), member.getFullName()));
            parameterMap.put("templateType", CommonConstant.EMAIL_TEMPLATE_TYPE_REDEEM);
            CashVoucherThread.getInstance(parameterMap).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVoucherConfirmEmail(PrepaidTopUpTransaction ptt, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        map.put("prepaidId", ptt.getPrepaid().getId());
        String attachmentName = RandomUtil.generateRandomNumberWithDate("Gift Voucher-") + ".pdf";
        try {
            String attachmentPath = "";
            File downloadFile = PDFUtil.convert(PRINT_VOUCHER_URL, request, map);
          /*   ServletUtil.upload(downloadFile,downloadFileName);
             pdfUrl= WebThreadLocal.getUrlRoot()+"/download/"+downloadFileName;*/
            attachmentPath = downloadFile.getPath();
            User member = WebThreadLocal.getUser();
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("company", WebThreadLocal.getCompany());
            parameterMap.put("attachmentName", attachmentName);
            parameterMap.put("attachmentPath", attachmentPath);
            parameterMap.put("user", member);
            parameterMap.put("prepaid", ptt.getPrepaid());
            try {
                parameterMap.put("expiryDate", DateUtil.dateToString(ptt.getExpiryDate(), "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (ptt.getPrepaid().getPickUpType().equals("friend")) {
                parameterMap.put("templateType", "CONFIRM_ONLINE_GIFT_VOUCHER");
                parameterMap.put("emailAddress", new EmailAddress(member.getEmail(), member.getFullName()));
                parameterMap.put("attachmentPath", "");
                CashVoucherThread.getInstance(parameterMap).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Set<Long> getUserIdsForPackageExpiryJourney(String prepaidType, Integer days, Integer remainUnits) {

        Calendar fromCalendar = new GregorianCalendar();
        Calendar toCalendar = new GregorianCalendar();
        Date from = new Date();

        String fromDate = "";
        String toDate = "";

        if (days != null && days > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (days == 30) {
                fromCalendar.setTime(from);
                fromCalendar.add(Calendar.DATE, 7);
                from = fromCalendar.getTime();
                days = days - 7;
            } else if (days == 60) {
                fromCalendar.setTime(from);
                fromCalendar.add(Calendar.DATE, 30);
                from = fromCalendar.getTime();
                days = days - 30;
            }

            fromCalendar.setTime(from);
            fromDate = sdf.format(fromCalendar.getTime());

            toCalendar.setTime(fromCalendar.getTime());
            toCalendar.add(Calendar.DATE, days.intValue());
            toDate = sdf.format(toCalendar.getTime());
        } else if (remainUnits != null) {
            try {
                fromDate = DateUtil.dateToString(from, "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Set<Long> userIds = prepaidDao.getUserIdsForPackageExpiryJourney(prepaidType, fromDate, toDate, remainUnits);
        return userIds;
    }

    @Override
    public Set<Long> getUserIdsForPackageEngagementJourney(Date from, String subCode, String type) {
        Calendar fromTransCalendar = new GregorianCalendar();
        Calendar toTransCalendar = new GregorianCalendar();
        Calendar fromNoTransCalendar = new GregorianCalendar();
        Calendar toNoTransCalendar = new GregorianCalendar();
        String fromDateTrans = "";
        String toDateTrans = "";
        String fromDateNoTrans = "";
        String toDateNoTrans = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int fromTransDays = 0;
        int toTransDays = 0;
        int fromNoTransDays = 0;
        int toNoTransDays = 0;
        if (subCode == "1") {
            fromTransDays = 180;
            toTransDays = 90;
            fromNoTransDays = 90;
            toNoTransDays = 0;
        } else if (subCode == "2") {
            fromTransDays = 210;
            toTransDays = 120;
            fromNoTransDays = 120;
            toNoTransDays = 0;
        } else if (subCode == "3") {
            fromTransDays = 240;
            toTransDays = 150;
            fromNoTransDays = 150;
            toNoTransDays = 0;
        }
        fromTransCalendar.setTime(from);
        fromTransCalendar.add(Calendar.DATE, -fromTransDays);
        toTransCalendar.setTime(from);
        toTransCalendar.add(Calendar.DATE, -toTransDays);

        fromNoTransCalendar.setTime(from);
        fromNoTransCalendar.add(Calendar.DATE, -fromNoTransDays);
        toNoTransCalendar.setTime(from);
        toNoTransCalendar.add(Calendar.DATE, -toNoTransDays);

        fromDateTrans = sdf.format(fromTransCalendar.getTime());
        toDateTrans = sdf.format(toTransCalendar.getTime());
        fromDateNoTrans = sdf.format(fromNoTransCalendar.getTime());
        toDateNoTrans = sdf.format(toNoTransCalendar.getTime());
        Set<Long> userIds = prepaidDao.getUserIdsForPackageEngagementJourney(fromDateTrans, toDateTrans, fromDateNoTrans, toDateNoTrans, type);
        return userIds;
    }

    @Override
    public Set<Long> getMemberIdsByExpiryPrepaid(String fromDate, String toDate, String prepaidType, Boolean hasRemaining) {
        return prepaidDao.getMemberIdsByExpiryPrepaid(fromDate, toDate, prepaidType, hasRemaining);
    }

    @Override
    public List<Prepaid> getPrepaidById(Long memberId, Long companyId) {
        return prepaidDao.getPrepaidByMemberId(memberId, companyId);
    }


    /**
     * created by rick --2018.9.6
     * <p>
     * 保存导入的prepaid模板
     * param ImportDemoVO
     *
     * @return
     */
    public List<PrepaidImportJxlsBean>  saveImportPrepaid(ImportDemoVO importDemoVO) {
        //读取模板里面的内容，封装到prepaidImportJxlsBeanList类中
        List<PrepaidImportJxlsBean> prepaidImportJxlsBeanList = new ArrayList<>();
        Map<String, Object> beans = new HashMap<>();
        beans.put("prepaidImportJxlsBeanList", prepaidImportJxlsBeanList);
        try {
            InputStream is = importDemoVO.getImportFile().getInputStream();
            ExcelUtil.read(is, "prepaidImportConfig.xml", beans);
        } catch (IOException e) {
            logger.error("error message:" + e.getMessage());
        }
        List<PrepaidImportJxlsBean> list = null;
        if (prepaidImportJxlsBeanList.size() > 0) {
            list = verificationImportData(prepaidImportJxlsBeanList);
        }
        return list;
    }

    //验证模板数据
    private List<PrepaidImportJxlsBean> verificationImportData(List<PrepaidImportJxlsBean> list) {
        //创建导入失败的集合
        List<PrepaidImportJxlsBean> errorRecords = CollectionUtils.getLightWeightList();

        for (PrepaidImportJxlsBean bean : list) {
            //验证shopname
            List<Shop> listShop;
            if(StringUtils.isNotBlank(bean.getShopName())){
                DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Shop.class);
                detachedCriteria.add(Restrictions.eq("name",bean.getShopName().trim()));
                detachedCriteria.add(Restrictions.eq("isActive",true));
                listShop = shopService.list(detachedCriteria);
                if(listShop == null || listShop.size() == 0){
                    bean.setReturnError("Shop is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Shop Name can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证clientname
            User user;
            if(StringUtils.isNotBlank(bean.getClientUsername())){
                user = userService.get("username", bean.getClientUsername().trim());
                if(user == null){
                    bean.setReturnError("Client Username is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            }else {
                bean.setReturnError("Client Username can not empty");
                errorRecords.add(bean);
                continue;
            }
            //验证prepaid type
            if(StringUtils.isNotBlank(bean.getPrepaidType())){
                if(!("CASH_PACKAGE".equals(bean.getPrepaidType().trim()) || "TREATMENT_PACKAGE".equals(bean.getPrepaidType().trim())
                    || "CASH_VOUCHER".equals(bean.getPrepaidType().trim()) || "TREATMENT_VOUCHER".equals(bean.getPrepaidType().trim()))){
                    bean.setReturnError("Pre-paid Type should be 'CASH_PACKAGE' or 'TREATMENT_PACKAGE' or 'CASH_VOUCHER' or 'TREATMENT_VOUCHER'");
                    errorRecords.add(bean);
                    continue;
                }
            }else {
                bean.setReturnError("Pre-paid Type can not empty");
                errorRecords.add(bean);
                continue;
            }
            //验证Pre-paid name
            if(StringUtils.isNotBlank(bean.getPrepaidName())){
                if(bean.getPrepaidName().trim().length() > 40){
                    bean.setReturnError("Pre-paid Name length can not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Pre-paid Name can not empty");
                errorRecords.add(bean);
                continue;
            }
            //验证pre-paid number
            String prepaidNumber;
            if(StringUtils.isNotBlank(bean.getPrepaidNumber())){
                Prepaid prepaid = get("reference", bean.getPrepaidNumber().trim());
                if(prepaid != null){
                    bean.setReturnError("Pre-paid Number is exist");
                    errorRecords.add(bean);
                    continue;
                }
                prepaidNumber = bean.getPrepaidNumber().trim();
            }else{
                prepaidNumber = RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_REF_PREFIX);
            }
            //验证prepaidValue
            if(StringUtils.isNotBlank(bean.getPrepaidValue().trim())){
                try{
                    Double.valueOf(bean.getPrepaidValue().trim());
                }catch (Exception e){
                    bean.setReturnError("Pre-paid value must be digits");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Pre-paid value can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证initialValue
            if(StringUtils.isNotBlank(bean.getInitialValue().trim())){
                try{
                    Double.valueOf(bean.getInitialValue().trim());
                }catch (Exception e){
                    bean.setReturnError("Initial value must be digits");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Initial value must be digits");
                errorRecords.add(bean);
                continue;
            }
            //验证remainValue
            if(StringUtils.isNotBlank(bean.getRemainValue())){
                try{
                    Double.valueOf(bean.getRemainValue().trim());
                }catch (Exception e){
                    bean.setReturnError("Remain value must be digits");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Remain value can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证purchaseDate
            Date purchaseDate;
            if(bean.getPurchaseDate() != null){
                try {
                    purchaseDate = DateUtil.stringToDateThrowException(bean.getPurchaseDate().trim(),"yyyy-MM-dd");
                } catch (ParseException e) {
                    bean.setReturnError("Purchase Date must be 'yyyy-MM-dd'");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Purchase Date can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证expiredDate
            Date expiredDate;
            if(bean.getExpiryDate() != null){
                try {
                    expiredDate = DateUtil.stringToDateThrowException(bean.getExpiryDate().trim(),"yyyy-MM-dd");
                } catch (ParseException e) {
                    bean.setReturnError("Expired Date must be 'yyyy-MM-dd'");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Expired Date can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证topUp
            if(StringUtils.isNotBlank(bean.getTopUp())){
                Boolean isTopUp = Boolean.parseBoolean(bean.getTopUp().trim());
                if(isTopUp ==null){
                    bean.setReturnError("Top Up should be 'TRUE' or 'FALSE'");
                    errorRecords.add(bean);
                    continue;
                }
            }else {
                bean.setReturnError("Top Up can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证commissionRate
            Double commissionRate;
            if(StringUtils.isNotBlank(bean.getCommissionRate())){
                try{
                    commissionRate = Double.valueOf(bean.getCommissionRate().trim());
                }catch (Exception e){
                    bean.setReturnError("Commission Rate must be digits");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                PrepaidAddVO prepaidAddVO = new PrepaidAddVO();
                prepaidAddVO.setPrepaidType(bean.getPrepaidType().trim());
                prepaidAddVO.setMemberId(user.getId());
                prepaidAddVO.setShopId(listShop.get(0).getId());
                prepaidAddVO.setIsTopUp(Boolean.valueOf(bean.getTopUp().trim()));
                commissionRate = getCalCommissionRateForPrepaid(prepaidAddVO);
            }
            //验证productOption
            Long productOptionByCode = null;
            if(StringUtils.isNotBlank(bean.getProductOption())){
                Long poaId = productOptionService.findProductOptionByCode(bean.getProductOption().trim());
                if(poaId == null){
                    bean.setReturnError("Product Option is not exist");
                    errorRecords.add(bean);
                    continue;
                }
                ProductOptionAttribute poa =productOptionAttributeService.get(poaId);
                if(poa !=null){
                    productOptionByCode = poa.getProductOption().getId();
                }else{
                    bean.setReturnError("Product Option is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            }

            List<KeyAndValueVO> key_pay = new ArrayList<>();
            //验证payment1
            if(StringUtils.isNotBlank(bean.getPayment1())){
                String[] split = bean.getPayment1().trim().split(":");
                if(split.length != 2){
                    bean.setReturnError("Payment1 format should be ' paymentName(eq cash):number(eq 999)'");
                    errorRecords.add(bean);
                    continue;
                }
                PaymentMethod paymentMethod = paymentMethodService.get("name", split[0].trim());
                if(paymentMethod == null){
                    bean.setReturnError("Payment1 Name is not exist");
                    errorRecords.add(bean);
                    continue;
                }

                try{
                    Double.valueOf(split[1]);
                }catch(Exception e){
                    bean.setReturnError("Payment1 format should be ' paymentName(eq cash):number(eq 999)'");
                    errorRecords.add(bean);
                    continue;
                }
                KeyAndValueVO keyAndValueVO = new KeyAndValueVO();
                keyAndValueVO.setId(paymentMethod.getId());
                keyAndValueVO.setKey("1");
                keyAndValueVO.setValue(split[1].trim());
                key_pay.add(keyAndValueVO);
            }

            //验证payment2
            if(StringUtils.isNotBlank(bean.getPayment2())){
                String[] split = bean.getPayment2().trim().split(":");
                if(split.length != 2){
                    bean.setReturnError("Payment2 format should be ' paymentName(eq cash):number(eq 999)'");
                    errorRecords.add(bean);
                    continue;
                }
                PaymentMethod paymentMethod = paymentMethodService.get("name", split[0].trim());
                if(paymentMethod == null){
                    bean.setReturnError("Payment2 Name is not exist");
                    errorRecords.add(bean);
                    continue;
                }

                try{
                    Double.valueOf(split[1]);
                }catch(Exception e){
                    bean.setReturnError("Payment2 format should be ' paymentName(eq cash):number(eq 999)'");
                    errorRecords.add(bean);
                    continue;
                }
                KeyAndValueVO keyAndValueVO = new KeyAndValueVO();
                keyAndValueVO.setId(paymentMethod.getId());
                keyAndValueVO.setKey("2");
                keyAndValueVO.setValue(split[1].trim());
                key_pay.add(keyAndValueVO);
            }

            //验证payment3
            if(StringUtils.isNotBlank(bean.getPayment3())){
                String[] split = bean.getPayment3().trim().split(":");
                if(split.length != 2){
                    bean.setReturnError("Payment3 format should be ' paymentName(eq cash):number(eq 999)'");
                    errorRecords.add(bean);
                    continue;
                }
                PaymentMethod paymentMethod = paymentMethodService.get("name", split[0].trim());
                if(paymentMethod == null){
                    bean.setReturnError("Payment3 Name is not exist");
                    errorRecords.add(bean);
                    continue;
                }

                try{
                    Double.valueOf(split[1]);
                }catch(Exception e){
                    bean.setReturnError("Payment3 format should be ' paymentName(eq cash):number(eq 999)'");
                    errorRecords.add(bean);
                    continue;
                }
                KeyAndValueVO keyAndValueVO = new KeyAndValueVO();
                keyAndValueVO.setId(paymentMethod.getId());
                keyAndValueVO.setKey("3");
                keyAndValueVO.setValue(split[1].trim());
                key_pay.add(keyAndValueVO);
            }
            List<KeyAndValueVO> key_therapist = new ArrayList<>();
            //验证技师1
            if(StringUtils.isNotBlank(bean.getTherapists1())){

                User staff = userService.getLoginUserByAccountWithType(bean.getTherapists1().trim(),CommonConstant.USER_ACCOUNT_TYPE_STAFF);
                if(staff !=null){
                    KeyAndValueVO keyAndValueVO = new KeyAndValueVO();
                    keyAndValueVO.setId(staff.getId());
                    keyAndValueVO.setKey("1");
                    keyAndValueVO.setValue(staff.getUsername());
                    key_therapist.add(keyAndValueVO);
                }else{
                    bean.setReturnError("Therapist1 is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证技师2
            if(StringUtils.isNotBlank(bean.getTherapists2())){
                User staff = userService.getLoginUserByAccountWithType(bean.getTherapists2().trim(),CommonConstant.USER_ACCOUNT_TYPE_STAFF);
                if(staff !=null){
                    KeyAndValueVO keyAndValueVO = new KeyAndValueVO();
                    keyAndValueVO.setId(staff.getId());
                    keyAndValueVO.setKey("2");
                    keyAndValueVO.setValue(staff.getUsername());
                    key_therapist.add(keyAndValueVO);
                }else{
                    bean.setReturnError("Therapist2 is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证技师3
            if(StringUtils.isNotBlank(bean.getTherapists3())){
                User staff = userService.getLoginUserByAccountWithType(bean.getTherapists3().trim(),CommonConstant.USER_ACCOUNT_TYPE_STAFF);
                if(staff != null){
                    KeyAndValueVO keyAndValueVO = new KeyAndValueVO();
                    keyAndValueVO.setId(staff.getId());
                    keyAndValueVO.setKey("3");
                    keyAndValueVO.setValue(staff.getUsername());
                    key_therapist.add(keyAndValueVO);
                }else{
                    bean.setReturnError("Therapist3 is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证remark
            if(StringUtils.isNotBlank(bean.getRemarks())){
                if(bean.getRemarks().trim().length() > 200){
                    bean.setReturnError("Remarks length can not more than 200");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证free
            if(StringUtils.isNotBlank(bean.getFree())){
                Boolean isFree = Boolean.parseBoolean(bean.getFree().trim());
                if(isFree ==null){
                    bean.setReturnError("Free must be 'TRUE' or 'FALSE'");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证status
            if(StringUtils.isNotBlank(bean.getStatus())){
                if(!("ACTIVE".equalsIgnoreCase(bean.getStatus().trim()) || "INACTIVE".equalsIgnoreCase(bean.getStatus().trim()))){
                    bean.setReturnError("status must be 'ACTIVE' or 'INACTIVE'");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("status can not be empty");
                errorRecords.add(bean);
                continue;
            }
            PrepaidAddVO prepaidAddVO = new PrepaidAddVO();
            prepaidAddVO.setShopId(listShop.get(0).getId());
            prepaidAddVO.setMemberId(user.getId());
            prepaidAddVO.setPrepaidType(bean.getPrepaidType().trim());
            prepaidAddVO.setPrepaidName(StringUtils.isNotBlank(bean.getPrepaidName()) ? bean.getPrepaidName().trim() : "");
            prepaidAddVO.setReferenceBackUp(prepaidNumber);
            prepaidAddVO.setPrepaidValue(Double.valueOf(bean.getPrepaidValue().trim()));
            prepaidAddVO.setInitValue(Double.valueOf(bean.getInitialValue().trim()));
            prepaidAddVO.setRemainValue(Double.valueOf(bean.getRemainValue().trim()));
            prepaidAddVO.setPurchaseDate(purchaseDate);
            prepaidAddVO.setExpiryDate(expiredDate);
            prepaidAddVO.setCommissionRate(commissionRate);
            prepaidAddVO.setProductOptionId(productOptionByCode);
            prepaidAddVO.setTherapists(key_therapist);
            prepaidAddVO.setPaymentMethods(key_pay);
            prepaidAddVO.setRemarks(bean.getRemarks());
            prepaidAddVO.setIsTopUp(Boolean.valueOf(bean.getTopUp().trim()));
            prepaidAddVO.setIsFree(StringUtils.isNotBlank(bean.getFree()) ? bean.getFree().trim() : "");
            if("ACTIVE".equals(bean.getStatus().trim())){
                prepaidAddVO.setIsActive("true");
            }else{
                prepaidAddVO.setIsActive("false");
            }
            savePrepaid(prepaidAddVO);
        }
        return errorRecords;
    }

   /* //模板转换成vo-----1
    private void wrapToPrepaidAddVO(Prepaid prepaid, PrepaidImportJxlsBean prepaidImportJxlsBean, User user, Shop shop
            , Double faceValue, Double initValue, Double remainValue, Date expiryTime, Date purchaseTime, Boolean isRedeem
            , Boolean isOnline, Boolean isTransfer, Boolean isFree, Boolean isAllCompanyUse
            , Double extraDiscount, ProductOption productOption, Category category, List<KeyAndValueVO> payment
            , List<KeyAndValueVO> therapists) {
        PrepaidAddVO prepaidAddVO = new PrepaidAddVO();
        prepaidAddVO.setPrepaidName(prepaidImportJxlsBean.getName());
        prepaidAddVO.setPrepaidType(prepaidImportJxlsBean.getPrepaidType());
        prepaidAddVO.setMember(user);
        prepaidAddVO.setExtraDiscount(extraDiscount);
        prepaidAddVO.setPrepaidValue(faceValue);
        prepaidAddVO.setShopId(shop.getId());
        prepaidAddVO.setExpiryDate(expiryTime);
        prepaidAddVO.setInitValue(initValue);
        prepaidAddVO.setPurchaseDate(purchaseTime);
        prepaidAddVO.setRemarks(prepaidImportJxlsBean.getRemarks());
        prepaidAddVO.setMemberId(user.getId());
        prepaidAddVO.setReference(prepaidImportJxlsBean.getRef());
        prepaidAddVO.setRemainValue(remainValue);
        prepaidAddVO.setIsRedeem(isRedeem);
        prepaidAddVO.setIsOnline(isOnline);
        prepaidAddVO.setTherapists(therapists);
        prepaidAddVO.setPaymentMethods(payment);

        if (productOption != null) {
            prepaidAddVO.setProductOptionId(productOption.getId());
        }
        if (category != null) {
            prepaidAddVO.setCategoryId(category.getId());
        }
        if (isTransfer != null) {
            prepaidAddVO.setIsTransfer(isTransfer + "");
        }
        if (isFree != null) {
            prepaidAddVO.setIsFree(isFree + "");
        }
        prepaidAddVO.setIsAllCompanyUse(isAllCompanyUse);

        prepaidAddVO.setCommissionRate(getCalCommissionRateForPrepaid(prepaidAddVO));

        if (prepaid == null) {
            prepaidAddVO.setIsAppend(false);
            savePrepaidByPrepaidAddVo(prepaidAddVO, prepaid);
        } else {
            prepaidAddVO.setIsAppend(true);
            prepaid.setPrepaidValue(prepaid.getPrepaidValue() + prepaidAddVO.getPrepaidValue());
            prepaid.setInitValue(prepaid.getInitValue() + prepaidAddVO.getInitValue());
            prepaid.setRemainValue(prepaid.getRemainValue() + prepaidAddVO.getRemainValue());
            update(prepaid);
            savePrepaidToTopUpTransction(prepaidAddVO, prepaid);
        }
    }*/

    //保存prepaid-----2
    private void savePrepaidByPrepaidAddVo(PrepaidAddVO prepaidAddVO,Prepaid prepaid) {
        prepaid = new Prepaid();
        prepaid.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_REF_PREFIX));
        prepaid.setIsActive(true);
        prepaid.setName(prepaidAddVO.getPrepaidName());
        prepaid.setRemainValue(prepaidAddVO.getRemainValue());
        prepaid.setInitValue(prepaidAddVO.getInitValue());
        prepaid.setPrepaidValue(prepaidAddVO.getPrepaidValue());
        prepaid.setCreated(new Date());
        prepaid.setCreatedBy(WebThreadLocal.getUser().getUsername());
        prepaid.setUser(prepaidAddVO.getMember());
        prepaid.setRemarks(prepaidAddVO.getRemarks());
        prepaid.setShop(shopService.get(prepaidAddVO.getShopId()));
        prepaid.setPrepaidType(prepaidAddVO.getPrepaidType());
        prepaid.setCompany(WebThreadLocal.getCompany());
        if (prepaidAddVO.getIsAllCompanyUse() == null) {
            prepaid.setIsAllCompanyUse(true);
        } else {
            prepaid.setIsAllCompanyUse(prepaidAddVO.getIsAllCompanyUse());
        }
        if (StringUtils.isNotBlank(prepaidAddVO.getIsFree())) {
            prepaid.setIsFree(Boolean.valueOf(prepaidAddVO.getIsFree()));
        }
        if (StringUtils.isNotBlank(prepaidAddVO.getIsTransfer())) {
            prepaid.setIsTransfer(Boolean.valueOf(prepaidAddVO.getIsTransfer()));
        }
        if(prepaidAddVO.getIsRedeem() != null)
        {
            prepaid.setIsRedeem(prepaidAddVO.getIsRedeem());
        }else
        {
            prepaid.setIsRedeem(false);
        }
        if(prepaidAddVO.getIsOnline() != null)
        {
            prepaid.setIsOnline(prepaidAddVO.getIsOnline());
        }
        else
        {
            prepaid.setIsOnline(false);
        }
        save(prepaid);
        savePrepaidToTopUpTransction(prepaidAddVO, prepaid);
    }

    //保存transction-----3
    private void savePrepaidToTopUpTransction(PrepaidAddVO prepaidAddVO, Prepaid prepaid) {
        PrepaidTopUpTransaction topUp = new PrepaidTopUpTransaction();
        topUp.setRemainValue(prepaidAddVO.getRemainValue());
        topUp.setIsActive(true);
        topUp.setIsRoot(false);
        topUp.setExtraDiscount(prepaidAddVO.getExtraDiscount());
        topUp.setExpiryDate(prepaidAddVO.getExpiryDate());
        topUp.setPrepaid(prepaid);
        topUp.setRemarks(prepaidAddVO.getRemarks());
        topUp.setCompany(WebThreadLocal.getCompany());
        topUp.setCreated(new Date());
        topUp.setCreatedBy(WebThreadLocal.getUser().getUsername());
        topUp.setPrepaidType(prepaidAddVO.getPrepaidType());
        topUp.setTopUpReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_TOP_UP_REF_PREFIX));
        topUp.setShop(shopService.get(prepaidAddVO.getShopId()));
        topUp.setTopUpDate(prepaidAddVO.getPurchaseDate());
        topUp.setTopUpInitValue(prepaidAddVO.getInitValue());
        topUp.setTopUpValue(prepaidAddVO.getPrepaidValue());
        topUp.setStatus(CommonConstant.PREPAID_TOPUP_TRANSACTION_STATUS_NORMAL);
        topUp.setCommissionRate(prepaidAddVO.getCommissionRate());
        if (prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_PACKAGE) ||
                prepaidAddVO.getPrepaidType().equals(PREPAID_TYPE_TREATMENT_VOUCHER)) {
            topUp.setProductOption(productOptionService.get(prepaidAddVO.getProductOptionId()));
            topUp.setCategory(categoryService.get(prepaidAddVO.getCategoryId()));
        }
        prepaidTopUpTransactionService.save(topUp);
        savePurchaseOrderNew(topUp, prepaidAddVO);
    }

    //保存订单------4
    private void savePurchaseOrderNew(PrepaidTopUpTransaction topUp, PrepaidAddVO prepaidAddVO) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PURCHASE_ORDER_REF_PREFIX));
        purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        purchaseOrder.setCompany(WebThreadLocal.getCompany());
        purchaseOrder.setCreated(new Date());
        purchaseOrder.setCreatedBy(WebThreadLocal.getUser().getUsername());
        purchaseOrder.setIsActive(true);
        purchaseOrder.setPurchaseDate(topUp.getTopUpDate());
        purchaseOrder.setShop(topUp.getShop());
        purchaseOrder.setUser(topUp.getPrepaid().getUser());
        purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        if (prepaidAddVO.getExtraDiscount() != null) {
            purchaseOrder.setTotalDiscount(prepaidAddVO.getExtraDiscount());
            purchaseOrder.setTotalAmount(topUp.getTopUpValue() - purchaseOrder.getTotalDiscount());
        } else {
            purchaseOrder.setTotalDiscount(0d);
            purchaseOrder.setTotalAmount(topUp.getTopUpValue() - purchaseOrder.getTotalDiscount());
        }
        prepaidAddVO.setEffectiveValueForCommission(topUp.getTopUpValue());
        purchaseOrderService.save(purchaseOrder);
        savePurchaseItem(topUp, prepaidAddVO, purchaseOrder);
    }

    //保存预付item -----5
    private void savePurchaseItem(PrepaidTopUpTransaction topUp, PrepaidAddVO prepaidAddVO, PurchaseOrder purchaseOrder) {
        PurchaseItem item = new PurchaseItem();
        item.setQty(1);
        item.setBuyPrepaidTopUpTransaction(topUp);
        item.setStatus(purchaseOrder.getStatus());
        item.setIsActive(true);
        item.setCreated(new Date());
        item.setCreatedBy(WebThreadLocal.getUser().getUsername());
        item.setPurchaseOrder(purchaseOrder);
        item.setPrice(topUp.getTopUpValue());
        item.setDiscountValue(purchaseOrder.getTotalDiscount());
        item.setAmount(purchaseOrder.getTotalAmount());
        item.setEffectiveValue(purchaseOrder.getTotalAmount());
        item.setExtraDiscountValue(purchaseOrder.getTotalDiscount());
        purchaseItemService.save(item);
        //以下代码摘原来代码
        //保存支付方式
        setPayments(item, prepaidAddVO, false);

        String pointsAction = "";
        String earnChannel = "";
        if (!topUp.getPrepaid().getIsRedeem()) {// it is not a redeem record
            earnChannel = CommonConstant.POINTS_EARN_CHANNEL_PREPAID;
            pointsAction = CommonConstant.POINTS_ACTION_PLUS;
            if (!topUp.getPrepaid().getIsOnline()) {// this is not a online record
                //set commission
                setStaffCommission(item, prepaidAddVO, null);

                //earn spa points and review loyalty level
//				purchaseOrderService.reviewSpaPointsAndLoyaltyLevel(order,ptt.getTopUpValue(),CommonConstant.POINTS_EARN_CHANNEL_PREPAID,
//						CommonConstant.ACTION_ADD,ptt.getPrepaidType(), ptt.getPrepaid().getUser().isMember());
                userLoyaltyLevelService.reviewLoyaltyLevelV2(CommonConstant.ACTION_ADD, purchaseOrder.getUser().getId(), topUp.getPrepaidType(), 0d);

            }// this is a online record

        } else {//this is a redeem record
            pointsAction = CommonConstant.POINTS_ACTION_MINUS;
            earnChannel = CommonConstant.POINTS_EARN_CHANNEL_REDEEM;
        }
        //set loyalty points
        if (!topUp.getPrepaid().getIsOnline() && !topUp.getPrepaid().getIsRedeem()) {
            // it is not a redeem and not a online record
            setLoyaltyPoints(earnChannel, purchaseOrder, topUp.getTopUpValue(), pointsAction);
        }

    }

    @Override
    public Boolean checkWhetherHasExpiringPackage(Long memberId) {

        User member = userService.get(memberId);
        if (member != null && member.isMember()) {
            String[] packages = new String[2];
            packages[0] = PREPAID_TYPE_CASH_PACKAGE;
            packages[1] = PREPAID_TYPE_TREATMENT_PACKAGE;

            DetachedCriteria pttDC = DetachedCriteria.forClass(PrepaidTopUpTransaction.class);
            pttDC.createAlias("prepaid", "p");
            pttDC.add(Restrictions.eq("p.user.id", memberId));
            pttDC.add(Restrictions.eq("isActive", true));
            pttDC.add(Restrictions.gt("remainValue", 0d));
            pttDC.add(Restrictions.in("prepaidType", packages));

            Date now = new Date();
            pttDC.add(Restrictions.le("expiryDate", DateUtil.getDateAfter(now, time_range_expring_package)));

            List<PrepaidTopUpTransaction> list = prepaidTopUpTransactionService.list(pttDC);
            if (list != null && list.size() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PrepaidImportGiftBean> importPrepaidGift(ImportDemoVO importDemoVO) {

        List<PrepaidImportGiftBean> list = new ArrayList<>();
        Map<String, Object> beans = new HashMap<>();
        beans.put("prepaidImportGiftBeanList", list);
        try {
            InputStream is = importDemoVO.getImportFile().getInputStream();
            ExcelUtil.read(is, "gcGiftImportConfig.xml", beans);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() == 0) {
            return null;
        }
        return startImportPrepaidGift(list);
    }

    private List<PrepaidImportGiftBean> startImportPrepaidGift(List<PrepaidImportGiftBean> list){
        //创建导入失败的集合
        List<PrepaidImportGiftBean> errorRecords = CollectionUtils.getLightWeightList();

        Double cashValue;
        Date issueDate;
        Date expireDate =null;
        for(PrepaidImportGiftBean bean : list){
            //验证voucherNumber
            if(StringUtils.isNotBlank(bean.getVoucherNumber())){
                if(bean.getVoucherNumber().trim().length() > 40){
                    bean.setReturnError("VoucherNumber length can not more than 40 !");
                    errorRecords.add(bean);
                    continue;
                }
                Prepaid prepaid = get("reference", bean.getVoucherNumber());
                if(prepaid != null){
                    bean.setReturnError("Voucher Number is exist !");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Voucher Number can not be empty !");
                errorRecords.add(bean);
                continue;
            }
            //验证cahsvalue
            try{
                cashValue = Double.valueOf(bean.getCashValue());
            }catch (Exception e){
                bean.setReturnError("Cash Value must be number");
                errorRecords.add(bean);
                continue;
            }
            //验证issueDate
            if(bean.getIssueDate() !=null){
                try{
                    String issueDateStr =  DateUtil.convertDateToString("yyyy-MM-dd",bean.getIssueDate());
                    issueDate = DateUtil.stringToDateThrowException(issueDateStr,"yyyy-MM-dd");
                }catch (Exception e){
                    bean.setReturnError("Issue Date format must be 'YYYY-MM-DD'");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Issue Date can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证expireDate
            if(bean.getExpiredDate() !=null) {
                try {
                    String expireDateStr = DateUtil.convertDateToString("yyyy-MM-dd", bean.getExpiredDate());
                    expireDate = DateUtil.stringToDateThrowException(expireDateStr, "yyyy-MM-dd");
                } catch (Exception e) {
                    bean.setReturnError("Expire Date format must be 'YYYY-MM-DD'");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证status
            if(StringUtils.isNotBlank(bean.getStatus())){
                if(!("ACTIVE".equals(bean.getStatus()) || "INACTIVE".equals(bean.getStatus()))){
                    bean.setReturnError("Status must be 'ACTIVE' or 'INACTIVE'");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Status can not be empty");
                errorRecords.add(bean);
                continue;
            }

            Prepaid prepaid = new Prepaid();
            prepaid.setCompany(WebThreadLocal.getCompany());
            prepaid.setCreatedBy(WebThreadLocal.getUser().getUsername());
            prepaid.setPrepaidType(PREPAID_TYPE_CASH_VOUCHER);
            prepaid.setName(bean.getVoucherName());
            prepaid.setReference(bean.getVoucherNumber());
            User client = userService.get("username", "GUEST");
            prepaid.setUser(client);
            if("ACTIVE".equals(bean.getStatus())){
                prepaid.setIsActive(true);
            }else{
                prepaid.setIsActive(false);
            }
            prepaid.setIsRedeem(false);
            prepaid.setIsOnline(false);
            prepaid.setIsAllCompanyUse(true);
            prepaid.setCreated(new Date());
            prepaid.setRemainValue(cashValue);
            prepaid.setInitValue(cashValue);
            prepaid.setPrepaidValue(cashValue);
            Shop shop = shopService.get("name", "Online");
            prepaid.setShop(shop);
            save(prepaid);
            savePrepaidToTopUpTransction(prepaid,issueDate,expireDate);
        }
        return errorRecords;
    }
    private void savePrepaidToTopUpTransction(Prepaid prepaid,Date issueDate,Date expireDate){
        PrepaidTopUpTransaction topUp = new PrepaidTopUpTransaction();
        topUp.setRemainValue(prepaid.getRemainValue());
        topUp.setIsActive(true);
        topUp.setIsRoot(false);
        topUp.setTopUpDate(issueDate);
        topUp.setExpiryDate(expireDate);
        topUp.setPrepaid(prepaid);
        topUp.setCompany(WebThreadLocal.getCompany());
        topUp.setCreated(new Date());
        topUp.setCreatedBy(WebThreadLocal.getUser().getUsername());
        topUp.setPrepaidType(prepaid.getPrepaidType());
        topUp.setTopUpReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_TOP_UP_REF_PREFIX));
        topUp.setShop(prepaid.getShop());
        topUp.setTopUpInitValue(prepaid.getInitValue());
        topUp.setTopUpValue(prepaid.getPrepaidValue());
        topUp.setCommissionRate(0d);
        topUp.setTopUpReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PREPAID_TOP_UP_REF_PREFIX));
        topUp.setStatus(CommonConstant.PREPAID_TOPUP_TRANSACTION_STATUS_NORMAL);
        prepaidTopUpTransactionService.save(topUp);
        savePurchaseOrderNew(topUp);
    }

    //保存订单------4
    private void savePurchaseOrderNew(PrepaidTopUpTransaction topUp) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PURCHASE_ORDER_REF_PREFIX));
        purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        purchaseOrder.setCompany(WebThreadLocal.getCompany());
        purchaseOrder.setCreated(new Date());
        purchaseOrder.setCreatedBy(WebThreadLocal.getUser().getUsername());
        purchaseOrder.setIsActive(true);
        purchaseOrder.setPurchaseDate(topUp.getTopUpDate());
        purchaseOrder.setShop(topUp.getShop());
        purchaseOrder.setUser(topUp.getPrepaid().getUser());
        purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        purchaseOrderService.save(purchaseOrder);
        savePurchaseItem(topUp, purchaseOrder);
    }

    //保存预付item -----5
    private void savePurchaseItem(PrepaidTopUpTransaction topUp, PurchaseOrder purchaseOrder) {
        PurchaseItem item = new PurchaseItem();
        item.setQty(1);
        item.setBuyPrepaidTopUpTransaction(topUp);
        item.setStatus(purchaseOrder.getStatus());
        item.setIsActive(true);
        item.setCreated(new Date());
        item.setCreatedBy(WebThreadLocal.getUser().getUsername());
        item.setPurchaseOrder(purchaseOrder);
        item.setPrice(topUp.getTopUpValue());
        item.setDiscountValue(purchaseOrder.getTotalDiscount());
        item.setAmount(purchaseOrder.getTotalAmount());
        item.setEffectiveValue(purchaseOrder.getTotalAmount());
        item.setExtraDiscountValue(purchaseOrder.getTotalDiscount());
        purchaseItemService.save(item);
    }

}
