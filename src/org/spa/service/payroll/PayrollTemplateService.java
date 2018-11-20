package org.spa.service.payroll;

import org.spa.dao.base.BaseDao;
import org.spa.model.payroll.PayrollTemplate;
import org.spa.vo.payroll.PayrollTemplateVO;

/**
 * @author Ivy 2016-8-25
 */
public interface PayrollTemplateService extends BaseDao<PayrollTemplate>{

    public void saveOrUpdate(PayrollTemplateVO templateVO);
}
