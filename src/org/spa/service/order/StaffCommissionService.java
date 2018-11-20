package org.spa.service.order;

import java.util.Date;
import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.StaffCommission;
import org.spa.model.product.Category;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface StaffCommissionService extends BaseDao<StaffCommission>{
	//
	public StaffCommission getStaffCommissionByItemAndDispalyOrder(Long purchaseItemId,int displayOrder);
	
	public void deleteStaffCommissionsByPurchaseItem(Long purchaseItemId);
	
	public List<StaffCommission> getStaffCommissionsByPurchaseDateAndStaffAndCategory(Date purchaseDate, Long staffId,Category category,Long companyId);
}
