package org.spa.serviceImpl.bundle;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.bundle.ProductBundle;
import org.spa.model.bundle.ProductBundleProductOption;
import org.spa.service.bundle.BundleService;
import org.spa.service.company.CompanyService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductService;
import org.spa.service.shop.ShopService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.bundle.BundleItemVO;
import org.spa.vo.bundle.BundleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BundleServiceImpl extends BaseDaoHibernate<ProductBundle> implements BundleService {

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ShopService shopService;
    
    @Autowired
    private CompanyService companyService;
    
    @Override
    public void saveOrUpdate(BundleVO bundleVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        ProductBundle productBundle;
        if (bundleVO.getId() != null) {
            productBundle = get(bundleVO.getId());
            productBundle.getProductBundleProductOptions().clear();
            productBundle.getShops().clear();
            productBundle.setIsActive(bundleVO.getActive());
        } else {
            productBundle = new ProductBundle();
            productBundle.setCompany(companyService.get(bundleVO.getCompanyId()));
            productBundle.setIsActive(true);
            productBundle.setCreated(now);
            productBundle.setCreatedBy(userName);
        }

        productBundle.setCode(bundleVO.getCode());
        productBundle.setStartTime(bundleVO.getStartTime());
        productBundle.setEndTime(bundleVO.getEndTime());
        productBundle.setDescription(bundleVO.getDescription());
        productBundle.setName(bundleVO.getName());
        productBundle.setBundleAmount(bundleVO.getBundleAmount());
        productBundle.setLastUpdated(now);
        productBundle.setLastUpdatedBy(userName);
        saveOrUpdate(productBundle);
        // shops
        if(bundleVO.getShopIds() !=null && bundleVO.getShopIds().size()>0){
        	for(Long shopId : bundleVO.getShopIds()) {
        		productBundle.getShops().add(shopService.get(shopId));
            }
        }

        // product
        List<BundleItemVO> bundleItems = bundleVO.getBundleItems();
        if(bundleItems != null && bundleItems.size() > 0) {
            for (BundleItemVO itemVo : bundleItems) {
            	if(itemVo.getProductOptionIds() ==null){
            		continue;
            	}
            	for(Long poId : itemVo.getProductOptionIds()){
            		ProductBundleProductOption pbp = new ProductBundleProductOption();
            		pbp.setGroups(itemVo.getGroup());
            		pbp.setProductOption(productOptionService.get(poId));
            		pbp.setProductBundle(productBundle);
            		productBundle.getProductBundleProductOptions().add(pbp);
            	}
            }
        }
        saveOrUpdate(productBundle);
        
    }
}
