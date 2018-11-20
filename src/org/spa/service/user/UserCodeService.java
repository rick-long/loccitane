package org.spa.service.user;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.User;
import org.spa.model.user.UserCode;

/**
 * Created by Ivy on 2016-6-14
 */
public interface UserCodeService extends BaseDao<UserCode>{

    UserCode saveActivatingCode(User user);

    UserCode saveResetPasswordCode(User user);

    UserCode getByCode(String code, String type);
}
