package org.spa.service.order;

import java.util.List;
import java.util.Map;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.PurchaseItem;
import org.spa.vo.page.Page;
import org.spa.vo.report.CustomerReportVO;
import org.spa.vo.report.SalesDetailsSummaryVO;
import org.spa.vo.report.SalesSearchVO;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface PurchaseItemService extends BaseDao<PurchaseItem>{
	//
	public Page<PurchaseItem> getPurchaseItemIdOfSalesDetailsByPage(SalesSearchVO salesSearchVO);
	
	public List<PurchaseItem> getPurchaseItemIdOfSalesDetails(SalesSearchVO salesSearchVO);
	
	public  Map<String,Object> getSalesDetailsSummaryVOList(List<PurchaseItem> detailsList,Long staffId);
	
	public  List<SalesDetailsSummaryVO> getSalesDetailsSummaryVOListV2(List<PurchaseItem> detailsList,Long staffId);
	
	public  List<SalesDetailsSummaryVO> getSalesDetailsSummaryVOListV3(List<PurchaseItem> detailsList);
	
	public  List<SalesDetailsSummaryVO> getSalesDetailsSummaryVOListV4(List<PurchaseItem> detailsList);
	
	public List<CustomerReportVO> getTotalSalesByDate(SalesSearchVO salesSearchVO);
	
	public Map<String,List<CustomerReportVO>> getSalesAnalysisByProdType(SalesSearchVO salesSearchVO);
	
	public Map<String,List<CustomerReportVO>> getCommissionAnalysisByProdType(SalesSearchVO salesSearchVO);
	
	public PurchaseItem getPIByPrepaidId(Long prepaidId);
	
	public  Map<Long,List<PurchaseItem>> getStaffDetailsSummaryVOList(List<PurchaseItem> detailsList);

    List<PurchaseItem> getPurchaseItemByBookNotShowStatus(Map parameters);
}