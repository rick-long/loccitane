package org.spa.serviceImpl.product;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Brand;
import org.spa.service.product.BrandService;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class BrandServiceImpl extends BaseDaoHibernate<Brand> implements BrandService{
	public List<Brand> getBrandsSortBy(Long companyId,Boolean isActive,String sortProperties){
		DetachedCriteria brandDC = DetachedCriteria.forClass(Brand.class);
        brandDC.add(Restrictions.eq("isActive", isActive));
        brandDC.addOrder(Order.asc(sortProperties));
        if (companyId != null) {
            brandDC.add(Restrictions.eq("company.id", companyId));
        }
        List<Brand> brandList = list(brandDC);
        return brandList;
	}

}
