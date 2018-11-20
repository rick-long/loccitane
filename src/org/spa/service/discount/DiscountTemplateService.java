package org.spa.service.discount;

import org.spa.dao.base.BaseDao;
import org.spa.model.discount.DiscountTemplate;
import org.spa.vo.discount.DiscountTemplateVO;

/**
 * Created by Ivy on 2016/5/12.
 */
public interface DiscountTemplateService extends BaseDao<DiscountTemplate>{

    public void saveOrUpdate(DiscountTemplateVO templateVO);

}
