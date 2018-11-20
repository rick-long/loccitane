package org.spa.serviceImpl.loyalty;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.prepaid.PrepaidDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.points.SpaPointsTransaction;
import org.spa.model.user.User;
import org.spa.service.loyalty.LoyaltyLevelService;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.service.points.SpaPointsTransactionService;
import org.spa.service.user.UserService;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.loyalty.UserLoyaltyLevelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/05/17.
 */
@Service
public class UserLoyaltyLevelServiceImpl extends BaseDaoHibernate<UserLoyaltyLevel> implements UserLoyaltyLevelService{

	@Autowired
	public LoyaltyLevelService loyaltyLevelService;
	
	@Autowired
	public SpaPointsTransactionService spaPointsTransactionService;
	
	@Autowired
	public PrepaidDao prepaidDao;
	
	@Autowired
	public UserService userService;
	
	
	public void saveUserLoyaltyLevel(UserLoyaltyLevelVO userLoyaltyLevelVO) {
		//find out all active  user loyalty level and set false
		DetachedCriteria dc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
		dc.add(Restrictions.eq("isActive", true));
		dc.add(Restrictions.eq("user.id", userLoyaltyLevelVO.getUser().getId()));
		
		List<UserLoyaltyLevel> ullList=list(dc);
		if(ullList !=null && ullList.size()>0){
			for(UserLoyaltyLevel ull : ullList){
				ull.setIsActive(false);
				saveOrUpdate(ull);
			}
		}
		UserLoyaltyLevel ull=new UserLoyaltyLevel();
		LoyaltyLevel ll=userLoyaltyLevelVO.getLoyaltyLevel();
		
		ull.setLoyaltyLevel(ll);
		ull.setUser(userLoyaltyLevelVO.getUser());
		ull.setJoinDate(userLoyaltyLevelVO.getJoinDate());
		
		try {
			Date expiryDate=null;
			if(userLoyaltyLevelVO.getMonthLimit() !=null && userLoyaltyLevelVO.getMonthLimit().intValue()>0){
				expiryDate=DateUtil.getLastMinutsOfLastDayAfterNumOfMonths(ull.getJoinDate(), userLoyaltyLevelVO.getMonthLimit().intValue());
				
			}else{
				if(ll.getMonthLimit() !=null && ll.getMonthLimit().doubleValue() !=0){
					expiryDate=DateUtil.getLastMinutsOfLastDayAfterNumOfMonths(ull.getJoinDate(), ll.getMonthLimit().intValue());
				}
			}
			//expired date is null: 永久
			ull.setExpiryDate(expiryDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ull.setRemarks(userLoyaltyLevelVO.getRemarks());
		
		ull.setIsActive(true);
		ull.setCreated(new Date());
		ull.setCreatedBy(WebThreadLocal.getUser() !=null ? WebThreadLocal.getUser().getUsername() : "Rgister");
		
		saveOrUpdate(ull);
		
		if(userLoyaltyLevelVO.getSendNotificationEmail() !=null && userLoyaltyLevelVO.getSendNotificationEmail()){
			//send email to client
			
		}
	}

	@Override
	public UserLoyaltyLevel getActiveUserLoyaltyLevelOfUser(Long userId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
		dc.add(Restrictions.eq("isActive", true));
		dc.add(Restrictions.eq("user.id", userId));
		
		List<UserLoyaltyLevel> ullList=list(dc);
		if(ullList !=null && ullList.size()>0){
			UserLoyaltyLevel ull=ullList.get(0);
			return ull;
		}else{
			return null;
		}
	}
	
	@Override
	public LoyaltyLevel getCurrentLLOfUser(Long userId) {
		
		LoyaltyLevel currentLL=null;
		UserLoyaltyLevel ull=getActiveUserLoyaltyLevelOfUser(userId);
		if(ull !=null){
			currentLL=ull.getLoyaltyLevel();
		}else{
			currentLL=loyaltyLevelService.getActiveLoyaltyLevelByRank(1);
		}
		return currentLL;
	}

	@Override
	public void reviewLoyaltyLevel(Map filterMap,boolean reviewAllRecords) {
		
		User user=(User) filterMap.get("user");
		Date now=new Date();
		
		LoyaltyLevel currentLL=getCurrentLLOfUser(user.getId());
		LoyaltyLevel nextLL=loyaltyLevelService.getNextLoyaltyLevel(currentLL);
		
		logger.debug("----reviewLoyaltyLevel--------currentLL{}:"+(currentLL !=null ? currentLL.getName() : null)+",nextLL{}:"+ (nextLL !=null ? nextLL.getName() : null));
		
		if(reviewAllRecords){
			//将当前用户的所有数据复原
			List<SpaPointsTransaction> sptList=spaPointsTransactionService.getSpaPointsTransactionByFields(user.getId(), null, null, false);
			for(SpaPointsTransaction spt : sptList){
				spt.setIsActive(true);
				spt.setUsedPoints(0d);
				spaPointsTransactionService.saveOrUpdate(spt);
			}
		}else{
			//不需要重新计算。将现有的有效的未使用points累加，不需要考虑降级的问题
			Date expiredDate=now;
			double remainPoints=spaPointsTransactionService.getRemainSpaPointsByUser(user.getId(),expiredDate);
			LoyaltyLevel upgradedLoyaltyLevel = null;
			double upgradedNeedPoints = nextLL.getRequiredSpaPoints().doubleValue();
			logger.debug("----reviewLoyaltyLevel--------remainPoints{}:"+remainPoints +",upgradedNeedPoints{}:"+upgradedNeedPoints 
					+",upgradedLoyaltyLevel{}:"+ (upgradedLoyaltyLevel !=null ? upgradedLoyaltyLevel.getName() : null));
			while (true){
		    	if(nextLL==null){
		    	   break;
		    	}
		    	if(remainPoints < upgradedNeedPoints){
		    		break;
		    	}
		    	upgradedLoyaltyLevel = nextLL;
	    		nextLL = loyaltyLevelService.getNextLoyaltyLevel(upgradedLoyaltyLevel);
	    		
	    		upgradedNeedPoints = nextLL.getRequiredSpaPoints().doubleValue();
	    	}
			logger.debug("------reviewLoyaltyLevel--------final upgraded Loyalty level{}:"+ (upgradedLoyaltyLevel !=null ? upgradedLoyaltyLevel.getName() : null));
			if(upgradedLoyaltyLevel !=null){
				//升级
				UserLoyaltyLevelVO userLoyaltyLevelVO=new UserLoyaltyLevelVO();
				userLoyaltyLevelVO.setLoyaltyLevel(upgradedLoyaltyLevel);
				userLoyaltyLevelVO.setJoinDate(now);
				userLoyaltyLevelVO.setUser(user);
				userLoyaltyLevelVO.setSendNotificationEmail(true);
				userLoyaltyLevelVO.setRemarks("upgrade from "+currentLL.getName() +" to " +upgradedLoyaltyLevel.getName());
				
				saveUserLoyaltyLevel(userLoyaltyLevelVO);
				
				spaPointsTransactionService.resetSpaPointsTransaction(upgradedNeedPoints, user.getId(), expiredDate,true);
			}
			
		}
	}
	
	@Override
	public void reviewLoyaltyLevelV2(String action,Long memberId,String prepaidType,Double currentDeleteValue) {
		
		User user = userService.get(memberId);
		Date now = new Date();
		if(!user.isMember()){
			return;
		}
		if(!prepaidType.equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)){
			return;
		}
		
		String fromDate ="";
		try {
			fromDate = DateUtil.dateToString(now, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		LoyaltyLevel upgradedLoyaltyLevel = null;
		LoyaltyLevel currentLL=getCurrentLLOfUser(memberId);
		double remainValue = prepaidDao.sumRemainValue(fromDate, prepaidType, memberId);
		// if delete,the currentDeleteValue is greater than 0,otherwise it will be 0;
		remainValue -= currentDeleteValue.doubleValue();
		
		if(!CommonConstant.ACTION_ADD.equals(action)){
			// if delete or edit, should review loyalty level from diva
			currentLL = loyaltyLevelService.getActiveLoyaltyLevelByRank(1);
			upgradedLoyaltyLevel = currentLL;
		}
		LoyaltyLevel nextLL=loyaltyLevelService.getNextLoyaltyLevel(currentLL);
		//if current loyalty level is 9 carat or black,the next diva level is black diva
		if (currentLL.getRank() ==7 || currentLL.getRank()==6) {
			nextLL = loyaltyLevelService.getActiveLoyaltyLevelByRank(6);//black diva
		}
		
		double upgradedNeedPoints = nextLL.getRequiredSpaPoints().doubleValue();
		logger.debug("----reviewLoyaltyLevelV2--------remainValue{}:"+remainValue +",upgradedNeedPoints{}:"+upgradedNeedPoints 
				+",upgradedLoyaltyLevel{}:"+ (upgradedLoyaltyLevel !=null ? upgradedLoyaltyLevel.getName() : null));
		while (true){
	    	if(nextLL==null){
	    	   break;
	    	}
	    	if(remainValue < upgradedNeedPoints){
	    		break;
	    	}
	    	upgradedLoyaltyLevel = nextLL;
    		nextLL = loyaltyLevelService.getNextLoyaltyLevel(upgradedLoyaltyLevel);
    		if (upgradedLoyaltyLevel.getRank() ==7 || upgradedLoyaltyLevel.getRank()==6) {
				nextLL = loyaltyLevelService.getActiveLoyaltyLevelByRank(6);//black diva
			}
    		upgradedNeedPoints = nextLL.getRequiredSpaPoints().doubleValue();
    	}
		logger.debug("------reviewLoyaltyLevelV2--------final upgraded Loyalty level{}:"+ (upgradedLoyaltyLevel !=null ? upgradedLoyaltyLevel.getName() : null));
		if(upgradedLoyaltyLevel !=null){
			//升级
			UserLoyaltyLevelVO userLoyaltyLevelVO=new UserLoyaltyLevelVO();
			userLoyaltyLevelVO.setLoyaltyLevel(upgradedLoyaltyLevel);
			if(CommonConstant.ACTION_DELETE.equals(action)){
				userLoyaltyLevelVO.setRemarks("downgrade by deleting a transaction which made level upgraded");
			}else if(CommonConstant.ACTION_EDIT.equals(action)){
				userLoyaltyLevelVO.setRemarks("downgrade by eidting remain value of a transaction which made level upgraded");
			}else{
				userLoyaltyLevelVO.setRemarks("upgrade from "+currentLL.getName() +" to " +upgradedLoyaltyLevel.getName());
			}
			userLoyaltyLevelVO.setJoinDate(now);
			userLoyaltyLevelVO.setUser(user);
			userLoyaltyLevelVO.setSendNotificationEmail(true);
			
			
			saveUserLoyaltyLevel(userLoyaltyLevelVO);
		}
	}
}
