package org.spa.service.salesforce;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.salesforce.ImportTriggerHistory;
import org.spa.vo.toSalesforce.ImportTriggerHistoryVO;

/**
 * Created by Ivy on 2017/10/17.
 */
public interface ImportTriggerHistoryService extends BaseDao<ImportTriggerHistory>{
	//
	public List<ImportTriggerHistory> checkImportTriggerHistory(String status,String module);
	
	public void saveImportTriggerHistory(ImportTriggerHistoryVO importTriggerHistoryVO);
	
	public void updateImportTriggerHistoryStatus(String reference,String status);
}
