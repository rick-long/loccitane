package org.spa.service.outSource;

import org.spa.dao.base.BaseDao;
import org.spa.model.shop.OutSourceAttributeKey;

/**
 * @author Ivy 2016-8-29
 */
public interface OutSourceAttributeKeyService extends BaseDao<OutSourceAttributeKey>{

	public OutSourceAttributeKey getOutSourceAttributeKeyByRefAndTemplateId(String keyRef,Long commissionTemplateId);
}
