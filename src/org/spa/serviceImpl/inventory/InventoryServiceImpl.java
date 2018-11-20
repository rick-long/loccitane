package org.spa.serviceImpl.inventory;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.company.Company;
import org.spa.model.inventory.Inventory;
import org.spa.service.inventory.InventoryService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.inventory.InventoryTransactionVO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Ivy 2016-3-31
 */
@Service
public class InventoryServiceImpl extends BaseDaoHibernate<Inventory> implements InventoryService {

    public Inventory get(Long companyId, Long productOptionId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inventory.class);
        detachedCriteria.add(Restrictions.eq("company.id", companyId));
        detachedCriteria.add(Restrictions.eq("productOption.id", productOptionId));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        return get(detachedCriteria);
    }

    public Inventory save(InventoryTransactionVO inventoryTransactionVO) {
        Company company = inventoryTransactionVO.getCompany();
        Long productOptionId = inventoryTransactionVO.getProductOptionId();
        Inventory inventory = get(company.getId(), productOptionId);
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setCompany(inventoryTransactionVO.getCompany());
            inventory.setCompany(inventoryTransactionVO.getCompany());
            inventory.setProductOption(inventoryTransactionVO.getProductOption());
            inventory.setIsActive(true);
            Date now = new Date();
            String userName = WebThreadLocal.getUser().getUsername();
            inventory.setCreated(now);
            inventory.setCreatedBy(userName);
            inventory.setLastUpdated(now);
            inventory.setLastUpdatedBy(userName);
            save(inventory);
        }
        return inventory;
    }

}
