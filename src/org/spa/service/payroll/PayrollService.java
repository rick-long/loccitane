package org.spa.service.payroll;
import java.util.Map;

import org.spa.dao.base.BaseDao;
import org.spa.model.payroll.StaffPayroll;
import org.spa.model.user.User;
import org.spa.vo.payroll.PayrollAddVO;

/**
 * Created by Ivy on 2016/06/16.
 */
public interface PayrollService extends BaseDao<StaffPayroll>{
	
	public void savePayroll(PayrollAddVO payrollAddVO);
	
	public void saveAndUpdatePayroll(PayrollAddVO payrollAddVO,User staff);
	
	public void regeneratePayrolls(StaffPayroll payroll);
	
	public Map<String,Double> getTargetAmountsByTypeMap(User staff);
}
