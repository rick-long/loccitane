package org.spa.serviceImpl.product;

import com.spa.constant.CommonConstant;
import com.spa.jxlsBean.ExcelUtil;
import com.spa.jxlsBean.importDemo.ProductImportJxlsBean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.spa.annotation.ShopAnnotation;
import org.spa.dao.poa.ProductDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.*;
import org.spa.model.shop.Shop;
import org.spa.service.product.*;
import org.spa.service.shop.ShopService;
import org.spa.utils.CollectionUtils;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.product.AssignShopVO;
import org.spa.vo.product.ProductAddVO;
import org.spa.vo.product.ProductEditVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

import static com.spa.constant.CommonConstant.*;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ProductServiceImpl extends BaseDaoHibernate<Product> implements ProductService {

    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    public CategoryService categoryService;
	@Autowired
	public ProductDao productDao;

    @Autowired
    public BrandService brandService;

    @Autowired
    public SupplierService supplierService;

    @Autowired
    public ProductDescriptionKeyService productDescriptionKeyService;

    @Autowired
    public ProductDescriptionService productDescriptionService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ProductOptionKeyService productOptionKeyService;
    
    @Autowired
    private ProductOptionAttributeService productOptionAttributeService;
    
    @Autowired
    private ShopService shopService;
    
    @Autowired
    private ProductOptionSupernumeraryPriceService productOptionSupernumeraryPriceService;
    
    
    // 获取指定目录的所有产品
    @Override
    public List<Product> listByCategory(Category category,Boolean isOnline) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
        
        criteria.add(Restrictions.eq("category.id", category.getId()));
        criteria.add(Restrictions.eq("isActive",true));
        if(isOnline !=null){
        	 criteria.add(Restrictions.eq("showOnApps",isOnline));
        }
        return list(criteria);
    }
	@Override
	public List<Product> listByCategory(Category category,Boolean isOnline,Long shopId) {
		return productDao.listByCategory(category,isOnline,shopId) ;
	}
	@Override
	public List<Long> getShopIds(Long productId){
		return productDao.getShopIds(productId);
	}
    @Override
    public Product saveProduct(ProductAddVO productAddVO) throws Exception {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        Product product = new Product();
        Brand brand = brandService.get(productAddVO.getBrandId());
        product.setBrand(brand);
        Category category = categoryService.get(productAddVO.getCategoryId());
        product.setCategory(category);
        product.setProdType(category.getTheTopestCategoryUnderRoot().getReference());
        product.setCompany(WebThreadLocal.getCompany());
        
        product.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PRODUCT_REF_PREFIX));
        product.setName(productAddVO.getName());
        product.setIsActive(true);
        product.setShowOnApps(productAddVO.getShowOnline() !=null ? productAddVO.getShowOnline() : false);
        product.setCreated(now);
        product.setCreatedBy(userName);
        product.setLastUpdated(now);
        product.setLastUpdatedBy(userName);
        save(product);

        //product description
        productDescriptionService.saveProductDescription(product, productAddVO.getPdvalues());

        // product option
        productOptionService.saveProductOption(product, productAddVO.getPoItemList());

        return product;
    }

    @Override
    public Product updateProduct(ProductEditVO productEditVO) throws Exception {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        Product product = get(productEditVO.getId());

        Brand brand = brandService.get(productEditVO.getBrandId());
        product.setBrand(brand);
        Category category = categoryService.get(productEditVO.getCategoryId());
        product.setCategory(category);
        product.setProdType(category.getTheTopestCategoryUnderRoot().getReference());
        
        product.setCompany(WebThreadLocal.getCompany());
        
        product.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PRODUCT_REF_PREFIX));
        product.setName(productEditVO.getName());
        product.setIsActive(productEditVO.getActive());
        product.setShowOnApps(productEditVO.getShowOnline() !=null ? productEditVO.getShowOnline() : false);
        product.setCreated(now);
        product.setCreatedBy(userName);
        product.setLastUpdated(now);
        product.setLastUpdatedBy(userName);
        product.setIsActive(productEditVO.getActive());
        saveOrUpdate(product);

        //product description
        productDescriptionService.saveProductDescription(product, productEditVO.getPdvalues());
        // product option
        productOptionService.saveProductOption(product, productEditVO.getPoItemList());

        return product;
    }

	@Override
	//读取product模板数据
	public List<ProductImportJxlsBean> importProduct(ImportDemoVO importDemoVO)
	{
		List<ProductImportJxlsBean> productImportJxlsBeanList = new ArrayList<>();
        Map<String, Object> beans = new HashMap<>();
        beans.put("productImportJxlsBeanList", productImportJxlsBeanList);
        try {
            InputStream is = importDemoVO.getImportFile().getInputStream();
            ExcelUtil.read(is, "productImportConfig.xml", beans);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (productImportJxlsBeanList.size() == 0) {
            return null;
        }
		return saveToProduct(productImportJxlsBeanList);
	}

	/**
	 * create by rick 2018-9-25
	 * @param productImportJxlsBeanList
	 * @return
	 */
	private List<ProductImportJxlsBean> saveToProduct(List<ProductImportJxlsBean> productImportJxlsBeanList)
	{
		//错误的数据封装
		List<ProductImportJxlsBean> errorRecords = CollectionUtils.getLightWeightList();

//		Field[] fields = ProductImportJxlsBean.class.getDeclaredFields();
//		Map<String,	String> map = new HashMap<>();
//		Integer index = 1;
//		for (Field field : fields) {
//			if(field.isAnnotationPresent(ShopAnnotation.class)) {
//				ShopAnnotation shopName = field.getAnnotation(ShopAnnotation.class);
//				map.put("SHOP_"+index, shopName.shopName());
//			}
//		}
		Category category=null;
		Category topistCategory=null;
		Product product =null;
		Boolean isActive =true;
		String ref = null;
		//开始保存数据
		for (ProductImportJxlsBean bean : productImportJxlsBeanList)
		{
			ref=RandomUtil.generateRandomNumberWithDate(CommonConstant.PRODUCT_REF_PREFIX);

			if(StringUtils.isAnyBlank(bean.getCategoryName())){
				bean.setReturnError("product name can not be null");
				errorRecords.add(bean);
				continue;
			}
			if(StringUtils.isAnyBlank(bean.getTreatmentName())){
				bean.setReturnError("treatment name can not be null");
				errorRecords.add(bean);
				continue;
			}else{
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
				detachedCriteria.add(Restrictions.eq("name", bean.getCategoryName().trim()));
				detachedCriteria.add(Restrictions.eq("isActive",true));
				category = categoryService.get(detachedCriteria);
				if(Objects.isNull(category)){
					bean.setReturnError("category is not exist");
					errorRecords.add(bean);
					continue;
				}
			}
			//验证价格
			if(StringUtils.isNotBlank(bean.getPrice())){
				try{
					Double.valueOf(bean.getPrice().trim());
				}catch (Exception e){
					bean.setReturnError("Price must be Digits");
					errorRecords.add(bean);
					continue;
				}
			}else{
				bean.setReturnError("Price can not be empty");
				errorRecords.add(bean);
				continue;
			}
			//验证duration
			if(StringUtils.isNotBlank(bean.getDuration())){
				try{
					Double.valueOf(bean.getDuration().trim());
				}catch (Exception e){
					bean.setReturnError("Duration must be Digits");
					errorRecords.add(bean);
					continue;
				}
			}else{
				bean.setReturnError("Duration can not be empty");
				errorRecords.add(bean);
				continue;
			}
			//验证processTime
			if(StringUtils.isNotBlank(bean.getProcessTime())){
				try{
					Double.valueOf(bean.getProcessTime().trim());
				}catch (Exception e){
					bean.setReturnError("ProcessTime must be Digits");
					errorRecords.add(bean);
					continue;
				}
			}else{
				bean.setReturnError("ProcessTime can not be empty");
				errorRecords.add(bean);
				continue;
			}
			//验证Capacity
			if(StringUtils.isNotBlank(bean.getCapacity())){
				try{
					Double.valueOf(bean.getCapacity().trim());
				}catch (Exception e){
					bean.setReturnError("Capacity must be Digits");
					errorRecords.add(bean);
					continue;
				}
			}else {
				bean.setReturnError("Capacity can not be empty");
				errorRecords.add(bean);
				continue;
			}
			//验证status
			if(StringUtils.isNotBlank(bean.getStatus())){
				if(!("ACTIVE".equalsIgnoreCase(bean.getStatus().trim()) || "INACTIVE".equalsIgnoreCase(bean.getStatus().trim()))){
					bean.setReturnError("Status should be 'ACTIVE' or 'INACTIVE'");
					errorRecords.add(bean);
					continue;
				}
			}else{
				bean.setReturnError("Status can not be empty");
				errorRecords.add(bean);
				continue;
			}
			if(!"ACTIVE".equalsIgnoreCase(bean.getStatus().trim())){
				isActive =false;
			}
			topistCategory = category.getTheTopestCategoryUnderRoot();
			product= new Product();
			product.setCategory(category);
			product.setCompany(WebThreadLocal.getCompany());
			product.setReference(ref);
			product.setName(bean.getTreatmentName());
			product.setIsActive(isActive);
			product.setCreated(new Date());
			product.setCreatedBy(WebThreadLocal.getUser().getUsername());
			product.setProdType(bean.getCategoryName());
			product.setBrand(brandService.get("reference","BR-DEFAULT"));
			save(product);

			//保存productOption
			Long productOptionByCode = null;
			if(StringUtils.isNotBlank(bean.getTreatmentCode())){
				Long poaId = productOptionService.findProductOptionByCode(bean.getTreatmentCode().trim());
				if(poaId != null){
					bean.setReturnError("Treatment code is exist");
					errorRecords.add(bean);
					continue;
				}
			}
			ProductOption po = new ProductOption();
			po.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.PRODUCT_OPTION_REF_PREFIX));
			po.setProduct(product);
			po.setIsActive(isActive);
			po.setCreated(product.getCreated());
			po.setCreatedBy(product.getCreatedBy());
			productOptionService.saveOrUpdate(po);

			if(StringUtils.isNotBlank(bean.getDuration())){
				try{
					Double.valueOf(bean.getDuration().trim());
				}catch (Exception e){
					bean.setReturnError("Duration must be digits");
					errorRecords.add(bean);
					continue;
				}
			}else{
				bean.setReturnError("Duration can not be empty");
				errorRecords.add(bean);
				continue;
			}
			if(StringUtils.isNotBlank(bean.getPrice())){
				try{
					Double.valueOf(bean.getPrice().trim());
				}catch (Exception e){
					bean.setReturnError("Price must be digits");
					errorRecords.add(bean);
					continue;
				}
			}else{
				bean.setReturnError("Price can not be empty");
				errorRecords.add(bean);
				continue;
			}

			if(StringUtils.isNotBlank(bean.getProcessTime())){
				try{
					Double.valueOf(bean.getProcessTime().trim());
				}catch (Exception e){
					bean.setReturnError("Processing Time must be digits");
					errorRecords.add(bean);
					continue;
				}
			}else{
				bean.setReturnError("Processing Time can not be empty");
				errorRecords.add(bean);
				continue;
			}

			if(!StringUtils.isAnyBlank(bean.getTreatmentCode())) {
				saveProductOptionAttribute("code", bean.getTreatmentCode().trim(), topistCategory.getId(), isActive, po, product);
			}
			if(!StringUtils.isAnyBlank(bean.getDuration())) {
				saveProductOptionAttribute(POK_DURATION, bean.getDuration().trim(), topistCategory.getId(), isActive, po, product);
			}
			if(!StringUtils.isAnyBlank(bean.getPrice())) {
				saveProductOptionAttribute(POK_PRICE, bean.getPrice().trim(), topistCategory.getId(), isActive, po, product);
			}
			if(!StringUtils.isAnyBlank(bean.getProcessTime())) {
				saveProductOptionAttribute(POK_PROCESS_TIME, bean.getProcessTime().trim(), topistCategory.getId(), isActive, po, product);
			}


			//额外价格
//			if(StringUtils.isNotBlank(bean.getAdditionalPriceShop_1())){
//				try{
//					Double.valueOf(bean.getAdditionalPriceShop_1().trim());
//				}catch (Exception e){
//					bean.setReturnError("Addition price must be digits");
//					errorRecords.add(bean);
//					continue;
//				}
//			}
//			if(StringUtils.isNotBlank(bean.getAdditionalPriceShop_2())){
//				try{
//					Double.valueOf(bean.getAdditionalPriceShop_2().trim());
//				}catch (Exception e){
//					bean.setReturnError("Addition price must be digits");
//					errorRecords.add(bean);
//					continue;
//				}
//			}
//			if(StringUtils.isNotBlank(bean.getAdditionalPriceShop_1())) {
//
//				ProductOptionSupernumeraryPrice posp=new ProductOptionSupernumeraryPrice();
//				Shop shop = shopService.get(2l);
//				posp.setShop(shop);
//				posp.setOriginalPrice(po.getOriginalPrice());
//				posp.setCreated(product.getCreated());
//				posp.setIsActive(isActive);
//				posp.setCreatedBy(product.getCreatedBy());
//				posp.setCompany(shop.getCompany());
//				posp.setProductOption(po);
//				posp.setAdditionalPrice(Double.parseDouble(bean.getAdditionalPriceShop_1()));
//				productOptionSupernumeraryPriceService.save(posp);
//			}
//
//			if(StringUtils.isNotBlank(bean.getAdditionalPriceShop_2())) {
//
//				ProductOptionSupernumeraryPrice posp=new ProductOptionSupernumeraryPrice();
//				Shop shop = shopService.get(3l);
//				posp.setShop(shop);
//				posp.setOriginalPrice(po.getOriginalPrice());
//				posp.setCreated(product.getCreated());
//				posp.setIsActive(isActive);
//				posp.setCreatedBy(product.getCreatedBy());
//				posp.setCompany(shop.getCompany());
//				posp.setProductOption(po);
//				posp.setAdditionalPrice(Double.parseDouble(bean.getAdditionalPriceShop_2()));
//				productOptionSupernumeraryPriceService.save(posp);
//			}

//			index = 1;
//			List<Shop> list;
//			for (Entry<String, String> entry : map.entrySet()) {
//				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Shop.class);
//				detachedCriteria.add(Restrictions.eq("name",map.get("SHOP_1")));
//				detachedCriteria.add(Restrictions.eq("isActive",true));
//				list = shopService.list(detachedCriteria);
//				if(list == null || list.size() == 0) {
//					bean.setReturnError("shop is not exist");
//					errorRecords.add(bean);
//					continue;
//				}
//				productOptionService.saveOrUpdate(po);
//				if(!StringUtils.isAnyBlank(bean.getDuration())) {
//					saveProductOptionAttribute(POK_DURATION, bean.getDuration().trim(), topistCategory.getId(), isActive, po, product);
//				}
//				if(!StringUtils.isAnyBlank(bean.getPrice())) {
//					saveProductOptionAttribute(POK_PRICE, bean.getPrice().trim(), topistCategory.getId(), isActive, po, product);
//				}
//				if(!StringUtils.isAnyBlank(bean.getProcessTime())) {
//					saveProductOptionAttribute(POK_PROCESS_TIME, bean.getProcessTime().trim(), topistCategory.getId(), isActive, po, product);
//				}
				//额外价格
//				saveProductOptionSupernumeraryPrice(list.get(0),map,bean,po,product,isActive,errorRecords,index);
//				index++;
//			}
		}
		return errorRecords;
	}

	/**
	 * 保存额外价格
	 * @param bean
	 */
	private void saveProductOptionSupernumeraryPrice(Shop shop,Map<String,String> map ,ProductImportJxlsBean bean,ProductOption po,Product product,Boolean isActive,List<ProductImportJxlsBean> errorRecords,Integer index)
	{
		Double amount = null;
        switch (index) {
		case 1:
			if(!StringUtils.isAnyBlank(bean.getAdditionalPriceShop_1())) {
				try{
					amount = NumberUtils.createDouble(bean.getAdditionalPriceShop_1().trim());
				}catch (Exception e){
					bean.setReturnError(map.get("SHOP_1").concat("additional price should be number"));
				}
			}
			break;
		case 2:
			if(!StringUtils.isAnyBlank(bean.getAdditionalPriceShop_2())) {
				try{
					amount = NumberUtils.createDouble(bean.getAdditionalPriceShop_1().trim());
				}catch (Exception e){
					bean.setReturnError(map.get("SHOP_2").concat(" additional price should be number"));
				}
			}
			break;
		default:

			break;
		}
		ProductOptionSupernumeraryPrice posp=new ProductOptionSupernumeraryPrice();
		posp.setShop(shop);
		posp.setOriginalPrice(po.getOriginalPrice());
		posp.setCreated(product.getCreated());
		posp.setIsActive(isActive);
		posp.setCreatedBy(product.getCreatedBy());
		posp.setCompany(shop.getCompany());
		posp.setProductOption(po);
		posp.setAdditionalPrice(amount);
		productOptionSupernumeraryPriceService.save(posp);
//		if(!StringUtils.isAnyBlank(bean.getAdditionalPriceShop_2())){
//			try{
//				amount = NumberUtils.createDouble(bean.getAdditionalPriceShop_2().trim());
//			}catch (Exception e){
//				bean.setReturnError("additional price elements should be number");
//			}
//			ProductOptionSupernumeraryPrice posp=new ProductOptionSupernumeraryPrice();
//			posp.setShop(shop);
//			posp.setOriginalPrice(po.getOriginalPrice());
//			posp.setCreated(product.getCreated());
//			posp.setIsActive(isActive);
//			posp.setCreatedBy(product.getCreatedBy());
//			posp.setCompany(shop.getCompany());
//			posp.setProductOption(po);
//			posp.setAdditionalPrice(amount);
//			productOptionSupernumeraryPriceService.save(posp);
//		}
	}

	/**
	 * 保存productOptionAttribute
	 * @param pokey
	 * @param value
	 * @param topiestCategoryId
	 * @param isActive
	 * @param po
	 * @param product
	 */
	private void saveProductOptionAttribute(String pokey,String value,
											Long topiestCategoryId,Boolean isActive,ProductOption po,Product product)
	{
		List<ProductOptionKey> pokList= productOptionKeyService.getPOKListOByRefAndCategoryId(pokey,topiestCategoryId, WebThreadLocal.getCompany().getId());
		ProductOptionKey productOptionKey ;
		if(pokList != null && pokList.size() > 0){
			productOptionKey = pokList.get(0);
		}else{
			productOptionKey =null;
		}
		if(productOptionKey !=null){
			ProductOptionAttribute poa = new ProductOptionAttribute();
			poa.setIsActive(isActive);
			poa.setProductOption(po);
			poa.setProductOptionKey(productOptionKey);
			poa.setCreated(product.getCreated());
			poa.setCreatedBy(product.getCreatedBy());
			poa.setValue(value);
			productOptionAttributeService.save(poa);
		}
	}

	@Override
	public void assignShops(AssignShopVO assignShopVO) {
		Date now = new Date();
		String userName = WebThreadLocal.getUser().getUsername();
		Product product = get(assignShopVO.getProductId());
		Long [] shopIds = assignShopVO.getShopIds();
		Set<Shop> shopsSet =product.getShops();
		shopsSet.clear();
		if(shopIds != null) {
			for(Long id : shopIds) {
				shopsSet.add(shopService.get(id));
			}
		}
		product.setLastUpdated(now);
		product.setLastUpdatedBy(userName);
		saveOrUpdate(product);
	}
}
