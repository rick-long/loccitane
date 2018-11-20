package org.spa.service.user;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.UserFamilyDetails;
import org.spa.vo.user.FamilyVO;

public interface UserFamilyDetailsService extends BaseDao<UserFamilyDetails>{


    void saveOrUpdate(FamilyVO familyVO);

    boolean emailUsed(FamilyVO familyVO);
}
