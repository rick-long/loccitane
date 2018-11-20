package org.spa.service.points;

import java.util.Date;
import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.points.LoyaltyPointsTransaction;
import org.spa.vo.loyalty.PointsAdjustVO;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface LoyaltyPointsTransactionService extends BaseDao<LoyaltyPointsTransaction>{
	//
	public void saveLoyaltyPointsTransaction(PointsAdjustVO pointsAdjustVO);
	
	public List<LoyaltyPointsTransaction> getLoyaltyPointsTransactionByFields(Long userId,Date expiredDate,Long purchaseOrderId,Boolean isActiveRecords);

	public double getRemainLoyaltyPointsByUser(Long userId,Date expiredDate);
	
	public void resetLoyaltyPointsTransaction(double requiredPointsForReddeem,Long userId,Date expiredDate,Boolean isActiveRecords);
	
	public List<LoyaltyPointsTransaction> getLoyaltyPointsTransactionByFields(Long userId, Date startDate,Date endDate,
			Long purchaseOrderId,Boolean isActiveRecords);
}
