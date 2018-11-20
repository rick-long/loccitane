package org.spa.service.bundle;

import org.spa.dao.base.BaseDao;
import org.spa.model.bundle.ProductBundle;
import org.spa.vo.bundle.BundleVO;

public interface BundleService extends BaseDao<ProductBundle> {

    public void saveOrUpdate(BundleVO bundleVO);

}
