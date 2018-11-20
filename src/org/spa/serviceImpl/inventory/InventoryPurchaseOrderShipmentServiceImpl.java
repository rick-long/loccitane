package org.spa.serviceImpl.inventory;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.inventory.*;
import org.spa.service.company.CompanyService;
import org.spa.service.inventory.InventoryPurchaseOrderService;
import org.spa.service.inventory.InventoryPurchaseOrderShipmentService;
import org.spa.service.inventory.InventoryWarehouseService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.inventory.InventoryPurchaseOrderShipmentItemVO;
import org.spa.vo.inventory.InventoryPurchaseOrderShipmentVO;
import org.spa.vo.inventory.InventoryTransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * @author Ivy 2016-3-31
 */
@Service
public class InventoryPurchaseOrderShipmentServiceImpl extends BaseDaoHibernate<InventoryPurchaseOrderShipment> implements InventoryPurchaseOrderShipmentService {

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
    private InventoryPurchaseOrderService inventoryPurchaseOrderService;

    @Autowired
    private InventoryWarehouseService inventoryWarehouseService;

    public void save(InventoryPurchaseOrderShipmentVO shipmentVO) {
        Long purchaseOrderId = shipmentVO.getInventoryPurchaseOrderId();
        if (purchaseOrderId == null) {
            return;
        }

        InventoryPurchaseOrder inventoryPurchaseOrder = inventoryPurchaseOrderService.get(purchaseOrderId);
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        InventoryPurchaseOrderShipment shipment = new InventoryPurchaseOrderShipment();
        shipment.setInventoryPurchaseOrder(inventoryPurchaseOrder);
        shipment.setDeliveryDate(shipmentVO.getDeliveryDate());
        shipment.setDeliveryNumber(shipmentVO.getDeliveryNumber());
        // shipment Item
        Set<InventoryPurchaseOrderShipmentItem> shipmentItemSet = shipment.getInventoryPurchaseOrderShipmentItems();
        for (InventoryPurchaseOrderShipmentItemVO itemVO : shipmentVO.getInventoryPurchaseOrderShipmentItemVOs()) {
            InventoryPurchaseOrderShipmentItem shipmentItem = new InventoryPurchaseOrderShipmentItem();
            shipmentItem.setInventoryPurchaseOrderShipment(shipment);
            shipmentItem.setProductOption(itemVO.getProductOption());
            shipmentItem.setQty(itemVO.getQty());
            shipmentItem.setIsActive(true);
            shipmentItem.setCreated(now);
            shipmentItem.setCreatedBy(userName);
            shipmentItem.setLastUpdated(now);
            shipmentItem.setLastUpdatedBy(userName);
            Set<InventoryTransaction> transactionSet = shipmentItem.getInventoryTransactions();
            // 添加库存交易记录和更新库存
            for (InventoryTransactionVO transactionVO : itemVO.getInventoryTransactionVOs()) {
                InventoryWarehouse warehouse = inventoryWarehouseService.saveOrUpdate(transactionVO);
                InventoryTransaction transaction = new InventoryTransaction();
                transaction.setInventory(warehouse.getInventory());
                transaction.setCompany(transactionVO.getCompany());
                transaction.setShop(transactionVO.getShop());
                transaction.setEntryDate(transactionVO.getEntryDate());
                transaction.setTransactionType(transactionVO.getTransactionType());
                transaction.setDirection(transactionVO.getDirection());
                transaction.setQty(transactionVO.getQty());
                transaction.setRemarks(transactionVO.getRemarks());
                transaction.setIsActive(true);
                transaction.setCreated(now);
                transaction.setCreatedBy(userName);
                transaction.setLastUpdated(now);
                transaction.setLastUpdatedBy(userName);
                transaction.setInventoryPurchaseOrderShipmentItem(shipmentItem);
                transactionSet.add(transaction);
            }
            shipmentItemSet.add(shipmentItem);
        }
        shipment.setIsActive(true);
        shipment.setCreated(now);
        shipment.setCreatedBy(userName);
        shipment.setLastUpdated(now);
        shipment.setLastUpdatedBy(userName);
        save(shipment); // 保存shipment
        inventoryPurchaseOrderService.updateStatus(inventoryPurchaseOrder); // 更新po的狀態
    }

}
