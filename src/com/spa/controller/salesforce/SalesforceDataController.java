package com.spa.controller.salesforce;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.spa.model.salesforce.ImportTriggerHistory;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.NumberUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.ServletUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.toSalesforce.ImportTriggerHistoryVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sforce.async.AsyncApiException;
import com.sforce.ws.ConnectionException;
import com.spa.constant.APIConstant;
import com.spa.controller.BaseController;
import com.spa.salesforce.BulkAPI;
import com.spa.thread.ImportDataToSFThread;

/**
 * Created by Ivy on 2017/08/17.
 */
@Controller
@RequestMapping("salesforceData")
public class SalesforceDataController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(SalesforceDataController.class);
	
	@RequestMapping("memberDetailsImport")
	public String memberDetailsImport(Model model) {
		
		return "salesforceData/memberDetailsImport";
	}
	
	@RequestMapping("resetDB")
	@ResponseBody
	public AjaxForm resetDB(Model model){
		try {
			userService.updateUserSetLastModifier("1970-01-01", false);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			errorAjaxForm.addErrorFields(new ErrorField("DB error", e.getMessage()));
			return errorAjaxForm;
		}
		
		return AjaxFormHelper.success("Update successfully!");
	}
	
	@RequestMapping("importData")
	@ResponseBody
	public AjaxForm importData(Model model,String putNum) {
		Integer putNumIn = 0;
		if(!NumberUtil.isNumeric(putNum)){
			 return AjaxFormHelper.error().addAlertError("Input number should be a number");
		}else{
			putNumIn =Integer.parseInt(putNum);
			if(putNumIn.intValue() ==0){
				 return AjaxFormHelper.error().addAlertError("Input number can't be 0.");
			}else if(putNumIn.intValue() >3000){
				 return AjaxFormHelper.error().addAlertError("Input number can't be more than 3000.");
			}
		}
		
//		System.out.println("Member details import to sf by Bulk API :: start time (current time millis) ---"+System.currentTimeMillis());
		logger.debug("Member details import to sf by Bulk API :: start time (current time millis) ---"+System.currentTimeMillis());
		Map<String,String> returnFiles = userService.getMemberDetailsImortToSFByCSV(putNumIn);
		String retrunSize = "0";  
		if(returnFiles !=null && returnFiles.size()>0){
			retrunSize = returnFiles.get("returnSize");
			BulkAPI api =new BulkAPI();
			try {
				String accountFile = returnFiles.get(APIConstant.SF_OBJECT_ACCOUNT);
				api.runSample(APIConstant.SF_OBJECT_ACCOUNT, APIConstant.SF_USERNAME, APIConstant.SF_PASSWORD_TOKEN, APIConstant.IMPORT_FILE_PATH + accountFile,APIConstant.OPERATION_ENUM_UPSERT);
				
				String contactFile = returnFiles.get(APIConstant.SF_OBJECT_CONTACT);
				api.runSample(APIConstant.SF_OBJECT_CONTACT, APIConstant.SF_USERNAME, APIConstant.SF_PASSWORD_TOKEN, APIConstant.IMPORT_FILE_PATH + contactFile,APIConstant.OPERATION_ENUM_UPSERT);
				
				//update last modifier
				List<Object[]> searchResults = userService.getMemberDetailsExportToCSVForSF(putNumIn);
		        if(searchResults !=null && searchResults.size()>0){
		        	for(Object[] obj : searchResults){
			        	Long id =(Long) obj[0];
			        	User user = userService.get(id);
			        	user.setLastModifier(new Date());
			        	userService.saveOrUpdate(user);
			        }
		        }
		        
			} catch (AsyncApiException | ConnectionException | IOException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("Member details import to sf by Bulk API :: end time (current time millis) ---"+System.currentTimeMillis());
		logger.debug("Member details import to sf by Bulk API :: end time (current time millis) ---"+System.currentTimeMillis());
		return AjaxFormHelper.error().addAlertError("Import Process Completed Successfully.("+retrunSize+" records submitted to Salesforce to proceed)");
	}
	
	@RequestMapping("importDataV2")
	@ResponseBody
	public AjaxForm importDataV2(Model model,String putNum,String operationEnum) {
		
		List<ImportTriggerHistory> importTriggerHistoryProcessing = importTriggerHistoryService.checkImportTriggerHistory(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING,APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
		if(importTriggerHistoryProcessing !=null && importTriggerHistoryProcessing.size()>0){
			ImportTriggerHistory importTriggerHistory =importTriggerHistoryProcessing.get(0);
			return AjaxFormHelper.error().addAlertError("There has a import task now.The processing time is from "+importTriggerHistory.getTriggerTime());
		}
		
		Integer putNumIn = 0;
		if(!NumberUtil.isNumeric(putNum)){
			 return AjaxFormHelper.error().addAlertError("Input number should be a number");
		}else{
			putNumIn =Integer.parseInt(putNum);
			if(putNumIn.intValue() ==0){
				 return AjaxFormHelper.error().addAlertError("Input number can't be 0.");
			}else{
				if(operationEnum.equals(APIConstant.OPERATION_ENUM_UPSERT)){
//					if(putNumIn.intValue() >3000){
//						 return AjaxFormHelper.error().addAlertError("Input number can't be more than 3000.");
//					}
				}
				
			}
		}
		
		// new a import trigger history
		ImportTriggerHistoryVO importTriggerHistoryVO = new ImportTriggerHistoryVO();
		importTriggerHistoryVO.setChannel(APIConstant.IMPORT_TRIGGER_CHANNEL_MANUAL);
		importTriggerHistoryVO.setReference(RandomUtil.generateRandomNumberWithTime(APIConstant.IMPORT_TRIGGER_PREFIX));
		try {
			importTriggerHistoryVO.setTriggerTime(DateUtil.dateToString(new Date(),APIConstant.TIME_FORMAT ));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		importTriggerHistoryVO.setOperationEnum(operationEnum);
		importTriggerHistoryVO.setModule(APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
		importTriggerHistoryVO.setStatus(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING);
		importTriggerHistoryVO.setCreatedBy(WebThreadLocal.getUser().getUsername());
		importTriggerHistoryService.saveImportTriggerHistory(importTriggerHistoryVO);
				
//		System.out.println("Member details import to sf by Bulk API :: start time (current time millis) ---"+System.currentTimeMillis());
		logger.debug("Member details import to sf by Bulk API :: start time (current time millis) ---"+System.currentTimeMillis());
		Map<String,String> returnFiles = userService.getMemberDetailsImortToSFByCSV(putNumIn);
		String retrunSize = "0";  
		if(returnFiles !=null && returnFiles.size()>0){
			retrunSize = returnFiles.get("returnSize");
			String accountFile = returnFiles.get(APIConstant.SF_OBJECT_ACCOUNT);
			String contactFile = returnFiles.get(APIConstant.SF_OBJECT_CONTACT);
			
			ImportDataToSFThread.getInstance(accountFile, contactFile, putNumIn,operationEnum,false).start();
			
		}
		//change status of import trigger
		importTriggerHistoryService.updateImportTriggerHistoryStatus(importTriggerHistoryVO.getReference(), APIConstant.IMPORT_TRIGGER_STATUS_COMPLETED);
		
//		System.out.println("Member details import to sf by Bulk API :: end time (current time millis) ---"+System.currentTimeMillis());
		logger.debug("Member details import to sf by Bulk API :: end time (current time millis) ---"+System.currentTimeMillis());
		return AjaxFormHelper.error().addAlertError("Import Process Completed Successfully.("+retrunSize+" records submitted to Salesforce to proceed)");
	}
	
	@RequestMapping("downloadLog")
	public void downloadLog(HttpServletRequest request,HttpServletResponse response) {
	    File downloadFile = new File("/home/ibsadmin/salesforce/log/salesforce_data_import.log");
	    ServletUtil.download(downloadFile, "salesforce_data_import.log", response);
	}
}
