package com.spa.plugin.bonus;

import com.spa.constant.CommonConstant;
import com.spa.tools.drools.KnowLedgeBaseReader;
import org.drools.runtime.StatefulKnowledgeSession;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.bonus.BonusRule;
import org.spa.model.product.Category;
import org.spa.service.bonus.BonusRuleService;
import org.spa.serviceImpl.bonus.BonusRuleServiceImpl;
import org.spa.utils.SpringUtil;
import org.spa.utils.WebThreadLocal;
import java.util.List;
import java.util.Set;


/**
 * @author Ivy on 2016-8-3
 */
public class BonusCalculationPlugin {

    private static final Logger logger = LoggerFactory.getLogger(BonusCalculationPlugin.class);

    private static final BonusRuleService bonusRuleService =SpringUtil.getBean(BonusRuleServiceImpl.class);

    /**
     * bonus fire process
     *
     * @param BonusAdapter
     */
    public static void process(BonusAdapter bonusAdapter) {
    	
    	/*DetachedCriteria dc = DetachedCriteria.forClass(BonusRule.class);
    	List<BonusRule> bonusRuleList=bonusRuleService.getActiveListByRefAndCompany(dc, null, WebThreadLocal.getCompany().getId());
        BonusRule finalBonusRule=getFinalBonusRule(bonusAdapter, bonusRuleList);
        logger.debug("check out set item cal bonus::::::finalBonusRule{}:" + (finalBonusRule !=null ? finalBonusRule.getDescription() : null));
        
        // 计算正确的bonus
        if(finalBonusRule !=null 
        	&& bonusAdapter.getCurrentDate().before(finalBonusRule.getEndDate())
        	&& bonusAdapter.getCurrentDate().after(finalBonusRule.getStartDate())
        	&& bonusAdapter.getTotalSales() >= bonusAdapter.getTargetAmount()){
        	
			if(bonusAdapter.getCategoryRef().equals(CommonConstant.CATEGORY_REF_TREATMENT)){
				if(){
					
				}
			}else if(bonusAdapter.getCategoryRef().equals(CommonConstant.CATEGORY_REF_GOODS)){
				bonusAdapter.setBonus(finalBonusRule);
			}
        }else{
        	bonusAdapter.setBonus(0d);
        }
        */
    }
    
   /* private static BonusRule getFinalBonusRule(BonusAdapter bonusAdapter,List<BonusRule> bonusRuleList){
    	
    	Category category = bonusAdapter.getCategory();
    	logger.debug("check out set item cal bonus::::::BonusAdapter:::::::category:"+category.getName());
    	while(category.getCategory() != null) {
//    		System.out.println("--------------parent category------"+(category.getCategory() !=null ? category.getCategory().getName() : null));
    		for (BonusRule bonusRule : bonusRuleList) {
//    			System.out.println("--------------bonusRule------"+bonusRule.getId());
    			Set<Category> categorySet = bonusRule.getCategories();
//        		System.out.println("--------------categorySet----contain ?? ---------"+categorySet.contains(category));
	            if(categorySet.contains(category)){
	            	return bonusRule;
	            }
        	}
    		category=category.getCategory();//parent category
        }
        return null;
    }*/
}
