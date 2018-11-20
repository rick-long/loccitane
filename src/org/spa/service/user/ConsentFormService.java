package org.spa.service.user;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.ConsentForm;
import org.spa.model.user.ConsentFormUser;
import org.spa.vo.user.ConsentFormVO;

/**
 * Created by Ivy on 2016/8/28.
 */
public interface ConsentFormService extends BaseDao<ConsentForm>{

    public void saveOrUpdate(ConsentFormVO consentFormVo);
    
    public ConsentFormUser getconsentFormUserBySignedConsentForm(Long consentFormId,Long userId);
}
