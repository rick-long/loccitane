package com.spa.thread;

import com.spa.job.salesforce.marketingCampaign.RunFTCJourneyJob;

import org.joda.time.DateTime;
import org.quartz.JobDataMap;

import java.util.Map;

/**
 *
 * @author Ivy on 2017-11-24
 */
public class RunFTCJourneyThread extends Thread {
	// delay 30 minuts = 1800 secords
	private static final int JOB_DELAY_START =1800;
    private Map<String, Object> parameterMap;

    public static RunFTCJourneyThread getInstance(Map<String, Object> parameterMap) {
    	RunFTCJourneyThread thread = new RunFTCJourneyThread();
        thread.parameterMap = parameterMap;
        return thread;
    }
    @Override
    public void run() {
    	
    	Long userId = (Long) parameterMap.get("userId");
    	Long campaignId = (Long) parameterMap.get("campaignId");
        // job 运行参数
        DateTime dateTime = new DateTime().plusSeconds(JOB_DELAY_START);
        
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userId", userId);
        jobDataMap.put("campaignId", campaignId);
        RunFTCJourneyJob.scheduleJob(dateTime.toDate(), jobDataMap, "FTCJourney");
    }
}
