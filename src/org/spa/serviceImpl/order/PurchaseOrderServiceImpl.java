package org.spa.serviceImpl.order;

import com.spa.constant.APIConstant;
import com.spa.constant.CommonConstant;
import com.spa.exception.ChainedException;
import com.spa.job.ReviewDelayScheduleJob;
import com.spa.plugin.commission.CommissionAdapter;
import com.spa.plugin.commission.CommissionCalculationPlugin;
import com.spa.plugin.discount.DiscountItemAdapter;
import com.spa.plugin.discount.DiscountItemPlugin;
import com.spa.thread.ImportDataToSFThread;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.spa.dao.order.*;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.model.bundle.ProductBundle;
import org.spa.model.company.Company;
import org.spa.model.inventory.InventoryTransaction;
import org.spa.model.order.*;
import org.spa.model.payment.Payment;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.points.SpaPointsTransaction;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOption;
import org.spa.model.salesforce.MarketingCampaign;
import org.spa.model.salesforce.UserMarketingCampaignTransaction;
import org.spa.model.user.User;
import org.spa.service.book.BookItemService;
import org.spa.service.book.BookService;
import org.spa.service.bundle.BundleService;
import org.spa.service.inventory.InventoryService;
import org.spa.service.inventory.InventoryTransactionService;
import org.spa.service.inventory.InventoryWarehouseService;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.service.order.*;
import org.spa.service.outSource.OutSourceAttributeKeyService;
import org.spa.service.payment.PaymentMethodService;
import org.spa.service.payment.PaymentService;
import org.spa.service.payroll.PayrollService;
import org.spa.service.points.LoyaltyPointsTransactionService;
import org.spa.service.points.SpaPointsTransactionService;
import org.spa.service.prepaid.PrepaidService;
import org.spa.service.prepaid.PrepaidTopUpTransactionService;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.salesforce.MarketingCampaignService;
import org.spa.service.salesforce.UserMarketingCampaignTransactionService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.*;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.inventory.InventoryTransactionVO;
import org.spa.vo.loyalty.PointsAdjustVO;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.OrderBundleVO;
import org.spa.vo.sales.OrderItemVO;
import org.spa.vo.sales.OrderVO;
import org.spa.vo.sales.SalesCommissionCalVO;
import org.spa.vo.sales.SpendingSummaryVO;
import org.spa.vo.shop.OutSourceAttributeVO;
import org.spa.vo.staff.StaffItemVO;
import org.spa.vo.user.ClientViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.order.StaffCommission;
import org.spa.utils.DateUtil;
import org.spa.utils.RandomUtil;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class PurchaseOrderServiceImpl extends BaseDaoHibernate<PurchaseOrder> implements PurchaseOrderService {

    @Autowired
    public ProductOptionService productOptionService;

    @Autowired
    public BookService bookService;

    @Autowired
    public UserService userService;

    @Autowired
    public PrepaidService prepaidService;

    @Autowired
    public ShopService shopService;

    @Autowired
    public PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    public StaffCommissionService staffCommissionService;

    @Autowired
    public InventoryTransactionService inventoryTransactionService;

    @Autowired
    public InventoryService inventoryService;

    @Autowired
    public InventoryWarehouseService inventoryWarehouseService;

    @Autowired
    public PurchaseItemService purchaseItemService;

    @Autowired
    public PrepaidTopUpTransactionService prepaidTopUpTransactionService;

    @Autowired
    public BookItemService bookItemService;

    @Autowired
    public SpaPointsTransactionService spaPointsTransactionService;

    @Autowired
    public UserLoyaltyLevelService userLoyaltyLevelService;

    @Autowired
    public LoyaltyPointsTransactionService loyaltyPointsTransactionService;

    @Autowired
    public OutSourceAttributeKeyService outSourceAttributeKeyService;

    @Autowired
    public PayrollService payrollService;

    @Autowired
    public StaffCommissionDao staffCommissionDao;

    @Autowired
    public PurchaseOrderDao purchaseOrderDao;

    @Autowired
    public PurchaseItemDao purchaseItemDao;

    @Autowired
    public CategoryService categoryService;

    @Autowired
    private UserMarketingCampaignTransactionService userMarketingCampaignTransactionService;

    @Autowired
    private MarketingCampaignService marketingCampaignService;

    @Autowired
    private OrderSurveyService orderSurveyService;

    @Autowired
    private BundleService bundleService;

    @Override
    public OrderVO checkBundleOrder(OrderVO orderVO, int idxId, HttpSession httpSession) {

        Long shopId = orderVO.getShopId();

        if (orderVO.getBookId() != null) {
            Book book = bookService.get(orderVO.getBookId());
            orderVO.setBookRef(book.getReference());
        }
        if (orderVO.getItemVOs() == null) {
            return null;
        }

        Long companyId = WebThreadLocal.getCompany().getId();
        Map<OrderBundleVO, List<OrderItemVO>> bundleMap = generateBundleMap(orderVO.getItemVOs(), orderVO.getShopId());
        Iterator<OrderBundleVO> it = bundleMap.keySet().iterator();
        while (it.hasNext()) {
            OrderBundleVO bundleVO = it.next();
            double bundleAmount = bundleVO.getBundleAmount();
            double totalEffectiveVal = bundleVO.getTotalBundleEffectiveVal();
            List<OrderItemVO> itemVOs = bundleMap.get(bundleVO);
            for (OrderItemVO orderItemVO : itemVOs) {
                ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());
                Integer qty = orderItemVO.getQty();
                double effectiveValue = po.getFinalPrice(shopId) * qty;
                orderItemVO.setProductOption(po);
                orderItemVO.setEffectiveAmount(effectiveValue);
                orderItemVO.setAmount(bundleVO.getBundleAmount() / bundleVO.getGroups());
                //check staff commission
                calBundleStaffCommission(orderItemVO, null, orderVO.getPurchaseDate(), false, companyId, bundleAmount, totalEffectiveVal);
            }
        }
        logger.debug("checkOrderItem------orderVO item size{}::" + orderVO.getItemVOs().size() + "---getidxId::" + orderVO.getItemVOs().get(0).getIdxId() + ",idxId{}::" + idxId);
        return orderVO;
    }

    private Map<OrderBundleVO, List<OrderItemVO>> generateBundleMap(List<OrderItemVO> itemVOs, Long shopId) {
        Map<OrderBundleVO, List<OrderItemVO>> bundleMap = new HashMap<OrderBundleVO, List<OrderItemVO>>();
        if (itemVOs == null || (itemVOs != null && itemVOs.size() == 0)) {
            return bundleMap;
        }

        Map<Long, List<OrderItemVO>> bundles = new HashMap<Long, List<OrderItemVO>>();
        Map<Long, OrderBundleVO> bundleVOs = new HashMap<Long, OrderBundleVO>();

        Iterator<OrderItemVO> it = itemVOs.iterator();

        while (it.hasNext()) {
            OrderItemVO orderItemVO = it.next();
            if (orderItemVO == null) {
                it.remove();
                continue;
            }
            if (orderItemVO.getIdxId() == null) {
                it.remove();
                continue;
            }
            if (orderItemVO.getProductOptionId() == null) {
                itemVOs.remove(orderItemVO);
                continue;
            }
            if (orderItemVO.getBundleId() == null) {
                break;
            }
            ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());
            Integer qty = orderItemVO.getQty();
            double effectiveValue = po.getFinalPrice(shopId) * qty;

            Long bundleId = orderItemVO.getBundleId();
            ProductBundle pb = bundleService.get(bundleId);

            OrderBundleVO bundleVO;
            List<OrderItemVO> bundleList = bundles.get(bundleId);
            if (bundleList != null && bundleList.size() > 0) {
                bundleList.add(orderItemVO);
                bundleVO = bundleVOs.get(bundleId);
                bundleVO.setTotalBundleEffectiveVal(bundleVO.getTotalBundleEffectiveVal() + effectiveValue);
            } else {
                bundleList = new ArrayList<OrderItemVO>();
                bundleList.add(orderItemVO);

                bundleVO = new OrderBundleVO();
                bundleVO.setBundleAmount(pb.getBundleAmount());
                bundleVO.setBundleId(bundleId);
                bundleVO.setGroups(pb.getGroups());
                bundleVO.setTotalBundleEffectiveVal(effectiveValue);
                bundles.put(bundleId, bundleList);
                bundleVOs.put(bundleId, bundleVO);
                bundleMap.put(bundleVO, bundleList);
            }
        }
        return bundleMap;
    }

    public OrderVO checkOrderItem(OrderVO orderVO, int idxId, HttpSession httpSession) {

        logger.debug("checkOrderItem------orderVO item size{}::" + orderVO.getItemVOs().size() + ",idxId{}::" + idxId);
        Long shopId = orderVO.getShopId();

        if (orderVO.getBookId() != null) {
            Book book = bookService.get(orderVO.getBookId());
            orderVO.setBookRef(book.getReference());
        }
        if (orderVO.getItemVOs() == null) {
            return null;
        }

        Long memberId = orderVO.getMemberId();
        Long companyId = WebThreadLocal.getCompany().getId();

        List<OrderItemVO> itemVOs = orderVO.getItemVOs();
        Iterator<OrderItemVO> it = itemVOs.iterator();
        while (it.hasNext()) {
            OrderItemVO orderItemVO = it.next();
            if (orderItemVO == null) {
                it.remove();
                continue;
            }
            if (orderItemVO.getIdxId() == null) {
                it.remove();
                continue;
            }
            if (orderItemVO.getIdxId().intValue() != idxId) {
                it.remove();
                continue;
            }

            if (orderItemVO.getProductOptionId() == null) {
                orderVO.getItemVOs().remove(orderItemVO);
                continue;
            }

            Prepaid prepaid = null;
            if (orderItemVO.getPaidByPackageId() != null) {
                prepaid = prepaidService.get(orderItemVO.getPaidByPackageId());
                if (prepaid == null) {
                    orderVO.setValidPrepaidMessage("NULL");
                    break;
                }
            } else if (StringUtils.isNotBlank(orderItemVO.getPaidByVoucherRef())) {
                prepaid = prepaidService.get("reference", orderItemVO.getPaidByVoucherRef());
                if (prepaid == null || (prepaid != null && !prepaid.isIsActive())) {
                    orderVO.setValidPrepaidMessage("NULL");
                    break;
                } else if (prepaid != null && prepaid.getFirstPrepaidTopUpTransaction().getExpiryDate().before(new Date())) {
                    orderVO.setValidPrepaidMessage("EXPIRY");
                    break;
                }
            }

            ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());
            orderItemVO.setProductOption(po);
            orderItemVO.setPrice(po.getFinalPrice(shopId));

            // cal discount
            Double[] returnDiscount = calItemDiscount(orderItemVO.getExtraDiscount(), shopId, memberId, orderItemVO.getQty(), po);
            double finalDiscount = returnDiscount[2];
            orderItemVO.setFinalDiscount(finalDiscount);
            logger.debug("check Item extra discount,public discount,final discount" + returnDiscount[0] + "," + returnDiscount[1] + "," + returnDiscount[2]);

            //cal amount
            Double[] itemEffectiveValAndAmount = calItemEffectiveValAndAmount(orderItemVO, po.getFinalPrice(shopId), finalDiscount);
            logger.debug("cal Item EffectiveVal And Amount----EffectiveVal---amount---net amount--" + itemEffectiveValAndAmount[0] + "," + itemEffectiveValAndAmount[1] + "," + itemEffectiveValAndAmount[2]);
            orderItemVO.setEffectiveAmount(itemEffectiveValAndAmount[0]);
            orderItemVO.setAmount(itemEffectiveValAndAmount[1]);
            orderItemVO.setNetAmount(itemEffectiveValAndAmount[2]);
            //check prepaid used

            reviewPrepaid(orderItemVO, null, false, httpSession);

            //check staff commission
            calStaffCommission(orderItemVO, null, orderVO.getPurchaseDate(), false, companyId);
        }
        logger.debug("checkOrderItem------orderVO item size{}::" + orderVO.getItemVOs().size() + "---getidxId::" + orderVO.getItemVOs().get(0).getIdxId() + ",idxId{}::" + idxId);
        return orderVO;
    }

    @Override
    public void getSuitablePackages(OrderItemVO orderItemVO, Long memberId, ProductOption po, Boolean usingCashPackage) {
        if (memberId != null && memberId.longValue() > 0 && !po.getIsGoods()) {
            //获取适合的预付付款
            //by company properties or system settings
            String prepaidSuitableOption = "";//EQUAL
            List<Prepaid> suitablePackages = new ArrayList<Prepaid>();
            suitablePackages.addAll(prepaidService.getSuitablePackagesByFilter(memberId, orderItemVO, prepaidSuitableOption, usingCashPackage));
            orderItemVO.setSuitablePackages(suitablePackages);
        }
    }

    @Override
    public Long savePurchaseOrder(OrderVO orderVO) {
        logger.debug("check out savePurchaseOrder---------");
        Company company = WebThreadLocal.getCompany();
        String loginUser = WebThreadLocal.getUser().getUsername();
        Date now = new Date();

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCompany(company);
        purchaseOrder.setIsActive(true);
        purchaseOrder.setCreated(now);
        purchaseOrder.setCreatedBy(loginUser);
        purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        purchaseOrder.setRemarks(orderVO.getRemarks());

        purchaseOrder.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PURCHASE_ORDER_REF_PREFIX));
        purchaseOrder.setPurchaseDate(orderVO.getPurchaseDate());
        purchaseOrder.setShowRemarksInInvoice(orderVO.getShowRemarksInInvoice() != null ? orderVO.getShowRemarksInInvoice() : false);
        purchaseOrder.setIsHotelGuest(orderVO.getIsHotelGuest() != null ? orderVO.getIsHotelGuest() : false);

        // out source attribute
        Set<PurchaseOrderOutSourceAttribute> poOutSourceAttrs = purchaseOrder.getPurchaseOrderOutSourceAttributes();
        OutSourceAttributeVO[] attributeVOs = orderVO.getOutSourceAttributeVOs();
        if (attributeVOs != null && attributeVOs.length > 0) {
            for (OutSourceAttributeVO attributeVO : attributeVOs) {
                if (StringUtils.isNoneBlank(attributeVO.getValue())) {
                    PurchaseOrderOutSourceAttribute attribute = new PurchaseOrderOutSourceAttribute();
                    attribute.setPurchaseOrder(purchaseOrder);
                    attribute.setOutSourceAttributeKey(outSourceAttributeKeyService.get(attributeVO.getOutSourceAttributeKeyId()));
                    attribute.setValue(attributeVO.getValue());
                    attribute.setIsActive(true);
                    attribute.setCreated(now);
                    attribute.setCreatedBy(loginUser);
                    attribute.setLastUpdated(now);
                    attribute.setLastUpdatedBy(loginUser);
                    poOutSourceAttrs.add(attribute);
                }
            }
        }
        if (orderVO.getMemberId() != null && orderVO.getMemberId().longValue() > 0) {
            purchaseOrder.setUser(userService.get(orderVO.getMemberId()));
        }
        if (orderVO.getShopId() != null && orderVO.getShopId().longValue() > 0) {
            purchaseOrder.setShop(shopService.get(orderVO.getShopId()));
        }

        if (orderVO.getBookId() != null && orderVO.getBookId().longValue() > 0) {
            Book book = bookService.get(orderVO.getBookId());
            purchaseOrder.setBook(book);
        }

        //cal tax amount
        purchaseOrder.setTaxAmount(0d);

        purchaseOrder.setTotalDiscount(0d);
        saveOrUpdate(purchaseOrder);

        //set common items and bundle items
        List<OrderItemVO> commonOrderItemVO = new ArrayList<OrderItemVO>();
        Map<OrderBundleVO, List<OrderItemVO>> bundleMap = new HashMap<OrderBundleVO, List<OrderItemVO>>();
        Map<Long, List<OrderItemVO>> bundles = new HashMap<Long, List<OrderItemVO>>();
        Map<Long, OrderBundleVO> bundleVOs = new HashMap<Long, OrderBundleVO>();

        Iterator<OrderItemVO> it = orderVO.getItemVOs().iterator();
        while (it.hasNext()) {
            OrderItemVO orderItemVO = it.next();

            if (orderItemVO.getNotShowCheckOutStatus() == null) {
                orderItemVO.setNotShowCheckOutStatus(orderVO.getCheckOutStatus());
            }

            if (orderItemVO.getBundleId() == null) {
                commonOrderItemVO.add(orderItemVO);
                continue;
            }
            //generate bundle map
            ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());
            Integer qty = orderItemVO.getQty();
            double effectiveValue = po.getFinalPrice(orderVO.getShopId()) * qty;

            Long bundleId = orderItemVO.getBundleId();
            ProductBundle pb = bundleService.get(bundleId);

            OrderBundleVO bundleVO;
            List<OrderItemVO> bundleList = bundles.get(bundleId);
            if (bundleList != null && bundleList.size() > 0) {
                bundleList.add(orderItemVO);
                bundleVO = bundleVOs.get(bundleId);
                bundleVO.setTotalBundleEffectiveVal(bundleVO.getTotalBundleEffectiveVal() + effectiveValue);
            } else {
                bundleList = new ArrayList<OrderItemVO>();
                bundleList.add(orderItemVO);

                bundleVO = new OrderBundleVO();
                bundleVO.setBundleAmount(pb.getBundleAmount());
                bundleVO.setBundleId(bundleId);
                bundleVO.setGroups(pb.getGroups());
                bundleVO.setTotalBundleEffectiveVal(effectiveValue);
                bundles.put(bundleId, bundleList);
                bundleVOs.put(bundleId, bundleVO);
                bundleMap.put(bundleVO, bundleList);
            }
        }
        setPurchaseCommonItems(orderVO, purchaseOrder, commonOrderItemVO);
        setPurchaseBundleItems(orderVO, purchaseOrder, bundleMap);
        //saveOrUpdate order
        saveOrUpdate(purchaseOrder);


        //set loyalty points
        setLoyaltyPoints(CommonConstant.POINTS_EARN_CHANNEL_SALES, purchaseOrder, purchaseOrder.getTotalAmount());

//		//earn points and member upgrade/downgrade level plugin
//		reviewSpaPointsAndLoyaltyLevel(purchaseOrder,purchaseOrder.getTotalAmount(),CommonConstant.POINTS_EARN_CHANNEL_SALES,
//				CommonConstant.ACTION_ADD,null, purchaseOrder.getUser().isMember());

        //set tips item
        setTipsItems(orderVO, purchaseOrder);

        //set payments
        setPayments(purchaseOrder, orderVO);

        //set order survey
        setOrderSurvey(purchaseOrder);
        // create by william -- 2018-9-14
        changeBooItemStatus(purchaseOrder.getId(), orderVO.getCheckOutStatus());

        return purchaseOrder.getId();
    }

    private void setPurchaseCommonItems(OrderVO orderVO, PurchaseOrder purchaseOrder, List<OrderItemVO> itemVOs) {
        Set<PurchaseItem> items = purchaseOrder.getPurchaseItems();
        logger.debug("check out setPurchaseItems---------");
        double totalAmount = purchaseOrder.getTotalAmount();
        double totalDiscount = purchaseOrder.getTotalDiscount();

        if (itemVOs != null && itemVOs.size() > 0) {
            PurchaseItem item = null;
            for (OrderItemVO orderItemVO : itemVOs) {
                if (orderItemVO != null
                        && orderItemVO.getProductOptionId() != null
                        && orderItemVO.getProductOptionId().longValue() > 0) {

                    ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());

                    double price = po.getFinalPrice(orderVO.getShopId()).doubleValue();

				/*//redeem by treatment package
				if(orderItemVO.getPaidByPackageId() !=null){
					Prepaid prepaid=prepaidService.get(orderItemVO.getPaidByPackageId());
					if(prepaid.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)){
						price=prepaid.getPrepaidValue() / prepaid.getInitValue();
					}
				}*/

                    Integer duration = po.getDuration();

                    item = new PurchaseItem();
                    item.setPrice(price);

                    Integer qty = orderItemVO.getQty();
                    item.setQty(qty);

                    item.setDuration(duration);
                    item.setProductOption(po);

                    item.setStatus(purchaseOrder.getStatus());
                    item.setIsActive(true);
                    item.setCreated(purchaseOrder.getCreated());
                    item.setCreatedBy(purchaseOrder.getCreatedBy());
                    item.setPurchaseOrder(purchaseOrder);

                    items.add(item);

                    if (orderItemVO.getBookItemId() != null && orderItemVO.getBookItemId().longValue() > 0) {
                        item.setBookItemId(orderItemVO.getBookItemId().toString());
                        //更新bookItem的状态，并释放item的资源，并且check book是否需要更新状态
                        bookItemService.updateStatus(orderItemVO.getBookItemId(), CommonConstant.BOOK_STATUS_COMPLETE);

                        BookItem bi = bookItemService.get(orderItemVO.getBookItemId());
                        if (orderItemVO.getNotShowCheckOutStatus() != null && orderItemVO.getNotShowCheckOutStatus()) {
                            bi.setStatus(CommonConstant.BOOK_STATUS_NOT_SHOW);

                            List<BookItem> bookItems = bookItemService.getBookItemsByBookId(bi.getBook().getId());
                            if (bookItems != null && bookItems.size() > 0) {
                                int notShowStatus = 0;
                                for (BookItem bookItem : bookItems) {
                                    if (!CommonConstant.BOOK_STATUS_NOT_SHOW.equals(bookItem.getStatus())) {
                                        notShowStatus++;
                                    }
                                }

                                if (notShowStatus == 0) {
                                    bi.getBook().setStatus(CommonConstant.BOOK_STATUS_NOT_SHOW);
                                }
                            }
                        }

                        if (purchaseOrder.getBook() != null) {
                            purchaseOrder.setBook(bi.getBook());
                        }
                    }
                    if (StringUtils.isNotBlank(orderItemVO.getStartTime())) {
                        Date startTime = DateUtil.stringToDate(orderItemVO.getStartTime(), "HH:mm");
                        if (duration != null) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(startTime);
                            calendar.set(Calendar.MINUTE, duration);
                            Date endTime = calendar.getTime();
                            item.setEndTime(endTime);
                        }
                        item.setStartTime(startTime);
                    }
                    double finalDiscount = 0;
                    // cal discount
                    Double[] returnDiscount = calItemDiscount(orderItemVO.getExtraDiscount(), purchaseOrder.getShop().getId(), purchaseOrder.getUser().getId(), qty, po);
                    item.setExtraDiscountValue(returnDiscount[0]);
                    finalDiscount = returnDiscount[2];
                    item.setDiscountValue(finalDiscount);
                    logger.debug("check Item extra discount,public discount,final discount" + returnDiscount[0] + "," + returnDiscount[1] + "," + returnDiscount[2]);

                    //cal amount
                    Double[] itemEffectiveValAndAmount = calItemEffectiveValAndAmount(orderItemVO, price, finalDiscount);
                    item.setEffectiveValue(itemEffectiveValAndAmount[0]);
                    orderItemVO.setEffectiveAmount(itemEffectiveValAndAmount[0]);
                    orderItemVO.setNetAmount(itemEffectiveValAndAmount[2]);

                    item.setAmount(itemEffectiveValAndAmount[1]);
                    item.setNetAmount(itemEffectiveValAndAmount[2]);
                    //review Inventory
                    reviewInventory(item, false);

                    //saveOrUpdate purchase item
                    purchaseItemService.saveOrUpdate(item);

                    reviewPrepaid(orderItemVO, item.getId(), true, null);

                    //cal staff commission(用预付付款时，提成的计算所用的item amount与预付付款的amount无关)
                    calStaffCommission(orderItemVO, item, orderVO.getPurchaseDate(), true, purchaseOrder.getCompany().getId());

                    totalDiscount += finalDiscount;
                    totalAmount += item.getAmount();

                }
            }
        }
        purchaseOrder.setTotalAmount(totalAmount);
        purchaseOrder.setTotalDiscount(totalDiscount);
    }

    private void setPurchaseBundleItems(OrderVO orderVO, PurchaseOrder purchaseOrder, Map<OrderBundleVO, List<OrderItemVO>> bundleMap) {
        if (bundleMap == null || (bundleMap != null && bundleMap.size() == 0)) {
            return;
        }
        logger.debug("check out setPurchaseBundleItems---------");
        Set<PurchaseItem> items = purchaseOrder.getPurchaseItems();
        double totalAmount = purchaseOrder.getTotalAmount();
        double totalDiscount = purchaseOrder.getTotalDiscount();

        Iterator<OrderBundleVO> bundleMapIt = bundleMap.keySet().iterator();
        while (bundleMapIt.hasNext()) {
            OrderBundleVO bundleVO = bundleMapIt.next();
            totalAmount += bundleVO.getBundleAmount();
            PurchaseItem item = null;
            for (OrderItemVO orderItemVO : bundleMap.get(bundleVO)) {
                ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());

                double price = po.getFinalPrice(orderVO.getShopId()).doubleValue();
                Integer duration = po.getDuration();

                item = new PurchaseItem();
                item.setPrice(price);

                Integer qty = orderItemVO.getQty();
                item.setQty(qty);

                item.setDuration(duration);
                item.setProductOption(po);

                item.setStatus(purchaseOrder.getStatus());
                item.setIsActive(true);
                item.setCreated(purchaseOrder.getCreated());
                item.setCreatedBy(purchaseOrder.getCreatedBy());
                item.setPurchaseOrder(purchaseOrder);

                items.add(item);

                if (orderItemVO.getBookItemId() != null && orderItemVO.getBookItemId().longValue() > 0) {
                    item.setBookItemId(orderItemVO.getBookItemId().toString());
                    //更新bookItem的状态，并释放item的资源，并且check book是否需要更新状态
                    bookItemService.updateStatus(orderItemVO.getBookItemId(), CommonConstant.BOOK_STATUS_COMPLETE);

                    BookItem bi = bookItemService.get(orderItemVO.getBookItemId());
                    if (purchaseOrder.getBook() != null) {
                        purchaseOrder.setBook(bi.getBook());
                    }
                }
                if (StringUtils.isNotBlank(orderItemVO.getStartTime())) {
                    Date startTime = DateUtil.stringToDate(orderItemVO.getStartTime(), "HH:mm");
                    if (duration != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(startTime);
                        calendar.set(Calendar.MINUTE, duration);
                        Date endTime = calendar.getTime();
                        item.setEndTime(endTime);
                    }
                    item.setStartTime(startTime);
                }
                double finalDiscount = 0;
                item.setExtraDiscountValue(0d);
                item.setDiscountValue(finalDiscount);

                //cal amount
//		        Double[] itemEffectiveValAndAmount=calItemEffectiveValAndAmount(orderItemVO, price, finalDiscount);
                double effectiveValue = price - finalDiscount;
                item.setEffectiveValue(effectiveValue);
                orderItemVO.setEffectiveAmount(effectiveValue);

                item.setAmount(bundleVO.getBundleAmount() / bundleVO.getGroups());

                //review Inventory
                reviewInventory(item, false);

                //saveOrUpdate purchase item
                purchaseItemService.saveOrUpdate(item);

                reviewPrepaid(orderItemVO, item.getId(), true, null);

                orderItemVO.setAmount(bundleVO.getBundleAmount() / bundleVO.getGroups());

                calBundleStaffCommission(orderItemVO, item, purchaseOrder.getPurchaseDate(), true, purchaseOrder.getCompany().getId(), bundleVO.getBundleAmount(), bundleVO.getTotalBundleEffectiveVal());
            }
        }
        purchaseOrder.setTotalAmount(totalAmount);
        purchaseOrder.setTotalDiscount(totalDiscount);
    }

    private void setTipsItems(OrderVO orderVO, PurchaseOrder purchaseOrder) {

        logger.debug("check out setTipsItems---------");
        double totalAmount = purchaseOrder.getTotalAmount();
        double totalDiscount = purchaseOrder.getTotalDiscount();
        Set<PurchaseItem> items = purchaseOrder.getPurchaseItems();
        if (orderVO.getTipsItemVOs() != null && orderVO.getTipsItemVOs().size() > 0) {
            StaffCommission sc = null;
            PurchaseItem item = null;
            for (KeyAndValueVO kv : orderVO.getTipsItemVOs()) {
                String key = kv.getKey();
                String value = kv.getValue();

                ProductOption po = productOptionService.get("reference", CommonConstant.PRODUCT_OPTION_FOR_TIPS_REF);

                double price = Double.parseDouble(value);

                item = new PurchaseItem();
                item.setPrice(price);
                item.setDuration(null);
                item.setProductOption(po);

                item.setStatus(purchaseOrder.getStatus());
                item.setIsActive(true);
                item.setCreated(purchaseOrder.getCreated());
                item.setCreatedBy(purchaseOrder.getCreatedBy());
                item.setPurchaseOrder(purchaseOrder);
                item.setExtraDiscountValue(0d);
                item.setDiscountValue(0d);

                item.setEffectiveValue(price);
                item.setAmount(price);
                item.setQty(1);

                //saveOrUpdate purchase item
                purchaseItemService.saveOrUpdate(item);

                totalDiscount += 0d;
                totalAmount += price;

                sc = new StaffCommission();
                sc.setIsActive(true);
                sc.setIsRequested(false);
                sc.setCreated(item.getCreated());
                sc.setCreatedBy(item.getCreatedBy());
                sc.setDisplayOrder(1);

                sc.setPurchaseDate(purchaseOrder.getPurchaseDate());
                sc.setStaff(userService.get(Long.parseLong(key)));
                sc.setPurchaseItem(item);

                sc.setAmount(price);
                sc.setCommissionValue(0);
                sc.setCommissionRate(0);

                item.getStaffCommissions().add(sc);
                items.add(item);
            }
        }
        purchaseOrder.setTotalAmount(totalAmount);
        purchaseOrder.setTotalDiscount(totalDiscount);
    }

    private Double[] calItemEffectiveValAndAmount(OrderItemVO orderItemVO, double price, double finalDiscount) {

        Double[] itemEffectiveValAndAmount = new Double[3];
        Integer qty = orderItemVO.getQty();
        double effectiveValue = price * qty;
        itemEffectiveValAndAmount[0] = effectiveValue;

        double itemAmount = effectiveValue - finalDiscount;
        /*
         * Add the new field "Adjusted Net Value" in the Sales. and
         * allow Admin to update Sales to edit this field only..
         * This field should be null by default. Only use this field if this is not null and it will be use for commission calculation if not null.
         * So if null, commission = net value x commission %
         * If not null, (i.e. Admin added a value in this field), commission = Adjusted Net Value x commission%
         * */
        if (orderItemVO.getAdjustNetValue() != null && orderItemVO.getAdjustNetValue().doubleValue() > 0) {
            itemEffectiveValAndAmount[1] = itemAmount;
            //this net amount is equal adjust net value and it will be use for commission calculation
            itemEffectiveValAndAmount[2] = orderItemVO.getAdjustNetValue();
            return itemEffectiveValAndAmount;
        }

        // Net Amount
        // the net amount is for sales paid by packages.
        double netAmount = calculateNetAmountWhenRedeemPrepaid(effectiveValue, orderItemVO);
//        if(netAmount>0){
//        	 double netDiscount = effectiveValue - netAmount;
//        	 if(netDiscount > finalDiscount){
//             	itemAmount = netAmount;
//             }
//        }
        itemEffectiveValAndAmount[1] = itemAmount;
        itemEffectiveValAndAmount[2] = netAmount;
        return itemEffectiveValAndAmount;
    }

    private Double[] calItemDiscount(Double extraDiscount, Long shopId, Long memberId, Integer qty, ProductOption productOption) {

        Double[] returnDiscount = new Double[3];

        double publicDiscount = 0;
        double finalDiscount = 0;
        ;

        if (extraDiscount != null && extraDiscount >= 0) {
            finalDiscount = extraDiscount;
        } else {
            //public discount
            Double amount = qty * productOption.getFinalPrice(shopId);
            DiscountItemAdapter discountItemAdapter = new DiscountItemAdapter();
            discountItemAdapter.setAmount(new BigDecimal(amount));
            discountItemAdapter.setProductOption(productOption);
            DiscountItemPlugin.process(discountItemAdapter, shopId, memberId);
            publicDiscount = discountItemAdapter.getDiscountValue().doubleValue();

            finalDiscount = publicDiscount;

        }
        returnDiscount[0] = extraDiscount;
        returnDiscount[1] = publicDiscount;
        returnDiscount[2] = finalDiscount;

        return returnDiscount;
    }

    private Double calculateNetAmountWhenRedeemPrepaid(Double effectiveValue, OrderItemVO orderItemVO) {

        Double netAmount = 0d;
        Prepaid redeemPrepaid = null;
        if (orderItemVO.getPaidByPackageId() != null) {
            redeemPrepaid = prepaidService.get(orderItemVO.getPaidByPackageId());
        } else if (StringUtils.isNotBlank(orderItemVO.getPaidByVoucherRef())) {
            redeemPrepaid = prepaidService.get("reference", orderItemVO.getPaidByVoucherRef());
        }
        if (redeemPrepaid != null) {
//			System.out.println("---calculateNetAmountWhenRedeemPrepaid---effectiveValue::"+effectiveValue
//					+"---redeemPrepaid.getFaceValue():::"+redeemPrepaid.getFaceValue()+"---redeemPrepaid.getPrepaidValue()::"+redeemPrepaid.getPrepaidValue());
            netAmount = effectiveValue * (redeemPrepaid.getPrepaidValue() / redeemPrepaid.getFaceValue());
        }
        return netAmount;
    }

    private void reviewInventory(PurchaseItem item, Boolean isDelete) {
        if (item.getProductOption() != null && item.getProductOption().getIsGoods()) {
            InventoryTransactionVO transactionVO = new InventoryTransactionVO();
            transactionVO.setCompany(WebThreadLocal.getCompany());
            transactionVO.setProductOption(item.getProductOption());
            transactionVO.setProductOptionId(item.getProductOption().getId());
            transactionVO.setQty(item.getQty());
            transactionVO.setItem(item);
            transactionVO.setShop(item.getPurchaseOrder().getShop());
            transactionVO.setShopId(item.getPurchaseOrder().getShop().getId());

            if (isDelete) {
                transactionVO.setDirection(CommonConstant.INVENTORY_TRANSACTION_DIRECTION_IN);
                inventoryWarehouseService.saveOrUpdate(transactionVO);

                InventoryTransaction it = item.getInventoryTransactions().iterator().next();
                inventoryTransactionService.delete(it);

            } else {
                transactionVO.setTransactionType(CommonConstant.INVENTORY_TRANSACTION_TYPE_SALE);
                transactionVO.setDirection(CommonConstant.INVENTORY_TRANSACTION_DIRECTION_OUT);
                transactionVO.setEntryDate(item.getPurchaseOrder().getPurchaseDate());
                inventoryTransactionService.save(transactionVO);
            }
        }
    }

    private void reviewPrepaid(OrderItemVO orderItemVO, Long purchaseItemId, Boolean isReview, HttpSession httpSession) {
        //paid by prepaid
        Boolean paidByPrepaid = false;
        Prepaid prepaid = null;

        if (orderItemVO.getPaidByPackageId() != null) {
            paidByPrepaid = true;
            prepaid = prepaidService.get(orderItemVO.getPaidByPackageId());
        } else if (StringUtils.isNotBlank(orderItemVO.getPaidByVoucherRef())) {
            paidByPrepaid = true;
            prepaid = prepaidService.get("reference", orderItemVO.getPaidByVoucherRef());
        }

        if (paidByPrepaid) {
            //review prepaid
            if (isReview) {
                prepaidService.reviewPrepaidWhenPaidByPrepaid(orderItemVO, prepaid.getId(), purchaseItemId);

            } else {
                prepaidService.checkingPrepaidWhenPaidByPrepaid(prepaid.getId(), orderItemVO, httpSession);
            }
        } else {
            orderItemVO.setPrepaidPaidAmount(0d);
            orderItemVO.setFinalAmount(orderItemVO.getAmount());
        }
    }

    private void setPayments(PurchaseOrder purchaseOrder, OrderVO orderVO) {

        Set<PurchaseItem> items = purchaseOrder.getPurchaseItems();

        Set<Payment> payments = purchaseOrder.getPayments();
        List<KeyAndValueVO> paymentMethods = orderVO.getPaymentMethods();
        if (paymentMethods != null && paymentMethods.size() > 0) {
            Payment payment = null;
            Double totalAmount = 0d;
            Double recalItemAmount = 0d;
            for (KeyAndValueVO kv : paymentMethods) {
                if (kv != null && kv.getKey() != null) {
                    String key = kv.getKey();
                    if (kv.getId() != null && kv.getId().longValue() > 0 && StringUtils.isNoneBlank(kv.getValue())) {
                        PaymentMethod pm = paymentMethodService.get(kv.getId());
                        payment = new Payment();
                        payment.setCompany(WebThreadLocal.getCompany());
                        payment.setIsActive(true);
                        payment.setCreated(purchaseOrder.getCreated());
                        payment.setCreatedBy(purchaseOrder.getCreatedBy());
                        payment.setPurchaseOrder(purchaseOrder);
                        payment.setDisplayOrder(Integer.valueOf(key));

                        Double amount = 0D;
                        String value = kv.getValue();
                        if (StringUtils.isNotBlank(value)) {
                            amount = Double.valueOf(value);
                        }

                        payment.setAmount(amount);
                        payment.setPaymentMethod(pm);
                        payment.setStatus(CommonConstant.PAYMENT_STATUS_PAID);
                        payments.add(payment);

                        if (pm.getReference().equals(CommonConstant.PAYMENT_METHOD_REF_NO_PAYMENT)) {
                            // no need to add the amount
                            recalItemAmount += amount;
                        } else {
//							if(payment.getRedeemPrepaidTopUpTransaction() !=null){
//								items.remove(payment.getPurchaseItem());
//							}
                            totalAmount = totalAmount + amount;
                        }

                    }
                }
            }
            purchaseOrder.setTotalAmount(totalAmount);
            saveOrUpdate(purchaseOrder);

//			System.out.println("----items size--"+items.size());
            // recal item amount when there has no payment method.
            if (items != null && items.size() > 0) {
                for (PurchaseItem pi : items) {
                    if (pi.getAmount() <= 0) {
                        continue;
                    }
//					System.out.println("recalItemAmount---"+recalItemAmount+"---pi.getAmount()---"+pi.getAmount());
                    if (recalItemAmount >= pi.getAmount()) {
                        pi.setAmount(0d);
                        recalItemAmount = recalItemAmount - pi.getAmount();
                        purchaseItemService.saveOrUpdate(pi);
                    } else {
                        pi.setAmount(pi.getAmount() - recalItemAmount);
                        purchaseItemService.saveOrUpdate(pi);
                        recalItemAmount = 0d;
                        break;
                    }
                }
            }
        }
    }

    private void calStaffCommission(OrderItemVO orderItemVO, PurchaseItem item, Date purchaseDate, Boolean isSave, Long companyId) {

        logger.debug("check out calStaffCommission::::::purchaseDate{},isSave{}:" + purchaseDate + "," + isSave);
        if (orderItemVO.getProductOptionId() == null) {
            return;
        }
        Boolean foldCommission = Boolean.FALSE;
//		if(StringUtils.isNotBlank(orderItemVO.getPaidByVoucherRef())){
//        	Prepaid prepaid=prepaidService.get("reference", orderItemVO.getPaidByVoucherRef());
//        	if(prepaid !=null && (prepaid.getIsFree() !=null && prepaid.getIsFree())){
//        		foldCommission=Boolean.TRUE;
//        	}
//        }
        ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());

        List<StaffItemVO> therapists = orderItemVO.getTherapists();
        if (therapists == null) {
            return;
        }

        Set<StaffCommission> staffCommissions = null;
        if (isSave) {
            staffCommissions = item.getStaffCommissions();
            item.getStaffCommissions().clear();
        }

        //saveOrUpdate staff commission
        int therapistSize = 0;
        for (StaffItemVO staffItemVO : therapists) {
            if (staffItemVO.getStaffId() != null && staffItemVO.getStaffId().longValue() > 0) {
                therapistSize++;
            }
        }
        //
        Double effectiveValue = orderItemVO.getEffectiveAmount();
        if (orderItemVO.getNetAmount() > 0) {
            effectiveValue = orderItemVO.getNetAmount();
        }
        if (orderItemVO.getExtraDiscount() != null) {
            //extra discount did not be contained to cal commission
            double extraDiscount = orderItemVO.getExtraDiscount().doubleValue();
            effectiveValue = effectiveValue - extraDiscount;
        }// else public discount should be contained to cal commission
        effectiveValue = effectiveValue / therapistSize;

        Double amount = 0d;
        if (isSave) {
            amount = item.getAmount() / therapistSize;
        }
        // cal commission rate by total amount
        Category topestCategory = po.getProduct().getCategory().getTheTopestCategoryUnderRoot();

        StaffCommission sc = null;
        User staff = null;
        for (StaffItemVO staffItemVO : therapists) {
            if (staffItemVO != null && staffItemVO.getKey() != null) {
                Integer key = staffItemVO.getKey();
                Long staffId = staffItemVO.getStaffId();

                if (staffId != null && staffId.longValue() > 0) {
                    staff = userService.get(staffId);
                    //calculate commission
                    CommissionAdapter commissionAdapter = new CommissionAdapter();
                    commissionAdapter.setCategory(po.getProduct().getCategory());
                    commissionAdapter.setProduct(po.getProduct());
                    commissionAdapter.setEffectiveValue(new BigDecimal(effectiveValue.doubleValue()));
                    commissionAdapter.setStaffId(staffId);

                    CommissionCalculationPlugin.process(commissionAdapter);

                    double commissionRate = commissionAdapter.getCommissionRate().doubleValue();
                    double commission = commissionAdapter.getCommission().doubleValue();

                    double extraCommissionRate = commissionAdapter.getExtraCommission().doubleValue();
                    double extraCommission = commissionAdapter.getExtraCommission().doubleValue();

                    double targetCommissionRate = commissionAdapter.getTargetCommissionRate().doubleValue();
                    double targetCommission = commissionAdapter.getTargetCommission().doubleValue();

                    double targetExtraCommissionRate = commissionAdapter.getTargetExtraCommissionRate().doubleValue();
                    double targetExtraCommission = commissionAdapter.getTargetExtraCommission().doubleValue();

                    boolean hitTarget = false;
                    // product/treatment check bonus
                    if (topestCategory.getReference().equals(CommonConstant.CATEGORY_REF_GOODS) || topestCategory.getReference().equals(CommonConstant.CATEGORY_REF_TREATMENT)) {
                        hitTarget = checkWhetherHas2ndTierTarget(staff, purchaseDate, effectiveValue, isSave, companyId, false, topestCategory.getReference(),
                                targetCommissionRate, targetCommission, targetExtraCommissionRate, targetExtraCommission);
                    }
                    //
                    if (isSave) {
                        sc = new StaffCommission();
                        sc.setIsActive(true);
                        sc.setIsRequested((staffItemVO.getRequested() != null) ? staffItemVO.getRequested() : false);
                        sc.setCreated(item.getCreated());
                        sc.setCreatedBy(item.getCreatedBy());
                        sc.setDisplayOrder(key);

                        sc.setPurchaseDate(purchaseDate);
                        sc.setStaff(staff);
                        sc.setPurchaseItem(item);

                        sc.setEffectiveValue(effectiveValue);
                        sc.setAmount(amount);

                        sc.setCommissionValue(commission);
                        sc.setCommissionRate(commissionRate);
                        if (hitTarget) {
                            sc.setTargetCommission(targetCommission);
                            sc.setTargetCommissionRate(targetCommissionRate);

                            sc.setTargetExtraCommission(targetExtraCommission);
                            sc.setTargetExtraCommissionRate(targetExtraCommissionRate);
                        } else {
                            sc.setTargetCommission(0d);
                            sc.setTargetCommissionRate(0d);

                            sc.setTargetExtraCommission(0d);
                            sc.setTargetExtraCommissionRate(0d);
                        }
                        sc.setExtraCommission(extraCommission);
                        sc.setExtraCommissionRate(extraCommissionRate);

                        sc.setExtraCommission(extraCommission);
                        sc.setExtraCommissionRate(extraCommissionRate);
                        staffCommissions.add(sc);
                    } else {
                        staff = userService.get(staffId);
                        staffItemVO.setStaffName(staff.getDisplayName());
                        if (hitTarget) {
                            staffItemVO.setCommission(targetCommission);
                            staffItemVO.setExtraCommission(targetExtraCommission);
                        } else {
                            staffItemVO.setCommission(commission);
                            staffItemVO.setExtraCommission(extraCommission);
                        }
                    }
                }
            }
        }
    }

    private void calBundleStaffCommission(OrderItemVO orderItemVO, PurchaseItem item, Date purchaseDate, Boolean isSave, Long companyId, double bundleAmount, double totalEffectiveVal) {

        double bundleDiscount = bundleAmount / totalEffectiveVal;

        logger.debug("check out calBundleStaffCommission::::::purchaseDate{},isSave{}:" + purchaseDate + "," + isSave);
        if (orderItemVO.getProductOptionId() == null) {
            return;
        }
        ProductOption po = productOptionService.get(orderItemVO.getProductOptionId());

        List<StaffItemVO> therapists = orderItemVO.getTherapists();
        if (therapists == null) {
            return;
        }

        Set<StaffCommission> staffCommissions = null;
        if (isSave) {
            staffCommissions = item.getStaffCommissions();
            item.getStaffCommissions().clear();
        }

        Double commissionRate = 0d;
        //saveOrUpdate staff commission
        int therapistSize = 0;
        for (StaffItemVO staffItemVO : therapists) {
            if (staffItemVO.getStaffId() != null && staffItemVO.getStaffId().longValue() > 0) {
                therapistSize++;
            }
        }
        //
        Double effectiveValue = orderItemVO.getEffectiveAmount() / therapistSize;

        Double amount = effectiveValue * bundleDiscount / therapistSize;

        // cal commission rate by total amount
        Category topestCategory = po.getProduct().getCategory().getTheTopestCategoryUnderRoot();

        StaffCommission sc = null;
        User staff = null;
        for (StaffItemVO staffItemVO : therapists) {
            if (staffItemVO != null && staffItemVO.getKey() != null) {
                Integer key = staffItemVO.getKey();
                Long staffId = staffItemVO.getStaffId();

                if (staffId != null && staffId.longValue() > 0) {
                    staff = userService.get(staffId);
                    //calculate commission
                    CommissionAdapter commissionAdapter = new CommissionAdapter();
                    commissionAdapter.setCategory(po.getProduct().getCategory());
                    commissionAdapter.setProduct(po.getProduct());
                    commissionAdapter.setEffectiveValue(new BigDecimal(effectiveValue.doubleValue()));
                    commissionAdapter.setStaffId(staffId);

                    CommissionCalculationPlugin.process(commissionAdapter);
                    commissionRate = commissionAdapter.getCommissionRate().doubleValue();

                    double commission = effectiveValue.doubleValue() * bundleDiscount * commissionRate;

                    //
                    if (isSave) {
                        sc = new StaffCommission();
                        sc.setIsActive(true);
                        sc.setIsRequested((staffItemVO.getRequested() != null) ? staffItemVO.getRequested() : false);
                        sc.setCreated(item.getCreated());
                        sc.setCreatedBy(item.getCreatedBy());
                        sc.setDisplayOrder(key);

                        sc.setPurchaseDate(purchaseDate);
                        sc.setStaff(staff);
                        sc.setPurchaseItem(item);

                        sc.setEffectiveValue(effectiveValue);
                        sc.setAmount(amount);
                        sc.setCommissionValue(commission);
                        sc.setCommissionRate(commissionRate);

                        sc.setTargetCommission(0d);
                        sc.setTargetCommissionRate(0d);

                        sc.setTargetExtraCommission(0d);
                        sc.setTargetExtraCommissionRate(0d);

                        staffCommissions.add(sc);


                    } else {
                        staff = userService.get(staffId);
                        staffItemVO.setStaffName(staff.getDisplayName());
                        staffItemVO.setCommission(commission);
                    }
                }
            }
        }
    }

    @Override
    public void removePurchaseOrder(Long id) {
        Date now = new Date();
        String loginUser = WebThreadLocal.getUser().getUsername();

        PurchaseOrder order = get(id);
        Long companyId = order.getCompany().getId();

        boolean isBuyPrepaid = false;
        PrepaidTopUpTransaction buyPtt = null;
        for (PurchaseItem item : order.getPurchaseItems()) {

            reviewInventory(item, true);

            buyPtt = item.getBuyPrepaidTopUpTransaction();
            if (buyPtt != null) {
                //is buy prepaid
                isBuyPrepaid = true;
                if (buyPtt.getIsUsedTopUpTransaction()) {
                    //used prepaid
                    return;
                } else {
                    //not used prepaid
                    break;
                }
            }
        }

        if (isBuyPrepaid) {
            Prepaid prepaid = buyPtt.getPrepaid();
            if (prepaid.getFirstPrepaidTopUpTransaction().getId() == buyPtt.getId()) {
                prepaidService.deletePrepaid(prepaid.getId());
            } else {
                prepaidService.deletePrepaidTopUpTransaction(buyPtt.getId());
            }
        } else {
            //review payments
            for (Payment payment : order.getPayments()) {

                PurchaseItem item = payment.getPurchaseItem();
                PrepaidTopUpTransaction redeemPtt = payment.getRedeemPrepaidTopUpTransaction();
                if (redeemPtt != null) {
                    Prepaid prepaid = redeemPtt.getPrepaid();
                    //paid by prepaid
                    double amount = payment.getAmount();
                    if (prepaid.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)
                            || prepaid.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER)) {
                        redeemPtt.setRemainValue(redeemPtt.getRemainValue() + amount);
                        prepaid.setRemainValue(prepaid.getRemainValue().doubleValue() + amount);
                    } else {
                        redeemPtt.setRemainValue(redeemPtt.getRemainValue() + item.getQty());
                        prepaid.setRemainValue(prepaid.getRemainValue() + item.getQty());
                    }

                    redeemPtt.setIsActive(true);
                    redeemPtt.setLastUpdated(now);
                    redeemPtt.setLastUpdatedBy(loginUser);
                    prepaidTopUpTransactionService.saveOrUpdate(redeemPtt);

                    prepaid.setIsActive(true);
                    prepaid.setLastUpdated(now);
                    prepaid.setLastUpdatedBy(loginUser);
                    prepaidService.saveOrUpdate(prepaid);
                }
            }
            // payments
            order.getPayments().clear();

            List<User> staffListFromItemByBonus = new ArrayList<User>();// for recal commission rate
            Date purchaseDate = order.getPurchaseDate();// for recal commission rate

            Set<String> reCheckTargetCommission = new HashSet<String>();
            for (PurchaseItem item : order.getPurchaseItems()) {
                if (StringUtils.isNotBlank(item.getProdType())) {
                    reCheckTargetCommission.add(item.getProdType());
                    // staff commission
                    for (StaffCommission sc : item.getStaffCommissions()) {
                        if (item.getProductOption() != null) {
                            staffListFromItemByBonus.add(sc.getStaff());
                        }
                    }
                }
                item.getStaffCommissions().clear();

                // book item
                if (StringUtils.isNotBlank(item.getBookItemId())) {
                    bookItemService.updateStatus(Long.valueOf(item.getBookItemId()), CommonConstant.BOOK_STATUS_CHECKIN_SERVICE);
                }

            }
            // purchase items
            order.getPurchaseItems().clear();

            //loyalty points
            order.getLoyaltyPointsTransactions().clear();

            //review
            order.getReviews().clear();

            //survey
            order.getOrderSurveys().clear();

            //delete purchase order discount/production code

            //member upgrade/downgrade level plugin
            reviewSpaPointsAndLoyaltyLevel(order, order.getTotalAmount(), CommonConstant.POINTS_EARN_CHANNEL_SALES,
                    CommonConstant.ACTION_DELETE, null, order.getUser().isMember());

            // purchase order
            delete(order.getId());

            // flush hibernate session
            getSession().flush();

            //re cal Target Commission
            if (reCheckTargetCommission != null && reCheckTargetCommission.size() > 0 &&
                    staffListFromItemByBonus != null && staffListFromItemByBonus.size() > 0) {
                for (User staff : staffListFromItemByBonus) {
                    for (String prodType : reCheckTargetCommission) {
                        checkWhetherHas2ndTierTarget(staff, purchaseDate, 0d, true, companyId, true, prodType,
                                0d, 0d, 0d, 0d);
                    }
                }
            }
        }
    }

    @Override
    public void reviewSpaPointsAndLoyaltyLevel(PurchaseOrder purchaseOrder, Double earnPoints, String earnChannel, String action, String prepaidType, Boolean isMember) {
        Boolean whetherEarn = CommonConstant.WHETHER_EARN_POINTS;
        if (whetherEarn && isMember && prepaidType != null && prepaidType.equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)) {
            logger.debug("----reviewSpaPointsAndLoyaltyLevel---------action{}:" + action);
            if (CommonConstant.ACTION_ADD.equals(action)) {

                //add
                PointsAdjustVO loyaltyPointsAdjustVO = new PointsAdjustVO();

                loyaltyPointsAdjustVO.setEarnChannel(earnChannel);
                loyaltyPointsAdjustVO.setEarnDate(purchaseOrder.getPurchaseDate());

                if (earnChannel.equals(CommonConstant.POINTS_EARN_CHANNEL_PREPAID)) {

                    loyaltyPointsAdjustVO.setMonthLimit(CommonConstant.LOYALTY_POINTS_EXPIRED_MONTHS_BY_PREPAID);

                } else if (earnChannel.equals(CommonConstant.POINTS_EARN_CHANNEL_SALES)) {

                    loyaltyPointsAdjustVO.setMonthLimit(CommonConstant.LOYALTY_POINTS_EXPIRED_MONTHS_BY_SALES);
                }

                loyaltyPointsAdjustVO.setPurchaseOrder(purchaseOrder);
                loyaltyPointsAdjustVO.setUser(purchaseOrder.getUser());

                loyaltyPointsAdjustVO.setAdjustPoints(earnPoints);
                loyaltyPointsAdjustVO.setAction(CommonConstant.POINTS_ACTION_PLUS);

                spaPointsTransactionService.saveSpaPointsTransaction(loyaltyPointsAdjustVO, true);

            } else if (CommonConstant.ACTION_DELETE.equals(action)) {
                //delete

                List<SpaPointsTransaction> sptList = spaPointsTransactionService.
                        getSpaPointsTransactionByFields(null, null, purchaseOrder.getId(), true);

                if (sptList != null && sptList.size() > 0) {
                    SpaPointsTransaction spt = sptList.get(0);
                    spaPointsTransactionService.delete(spt);

                    //review loyalty level
                    Map filterMap = CollectionUtils.getLightWeightMap();
                    filterMap.put("user", spt.getUser());
                    userLoyaltyLevelService.reviewLoyaltyLevel(filterMap, false);
                }

            }
        }
    }

    private void setLoyaltyPoints(String earnChannel, PurchaseOrder purchaseOrder, Double earnPoints) {

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
        spaPointsAdjustVO.setAction(CommonConstant.POINTS_ACTION_PLUS);

        loyaltyPointsTransactionService.saveLoyaltyPointsTransaction(spaPointsAdjustVO);
    }

    @Override
    public Double[] calculateTotalSalesAndCommissionByCategory(User staff, Date purchaseDate, Category category) {
        Double[] returnVal = new Double[2];

        Map filterMap = CollectionUtils.getLightWeightMap();

        DateTime fromDate = new DateTime(purchaseDate).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        DateTime toDate = new DateTime(purchaseDate).dayOfMonth().withMaximumValue().withSecondOfMinute(0);

        Long companyId = null;
        if (WebThreadLocal.getCompany() != null) {
            companyId = WebThreadLocal.getCompany().getId();
        }
        List<Long> allChildrens = new ArrayList<Long>();
        List<Long> childrenCategoryIds = categoryService.getAllChildrenByCategory(allChildrens, category.getId());
        Double commissionStatistics = 0d;
        Double salesStatistics = 0d;
        if (childrenCategoryIds != null && childrenCategoryIds.size() > 0) {
            //cal sales and commission
            filterMap.clear();
            filterMap.put("fromDate", fromDate.toDate());
            filterMap.put("toDate", toDate.toDate());
            filterMap.put("staffId", staff.getId());
            filterMap.put("companyId", companyId);
            Double[] sum = staffCommissionDao.sumStaffCommissionByFilter(filterMap, childrenCategoryIds);

            if (sum != null && sum.length > 0) {
                if (sum[0] != null) {
                    commissionStatistics = sum[0];
                }
                if (sum[1] != null) {
                    salesStatistics = sum[1];
                }
            }
        }
        logger.debug("category ref--" + category.getReference() + "--total commissionStatistics {}::" + commissionStatistics + "--- salesStatistics {}::" + salesStatistics);

        returnVal[0] = salesStatistics;
        returnVal[1] = commissionStatistics;

        return returnVal;
    }

    /*
	 *  1st tier - monthly target not reached
		2nd tier- monthly target reached
	Once hit the treatment target，the treatment sales of this month will use the new commission rate.
	 * */
    private Boolean checkWhetherHas2ndTierTarget(User staff, Date purchaseDate, Double effectiveValue, Boolean isSave, Long companyId,
                                                 Boolean isDelete, String prodType, double targetCommissionRate, double targetCommission,
                                                 double targetExtraCommissionRate, double targetExtraCommission) {

        boolean hitTarget = false;
        Map<String, Double> targetAmountsByTypeMap = payrollService.getTargetAmountsByTypeMap(staff);
        if (targetAmountsByTypeMap == null) {
            return hitTarget;
        }
        Double targetAmount = targetAmountsByTypeMap.get(prodType);
        if (targetAmount == null || (targetAmount != null && targetAmount.doubleValue() == 0)) {
            return hitTarget;
        }
        Category category = categoryService.getByReference(prodType, companyId);
        Double[] totalSalesAndCommission = calculateTotalSalesAndCommissionByCategory(staff, purchaseDate, category);
        Double totalSales = totalSalesAndCommission[0];
        logger.debug("checkWhetherHas2ndTierTarget totalSales {},effectiveValue{},TargetAmount{},isDelete{}:::"
                + totalSales + "," + effectiveValue + "," + targetAmount + "," + isDelete);
        totalSales += effectiveValue;

        if (totalSales.doubleValue() >= targetAmount) {
            hitTarget = true;
            if (!isDelete) {
                if (isSave) {
                    //re-cal all commission for this staff using new commission rate
                    List<StaffCommission> scList = staffCommissionService.getStaffCommissionsByPurchaseDateAndStaffAndCategory(purchaseDate, staff.getId(), category, companyId);
                    if (scList != null && scList.size() > 0) {
                        for (StaffCommission sc : scList) {
                            sc.setTargetExtraCommission(targetExtraCommission);
                            sc.setTargetCommission(targetCommission);
                            sc.setTargetCommissionRate(targetCommissionRate);
                            sc.setTargetExtraCommissionRate(targetExtraCommissionRate);

                            staffCommissionService.saveOrUpdate(sc);
                        }
                    }
                }
            }

        } else {
            if (isDelete) {//using commisison rate
                List<StaffCommission> scList = staffCommissionService.getStaffCommissionsByPurchaseDateAndStaffAndCategory(purchaseDate, staff.getId(), category, companyId);
                if (scList != null && scList.size() > 0) {
                    for (StaffCommission sc : scList) {

                        sc.setTargetExtraCommission(0d);
                        sc.setTargetCommission(0d);
                        sc.setTargetCommissionRate(0d);
                        sc.setTargetExtraCommissionRate(0d);
                        staffCommissionService.saveOrUpdate(sc);
                    }
                }
            }
        }
        return hitTarget;
    }

    @Override
    public List<ClientViewVO> getSalesAnalysisByClientAndShop(Map parameters) {
        return purchaseOrderDao.getSalesAnalysisByClientAndShop(parameters);
    }

    @Override
    public List<PurchaseOrder> getSalesHistoryList(Map parameters) {
        Date startDate = (Date) parameters.get("startDate");
        Date endDate = (Date) parameters.get("endDate");
        Long companyId = (Long) parameters.get("companyId");
        Long userId = (Long) parameters.get("userId");

        DetachedCriteria idCriteria = DetachedCriteria.forClass(PurchaseOrder.class);
        idCriteria.add(Restrictions.eq("company.id", companyId));
        idCriteria.add(Restrictions.eq("isActive", true));
        idCriteria.add(Restrictions.eq("status", CommonConstant.ORDER_STATUS_COMPLETED));
        if (startDate != null) {
            idCriteria.add(Restrictions.le("purchaseDate", DateUtil.getLastMinuts(endDate)));
        }
        if (endDate != null) {
            idCriteria.add(Restrictions.ge("purchaseDate", DateUtil.getFirstMinuts(startDate)));
        }
        idCriteria.add(Restrictions.eq("user.id", userId));
        idCriteria.addOrder(Order.desc("purchaseDate"));

        List<PurchaseOrder> orderList = list(idCriteria);
        return orderList;
    }

    public List getSpendingSummaryList(Map parameters) {

        Map spendingSummaryMap = new HashMap();
        /*Date endDate = (Date)parameters.get("endDate");*/

        //treatments
        spendingSummaryMap.put(CommonConstant.CATEGORY_REF_TREATMENT, new SpendingSummaryVO(CommonConstant.CATEGORY_REF_TREATMENT,
                new Double(0), new Double(0), new Double(0), new Double(0), new Double(0), new Double(0)));
        //goods
        spendingSummaryMap.put(CommonConstant.CATEGORY_REF_GOODS, new SpendingSummaryVO(CommonConstant.CATEGORY_REF_GOODS,
                new Double(0), new Double(0), new Double(0), new Double(0), new Double(0), new Double(0)));

        //hair salon
        spendingSummaryMap.put(CommonConstant.CATEGORY_REF_HAIRSALON, new SpendingSummaryVO(CommonConstant.CATEGORY_REF_HAIRSALON,
                new Double(0), new Double(0), new Double(0), new Double(0), new Double(0), new Double(0)));

        Integer[] months = new Integer[]{1, 3, 6, 12, 24, 100};
        List list = new ArrayList<>();
        for (int month : months) {
            Date endDate = new Date();
            if (month != 100) {
                Date startDate = getSpendingSummaryDate(endDate, month);
                parameters.put("startDate", startDate);
                parameters.put("endDate", endDate);

            } else if (month == 100) {

                parameters.put("startDate", null);
                parameters.put("endDate", null);
            }
            list = purchaseItemDao.getSpengingSummary(parameters);

            Iterator it = list.iterator();
            while (it.hasNext()) {
                SpendingSummaryVO tempVO = (SpendingSummaryVO) it.next();

                SpendingSummaryVO spendingSummaryVO = (SpendingSummaryVO) spendingSummaryMap.get(tempVO.getProdType());
                if (spendingSummaryVO != null) {
                    if (month == 1) {
                        spendingSummaryVO.setTwentyFourMonth((tempVO.getValue() != null) ? tempVO.getValue() : new Double(0));
                    } else if (month == 1) {
                        spendingSummaryVO.setOneMonth((tempVO.getValue() != null) ? tempVO.getValue() : new Double(0));
                    } else if (month == 3) {
                        spendingSummaryVO.setThreeMonth((tempVO.getValue() != null) ? tempVO.getValue() : new Double(0));
                    } else if (month == 6) {
                        spendingSummaryVO.setSixMonth((tempVO.getValue() != null) ? tempVO.getValue() : new Double(0));
                    } else if (month == 12) {
                        spendingSummaryVO.setTwelveMonth((tempVO.getValue() != null) ? tempVO.getValue() : new Double(0));
                    } else if (month == 24) {
                        spendingSummaryVO.setTwentyFourMonth((tempVO.getValue() != null) ? tempVO.getValue() : new Double(0));
                    } else if (month == 100) {
                        spendingSummaryVO.setAllMonth((tempVO.getValue() != null) ? tempVO.getValue() : new Double(0));
                    }

                }
            }
        }
        return new ArrayList(spendingSummaryMap.values());
    }

    private Date getSpendingSummaryDate(Date endDate, int month) {
        Calendar c = new GregorianCalendar();
        c.setTime(endDate);
        c.add(Calendar.MONTH, -month);

        Date startDate = DateUtil.getFirstMinuts(c.getTime());
        return startDate;
    }

    @Override
    public Double getTotalRevenueByFilters(Date startDate, Date endDate, Long shopId, Long companyId) {
        return purchaseOrderDao.getTotalRevenueByFilters(startDate, endDate, shopId, companyId);
    }

    @Override
    public Double getTotalServiceByFilters(Date startDate, Date endDate, Long shopId, Long companyId) {
        Double treatments = purchaseOrderDao.getTotalAmountByProdType(startDate, endDate, shopId, companyId, CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED, CommonConstant.CATEGORY_REF_TREATMENT);
        Double hairSalons = purchaseOrderDao.getTotalAmountByProdType(startDate, endDate, shopId, companyId, CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED, CommonConstant.CATEGORY_REF_HAIRSALON);
        return treatments.doubleValue() + hairSalons.doubleValue();
    }

    @Override
    public Double getTotalPackageByFilters(Date startDate, Date endDate, Long shopId, Long companyId) {
        return purchaseOrderDao.getTotalPackageByFilters(startDate, endDate, shopId, companyId);
    }

    @Override
    public Double getTotalAmountByProdType(Date startDate, Date endDate, Long shopId, Long companyId, String status, String prodType) {
        return purchaseOrderDao.getTotalAmountByProdType(startDate, endDate, shopId, companyId, status, prodType);
    }

    @Override
    public Double getTotalRetailByFilters(Date startDate, Date endDate, Long shopId, Long companyId) {
        return purchaseOrderDao.getTotalAmountByProdType(startDate, endDate, shopId, companyId, CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED, CommonConstant.CATEGORY_REF_GOODS);
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrderByIdsOfSalesDetails(SalesSearchVO salesSearchVO) {
        List<PurchaseOrder> purchaseOrderList = new ArrayList();
        List<Long> purchaseOrderIds = purchaseItemDao.getPurchaseOrderIdOfSalesDetails(salesSearchVO);
        if (purchaseOrderIds != null && purchaseOrderIds.size() > 0) {
            Long[] ids = (Long[]) purchaseOrderIds.toArray(new Long[purchaseOrderIds.size()]);
            purchaseOrderList = purchaseOrderDao.getPurchaseOrdersByIds(ids);
        }
        return purchaseOrderList;
    }

    @Override
    public Map<String, Object> getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(SalesSearchVO salesSearchVO) {
        List<PurchaseOrder> purchaseOrderList = getPurchaseOrderByIdsOfSalesDetails(salesSearchVO);
        return getPaymentAmountAndTotalRevenueAndGrossRevenueByOrderList(purchaseOrderList, salesSearchVO.getStaffId());
    }

    @Override
    public Map<String, Object> getPaymentAmountAndTotalRevenueAndGrossRevenueByOrderList(List<PurchaseOrder> orderList, Long staffId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Double> paymentAmount = new HashMap<String, Double>();
        Double totalCommission = 0d;
        Double grossRevenue = 0d;
        Double totalRevenue = 0d;
        Double totalExtraCommission = 0d;
        Double totalTargetExtraCommission = 0d;
        Double totalTargetCommission = 0d;
        if (orderList.size() > 0) {
            for (PurchaseOrder po : orderList) {
                //gross revenue = order amount
                grossRevenue += po.getTotalAmount();
                totalRevenue += po.getTotalAmount();
                for (PurchaseItem pi : po.getPurchaseItems()) {
                    for (StaffCommission sc : pi.getStaffCommissions()) {
                        if (staffId != null && !sc.getStaff().getId().equals(staffId)) {
                            continue;
                        }
                        totalCommission += sc.getCommissionValue();
                        totalExtraCommission += sc.getExtraCommission();
                        totalTargetExtraCommission += sc.getTargetExtraCommission();
                        totalTargetCommission += sc.getTargetCommission();
                    }
                }
                if (po.getPayments() != null) {
                    for (Payment pay : po.getPayments()) {
                        if (pay != null && pay.getRedeemPrepaidTopUpTransaction() != null && pay.getRedeemPrepaidTopUpTransaction().getProductOption() != null && pay.getRedeemPrepaidTopUpTransaction().getProductOption().getReference().equals("PO-20161229-1489")) {
                            //sale paid by franchise prepaid
                            continue;
                        }
                        Double amount = pay.getAmount();
                        //total revenue = order amount  + no payment
                        if (pay.getPaymentMethod().getReference().equals(CommonConstant.PAYMENT_METHOD_REF_NO_PAYMENT)) {
                            totalRevenue += amount;
                        }
                        //
                        if (paymentAmount.get(pay.getPaymentMethod().getName()) != null) {
                            amount += paymentAmount.get(pay.getPaymentMethod().getName()).doubleValue();
                        }
                        paymentAmount.put(pay.getPaymentMethod().getName(), amount);
                    }
                }
            }
        }
        returnMap.put("totalCommission", totalCommission);
        returnMap.put("totalRevenue", totalRevenue);
        returnMap.put("paymentAmount", paymentAmount);
        returnMap.put("grossRevenue", grossRevenue);
        returnMap.put("totalExtraCommission",totalExtraCommission);
        returnMap.put("totalTargetExtraCommission",totalTargetExtraCommission);
        returnMap.put("totalTargetCommission",totalTargetCommission);
        return returnMap;
    }

    @Override
    public Long getCountOrdersByFilters(String fromDate, String endDate, String orderStatus, Long userId) {
        return purchaseOrderDao.getCountOrdersByFilters(fromDate, endDate, orderStatus, userId);
    }

    @Override
    public void runFTCJourney(Long userId, Long marketingCampaignId) {
        System.out.println(" run ----runFTCJourney---start--");
        Date currentDate = new Date();

        String loginUser = "PurchasePackage3MMarketingCampaignJob";
        // find all transactions of this campaign
        List<UserMarketingCampaignTransaction> umps = userMarketingCampaignTransactionService.getListByFilters(userId, "23", true);

        // set transactions is active to 0
        // update "last updated" of member who have transactions
        if (umps != null && umps.size() > 0) {
            for (UserMarketingCampaignTransaction ump : umps) {
                userMarketingCampaignTransactionService.delete(ump.getId());
                User user = ump.getUser();
                user.setLastUpdated(currentDate);
                user.setLastUpdatedBy(loginUser);
                userService.saveOrUpdate(user);
            }
        }

        //#23A or #23B
        MarketingCampaign marketingCampaign = marketingCampaignService.get(marketingCampaignId);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -3);
        Date last3MonthDate = calendar.getTime();

        Long countNumber = null;
        try {
            countNumber = purchaseOrderDao.getCountOrdersByFilters(null, DateUtil.dateToString(currentDate, "yyyy-MM-dd"), CommonConstant.ORDER_STATUS_COMPLETED, userId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("---countNumber---" + countNumber);
        if (countNumber == null) {
            return;
        }
        if (countNumber > 1) {
            return;
        }
        User user = userService.get(userId);
        UserMarketingCampaignTransaction umct = new UserMarketingCampaignTransaction();
        umct.setCreated(currentDate);
        umct.setCreatedBy(loginUser);
        umct.setIsActive(true);
        umct.setUser(user);
        umct.setMarketingCampaign(marketingCampaign);
        userMarketingCampaignTransactionService.saveOrUpdate(umct);

        // update "last updated" of member who have transactions
        user.setLastUpdated(currentDate);
        user.setLastUpdatedBy(loginUser);
        userService.saveOrUpdate(user);

//			select DISTINCT(u.id),u.username,u.full_name,s.name,"
//			+"u.first_name,u.last_name,u.gender,u.email,u.date_of_birth"

        List<Object[]> searchResults = new ArrayList<Object[]>();
        Object[] userObj = new Object[9];
        userObj[0] = user.getId();
        userObj[1] = user.getUsername();
        userObj[2] = user.getFullName();
        userObj[3] = user.getShop().getName();
        userObj[4] = user.getFirstName();
        userObj[5] = user.getLastName();
        userObj[6] = user.getGender();
        userObj[7] = user.getEmail();
        userObj[8] = user.getDateOfBirth();

        searchResults.add(userObj);

        Map<String, String> returnFiles = userService.getMemberDetailsImortToSFByCSV(null, false, searchResults);

        if (returnFiles != null && returnFiles.size() > 0) {
            String accountFile = returnFiles.get(APIConstant.SF_OBJECT_ACCOUNT);
            String contactFile = returnFiles.get(APIConstant.SF_OBJECT_CONTACT);

            ImportDataToSFThread.getInstance(accountFile, contactFile, null, APIConstant.OPERATION_ENUM_UPSERT, false, searchResults).start();
        }
        System.out.println(" run ----runFTCJourney---end--");
    }

    @Override
    public void sendThankYouEmail(PurchaseOrder purchaseOrder) {
        int delay_minis = 2;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, delay_minis);
        Date triggerTime = calendar.getTime();

        boolean send = false;
        List<String> productTypes = CommonConstant.productTypes;
        for (PurchaseItem purchaseItem : purchaseOrder.getPurchaseItems()) {
            if (productTypes.contains(purchaseItem.getProductOption().getProduct().getProdType())) {
                send = true;
                break;
            }
        }

        if (send) {
            // cron expression
            String cronExpression = "";
            CronUtil calHelper = new CronUtil(triggerTime);
            cronExpression = calHelper.getSeconds() + " " +
                    calHelper.getMins() + " " +
                    calHelper.getHours() + " " +
                    calHelper.getDaysOfMonth() + " " +
                    calHelper.getMonths() + " " +
                    calHelper.getDaysOfWeek() + " " +
                    calHelper.getYears();

//			System.out.println("-sendThankYouEmail-----delay----triggerTime---"+triggerTime+"----cronExpression---"+cronExpression);

            //schedule it
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = null;

            try {
                scheduler = schedulerFactory.getScheduler();
                // create job
                JobDetail job = JobBuilder.newJob(ReviewDelayScheduleJob.class)
                        .withIdentity(RandomUtil.generateRandomNumberWithTime("REVIEW_DELAY_JOB_"), "DEFAULT")
                        .build();
                job.getJobDataMap().put("purchaseOrder", purchaseOrder);
                job.getJobDataMap().put("user", purchaseOrder.getUser());
                job.getJobDataMap().put("urlRoot", WebThreadLocal.getUrlRoot());
                job.getJobDataMap().put("company", WebThreadLocal.getCompany());
                job.getJobDataMap().put("orderSurveyService", orderSurveyService);

                // create trigger
                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(RandomUtil.generateRandomNumberWithTime("REVIEW_DELAY_JOB_"), "DEFAULT")
                        .withSchedule(
                                CronScheduleBuilder.cronSchedule(cronExpression))
                        .build();
                scheduler.scheduleJob(job, trigger);
                scheduler.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public Long getCountMembersFromOrdersByFilters(SalesSearchVO salesSearchVO) {
        return purchaseOrderDao.getCountMembersFromOrdersByFilters(salesSearchVO);
    }

    private void setOrderSurvey(PurchaseOrder purchaseOrder) {
        Boolean survey = false;
        List<String> productTypes = CommonConstant.productTypes;
        for (PurchaseItem purchaseItem : purchaseOrder.getPurchaseItems()) {
            if (productTypes.contains(purchaseItem.getProductOption().getProduct().getProdType())) {
                survey = true;
                break;
            }
        }
        if (survey) {
            OrderSurvey os = new OrderSurvey();
            os.setPurchaseOrder(purchaseOrder);
            os.setMember(purchaseOrder.getUser());
            os.setIsActive(true);
            os.setStatus("ACTIVING");
            os.setOrderCompletedDate(purchaseOrder.getCreated());
            os.setCreated(purchaseOrder.getCreated());
            os.setLastUpdated(purchaseOrder.getLastUpdated());
            os.setCreatedBy(purchaseOrder.getCreatedBy());
            os.setLastUpdatedBy(purchaseOrder.getLastUpdatedBy());
            orderSurveyService.saveOrUpdate(os);
        }
    }

    @Override
    public PurchaseOrder saveAppsPurchaseOrder(Long bookingId, Boolean paid) {
        logger.debug("check out savePurchaseOrder---------");
        Company company = new Company();
        company.setId(1L);
        /*		String loginUser=WebThreadLocal.getUser().getUsername();*/
        Date now = new Date();

        Book book = bookService.get(bookingId);

        Date purchaseDate = DateUtil.getFirstMinuts(book.getAppointmentTime());
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCompany(company);
        purchaseOrder.setIsActive(true);
        purchaseOrder.setCreated(now);
        purchaseOrder.setCreatedBy(book.getUser().getUsername());
        //by paypal set order's status is pending and set payment is unpaid.
        purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_PENDING);
        purchaseOrder.setRemarks(book.getRemarks());

        purchaseOrder.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PURCHASE_ORDER_REF_PREFIX));
        purchaseOrder.setPurchaseDate(purchaseDate);
        purchaseOrder.setShowRemarksInInvoice(false);
        purchaseOrder.setIsHotelGuest(false);

        if (book.getUser() != null) {
            purchaseOrder.setUser(book.getUser());
        }
        if (book.getShop() != null) {
            purchaseOrder.setShop(book.getShop());
        }

        purchaseOrder.setBook(book);

        //cal tax amount
        purchaseOrder.setTaxAmount(0d);

        purchaseOrder.setTotalDiscount(0d);
        if (paid) {
            saveOrUpdate(purchaseOrder);
        }
        //set items
        setAppsPurchaseItems(book, purchaseOrder, paid);
        //from apps discount
        if (paid) {
            //saveOrUpdate order
            saveOrUpdate(purchaseOrder);

//			//earn points and member upgrade/downgrade level plugin
//			reviewSpaPointsAndLoyaltyLevel(purchaseOrder,purchaseOrder.getTotalAmount(),CommonConstant.POINTS_EARN_CHANNEL_SALES,
//					CommonConstant.ACTION_ADD,null, purchaseOrder.getUser().isMember());
//
//			//set tips item
//			setTipsItems(orderVO, purchaseOrder);

            //set payments
            setAppsPayments(purchaseOrder);

            //set order survey
            setOrderSurvey(purchaseOrder);
        }
        return purchaseOrder;
    }

    private void setAppsPurchaseItems(Book book, PurchaseOrder purchaseOrder, Boolean paid) {
        Set<PurchaseItem> items = purchaseOrder.getPurchaseItems();
        logger.debug("check out setPurchaseItems---------");
        double totalAmount = purchaseOrder.getTotalAmount();
        double totalDiscount = purchaseOrder.getTotalDiscount();

        double onlineAmt = 0;
        double shopAmt = 0;
        Set<BookItem> bookItems = book.getBookItems();
        if (bookItems != null && bookItems.size() > 0) {
            PurchaseItem item = null;
            for (BookItem bi : bookItems) {
                if (bi != null
                        && bi.getProductOption() != null) {

                    ProductOption po = bi.getProductOption();

                    double price = po.getFinalPrice(book.getShop().getId()).doubleValue();
                    Integer duration = po.getDuration();

                    item = new PurchaseItem();
                    item.setPrice(price);

                    Integer qty = 1;
                    item.setQty(qty);

                    item.setDuration(duration);
                    item.setProductOption(po);

                    item.setStatus(purchaseOrder.getStatus());
                    item.setIsActive(true);
                    item.setCreated(purchaseOrder.getCreated());
                    item.setCreatedBy(purchaseOrder.getCreatedBy());
                    item.setPurchaseOrder(purchaseOrder);
                    items.add(item);

//				if(orderItemVO.getBookItemId() !=null && orderItemVO.getBookItemId().longValue()>0){
//					item.setBookItemId(orderItemVO.getBookItemId().toString());
//					//更新bookItem的状态，并释放item的资源，并且check book是否需要更新状态
//					bookItemService.updateStatus(orderItemVO.getBookItemId(), CommonConstant.BOOK_STATUS_COMPLETE);
//
//					BookItem bi=bookItemService.get(orderItemVO.getBookItemId());
//					if(purchaseOrder.getBook() !=null){
//						purchaseOrder.setBook(bi.getBook());
//					}
//				}
                    if (bi.getAppointmentTime() != null) {
                        item.setStartTime(bi.getAppointmentTime());
                    }
                    if (bi.getAppointmentEndTime() != null) {
                        item.setEndTime(bi.getAppointmentEndTime());
                    }
                    // cal discount
                    Double[] returnDiscount = calItemDiscount(null, purchaseOrder.getShop().getId(), purchaseOrder.getUser().getId(), qty, po);
                    item.setExtraDiscountValue(returnDiscount[0]);
                    double finalDiscount = returnDiscount[2];

                    item.setDiscountValue(finalDiscount);
                    logger.debug("check Item extra discount,public discount,final discount" + returnDiscount[0] + "," + returnDiscount[1] + "," + returnDiscount[2]);

                    //cal amount
                    OrderItemVO orderItemVO = new OrderItemVO();
                    orderItemVO.setQty(1);
                    Double[] itemEffectiveValAndAmount = calItemEffectiveValAndAmount(orderItemVO, price, finalDiscount);
                    item.setEffectiveValue(itemEffectiveValAndAmount[0]);
                    item.setAmount(itemEffectiveValAndAmount[1]);
                    item.setNetAmount(itemEffectiveValAndAmount[2]);

                    //online discount
                    double onlineDiscount = item.getAmount() * 10 / 100;
                    if (!paid) {
                        onlineAmt += item.getAmount() - onlineDiscount;
                        shopAmt += item.getAmount();
                    }
                    item.setAmount(item.getAmount() - onlineDiscount);
                    item.setDiscountValue(finalDiscount + onlineDiscount);
                    //review Inventory
//		        reviewInventory(item,false);

                    //saveOrUpdate purchase item
                    if (paid) {
                        purchaseItemService.saveOrUpdate(item);
                    }
                    //review prepaid
//		        reviewPrepaid(orderItemVO, item.getId(), true,null);

                    //cal staff commission(用预付付款时，提成的计算所用的item amount与预付付款的amount无关)
                    if (paid) {
                        calAppsStaffCommission(bi, item, purchaseOrder.getPurchaseDate(), true, purchaseOrder.getCompany().getId());
                    }

                    totalDiscount += finalDiscount;
                    totalAmount += item.getAmount();
                }
            }
        }
        if (!paid) {
            purchaseOrder.setTotalAmount(shopAmt);
            purchaseOrder.setTotalDiscount(onlineAmt);
        } else {
            purchaseOrder.setTotalAmount(totalAmount);
            purchaseOrder.setTotalDiscount(totalDiscount);
        }
    }

    private void calAppsStaffCommission(BookItem bi, PurchaseItem item, Date purchaseDate, Boolean isSave, Long companyId) {

        logger.debug("check out calStaffCommission::::::purchaseDate{},isSave{}:" + purchaseDate + "," + isSave);
        if (bi.getProductOption() == null) {
            return;
        }
        Boolean foldCommission = Boolean.FALSE;
//		if(StringUtils.isNotBlank(orderItemVO.getPaidByVoucherRef())){
//        	Prepaid prepaid=prepaidService.get("reference", orderItemVO.getPaidByVoucherRef());
//        	if(prepaid !=null && (prepaid.getIsFree() !=null && prepaid.getIsFree())){
//        		foldCommission=Boolean.TRUE;
//        	}
//        }
        ProductOption po = bi.getProductOption();

        List<User> therapists = bi.getTherapistList();
        if (therapists == null) {
            return;
        }

        Set<StaffCommission> staffCommissions = null;
        if (isSave) {
            staffCommissions = item.getStaffCommissions();
            item.getStaffCommissions().clear();
        }
        //saveOrUpdate staff commission
        int therapistSize = therapists.size();
        //
        Double effectiveValue = item.getEffectiveValue();
        if (item.getNetAmount() > 0) {
            effectiveValue = item.getNetAmount();
        }
        effectiveValue = effectiveValue / therapistSize;

        // cal commission rate by total amount
        Category topestCategory = po.getProduct().getCategory().getTheTopestCategoryUnderRoot();

        StaffCommission sc = null;
        int i = 0;
        for (User staff : therapists) {
            //calculate commission
            CommissionAdapter commissionAdapter = new CommissionAdapter();
            commissionAdapter.setCategory(po.getProduct().getCategory());
            commissionAdapter.setProduct(po.getProduct());
            commissionAdapter.setEffectiveValue(new BigDecimal(effectiveValue.doubleValue()));
            commissionAdapter.setStaffId(staff.getId());

            CommissionCalculationPlugin.process(commissionAdapter);

            double commissionRate = commissionAdapter.getCommissionRate().doubleValue();
            double commission = commissionAdapter.getCommission().doubleValue();

            double extraCommissionRate = commissionAdapter.getExtraCommission().doubleValue();
            double extraCommission = commissionAdapter.getExtraCommission().doubleValue();

            double targetCommissionRate = commissionAdapter.getTargetCommissionRate().doubleValue();
            double targetCommission = commissionAdapter.getTargetCommission().doubleValue();

            double targetExtraCommissionRate = commissionAdapter.getTargetExtraCommissionRate().doubleValue();
            double targetExtraCommission = commissionAdapter.getTargetExtraCommission().doubleValue();

            boolean hitTarget = false;
            // product/treatment check bonus
            if (topestCategory.getReference().equals(CommonConstant.CATEGORY_REF_GOODS) || topestCategory.getReference().equals(CommonConstant.CATEGORY_REF_TREATMENT)) {
                hitTarget = checkWhetherHas2ndTierTarget(staff, purchaseDate, effectiveValue, isSave, companyId, false, topestCategory.getReference(),
                        targetCommissionRate, targetCommission, targetExtraCommissionRate, targetExtraCommission);
            }
            //
            if (isSave) {
                sc = new StaffCommission();
                sc.setIsActive(true);
                sc.setIsRequested(false);
                sc.setCreated(item.getCreated());
                sc.setCreatedBy(item.getCreatedBy());
                sc.setDisplayOrder(i);

                sc.setPurchaseDate(purchaseDate);
                sc.setStaff(staff);
                sc.setPurchaseItem(item);
                i++;
                sc.setEffectiveValue(effectiveValue);
                sc.setAmount(effectiveValue);

                sc.setCommissionValue(commission);
                sc.setCommissionRate(commissionRate);
                if (hitTarget) {
                    sc.setTargetCommission(targetCommission);
                    sc.setTargetCommissionRate(targetCommissionRate);

                    sc.setTargetExtraCommission(targetExtraCommission);
                    sc.setTargetExtraCommissionRate(targetExtraCommissionRate);
                } else {
                    sc.setTargetCommission(0d);
                    sc.setTargetCommissionRate(0d);

                    sc.setTargetExtraCommission(0d);
                    sc.setTargetExtraCommissionRate(0d);
                }
                sc.setExtraCommission(extraCommission);
                sc.setExtraCommissionRate(extraCommissionRate);

                sc.setExtraCommission(extraCommission);
                sc.setExtraCommissionRate(extraCommissionRate);
                staffCommissions.add(sc);
            }
        }
    }

    @Override
    public void handelStatusAfterPaid(Long bookingId) {
        Book book = bookService.get(bookingId);
        PurchaseOrder po = book.getPurchaseOrders().iterator().next();

        po.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        po.getPayments().iterator().next().setStatus(CommonConstant.PAYMENT_STATUS_PAID);

        //purchase item save status
        for (PurchaseItem pi : po.getPurchaseItems()) {
            pi.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
            purchaseItemService.saveOrUpdate(pi);
        }
        //set loyalty points
        setLoyaltyPoints(CommonConstant.POINTS_EARN_CHANNEL_SALES, po, po.getTotalAmount());

        saveOrUpdate(po);
        //
        book.setStatus(CommonConstant.BOOK_STATUS_PAID);
        bookService.saveOrUpdate(book);

    }

    private void setAppsPayments(PurchaseOrder purchaseOrder) {

        Set<Payment> payments = purchaseOrder.getPayments();
        PaymentMethod pm = paymentMethodService.get("reference", "ONLINE");
        Payment payment = new Payment();
        payment.setCompany(purchaseOrder.getCompany());
        payment.setIsActive(true);
        payment.setCreated(purchaseOrder.getCreated());
        payment.setCreatedBy(purchaseOrder.getCreatedBy());
        payment.setPurchaseOrder(purchaseOrder);
        payment.setDisplayOrder(1);

        payment.setAmount(purchaseOrder.getTotalAmount());
        payment.setPaymentMethod(pm);

        if (purchaseOrder.getStatus().equals(CommonConstant.ORDER_STATUS_PENDING)) {
            payment.setStatus(CommonConstant.PAYMENT_STATUS_UNPAID);
        } else if (purchaseOrder.getStatus().equals(CommonConstant.ORDER_STATUS_COMPLETED)) {
            payment.setStatus(CommonConstant.PAYMENT_STATUS_PAID);
        }

        payments.add(payment);

        saveOrUpdate(purchaseOrder);
    }

    @Override
    public PurchaseOrder saveAppsPurchaseOrder2(Long bookingId) {
        logger.debug("check out saveAppsPurchaseOrder2---------");
        Company company = new Company();
        company.setId(1L);
        /*		String loginUser=WebThreadLocal.getUser().getUsername();*/
        Date now = new Date();

        Book book = bookService.get(bookingId);

        Date purchaseDate = DateUtil.getFirstMinuts(book.getAppointmentTime());
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCompany(company);
        purchaseOrder.setIsActive(true);
        purchaseOrder.setCreated(now);
        purchaseOrder.setCreatedBy(book.getUser().getUsername());
        //by paypal set order's status is pending and set payment is unpaid.
        purchaseOrder.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        purchaseOrder.setRemarks(book.getRemarks());

        purchaseOrder.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PURCHASE_ORDER_REF_PREFIX));
        purchaseOrder.setPurchaseDate(purchaseDate);
        purchaseOrder.setShowRemarksInInvoice(false);
        purchaseOrder.setIsHotelGuest(false);

        if (book.getUser() != null) {
            purchaseOrder.setUser(book.getUser());
        }
        if (book.getShop() != null) {
            purchaseOrder.setShop(book.getShop());
        }

        purchaseOrder.setBook(book);

        //cal tax amount
        purchaseOrder.setTaxAmount(0d);

        purchaseOrder.setTotalDiscount(0d);
        saveOrUpdate(purchaseOrder);
        //set items
        setAppsPurchaseItems(book, purchaseOrder, true);
        //from apps discount
        //saveOrUpdate order
        saveOrUpdate(purchaseOrder);

//			//earn points and member upgrade/downgrade level plugin
//			reviewSpaPointsAndLoyaltyLevel(purchaseOrder,purchaseOrder.getTotalAmount(),CommonConstant.POINTS_EARN_CHANNEL_SALES,
//					CommonConstant.ACTION_ADD,null, purchaseOrder.getUser().isMember());
//
//			//set tips item
//			setTipsItems(orderVO, purchaseOrder);

        //set payments
        setAppsPayments(purchaseOrder);

        //set order survey
        setOrderSurvey(purchaseOrder);
        //set loyalty points
        setLoyaltyPoints(CommonConstant.POINTS_EARN_CHANNEL_SALES, purchaseOrder, purchaseOrder.getTotalAmount());

        saveOrUpdate(purchaseOrder);
        //
        book.setStatus(CommonConstant.BOOK_STATUS_PAID);

        bookService.saveOrUpdate(book);
        return purchaseOrder;
    }

    /* create by william -- 2018-9-14 */
    public void changeBooItemStatus(Long orderId, Boolean checkOutStatus) {

//		List<PurchaseItem> piList = purchaseItemService.getPurchaseItemByOrder(orderId);
//		List<BookItem> bookItemList = new ArrayList<>();
//		Book book = null;
//
//		if (checkOutStatus) {
//			for (PurchaseItem pi : piList) {
//				BookItem bi = bookItemService.get(Long.valueOf(pi.getBookItemId()));
//				bi.setStatus(CommonConstant.BOOK_STATUS_NOT_SHOW);
//				bookItemService.saveOrUpdate(bi);
//
//				book = bookService.get(bi.getBook().getId());
//				bookItemList = bookItemService.getBookItemsByBookId(book.getId());
//			}
//
//			for (BookItem bi : bookItemList) {
//				if (CommonConstant.BOOK_STATUS_NOT_SHOW.equals(bi.getStatus())) {
//					book.setStatus(CommonConstant.BOOK_STATUS_NOT_SHOW);
//					bookService.saveOrUpdate(book);
//				}
//			}
//		}

    }
}
