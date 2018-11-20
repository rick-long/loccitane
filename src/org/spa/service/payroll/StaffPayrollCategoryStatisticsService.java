package org.spa.service.payroll;

import org.spa.dao.base.BaseDao;
import org.spa.model.payroll.StaffPayrollCategoryStatistics;

/**
 * Created by Ivy on 2016/06/16.
 */
public interface StaffPayrollCategoryStatisticsService extends BaseDao<StaffPayrollCategoryStatistics>{
	
	public StaffPayrollCategoryStatistics getStaffPayrollCategoryStatisticsByCategoryRefAndPayroll(String categoryRef, Long payrollId);
}
