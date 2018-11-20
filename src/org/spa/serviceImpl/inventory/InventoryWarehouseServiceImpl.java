package org.spa.serviceImpl.inventory;

import com.spa.constant.CommonConstant;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.inventory.InventoryWarehouseDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.inventory.Inventory;
import org.spa.model.inventory.InventoryWarehouse;
import org.spa.service.inventory.InventoryService;
import org.spa.service.inventory.InventoryWarehouseService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.inventory.InventoryTransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Ivy 2016-3-31
 */
@Service
public class InventoryWarehouseServiceImpl extends BaseDaoHibernate<InventoryWarehouse> implements InventoryWarehouseService {

    @Autowired
    protected InventoryService inventoryService;
    
    @Autowired
    public InventoryWarehouseDao inventoryWarehouseDao;
    
    public InventoryWarehouse get(Long shopId, Long inventoryId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InventoryWarehouse.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        detachedCriteria.add(Restrictions.eq("inventory.id", inventoryId));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        return get(detachedCriteria);
    }

    public InventoryWarehouse saveOrUpdate(InventoryTransactionVO inventoryTransactionVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        Long shopId = inventoryTransactionVO.getShopId();
        Inventory inventory = inventoryService.save(inventoryTransactionVO);
        InventoryWarehouse inventoryWarehouse = get(shopId, inventory.getId());
        if (inventoryWarehouse == null) {
            inventoryWarehouse = new InventoryWarehouse();
            inventoryWarehouse.setCompany(inventoryTransactionVO.getCompany());
            inventoryWarehouse.setShop(inventoryTransactionVO.getShop());
            inventoryWarehouse.setInventory(inventory);
            inventoryWarehouse.setIsActive(true);
            inventoryWarehouse.setCreated(now);
            inventoryWarehouse.setCreatedBy(userName);
        }

        String transactionType = inventoryTransactionVO.getTransactionType();
        if (CommonConstant.INVENTORY_TRANSACTION_DIRECTION_IN.equals(inventoryTransactionVO.getDirection())) {
            int qty = inventoryWarehouse.getQty() + inventoryTransactionVO.getQty();
            inventoryWarehouse.setQty(qty);
        } else if (CommonConstant.INVENTORY_TRANSACTION_DIRECTION_OUT.equals(inventoryTransactionVO.getDirection())) {
            int qty = inventoryWarehouse.getQty() - inventoryTransactionVO.getQty();
            inventoryWarehouse.setQty(qty > 0 ? qty : 0); // 保证大于等于0
        } else {
            logger.error("inventory transaction type error: {}", transactionType);
        }

        inventoryWarehouse.setLastUpdated(now);
        inventoryWarehouse.setLastUpdatedBy(userName);
        saveOrUpdate(inventoryWarehouse);
        return inventoryWarehouse;
    }

	@Override
	public Integer getProductOptionQtyByShop(Long productOptionId, Long shopId) {
		return inventoryWarehouseDao.getProductOptionQtyByShop(productOptionId, shopId);
	}
}
