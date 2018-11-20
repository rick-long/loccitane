package org.spa.service.awardRedemption;


import org.spa.dao.base.BaseDao;
import org.spa.model.awardRedemption.AwardRedemptionTransaction;
import org.spa.vo.loyalty.AwardRedeemVO;

/**
 * Created by Ivy on 2016/05/23.
 */
public interface AwardRedemptionTransactionService extends BaseDao<AwardRedemptionTransaction>{
	//
	public Long saveAwardRedemptionTransaction(AwardRedeemVO awardRedeemVO);
}
