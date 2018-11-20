package com.spa.job;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.service.loyalty.LoyaltyLevelService;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class DownloadLoyaltyLevelJob extends OpenSessionQuartzJobBean{
	
	
	public DownloadLoyaltyLevelJob() {
	}

	@Autowired
	private UserLoyaltyLevelService userLoyaltyLevelService;
	@Autowired
	private LoyaltyLevelService loyaltyLevelService;
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println("---- DownloadLoyaltyLevelJob---start ----");
		Date expiryDate = new Date();
		//3年后过期
		Date newExpiryDate = null;
		try {
			newExpiryDate = DateUtil.getLastMinutsOfLastDayAfterNumOfMonths(expiryDate, 36);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		LoyaltyLevel newLl= loyaltyLevelService.getActiveLoyaltyLevelByRank(1);
		
		DetachedCriteria dc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
		dc.add(Restrictions.le("expiryDate", expiryDate));
		dc.add(Restrictions.eq("isActive", true));
		
		List<UserLoyaltyLevel> userLLList = userLoyaltyLevelService.list(dc);
		if(userLLList !=null && userLLList.size()>0){
			for(UserLoyaltyLevel ull : userLLList){
				ull.setIsActive(false);
				userLoyaltyLevelService.saveOrUpdate(ull);
				
				UserLoyaltyLevel newUll = new UserLoyaltyLevel();
				newUll.setUser(ull.getUser());
				newUll.setIsActive(true);
				newUll.setExpiryDate(newExpiryDate);
				newUll.setJoinDate(expiryDate);
				newUll.setLoyaltyLevel(newLl);
				newUll.setCreated(expiryDate);
				newUll.setLastUpdated(expiryDate);
				newUll.setCreatedBy("By System Auto");
				newUll.setLastUpdatedBy("By System Auto");
				newUll.setRemarks("Downgrade from "+ull.getLoyaltyLevel().getName()+" to Diva level!");
				
				userLoyaltyLevelService.saveOrUpdate(newUll);
			}
		}
	}

}
