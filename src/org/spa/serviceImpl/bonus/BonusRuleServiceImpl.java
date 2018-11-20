package org.spa.serviceImpl.bonus;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.bonus.BonusAttribute;
import org.spa.model.bonus.BonusRule;
import org.spa.service.bonus.BonusAttributeKeyService;
import org.spa.service.bonus.BonusRuleService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.bonus.BonusAttributeVO;
import org.spa.vo.bonus.BonusRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class BonusRuleServiceImpl extends BaseDaoHibernate<BonusRule> implements BonusRuleService {
	
    @Autowired
    private BonusAttributeKeyService bonusAttributeKeyService;  
    
    public void saveOrUpdate(BonusRuleVO bonusRuleVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        BonusRule bonusRule;
        if (bonusRuleVO.getId() != null) {
            bonusRule = get(bonusRuleVO.getId());
            bonusRule.getBonusAttributes().clear();
            saveOrUpdate(bonusRule);
            getSession().flush();
        } else {
            bonusRule = new BonusRule();
            bonusRule.setCompany(bonusRuleVO.getCompany());
            bonusRule.setBonusTemplate(bonusRuleVO.getBonusTemplate());
            bonusRule.setIsActive(true);
            bonusRule.setCreated(now);
            bonusRule.setCreatedBy(userName);
        }
        bonusRule.setDescription(bonusRuleVO.getDescription());
        
        // bonus attribute
        Set<BonusAttribute> bonusAttributes = bonusRule.getBonusAttributes();
        BonusAttributeVO[] attributeVOs = bonusRuleVO.getBonusAttributeVOs();
        if(attributeVOs != null && attributeVOs.length > 0) {
            for (BonusAttributeVO attributeVO : attributeVOs) {
                if (StringUtils.isNoneBlank(attributeVO.getValue())) {
                    BonusAttribute attribute = new BonusAttribute();
                    attribute.setBonusRule(bonusRule);
                    attribute.setBonusAttributeKey(bonusAttributeKeyService.get(attributeVO.getBonusAttributeKeyId()));
                    attribute.setValue(attributeVO.getValue());
                    attribute.setIsActive(true);
                    attribute.setCreated(now);
                    attribute.setCreatedBy(userName);
                    attribute.setLastUpdated(now);
                    attribute.setLastUpdatedBy(userName);
                    bonusAttributes.add(attribute);
                }
            } 
        }
        
        bonusRule.setLastUpdated(now);
        bonusRule.setLastUpdatedBy(userName);
        saveOrUpdate(bonusRule);
    }
}
