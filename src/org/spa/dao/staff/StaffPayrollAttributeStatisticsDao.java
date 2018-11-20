package org.spa.dao.staff;

import org.spa.model.payroll.StaffPayrollAttributeStatistics;

public interface StaffPayrollAttributeStatisticsDao {

	public StaffPayrollAttributeStatistics getStaffPayrollAttributeStatisticsByKeyRefAndPayroll(final String keyRef, final Long payrollId);
}
