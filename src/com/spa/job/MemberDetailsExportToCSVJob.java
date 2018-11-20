package com.spa.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberDetailsExportToCSVJob extends OpenSessionQuartzJobBean{

	@Autowired
	private UserService userService;
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println("---MemberDetailsExportToCSVJob------job exute start time "+System.currentTimeMillis());
		userService.getMemberDetailsImortToSFByCSV(1000);
		System.out.println("---MemberDetailsExportToCSVJob------job exute end time "+System.currentTimeMillis());
	}
	
}