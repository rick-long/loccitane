package org.spa.service.marketing;

import org.spa.dao.base.BaseDao;
import org.spa.model.company.Company;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.user.User;
import org.spa.vo.marketing.MktMailShotVO;

/**
 * Created by Ivy on 2016-6-1
 */
public interface MktMailShotService extends BaseDao<MktMailShot>{

    public void save(MktMailShotVO mailShotVO);

    public MktMailShot saveRegistrationShot(User user);
    
    MktMailShot saveCommonMktMailShot(String emailTemplateType, User user, Company company);

    MktMailShot saveCommonMktMailShot(String emailTemplateType, User user);
}
