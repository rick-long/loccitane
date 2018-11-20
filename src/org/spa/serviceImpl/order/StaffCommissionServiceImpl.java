package org.spa.serviceImpl.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.spa.dao.order.StaffCommissionDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.StaffCommission;
import org.spa.model.product.Category;
import org.spa.service.order.StaffCommissionService;
import org.spa.service.product.CategoryService;
import org.spa.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/04/16.
 */
@Service
public class StaffCommissionServiceImpl extends BaseDaoHibernate<StaffCommission> implements StaffCommissionService{
	
	@Autowired
	public StaffCommissionDao staffCommissionDao;
	
	@Autowired
	public CategoryService categoryService;
	
	
	@Override
	public void deleteStaffCommissionsByPurchaseItem(Long purchaseItemId) {
		staffCommissionDao.deleteStaffCommissionsByPurchaseItem(purchaseItemId);
	}
	
	@Override
	public StaffCommission getStaffCommissionByItemAndDispalyOrder(Long purchaseItemId, int displayOrder) {
		DetachedCriteria dc = DetachedCriteria.forClass(StaffCommission.class);
		//
		dc.add(Restrictions.eq("isActive",true));
		dc.add(Restrictions.eq("displayOrder",displayOrder));
		dc.add(Restrictions.eq("purchaseItem.id",purchaseItemId));
		List<StaffCommission> scList=list(dc);
		StaffCommission sc=null;
		if(scList !=null && scList.size()>0){
			sc=scList.get(0);
		}
		return sc ;
	}
	
	@Override
	public List<StaffCommission> getStaffCommissionsByPurchaseDateAndStaffAndCategory(Date purchaseDate, Long staffId,Category category,Long companyId) {
		
		List<Long> allChildrens=new ArrayList<Long>();
		List<Long> childrenCategoryIds=categoryService.getAllChildrenByCategory(allChildrens, category.getId());
		
		DateTime fromDate = new DateTime(purchaseDate).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
		DateTime toDate= new DateTime(purchaseDate).dayOfMonth().withMaximumValue().withSecondOfMinute(0);
		
		Map filterMap =CollectionUtils.getLightWeightMap();
		filterMap.clear();
		filterMap.put("fromDate", fromDate.toDate());
		filterMap.put("toDate", toDate.toDate());
		filterMap.put("staffId", staffId);
		filterMap.put("companyId", companyId);
		
		List<StaffCommission> scList=staffCommissionDao.getStaffCommissionsByFilter(filterMap, childrenCategoryIds);
		
		return scList ;
	}
}
