package org.spa.serviceImpl.user;

import com.spa.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.user.User;
import org.spa.model.user.UserGroup;
import org.spa.service.user.UserGroupService;
import org.spa.service.user.UserService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.user.MemberAdvanceVO;
import org.spa.vo.user.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class UserGroupServiceImpl extends BaseDaoHibernate<UserGroup> implements UserGroupService {

    @Autowired
    private UserService userService;

    @Override
    public void saveOrUpdate(UserGroupVO userGroupVO) {
        // TODO Auto-generated method stub
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        UserGroup userGroup;
        if (userGroupVO.getId() != null) {
            userGroup = get(userGroupVO.getId());
			userGroup.setIsActive(userGroupVO.getActive());
        } else {
            userGroup = new UserGroup();
            userGroup.setCompany(userGroupVO.getCompany());
            userGroup.setIsActive(true);
            userGroup.setCreated(now);
            userGroup.setCreatedBy(userName);
        }

        userGroup.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.USER_GROUP_REF_PREFIX));
        userGroup.setName(userGroupVO.getName());
        userGroup.setRemarks(userGroupVO.getRemarks());
        userGroup.setType(userGroupVO.getType());
        userGroup.setModule(userGroupVO.getModule());
        
        // set users
        Set<User> userSet = userGroup.getUsers();
        Long[] userIds = userGroupVO.getUserIds();
        if (userIds != null && userIds.length > 0) {
            for (Long userId : userIds) {
                userSet.add(userService.get(userId));
            }
        }
        userGroup.setLastUpdated(now);
        userGroup.setLastUpdatedBy(userName);
        saveOrUpdate(userGroup);
    }

	@Override
	public List<UserGroup> getUserGroupByTypeAndModule(String type, String module, Long companyId) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserGroup.class);
		dc.add(Restrictions.eq("isActive", true));
		if(StringUtils.isNoneBlank(type)){
			dc.add(Restrictions.eq("type", type));
		}
		if(StringUtils.isNoneBlank(module)){
			dc.add(Restrictions.eq("module", module));
		}
		if(companyId !=null && companyId.longValue()>0){
			  dc.add(Restrictions.eq("company.id", companyId));
		}
		List<UserGroup> ugList=list(dc);
		return ugList;
	}

	@Override
	public UserGroup getStaffGroupByFilters(Long staffId, String type, String module, Long companyId) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserGroup.class);
		dc.add(Restrictions.eq("isActive", true));
		if(StringUtils.isNoneBlank(type)){
			dc.add(Restrictions.eq("type", type));
		}
		if(StringUtils.isNoneBlank(module)){
			dc.add(Restrictions.eq("module", module));
		}
		if(companyId !=null && companyId.longValue()>0){
			  dc.add(Restrictions.eq("company.id", companyId));
		}
		if(staffId !=null){
			dc.createAlias("users", "staff");
			dc.add(Restrictions.eq("staff.id", staffId));
		}
		List<UserGroup> ugList=list(dc);
		UserGroup ug=null;
		if(ugList !=null && ugList.size()>0){
			ug=ugList.get(0);
		}
		return ug;
	}

	@Override
	public List<UserGroup> getUserGroupByFilters(Long memberId, String type, String module, Long companyId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(UserGroup.class);
		dc.add(Restrictions.eq("isActive", true));
		
		if(StringUtils.isNoneBlank(type)){
			dc.add(Restrictions.eq("type", type));
		}
		if(StringUtils.isNoneBlank(module)){
			dc.add(Restrictions.eq("module", module));
		}
		if(companyId !=null && companyId.longValue()>0){
			  dc.add(Restrictions.eq("company.id", companyId));
		}
		if(memberId !=null){
			dc.createAlias("users", "member");
			dc.add(Restrictions.eq("member.id", memberId));
		}
		List<UserGroup> ugList=list(dc);
		return ugList;
	}

	@Override
	public void addUserToGroup(Long userGroupId, MemberAdvanceVO memberAdvanceVO) {
		UserGroup userGroup = get(userGroupId);
		userGroup.getUsers().clear();
		getSession().flush();
		List<User> userList = userService.listAllMember(memberAdvanceVO);
		userGroup.getUsers().addAll(userList);
		saveOrUpdate(userGroup);
	}
}
