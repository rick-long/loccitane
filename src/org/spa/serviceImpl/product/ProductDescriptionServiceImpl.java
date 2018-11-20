package org.spa.serviceImpl.product;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Product;
import org.spa.model.product.ProductDescription;
import org.spa.model.product.ProductDescriptionKey;
import org.spa.service.product.ProductDescriptionKeyService;
import org.spa.service.product.ProductDescriptionService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.KeyAndValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ProductDescriptionServiceImpl extends BaseDaoHibernate<ProductDescription> implements ProductDescriptionService {

    @Autowired
    private ProductDescriptionKeyService productDescriptionKeyService;

    @Override
    public void saveProductDescription(Product product, List<KeyAndValueVO> kvList) {
        if (kvList == null || kvList.size() == 0 || product == null) {
            return;
        }
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        //product description
        if (kvList != null && kvList.size() > 0) {
            product.getProductDescriptions().clear();
            ProductDescriptionKey pdk = null;
            ProductDescription pd = null;
            for (KeyAndValueVO kv : kvList) {
                if(kv.getId() != null) {
                    pd = get(kv.getId());
                } else {
                    pd = new ProductDescription();
                    pdk = productDescriptionKeyService.get(Long.valueOf(kv.getKey()));
                    pd.setProductDescriptionKey(pdk);
                    pd.setCompany(product.getCompany());
                    pd.setProduct(product);
                    pd.setIsActive(true);
                    pd.setCreated(now);
                    pd.setCreatedBy(userName);
                }

                pd.setValue(kv.getValue());
                pd.setLastUpdated(now);
                pd.setLastUpdatedBy(userName);
                saveOrUpdate(pd);
            }
        }
    }
}
