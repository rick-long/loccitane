package org.spa.service.user;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.User;
import org.spa.model.user.UserLoginHistory;

public interface UserLoginHistoryService extends BaseDao<UserLoginHistory>{

    public void save(User user, String ip);

}
