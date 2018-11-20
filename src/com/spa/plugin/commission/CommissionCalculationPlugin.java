package com.spa.plugin.commission;

import com.spa.tools.drools.KnowLedgeBaseReader;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.commission.CommissionRule;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.service.commission.CommissionRuleService;
import org.spa.serviceImpl.commission.CommissionRuleServiceImpl;
import org.spa.utils.SpringUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


/**
 * @author Ivy on 2016-8-3
 */
public class CommissionCalculationPlugin {

    private static final Logger logger = LoggerFactory.getLogger(CommissionCalculationPlugin.class);

    private static final CommissionRuleService commissionRuleService =SpringUtil.getBean(CommissionRuleServiceImpl.class);
    public static void process1(CommissionAdapter commissionAdapter) {
    	
    	Long staffId=commissionAdapter.getStaffId();
    	ProductOption productOption = commissionAdapter.getProductOption();
    	CommissionRule commissionRule = commissionRuleService.hitCommissionRule(productOption, staffId);
    	 logger.debug("check out set item cal commission::::::finalCommissionRule{}:" + (commissionRule !=null ? commissionRule.getDescription() : null));
         // 计算正确的commission
         if(commissionRule !=null){
         	StatefulKnowledgeSession session = KnowLedgeBaseReader.getSession(commissionRule.getCommissionTemplate().getContent());
            session.insert(commissionRule);
            session.insert(commissionAdapter);
            session.fireAllRules();
            session.dispose();
         }else{
         	commissionAdapter.setCommissionRate(new BigDecimal(0));
         	commissionAdapter.setCommission(new BigDecimal(0));
         	
         	commissionAdapter.setExtraCommissionRate(new BigDecimal(0));
         	commissionAdapter.setExtraCommission(new BigDecimal(0));
         	
         	commissionAdapter.setTargetCommission(new BigDecimal(0));
         	commissionAdapter.setTargetCommissionRate(new BigDecimal(0));
         	
         	commissionAdapter.setTargetExtraCommission(new BigDecimal(0));
         	commissionAdapter.setTargetExtraCommissionRate(new BigDecimal(0));
         }
    }
    /**
     * commission fire process
     *
     * @param CommissionAdapter
     */
    public static void process(CommissionAdapter commissionAdapter) {
        
    	Long staffId=commissionAdapter.getStaffId();
        //  查找系统所有有效的
        List<CommissionRule> commissionRuleList =commissionRuleService.getCommissionRuleListByFilters(staffId);
        if (commissionRuleList == null || commissionRuleList.size() == 0) {
            logger.debug("No commission rule find::");
            return;
        }
        logger.debug("check out set item cal commission::::::commissionRuleList size:"+commissionRuleList.size());
    	/*Stream<CommissionRule> commissionRuleStream=commissionRuleList.stream();
    	List<CommissionRule> availableCommissionRuleList = commissionRuleStream.filter(item -> {
            Set<Category> categorySet = item.getCategories();
            for (Category category = commissionAdapter.getCategory(); category.getCategory() != null; category = category.getCategory()) {
                if (categorySet.contains(category)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());*/
    	
        CommissionRule finalCommissionRule=getFinalCommissionRule(commissionAdapter, commissionRuleList);
        
        logger.debug("check out set item cal commission::::::finalCommissionRule{}:" + (finalCommissionRule !=null ? finalCommissionRule.getDescription() : null));
        // 计算正确的commission
        if(finalCommissionRule !=null){
        	StatefulKnowledgeSession session = KnowLedgeBaseReader.getSession(finalCommissionRule.getCommissionTemplate().getContent());
            session.insert(finalCommissionRule);
            session.insert(commissionAdapter);
            session.fireAllRules();
            session.dispose();
        }else{
        	commissionAdapter.setCommissionRate(new BigDecimal(0));
         	commissionAdapter.setCommission(new BigDecimal(0));
         	
         	commissionAdapter.setExtraCommissionRate(new BigDecimal(0));
         	commissionAdapter.setExtraCommission(new BigDecimal(0));
         	
         	commissionAdapter.setTargetCommission(new BigDecimal(0));
         	commissionAdapter.setTargetCommissionRate(new BigDecimal(0));
         	
         	commissionAdapter.setTargetExtraCommission(new BigDecimal(0));
         	commissionAdapter.setTargetExtraCommissionRate(new BigDecimal(0));
        }
        System.out.println("effectiveValue = "+commissionAdapter.getEffectiveValue()+",commissionRate = "+commissionAdapter.getCommissionRate()+",commission = "+commissionAdapter.getCommission()
        +",extraCommissionRate = "+commissionAdapter.getExtraCommissionRate()+ ",extraCommission = "+commissionAdapter.getExtraCommission()+
        ",targetCommissionRate = "+commissionAdapter.getTargetCommissionRate()+",targetCommission="+commissionAdapter.getTargetCommission()+
        ",targetExtraCommissionRate="+commissionAdapter.getTargetExtraCommissionRate()+",targetExtraCommission="+commissionAdapter.getTargetExtraCommission());
    }
    
    private static CommissionRule getFinalCommissionRule(CommissionAdapter commissionAdapter,List<CommissionRule> commissionRuleList){
    	
    	Category category = commissionAdapter.getCategory();
    	Product product = commissionAdapter.getProduct();
    	logger.debug("check out set item cal commission::::::CommissionAdapter:::::::category:"+category.getName());
    	while(category.getCategory() != null) {
//    		System.out.println("--------------parent category------"+(category.getCategory() !=null ? category.getCategory().getName() : null));
    		for (CommissionRule commissionRule : commissionRuleList) {
//    			System.out.println("--------------commissionRule------"+commissionRule.getId());
//    			System.out.println("--------------product contains------"+commissionRule.getProducts().contains(product)
//    					+"--commissionRule.getProducts()---"+commissionRule.getProducts()+"--product--"+product.getId());
    			if(commissionRule.getProducts().contains(product)){
    				return commissionRule;
    			}
    			Set<Category> categorySet = commissionRule.getCategories();
//        		System.out.println("--------------categorySet----contain ?? ---------"+categorySet.contains(category));
	            if(categorySet.contains(category)){
	            	return commissionRule;
	            }
        	}
    		category=category.getCategory();//parent category
        }
        return null;
    }
}
