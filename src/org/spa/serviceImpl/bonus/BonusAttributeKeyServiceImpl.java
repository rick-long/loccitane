package org.spa.serviceImpl.bonus;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.bonus.BonusAttributeKey;
import org.spa.service.bonus.BonusAttributeKeyService;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class BonusAttributeKeyServiceImpl extends BaseDaoHibernate<BonusAttributeKey> implements BonusAttributeKeyService {

	@Override
	public BonusAttributeKey getBonusAttributeKeyByRefAndTemplateId(String keyRef,
		Long bonusTemplateId) {
	 	DetachedCriteria dc = DetachedCriteria.forClass(BonusAttributeKey.class);
        
        if (StringUtils.isNotBlank(keyRef)) {
            dc.add(Restrictions.eq("reference", keyRef));
        }
        if(bonusTemplateId !=null){
        	dc.add(Restrictions.eq("bonusTemplate.id", bonusTemplateId));
        }
        List<BonusAttributeKey> cakList=list(dc);
        if(cakList !=null && cakList.size()>0){
        	return cakList.get(0);
        }
		return null;
	}

}
