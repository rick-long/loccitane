package org.spa.serviceImpl.commission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.spa.dao.commission.CommissionRuleDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.commission.CommissionAttribute;
import org.spa.model.commission.CommissionRule;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.user.User;
import org.spa.model.user.UserGroup;
import org.spa.service.commission.CommissionAttributeKeyService;
import org.spa.service.commission.CommissionRuleService;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductService;
import org.spa.service.user.UserGroupService;
import org.spa.service.user.UserService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.commission.CommissionAttributeVO;
import org.spa.vo.commission.CommissionRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class CommissionRuleServiceImpl extends BaseDaoHibernate<CommissionRule> implements CommissionRuleService {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private CommissionAttributeKeyService commissionAttributeKeyService;
    
    @Autowired
    private CommissionRuleDao commissionRuleDao;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;
    
    public void saveOrUpdate(CommissionRuleVO commissionRuleVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        CommissionRule commissionRule;
        if (commissionRuleVO.getId() != null) {
            commissionRule = get(commissionRuleVO.getId());
            commissionRule.getProducts().clear();
            commissionRule.getCategories().clear();
            commissionRule.getUserGroups().clear();
            commissionRule.setIsActive(commissionRuleVO.getActive());
            commissionRule.getCommissionAttributes().clear();
            saveOrUpdate(commissionRule);
            getSession().flush();
        } else {
            commissionRule = new CommissionRule();
            commissionRule.setCompany(commissionRuleVO.getCompany());
            commissionRule.setCommissionTemplate(commissionRuleVO.getCommissionTemplate());
            commissionRule.setIsActive(true);
            commissionRule.setCreated(now);
            commissionRule.setCreatedBy(userName);
        }
        commissionRule.setDescription(commissionRuleVO.getDescription());

        // category
        Set<Category> categories = commissionRule.getCategories();
        SelectOptionVO[] categoryVOs = commissionRuleVO.getCategoryVOs();
        if (categoryVOs != null && categoryVOs.length > 0) {
            for (SelectOptionVO optionVO : categoryVOs) {
                categories.add(categoryService.get(optionVO.getValue()));
            }
        }
        // product
        Set<Product> products = commissionRule.getProducts();
        SelectOptionVO[] productVOs = commissionRuleVO.getProductVOs();
        if(productVOs != null && productVOs.length > 0) {
            for (SelectOptionVO optionVO : productVOs) {
                products.add(productService.get(optionVO.getValue()));
            }
        }
        
        //user group
        Set<UserGroup> userGroups=commissionRule.getUserGroups();
        SelectOptionVO[] userGroupVOs = commissionRuleVO.getUserGroupVOs();
        if (userGroupVOs != null && userGroupVOs.length > 0) {
            for (SelectOptionVO optionVO : userGroupVOs) {
            	userGroups.add(userGroupService.get(optionVO.getValue()));
            }
        }
        
        // commission attribute
        Set<CommissionAttribute> commissionAttributes = commissionRule.getCommissionAttributes();
        CommissionAttributeVO[] attributeVOs = commissionRuleVO.getCommissionAttributeVOs();
        if(attributeVOs != null && attributeVOs.length > 0) {
            for (CommissionAttributeVO attributeVO : attributeVOs) {
                if (StringUtils.isNoneBlank(attributeVO.getValue())) {
                    CommissionAttribute attribute = new CommissionAttribute();
                    attribute.setCommissionRule(commissionRule);
                    attribute.setCommissionAttributeKey(commissionAttributeKeyService.get(attributeVO.getCommissionAttributeKeyId()));
                    attribute.setValue(attributeVO.getValue());
                    attribute.setIsActive(true);
                    attribute.setCreated(now);
                    attribute.setCreatedBy(userName);
                    attribute.setLastUpdated(now);
                    attribute.setLastUpdatedBy(userName);
                    commissionAttributes.add(attribute);
                }
            } 
        }
        if(commissionRuleVO.getCalTarget() != null){
            commissionRule.setCalTarget(true);
        }else{
            commissionRule.setCalTarget(false);
        }
        commissionRule.setLastUpdated(now);
        commissionRule.setLastUpdatedBy(userName);
        saveOrUpdate(commissionRule);
    }

	@Override
	public List<CommissionRule> getCommissionRuleListByFilters(Long staffId) {
		Long companyId=null;
		if(WebThreadLocal.getCompany() !=null){
			companyId=WebThreadLocal.getCompany().getId();
		}
		return commissionRuleDao.getCommissionRuleListByFilters(CommonConstant.USER_GROUP_TYPE_STAFF,CommonConstant.USER_GROUP_MODULE_COMMISSION, staffId, companyId);
	}

	@Override
	public void deleteCommissionRule(Long id) {
		CommissionRule cr = get(id);
		cr.getCommissionAttributes().clear();
		cr.getProducts().clear();
		cr.getCategories().clear();
		cr.getUserGroups().clear();
		saveOrUpdate(cr);
		delete(id);
	}

    @Override
    public CommissionRule hitCommissionRule(ProductOption option,Long userId) {
        if(option == null)
            return null;
        if(userId == null || userId <= 0)
            return null;
        //判断是否有填入product
        String sql = "select c.* from commission_rule c,commission_rule_product cd where c.id = cd.commission_rule_id and c.is_active = 1" +
                " and cd.product_id = ?";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(CommissionRule.class);
        List<CommissionRule> list = sqlQuery.setParameter(0, option.getProduct().getId()).list();
        if(list != null && list.size() > 0){
            long maxCommission = getMaxCommission(list, userId);
            //匹配了product就返回，没有就返回null
            if(maxCommission > 0){
                return get(maxCommission);
            }
            return null;
        }else{
            //判断是否有category
            String cate_sql = "select c.* from category c,product p where c.id = p.category_id and c.is_active = 1" +
                    " and p.id = ?";
            SQLQuery cate_query = categoryService.getSession().createSQLQuery(cate_sql).addEntity(Category.class);
            Category category = (Category) cate_query.setParameter(0, option.getProduct().getId()).uniqueResult();
            if(category == null){
                return null;
            }
            String com_sql = "select c.* from commission_rule c,commission_rule_category cc where c.id = cc.commission_rule_id and c.is_active = 1" +
                    " and cc.category_id = ?";
            SQLQuery com_Query = getSession().createSQLQuery(com_sql).addEntity(CommissionRule.class);
            List<CommissionRule> com_list = com_Query.setParameter(0, category.getId()).list();
            if(com_list == null || com_list.size() == 0){
                return null;
            }
            long maxCommission = getMaxCommission(com_list, userId);
            if(maxCommission > 0){
                return get(maxCommission);
            }
            return null;
        }
    }

    private long getMaxCommission(List<CommissionRule> commissionRules,long userId){
        long maxRuleId = 0;
        Double maxRuleRate = 0d;
        Object[] objects = new Object[]{maxRuleId, maxRuleRate};
        for(CommissionRule commissionRule : commissionRules){
            //如果usergroup为null 则对所有的用户生效
            String com_sql = "select u.* from commission_rule_user_group c,user_group u where c.user_group_id = u.id and u.is_active = 1" +
                    " and c.commission_rule_id = ?";
            SQLQuery group_Query = userGroupService.getSession().createSQLQuery(com_sql).addEntity(UserGroup.class);
            List<UserGroup> group_list = group_Query.setParameter(0, commissionRule.getId()).list();
            if(group_list != null && group_list.size() > 0){
                for(UserGroup userGroup : group_list){
                    String user_sql = "select u.* from user u,user_user_group ug where ug.user_id = u.id and u.activing = 1" +
                            " and ug.user_group_id = ? and u.id = ?";
                    SQLQuery user_Query = userService.getSession().createSQLQuery(user_sql).addEntity(User.class);
                    User user = (User) user_Query.setParameter(0, userGroup.getId()).setParameter(1, userId).uniqueResult();
                    if(user != null){
                        Set<CommissionAttribute> commissionAttributes = commissionRule.getCommissionAttributes();
                        getMaxRuleId(commissionAttributes, commissionRule,objects);
                        continue;
                    }
                }
            }else {
                Set<CommissionAttribute> commissionAttributes = commissionRule.getCommissionAttributes();
                getMaxRuleId(commissionAttributes, commissionRule,objects);
            }
        }
        return (long)objects[0];
    }

    private void getMaxRuleId(Set<CommissionAttribute> commissionAttributes,CommissionRule commissionRule,Object[] objects){
        if(commissionAttributes.size() > 0){
            for(CommissionAttribute ca : commissionAttributes){
                if("RATE".equals(ca.getCommissionAttributeKey().getReference())){
                    if(Double.valueOf(ca.getValue()) > (Double) objects[1]){
                        objects[1] =  Double.valueOf(ca.getValue());
                        objects[0] = commissionRule.getId();
                    }
                    continue;
                }
            }
        }else{
            if((Double) objects[1] == 0){
                objects[0] = commissionRule.getId();
            }
        }
    }
}
