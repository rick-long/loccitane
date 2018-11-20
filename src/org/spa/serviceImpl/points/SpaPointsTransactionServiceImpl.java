package org.spa.serviceImpl.points;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.points.SpaPointsTransaction;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.service.points.SpaPointsTransactionService;
import org.spa.utils.CollectionUtils;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.loyalty.PointsAdjustVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class SpaPointsTransactionServiceImpl extends BaseDaoHibernate<SpaPointsTransaction> implements SpaPointsTransactionService{

	@Autowired
	public UserLoyaltyLevelService userLoyaltyLevelService;
	
	private static Logger logger = Logger.getLogger(SpaPointsTransactionServiceImpl.class);
	@Override
	public void saveSpaPointsTransaction(PointsAdjustVO pointsAdjustVO,boolean reviewLoyaltyLevel) {
		
		SpaPointsTransaction spt=null;
		Date now=new Date();
		String loginUser=WebThreadLocal.getUser().getUsername();
		
		if(pointsAdjustVO.getPurchaseOrder() !=null){
			List<SpaPointsTransaction> sptList=getSpaPointsTransactionByFields(null, null, pointsAdjustVO.getPurchaseOrder().getId(),true);
			//找出是否含有此订单的记录，如果有表示编辑，没有表示添加
			if(sptList !=null && sptList.size()>0){
				spt=sptList.get(0);
			}
		}
		if(spt != null){
			spt.setLastUpdated(now);
			spt.setLastUpdatedBy(loginUser);
		}else{
			spt=new SpaPointsTransaction();
			spt.setIsActive(true);
			spt.setCreated(now);
			spt.setCreatedBy(loginUser);
			spt.setPurchaseOrder(pointsAdjustVO.getPurchaseOrder());
			spt.setUser(pointsAdjustVO.getUser());
		}
		spt.setEarnChannel(pointsAdjustVO.getEarnChannel());
		spt.setEarnDate(pointsAdjustVO.getEarnDate());
		spt.setRemarks(pointsAdjustVO.getRemarks());
		
		if(CommonConstant.POINTS_ACTION_PLUS.equals(pointsAdjustVO.getAction())){
			
			spt.setEarnPoints(pointsAdjustVO.getAdjustPoints());
			
		}else if(CommonConstant.POINTS_ACTION_MINUS.equals(pointsAdjustVO.getAction())){
			
			spt.setEarnPoints(-pointsAdjustVO.getAdjustPoints());
		}

		try {
			spt.setExpiryDate(DateUtil.getLastMinutsOfLastDayAfterNumOfMonths(pointsAdjustVO.getEarnDate(),pointsAdjustVO.getMonthLimit()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		spt.setEarnPoints(pointsAdjustVO.getAdjustPoints());
		spt.setUsedPoints(0d);
		
		saveOrUpdate(spt);
		logger.debug("saveSpaPointsTransaction-----reviewLoyaltyLevel{}:"+reviewLoyaltyLevel);
		if(reviewLoyaltyLevel){
			Map filterMap=CollectionUtils.getLightWeightMap();
			filterMap.put("user", spt.getUser());
			userLoyaltyLevelService.reviewLoyaltyLevel(filterMap,false);
		}
	}
	
	@Override
	public double getRemainSpaPointsByUser(Long userId,Date expiredDate) {
		
		double remainPoints=0;
		
		List<SpaPointsTransaction> sptList=getSpaPointsTransactionByFields(userId, expiredDate,null,true);
		
		if(sptList !=null && sptList.size()>0){
			for(SpaPointsTransaction spt : sptList){
				remainPoints += spt.getEarnPoints().doubleValue() - spt.getUsedPoints().doubleValue();
			}
		}
		return remainPoints;
	}
	@Override
	public List<SpaPointsTransaction> getSpaPointsTransactionByFields(Long userId,Date expiredDate,Long purchaseOrderId,Boolean isActiveRecords){
		DetachedCriteria spDC = DetachedCriteria.forClass(SpaPointsTransaction.class);
		if(isActiveRecords !=null){
			spDC.add(Restrictions.eq("isActive", isActiveRecords));
		}
		// search by user id
		if(userId !=null && userId.longValue()>0){
			spDC.add(Restrictions.eq("user.id", userId));
		}
		// search by purchase order id
		if(purchaseOrderId !=null && purchaseOrderId.longValue()>0){
			spDC.add(Restrictions.eq("purchaseOrder.id", purchaseOrderId));
		}
		// search expiry date
		if(expiredDate !=null){
			spDC.add(Restrictions.gt("expiryDate", DateUtil.getLastMinuts(expiredDate)));
		}
		// order by earn date asc
		spDC.addOrder(Order.asc("earnDate"));
		
		List<SpaPointsTransaction> sptList=list(spDC);
		return sptList;
	}
	
	@Override
	public void resetSpaPointsTransaction(double upgradeNeedPoints,Long userId,Date expiredDate,Boolean isActiveRecords) {
		
		Date now =new Date();
		String loginUser=WebThreadLocal.getUser().getUsername();
		
		List<SpaPointsTransaction> sptList=getSpaPointsTransactionByFields(userId, expiredDate,null,isActiveRecords);
		
		if(sptList !=null && sptList.size()>0){
			boolean isBreak=false;
			for(SpaPointsTransaction spt : sptList){
				double remainPoints=spt.getEarnPoints().doubleValue() - spt.getUsedPoints().doubleValue();
				if(upgradeNeedPoints >= remainPoints){
					upgradeNeedPoints=upgradeNeedPoints - remainPoints;
					//用来升级，如果这条record的points完全用来升级，那么这条record的is active设置为false
					spt.setUsedPoints(spt.getEarnPoints());
				}else{
					spt.setUsedPoints(upgradeNeedPoints);
					isBreak=true;
				}
				
				spt.setLastUpdated(now);
				spt.setLastUpdatedBy(loginUser);
				
				saveOrUpdate(spt);
				
				if(isBreak){
					break;
				}
			}
		}
	}
}
