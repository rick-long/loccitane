package org.spa.service.marketing;

import org.spa.dao.base.BaseDao;
import org.spa.model.marketing.MktEmailTemplate;
import org.spa.vo.marketing.MktEmailTemplateVO;

/**
 * Created by Ivy on 2016-6-3
 */
public interface MktEmailTemplateService extends BaseDao<MktEmailTemplate>{

    public void saveOrUpdate(MktEmailTemplateVO emailTemplateVO);

    public MktEmailTemplate getByType(String type, Long companyId);
}
