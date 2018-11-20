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
import org.spa.utils.DateUtil;
import org.spa.utils.RandomUtil;
import org.spa.vo.toSalesforce.ImportTriggerHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.spa.constant.APIConstant;
import com.spa.job.OpenSessionQuartzJobBean;
import com.spa.thread.ImportDataToSFThread;

public class MemberDetailsBulkAPIToSFJob extends OpenSessionQuartzJobBean{
	
	private static final Logger logger = Logger.getLogger(MemberDetailsBulkAPIToSFJob.class);
	
	private static final Integer limitNumber=1000;
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ImportTriggerHistoryService importTriggerHistoryService;
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
		Date fireTime = ctx.getFireTime();
		logger.debug("---Import member data to SF by SSL2 system shedule -------executeJob-----fire time ---"+ fireTime);
		String triggerTime="";
		try {
			triggerTime = DateUtil.dateToString(fireTime,APIConstant.TIME_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ImportTriggerHistory> importTriggerHistorys = importTriggerHistoryService.checkImportTriggerHistory(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING,APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
		if(importTriggerHistorys !=null && importTriggerHistorys.size()>0){
			return;
		}
		String operationEnum = APIConstant.OPERATION_ENUM_UPSERT;
		// new a import trigger history
		ImportTriggerHistoryVO importTriggerHistoryVO = new ImportTriggerHistoryVO();
		importTriggerHistoryVO.setChannel(APIConstant.IMPORT_TRIGGER_CHANNEL_AUTO);
		importTriggerHistoryVO.setReference(RandomUtil.generateRandomNumberWithTime(APIConstant.IMPORT_TRIGGER_PREFIX));
		importTriggerHistoryVO.setTriggerTime(triggerTime);
		importTriggerHistoryVO.setOperationEnum(operationEnum);
		importTriggerHistoryVO.setModule(APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
		importTriggerHistoryVO.setStatus(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING);
		importTriggerHistoryVO.setCreatedBy(APIConstant.IMPORT_TRIGGER_CHANNEL_API_CALL);
		importTriggerHistoryService.saveImportTriggerHistory(importTriggerHistoryVO);
		
		try {
			logger.debug(" -- Import member data to SF by SSL2 system shedule  importing start time ---"+DateUtil.dateToString(new Date(),APIConstant.TIME_FORMAT));
			
			Map<String,String> returnFiles = userService.getMemberDetailsImortToSFByCSV(limitNumber);
			
			if(returnFiles !=null && returnFiles.size()>0){
				String accountFile = returnFiles.get(APIConstant.SF_OBJECT_ACCOUNT);
				String contactFile = returnFiles.get(APIConstant.SF_OBJECT_CONTACT);
				
				ImportDataToSFThread.getInstance(accountFile, contactFile, limitNumber,operationEnum,false).start();
			}
			//change status of import trigger
			importTriggerHistoryService.updateImportTriggerHistoryStatus(importTriggerHistoryVO.getReference(), APIConstant.IMPORT_TRIGGER_STATUS_COMPLETED);
			
			logger.debug(" -- Import member data to SF by SSL2 system shedule  importing end time ---"+DateUtil.dateToString(new Date(),APIConstant.TIME_FORMAT));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}