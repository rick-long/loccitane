package org.spa.service.points;

import java.util.Date;
import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.points.SpaPointsTransaction;
import org.spa.vo.loyalty.PointsAdjustVO;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface SpaPointsTransactionService extends BaseDao<SpaPointsTransaction>{
	//
	public List<SpaPointsTransaction> getSpaPointsTransactionByFields(Long userId,Date expiredDate,Long purchaseOrderId,Boolean isActiveRecords);
	
	public void saveSpaPointsTransaction(PointsAdjustVO pointsAdjustVO,boolean reviewLoyaltyLevel);
	
	public double getRemainSpaPointsByUser(Long userId,Date expiredDate);
	
	public void resetSpaPointsTransaction(double upgradePoints,Long userId,Date expiredDate,Boolean isActiveRecords);
	
}
