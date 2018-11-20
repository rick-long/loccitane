package org.spa.serviceImpl.product;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.ProductOption;
import org.spa.model.product.ProductOptionSupernumeraryPrice;
import org.spa.model.shop.Shop;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductOptionSupernumeraryPriceService;
import org.spa.service.shop.ShopService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.product.SupernumeraryPriceAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ProductOptionSupernumeraryPriceServiceImpl extends BaseDaoHibernate<ProductOptionSupernumeraryPrice> implements ProductOptionSupernumeraryPriceService {

    @Autowired
    public ProductOptionService productOptionService;

    @Autowired
    public ShopService shopService;

    @Override
    public void saveProductOptionSupernumeraryPrice(SupernumeraryPriceAddVO supernumeraryPriceAddVO) {

        List<KeyAndValueVO> kvList = supernumeraryPriceAddVO.getShopPriceList();
        if (kvList != null && kvList.size() > 0) {

            Shop shop = null;
            ProductOption po = null;
            ProductOptionSupernumeraryPrice posp = null;
            for (KeyAndValueVO kv : kvList) {
                if (kv.getId() != null && kv.getId().longValue() > 0) {
                    posp = get(Long.valueOf(kv.getId()));
                    posp.setLastUpdated(new Date());
                    posp.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
                } else {
                    posp = new ProductOptionSupernumeraryPrice();
                    shop = shopService.get(Long.valueOf(kv.getKey()));
                    posp.setShop(shop);
                    posp.setOriginalPrice(supernumeraryPriceAddVO.getOriginalPrice());
                    posp.setCreated(new Date());
                    posp.setIsActive(true);
                    posp.setCreatedBy(WebThreadLocal.getUser().getUsername());
                    posp.setCompany(shop.getCompany());

                    po = productOptionService.get(supernumeraryPriceAddVO.getProductOptionId());
                    posp.setProductOption(po);
                }
                double additionalPrice = 0;
                if (StringUtils.isNoneBlank(kv.getValue())) {
                    additionalPrice = Double.valueOf(kv.getValue());
                }
                posp.setAdditionalPrice(additionalPrice);
                save(posp);
            }
        }
    }

    @Override
    public ProductOptionSupernumeraryPrice getProductOptionSupernumeraryPriceByShopAndPO(Long productOptionId,
                                                                                         Long shopId) {
        ProductOptionSupernumeraryPrice posp = null;
        DetachedCriteria dc = DetachedCriteria.forClass(ProductOptionSupernumeraryPrice.class);
        //
        dc.add(Restrictions.eq("isActive", true));

        if (shopId != null) {
            dc.add(Restrictions.eq("shop.id", shopId));
        }
        if (productOptionId != null) {
            dc.add(Restrictions.eq("productOption.id", productOptionId));
        }

        List<ProductOptionSupernumeraryPrice> pospList = list(dc);
        if (pospList != null && pospList.size() > 0) {
            posp = pospList.get(0);
        }
        return posp;
    }
    @Override
    public List<ProductOptionSupernumeraryPrice> getProductOptionSupernumeraryPrices(Long productOptionId, List<Long> shopIds) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProductOptionSupernumeraryPrice.class);
        //
        if (shopIds==null||productOptionId==null){
            return null;
        }
            dc.add(Restrictions.eq("isActive", true));
            dc.add(Restrictions.in("shop.id", shopIds));
            dc.add(Restrictions.eq("productOption.id", productOptionId));
        List<ProductOptionSupernumeraryPrice> pospList = list(dc);
        return pospList;
    }


}
