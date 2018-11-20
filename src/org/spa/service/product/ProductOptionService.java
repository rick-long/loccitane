package org.spa.service.product;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.vo.page.Page;
import org.spa.vo.product.PoItemVO;

import java.util.List;
import java.util.Map;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ProductOptionService extends BaseDao<ProductOption>{

    public void saveProductOption(Product product, List<PoItemVO> poItemList)throws Exception;

    List<ProductOption> getProductOptions();
    
    Map<Long , Integer> getDurationByProductOptionIds(Long companyId,List<Long> productOptionIds,List<String> productOptionAttributeStr);

    List<ProductOption> searchProductOptionList(String code/*,Integer pageNumber,Integer pageSize*/);

    Long findProductOptionByCode(String code);

    List<ProductOption> findProductOptionLikeByCode(String code);
}
