package org.spa.serviceImpl.bonus;

import org.spa.dao.bonus.BonusAttributeDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.bonus.BonusAttribute;
import org.spa.service.bonus.BonusAttributeService;
import org.spa.utils.WebThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class BonusAttributeServiceImpl extends BaseDaoHibernate<BonusAttribute> implements BonusAttributeService {

	@Autowired
	private BonusAttributeDao bonusAttributeDao;
	
	@Override
	public BonusAttribute getBonusAttributeByRuleAndKeyRef(String keyRef, Long bonusRuleId) {
		Long companyId=null;
		if(WebThreadLocal.getCompany() !=null){
			companyId=WebThreadLocal.getCompany().getId();
		}
		return bonusAttributeDao.getBonusAttributeByRuleAndKeyRef(keyRef, bonusRuleId, companyId);
	}

}
