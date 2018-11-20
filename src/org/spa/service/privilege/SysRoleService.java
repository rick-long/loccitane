package org.spa.service.privilege;

import org.spa.dao.base.BaseDao;
import org.spa.model.privilege.SysRole;
import org.spa.vo.user.SysRoleVO;

/**
 * Created by Ivy on 2016-5-24
 */
public interface SysRoleService extends BaseDao<SysRole>{

    public void saveOrUpdate(SysRoleVO sysRoleVO);

    public void assignPermissions(SysRoleVO sysRoleVO);
}
