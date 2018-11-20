package org.spa.daoHibenate.commission;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.commission.CommissionRuleDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.commission.CommissionRule;
import org.springframework.stereotype.Repository;
@Repository
public class CommissionRuleDaoHibernate extends BaseDaoHibernate<CommissionRule> implements CommissionRuleDao{

	@Override
	public List<CommissionRule> getCommissionRuleListByFilters(final String userGroupType,final String userGroupModule,final Long staffId,final Long companyId) {
		StringBuilder sb=new StringBuilder();
		sb.append(" select cr.* from COMMISSION_RULE cr");
		if(staffId !=null && staffId.longValue()>0){
			sb.append(" LEFT JOIN COMMISSION_RULE_USER_GROUP crug ON cr.id=crug.commission_rule_id");
			sb.append(" LEFT JOIN USER_GROUP ug ON crug.user_group_id=ug.id");
			sb.append(" LEFT JOIN USER_USER_GROUP uug on ug.id = uug.user_group_id");
		}
		
		sb.append(" where cr.is_active=1");
		
		if(companyId !=null && companyId.longValue()>0){
			sb.append(" and cr.company_id ="+companyId);
		}
		if(StringUtils.isNotBlank(userGroupType)){
			sb.append(" and ug.type = "+"'"+userGroupType+"'");
		}
		if(StringUtils.isNotBlank(userGroupModule)){
			sb.append(" and ug.module = "+"'"+userGroupModule+"'");
		}
		if(staffId !=null && staffId.longValue()>0){
			sb.append(" and uug.user_id = "+staffId);
			sb.append(" and ug.is_active=1");
		}
		
		Session session = getSession();
    	SQLQuery query = session.createSQLQuery(sb.toString());
    	query.addEntity(CommissionRule.class);
    	List<CommissionRule> resultList = query.list();
		
		return resultList;
	}
}
