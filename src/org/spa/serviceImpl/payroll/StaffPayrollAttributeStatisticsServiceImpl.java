package org.spa.serviceImpl.payroll;

import org.spa.dao.staff.StaffPayrollAttributeStatisticsDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payroll.StaffPayrollAttributeStatistics;
import org.spa.service.payroll.StaffPayrollAttributeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/06/16.
 */
@Service
public class StaffPayrollAttributeStatisticsServiceImpl extends BaseDaoHibernate<StaffPayrollAttributeStatistics> implements StaffPayrollAttributeStatisticsService{

	@Autowired
	public StaffPayrollAttributeStatisticsDao staffPayrollAttributeStatisticsDao;
	
	@Override
	public StaffPayrollAttributeStatistics getStaffPayrollAttributeStatisticsByKeyRefAndPayroll(String keyRef, Long payrollId){
		
		return staffPayrollAttributeStatisticsDao.getStaffPayrollAttributeStatisticsByKeyRefAndPayroll(keyRef, payrollId);
	}
}
