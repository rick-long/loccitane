package com.spa.job.salesforce.marketingCampaign.prepaid;

import com.spa.job.OpenSessionQuartzJobBean;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.salesforce.MarketingCampaign;
import org.spa.model.salesforce.UserMarketingCampaignTransaction;
import org.spa.model.user.User;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.service.salesforce.MarketingCampaignService;
import org.spa.service.salesforce.UserMarketingCampaignTransactionService;
import org.spa.service.user.UserService;
import org.spa.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CashPackageUponUpgradeJourneyJob extends OpenSessionQuartzJobBean {
	
    private static final String marketing_campaign_code="7";
    private static final String marketing_campaign_subCode="cp4";
    
    @Autowired
    private UserMarketingCampaignTransactionService userMarketingCampaignTransactionService;
    
    @Autowired
    private UserLoyaltyLevelService userLoyaltyLevelService;
    
    @Autowired
    private MarketingCampaignService marketingCampaignService;
    @Autowired
    private UserService userService;
    @Override
    protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
    	 String loginUser ="CashPackageUponUpgradeJourneyJob";
         Date currentDate = new Date();
         
         String today="2018-03-21";
         currentDate= DateUtil.stringToDate(today, "yyyy-MM-dd");
         
         List<Long> prevIds =new ArrayList<Long>();
         List<UserMarketingCampaignTransaction> prevUmps=userMarketingCampaignTransactionService.getUserMarketingCampaignTransactions(null, marketing_campaign_code, marketing_campaign_subCode);
         MarketingCampaign marketingCampaign=marketingCampaignService.getMarketingCampaignByCodeAndSubCode(marketing_campaign_code, marketing_campaign_subCode);
         
         DetachedCriteria dc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
 		 dc.add(Restrictions.eq("isActive", true));
 		 dc.add(Restrictions.ge("joinDate", DateUtil.getFirstMinuts(currentDate)));
 		 dc.add(Restrictions.le("joinDate", DateUtil.getLastMinuts(currentDate)));
 		 List<UserLoyaltyLevel> ullList=userLoyaltyLevelService.list(dc);
 		 Set<Long>userIds =new HashSet<Long>();
 		 for(UserLoyaltyLevel ull : ullList){
 			userIds.add(ull.getUser().getId());
 		 }
 		 
 		 User preUser =null;
         if(prevUmps !=null && prevUmps.size()>0){
            System.out.println(" run ----CashPackageUponUpgradeJourneyJob---  prev id size ----"+prevUmps.size());
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
         	System.out.println(" run ----CashPackageUponUpgradeJourneyJob---  postIds id size ----"+postIds.size());
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
         System.out.println(" run ----TreatmentPackageExpiryJourney0UnitRemainJob---end--");

    }
}