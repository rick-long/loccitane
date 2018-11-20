package org.spa.service.user;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.UserGroup;
import org.spa.vo.user.MemberAdvanceVO;
import org.spa.vo.user.UserGroupVO;

public interface UserGroupService extends BaseDao<UserGroup>{

    public void saveOrUpdate(UserGroupVO userGroupVO);

    public List<UserGroup> getUserGroupByTypeAndModule(String type,String module,Long companyId);
    
    public List<UserGroup> getUserGroupByFilters(Long memberId,String type,String module,Long companyId);
    
    public UserGroup getStaffGroupByFilters(Long staffId, String type, String module, Long companyId);

    void addUserToGroup(Long userGroupId, MemberAdvanceVO memberAdvanceVO);
}
