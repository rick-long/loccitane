package org.spa.daoHibenate.staff;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.staff.StaffPayrollCategoryStatisticsDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payroll.StaffPayrollCategoryStatistics;
import org.springframework.stereotype.Repository;

@Repository
public class StaffPayrollCategoryStatisticsDaoHibernate extends BaseDaoHibernate<StaffPayrollCategoryStatistics> implements StaffPayrollCategoryStatisticsDao{

	@Override
	public StaffPayrollCategoryStatistics getStaffPayrollCategoryStatisticsByCategoryRefAndPayroll(final String categoryRef, final Long payrollId) {

		Session session = getSession();
		StringBuilder sb=new StringBuilder();
		sb.append("select spcs.* from STAFF_PAYROLL_CATEGORY_STATISTICS spcs LEFT JOIN CATEGORY c ");
		sb.append("on spcs.category_id = c.id ");
		sb.append("where 1 =1 ");
		if(payrollId !=null && payrollId.longValue()>0){
			sb.append("and spcs.staff_payroll_id= ? ");
		}
		if(StringUtils.isNotBlank(categoryRef)){
			sb.append("and c.reference= ? ");
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(StaffPayrollCategoryStatistics.class);
		
		if(payrollId !=null && payrollId.longValue()>0){
			query.setLong(0,payrollId );
		}
		if(StringUtils.isNotBlank(categoryRef)){
			query.setString(1, categoryRef);
		}
		List<StaffPayrollCategoryStatistics> resultList = query.list();
		if(resultList !=null && resultList.size()>0){
			return resultList.get(0);
		}
		return null;
	}
	
	
}
