package org.spa.serviceImpl.commission;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.commission.CommissionAttributeKey;
import org.spa.service.commission.CommissionAttributeKeyService;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class CommissionAttributeKeyServiceImpl extends BaseDaoHibernate<CommissionAttributeKey> implements CommissionAttributeKeyService {

	@Override
	public CommissionAttributeKey getCommissionAttributeKeyByRefAndTemplateId(String keyRef,
		Long commissionTemplateId) {
	 	DetachedCriteria dc = DetachedCriteria.forClass(CommissionAttributeKey.class);
        
        if (StringUtils.isNotBlank(keyRef)) {
            dc.add(Restrictions.eq("reference", keyRef));
        }
        if(commissionTemplateId !=null){
        	dc.add(Restrictions.eq("commissionTemplate.id", commissionTemplateId));
        }
        List<CommissionAttributeKey> cakList=list(dc);
        if(cakList !=null && cakList.size()>0){
        	return cakList.get(0);
        }
		return null;
	}

}
