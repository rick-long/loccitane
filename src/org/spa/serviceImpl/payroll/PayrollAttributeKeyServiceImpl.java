package org.spa.serviceImpl.payroll;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payroll.PayrollAttributeKey;
import org.spa.service.payroll.PayrollAttributeKeyService;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-8-25
 */
@Service
public class PayrollAttributeKeyServiceImpl extends BaseDaoHibernate<PayrollAttributeKey> implements PayrollAttributeKeyService {

	@Override
	public PayrollAttributeKey getPayrollAttributeKeyByRefAndTemplateId(String keyRef,
		Long payrollTemplateId) {
	 	DetachedCriteria dc = DetachedCriteria.forClass(PayrollAttributeKey.class);
        
        if (StringUtils.isNotBlank(keyRef)) {
            dc.add(Restrictions.eq("reference", keyRef));
        }
        if(payrollTemplateId !=null){
        	dc.add(Restrictions.eq("payrollTemplate.id", payrollTemplateId));
        }
        List<PayrollAttributeKey> cakList=list(dc);
        if(cakList !=null && cakList.size()>0){
        	return cakList.get(0);
        }
		return null;
	}

}
