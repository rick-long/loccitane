package org.spa.service.commission;

import org.spa.dao.base.BaseDao;
import org.spa.model.commission.CommissionTemplate;
import org.spa.vo.commission.CommissionTemplateVO;

/**
 * @author Ivy 2016-7-25
 */
public interface CommissionTemplateService extends BaseDao<CommissionTemplate>{

    public void saveOrUpdate(CommissionTemplateVO templateVO);
}
