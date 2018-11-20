package com.spa.plugin.discount;

import com.spa.tools.drools.KnowLedgeBaseReader;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.discount.DiscountRule;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOption;
import org.spa.service.discount.DiscountRuleService;
import org.spa.serviceImpl.discount.DiscountRuleServiceImpl;
import org.spa.utils.SpringUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Ivy on 2016-6-17
 */
public class DiscountItemPlugin {

    private static final Logger logger = LoggerFactory.getLogger(DiscountItemPlugin.class);

    private static final DiscountRuleService discountRuleService = SpringUtil.getBean(DiscountRuleServiceImpl.class);

    /**
     * discount item fire process
     *
     * @param itemAdapter
     * @param shopId
     * @param memberId
     */
    public static void process(DiscountItemAdapter itemAdapter, Long shopId, Long memberId) {
        //  查找系统所有的discount rule
    	Date endTime = new Date();
    	//user group
        List<DiscountRule> discountRuleList = discountRuleService.getListByFilters(shopId, memberId, endTime);
        if (discountRuleList == null || discountRuleList.size() == 0) {
            logger.debug("No discount rule find for user group----");
        }else{
        	logger.debug("discountRuleList------------"+discountRuleList.size());
        }
        //loyalty groups
        List<DiscountRule> discountRuleList2 = discountRuleService.getListByLoyaltyGroups(shopId, memberId, endTime);
        if (discountRuleList2 == null || discountRuleList2.size() == 0) {
            logger.debug("No discount rule find for loyalty group ----");
        }else{
        	logger.debug("discountRuleList2------------"+discountRuleList2.size());
        }
        
        discountRuleList.addAll(discountRuleList2);
        
        if (discountRuleList == null || discountRuleList.size() == 0) {
            logger.debug("No discount rule find for shop:{}", shopId);
            return;
        }
        
        Stream<DiscountRule> discountRuleStream = discountRuleList.stream();

        // get user groups by member
//        Long companyId=WebThreadLocal.getCompany().getId();
//        List<UserGroup> ugList = userGroupService.getUserGroupByFilters(memberId, CommonConstant.USER_GROUP_TYPE_MEMBER, CommonConstant.USER_GROUP_MODULE_DISCOUNT, companyId);
        
        ProductOption productOption = itemAdapter.getProductOption();
        // 过滤产品或者目录
        List<DiscountRule> availableDiscountRuleList = discountRuleStream.filter(item -> {
        	
    		if (productOption.getProduct() !=null && item.getProducts().contains(productOption.getProduct())) {
                return true;
            }
            
            Set<Category> categorySet = item.getCategories();
            for (Category category =  productOption !=null ? productOption.getProduct().getCategory() : itemAdapter.getCategory();category.getCategory() != null; category = category.getCategory()) {
                if (categorySet.contains(category)) {
                    return true;
                }
            }
            
            //过滤user group
            /*if(ugList !=null && ugList.size() > 0 ){
            	 Set<UserGroup> userGroupSet=item.getUserGroups();
                 for (UserGroup ug : ugList) {
                     if (userGroupSet.contains(ug)) {
                         return true;
                     }
                 }
            }*/
           
            return false;
        }).collect(Collectors.toList());

        // 计算最好的折扣

        BigDecimal maxDiscountValue = new BigDecimal(0);
        DiscountRule maxDiscountRule = null;
        for (DiscountRule discountRule : availableDiscountRuleList) {
        	
            StatefulKnowledgeSession session = KnowLedgeBaseReader.getSession(discountRule.getDiscountTemplate().getContent());
            session.insert(discountRule);
            session.insert(itemAdapter);
            session.fireAllRules();
            session.dispose();

            // 记录最大的discount
            if (maxDiscountValue.compareTo(itemAdapter.getDiscountValue()) < 0) {
                maxDiscountValue = itemAdapter.getDiscountValue();
                maxDiscountRule = discountRule;
            }
        }
        logger.debug("DiscountItemPlugin----final discount :{}", maxDiscountValue);
        // 回填最大的discount和应用的规则
        itemAdapter.setDiscountValue(maxDiscountValue);
        itemAdapter.setDiscountRule(maxDiscountRule);
    }

}
