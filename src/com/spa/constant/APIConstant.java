package com.spa.constant;

import org.spa.utils.PropertiesUtil;

public class APIConstant {
	
	public static final String IMPORT_FILE_PATH=PropertiesUtil.getValueByName("SF_IMPORT_FILE_PATH");// /home/ibsadmin/salesforce/csv/";/Users/liusiping/Documents/
	
	public static final String SF_OBJECT_ACCOUNT="Account";
	
	public static final String SF_OBJECT_CONTACT="Contact";
	
	public static final String SSL_IMPORT_TO_SF_MEMBER="data_from_sot_member";
	
	public static final String SF_RECORD_TYPE_ID_MEMBER="0126F000000ubtE";
	
											
	public static final String SF_USERNAME=PropertiesUtil.getValueByName("SF_USERNAME");// "import_api@imanagesystems.com";//salesforce sanbox
//	public static final String SF_USERNAME="import_api@imanagesystems.com";//salesforce production

	
	public static final String SF_PASSWORD_TOKEN=PropertiesUtil.getValueByName("SF_PASSWORD_TOKEN");//"Ims@2017!GjIxnEFz2II4QjsFAlQXCOjUW";//salesforce sanbox	
//	public static final String SF_PASSWORD_TOKEN="1qaz2wsxJ3h7mPl8VTZ4A521EUSW1Hjx";//salesforce production
	
	public static final String SF_END_POINT=PropertiesUtil.getValueByName("SF_END_POINT");//"https://cs57.salesforce.com/services/Soap/u/38.0";//salesforce sanbox
//	public static final String SF_END_POINT="https://imanagesystems.my.salesforce.com/services/Soap/u/38.0";//salesforce production
	
	public static final String OPERATION_ENUM_UPSERT="upsert";
	
	public static final String OPERATION_ENUM_INSERT="insert";
	
	public static final String IMPORT_TRIGGER_MODULE_MEMBER="MEMBER";
	
	public static final String IMPORT_TRIGGER_CHANNEL_MANUAL="MANUAL";
	public static final String IMPORT_TRIGGER_CHANNEL_AUTO="AUTO";
	public static final String IMPORT_TRIGGER_CHANNEL_API_CALL="API_CALL";
	
	public static final String IMPORT_TRIGGER_STATUS_PROCESSING="PROCESSING";
	public static final String IMPORT_TRIGGER_STATUS_COMPLETED="COMPLETED";
	
	public static final String TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	public static final String IMPORT_TRIGGER_PREFIX="IMPORT_TRIGGER";
}
