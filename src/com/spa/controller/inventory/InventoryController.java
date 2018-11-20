package com.spa.controller.inventory;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.jxlsBean.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jxls.common.Context;
import org.spa.model.company.Company;
import org.spa.model.inventory.Inventory;
import org.spa.model.inventory.InventoryPurchaseOrder;
import org.spa.model.inventory.InventoryTransaction;
import org.spa.model.inventory.InventoryWarehouse;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.product.Supplier;
import org.spa.model.shop.Shop;
import org.spa.utils.*;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.inventory.*;
import org.spa.vo.page.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ivy on 2016/03/28.
 */
@Controller
@RequestMapping("inventory")
public class InventoryController extends BaseController {

    public static final String PRINT_INVENTORY_PURCHASE_ORDER_URL = "/inventory/toPurchaseOrderTemplate";

    // inventory Management ------------------------------------------------------------------------

    @RequestMapping("toView")
    public String management(Model model) {
        InventoryListVO inventoryListVO = new InventoryListVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));

        //brand list
        model.addAttribute("brandList", brandService.getBrandsSortBy(WebThreadLocal.getCompany().getId(), true, "name"));
        model.addAttribute("inventoryListVO",inventoryListVO);
        return "inventory/inventoryManagement";
    }

    @RequestMapping("list")
    public String list(Model model, InventoryListVO inventoryListVO) {
        Long shopId = inventoryListVO.getShopId();
        String productName = inventoryListVO.getProductName();
        int qty = inventoryListVO.getQty();
        String isActive = inventoryListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inventory.class);
        detachedCriteria.createAlias("inventoryWarehouses", "iw");

        Long brandId = inventoryListVO.getBrandId();
        Long categoryId = inventoryListVO.getCategoryId();


        if((brandId !=null && brandId.longValue()>0) || (StringUtils.isNoneBlank(productName))|| categoryId != null){
            detachedCriteria.createAlias("productOption", "po");
            detachedCriteria.createAlias("po.product", "p");

        }
        if (brandId !=null && brandId.longValue()>0) {
            detachedCriteria.createAlias("p.brand", "brand");
            detachedCriteria.add(Restrictions.eq("brand.id",brandId));
        }
        if (categoryId != null) {
            List<Long> allChildren = new ArrayList<>();
            allChildren.add(categoryId);
            categoryService.getAllChildrenByCategory(allChildren, categoryId);
            if (allChildren.size() > 0) {
                detachedCriteria.add(Restrictions.in("p.category.id", allChildren));
            }
        }
        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("iw.shop.id", shopId));

        }else{
            detachedCriteria.add(Restrictions.in("iw.shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
        }

        if (StringUtils.isNoneBlank(productName)) {
            detachedCriteria.add(Restrictions.like("p.name", productName, MatchMode.ANYWHERE));
        }
        if (qty > -1) {
            detachedCriteria.add(Restrictions.gt("iw.qty", qty));
        } else {
            detachedCriteria.add(Restrictions.gt("iw.qty", 0)); // 默认显示有库存的数据
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }

        Page<Inventory> page = inventoryService.list(detachedCriteria, inventoryListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(inventoryListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "inventory/inventoryList";
    }

    // purchase Order Management -----------------------------------------------------------------

    @RequestMapping("purchaseOrderManagement")
    public String purchaseOrderManagement(Model model, InventoryPurchaseOrderListVO inventoryPurchaseOrderListVO) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
        Long companyId = WebThreadLocal.getCompany().getId();
        List<Supplier> supplierList = supplierService.getActiveListByRefAndCompany(criteria, null, companyId);
        model.addAttribute("supplierList", supplierList);

        return "inventory/purchaseOrderManagement";
    }

    @RequestMapping("purchaseOrderList")
    public String purchaseOrderList(Model model, InventoryPurchaseOrderListVO inventoryPurchaseOrderListVO) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InventoryPurchaseOrder.class);
        String isActive = inventoryPurchaseOrderListVO.getIsActive();
        String status = inventoryPurchaseOrderListVO.getStatus();
        Long supplierId = inventoryPurchaseOrderListVO.getSupplierId();
        String deliveryNoteNumber=inventoryPurchaseOrderListVO.getDeliveryNoteNumber();
        String reference=inventoryPurchaseOrderListVO.getReference();
        String expectedDeliveryDate =inventoryPurchaseOrderListVO.getExpectedDeliveryDate();
        String date=inventoryPurchaseOrderListVO.getDate();
        if (StringUtils.isNotBlank(expectedDeliveryDate)) {
            detachedCriteria.add(Restrictions.ge("expectedDeliveryDate",
                    DateUtil.stringToDate(expectedDeliveryDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
            detachedCriteria.add(Restrictions.le("expectedDeliveryDate",
                    DateUtil.stringToDate(expectedDeliveryDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }
        //to date
        if (StringUtils.isNotBlank(date)) {
            detachedCriteria.add(Restrictions.ge("date",
                    DateUtil.stringToDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
            detachedCriteria.add(Restrictions.le("date",
                    DateUtil.stringToDate(date+" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }
        if (supplierId != null) {
            detachedCriteria.add(Restrictions.eq("supplier.id", supplierId));
        }
        if (StringUtils.isNoneBlank(reference)) {
            detachedCriteria.add(Restrictions.like("reference", reference, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNoneBlank(deliveryNoteNumber)) {
            detachedCriteria.add(Restrictions.like("deliveryNoteNumber", deliveryNoteNumber, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNoneBlank(status)) {
            detachedCriteria.add(Restrictions.eq("status", status));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        Page<InventoryPurchaseOrder> page = inventoryPurchaseOrderService.list(detachedCriteria, inventoryPurchaseOrderListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(inventoryPurchaseOrderListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "inventory/purchaseOrderList";
    }

    @RequestMapping("purchaseOrderToAdd")
    public String purchaseOrderToAdd(Model model, InventoryPurchaseOrderVO inventoryPurchaseOrderVO,HttpSession session) {
        String inventoryFromToken= UUID.randomUUID().toString();
        session.setAttribute("inventoryFromToken", inventoryFromToken);
        inventoryPurchaseOrderVO.setState(CommonConstant.STATE_ADD);
        if (inventoryPurchaseOrderVO.getId() != null) {
            InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(inventoryPurchaseOrderVO.getId());
            model.addAttribute("inventoryPurchaseOrder", inventoryPurchaseOrder);
            BeanUtils.copyProperties(inventoryPurchaseOrder, inventoryPurchaseOrderVO);
            inventoryPurchaseOrderVO.setSupplierId(inventoryPurchaseOrder.getSupplier().getId());
            inventoryPurchaseOrderVO.setState(CommonConstant.STATE_EDIT);
            inventoryPurchaseOrderVO.setShopList(inventoryPurchaseOrder.getShops().stream().map(Shop::getId).collect(Collectors.toList()));
        } else {
            Date date = new Date();
            inventoryPurchaseOrderVO.setDate(date);
            inventoryPurchaseOrderVO.setExpectedDeliveryDate(date);
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
        Long companyId = WebThreadLocal.getCompany().getId();
        List<Supplier> supplierList = supplierService.getActiveListByRefAndCompany(criteria, null, companyId);
        model.addAttribute("inventoryPurchaseOrderVO", inventoryPurchaseOrderVO);
        model.addAttribute("supplierList", supplierList);
        List<Shop> shopList = shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false);
        model.addAttribute("shopList", shopList);
        model.addAttribute("inventoryFromToken", inventoryFromToken);
        return "inventory/purchaseOrderAdd";
    }

    @RequestMapping("purchaseOrderAdd")
    @ResponseBody
    public AjaxForm purchaseOrderAdd(@Valid InventoryPurchaseOrderVO inventoryPurchaseOrderVO, BindingResult result , HttpSession session) {

        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            if(inventoryPurchaseOrderVO.getToken().equals(session.getAttribute("inventoryFromToken"))){
                inventoryPurchaseOrderService.saveOrUpdate(inventoryPurchaseOrderVO);
                session.removeAttribute("inventoryFromToken");
                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
            }else{
                return AjaxFormHelper.success(I18nUtil.getMessageKey("Form duplicate submission"));
            }

        }
    }
    @RequestMapping("toPurchaseOrderAddSelectProduct")
    public String toPurchaseOrderAddSelectProduct(Model model, InventoryPurchaseOrderVO inventoryPurchaseOrderVO) {
        //inventoryPurchaseOrderVO.setState(CommonConstant.STATE_ADD);
        model.addAttribute("inventoryPurchaseOrderVO", inventoryPurchaseOrderVO);
        if (inventoryPurchaseOrderVO.getId() != null) {
            InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(inventoryPurchaseOrderVO.getId());
            model.addAttribute("inventoryPurchaseOrder", inventoryPurchaseOrder);
        }
        Long supplierId = inventoryPurchaseOrderVO.getSupplierId();
        Supplier supplier = supplierService.get(supplierId);
        Set<ProductOption> options = new HashSet<>();
        for(Product product : supplier.getProducts()){
            options.addAll(product.getProductOptions());
        }
        model.addAttribute("ProductOptions",options);
        return "inventory/purchaseOrderAddSelectProduct";
    }

    @RequestMapping("purchaseOrderConfirm")
    public String purchaseOrderConfirm(Model model, InventoryPurchaseOrderVO inventoryPurchaseOrderVO) {
        Supplier supplier = supplierService.get(inventoryPurchaseOrderVO.getSupplierId());
        model.addAttribute("supplier", supplier);
        model.addAttribute("inventoryPurchaseOrderVO", inventoryPurchaseOrderVO);
        for (InventoryPurchaseOrderItemVO orderItemVO : inventoryPurchaseOrderVO.getInventoryPurchaseOrderItemVOs()) {
            ProductOption productOption = productOptionService.get(orderItemVO.getProductOptionId());
            orderItemVO.setProductOption(productOption);
            orderItemVO.setTotal(productOption.getOriginalPrice() * orderItemVO.getQty());
        }
        List<Shop> shopList = new ArrayList<>();
        if(inventoryPurchaseOrderVO.getShopList() != null && inventoryPurchaseOrderVO.getShopList().size() > 0){
            shopList = inventoryPurchaseOrderVO.getShopList().stream().map(shopId -> shopService.get(shopId)).collect(Collectors.toList());
        }
        model.addAttribute("shopList", shopList);
        return "inventory/purchaseOrderConfirm";
    }

    @RequestMapping("purchaseOrderView")
    public String purchaseOrderView(Model model, Long id) {
        InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(id);
        model.addAttribute("inventoryPurchaseOrder", inventoryPurchaseOrder);
        model.addAttribute("shopList", inventoryPurchaseOrder.getShops());
        return "inventory/purchaseOrderView";
    }

    @RequestMapping("purchaseOrderToShipmentAdd")
    public String purchaseOrderToShipmentAdd(Model model, InventoryPurchaseOrderShipmentVO inventoryPurchaseOrderShipmentVO) {
        Long purchaseOrderId = inventoryPurchaseOrderShipmentVO.getInventoryPurchaseOrderId();
        inventoryPurchaseOrderShipmentVO.setDeliveryDate(new Date());
        model.addAttribute("inventoryPurchaseOrderShipmentVO", inventoryPurchaseOrderShipmentVO);
        model.addAttribute("inventoryPurchaseOrder", inventoryPurchaseOrderService.get(purchaseOrderId));
        return "inventory/purchaseOrderShipmentAdd";
    }

    @RequestMapping("purchaseOrderShipmentAssign")
    public String purchaseOrderShipmentAssign(Model model, InventoryPurchaseOrderShipmentVO inventoryPurchaseOrderShipmentVO) {
        Long purchaseOrderId = inventoryPurchaseOrderShipmentVO.getInventoryPurchaseOrderId();
        InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(purchaseOrderId);
        inventoryPurchaseOrderShipmentVO.setDeliveryDate(new Date());
        model.addAttribute("inventoryPurchaseOrderShipmentVO", inventoryPurchaseOrderShipmentVO);
        model.addAttribute("shopList",inventoryPurchaseOrder.getShops());
        for (InventoryPurchaseOrderShipmentItemVO itemVO : inventoryPurchaseOrderShipmentVO.getInventoryPurchaseOrderShipmentItemVOs()) {
            itemVO.setProductOption(productOptionService.get(itemVO.getProductOptionId()));
        }
        return "inventory/purchaseOrderShipmentAssign";
    }

    @RequestMapping("purchaseOrderShipmentAddConfirm")
    public String purchaseOrderShipmentAddConfirm(Model model, InventoryPurchaseOrderShipmentVO inventoryPurchaseOrderShipmentVO) {
        model.addAttribute("inventoryPurchaseOrderShipmentVO", inventoryPurchaseOrderShipmentVO);
        for (InventoryPurchaseOrderShipmentItemVO shipmentItemVO : inventoryPurchaseOrderShipmentVO.getInventoryPurchaseOrderShipmentItemVOs()) {
            ProductOption productOption = productOptionService.get(shipmentItemVO.getProductOptionId());
            shipmentItemVO.setProductOption(productOption);
            for (InventoryTransactionVO transactionVO : shipmentItemVO.getInventoryTransactionVOs()) {
                transactionVO.setShop(shopService.get(transactionVO.getShopId()));
                transactionVO.setProductOption(productOption);
            }
        }
        Long purchaseOrderId = inventoryPurchaseOrderShipmentVO.getInventoryPurchaseOrderId();
        InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(purchaseOrderId);
        model.addAttribute("inventoryPurchaseOrderShipmentVO", inventoryPurchaseOrderShipmentVO);
        model.addAttribute("shopList",inventoryPurchaseOrder.getShops());
        return "inventory/purchaseOrderShipmentAddConfirm";
    }

    @RequestMapping("purchaseOrderShipmentAdd")
    @ResponseBody
    public AjaxForm purchaseOrderShipmentAdd(@Valid InventoryPurchaseOrderShipmentVO inventoryPurchaseOrderShipmentVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Company company = WebThreadLocal.getCompany();
            for (InventoryPurchaseOrderShipmentItemVO itemVO : inventoryPurchaseOrderShipmentVO.getInventoryPurchaseOrderShipmentItemVOs()) {
                ProductOption productOption = productOptionService.get(itemVO.getProductOptionId());
                itemVO.setProductOption(productOption);
                for (InventoryTransactionVO transactionVO : itemVO.getInventoryTransactionVOs()) {
                    transactionVO.setProductOption(productOption);
                    transactionVO.setShop(shopService.get(transactionVO.getShopId()));
                    transactionVO.setTransactionType(CommonConstant.INVENTORY_TRANSACTION_TYPE_PURCHASE_ORDER);
                    transactionVO.setDirection(CommonConstant.INVENTORY_TRANSACTION_DIRECTION_IN);
                    transactionVO.setEntryDate(inventoryPurchaseOrderShipmentVO.getDeliveryDate());
                    transactionVO.setCompany(company);
                }
            }
            inventoryPurchaseOrderShipmentService.save(inventoryPurchaseOrderShipmentVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    // inventory transaction ---------------------------------------------------------------------------

    @RequestMapping("transactionManagement")
    public String transactionManagement(Model model) {
        InventoryTransactionListVO inventoryTransactionListVO = new InventoryTransactionListVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));

        //brand list
        model.addAttribute("brandList", brandService.getBrandsSortBy(WebThreadLocal.getCompany().getId(), true, "name"));
        model.addAttribute("inventoryTransactionListVO",inventoryTransactionListVO);
        return "inventory/transactionManagement";
    }

    @RequestMapping("transactionList")
    public String transactionList(Model model, InventoryTransactionListVO inventoryTransactionListVO) {
        Long shopId = inventoryTransactionListVO.getShopId();
        String productName = inventoryTransactionListVO.getProductName();
        String transactionType = inventoryTransactionListVO.getTransactionType();
        String isActive = inventoryTransactionListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InventoryTransaction.class);
        //from date

        if (StringUtils.isNotBlank(inventoryTransactionListVO.getFromDate())) {
            detachedCriteria.add(Restrictions.ge("entryDate",
                    DateUtil.stringToDate(inventoryTransactionListVO.getFromDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
        }
        //to date
        if (StringUtils.isNotBlank(inventoryTransactionListVO.getToDate())) {
            detachedCriteria.add(Restrictions.le("entryDate",
                    DateUtil.stringToDate(inventoryTransactionListVO.getToDate() +" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }

        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        }

        Long brandId = inventoryTransactionListVO.getBrandId();
        Long categoryId = inventoryTransactionListVO.getCategoryId();

        if((brandId !=null && brandId.longValue()>0) || (StringUtils.isNoneBlank(productName)) || categoryId != null){
            detachedCriteria.createAlias("inventory", "i");
            detachedCriteria.createAlias("i.productOption", "po");
            detachedCriteria.createAlias("po.product", "p");
        }
        if (brandId !=null && brandId.longValue()>0) {

            detachedCriteria.createAlias("p.brand", "brand");
            detachedCriteria.add(Restrictions.eq("brand.id",brandId));
        }

        if (StringUtils.isNoneBlank(productName)) {
            detachedCriteria.add(Restrictions.like("p.name", productName, MatchMode.ANYWHERE));
        }

        if (categoryId != null) {
            List<Long> allChildren = new ArrayList<>();
            allChildren.add(categoryId);
            categoryService.getAllChildrenByCategory(allChildren, categoryId);
            if (allChildren.size() > 0) {
                detachedCriteria.add(Restrictions.in("p.category.id", allChildren));
            }
        }


        if (StringUtils.isNotBlank(transactionType)) {
            detachedCriteria.add(Restrictions.eq("transactionType", transactionType));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        detachedCriteria.addOrder(Order.desc("entryDate"));
        Page<InventoryTransaction> page = inventoryTransactionService.list(detachedCriteria, inventoryTransactionListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(inventoryTransactionListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "inventory/transactionList";
    }

    @RequestMapping("transactionToAdd")
    public String transactionToAdd(Model model, InventoryTransactionVO inventoryTransactionVO) {
        inventoryTransactionVO.setEntryDate(new Date());
        model.addAttribute("inventoryTransactionVO", inventoryTransactionVO);
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "inventory/transactionAdd";
    }

    @RequestMapping("transactionConfirm")
    public String transactionConfirm(Model model, TransactionVO transactionVO) {
        InventoryTransactionVO[] inventoryTransactionVOs = transactionVO.getInventoryTransactionVOs();
        model.addAttribute("inventoryTransactionVOs", inventoryTransactionVOs);
        for (InventoryTransactionVO inventoryTransactionVO : inventoryTransactionVOs) {
            inventoryTransactionVO.setProductOption(productOptionService.get(inventoryTransactionVO.getProductOptionId()));
            inventoryTransactionVO.setShop(shopService.get(inventoryTransactionVO.getShopId()));
        }
        return "inventory/transactionConfirm";
    }

    @RequestMapping("transactionAdd")
    @ResponseBody
    public AjaxForm transactionAdd(@Valid TransactionVO transactionVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Company company = WebThreadLocal.getCompany();
            InventoryTransactionVO[] inventoryTransactionVOs = transactionVO.getInventoryTransactionVOs();
            for (InventoryTransactionVO inventoryTransactionVO : inventoryTransactionVOs) {
                inventoryTransactionVO.setProductOption(productOptionService.get(inventoryTransactionVO.getProductOptionId()));
                inventoryTransactionVO.setShop(shopService.get(inventoryTransactionVO.getShopId()));
                inventoryTransactionVO.setCompany(company);
                inventoryTransactionVO.setDirection(CommonConstant.getTransactionDirection(inventoryTransactionVO.getTransactionType()));
            }
            inventoryTransactionService.save(inventoryTransactionVOs);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    // inventory transfer ---------------------------------------------------------------------------
    @RequestMapping("transferToAdd")
    public String transferToAdd(Model model, InventoryTransferVO inventoryTransferVO) {
        inventoryTransferVO.setEntryDate(new Date());
        inventoryTransferVO.setInventory(inventoryService.get(inventoryTransferVO.getInventoryId()));
        model.addAttribute("inventoryTransferVO", inventoryTransferVO);
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "inventory/transferAdd";
    }

    @RequestMapping("transferConfirm")
    public String transferConfirm(Model model, InventoryTransferVO inventoryTransferVO) {
        Inventory inventory = inventoryService.get(inventoryTransferVO.getInventoryId());
        inventoryTransferVO.setInventory(inventory);
        inventoryTransferVO.setFromShop(shopService.get(inventoryTransferVO.getFromShopId()));
        inventoryTransferVO.setToShop(shopService.get(inventoryTransferVO.getToShopId()));
        model.addAttribute("inventoryTransferVO", inventoryTransferVO);
        // 验证库存是否有效
        boolean hasInventory = false;
        for (InventoryWarehouse warehouse : inventory.getInventoryWarehouses()) {
            hasInventory |= warehouse.getShop().getId().equals(inventoryTransferVO.getFromShopId()); // 是否有库存
            if (warehouse.getShop().getId().equals(inventoryTransferVO.getFromShopId()) && inventoryTransferVO.getQty() > warehouse.getQty()) {
                model.addAttribute("error", "true");
                break;
            }
        }
        // 没有库存
        if (!hasInventory) {
            model.addAttribute("error", "true");
        }
        return "inventory/transferConfirm";
    }

    @RequestMapping("transferAdd")
    @ResponseBody
    public AjaxForm transferAdd(@Valid InventoryTransferVO inventoryTransferVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Company company = WebThreadLocal.getCompany();
            inventoryTransferVO.setCompany(company);
            inventoryTransferVO.setInventory(inventoryService.get(inventoryTransferVO.getInventoryId()));
            inventoryTransferVO.setFromShop(shopService.get(inventoryTransferVO.getFromShopId()));
            inventoryTransferVO.setToShop(shopService.get(inventoryTransferVO.getToShopId()));
            inventoryTransactionService.transfer(inventoryTransferVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    // print inventory purchase order --------------------------------------------------------------------

    @RequestMapping("toPurchaseOrderTemplate")
    public String purchaseOrderTemplate(Long id, Model model) {
        InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(id);
        model.addAttribute("inventoryPurchaseOrder", inventoryPurchaseOrder);
        return "inventory/purchaseOrderTemplate";
    }

    @RequestMapping("print")
    public void print(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        String downloadFileName = RandomUtil.generateRandomNumberWithDate("IO" + id + "-") + ".pdf";
        File downloadFile = PDFUtil.convert(PRINT_INVENTORY_PURCHASE_ORDER_URL, request, map);
        ServletUtil.download(downloadFile, downloadFileName, response);
    }
    @RequestMapping("inventoryDetailsExport")
    public void inventoryDetailsExport(InventoryTransactionListVO inventoryTransactionListVO, HttpServletResponse response) {
        Long shopId = inventoryTransactionListVO.getShopId();
        String productName = inventoryTransactionListVO.getProductName();
        String transactionType = inventoryTransactionListVO.getTransactionType();
        String isActive = inventoryTransactionListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InventoryTransaction.class);
        //purchase item details
        if (StringUtils.isNotBlank(inventoryTransactionListVO.getFromDate())) {
            detachedCriteria.add(Restrictions.ge("entryDate",
                    DateUtil.stringToDate(inventoryTransactionListVO.getFromDate()+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
        }
        //to date
        if (StringUtils.isNotBlank(inventoryTransactionListVO.getToDate())) {
            detachedCriteria.add(Restrictions.le("entryDate",
                    DateUtil.stringToDate(inventoryTransactionListVO.getToDate() +" 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }

        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        }

        Long brandId = inventoryTransactionListVO.getBrandId();
        if (brandId !=null && brandId.longValue()>0) {
            detachedCriteria.createAlias("inventory", "i");
            detachedCriteria.createAlias("i.productOption", "po");
            detachedCriteria.createAlias("po.product", "p");
            detachedCriteria.createAlias("p.brand", "brand");
            detachedCriteria.add(Restrictions.eq("brand.id",brandId));
        }

        if (StringUtils.isNoneBlank(productName)) {
            detachedCriteria.createAlias("inventory", "iy");
            detachedCriteria.createAlias("iy.productOption", "po");
            detachedCriteria.createAlias("po.product", "p");
            detachedCriteria.add(Restrictions.like("p.name", productName, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(transactionType)) {
            detachedCriteria.add(Restrictions.eq("transactionType", transactionType));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        detachedCriteria.addOrder(Order.desc("entryDate"));
        List<InventoryTransaction>  items= inventoryTransactionService.list(detachedCriteria);

        Context context = new Context();
        context.putVar("items", items);

        File downloadFile = ExcelUtil.write("inventoryTransactionExportTemplate.xls", context);

        ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("inventory-Transaction-Details-Report-") + ".xls", response);
    }




    @RequestMapping("listDataExport")
    public void listDataExport(InventoryListVO inventoryListVO, HttpServletResponse response) {
        Long shopId = inventoryListVO.getShopId();
        String productName = inventoryListVO.getProductName();
        int qty = inventoryListVO.getQty();
        String isActive = inventoryListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inventory.class);
        detachedCriteria.createAlias("inventoryWarehouses", "iw");

        Long brandId = inventoryListVO.getBrandId();
        Long categoryId = inventoryListVO.getCategoryId();

        if((brandId !=null && brandId.longValue()>0) || (StringUtils.isNoneBlank(productName))|| categoryId != null){
            detachedCriteria.createAlias("productOption", "po");
            detachedCriteria.createAlias("po.product", "p");


        }
        if (brandId !=null && brandId.longValue()>0) {
            detachedCriteria.createAlias("p.brand", "brand");
            detachedCriteria.add(Restrictions.eq("brand.id",brandId));
        }

        if (categoryId != null) {
            List<Long> allChildren = new ArrayList<>();
            allChildren.add(categoryId);
            categoryService.getAllChildrenByCategory(allChildren, categoryId);
            if (allChildren.size() > 0) {
                detachedCriteria.add(Restrictions.in("p.category.id", allChildren));
            }
        }

        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("iw.shop.id", shopId));

        }else{
            detachedCriteria.add(Restrictions.in("iw.shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
        }

        if (StringUtils.isNoneBlank(productName)) {
            detachedCriteria.add(Restrictions.like("p.name", productName, MatchMode.ANYWHERE));
        }
        if (qty > -1) {
            detachedCriteria.add(Restrictions.gt("iw.qty", qty));
        } else {
            detachedCriteria.add(Restrictions.gt("iw.qty", 0)); // 默认显示有库存的数据
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        List<Inventory>  items= inventoryService.list(detachedCriteria);

        Context context = new Context();
        context.putVar("items", items);

        File downloadFile = ExcelUtil.write("inventoryExportTemplate.xls", context);

        ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("inventory-Details-Report-") + ".xls", response);
    }
}