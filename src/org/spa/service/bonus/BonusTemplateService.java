package org.spa.service.bonus;

import org.spa.dao.base.BaseDao;
import org.spa.model.bonus.BonusTemplate;
import org.spa.vo.bonus.BonusTemplateVO;

/**
 * @author Ivy 2016-7-25
 */
public interface BonusTemplateService extends BaseDao<BonusTemplate>{

    public void saveOrUpdate(BonusTemplateVO templateVO);
}
