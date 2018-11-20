package org.spa.service.product;

import org.spa.dao.base.BaseDao;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.product.AssignShopVO;
import org.spa.vo.product.ProductAddVO;
import org.spa.vo.product.ProductEditVO;
import com.spa.jxlsBean.importDemo.ProductImportJxlsBean;

import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ProductService extends BaseDao<Product>{

    // 获取指定目录的所有产品
	public List<Product> listByCategory(Category category,Boolean isOnline);
	public List<Product> listByCategory(Category category,Boolean isOnline,Long shopId);
	public Product saveProduct(ProductAddVO productAddVO) throws Exception;
	
	public Product updateProduct(ProductEditVO productEditVO) throws Exception;
	
	public List<ProductImportJxlsBean> importProduct(ImportDemoVO importDemoVO);
	public void assignShops(AssignShopVO assignShopVO);
	public List<Long> getShopIds(Long productId);
}
