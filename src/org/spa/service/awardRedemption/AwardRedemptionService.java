package org.spa.service.awardRedemption;


import org.spa.dao.base.BaseDao;
import org.spa.model.awardRedemption.AwardRedemption;
import org.spa.vo.loyalty.AwardRedemptionAddVO;

/**
 * Created by Ivy on 2016/05/23.
 */
public interface AwardRedemptionService extends BaseDao<AwardRedemption>{
	//
	public void saveAwardRedemption(AwardRedemptionAddVO awardRedemptionAddVO);
}
