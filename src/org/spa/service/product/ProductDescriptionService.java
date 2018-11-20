package org.spa.service.product;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.Product;
import org.spa.model.product.ProductDescription;
import org.spa.vo.common.KeyAndValueVO;

import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ProductDescriptionService extends BaseDao<ProductDescription>{
    public void saveProductDescription(Product product, List<KeyAndValueVO> kvList);
}
