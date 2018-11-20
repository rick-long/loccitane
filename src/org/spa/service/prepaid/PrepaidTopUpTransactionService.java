package org.spa.service.prepaid;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.spa.dao.base.BaseDao;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface PrepaidTopUpTransactionService extends BaseDao<PrepaidTopUpTransaction>{
	//
	public List<PrepaidTopUpTransaction> getActivePrepaidTopUpTransactionsByPrepaid(Prepaid prepaid);
	
	public List<PrepaidTopUpTransaction> getClientPrepaidTopUpTransactionView(Map parameters);
	
	public Map  getPrepaidOutstandingMap(String finishDate, Long shopId, Long companyId);
	
	public List<PrepaidTopUpTransaction> getExpiringPrepaidsByFilters(Date startDate);
}
