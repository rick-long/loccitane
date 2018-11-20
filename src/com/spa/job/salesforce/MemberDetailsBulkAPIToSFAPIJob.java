package com.spa.job.salesforce;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.salesforce.ImportTriggerHistory;
import org.spa.service.salesforce.ImportTriggerHistoryService;
import org.spa.service.user.UserService;
import org.spa.serviceImpl.salesforce.ImportTriggerHistoryServiceImpl;
import org.spa.serviceImpl.user.UserServiceImpl;
import org.spa.utils.DateUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.SpringUtil;
import org.spa.vo.toSalesforce.ImportTriggerHistoryVO;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.spa.constant.APIConstant;
import com.spa.thread.ImportDataToSFThread;

public class MemberDetailsBulkAPIToSFAPIJob extends QuartzJobBean{
	
	private static final Logger logger = Logger.getLogger(MemberDetailsBulkAPIToSFAPIJob.class);
	
	private static final Integer limitNumber=1000;
	private static final UserService userService = SpringUtil.getBean(UserServiceImpl.class);
	
	private static final ImportTriggerHistoryService importTriggerHistoryService = SpringUtil.getBean(ImportTriggerHistoryServiceImpl.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		Date fireTime = context.getFireTime();
		logger.debug("---Import member data to SF by API call shedule  -------executeJob-----fire time ---"+ fireTime);
		String triggerTime="";
		try {
			triggerTime = DateUtil.dateToString(fireTime,APIConstant.TIME_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String channel=APIConstant.IMPORT_TRIGGER_CHANNEL_API_CALL;
		
		String operationEnum=(String) context.get("operationEnum");
		if(StringUtils.isEmpty(operationEnum)){
			operationEnum = APIConstant.OPERATION_ENUM_UPSERT;
		}
		logger.debug("---Import member data to SF by API call shedule-------triggerTime---"+triggerTime+"---operationEnum--"+operationEnum);
		
		List<ImportTriggerHistory> importTriggerHistorys = importTriggerHistoryService.checkImportTriggerHistory(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING,APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
		if(importTriggerHistorys !=null && importTriggerHistorys.size()>0){
			return;
		}
		// new a import trigger history
		ImportTriggerHistoryVO importTriggerHistoryVO = new ImportTriggerHistoryVO();
		importTriggerHistoryVO.setChannel(channel);
		importTriggerHistoryVO.setReference(RandomUtil.generateRandomNumberWithTime(APIConstant.IMPORT_TRIGGER_PREFIX));
		importTriggerHistoryVO.setTriggerTime(triggerTime);
		importTriggerHistoryVO.setOperationEnum(operationEnum);
		importTriggerHistoryVO.setModule(APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
		importTriggerHistoryVO.setStatus(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING);
		importTriggerHistoryVO.setCreatedBy(channel);
		importTriggerHistoryService.saveImportTriggerHistory(importTriggerHistoryVO);
		
		logger.debug("---Import member data to SF by API call shedule -- importing start time ---"+System.currentTimeMillis());
		Map<String,String> returnFiles = userService.getMemberDetailsImortToSFByCSV(limitNumber);
		
		if(returnFiles !=null && returnFiles.size()>0){
			String accountFile = returnFiles.get(APIConstant.SF_OBJECT_ACCOUNT);
			String contactFile = returnFiles.get(APIConstant.SF_OBJECT_CONTACT);
			
			ImportDataToSFThread.getInstance(accountFile, contactFile, limitNumber,operationEnum,false).start();
		}
		//change status of import trigger
		importTriggerHistoryService.updateImportTriggerHistoryStatus(importTriggerHistoryVO.getReference(), APIConstant.IMPORT_TRIGGER_STATUS_COMPLETED);
		
		logger.debug("---Import member data to SF by API call shedule-- importing end time ---"+System.currentTimeMillis());
	}
}