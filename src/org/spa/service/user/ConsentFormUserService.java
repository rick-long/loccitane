package org.spa.service.user;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.ConsentFormUser;
import org.spa.vo.user.ConsentFormUserSignVO;

/**
 * Created by Ivy on 2016/8/28.
 */
public interface ConsentFormUserService extends BaseDao<ConsentFormUser>{

    public void saveOrUpdate(ConsentFormUserSignVO consentFormUserSignVO);
    
    public List<ConsentFormUser> getConsentFormSignedByFilter(Long userId,Long consentFormId,Long shopId);
}
