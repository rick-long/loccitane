package org.spa.serviceImpl.commission;

import org.spa.dao.commission.CommissionAttributeDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.commission.CommissionAttribute;
import org.spa.service.commission.CommissionAttributeService;
import org.spa.utils.WebThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class CommissionAttributeServiceImpl extends BaseDaoHibernate<CommissionAttribute> implements CommissionAttributeService {

	@Autowired
	private CommissionAttributeDao commissionAttributeDao;
	
	@Override
	public CommissionAttribute getCommissionAttributeByRuleAndKeyRef(String keyRef, Long commissionRuleId) {
		Long companyId=null;
		if(WebThreadLocal.getCompany() !=null){
			companyId=WebThreadLocal.getCompany().getId();
		}
		return commissionAttributeDao.getCommissionAttributeByRuleAndKeyRef(keyRef, commissionRuleId, companyId);
	}

}
