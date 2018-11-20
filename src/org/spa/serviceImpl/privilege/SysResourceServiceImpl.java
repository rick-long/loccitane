package org.spa.serviceImpl.privilege;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.privilege.SysResource;
import org.spa.service.privilege.SysResourceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivy on 2016-5-24
 */
@Service
public class SysResourceServiceImpl extends BaseDaoHibernate<SysResource> implements SysResourceService {

    /**
     * 获取已排序的sysResource集合
     *
     * @return
     */
    public List<SysResource> getOrderedList() {
        List<SysResource> orderedList = new ArrayList<>();
        orderedList.add(get("type", "root"));
        DetachedCriteria criteria = DetachedCriteria.forClass(SysResource.class);
        criteria.add(Restrictions.eq("type", "module"));
        criteria.addOrder(Order.asc("moduleOrder"));
        List<SysResource> resources = list(criteria);
        for (SysResource resource : resources) {
            orderedList.add(resource);
            addSysResource(resource, orderedList);
        }
        return orderedList;
    }

    private void addSysResource(SysResource resource, List<SysResource> list) {
        if (resource.getSysResources() == null || resource.getSysResources().size() == 0) {
            return;
        }
        for (SysResource child : resource.getSysResources()) {
            list.add(child);
            addSysResource(child, list);
        }
    }
}
