package org.spa.dao.productOption;

import org.spa.model.product.ProductOption;
import org.spa.vo.page.Page;

import java.util.List;


public interface ProductOptionDao {

    List<ProductOption> searchProductOptionList(String code/*, Integer pageSize, Integer page*/);

    Long findProductOptionByCode(String code);

    List<ProductOption> findProductOptionLikeByCode(String code);
}
