package org.spa.service.loyalty;

import org.spa.dao.base.BaseDao;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.vo.loyalty.LoyaltyLevelAddVO;

/**
 * Created by Ivy on 2016/05/17
 */
public interface LoyaltyLevelService extends BaseDao<LoyaltyLevel>{
	
	public void saveLoyaltyLevel(LoyaltyLevelAddVO loyaltyLevelAddVO);
	
	public LoyaltyLevel getActiveLoyaltyLevelByRank(Integer rank);
	
	public LoyaltyLevel getNextLoyaltyLevel(LoyaltyLevel currentLL);

	public LoyaltyLevel getPrevLoyaltyLevel(LoyaltyLevel currentLL);
	
	public LoyaltyLevel getHighestLoyaltyLevel();
}
