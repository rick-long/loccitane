package org.spa.serviceImpl.inventory;

import com.spa.constant.CommonConstant;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.inventory.InventoryTransaction;
import org.spa.model.inventory.InventoryWarehouse;
import org.spa.model.product.ProductOption;
import org.spa.service.company.CompanyService;
import org.spa.service.inventory.InventoryTransactionService;
import org.spa.service.inventory.InventoryWarehouseService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.inventory.InventoryTransactionVO;
import org.spa.vo.inventory.InventoryTransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Ivy 2016-3-31
 */
@Service
public class InventoryTransactionServiceImpl extends BaseDaoHibernate<InventoryTransaction> implements InventoryTransactionService {

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
    private InventoryWarehouseService inventoryWarehouseService;

    public void save(InventoryTransactionVO[] inventoryTransactionVOs) {
        if (inventoryTransactionVOs == null || inventoryTransactionVOs.length == 0) {
            logger.warn("inventoryTransactionVOs is null or empty array!");
            return;
        }
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        for (InventoryTransactionVO transactionVO : inventoryTransactionVOs) {
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
            
            transaction.setPurchaseItem(transactionVO.getItem());
            if(transactionVO.getItem()!=null && transactionVO.getItem().getPurchaseOrder() !=null){
            	transaction.setPurchaseOrder(transactionVO.getItem().getPurchaseOrder());
            }else{
            	transaction.setPurchaseOrder(null);
            }
            
            
            save(transaction);
        }
    }

    public void save(InventoryTransactionVO transactionVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
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
        
        transaction.setPurchaseItem(transactionVO.getItem() !=null ? transactionVO.getItem() :null);
        transaction.setPurchaseOrder(transactionVO.getItem() !=null ?transactionVO.getItem().getPurchaseOrder() :null);
        
        save(transaction);
    }

    /**
     * 店与店库存交易
     *
     * @param inventoryTransferVO
     */
    public void transfer(InventoryTransferVO inventoryTransferVO) {
        ProductOption productOption = inventoryTransferVO.getInventory().getProductOption();
        InventoryTransactionVO fromTransactionVO = new InventoryTransactionVO();
        fromTransactionVO.setCompany(inventoryTransferVO.getCompany());
        fromTransactionVO.setDirection(CommonConstant.INVENTORY_TRANSACTION_DIRECTION_OUT);
        fromTransactionVO.setEntryDate(inventoryTransferVO.getEntryDate());
        fromTransactionVO.setProductOption(productOption);
        fromTransactionVO.setProductOptionId(productOption.getId());
        fromTransactionVO.setQty(inventoryTransferVO.getQty());
        fromTransactionVO.setShop(inventoryTransferVO.getFromShop());
        fromTransactionVO.setShopId(inventoryTransferVO.getFromShopId());
        fromTransactionVO.setTransactionType(CommonConstant.INVENTORY_TRANSACTION_TYPE_TRANSFER_MINUS);
        save(fromTransactionVO);

        InventoryTransactionVO toTransactionVO = new InventoryTransactionVO();
        toTransactionVO.setCompany(inventoryTransferVO.getCompany());
        toTransactionVO.setDirection(CommonConstant.INVENTORY_TRANSACTION_DIRECTION_IN);
        toTransactionVO.setEntryDate(inventoryTransferVO.getEntryDate());
        toTransactionVO.setProductOption(productOption);
        toTransactionVO.setProductOptionId(productOption.getId());
        toTransactionVO.setQty(inventoryTransferVO.getQty());
        toTransactionVO.setShop(inventoryTransferVO.getToShop());
        toTransactionVO.setShopId(inventoryTransferVO.getToShopId());
        toTransactionVO.setTransactionType(CommonConstant.INVENTORY_TRANSACTION_TYPE_TRANSFER_ADD);
        save(toTransactionVO);
    }

}
