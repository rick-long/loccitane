package org.spa.serviceImpl.outSource;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.shop.OutSourceAttributeKey;
import org.spa.service.outSource.OutSourceAttributeKeyService;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-8-25
 */
@Service
public class OutSourceAttributeKeyServiceImpl extends BaseDaoHibernate<OutSourceAttributeKey> implements OutSourceAttributeKeyService {

	@Override
	public OutSourceAttributeKey getOutSourceAttributeKeyByRefAndTemplateId(String keyRef,
		Long outSourceTemplateId) {
	 	DetachedCriteria dc = DetachedCriteria.forClass(OutSourceAttributeKey.class);
        
        if (StringUtils.isNotBlank(keyRef)) {
            dc.add(Restrictions.eq("reference", keyRef));
        }
        if(outSourceTemplateId !=null){
        	dc.add(Restrictions.eq("outSourceTemplate.id", outSourceTemplateId));
        }
        List<OutSourceAttributeKey> cakList=list(dc);
        if(cakList !=null && cakList.size()>0){
        	return cakList.get(0);
        }
		return null;
	}

}
