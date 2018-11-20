package org.spa.serviceImpl.salesforce;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.salesforce.ImportTriggerHistory;
import org.spa.service.salesforce.ImportTriggerHistoryService;
import org.spa.vo.toSalesforce.ImportTriggerHistoryVO;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2017/10/17.
 */
@Service
public class ImportTriggerHistoryServiceImpl extends BaseDaoHibernate<ImportTriggerHistory> implements ImportTriggerHistoryService{

	
	@Override
	public List<ImportTriggerHistory> checkImportTriggerHistory(String status,String module) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ImportTriggerHistory.class);
		
		detachedCriteria.add(Restrictions.eq("status", status));
		detachedCriteria.add(Restrictions.eq("module", module));
		
		List<ImportTriggerHistory> importDataToSFHistorys = list(detachedCriteria);
		return importDataToSFHistorys;
	}

	@Override
	public void saveImportTriggerHistory(ImportTriggerHistoryVO importTriggerHistoryVO) {
		ImportTriggerHistory importTriggerHistory = new ImportTriggerHistory();
		importTriggerHistory.setReference(importTriggerHistoryVO.getReference());
		importTriggerHistory.setCreated(new Date());
		importTriggerHistory.setChannel(importTriggerHistoryVO.getChannel());
		importTriggerHistory.setCreatedBy(importTriggerHistoryVO.getCreatedBy());
		importTriggerHistory.setCronExpression(importTriggerHistoryVO.getCronExpression());
		importTriggerHistory.setDescription(importTriggerHistoryVO.getDescription());
		importTriggerHistory.setModule(importTriggerHistoryVO.getModule());
		importTriggerHistory.setOperationEnum(importTriggerHistoryVO.getOperationEnum());
		importTriggerHistory.setStatus(importTriggerHistoryVO.getStatus());
		importTriggerHistory.setTriggerTime(importTriggerHistoryVO.getTriggerTime());
		
		saveOrUpdate(importTriggerHistory);
	}

	@Override
	public void updateImportTriggerHistoryStatus(String reference,String status) {
		ImportTriggerHistory importTriggerHistory = get("reference", reference);
		importTriggerHistory.setStatus(status);
		saveOrUpdate(importTriggerHistory);
	}
}