package org.spa.service.payroll;

import org.spa.dao.base.BaseDao;
import org.spa.model.payroll.StaffPayrollAttributeStatistics;

/**
 * Created by Ivy on 2016/06/16.
 */
public interface StaffPayrollAttributeStatisticsService extends BaseDao<StaffPayrollAttributeStatistics>{
	
	public StaffPayrollAttributeStatistics getStaffPayrollAttributeStatisticsByKeyRefAndPayroll(String keyRef, Long payrollId);
}
