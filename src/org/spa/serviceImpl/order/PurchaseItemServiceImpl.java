package org.spa.serviceImpl.order;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.order.PurchaseItemDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.order.StaffCommission;
import org.spa.model.product.Category;
import org.spa.service.order.PurchaseItemService;
import org.spa.service.product.CategoryService;
import org.spa.utils.CollectionUtils;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.page.Page;
import org.spa.vo.report.CustomerReportVO;
import org.spa.vo.report.SalesDetailsSummaryVO;
import org.spa.vo.report.SalesSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;
import com.spa.tools.land.MathUtils;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class PurchaseItemServiceImpl extends BaseDaoHibernate<PurchaseItem> implements PurchaseItemService{

	@Autowired
	public PurchaseItemDao purchaseItemDao;
	
	@Autowired
	public CategoryService categoryService;
	
	@Override
	public Page<PurchaseItem> getPurchaseItemIdOfSalesDetailsByPage(SalesSearchVO salesSearchVO) {
		
		if(salesSearchVO.getCategoryId() !=null){
			List<Long> lowestCategories = new ArrayList<>();
			categoryService.getLowestCategoriesByCategory(salesSearchVO.getCategoryId(),lowestCategories);
			lowestCategories.add(salesSearchVO.getCategoryId());
			Long[] lowestCategoriesByCategoryIds =  (Long[])lowestCategories.toArray(new Long[lowestCategories.size()]);
			salesSearchVO.setLowestCategoriesByCategoryIds(lowestCategoriesByCategoryIds);
		}
		
		Long totalSize = purchaseItemDao.getPurchaseItemIdOfSalesDetailsTotalSize(salesSearchVO);
		
		List<PurchaseItem> purchaseItemList =new ArrayList<>();
		List<Long> purchaseItemIds = purchaseItemDao.getPurchaseItemIdOfSalesDetails(salesSearchVO);
		if(purchaseItemIds !=null && purchaseItemIds.size()>0){
			Long[] piIds = (Long[])purchaseItemIds.toArray(new Long[purchaseItemIds.size()]);
			purchaseItemList = purchaseItemDao.getPurchaseItemsByIds(piIds);
		}
		return new Page<PurchaseItem>(purchaseItemList, totalSize, salesSearchVO.getPageNumber(), salesSearchVO.getPageSize());
	}
	
	@Override
	public List<PurchaseItem> getPurchaseItemIdOfSalesDetails(SalesSearchVO salesSearchVO) {
		
		if(salesSearchVO.getCategoryId() !=null){
			List<Long> lowestCategories = new ArrayList<>();
			categoryService.getLowestCategoriesByCategory(salesSearchVO.getCategoryId(),lowestCategories);
			lowestCategories.add(salesSearchVO.getCategoryId());
			Long[] lowestCategoriesByCategoryIds =  (Long[])lowestCategories.toArray(new Long[lowestCategories.size()]);
			salesSearchVO.setLowestCategoriesByCategoryIds(lowestCategoriesByCategoryIds);
		}
		
		List<PurchaseItem> purchaseItemList =new ArrayList<>();
		List<Long> purchaseItemIds = purchaseItemDao.getAllPurchaseItemIdOfSalesDetails(salesSearchVO);
		if(purchaseItemIds !=null && purchaseItemIds.size()>0){
			Long[] piIds = (Long[])purchaseItemIds.toArray(new Long[purchaseItemIds.size()]);
			purchaseItemList = purchaseItemDao.getPurchaseItemsByIds(piIds);
		}
		return purchaseItemList;
	}
	@Override
	public  Map<String,Object> getSalesDetailsSummaryVOList(List<PurchaseItem> detailsList,Long staffId){
		
		List<SalesDetailsSummaryVO> summaryList = new ArrayList<SalesDetailsSummaryVO>();   	
		Map map = new HashMap();
		
		boolean isStaff = false;
		if (staffId !=null && staffId.longValue()>0) {
			isStaff = true;
		}
		if(detailsList ==null){
			return null;
		}
		Double totalItemAmount=0d;
      	Double totalCommission =0d;
      	Double totalAmount =0d;
      	Long newPOId =null;
		for(PurchaseItem purchaseItem : detailsList){
			totalCommission += purchaseItem.getTotalCommission();
        	totalItemAmount += purchaseItem.getAmount();
        	
        	PurchaseOrder po =purchaseItem.getPurchaseOrder();
        	if(newPOId == null){
        		newPOId = po.getId();
        		totalAmount +=po.getTotalAmount();
        	}else{
        		if(newPOId != po.getId()){
        			newPOId =po.getId();
        			totalAmount +=po.getTotalAmount();
        		}
        	}
        	
        	//
			String categoryName="";
			String prodType= "";
			Long categoryId= null;
			if(purchaseItem.getBuyPrepaidTopUpTransaction() !=null){
				prodType=CommonConstant.CATEGORY_REF_PREPAID;
				if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)){
					categoryName="Cash Package";
					categoryId=1000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER)){
					categoryName="Cash Voucher";
					categoryId=2000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)){
					categoryName="Treatment Package";
					categoryId=3000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)){
					categoryName="Treatment Voucher";
					categoryId=4000000L;
				}
				
			}else{
				Category category = purchaseItem.getProductOption().getProduct().getCategory();
				categoryId =category.getId();
				categoryName = category.getName();
				prodType = purchaseItem.getProductOption().getProduct().getProdType();
			}
			
			SalesDetailsSummaryVO vo = null;
			vo = (SalesDetailsSummaryVO)map.get(categoryId);
			if (vo==null) {
				vo = new SalesDetailsSummaryVO();				
				vo.setCategoryName(categoryName);
				vo.setCategoryId(categoryId);				
				vo.setProdType(prodType);
				
				if (!isStaff) {
					vo.setAmount(purchaseItem.getAmount());
					vo.setUnit(new Double(purchaseItem.getQty()));
					vo.setCommission(purchaseItem.getTotalCommission());
				} else {
					vo.setAmount(purchaseItem.getTherapistAmount(staffId));
					vo.setUnit(new Double(purchaseItem.getQty()));
					vo.setCommission(purchaseItem.getTotalCommissionByStaff(staffId));
				}
				
			} else {
				
				double amount = 0;
				double unit = 0 ;
				double commission = 0;
				if (!isStaff) {
					amount = purchaseItem.getAmount();
					unit = purchaseItem.getQty();
					commission = purchaseItem.getTotalCommission().doubleValue();
				} else {
					amount = purchaseItem.getTherapistAmount(staffId).doubleValue();
					unit = purchaseItem.getQty();
					commission = purchaseItem.getTotalCommissionByStaff(staffId);
				}
				
				vo.setAmount(new Double(vo.getAmount().doubleValue()+amount));
				vo.setUnit(new Double(vo.getUnit().doubleValue()+unit));
				vo.setCommission(new Double(vo.getCommission().doubleValue()+commission));
				
			}
			
			map.put(categoryId, vo);
		}
		summaryList.addAll(map.values());
		CollectionUtils.sort(summaryList, "prodType", true);
		
		Map<String,Object> reuturnMap = new HashMap<String,Object>();
		reuturnMap.put("summaryList", summaryList);
		reuturnMap.put("totalAmount", totalAmount);
		reuturnMap.put("totalItemAmount", totalItemAmount);
		reuturnMap.put("totalCommission", totalCommission);
		return reuturnMap;
	}
	//by category
	@Override
	public  List<SalesDetailsSummaryVO> getSalesDetailsSummaryVOListV2(List<PurchaseItem> detailsList,Long staffId){
		
		List<SalesDetailsSummaryVO> summaryList = new ArrayList<SalesDetailsSummaryVO>();   	
		Map map = new HashMap();
		
		boolean isStaff = false;
		if (staffId !=null && staffId.longValue()>0) {
			isStaff = true;
		}
		if(detailsList ==null){
			return null;
		}
		for(PurchaseItem purchaseItem : detailsList){
        	
			String categoryName="";
			String prodType= "";
			Long categoryId= null;
			if(purchaseItem.getBuyPrepaidTopUpTransaction() !=null){
				prodType=CommonConstant.CATEGORY_REF_PREPAID;
				if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)){
					categoryName="Cash Package";
					categoryId=1000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER)){
					categoryName="Cash Voucher";
					categoryId=2000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)){
					categoryName="Treatment Package";
					categoryId=3000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)){
					categoryName="Treatment Voucher";
					categoryId=4000000L;
				}
				
			}else{
				Category category = purchaseItem.getProductOption().getProduct().getCategory();
				categoryId =category.getId();
				categoryName = category.getName();
				prodType = purchaseItem.getProductOption().getProduct().getProdType();
			}
			
			SalesDetailsSummaryVO vo = null;
			vo = (SalesDetailsSummaryVO)map.get(categoryId);
			if (vo==null) {
				vo = new SalesDetailsSummaryVO();				
				vo.setCategoryName(categoryName);
				vo.setCategoryId(categoryId);				
				vo.setProdType(prodType);
				
				if (!isStaff) {
					vo.setAmount(purchaseItem.getAmount());
					vo.setUnit(new Double(purchaseItem.getQty()));
					vo.setCommission(purchaseItem.getTotalCommission());
				} else {
					vo.setAmount(purchaseItem.getTherapistAmount(staffId));
					vo.setUnit(new Double(purchaseItem.getQty()));
					vo.setCommission(purchaseItem.getTotalCommissionByStaff(staffId));
				}
				
			} else {
				
				double amount = 0;
				double unit = 0 ;
				double commission = 0;
				if (!isStaff) {
					amount = purchaseItem.getAmount();
					unit = purchaseItem.getQty();
					commission = purchaseItem.getTotalCommission().doubleValue();
				} else {
					amount = purchaseItem.getTherapistAmount(staffId).doubleValue();
					unit = purchaseItem.getQty();
					commission = purchaseItem.getTotalCommissionByStaff(staffId);
				}
				
				vo.setAmount(new Double(vo.getAmount().doubleValue()+amount));
				vo.setUnit(new Double(vo.getUnit().doubleValue()+unit));
				vo.setCommission(new Double(vo.getCommission().doubleValue()+commission));
				
			}
			
			map.put(categoryId, vo);
		}
		summaryList.addAll(map.values());
		CollectionUtils.sort(summaryList, "prodType", true);
		return summaryList;
	}
	//by product option
	@Override
	public  List<SalesDetailsSummaryVO> getSalesDetailsSummaryVOListV3(List<PurchaseItem> detailsList){
		
		List<SalesDetailsSummaryVO> summaryList = new ArrayList<SalesDetailsSummaryVO>();   	
		Map map = new HashMap();
		
		if(detailsList ==null){
			return null;
		}
		for(PurchaseItem purchaseItem : detailsList){
        	
			String categoryName="";
			String prodType= "";
			Long categoryId= null;
			if(purchaseItem.getBuyPrepaidTopUpTransaction() !=null){
				prodType=CommonConstant.CATEGORY_REF_PREPAID;
				if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE)){
					categoryName="Cash Package";
					categoryId=1000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER)){
					categoryName="Cash Voucher";
					categoryId=2000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)){
					categoryName="Treatment Package";
					categoryId=3000000L;
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)){
					categoryName="Treatment Voucher";
					categoryId=4000000L;
				}
				
			}else{
				categoryId =purchaseItem.getProductOption().getId();
				categoryName = purchaseItem.getProductOption().getLabel6();
				prodType = purchaseItem.getProductOption().getProduct().getProdType();
			}
			
			SalesDetailsSummaryVO vo = null;
			vo = (SalesDetailsSummaryVO)map.get(categoryId);
			if (vo==null) {
				vo = new SalesDetailsSummaryVO();				
				vo.setCategoryName(categoryName);
				vo.setCategoryId(categoryId);				
				vo.setProdType(prodType);
				vo.setAmount(purchaseItem.getAmount());
				vo.setUnit(new Double(purchaseItem.getQty()));
				
			} else {
				
				double amount = purchaseItem.getAmount();
				double unit = purchaseItem.getQty();
				
				vo.setAmount(new Double(vo.getAmount().doubleValue()+amount));
				vo.setUnit(new Double(vo.getUnit().doubleValue()+unit));
				
			}
			
			map.put(categoryId, vo);
		}
		summaryList.addAll(map.values());
		CollectionUtils.sort(summaryList, "prodType", true);
		return summaryList;
	}
	// by product type
	@Override
	public  List<SalesDetailsSummaryVO> getSalesDetailsSummaryVOListV4(List<PurchaseItem> detailsList){
		
		List<SalesDetailsSummaryVO> summaryList = new ArrayList<SalesDetailsSummaryVO>();   	
		Map map = new HashMap();
		
		if(detailsList ==null){
			return null;
		}
		for(PurchaseItem purchaseItem : detailsList){
        	
			String categoryName="";
			String prodType= "";
			if(purchaseItem.getBuyPrepaidTopUpTransaction() !=null){
				prodType=CommonConstant.CATEGORY_REF_PREPAID;
				if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_PACKAGE) || purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE)){
					categoryName="Package";
				}else if(purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER) || purchaseItem.getBuyPrepaidTopUpTransaction().getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)){
					categoryName="Voucher";
				}
				
			}else{
				prodType = purchaseItem.getProductOption().getProduct().getProdType();
				categoryName=purchaseItem.getProductOption().getProduct().getProdType();
			}
			
			SalesDetailsSummaryVO vo = null;
			vo = (SalesDetailsSummaryVO)map.get(categoryName);
			if (vo==null) {
				vo = new SalesDetailsSummaryVO();				
				vo.setCategoryName(categoryName);			
				vo.setProdType(prodType);
				
				vo.setAmount(purchaseItem.getAmount());
				vo.setUnit(new Double(purchaseItem.getQty()));
				
			} else {
				double amount = purchaseItem.getAmount();
				double unit = purchaseItem.getQty();
				
				vo.setAmount(new Double(vo.getAmount().doubleValue()+amount));
				vo.setUnit(new Double(vo.getUnit().doubleValue()+unit));
				
			}
			
			map.put(categoryName, vo);
		}
		summaryList.addAll(map.values());
		return summaryList;
	}
	@Override
	public  Map<Long,List<PurchaseItem>> getStaffDetailsSummaryVOList(List<PurchaseItem> detailsList){
		
		List<PurchaseItem> summaryList = null; 	
		Map map = new HashMap();
		
		if(detailsList ==null){
			return null;
		}
		for(PurchaseItem purchaseItem : detailsList){
        	String categoryName="";
			for(StaffCommission sc : purchaseItem.getStaffCommissions()){
				summaryList = (List<PurchaseItem>)map.get(sc.getStaff().getId());
				if (summaryList==null) {
					summaryList =new ArrayList<PurchaseItem>();  
					
				}
				summaryList.add(purchaseItem);
				map.put(sc.getStaff().getId(), summaryList);
			}
		}
		return map;
	}

	@Override
	public List<PurchaseItem> getPurchaseItemByBookNotShowStatus(Map parameters) {
		Long companyId = (Long) parameters.get("companyId");
		Long userId = (Long) parameters.get("userId");
		String bookStatus = (String) parameters.get("bookStatus");

		DetachedCriteria idCriteria = DetachedCriteria.forClass(PurchaseItem.class);
		idCriteria.createAlias("purchaseOrder", "po");
		idCriteria.add(Restrictions.eq("po.company.id", companyId));
		idCriteria.add(Restrictions.eq("isActive", true));
		idCriteria.add(Restrictions.eq("po.user.id", userId));

		List<PurchaseItem> itemList = list(idCriteria);
		return itemList;
	}

	@Override
	public List<CustomerReportVO> getTotalSalesByDate(SalesSearchVO salesSearchVO) {
		List<CustomerReportVO> list= (List<CustomerReportVO>) purchaseItemDao.getTotalSalesByFormatDate(salesSearchVO);
		return list;
	}
	
//	@Override
//	public List<CustomerReportVO> getTotalSalesAndCommission(SalesSearchVO salesSearchVO) {
//		List<CustomerReportVO> list= (List<CustomerReportVO>) purchaseItemDao.getTotalSalesAndCommission(salesSearchVO);
//		return list;
//	}
	
	private List<CustomerReportVO> getSalesAnalysisByStaff(SalesSearchVO salesSearchVO){
		List<CustomerReportVO> list = (List<CustomerReportVO>)purchaseItemDao.getSalesAnalysisByCategory(salesSearchVO);
		Double totalAmt = 0d;
		Double totalQty = 0d;
		for(CustomerReportVO vo : list){
			totalAmt += vo.getSumAmt();
			totalQty += vo.getSumQty();
		}
		for(CustomerReportVO vo : list){
			if(vo.getSumAmt() !=null && vo.getSumAmt().doubleValue()>0){
				vo.setPercentageOfSales(MathUtils.round(vo.getSumAmt().doubleValue(), 6) / MathUtils.round(totalAmt, 6));
			}
			if(vo.getSumQty() !=null && vo.getSumQty().doubleValue()>0){
				vo.setPercentageOfUnits(MathUtils.round(vo.getSumQty().doubleValue(), 6) / MathUtils.round(totalQty, 6));
			}
		}
		return list;
	}
	
	@Override
	public Map<String,List<CustomerReportVO>> getSalesAnalysisByProdType(SalesSearchVO salesSearchVO){
		
		Map<String,List<CustomerReportVO>> salesAnalysis = new HashMap<String,List<CustomerReportVO>>();
		if(StringUtils.isNotBlank(salesSearchVO.getProdType())){
			salesAnalysis.put(salesSearchVO.getProdType(), getSalesAnalysisByStaff(salesSearchVO));
		}else{
			List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
			for(Category subCa : subCategoryList){
				if(subCa.getReference().equals(CommonConstant.CATEGORY_REF_TIPS)){
					continue;
				}
				salesSearchVO.setProdType(subCa.getReference());
				salesAnalysis.put(subCa.getReference(),getSalesAnalysisByStaff(salesSearchVO));
			}
			salesSearchVO.setProdType(CommonConstant.CATEGORY_REF_PREPAID);
			salesAnalysis.put(CommonConstant.CATEGORY_REF_PREPAID, getSalesAnalysisByStaff(salesSearchVO));
		}
		return salesAnalysis;
	}
	private List<CustomerReportVO> getCommissionAnalysisByStaff(SalesSearchVO salesSearchVO){
		List<CustomerReportVO> list = (List<CustomerReportVO>)purchaseItemDao.getCommissionAnalysisByStaff(salesSearchVO);
		Double totalAmt = 0d;
		for(CustomerReportVO vo : list){
			totalAmt += vo.getSumAmt();
		}
		for(CustomerReportVO vo : list){
			if(vo.getSumAmt() !=null && vo.getSumAmt().doubleValue()>0){
				vo.setPercentageOfSales(MathUtils.round(vo.getSumAmt().doubleValue(), 6) / MathUtils.round(totalAmt, 6));
			}
		}
		return list;
	}
	
	@Override
	public Map<String,List<CustomerReportVO>> getCommissionAnalysisByProdType(SalesSearchVO salesSearchVO){
		
		Map<String,List<CustomerReportVO>> commissionAnalysis = new HashMap<String,List<CustomerReportVO>>();
		if(StringUtils.isNotBlank(salesSearchVO.getProdType())){
			commissionAnalysis.put(salesSearchVO.getProdType(), getCommissionAnalysisByStaff(salesSearchVO));
		}else{
			List<Category> subCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
			for(Category subCa : subCategoryList){
				if(subCa.getReference().equals(CommonConstant.CATEGORY_REF_TIPS)){
					continue;
				}
				salesSearchVO.setProdType(subCa.getReference());
				commissionAnalysis.put(subCa.getReference(),getCommissionAnalysisByStaff(salesSearchVO));
			}
			salesSearchVO.setProdType(CommonConstant.CATEGORY_REF_PREPAID);
			commissionAnalysis.put(CommonConstant.CATEGORY_REF_PREPAID, getCommissionAnalysisByStaff(salesSearchVO));
		}
		return commissionAnalysis;
	}

	@Override
	public PurchaseItem getPIByPrepaidId(Long prepaidId) {
		return purchaseItemDao.getPIByPrepaidId(prepaidId);
	}
}