package org.spa.dao.staff;

import org.spa.model.payroll.StaffPayrollCategoryStatistics;

public interface StaffPayrollCategoryStatisticsDao {

	public StaffPayrollCategoryStatistics getStaffPayrollCategoryStatisticsByCategoryRefAndPayroll(final String categoryRef, final Long payrollId);
}
