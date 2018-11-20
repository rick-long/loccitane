package com.spa.controller.report;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jxls.common.Context;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.utils.DateUtil;
import org.spa.utils.PDFUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.ServletUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.page.Page;
import org.spa.vo.report.CustomerReportVO;
import org.spa.vo.report.DailyReportVO;
import org.spa.vo.report.PrepaidAnalysisVO;
import org.spa.vo.report.SalesDetailsSummaryVO;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.report.SalesTemplateVO;
import org.spa.vo.report.SummaryVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.jxlsBean.ExcelUtil;

/**
 * Created by Ivy on 2017/06/21.
 */
@Controller
@RequestMapping("report")
public class ReportController extends BaseController {

    public static final String PRINT_CUSTOMER_URL = "/report/customerReportTemplate";
    public static final String PRINT_COMPLETE_URL = "/report/completeReportTemplate";


    @RequestMapping("toSalesDetails")
    public String toSalesDetails(Model model) {
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true));

        DetachedCriteria dcPM = DetachedCriteria.forClass(PaymentMethod.class);
        model.addAttribute("paymentMethodList", paymentMethodService.getActiveListByRefAndCompany(dcPM, null, WebThreadLocal.getCompany().getId()));

        model.addAttribute("fromDate", new Date());

        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("salesSearchVO", salesSearchVO);
        return "report/salesDetails";
    }

    @RequestMapping("listSalesDetails")
    public String listSalesDetails(Model model, SalesSearchVO salesSearchVO) {

        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        Long rootCategoryId = salesSearchVO.getRootCategoryId();
        if (rootCategoryId != null && rootCategoryId != 1) {
            Category rootCategory = categoryService.get(rootCategoryId);
            if (rootCategoryId != null && rootCategoryId == 1000000) {
                salesSearchVO.setProdType(CommonConstant.CATEGORY_REF_PREPAID);
            } else {
                salesSearchVO.setProdType(rootCategory.getReference());
            }
        }
        //purchase item details
        Page<PurchaseItem> itemPage = purchaseItemService.getPurchaseItemIdOfSalesDetailsByPage(salesSearchVO);
        model.addAttribute("page", itemPage);
        // version1 end
        List<PurchaseItem> totalPurchaseItems = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
        List<SalesDetailsSummaryVO> summaryList = purchaseItemService.getSalesDetailsSummaryVOListV2(totalPurchaseItems, salesSearchVO.getStaffId());
        model.addAttribute("summaryList", summaryList);

        Double totalCommission = 0d;
        Double grossRevenue = 0d;
        Double totalRevenue = 0d;
        Map<String, Double> paymentAmount = new HashMap<String, Double>();
        Map<String, Object> returnMap = purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);
        if (returnMap != null && returnMap.size() > 0) {
            totalCommission = (Double) returnMap.get("totalCommission");
            grossRevenue = (Double) returnMap.get("grossRevenue");
            totalRevenue = (Double) returnMap.get("totalRevenue");
            paymentAmount = (Map<String, Double>) returnMap.get("paymentAmount");
        }
        model.addAttribute("totalCommission", totalCommission);
        model.addAttribute("grossRevenue", grossRevenue);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("paymentAmount", paymentAmount);

        Boolean showCommission = true;
        if (WebThreadLocal.getUser().getFirstRoleForStaff().equals("HOTEL_MANAGER")) {
            showCommission = false;
        }
        model.addAttribute("showCommission", showCommission);
        salesSearchVO.setLowestCategoriesByCategoryIds(null);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(salesSearchVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "report/salesDetailsList";
    }

    @RequestMapping("toSalesDetailsForStaff")
    public String toSalesDetailsForStaff(Model model) {
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true));

        DetachedCriteria dcPM = DetachedCriteria.forClass(PaymentMethod.class);
        model.addAttribute("paymentMethodList", paymentMethodService.getActiveListByRefAndCompany(dcPM, null, WebThreadLocal.getCompany().getId()));

        model.addAttribute("fromDate", new Date());

        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("salesSearchVO", salesSearchVO);
        return "report/salesDetailsForStaff";
    }

    @RequestMapping("listSalesDetailsForStaff")
    public String listSalesDetailsForStaff(Model model, SalesSearchVO salesSearchVO) {

        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        Long rootCategoryId = salesSearchVO.getRootCategoryId();
        if (rootCategoryId != null && rootCategoryId != 1) {
            Category rootCategory = categoryService.get(rootCategoryId);
            if (rootCategoryId != null && rootCategoryId == 1000000) {
                salesSearchVO.setProdType(CommonConstant.CATEGORY_REF_PREPAID);
            } else {
                salesSearchVO.setProdType(rootCategory.getReference());
            }
        }
        //purchase item details
        Page<PurchaseItem> itemPage = purchaseItemService.getPurchaseItemIdOfSalesDetailsByPage(salesSearchVO);
        model.addAttribute("page", itemPage);

        List<PurchaseItem> totalPurchaseItems = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
        List<SalesDetailsSummaryVO> summaryList = purchaseItemService.getSalesDetailsSummaryVOListV2(totalPurchaseItems, salesSearchVO.getStaffId());
        model.addAttribute("summaryList", summaryList);

        Double totalCommission = 0d;
        Double grossRevenue = 0d;
        Double totalRevenue = 0d;
        Map<String, Double> paymentAmount = new HashMap<String, Double>();
        Map<String, Object> returnMap = purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);
        if (returnMap != null && returnMap.size() > 0) {
            totalCommission = (Double) returnMap.get("totalCommission");
            grossRevenue = (Double) returnMap.get("grossRevenue");
            totalRevenue = (Double) returnMap.get("totalRevenue");
            paymentAmount = (Map<String, Double>) returnMap.get("paymentAmount");
        }
        model.addAttribute("totalCommission", totalCommission);
        model.addAttribute("grossRevenue", grossRevenue);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("paymentAmount", paymentAmount);

        Boolean showCommission = true;
        if (WebThreadLocal.getUser().getFirstRoleForStaff().equals("HOTEL_MANAGER")) {
            showCommission = false;
        }
        model.addAttribute("showCommission", showCommission);
        salesSearchVO.setLowestCategoriesByCategoryIds(null);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(salesSearchVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "report/salesDetailsListForStaff";
    }

    @RequestMapping("salesDetailsExport")
    public void salesDetailsExport(SalesSearchVO salesSearchVO, HttpServletResponse response) throws ParseException {

        System.out.println(" export begin time ---" + new Date());
        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        Long rootCategoryId = salesSearchVO.getRootCategoryId();
        if (rootCategoryId != null && rootCategoryId != 1) {
            Category rootCategory = categoryService.get(rootCategoryId);
            if (rootCategoryId != null && rootCategoryId == 1000000) {
                salesSearchVO.setProdType(CommonConstant.CATEGORY_REF_PREPAID);
            } else {
                salesSearchVO.setProdType(rootCategory.getReference());
            }
        }
        //purchase item details
        List<PurchaseItem> items = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
        List<SalesTemplateVO> voList = new ArrayList<SalesTemplateVO>();
        SalesTemplateVO vo = null;
//		 System.out.println(" export items size ---"+items.size()+"---"+new Date());

        PurchaseOrder po = null;
        ProductOption pro = null;
//		int i=1;
        for (PurchaseItem item : items) {
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
            vo.setFullPrice(pro != null ? pro.getOriginalPrice() : 0d);
            vo.setCostOfProduct(pro != null ? pro.getCostOfProduct() : 0d);
            vo.setPayment(item.getPurchaseOrder().getPaymentMethodsAndAmount2());

            vo.setRequested(item.getIsRequested() ? "Y" : "N");
//        	System.out.println("---i----"+i+"---"+new Date());
            voList.add(vo);
//        	i++;
        }
//        System.out.println(" export volist size ---"+voList.size()+"---"+new Date());
        Context context = new Context();
        context.putVar("volist", voList);

        File downloadFile = ExcelUtil.write("salesExportTemplate.xls", context);

        ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Sales-Details-Report-") + ".xls", response);
        System.out.println(" export end time ---" + new Date());
    }

    @RequestMapping("salesDetailsForStaffExport")
    public void salesDetailsForStaffExport(SalesSearchVO salesSearchVO, HttpServletResponse response) throws ParseException {
        Context context = new Context();
        if(StringUtils.isNotBlank(salesSearchVO.getFullName())){
            context.putVar("fullName","FullName: "+salesSearchVO.getFullName());
        }else{
            context.putVar("fullName","FullName: ");
        }
        if(salesSearchVO.getShopId() != null){
            Shop shop = shopService.get(salesSearchVO.getShopId());
            context.putVar("shopName","ShopName: "+shop.getName());
        }else{
            context.putVar("shopName","ShopName: ");
        }
        context.putVar("from","From: "+salesSearchVO.getFromDate());
        context.putVar("to","To: "+salesSearchVO.getToDate());
        if(salesSearchVO.getPaymentMethodId() != null && salesSearchVO.getPaymentMethodId() > 0){
            PaymentMethod paymentMethod = paymentMethodService.get(salesSearchVO.getPaymentMethodId());
            context.putVar("paymentMethod","PaymentMethod: "+paymentMethod.getName());
        }else{
            context.putVar("paymentMethod","PaymentMethod: ALL");
        }
        if(salesSearchVO.getRootCategoryId() != null && salesSearchVO.getRootCategoryId() > 1){
            Category category = categoryService.get(salesSearchVO.getRootCategoryId());
            context.putVar("productType","ProductType: "+category.getName());
        }else {
            context.putVar("productType","ProductType: ALL");
        }
        if(salesSearchVO.getCategoryId() != null && salesSearchVO.getCategoryId() > 0){
            Category category = categoryService.get(salesSearchVO.getCategoryId());
            context.putVar("category","CategoryType: "+category.getName());
        }else {
            context.putVar("category","CategoryType: ALL");
        }
        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        Long rootCategoryId = salesSearchVO.getRootCategoryId();
        if (rootCategoryId != null && rootCategoryId != 1) {
            Category rootCategory = categoryService.get(rootCategoryId);
            if (rootCategoryId != null && rootCategoryId == 1000000) {
                salesSearchVO.setProdType(CommonConstant.CATEGORY_REF_PREPAID);
            } else {
                salesSearchVO.setProdType(rootCategory.getReference());
            }
        }
        //purchase item details
        List<PurchaseItem> items = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
        List<SalesTemplateVO> voList = new ArrayList<SalesTemplateVO>();
        SalesTemplateVO vo = null;
        PurchaseOrder po = null;
//		ProductOption pro =null;
        for (PurchaseItem item : items) {
            po = item.getPurchaseOrder();
//        	pro = item.getProductOption();
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
            //vo.setFullPrice(pro !=null ? pro.getOriginalPrice() : 0d);
//       		vo.setCostOfProduct(pro !=null ? pro.getCostOfProduct() :0d);
            vo.setPayment(item.getPurchaseOrder().getPaymentMethodsAndAmount2());
            vo.setRequested(item.getIsRequested() ? "Y" : "N");
            voList.add(vo);
        }

        context.putVar("volist", voList);

        File downloadFile = ExcelUtil.write("salesExportForStaffTemplate.xls", context);

        ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Sales-Details-Report-") + ".xls", response);
    }

    @RequestMapping("toCustomer")
    public String toCustomer(Model model) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true));
        DetachedCriteria dcPM = DetachedCriteria.forClass(PaymentMethod.class);
        model.addAttribute("paymentMethodList", paymentMethodService.getActiveListByRefAndCompany(dcPM, null, WebThreadLocal.getCompany().getId()));
        model.addAttribute("fromDate", new Date());
        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        subCategoryList.remove(categoryService.get("reference", "CA-TIPS"));
        model.addAttribute("subCategoryList", subCategoryList);
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        model.addAttribute("salesSearchVO", salesSearchVO);
        return "report/customerReport";
    }

    @RequestMapping("printCustomer")
    public void printCustomer(SalesSearchVO salesSearchVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        Map<String, Object> map = new HashMap<>();

        map.put("username", salesSearchVO.getUsername());

        map.put("fromDate", StringUtils.replace(salesSearchVO.getFromDate(), "-", ""));
        map.put("toDate", StringUtils.replace(salesSearchVO.getToDate(), "-", ""));

        map.put("shopId", salesSearchVO.getShopId());
        map.put("paymentMethodId", salesSearchVO.getPaymentMethodId());
        map.put("staffId", salesSearchVO.getStaffId());
        map.put("rootCategoryId", salesSearchVO.getRootCategoryId());
        map.put("prodType", salesSearchVO.getProdType());
        map.put("categoryId", salesSearchVO.getCategoryId());
        map.put("productId", salesSearchVO.getProductId());
        map.put("productOptionId", salesSearchVO.getProductOptionId());
        /*map.put("isLanlordReport", salesSearchVO.getIsLanlordReport());*/

        String downloadFileName = RandomUtil.generateRandomNumberWithDate("Customer-Report-") + ".pdf";
        try {

            File downloadFile = PDFUtil.convert(PRINT_CUSTOMER_URL, request, map);
            ServletUtil.download(downloadFile, downloadFileName, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("customerReportTemplate")
    public String customerReportTemplate(SalesSearchVO salesSearchVO, Model model) {
        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        if (salesSearchVO.getRootCategoryId() != null && salesSearchVO.getRootCategoryId() != 1) {
            Category rootCategory = categoryService.get(salesSearchVO.getRootCategoryId());
            salesSearchVO.setProdType(rootCategory.getReference());
        }
        model.addAttribute("fromDate", salesSearchVO.getFromDate());
        model.addAttribute("toDate", salesSearchVO.getToDate());

        if (salesSearchVO.getCategoryId() != null) {
            List<Long> lowestCategories = new ArrayList<>();
            //获取分类的所有子分类
            categoryService.getLowestCategoriesByCategory(salesSearchVO.getCategoryId(), lowestCategories);
            lowestCategories.add(salesSearchVO.getCategoryId());
            Long[] lowestCategoriesByCategoryIds = lowestCategories.toArray(new Long[lowestCategories.size()]);
            salesSearchVO.setLowestCategoriesByCategoryIds(lowestCategoriesByCategoryIds);
        }

        Double noPayment = 0d;
        Map<String, Object> returnMap = purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);

        Double totalCommission = (Double) returnMap.get("totalCommission");
        Double grossRevenue = (Double) returnMap.get("grossRevenue");
        Double totalRevenue = (Double) returnMap.get("totalRevenue");
        Double totalExtraCommission = (Double) returnMap.get("totalExtraCommission");
        Double totalTargetExtraCommission = (Double) returnMap.get("totalTargetExtraCommission");
        Double totalTargetCommission = (Double) returnMap.get("totalTargetCommission");
        Map<String, Double> paymentAmount = (Map<String, Double>) returnMap.get("paymentAmount");

        model.addAttribute("grossRevenue", grossRevenue);
        model.addAttribute("totalRevenue", totalRevenue);
        if(totalTargetCommission == null || totalTargetCommission == 0d){
            model.addAttribute("totalCommission", totalCommission);
        }else{
            model.addAttribute("totalCommission", totalTargetCommission);
        }
        if(totalTargetCommission == null || totalTargetCommission == 0d){
            model.addAttribute("totalExtraCommission", totalExtraCommission);
        }else{
            model.addAttribute("totalExtraCommission", totalTargetExtraCommission);
        }

        if (paymentAmount != null && paymentAmount.size() > 0) {
            Set<String> strings = paymentAmount.keySet();
            for (String s : strings) {
                if ("No Payment".equals(s)) {
                    noPayment = paymentAmount.get(s);
                    break;
                }
            }
        }
        model.addAttribute("noPayment", noPayment);

        List<CustomerReportVO> totalSalesByDate = purchaseItemService.getTotalSalesByDate(salesSearchVO);
        model.addAttribute("totalSalesByDate", totalSalesByDate);

        String oldProdType = salesSearchVO.getProdType();
        Map<String, List<CustomerReportVO>> commissionAnalysis = purchaseItemService.getCommissionAnalysisByProdType(salesSearchVO);

        salesSearchVO.setProdType(oldProdType);
        Map<String, List<CustomerReportVO>> salesAnalysis = purchaseItemService.getSalesAnalysisByProdType(salesSearchVO);

        model.addAttribute("commissionAnalysis", commissionAnalysis);
        model.addAttribute("salesAnalysis", salesAnalysis);

        //sales summary by shop start
        SalesSearchVO salesSearchVO2 = new SalesSearchVO();
        salesSearchVO2.setFromDate(salesSearchVO.getFromDate());
        salesSearchVO2.setToDate(salesSearchVO.getToDate());
        salesSearchVO2.setRootCategoryId(salesSearchVO.getRootCategoryId());
        salesSearchVO2.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);

        List<Shop> shops = shopService.getListByCompany(1l, false, false, false);
        Map<String, SummaryVO> results = new HashMap<String, SummaryVO>();

        for (Shop s : shops) {
            if ("HEAD_OFFICE".equals(s.getReference())) {
                continue;
            }
            if ("DISCOVERY_BAY".equals(s.getReference())) {
                continue;
            }
            salesSearchVO2.setShopId(s.getId());
            List<PurchaseItem> totalPurchaseItems = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO2);
            SummaryVO vo = new SummaryVO();
            vo.setPackagesRevenue(0d);
            vo.setProductRevenue(0d);
            vo.setTreatmentRevenue(0d);

            if (totalPurchaseItems == null || totalPurchaseItems.size() == 0) {
                results.put(s.getName(), vo);
                continue;
            }
            //Map map =new HashMap();
            String pType = "";
            for (PurchaseItem purchaseItem : totalPurchaseItems) {
                if (purchaseItem.getBuyPrepaidTopUpTransaction() != null) {
                    if (purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE) || purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)) {
                        pType = "Packages";
                    } else if (purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER)
                            || purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)) {
                        continue;
                    }
                } else {
                    pType = purchaseItem.getProductOption().getProduct().getProdType();
                }

                double amount = purchaseItem.getAmount();

                if (pType.equals("Packages")) {
                    vo.setPackagesRevenue(new Double(vo.getPackagesRevenue().doubleValue() + amount));
                } else if (pType.equals(CommonConstant.CATEGORY_REF_GOODS)) {
                    vo.setProductRevenue(new Double(vo.getProductRevenue().doubleValue() + amount));
                } else if (pType.equals(CommonConstant.CATEGORY_REF_TREATMENT)) {
                    vo.setTreatmentRevenue(new Double(vo.getTreatmentRevenue().doubleValue() + amount));
                }
            }
            results.put(s.getName(), vo);
        }
        model.addAttribute("results", results);
        //sales summary by shop end
        List<Shop> locations = new ArrayList<Shop>();
        if (salesSearchVO.getShopId() != null) {
            Shop location = shopService.get(salesSearchVO.getShopId());
            locations.add(location);

        } else {
            locations.addAll(shopService.getAllShop(WebThreadLocal.getCompany().getId()));
        }
        model.addAttribute("locations", locations);
        return "report/customerReportTemplate";
    }

    @RequestMapping("salesSummaryByShopTemplate")
    public String salesSummaryByShopTemplate(Model model) {

        String fromDate = "";//开始时间
        String toDate = "";//结束时间
        Calendar fromCalendar = new GregorianCalendar();
        Date from = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        toDate = sdf.format(from);
        fromCalendar.setTime(from);
        fromCalendar.add(Calendar.DATE, -1);
        fromDate = sdf.format(fromCalendar.getTime());

        //sales summary by shop start
        SalesSearchVO salesSearchVO2 = new SalesSearchVO();
        salesSearchVO2.setFromDate(fromDate);
        salesSearchVO2.setToDate(toDate);
        salesSearchVO2.setRootCategoryId(1l);
        salesSearchVO2.setIsSearchByJob(true);
        salesSearchVO2.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);

        List<Shop> shops = shopService.getListByCompany(1l, true, false, false);
        Map<String, SummaryVO> results = new HashMap<String, SummaryVO>();

        for (Shop s : shops) {
            if ("HEAD_OFFICE".equals(s.getReference())) {
                continue;
            }
            if ("DISCOVERY_BAY".equals(s.getReference())) {
                continue;
            }
            salesSearchVO2.setShopId(s.getId());
            salesSearchVO2.setCompanyId(1l);
            Long numOfClients = purchaseOrderService.getCountMembersFromOrdersByFilters(salesSearchVO2);

            List<PurchaseItem> totalPurchaseItems = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO2);
            SummaryVO vo = new SummaryVO();
            vo.setPackagesRevenue(0d);
            vo.setProductRevenue(0d);
            vo.setTreatmentRevenue(0d);
            vo.setHairsalonRevenue(0d);
            vo.setVouchersRevenue(0d);
            vo.setNumOfClients(numOfClients.intValue());

            if (totalPurchaseItems == null || totalPurchaseItems.size() == 0) {
                results.put(s.getName(), vo);
                continue;
            }
            Map map = new HashMap();
            String pType = "";
            for (PurchaseItem purchaseItem : totalPurchaseItems) {

                if (purchaseItem.getBuyPrepaidTopUpTransaction() != null) {
                    if (purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE) || purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)) {
                        pType = "Packages";
                    } else if (purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER)
                            || purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)) {
                        pType = "Vouchers";
                    }
                } else {
                    pType = purchaseItem.getProductOption().getProduct().getProdType();
                }

                double amount = purchaseItem.getAmount();

                if (pType.equals("Packages")) {
                    vo.setPackagesRevenue(new Double(vo.getPackagesRevenue().doubleValue() + amount));
                } else if (pType.equals("Vouchers")) {
                    vo.setVouchersRevenue(new Double(vo.getVouchersRevenue().doubleValue() + amount));
                } else if (pType.equals(CommonConstant.CATEGORY_REF_HAIRSALON)) {
                    vo.setHairsalonRevenue(new Double(vo.getHairsalonRevenue().doubleValue() + amount));
                } else if (pType.equals(CommonConstant.CATEGORY_REF_GOODS)) {
                    vo.setProductRevenue(new Double(vo.getProductRevenue().doubleValue() + amount));
                } else if (pType.equals(CommonConstant.CATEGORY_REF_TREATMENT)) {

                    vo.setTreatmentRevenue(new Double(vo.getTreatmentRevenue().doubleValue() + amount));
//					System.out.println("amount" +amount +"--vo.getTreatmentRevenue()--"+vo.getTreatmentRevenue()+"--id---"+purchaseItem.getId());
                }
            }

            results.put(s.getName(), vo);
        }

        model.addAttribute("results", results);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        //sales summary by shop end
        return "report/salesSummaryByShopTemplate";
    }

    @RequestMapping("toPrepaidOutstanding")
    public String toPrepaidOutstanding(Model model) {
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true));
        model.addAttribute("fromDate", new Date());
        model.addAttribute("salesSearchVO", salesSearchVO);
        return "report/prepaidOutstanding";
    }

    @RequestMapping("listPrepaidOutstanding")
    public String listPrepaidOutstanding(Model model, SalesSearchVO salesSearchVO) {
        String finishDate = salesSearchVO.getFromDate();
        Long shopId = salesSearchVO.getShopId();
        Long companyId = null;
        if (WebThreadLocal.getCompany() != null) {
            companyId = WebThreadLocal.getCompany().getId();
        }
        Map returnMap = prepaidTopUpTransactionService.getPrepaidOutstandingMap(finishDate, shopId, companyId);
        List<PrepaidAnalysisVO> volist = (List<PrepaidAnalysisVO>) returnMap.get("prepaidAnalysisVOList");
        List<PrepaidAnalysisVO> prepaidOutstandingSummaryList = (List<PrepaidAnalysisVO>) returnMap.get("prepaidOutstandingSummaryList");
        model.addAttribute("prepaidOutstandingSummaryList", prepaidOutstandingSummaryList);
        model.addAttribute("prepaidAnalysisVOList", volist);
        return "report/prepaidOutstandingList";
    }

    @RequestMapping("prepaidOutstandingExport")
    public void prepaidOutstandingExport(Model model, SalesSearchVO salesSearchVO, HttpServletResponse response) {
        String finishDate = salesSearchVO.getFromDate();
        Long shopId = salesSearchVO.getShopId();
        Long companyId = null;
        if (WebThreadLocal.getCompany() != null) {
            companyId = WebThreadLocal.getCompany().getId();
        }
        Map returnMap = prepaidTopUpTransactionService.getPrepaidOutstandingMap(finishDate, shopId, companyId);
        List<PrepaidAnalysisVO> volist = (List<PrepaidAnalysisVO>) returnMap.get("prepaidAnalysisVOList");
        Context context = new Context();
        context.putVar("items", volist);

        File downloadFile = ExcelUtil.write("prepaidOutstandingExportTemplate.xls", context);

        ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("PrepaidOutstanding-Export-") + ".xls", response);

    }

    @RequestMapping("toComplete")
    public String toComplete(Model model, SalesSearchVO salesSearchVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true));
        DetachedCriteria dcPM = DetachedCriteria.forClass(PaymentMethod.class);
        model.addAttribute("paymentMethodList", paymentMethodService.getActiveListByRefAndCompany(dcPM, null, WebThreadLocal.getCompany().getId()));

        model.addAttribute("fromDate", new Date());

        List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("subCategoryList", subCategoryList);

        return "report/completeReport";
    }

    @RequestMapping("printComplete")
    public void printComplete(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("prepaidId", 1);
        String downloadFileName = RandomUtil.generateRandomNumberWithDate("V-") + ".pdf";
        try {
            File downloadFile = PDFUtil.convert(PRINT_COMPLETE_URL, request, map);
            ServletUtil.download(downloadFile, downloadFileName, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("completeReportTemplate")
    public String completeReportTemplate(Long prepaidId, String test, Model model) {
        Prepaid prepaid = prepaidService.get(prepaidId);

        model.addAttribute("prepaid", prepaid);

        return "report/completeReportTemplate";
    }

    @RequestMapping("customeReportTemplateForDayEndReport")
    public String customeReportTemplateForDayEndReport(Model model, Long shopId, Long rootCategoryId, Boolean isLanlordReport) {

        System.out.println("---customeReportTemplateForDayEndReport-----shopId----" + shopId + "---rootCategoryId---" + rootCategoryId + "---isLanlordReport---" + isLanlordReport);
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        salesSearchVO.setRootCategoryId(rootCategoryId);

        String fromDate = "";//开始时间
        String toDate = "";//结束时间
        Calendar fromCalendar = new GregorianCalendar();
        Date from = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        toDate = sdf.format(from);
        fromCalendar.setTime(from);
        fromCalendar.add(Calendar.DATE, -1);
        fromDate = sdf.format(fromCalendar.getTime());

        salesSearchVO.setFromDate(fromDate);
        salesSearchVO.setToDate(toDate);
        salesSearchVO.setIsSearchByJob(true);

        Shop shop = shopService.get(shopId);
        if (!shop.getReference().equals("HEAD_OFFICE")) {
            salesSearchVO.setShopId(shopId);
        }

        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);

        if (rootCategoryId != null && rootCategoryId != 1) {
            Category rootCategory = categoryService.get(rootCategoryId);
            salesSearchVO.setProdType(rootCategory.getReference());
        }

        model.addAttribute("fromDate", salesSearchVO.getFromDate());
        model.addAttribute("toDate", salesSearchVO.getToDate());

        if (salesSearchVO.getCategoryId() != null) {
            List<Long> lowestCategories = new ArrayList<>();
            categoryService.getLowestCategoriesByCategory(salesSearchVO.getCategoryId(), lowestCategories);
            lowestCategories.add(salesSearchVO.getCategoryId());
            Long[] lowestCategoriesByCategoryIds = lowestCategories.toArray(new Long[lowestCategories.size()]);
            salesSearchVO.setLowestCategoriesByCategoryIds(lowestCategoriesByCategoryIds);
        }

        Double totalCommission = 0d;
        Double grossRevenue = 0d;
        Double totalRevenue = 0d;
        Double noPayment = 0d;
        Map<String, Double> paymentAmount = new HashMap<String, Double>();
        Map<String, Object> returnMap = purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);
        if (returnMap != null && returnMap.size() > 0) {
            totalCommission = (Double) returnMap.get("totalCommission");
            grossRevenue = (Double) returnMap.get("grossRevenue");
            totalRevenue = (Double) returnMap.get("totalRevenue");
            paymentAmount = (Map<String, Double>) returnMap.get("paymentAmount");
        }
        model.addAttribute("totalCommission", totalCommission);
        model.addAttribute("grossRevenue", grossRevenue);
        model.addAttribute("totalRevenue", totalRevenue);
        if (paymentAmount != null && paymentAmount.size() > 0) {
            Iterator keyIt = paymentAmount.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = (String) keyIt.next();
                if ("No Payment".equals(key)) {
                    noPayment = paymentAmount.get(key);
                    break;
                }
            }
        }
        model.addAttribute("noPayment", noPayment);

        List<CustomerReportVO> totalSalesByDate = purchaseItemService.getTotalSalesByDate(salesSearchVO);
        model.addAttribute("totalSalesByDate", totalSalesByDate);

        String oldProdType = salesSearchVO.getProdType();
        Map<String, List<CustomerReportVO>> commissionAnalysis = null;

        if (!isLanlordReport) {
            commissionAnalysis = purchaseItemService.getCommissionAnalysisByProdType(salesSearchVO);
        }
        //
        salesSearchVO.setProdType(oldProdType);
        Map<String, List<CustomerReportVO>> salesAnalysis = purchaseItemService.getSalesAnalysisByProdType(salesSearchVO);

        model.addAttribute("commissionAnalysis", commissionAnalysis);
        model.addAttribute("salesAnalysis", salesAnalysis);

        List<Shop> locations = new ArrayList<Shop>();

        Shop location = shopService.get(shopId);
        locations.add(location);

        model.addAttribute("locations", locations);
        model.addAttribute("isLanlordReport", isLanlordReport);
        return "report/customerReportTemplate";
    }

    @RequestMapping("toDailyReportForShopManager")
    public String toDailyReportForShopManager(Model model) {
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        model.addAttribute("fromDate", new Date());
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true));
        model.addAttribute("salesSearchVO", salesSearchVO);
        return "report/dailyReportForShopManager";
    }

    @RequestMapping("listDailyReportForShopManager")
    public String listDailyReportForShopManager(Model model, SalesSearchVO salesSearchVO) {

        salesSearchVO.setToDate(salesSearchVO.getFromDate());
        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        salesSearchVO.setCompanyId(WebThreadLocal.getCompany().getId());
        salesSearchVO.setRootCategoryId(1l);
        // daily total sales
        Double totalRevenue = 0d;
        Map<String, Double> paymentAmount = new HashMap<String, Double>();
        Map<String, Object> returnMap = purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);
        if (returnMap != null && returnMap.size() > 0) {
            paymentAmount = (Map<String, Double>) returnMap.get("paymentAmount");
            totalRevenue = (Double) returnMap.get("totalRevenue");
        }

        DetachedCriteria dcPM = DetachedCriteria.forClass(PaymentMethod.class);
        dcPM.add(Restrictions.eq("isActive", true));
        dcPM.addOrder(Order.desc("displayOrder"));
        List<PaymentMethod> paymentMethodList = paymentMethodService.list(dcPM);
//		Map<String,Double> imcomeMap=new HashMap<String,Double>();
//		Map<String,Double> prepaidMap=new HashMap<String,Double>();
        Double totalSales = 0d;
        Double sumIncome = 0d;
        Double sumPrepaid = 0d;
        Double sumOtherPrepaid = 0d;
        Double cash = 0d;
        Double ae = 0d;
        Double visa = 0d;
        Double uniompay = 0d;
        Double eps = 0d;

        Double packages = 0d;
        Double voucher = 0d;
        Double hotels = 0d;
        Double wings = 0d;
        for (PaymentMethod pm : paymentMethodList) {
            Double amount = paymentAmount.get(pm.getName());
            if (amount == null) {
                amount = 0d;
            }

            totalSales += amount;
            if (pm.getReference().equals("CASH") || pm.getReference().equals("CREDIT_CARD") || pm.getReference().equals("EPS") || pm.getReference().equals("AE") || pm.getReference().equals("UNIONPAY")) {
//				imcomeMap.put(pm.getName(), amount);
                if (pm.getReference().equals("CASH")) {
                    cash += amount;
                } else if (pm.getReference().equals("CREDIT_CARD")) {
                    visa += amount;
                } else if (pm.getReference().equals("EPS")) {
                    eps += amount;
                } else if (pm.getReference().equals("AE")) {
                    ae += amount;
                } else if (pm.getReference().equals("UNIONPAY")) {
                    uniompay += amount;
                }
                sumIncome += amount;
            }
            if (pm.getReference().equals("PACKAGE") || pm.getReference().equals("VOUCHER")) {

                if (pm.getReference().equals("PACKAGE")) {
                    packages += amount;
                } else if (pm.getReference().equals("VOUCHER")) {
                    voucher += amount;
                }
                sumPrepaid += amount;
            }
            if (pm.getReference().equals("WINGS_II_GUEST") || pm.getReference().equals("HOTEL_GUEST")) {

                if (pm.getReference().equals("WINGS_II_GUEST")) {
                    wings += amount;
                } else if (pm.getReference().equals("HOTEL_GUEST")) {
                    hotels += amount;
                }
                sumOtherPrepaid += amount;
            }
        }
//		model.addAttribute("imcomeMap", imcomeMap);
//		model.addAttribute("prepaidMap", prepaidMap);
        model.addAttribute("sumPrepaid", sumPrepaid);
        model.addAttribute("sumOtherPrepaid", sumOtherPrepaid);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalRevenue", totalRevenue);

        model.addAttribute("cash", cash);
        model.addAttribute("visa", visa);
        model.addAttribute("eps", eps);
        model.addAttribute("ae", ae);
        model.addAttribute("uniompay", uniompay);

        model.addAttribute("packages", packages);
        model.addAttribute("voucher", voucher);
        model.addAttribute("wings", wings);
        model.addAttribute("hotels", hotels);

        model.addAttribute("others", totalSales.doubleValue() - sumIncome.doubleValue() - sumPrepaid.doubleValue() - sumOtherPrepaid.doubleValue());
        model.addAttribute("sumIncome", totalSales.doubleValue() - sumPrepaid.doubleValue() - sumOtherPrepaid.doubleValue());
        //daily breakdown
        List<PurchaseItem> totalPurchaseItems = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
        List<SalesDetailsSummaryVO> summaryList = purchaseItemService.getSalesDetailsSummaryVOListV3(totalPurchaseItems);
        model.addAttribute("summaryList", summaryList);

        //number of client

        Long numOfClients = purchaseOrderService.getCountMembersFromOrdersByFilters(salesSearchVO);
        model.addAttribute("numOfClients", numOfClients);

        // therapist breakdown
        Map<String, List<SalesDetailsSummaryVO>> staffSummary = new HashMap<String, List<SalesDetailsSummaryVO>>();
        Map<Long, List<PurchaseItem>> staffMap = purchaseItemService.getStaffDetailsSummaryVOList(totalPurchaseItems);
        if (staffMap != null && staffMap.size() > 0) {
            for (Map.Entry<Long, List<PurchaseItem>> entry : staffMap.entrySet()) {
                Long staffId = entry.getKey();
                List<SalesDetailsSummaryVO> list = purchaseItemService.getSalesDetailsSummaryVOListV4(entry.getValue());
                staffSummary.put(userService.get(staffId).getDisplayName(), list);
            }
        }
        model.addAttribute("staffSummary", staffSummary);

        return "report/dailyReportForShopManagerList";
    }

    @RequestMapping("toDailyReportForSOTManager")
    public String toDailyReportForSOTManager(Model model, SalesSearchVO salesSearchVO) {
        model.addAttribute("fromDate", new Date());
//	    model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true,true));
        return "report/dailyReportForSOTManager";
    }

    @RequestMapping("listDailyReportForSOTManager")
    public String listDailyReportForSOTManager(Model model, SalesSearchVO salesSearchVO) {

        salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);
        salesSearchVO.setCompanyId(WebThreadLocal.getCompany().getId());
        salesSearchVO.setRootCategoryId(1l);

        List<Shop> shopList = shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true);
        //daily breakdown
        List<DailyReportVO> list = new ArrayList<DailyReportVO>();
        List<SalesDetailsSummaryVO> summaryList = new ArrayList<SalesDetailsSummaryVO>();
        for (Shop s : shopList) {
            DailyReportVO vo = new DailyReportVO();
            vo.setShopName(s.getName());
            vo.setShopId(s.getId());

            salesSearchVO.setShopId(s.getId());

            Double grossRevenue = 0d;
            Double totalRevenue = 0d;
            Map<String, Object> returnMap = purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);
            if (returnMap != null && returnMap.size() > 0) {
                grossRevenue = (Double) returnMap.get("grossRevenue");
                totalRevenue = (Double) returnMap.get("totalRevenue");
            }
            vo.setSales(totalRevenue);
            vo.setRevenue(grossRevenue);

            List<PurchaseItem> totalPurchaseItems = purchaseItemService.getPurchaseItemIdOfSalesDetails(salesSearchVO);
            summaryList = purchaseItemService.getSalesDetailsSummaryVOListV3(totalPurchaseItems);

            for (SalesDetailsSummaryVO vos : summaryList) {
                if (vos.getProdType().equals(CommonConstant.CATEGORY_REF_TREATMENT)) {
                    vo.setTreatmentSummaryVo(vos);
                } else if (vos.getProdType().equals(CommonConstant.CATEGORY_REF_PREPAID) && (vos.getCategoryName().equals("Cash Package") || vos.getCategoryName().equals("Treatment Package"))) {
                    if (vo.getPackageSummaryVo() != null) {
                        SalesDetailsSummaryVO v = vo.getPackageSummaryVo();
                        v.setUnit(v.getUnit() + vos.getUnit());
                        v.setAmount(v.getAmount() + vos.getAmount());
                        vo.setPackageSummaryVo(v);
                    } else {
                        vo.setPackageSummaryVo(vos);
                    }

                } else if (vos.getProdType().equals(CommonConstant.CATEGORY_REF_PREPAID) && (vos.getCategoryName().equals("Cash Voucher") || vos.getCategoryName().equals("Treatment Voucher"))) {
                    if (vo.getVoucherSummaryVo() != null) {
                        SalesDetailsSummaryVO v = vo.getVoucherSummaryVo();
                        v.setUnit(v.getUnit() + vos.getUnit());
                        v.setAmount(v.getAmount() + vos.getAmount());
                        vo.setVoucherSummaryVo(v);
                    } else {
                        vo.setVoucherSummaryVo(vos);
                    }
                }
            }
            list.add(vo);
        }
        model.addAttribute("list", list);

        return "report/dailyReportForSOTManagerList";
    }


    @RequestMapping("revenueByShopReportTemplate")
    public String revenueByShopReportTemplate(Model model) {

        Map<String, Double> revenuesMap = new HashMap<String, Double>();

        String fromDate = "";//开始时间
        String toDate = "";//结束时间
        Calendar fromCalendar = new GregorianCalendar();
        Date from = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        toDate = sdf.format(from);
        fromCalendar.setTime(from);
        fromCalendar.add(Calendar.DATE, -1);
        fromDate = sdf.format(fromCalendar.getTime());

        List<Shop> shopList = shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, false);
        for (Shop s : shopList) {
            SalesSearchVO salesSearchVO = new SalesSearchVO();
            salesSearchVO.setRootCategoryId(1L);
            salesSearchVO.setIsSearchByJob(true);
            salesSearchVO.setFromDate(fromDate);
            salesSearchVO.setToDate(toDate);

            salesSearchVO.setShopId(s.getId());
            salesSearchVO.setStatus(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED);

            Double totalRevenue = 0d;
            Map<String, Object> returnMap = purchaseOrderService.getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(salesSearchVO);
            if (returnMap != null && returnMap.size() > 0) {
                totalRevenue = (Double) returnMap.get("totalRevenue");
            }
            revenuesMap.put(s.getName(), totalRevenue);
        }
        model.addAttribute("revenuesMap", revenuesMap);
        return "report/revenueByShopReportTemplate";
    }

}
