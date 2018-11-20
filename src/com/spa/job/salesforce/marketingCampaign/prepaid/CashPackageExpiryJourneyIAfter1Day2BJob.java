package com.spa.job.salesforce.marketingCampaign.prepaid;

import com.spa.constant.CommonConstant;
import com.spa.job.OpenSessionQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.salesforce.MarketingCampaign;
import org.spa.model.salesforce.UserMarketingCampaignTransaction;
import org.spa.model.user.User;
import org.spa.service.prepaid.PrepaidService;
import org.spa.service.salesforce.MarketingCampaignService;
import org.spa.service.salesforce.UserMarketingCampaignTransactionService;
import org.spa.service.user.UserService;
import org.spa.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CashPackageExpiryJourneyIAfter1Day2BJob extends OpenSessionQuartzJobBean {
	
    private static final String marketing_campaign_code="7";
    private static final String marketing_campaign_subCode="cp2b";// has cash remaining
    private static final int expiry_days =1;
    
    @Autowired
    private UserMarketingCampaignTransactionService userMarketingCampaignTransactionService;
    @Autowired
    private PrepaidService prepaidService;
    @Autowired
    private MarketingCampaignService marketingCampaignService;
    @Autowired
    private UserService userService;
    @Override
    protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
        String loginUser ="CashPackageExpiryJourneyIAfter1Day2BJob";
        Date currentDate = new Date();
        List<Long> prevIds =new ArrayList<Long>();
        List<UserMarketingCampaignTransaction> prevUmps=userMarketingCampaignTransactionService.getUserMarketingCampaignTransactions(null, marketing_campaign_code, marketing_campaign_subCode);
        MarketingCampaign marketingCampaign=marketingCampaignService.getMarketingCampaignByCodeAndSubCode(marketing_campaign_code, marketing_campaign_subCode);
        
        Date toDate =new Date();
        Calendar fromCalendar=new GregorianCalendar();
        fromCalendar.setTime(toDate);
        fromCalendar.add(Calendar.DATE,-expiry_days);
        Set<Long> userIds =new HashSet<Long>();;
		try {
			userIds = prepaidService.getMemberIdsByExpiryPrepaid(DateUtil.dateToString(fromCalendar.getTime(), "yyyy-MM-dd"), DateUtil.dateToString(toDate, "yyyy-MM-dd"), CommonConstant.PREPAID_TYPE_CASH_PACKAGE,true);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        User preUser =null;
        if(prevUmps !=null && prevUmps.size()>0){
            System.out.println(" run ----CashPackageExpiryJourneyIAfter1Day2BJob---  prev id size ----"+prevUmps.size());
            for(UserMarketingCampaignTransaction prev : prevUmps){
                preUser= prev.getUser();
                if(!userIds.contains(preUser.getId())){
                    //remove code
                    userMarketingCampaignTransactionService.delete(prev.getId());
                    //set user last update date
                    preUser.setLastUpdated(currentDate);
                    preUser.setLastUpdatedBy(loginUser);
                    userService.saveOrUpdate(preUser);
                }
                // keep prev id
                prevIds.add(preUser.getId());
            }
        }

        List<Long> postIds = userService.getMemberByNotInIds(prevIds);
        if(postIds !=null && postIds.size()>0){
        	System.out.println(" run ----CashPackageExpiryJourneyIAfter1Day2BJob---  postIds id size ----"+postIds.size());
            User postUser =null;
            for(Long postId : postIds){
            	
                if(userIds.contains(postId)){
                    postUser = userService.get(postId);
                    UserMarketingCampaignTransaction newRecord = new UserMarketingCampaignTransaction();
                    newRecord.setCreated(currentDate);
                    newRecord.setCreatedBy(loginUser);
                    newRecord.setIsActive(true);
                    newRecord.setUser(postUser);
                    newRecord.setMarketingCampaign(marketingCampaign);
                    userMarketingCampaignTransactionService.saveOrUpdate(newRecord);
                    
                    postUser.setLastUpdated(currentDate);
                    postUser.setLastUpdatedBy(loginUser);
                    userService.saveOrUpdate(postUser);
                }

            }
        }
        System.out.println(" run ----CashPackageExpiryJourneyIAfter1Day2BJob---end--");

    }
}