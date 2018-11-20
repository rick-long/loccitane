package org.spa.service.bonus;

import org.spa.dao.base.BaseDao;
import org.spa.model.bonus.BonusRule;
import org.spa.vo.bonus.BonusRuleVO;

/**
 * @author Ivy 2016-7-25
 */
public interface BonusRuleService extends BaseDao<BonusRule>{

    public void saveOrUpdate(BonusRuleVO bonusRuleVO);
    
}
