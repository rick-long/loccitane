package org.spa.dao.order;

import java.util.List;
import java.util.Map;

import org.spa.model.order.StaffCommission;

public interface StaffCommissionDao {

	public void deleteStaffCommissionsByPurchaseItem(final Long purchaseItemId);
	
	public Double[] sumStaffCommissionByFilter(final Map<String, Object> filterMap,List<Long> categoryIdList);
	
	public List<StaffCommission> getStaffCommissionsByFilter(final Map<String, Object> filterMap,List<Long> categoryIdList);
}
