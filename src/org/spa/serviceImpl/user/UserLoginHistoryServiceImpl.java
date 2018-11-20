package org.spa.serviceImpl.user;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.user.User;
import org.spa.model.user.UserLoginHistory;
import org.spa.service.user.UserLoginHistoryService;
import org.spa.utils.WebThreadLocal;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class UserLoginHistoryServiceImpl extends BaseDaoHibernate<UserLoginHistory> implements UserLoginHistoryService {

    public void save(User user, String ip) {
        UserLoginHistory history = new UserLoginHistory();
        history.setUser(user);
        history.setDate(new Date());
        history.setIp(ip);
        save(history);
    }

}
