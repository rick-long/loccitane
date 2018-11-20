package org.spa.serviceImpl.points;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.points.LoyaltyPointsTransaction;
import org.spa.service.points.LoyaltyPointsTransactionService;
import org.spa.service.user.UserService;
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
public class LoyaltyPointsTransactionServiceImpl extends BaseDaoHibernate<LoyaltyPointsTransaction> implements LoyaltyPointsTransactionService{

	@Autowired
	public UserService userService;
	
	@Override
	public void saveLoyaltyPointsTransaction(PointsAdjustVO pointsAdjustVO) {
		
		LoyaltyPointsTransaction lpt=null;
		Date now=new Date();
		String loginUser=pointsAdjustVO.getPurchaseOrder().getUser().getUsername();
		
		if(pointsAdjustVO.getPurchaseOrder() !=null){
			List<LoyaltyPointsTransaction> lptList=getLoyaltyPointsTransactionByFields(null, null, pointsAdjustVO.getPurchaseOrder().getId(), true);
			//找出是否含有此订单的记录，如果有表示编辑，没有表示添加
			if(lptList !=null && lptList.size()>0){
				//编辑
				lpt=lptList.get(0);
			}//else 添加
		}
		if(lpt !=null){
			lpt.setLastUpdated(now);
			lpt.setLastUpdatedBy(loginUser);
		}else{
			lpt=new LoyaltyPointsTransaction();
			lpt.setCreated(now);
			lpt.setCreatedBy(loginUser);
			lpt.setUser(pointsAdjustVO.getUser());
			lpt.setPurchaseOrder(pointsAdjustVO.getPurchaseOrder());
			lpt.setIsActive(true);
		}
		lpt.setEarnChannel(pointsAdjustVO.getEarnChannel());
		lpt.setEarnDate(pointsAdjustVO.getEarnDate());
		lpt.setRemarks(pointsAdjustVO.getRemarks());
		
		if(CommonConstant.POINTS_ACTION_PLUS.equals(pointsAdjustVO.getAction())){
			
			lpt.setEarnPoints(pointsAdjustVO.getAdjustPoints());
			
		}else if(CommonConstant.POINTS_ACTION_MINUS.equals(pointsAdjustVO.getAction())){
			
			lpt.setEarnPoints(-pointsAdjustVO.getAdjustPoints());
		}
		try {
			
			lpt.setExpiryDate(DateUtil.getLastMinutsOfLastDayAfterNumOfMonths(pointsAdjustVO.getEarnDate(),pointsAdjustVO.getMonthLimit()));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		lpt.setRedeemedPoints(0d);
		
		saveOrUpdate(lpt);
	}

	@Override
	public List<LoyaltyPointsTransaction> getLoyaltyPointsTransactionByFields(Long userId, Date expiredDate,
			Long purchaseOrderId,Boolean isActiveRecords) {
		DetachedCriteria lpDC = DetachedCriteria.forClass(LoyaltyPointsTransaction.class);
		if(isActiveRecords !=null){
			lpDC.add(Restrictions.eq("isActive", isActiveRecords));
		}
		// search by user id
		if(userId !=null && userId.longValue()>0){
			lpDC.add(Restrictions.eq("user.id", userId));
		}
		// search by purchase order id
		if(purchaseOrderId !=null && purchaseOrderId.longValue()>0){
			lpDC.add(Restrictions.eq("purchaseOrder.id", purchaseOrderId));
		}
		// search expiry date
		if(expiredDate !=null){
			lpDC.add(Restrictions.gt("expiryDate", DateUtil.getLastMinuts(expiredDate)));
		}
		// order by earn date asc
		lpDC.addOrder(Order.asc("earnDate"));
		
		List<LoyaltyPointsTransaction> lptList=list(lpDC);
		return lptList;
	}

	@Override
	public List<LoyaltyPointsTransaction> getLoyaltyPointsTransactionByFields(Long userId, Date startDate,Date endDate,
			Long purchaseOrderId,Boolean isActiveRecords) {
		DetachedCriteria lpDC = DetachedCriteria.forClass(LoyaltyPointsTransaction.class);
		if(isActiveRecords !=null){
			lpDC.add(Restrictions.eq("isActive", isActiveRecords));
		}
		// search by user id
		if(userId !=null && userId.longValue()>0){
			lpDC.add(Restrictions.eq("user.id", userId));
		}
		// search by purchase order id
		if(purchaseOrderId !=null && purchaseOrderId.longValue()>0){
			lpDC.add(Restrictions.eq("purchaseOrder.id", purchaseOrderId));
		}
		// search expiry date
		if(startDate !=null){
			lpDC.add(Restrictions.ge("earnDate", DateUtil.getFirstMinuts(startDate)));
		}
		if(endDate !=null){
			lpDC.add(Restrictions.le("earnDate", DateUtil.getLastMinuts(endDate)));
		}
		// order by earn date asc
		lpDC.addOrder(Order.asc("earnDate"));
		
		List<LoyaltyPointsTransaction> lptList=list(lpDC);
		return lptList;
	}
	
	@Override
	public double getRemainLoyaltyPointsByUser(Long userId, Date expiredDate) {
		double remainPoints=0;
		
		List<LoyaltyPointsTransaction> lptList=getLoyaltyPointsTransactionByFields(userId, expiredDate, null,true);
		
		if(lptList !=null && lptList.size()>0){
			for(LoyaltyPointsTransaction lpt : lptList){
				remainPoints += lpt.getEarnPoints().doubleValue() - lpt.getRedeemedPoints().doubleValue();
			}
		}
		return remainPoints;
	}
	
	@Override
	public void resetLoyaltyPointsTransaction(double requiredPointsForRedeem,Long userId,Date expiredDate,Boolean isActiveRecords) {
		
		Date now =new Date();
		String loginUser=WebThreadLocal.getUser().getUsername();
		
		List<LoyaltyPointsTransaction> lptList=getLoyaltyPointsTransactionByFields(userId, expiredDate, null,isActiveRecords);
		
		if(lptList !=null && lptList.size()>0){
			boolean isBreak=false;
			for(LoyaltyPointsTransaction lpt : lptList){
				double remainPoints=lpt.getEarnPoints().doubleValue() - lpt.getRedeemedPoints().doubleValue();
				if(requiredPointsForRedeem >= remainPoints){
					requiredPointsForRedeem=requiredPointsForRedeem - remainPoints;
					//用来redeem，如果这条record的points完全用来redeem，那么这条record的is active 设置为false
					lpt.setRedeemedPoints(lpt.getEarnPoints());
					lpt.setIsActive(false);
				}else{
					lpt.setRedeemedPoints(lpt.getRedeemedPoints()+requiredPointsForRedeem);
					isBreak=true;
				}
				
				lpt.setLastUpdated(now);
				lpt.setLastUpdatedBy(loginUser);
				
				saveOrUpdate(lpt);
				
				if(isBreak){
					break;
				}
			}
		}
	}
	
}
