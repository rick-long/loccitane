package org.spa.service.product;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.Supplier;
import org.spa.vo.supplier.SupplierVO;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface SupplierService extends BaseDao<Supplier>{
	//
	public void saveOrUpdateSupplier(SupplierVO supplierVO);
}
