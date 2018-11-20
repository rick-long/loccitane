package org.spa.service.privilege;

import org.spa.dao.base.BaseDao;
import org.spa.model.privilege.SysResource;

import java.util.List;

/**
 * Created by Ivy on 2016-5-24
 */
public interface SysResourceService extends BaseDao<SysResource> {

    public List<SysResource> getOrderedList();
}
