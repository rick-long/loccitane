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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TreatmentPackageExpiryJourney60DaysJob extends OpenSessionQuartzJobBean {

    private static final Long marketing_campaign_id = 8L;//#7tpe60
    private static final Integer expiry_days=60;
    
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
        String loginUser ="TreatmentPackageExpiryJourney60DaysJob";
        Date currentDate = new Date();
        List<Long> prevIds =new ArrayList<Long>();
        List<UserMarketingCampaignTransaction> prevUmps=userMarketingCampaignTransactionService.getUserMarketingCampaignTransactions(null,marketing_campaign_id,true);
        MarketingCampaign marketingCampaign=marketingCampaignService.get(marketing_campaign_id);
        
        Set<Long>userIds=prepaidService.getUserIdsForPackageExpiryJourney(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE,expiry_days,null);
        User preUser =null;
        if(prevUmps !=null && prevUmps.size()>0){
            System.out.println(" run ----TreatmentPackageExpiryJourney60DaysJob---  prev id size ----"+prevUmps.size());
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
                prevIds.add(preUser.getId());
            }
        }

        List<Long> postIds = userService.getMemberByNotInIds(prevIds);
        if(postIds !=null && postIds.size()>0){
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
        System.out.println(" run ----TreatmentPackageExpiryJourney60DaysJob---end--");

    }
}