package org.spa.serviceImpl.discount;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.discount.DiscountAttribute;
import org.spa.model.discount.DiscountRule;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.shop.Shop;
import org.spa.model.user.UserGroup;
import org.spa.service.discount.DiscountAttributeKeyService;
import org.spa.service.discount.DiscountRuleService;
import org.spa.service.loyalty.LoyaltyLevelService;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductService;
import org.spa.service.user.UserGroupService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.discount.DiscountAttributeVO;
import org.spa.vo.discount.DiscountRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-5-12
 */
@Service
public class DiscountRuleServiceImpl extends BaseDaoHibernate<DiscountRule> implements DiscountRuleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private LoyaltyLevelService loyaltyLevelService;
    
    @Autowired
    private DiscountAttributeKeyService discountAttributeKeyService;

    public void saveOrUpdate(DiscountRuleVO discountRuleVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        DiscountRule discountRule;
        if (discountRuleVO.getId() != null) {
            discountRule = get(discountRuleVO.getId());
            discountRule.getCategories().clear();
            discountRule.getProducts().clear();
            discountRule.getUserGroups().clear();
            discountRule.getLoyaltyGroups().clear();
            discountRule.getDiscountAttributes().clear();
            discountRule.getShops().clear();
            saveOrUpdate(discountRule);
            getSession().flush();
        } else {
            discountRule = new DiscountRule();
            discountRule.setCompany(discountRuleVO.getCompany());
            discountRule.setDiscountTemplate(discountRuleVO.getDiscountTemplate());
            discountRule.setIsActive(true);
            discountRule.setCreated(now);
            discountRule.setCreatedBy(userName);
        }

        discountRule.setCode(discountRuleVO.getCode());
        discountRule.setStartTime(discountRuleVO.getStartTime());
        discountRule.setEndTime(discountRuleVO.getEndTime());
        discountRule.setDescription(discountRuleVO.getDescription());

        // category
        Set<Category> categories = discountRule.getCategories();
        SelectOptionVO[] categoryVOs = discountRuleVO.getCategoryVOs();
        if (categoryVOs != null && categoryVOs.length > 0) {
            for (SelectOptionVO optionVO : categoryVOs) {
                categories.add(categoryService.get(optionVO.getValue()));
            }
        }

        // shops
        Set<Shop> shopSet = discountRule.getShops();
        shopSet.addAll(discountRuleVO.getShopList());

        // product
        Set<Product> products = discountRule.getProducts();
        SelectOptionVO[] productVOs = discountRuleVO.getProductVOs();
        if(productVOs != null && productVOs.length > 0) {
            for (SelectOptionVO optionVO : productVOs) {
                products.add(productService.get(optionVO.getValue()));
            }
        }

        //user group
        Set<UserGroup> userGroups=discountRule.getUserGroups();
        SelectOptionVO[] userGroupVOs = discountRuleVO.getUserGroupIds();
        if (userGroupVOs != null && userGroupVOs.length > 0) {
            for (SelectOptionVO optionVO : userGroupVOs) {
                userGroups.add(userGroupService.get(optionVO.getValue()));
            }
        }

        //loyalty group
        Set<LoyaltyLevel> loyaltyGroups=discountRule.getLoyaltyGroups();
        SelectOptionVO[] loyaltyGroupVOs = discountRuleVO.getLoyaltyGroupIds();
        if (loyaltyGroupVOs != null && loyaltyGroupVOs.length > 0) {
            for (SelectOptionVO optionVO : loyaltyGroupVOs) {
            	loyaltyGroups.add(loyaltyLevelService.get(optionVO.getValue()));
            }
        }
        
        // discount attribute
        Set<DiscountAttribute> discountAttributes = discountRule.getDiscountAttributes();
        DiscountAttributeVO[] attributeVOs = discountRuleVO.getDiscountAttributeVOs();
        if(attributeVOs != null && attributeVOs.length > 0) {
            for (DiscountAttributeVO attributeVO : attributeVOs) {
                if (StringUtils.isNoneBlank(attributeVO.getValue())) {
                    DiscountAttribute attribute = new DiscountAttribute();
                    attribute.setDiscountRule(discountRule);
                    attribute.setDiscountAttributeKey(discountAttributeKeyService.get(attributeVO.getDiscountAttributeKeyId()));
                    attribute.setValue(attributeVO.getValue());
                    attribute.setIsActive(true);
                    attribute.setCreated(now);
                    attribute.setCreatedBy(userName);
                    attribute.setLastUpdated(now);
                    attribute.setLastUpdatedBy(userName);
                    discountAttributes.add(attribute);
                }
            } 
        }
        
        discountRule.setLastUpdated(now);
        discountRule.setLastUpdatedBy(userName);
        saveOrUpdate(discountRule);
    }

    @Override
    public List<DiscountRule> getListByFilters(Long shopId, Long memberId,Date endTime) {
    	
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DiscountRule.class);
        detachedCriteria.add(Restrictions.eq("isActive", true));
        
        if(shopId !=null){
        	detachedCriteria.createAlias("shops", "s");
            detachedCriteria.add(Restrictions.eq("s.id", shopId));
        }
        if(memberId !=null){
        	//userGroups
        	detachedCriteria.createAlias("userGroups", "ugs");
            detachedCriteria.createAlias("ugs.users", "u");
            detachedCriteria.add(Restrictions.eq("u.id", memberId));
        }
        if(endTime !=null){
        	 detachedCriteria.add(Restrictions.ge("endTime", endTime)); // 还没有结束
        }
        
        return list(detachedCriteria);
    }
    
    @Override 
    public List<DiscountRule> getListByFilters(Long shopId,Date endTime) {
    	return getListByFilters(shopId, null, endTime);
    }
    
    @Override 
    public List<DiscountRule> getListByLoyaltyGroups(Long shopId,Long memberId,Date endTime){
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DiscountRule.class);
    	detachedCriteria.add(Restrictions.eq("isActive", true));
    	
    	if(shopId !=null){
    		detachedCriteria.createAlias("shops", "s");
    		detachedCriteria.add(Restrictions.eq("s.id", shopId));
    	}
        if(memberId !=null){
        	//userGroups
        	detachedCriteria.createAlias("loyaltyGroups", "ll");
            detachedCriteria.createAlias("ll.userLoyaltyLevels", "ull");
            detachedCriteria.add(Restrictions.eq("ull.isActive", true));
            detachedCriteria.add(Restrictions.eq("ull.user.id", memberId));
        }
        if(endTime !=null){
       	 	detachedCriteria.add(Restrictions.ge("endTime", endTime)); // 还没有结束
        }
        return list(detachedCriteria);
    }

	@Override
	public void deleteDiscountRule(Long id) {
		
		DiscountRule dr = get(id);
		
		dr.getDiscountAttributes().clear();
		dr.getCategories().clear();
		dr.getProducts().clear();
		dr.getLoyaltyGroups().clear();
		dr.getUserGroups().clear();
		saveOrUpdate(dr);
		
		delete(id);
	}
}
