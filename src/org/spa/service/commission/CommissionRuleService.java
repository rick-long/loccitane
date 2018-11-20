package org.spa.service.commission;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.commission.CommissionRule;
import org.spa.model.product.ProductOption;
import org.spa.vo.commission.CommissionRuleVO;

/**
 * @author Ivy 2016-7-25
 */
public interface CommissionRuleService extends BaseDao<CommissionRule>{

    public void saveOrUpdate(CommissionRuleVO commissionRuleVO);
    
    public List<CommissionRule> getCommissionRuleListByFilters(Long staffId);
    
    public void deleteCommissionRule(Long id);

    CommissionRule hitCommissionRule(ProductOption option,Long userId);
    
}
