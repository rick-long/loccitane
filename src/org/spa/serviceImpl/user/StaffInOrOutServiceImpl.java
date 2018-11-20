package org.spa.serviceImpl.user;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.staff.StaffInOrOut;
import org.spa.service.user.StaffInOrOutService;
import org.springframework.stereotype.Service;

@Service
public class StaffInOrOutServiceImpl extends BaseDaoHibernate<StaffInOrOut> implements StaffInOrOutService {

}
