package com.spa.job.salesforce.marketingCampaign.prepaid;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PackageEngagementJourneyBJob extends OpenSessionQuartzJobBean {

	private static final String marketing_campaign_code="16";
	private static final String type="B";
	
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
        String loginUser ="PackageEngagementJourneyBJob";
        Date currentDate = new Date();
        List<Long> prevIds =new ArrayList<Long>();
        List<UserMarketingCampaignTransaction> prevUmps=userMarketingCampaignTransactionService.getTransactionsBySubCodeFuzzySearch(marketing_campaign_code, null);
        MarketingCampaign marketingCampaign=null;
        User preUser =null;
        if(prevUmps !=null && prevUmps.size()>0){
            System.out.println(" run ----PackageEngagementJourneyBJob---  prev id size ----"+prevUmps.size());
            for(UserMarketingCampaignTransaction prev : prevUmps){
                preUser= prev.getUser();
            	int days = DateUtil.differentDays(preUser.getBackUpDate(), currentDate);
            	String subCode ="";
            	if(days >= 0 && days <= 30){
            		//check next prev
            		subCode="1";
            		marketingCampaign=marketingCampaignService.getMarketingCampaignByCodeAndSubCode(marketing_campaign_code, "B1");
            	}else if(days > 30 && days <= 60){
            		//check A2
            		subCode="2";
            		marketingCampaign=marketingCampaignService.getMarketingCampaignByCodeAndSubCode(marketing_campaign_code, "B2");
            	}else if(days > 60 && days <= 90){
            		// check A3
            		subCode="3";
            		marketingCampaign=marketingCampaignService.getMarketingCampaignByCodeAndSubCode(marketing_campaign_code, "B3");
                    
            	}else if(days > 90){
            		//move A3 and update member and move value of back up date.
                    userMarketingCampaignTransactionService.delete(prev.getId());
                    preUser.setBackUpDate(null);
                    //set user last update date
                    preUser.setLastUpdated(currentDate);
                    preUser.setLastUpdatedBy(loginUser);
                    userService.saveOrUpdate(preUser);
                    prevIds.add(preUser.getId());
                    continue;
            	}
            	Set<Long>userIds=prepaidService.getUserIdsForPackageEngagementJourney(preUser.getBackUpDate(),subCode,type);
            	
        		if(userIds==null || (userIds !=null && !userIds.contains(preUser.getId()))){
        			//remove code
                    userMarketingCampaignTransactionService.delete(prev.getId());
                    preUser.setBackUpDate(null);
                    //set user last update date
                    preUser.setLastUpdated(currentDate);
                    preUser.setLastUpdatedBy(loginUser);
                    userService.saveOrUpdate(preUser);
                    prevIds.add(preUser.getId());
                    
        		}else{
        			if(!prev.getMarketingCampaign().getSubCode().equals(subCode)){
        				prev.setMarketingCampaign(marketingCampaign);
        				prev.setCreated(currentDate);
        				prev.setCreatedBy(loginUser);
            			userMarketingCampaignTransactionService.saveOrUpdate(prev);
            			//set user last update date
                        preUser.setLastUpdated(currentDate);
                        preUser.setLastUpdatedBy(loginUser);
                        userService.saveOrUpdate(preUser);
                        prevIds.add(preUser.getId());
        			}
        		}
                
            }
        }

        List<Long> postIds = userService.getMemberByNotInIds(prevIds);
        if(postIds !=null && postIds.size()>0){
            User postUser =null;
            marketingCampaign=marketingCampaignService.getMarketingCampaignByCodeAndSubCode(marketing_campaign_code, "B1");
            Set<Long>userIds=prepaidService.getUserIdsForPackageEngagementJourney(currentDate,"1",type);
            if(userIds !=null && userIds.size()>0){
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
                        
                        postUser.setBackUpDate(currentDate);
                        postUser.setLastUpdated(currentDate);
                        postUser.setLastUpdatedBy(loginUser);
                        userService.saveOrUpdate(postUser);
                    }
                }
            }
        }
        System.out.println(" run ----PackageEngagementJourneyBJob---end--");

    }
}