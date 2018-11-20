package com.spa.controller.salesforce;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.NumberUtil;
import org.spa.utils.ServletUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spa.constant.APIConstant;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.thread.ImportDataToSFThread;

/**
 * Created by Ivy on 2017/08/17.
 */
@Controller
@RequestMapping("sfDataDEMO")
public class SalesforceDataDEMOController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(SalesforceDataDEMOController.class);
	
	@RequestMapping("memberDetailsImport")
	public String memberDetailsImport(Model model) {
		return "salesforceData/demo/memberDetailsImport";
	}
	@RequestMapping("memberDetailsDataList")
	public String listData(Model model) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("enabled", Boolean.TRUE));
		detachedCriteria.add(Restrictions.like("username", CommonConstant.SF_DEMO_DATA_PREFIX, MatchMode.ANYWHERE));
		detachedCriteria.add(Restrictions.eq("accountType",CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
		detachedCriteria.addOrder(Order.desc("username"));
		
		List<User> demoUsers = userService.list(detachedCriteria);
		model.addAttribute("demoUsers", demoUsers);
		
		return "salesforceData/demo/memberDetailsDataList";
	}
	
	@RequestMapping("resetDB")
	@ResponseBody
	public AjaxForm resetDB(Model model,String updateDate){
		try {
			if(StringUtils.isBlank(updateDate)){
				updateDate ="1970-01-01";
			}
			userService.updateUserSetLastModifier(updateDate, true);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			errorAjaxForm.addErrorFields(new ErrorField("DB error", e.getMessage()));
			return errorAjaxForm;
		}
		
		return AjaxFormHelper.success("Update successfully!");
	}
	
	
	@RequestMapping("importDataV2")
	@ResponseBody
	public AjaxForm importDataV2(Model model,String putNum,String isDEMO) {
		
//		List<ImportTriggerHistory> importTriggerHistoryProcessing = importTriggerHistoryService.checkImportTriggerHistory(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING,APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
//		if(importTriggerHistoryProcessing !=null && importTriggerHistoryProcessing.size()>0){
//			ImportTriggerHistory importTriggerHistory =importTriggerHistoryProcessing.get(0);
//			return AjaxFormHelper.error().addAlertError("There has a import task now.The processing time is from "+importTriggerHistory.getTriggerTime());
//		}
		Boolean isDemo = Boolean.TRUE;
		if(StringUtils.isNotBlank(isDEMO)){
			isDemo =  Boolean.parseBoolean(isDEMO);
		}
		Integer putNumIn = 0;
		if(!NumberUtil.isNumeric(putNum)){
			 return AjaxFormHelper.error().addAlertError("Input number should be a number");
		}else{
			putNumIn =Integer.parseInt(putNum);
			if(putNumIn.intValue() ==0){
				 return AjaxFormHelper.error().addAlertError("Input number can't be 0.");
			}else if(putNumIn.intValue() >5){
				 return AjaxFormHelper.error().addAlertError("Input number can't be more than 5.");
			}
		}
		
		// new a import trigger history
//		ImportTriggerHistoryVO importTriggerHistoryVO = new ImportTriggerHistoryVO();
//		importTriggerHistoryVO.setChannel(APIConstant.IMPORT_TRIGGER_CHANNEL_MANUAL);
//		importTriggerHistoryVO.setReference(RandomUtil.generateRandomNumberWithTime(APIConstant.IMPORT_TRIGGER_PREFIX));
//		try {
//			importTriggerHistoryVO.setTriggerTime(DateUtil.dateToString(new Date(),APIConstant.TIME_FORMAT ));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		importTriggerHistoryVO.setOperationEnum(APIConstant.OPERATION_ENUM_UPSERT);
//		importTriggerHistoryVO.setModule(APIConstant.IMPORT_TRIGGER_MODULE_MEMBER);
//		importTriggerHistoryVO.setStatus(APIConstant.IMPORT_TRIGGER_STATUS_PROCESSING);
//		importTriggerHistoryVO.setCreatedBy(WebThreadLocal.getUser().getUsername());
//		importTriggerHistoryService.saveImportTriggerHistory(importTriggerHistoryVO);
				
//		System.out.println("Member details import to sf by Bulk API :: start time (current time millis) ---"+System.currentTimeMillis());
		logger.debug("Member details import to sf by Bulk API :: start time (current time millis) ---"+System.currentTimeMillis());
		Map<String,String> returnFiles = userService.getMemberDetailsImortToSFByCSV(putNumIn,true);
		String retrunSize = "0";  
		if(returnFiles !=null && returnFiles.size()>0){
			retrunSize = returnFiles.get("returnSize");
			String accountFile = returnFiles.get(APIConstant.SF_OBJECT_ACCOUNT);
			String contactFile = returnFiles.get(APIConstant.SF_OBJECT_CONTACT);
			
			ImportDataToSFThread.getInstance(accountFile, contactFile, putNumIn,APIConstant.OPERATION_ENUM_UPSERT,isDemo).start();
			
		}
		//change status of import trigger
//		importTriggerHistoryService.updateImportTriggerHistoryStatus(importTriggerHistoryVO.getReference(), APIConstant.IMPORT_TRIGGER_STATUS_COMPLETED);
		
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
