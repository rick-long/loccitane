package org.spa.service.bonus;

import org.spa.dao.base.BaseDao;
import org.spa.model.bonus.BonusAttributeKey;

/**
 * @author Ivy 2016-7-25
 */
public interface BonusAttributeKeyService extends BaseDao<BonusAttributeKey>{

	public BonusAttributeKey getBonusAttributeKeyByRefAndTemplateId(String keyRef,Long bonusTemplateId);
}
