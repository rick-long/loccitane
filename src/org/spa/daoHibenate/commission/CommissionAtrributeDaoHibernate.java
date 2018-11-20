package org.spa.daoHibenate.commission;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.commission.CommissionAttributeDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.commission.CommissionAttribute;
import org.springframework.stereotype.Repository;

@Repository
public class CommissionAtrributeDaoHibernate extends BaseDaoHibernate<CommissionAttribute> implements CommissionAttributeDao{
	
	@Override
	public CommissionAttribute getCommissionAttributeByRuleAndKeyRef(final String keyRef,final Long commissionRuleId,final Long companyId) {
		
		CommissionAttribute ca=null;
		
		StringBuffer sql=new StringBuffer();
		sql.append("select ca.* from COMMISSION_ATTRIBUTE ca ");
		sql.append("LEFT JOIN COMMISSION_ATTRIBUTE_KEY cak on ca.commission_attribute_key_id=cak.id ");
		sql.append("LEFT JOIN COMMISSION_TEMPLATE ct on cak.commission_template_id=ct.id ");
		sql.append("where ca.commission_rule_id=? ");
		sql.append("and cak.reference=? ");
		if(companyId !=null){
			sql.append(" and ct.company_id= ?");
		}
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());

		query.addEntity(CommissionAttribute.class);
		

		query.setLong(0, commissionRuleId);
		query.setString(1, keyRef);
		if(companyId !=null){
			query.setLong(2, companyId);
		}
		
		List<CommissionAttribute> resultList =query.list() ;
		
		if(resultList !=null && resultList.size()>0){
			ca=resultList.get(0);
		}
		return ca;
	}
}
