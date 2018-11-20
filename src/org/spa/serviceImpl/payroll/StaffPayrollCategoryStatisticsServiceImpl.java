package org.spa.serviceImpl.payroll;

import org.spa.dao.staff.StaffPayrollCategoryStatisticsDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payroll.StaffPayrollCategoryStatistics;
import org.spa.service.payroll.StaffPayrollCategoryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/06/16.
 */
@Service
public class StaffPayrollCategoryStatisticsServiceImpl extends BaseDaoHibernate<StaffPayrollCategoryStatistics> implements StaffPayrollCategoryStatisticsService{

	@Autowired
	public StaffPayrollCategoryStatisticsDao staffPayrollCategoryStatisticsDao;
	
	@Override
	public StaffPayrollCategoryStatistics getStaffPayrollCategoryStatisticsByCategoryRefAndPayroll(String categoryRef, Long payrollId){
		
		return staffPayrollCategoryStatisticsDao.getStaffPayrollCategoryStatisticsByCategoryRefAndPayroll(categoryRef, payrollId);
	}
}
