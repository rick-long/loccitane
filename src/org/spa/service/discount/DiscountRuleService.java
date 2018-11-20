package org.spa.service.discount;

import java.util.Date;
import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.discount.DiscountRule;
import org.spa.vo.discount.DiscountRuleVO;

/**
 * Created by Ivy on 2016/5/12.
 */
public interface DiscountRuleService extends BaseDao<DiscountRule>{

    public void saveOrUpdate(DiscountRuleVO discountRuleVO);
    
    public List<DiscountRule> getListByFilters(Long shopId, Long memberId,Date endTime);
    
    public List<DiscountRule> getListByFilters(Long shopId,Date endTime);
    
    public List<DiscountRule> getListByLoyaltyGroups(Long shopId,Long memberId,Date endTime);
    
    public void deleteDiscountRule(Long id);
}
