package org.spa.serviceImpl.user;

import java.util.Date;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.user.User;
import org.spa.model.user.UserCode;
import org.spa.service.user.UserCodeService;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016-6-14
 */
@Service
public class UserCodeServiceImpl extends BaseDaoHibernate<UserCode> implements UserCodeService {

    @Override
    public UserCode saveActivatingCode(User user) {
        return save(user, CommonConstant.USER_CODE_TYPE_ACTIVATING);
    }

    @Override
    public UserCode saveResetPasswordCode(User user) {
        return save(user, CommonConstant.USER_CODE_TYPE_RESET_PASSWORD);
    }

    private UserCode save(User user, String type) {
        UserCode userCode = new UserCode();
        userCode.setUser(user);
        userCode.setType(type);
        userCode.setCode(UUID.randomUUID().toString().replace("-", ""));
        userCode.setExpireDate(new DateTime().plusDays(1).toDate()); // 一天后过期
        userCode.setCreated(new Date());
        save(userCode);
        return userCode;
    }

    @Override
    public UserCode getByCode(String code, String type) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCode.class);
        detachedCriteria.add(Restrictions.eq("type", type));
        detachedCriteria.add(Restrictions.eq("code", code));
        detachedCriteria.add(Restrictions.ge("expireDate", new Date())); // 有效期要比现在时间大，才有效
        return get(detachedCriteria);
    }

}
