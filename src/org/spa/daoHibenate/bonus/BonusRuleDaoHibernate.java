package org.spa.daoHibenate.bonus;

import org.spa.dao.bonus.BonusRuleDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.bonus.BonusRule;
import org.springframework.stereotype.Repository;
@Repository
public class BonusRuleDaoHibernate extends BaseDaoHibernate<BonusRule> implements BonusRuleDao{
	
}
