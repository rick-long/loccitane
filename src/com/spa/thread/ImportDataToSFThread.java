package com.spa.thread;

import com.sforce.async.AsyncApiException;
import com.sforce.ws.ConnectionException;
import com.spa.constant.APIConstant;
import com.spa.salesforce.BulkAPI;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.spa.model.user.User;
import org.spa.service.user.UserService;
import org.spa.serviceImpl.user.UserServiceImpl;
import org.spa.utils.SpringUtil;

/**
 * import data to SF
 *
 * @author Ivy on 2017-8-23
 */
public class ImportDataToSFThread extends Thread {

    private String accountFile;
    private String contactFile;
    private Integer importNumber;
    private Boolean isDemo;
    private String operationEnum;
    private List<Object[]> searchResults;
    private static final UserService userService = SpringUtil.getBean(UserServiceImpl.class);
    
    public static ImportDataToSFThread getInstance(String accountFile,String contactFile,Integer importNumber,String operationEnum,Boolean isDemo) {
    	ImportDataToSFThread importDataToSFThread = new ImportDataToSFThread();
    	importDataToSFThread.accountFile = accountFile;
    	importDataToSFThread.contactFile = contactFile;
    	importDataToSFThread.importNumber = importNumber;
    	importDataToSFThread.operationEnum=operationEnum;
    	importDataToSFThread.isDemo=isDemo;
        return importDataToSFThread;
    }
    
    public static ImportDataToSFThread getInstance(String accountFile,String contactFile,Integer importNumber,String operationEnum,Boolean isDemo,List<Object[]> searchResults) {
    	ImportDataToSFThread importDataToSFThread = new ImportDataToSFThread();
    	importDataToSFThread.accountFile = accountFile;
    	importDataToSFThread.contactFile = contactFile;
    	importDataToSFThread.importNumber = importNumber;
    	importDataToSFThread.operationEnum=operationEnum;
    	importDataToSFThread.isDemo=isDemo;
    	importDataToSFThread.searchResults = searchResults;
        return importDataToSFThread;
    }

    @Override
    public void run() {
    	System.out.println("-----ImportDataToSFThread ---- run ----start------");
    	BulkAPI api =new BulkAPI();
		try {
			
			api.runSample(APIConstant.SF_OBJECT_ACCOUNT, APIConstant.SF_USERNAME, APIConstant.SF_PASSWORD_TOKEN, APIConstant.IMPORT_FILE_PATH + accountFile,operationEnum);
			api.runSample(APIConstant.SF_OBJECT_CONTACT, APIConstant.SF_USERNAME, APIConstant.SF_PASSWORD_TOKEN, APIConstant.IMPORT_FILE_PATH + contactFile,operationEnum);
			
			//update last modifier
			if(searchResults ==null){
				searchResults = userService.getMemberDetailsExportToCSVForSF(importNumber,isDemo);
			}
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
		System.out.println("-----ImportDataToSFThread ---- run ----end------");
    }
}
