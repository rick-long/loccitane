package org.spa.service.loyalty;

import java.util.Map;

import org.spa.dao.base.BaseDao;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.vo.loyalty.UserLoyaltyLevelVO;
/**
 * Created by Ivy on 2016/05/17
 */
public interface UserLoyaltyLevelService extends BaseDao<UserLoyaltyLevel>{
	
	public void saveUserLoyaltyLevel(UserLoyaltyLevelVO userLoyaltyLevelVO);
	
	public LoyaltyLevel getCurrentLLOfUser(Long userId);
	
	public UserLoyaltyLevel getActiveUserLoyaltyLevelOfUser(Long userId);
	
	public void reviewLoyaltyLevel(Map filterMap,boolean reviewAllRecords);
	
	public void reviewLoyaltyLevelV2(String action,Long memberId,String prepaidType,Double currentDeleteValue);
}
