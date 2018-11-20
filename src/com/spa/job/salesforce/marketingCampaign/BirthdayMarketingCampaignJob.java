package com.spa.job.salesforce.marketingCampaign;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.salesforce.MarketingCampaign;
import org.spa.model.salesforce.UserMarketingCampaignTransaction;
import org.spa.model.user.User;
import org.spa.service.salesforce.MarketingCampaignService;
import org.spa.service.salesforce.UserMarketingCampaignTransactionService;
import org.spa.service.user.UserService;
import org.spa.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.spa.job.OpenSessionQuartzJobBean;

public class BirthdayMarketingCampaignJob extends OpenSessionQuartzJobBean{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMarketingCampaignTransactionService userMarketingCampaignTransactionService;
	
	@Autowired
	private MarketingCampaignService marketingCampaignService;
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println(" run ----BirthdayMarketingCampaignJob---");
		Date currentDate = new Date();
		
		String loginUser ="BirthdayMarketingCampaignJob";
		// find all transactions of this campaign
		List<UserMarketingCampaignTransaction> umps = userMarketingCampaignTransactionService.getUserMarketingCampaignTransactions(null,5l,true);
		
		// set transactions is active to 0
		// update "last updated" of member who have transactions
		if(umps !=null && umps.size()>0){
			System.out.println(" run ----BirthdayMarketingCampaignJob--- birthday MarketingCampaignTransaction size ----"+umps.size());
			for(UserMarketingCampaignTransaction ump : umps){
				userMarketingCampaignTransactionService.delete(ump.getId());
				User user = ump.getUser();
				user.setLastUpdated(currentDate);
				user.setLastUpdatedBy(loginUser);
				userService.saveOrUpdate(user);
			}
		}
		
		try {
			MarketingCampaign marketingCampaign = marketingCampaignService.get(5l);
			
			Date nextMonthDate = DateUtil.getLastDayAfterNumOfMonths(currentDate, 1);
			String nextMonthStr = DateUtil.convertDateToString("M", nextMonthDate);
			Integer nextMonth = new Integer(nextMonthStr);
			System.out.println("---nextMonth --"+nextMonth);
			// find out members who will be birthday next month
			List<User> users = userService.getUsersByBirthdayMonth(nextMonth);
			if(users !=null && users.size()>0){
				System.out.println(" run ----BirthdayMarketingCampaignJob--- birthday users size ----"+users.size());
				for(User user : users){
					// save birthday campaign code in transaction
					UserMarketingCampaignTransaction umct = new UserMarketingCampaignTransaction();
					umct.setCreated(currentDate);
					umct.setCreatedBy(loginUser);
					umct.setIsActive(true);
					umct.setUser(user);
					umct.setMarketingCampaign(marketingCampaign);
					userMarketingCampaignTransactionService.saveOrUpdate(umct);
					
					// update "last updated" of member who have transactions
					user.setLastUpdated(currentDate);
					user.setLastUpdatedBy(loginUser);
					userService.saveOrUpdate(user);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(" run ----BirthdayMarketingCampaignJob---end--");
	}
}