package org.spa.daoHibenate.staff;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.staff.StaffPayrollAttributeStatisticsDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payroll.StaffPayrollAttributeStatistics;
import org.springframework.stereotype.Repository;

@Repository
public class StaffPayrollAttributeStatisticsDaoHibernate extends BaseDaoHibernate<StaffPayrollAttributeStatistics> implements StaffPayrollAttributeStatisticsDao{

	@Override
	public StaffPayrollAttributeStatistics getStaffPayrollAttributeStatisticsByKeyRefAndPayroll(final String keyRef, final Long payrollId) {

		Session session = getSession();
		StringBuilder sb=new StringBuilder();
		sb.append("select spas.* from STAFF_PAYROLL_ATTRIBUTE_STATISTICS spas LEFT JOIN PAYROLL_ATTRIBUTE_KEY pak ");
		sb.append("on spas.payroll_attribute_key_id = pak.id ");
		sb.append("where pak.is_active=1 ");
		if(payrollId !=null && payrollId.longValue()>0){
			sb.append("and spas.staff_payroll_id= ? ");
		}
		if(StringUtils.isNotBlank(keyRef)){
			sb.append("and pak.reference= ? ");
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(StaffPayrollAttributeStatistics.class);
		
		if(payrollId !=null && payrollId.longValue()>0){
			query.setLong(0,payrollId );
		}
		if(StringUtils.isNotBlank(keyRef)){
			query.setString(1, keyRef);
		}
		
		List<StaffPayrollAttributeStatistics> resultList = query.list();
		if(resultList !=null && resultList.size()>0){
			return resultList.get(0);
		}
		return null;
	}
	
	
}
