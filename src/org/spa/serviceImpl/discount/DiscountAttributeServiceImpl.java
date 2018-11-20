package org.spa.serviceImpl.discount;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.discount.DiscountAttribute;
import org.spa.service.discount.DiscountAttributeService;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-5-12
 */
@Service
public class DiscountAttributeServiceImpl extends BaseDaoHibernate<DiscountAttribute> implements DiscountAttributeService {

    @Override
    public Long count(Long attributeKeyId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(DiscountAttribute.class);
        criteria.add(Restrictions.eq("discountAttributeKey.id", attributeKeyId));
        return getCount(criteria);

    }
}
