package org.spa.serviceImpl.product;

import java.util.Date;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Product;
import org.spa.model.product.Supplier;
import org.spa.service.product.ProductService;
import org.spa.service.product.SupplierService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.supplier.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class SupplierServiceImpl extends BaseDaoHibernate<Supplier> implements SupplierService{

	 @Autowired
	 private ProductService productService;
	 
	@Override
	public void saveOrUpdateSupplier(SupplierVO supplierVO) {
		Date now=new Date();
		String loginUser=WebThreadLocal.getUser().getUsername();
		
		Supplier supplier=null;
		Long id=supplierVO.getId();
		if(id !=null && id.longValue()>0){
			supplier=get(supplierVO.getId());
			supplier.setLastUpdated(now);
			supplier.setLastUpdatedBy(loginUser);
			supplier.getProducts().clear();
			supplier.setIsActive(supplierVO.getActive());
		}else{
			supplier=new Supplier();
			supplier.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.SUPPLIER_REF_PREFIX));
			supplier.setIsActive(true);
			supplier.setCompany(WebThreadLocal.getCompany());
			supplier.setCreated(now);
			supplier.setCreatedBy(loginUser);
		}
		supplier.setName(supplierVO.getName());
		supplier.setContactEmail(supplierVO.getContactEmail());
		supplier.setContactPerson(supplierVO.getContactPerson());
		supplier.setContactTel(supplierVO.getContactTel());
		saveOrUpdate(supplier);
		
		if(supplierVO.getProductIds() !=null && supplierVO.getProductIds().length>0){
	        for (Long productID : supplierVO.getProductIds()) {
	            Product product = productService.get(productID);
	            supplier.getProducts().add(product);
	        }
		}
		saveOrUpdate(supplier);
	}
}
