package org.spa.serviceImpl.privilege;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.privilege.SysResource;
import org.spa.model.privilege.SysRole;
import org.spa.service.privilege.SysResourceService;
import org.spa.service.privilege.SysRoleService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.user.SysRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * Created by Ivy on 2016-5-24
 */
@Service
public class SysRoleServiceImpl extends BaseDaoHibernate<SysRole> implements SysRoleService {

    @Autowired
    private SysResourceService sysResourceService;

    public void saveOrUpdate(SysRoleVO sysRoleVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        SysRole sysRole;
        if (sysRoleVO.getId() != null) {
            sysRole = get(sysRoleVO.getId());
            sysRole.setIsActive(sysRoleVO.getIsActive());
        } else {
            sysRole = new SysRole();
            sysRole.setReference(sysRoleVO.getReference());
            sysRole.setCompany(sysRoleVO.getCompany());
            sysRole.setIsActive(true);
            sysRole.setCreated(now);
            sysRole.setCreatedBy(userName);
        }
        sysRole.setName(sysRoleVO.getName());
        sysRole.setLastUpdated(now);
        sysRole.setLastUpdatedBy(userName);
        saveOrUpdate(sysRole);
    }

    /**
     * 分配权限
     *
     * @param sysRoleVO
     */
    public void assignPermissions(SysRoleVO sysRoleVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        SysRole sysRole = get(sysRoleVO.getId());
        Long [] sysResourceIds = sysRoleVO.getSysResourceIds();
        Set<SysResource> sysResourceSet = sysRole.getSysResources();
        sysResourceSet.clear();
        if(sysResourceIds != null) {
            for(Long id : sysResourceIds) {
                sysResourceSet.add(sysResourceService.get(id));
            }
        }
        sysRole.setLastUpdated(now);
        sysRole.setLastUpdatedBy(userName);
        saveOrUpdate(sysRole);
    }
}
