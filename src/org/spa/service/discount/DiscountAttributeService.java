package org.spa.service.discount;

import org.spa.dao.base.BaseDao;
import org.spa.model.discount.DiscountAttribute;

/**
 * Created by Ivy on 2016/5/12.
 */
public interface DiscountAttributeService extends BaseDao<DiscountAttribute>{


    Long count(Long attributeKeyId);
}
