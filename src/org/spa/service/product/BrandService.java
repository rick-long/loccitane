package org.spa.service.product;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.Brand;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface BrandService extends BaseDao<Brand>{
	//
	public List<Brand> getBrandsSortBy(Long companyId,Boolean isActive,String sortProperties);
}
