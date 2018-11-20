package org.spa.serviceImpl.user;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.user.User;
import org.spa.model.user.UserFamilyDetails;
import org.spa.service.user.UserFamilyDetailsService;
import org.spa.service.user.UserService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.user.FamilyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class UserFamilyDetailsServiceImpl extends BaseDaoHibernate<UserFamilyDetails> implements UserFamilyDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public void saveOrUpdate(FamilyVO familyVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        UserFamilyDetails familyDetails;
        if (familyVO.getId() != null) {
            familyDetails = get(familyVO.getId());
            familyDetails.setIsActive(true);
        } else {
            familyDetails = new UserFamilyDetails();
            familyDetails.setIsActive(true);
            familyDetails.setCreated(now);
            familyDetails.setCreatedBy(userName);
            User user = userService.get(familyVO.getMemberId());
            if (user == null) {
                throw new IllegalArgumentException("Cannot found member with id:" + familyVO.getMemberId());
            }
            familyDetails.setUser(user);
        }
        familyDetails.setName(familyVO.getName());
        familyDetails.setEmail(familyVO.getEmail());
        familyDetails.setTelNum(familyVO.getTelNum());
        familyDetails.setLastUpdated(now);
        familyDetails.setLastUpdatedBy(userName);
        saveOrUpdate(familyDetails);
    }

    @Override
    public boolean emailUsed(FamilyVO familyVO) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserFamilyDetails.class);
        criteria.add(Restrictions.eq("user.id", familyVO.getMemberId()));
        criteria.add(Restrictions.eq("email", familyVO.getEmail()));
        if (familyVO.getId() != null) {
            criteria.add(Restrictions.not(Restrictions.eq("id", familyVO.getId())));
        }
        return getCount(criteria) > 0;
    }
}
