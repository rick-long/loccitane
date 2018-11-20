package org.spa.service.outSource;

import org.spa.dao.base.BaseDao;
import org.spa.model.shop.OutSourceTemplate;
import org.spa.vo.shop.OutSourceTemplateVO;

/**
 * @author Ivy 2016-8-30
 */
public interface OutSourceTemplateService extends BaseDao<OutSourceTemplate>{

    public void saveOrUpdate(OutSourceTemplateVO templateVO);
}
