package org.spa.serviceImpl.inventory;

import com.spa.constant.CommonConstant;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.inventory.InventoryPurchaseItem;
import org.spa.model.inventory.InventoryPurchaseOrder;
import org.spa.model.inventory.InventoryPurchaseOrderShipment;
import org.spa.model.inventory.InventoryPurchaseOrderShipmentItem;
import org.spa.model.product.ProductOption;
import org.spa.model.product.Supplier;
import org.spa.model.shop.Shop;
import org.spa.service.company.CompanyService;
import org.spa.service.inventory.InventoryPurchaseOrderService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductService;
import org.spa.service.product.SupplierService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.inventory.InventoryPurchaseOrderItemVO;
import org.spa.vo.inventory.InventoryPurchaseOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Ivy 2016-3-31
 */
@Service
public class InventoryPurchaseOrderServiceImpl extends BaseDaoHibernate<InventoryPurchaseOrder> implements InventoryPurchaseOrderService {

    @Autowired
    protected UserService userService;

    @Autowired
    protected RoomService roomService;

    @Autowired
    protected ShopService shopService;

    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductOptionService productOptionService;

    @Autowired
    private SupplierService supplierService;

    public void saveOrUpdate(InventoryPurchaseOrderVO inventoryPurchaseOrderVO) {
        InventoryPurchaseOrder order;
        String prefix="MUL-";
        if(inventoryPurchaseOrderVO.getId() != null) {
            order = get(inventoryPurchaseOrderVO.getId());
            if(inventoryPurchaseOrderVO.getShopList().size() ==1){
            	Long shopId = inventoryPurchaseOrderVO.getShopList().get(0);
            	Shop s =shopService.get(shopId);
            	prefix= s.getPrefix()+"-";
            }
            String reference = order.getReference();
        	String[] split =reference.split("-");
        	String newRef=prefix + split[1]+"-"+split[2];
        	order.setReference(newRef);
        	
        	order.getShops().clear();
        } else {
            order = new InventoryPurchaseOrder();
           
            if(inventoryPurchaseOrderVO.getShopList().size()==1){
            	Long shopId = inventoryPurchaseOrderVO.getShopList().get(0);
            	Shop s =shopService.get(shopId);
            	prefix= s.getPrefix()+"-";
            }
            order.setReference(RandomUtil.generateRandomNumberWithDate(prefix));
            order.setStatus(CommonConstant.INVENTORY_PURCHASE_ORDER_STATUS_PENDING);
        }

        order.setCompany(WebThreadLocal.getCompany());
        order.setDate(inventoryPurchaseOrderVO.getDate());
        order.setExpectedDeliveryDate(inventoryPurchaseOrderVO.getExpectedDeliveryDate());
        //order.setDeliveryNoteNumber(inventoryPurchaseOrderVO.getDeliveryNoteNumber());
        order.setRemarks(inventoryPurchaseOrderVO.getRemarks());

        Supplier supplier = supplierService.get(inventoryPurchaseOrderVO.getSupplierId());
        order.setSupplier(supplier);
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        order.setIsActive(true);
        order.setCreated(now);
        order.setCreatedBy(userName);
        order.setLastUpdated(now);
        order.setLastUpdatedBy(userName);
        BigDecimal orderTotal = new BigDecimal(0);
        Set<InventoryPurchaseItem> purchaseItems = order.getInventoryPurchaseItems();
        purchaseItems.clear(); // 清除之前的數據
        for (InventoryPurchaseOrderItemVO inventoryPurchaseOrderItemVO : inventoryPurchaseOrderVO.getInventoryPurchaseOrderItemVOs()) {
            InventoryPurchaseItem purchaseItem = new InventoryPurchaseItem();
            purchaseItem.setInventoryPurchaseOrder(order);
            purchaseItem.setQty(inventoryPurchaseOrderItemVO.getQty());
            ProductOption productOption = productOptionService.get(inventoryPurchaseOrderItemVO.getProductOptionId());
            purchaseItem.setProductOption(productOption);
            BigDecimal price = new BigDecimal(productOption.getOriginalPrice().toString());
            BigDecimal itemTotal = price.multiply(new BigDecimal(inventoryPurchaseOrderItemVO.getQty()));
            purchaseItem.setCost(price);
            purchaseItem.setIsReceiveGoods(false);
            purchaseItem.setTotal(itemTotal);
            purchaseItem.setIsActive(true);
            purchaseItem.setCreated(now);
            purchaseItem.setCreatedBy(userName);
            purchaseItem.setLastUpdated(now);
            purchaseItem.setLastUpdatedBy(userName);
            orderTotal = orderTotal.add(itemTotal);
            purchaseItems.add(purchaseItem);
        }
        order.setTotalAmount(orderTotal);

        for(Long shopId : inventoryPurchaseOrderVO.getShopList()) {
            order.getShops().add(shopService.get(shopId));
        }
        saveOrUpdate(order);
    }

    public void updateStatus(InventoryPurchaseOrder inventoryPurchaseOrder) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        Map<Long, Integer> productAmountMap = new HashMap<>();
        for (InventoryPurchaseOrderShipment shipment : inventoryPurchaseOrder.getInventoryPurchaseOrderShipments()) {
            for (InventoryPurchaseOrderShipmentItem shipmentItem : shipment.getInventoryPurchaseOrderShipmentItems()) {
                Long productOptionId = shipmentItem.getProductOption().getId();
                Integer amount = productAmountMap.get(productOptionId);
                if (amount == null) {
                    amount = 0;
                }
                amount += shipmentItem.getQty();
                productAmountMap.put(productOptionId, amount);
            }
        }

        Boolean hasFinish = true;
        for (InventoryPurchaseItem item : inventoryPurchaseOrder.getInventoryPurchaseItems()) {
            Integer amount = productAmountMap.get(item.getProductOption().getId());
            if (amount == null || amount < item.getQty()) {
                hasFinish = false;
                break;
            }
        }
        inventoryPurchaseOrder.setLastUpdated(now);
        inventoryPurchaseOrder.setLastUpdatedBy(userName);
        if (hasFinish) {
            inventoryPurchaseOrder.setStatus(CommonConstant.INVENTORY_PURCHASE_ORDER_STATUS_COMPLETE);
        } else {
            inventoryPurchaseOrder.setStatus(CommonConstant.INVENTORY_PURCHASE_ORDER_STATUS_RECEIVING);
        }
        saveOrUpdate(inventoryPurchaseOrder);
    }

}
