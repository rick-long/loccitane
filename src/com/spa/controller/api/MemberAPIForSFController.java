package com.spa.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.spa.model.user.User;
import org.spa.utils.CronUtil;
import org.spa.utils.DateUtil;
import org.spa.utils.EncryptUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.Results;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spa.constant.APIConstant;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.job.salesforce.MemberDetailsBulkAPIToSFAPIJob;


@RestController
@RequestMapping("api")
public class MemberAPIForSFController extends BaseController{

	private static final Logger logger = Logger.getLogger(MemberAPIForSFController.class);
	
	@RequestMapping("hello")
	public String test() {
		return "Hello";
	}
	
	@RequestMapping(value = "member/updateDetails",method = {RequestMethod.POST})
	@ResponseBody
	public Results updateMemberDetails(@RequestBody MemberAPIVO memberAPIVO) {
		return userService.updateMemberDetailsByAPI(memberAPIVO);
	}

	@RequestMapping(value = "member/import/schedule",method = {RequestMethod.POST})
	@ResponseBody
	public Results schedule(@RequestBody MemberAPIVO memberAPIVO,HttpServletRequest request) {
		logger.debug("--salesforce import schedule api call ---start ---"+ new Date());
		Results results = Results.getInstance();
		
		String username = memberAPIVO.getUsername();
		String password = memberAPIVO.getPassword();
		User loginer = userService.getUserByUsername(username,CommonConstant.USER_ACCOUNT_TYPE_STAFF);
		if (loginer == null || !loginer.getPassword().equals(EncryptUtil.SHA1(password))) {
			return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
		}
		String triggerTime = memberAPIVO.getTriggerTime();
		if (StringUtils.isEmpty(triggerTime)) {
			return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Fire time is empty!");
		}
		Matcher matcher = DateUtil.pattern.matcher(triggerTime);
        boolean isMatched = matcher.matches();
		if(!isMatched) {
			return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Fire time is not a date type!");
		}
		Date fireDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(APIConstant.TIME_FORMAT);                
		try {
			 fireDate= sdf.parse(triggerTime);
		} catch (ParseException e) {
			e.printStackTrace();
			 return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", e.getMessage());
		}
		
		Date  now =new Date();
		if(fireDate.before(now)){
			return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Fire time is before now,the shechule will not fired! Please re-call.");
		}
		// cron expression
		String cronExpression ="";
		CronUtil calHelper = new CronUtil (DateUtil.stringToDate(triggerTime, APIConstant.TIME_FORMAT));
		cronExpression = calHelper.getSeconds() + " " +
		calHelper.getMins() +  " " +
		calHelper.getHours() + " " +
		calHelper.getDaysOfMonth() +  " " +
		calHelper.getMonths() +  " " +
		calHelper.getDaysOfWeek() +  " "+
		calHelper.getYears();
		
		logger.debug("--salesforce import schedule api call ---triggerTime ---"+triggerTime+"----cronExpression---"+cronExpression);
		
		//schedule it
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
    	
		try {
			scheduler = schedulerFactory.getScheduler();
			 // create job
	    	JobDetail job = JobBuilder.newJob(MemberDetailsBulkAPIToSFAPIJob.class)
					.withIdentity(RandomUtil.generateRandomNumberWithTime("SF_IMPORT_JOB_"),"DEFAULT")
					.build();
	    	// create trigger
	    	Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(RandomUtil.generateRandomNumberWithTime("SF_IMPORT_TRIGGER_"), "DEFAULT")
					.withSchedule(
							CronScheduleBuilder.cronSchedule(cronExpression))
//					.startAt(DateUtil.stringToDate(triggerTime, APIConstant.TIME_FORMAT))
					.build();
	    	//put job and trigger in scheduler
	    	scheduler.scheduleJob(job, trigger);
	    	//start scheduler
            scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		logger.debug("--salesforce import schedule api call ---end ---"+ new Date());
    	return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "Successfully!");
	}
}
