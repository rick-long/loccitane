package org.spa.serviceImpl.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.productOption.ProductOptionDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.product.ProductOptionAttribute;
import org.spa.model.product.ProductOptionKey;
import org.spa.service.product.ProductOptionAttributeService;
import org.spa.service.product.ProductOptionKeyService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.page.Page;
import org.spa.vo.product.PoItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ProductOptionServiceImpl extends BaseDaoHibernate<ProductOption> implements ProductOptionService{
	
	@Autowired
	public ProductOptionAttributeService productOptionAttributeService;
	
	@Autowired
	public ProductOptionKeyService productOptionKeyService;

	@Autowired
	public ProductService productService;

	private final static String BARCODE = "barcode";

    private final static String CODE = "code";
    @Autowired
    private ProductOptionDao productOptionDao;


    @Override
    public void saveProductOption(Product product, List<PoItemVO> poItemList)throws Exception{
        if (poItemList == null || poItemList.size() == 0 || product == null) {
            return;
        }
        
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        for (PoItemVO poItemVO : poItemList) {
            ProductOption po;
            if (poItemVO.getPoId() != null && poItemVO.getPoId() > 0) {
                po = get(poItemVO.getPoId());
            } else {
                po = new ProductOption();
                po.setProduct(product);
                po.setIsActive(true);
                po.setCreated(now);
                po.setCreatedBy(userName);
            }
            po.setLastUpdated(now);
            po.setLastUpdatedBy(userName);
            po.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PRODUCT_OPTION_REF_PREFIX));

            List<KeyAndValueVO> kvList = poItemVO.getPoValues();
            ProductOptionKey pok = null;
            ProductOptionAttribute poa = null;

            if(kvList == null || kvList.size() == 0 ){
                delete(po.getId());
                getSession().flush();
            }else{
                int i = 0;
                for(KeyAndValueVO kv : kvList){
                   if(StringUtils.isBlank(kv.getValue())){
                       i += 1;
                   }
                }
                if(i == kvList.size()){
                    delete(po.getId());
                    getSession().flush();
                }else{
                    for (KeyAndValueVO kv : kvList) {
                        String key = kv.getKey();
                        String val = kv.getValue();
                        pok = productOptionKeyService.get(Long.valueOf(key));

                        if (BARCODE.equalsIgnoreCase(pok.getReference())) {
                            po.setBarcode(val);
                        }
                        saveOrUpdate(po);
                        if (kv.getId() != null && kv.getId() > 0) {
                            poa = productOptionAttributeService.get(kv.getId());
                            if(poa != null){
                                if (CODE.equalsIgnoreCase(pok.getReference()) || BARCODE.equalsIgnoreCase(pok.getReference())) {
                                    if (StringUtils.isNotBlank(val)) {
                                        poa.setValue(val);
                                    } else {
                                        poa.setValue(RandomUtil.generateRandomNumberWithTime(""));
                                    }
                                } else {
                                    if(StringUtils.isNotBlank(kv.getValue())){
                                        poa.setValue(val);
                                    }else{
                                        productOptionAttributeService.delete(poa.getId());
                                        continue;
                                    }
                                }
                            }
                            poa.setLastUpdated(now);
                            poa.setLastUpdatedBy(userName);
                        } else {
                            if (CODE.equalsIgnoreCase(pok.getReference()) || BARCODE.equalsIgnoreCase(pok.getReference())) {
                                poa = new ProductOptionAttribute();
                                poa.setIsActive(true);
                                poa.setProductOption(po);
                                poa.setProductOptionKey(pok);
                                poa.setCreated(now);
                                poa.setCreatedBy(userName);
                                if (StringUtils.isNotBlank(val)) {
                                    poa.setValue(val);
                                } else {
                                    poa.setValue(RandomUtil.generateRandomNumberWithTime(""));
                                }
                            } else {
                                if(StringUtils.isNotBlank(kv.getValue())){
                                    poa = new ProductOptionAttribute();
                                    poa.setIsActive(true);
                                    poa.setProductOption(po);
                                    poa.setProductOptionKey(pok);
                                    poa.setCreated(now);
                                    poa.setCreatedBy(userName);
                                    poa.setValue(val);
                                }else{
                                    continue;
                                }
                            }
                        }

                        productOptionAttributeService.saveOrUpdate(poa);
                    }
                }
            }
        }
    }

    @Override
    public List<ProductOption> getProductOptions() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductOption.class);
        detachedCriteria.add(Restrictions.eq("isActive", true));
        return list(detachedCriteria);
    }

	@Override
	public Map<Long, Integer> getDurationByProductOptionIds(Long companyId,List<Long> productOptionIds,List<String> productOptionAttributeStr) {
		return productOptionAttributeService.getDurationByProductOptionIds(companyId,productOptionIds,productOptionAttributeStr);
	}

    @Override
    public List<ProductOption> searchProductOptionList(String code) {
        return productOptionDao.searchProductOptionList(code);
    }

    @Override
    public Long findProductOptionByCode(String code) {
       return productOptionDao.findProductOptionByCode(code);
    }

    @Override
    public List<ProductOption> findProductOptionLikeByCode(String code) {
        return productOptionDao.findProductOptionLikeByCode(code);
    }
}
