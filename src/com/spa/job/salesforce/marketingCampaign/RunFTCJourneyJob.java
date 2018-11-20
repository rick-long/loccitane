package com.spa.job.salesforce.marketingCampaign;


import java.util.Date;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.service.order.PurchaseOrderService;
import org.spa.utils.RandomUtil;
import org.spa.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.spa.job.OpenSessionQuartzJobBean;
/**
*
* @author Ivy on 2017-11-24
*/
public class RunFTCJourneyJob extends OpenSessionQuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    
    public static final StdScheduler SCHEDULER = (StdScheduler) SpringUtil.getBean("baseQuartzScheduler");
    
    public static void scheduleJob(Date startTime, JobDataMap jobDataMap, String group) {
        String identity = RandomUtil.generateRandomNumberWithTime("FTC-");
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(identity, group).startAt(startTime);
        JobDetail jobDetail = JobBuilder.newJob(RunFTCJourneyJob.class).withIdentity(identity, group).setJobData(jobDataMap).build();
        try {
            SCHEDULER.scheduleJob(jobDetail, triggerBuilder.build());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob(JobExecutionContext context) throws JobExecutionException {
    	System.out.println("RunFTCJourneyJob----executeJob-----"+new Date());
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        
        Long userId = (Long) jobDataMap.get("userId");
        Long campaignId=(Long) jobDataMap.get("campaignId");
        purchaseOrderService.runFTCJourney(userId,campaignId);
        
    }
}