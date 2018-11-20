package org.spa.daoHibenate.bonus;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.bonus.BonusAttributeDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.bonus.BonusAttribute;
import org.springframework.stereotype.Repository;

@Repository
public class BonusAtrributeDaoHibernate extends BaseDaoHibernate<BonusAttribute> implements BonusAttributeDao{
	
	@Override
	public BonusAttribute getBonusAttributeByRuleAndKeyRef(final String keyRef,final Long bonusRuleId,final Long companyId) {
		
		BonusAttribute ca=null;
		
		StringBuffer sql=new StringBuffer();
		sql.append("select ca.* from BONUS_ATTRIBUTE ca ");
		sql.append("LEFT JOIN BONUS_ATTRIBUTE_KEY cak on ca.bonus_attribute_key_id=cak.id ");
		sql.append("LEFT JOIN BONUS_TEMPLATE ct on cak.bonus_template_id=ct.id ");
		sql.append("where ca.bonus_rule_id=? ");
		sql.append("and cak.reference=? ");
		if(companyId !=null){
			sql.append(" and ct.company_id= ?");
		}
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());

		query.addEntity(BonusAttribute.class);
		

		query.setLong(0, bonusRuleId);
		query.setString(1, keyRef);
		if(companyId !=null){
			query.setLong(2, companyId);
		}
		
		List<BonusAttribute> resultList =query.list() ;
		
		if(resultList !=null && resultList.size()>0){
			ca=resultList.get(0);
		}
		return ca;
	}
}
