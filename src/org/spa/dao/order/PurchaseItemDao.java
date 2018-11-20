package org.spa.dao.order;

import java.util.List;
import java.util.Map;

import org.spa.model.order.PurchaseItem;
import org.spa.vo.report.CustomerReportVO;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.SpendingSummaryVO;

public interface PurchaseItemDao {

	List<SpendingSummaryVO> getSpengingSummary(final Map parameterMap);
	
	List<Long> getPurchaseOrderIdOfSalesDetails(final SalesSearchVO salesSearchVO);
	
	List<Long> getPurchaseItemIdOfSalesDetails(final SalesSearchVO salesSearchVO);
	List<Long> getAllPurchaseItemIdOfSalesDetails(final SalesSearchVO salesSearchVO);
	
	List<PurchaseItem> getPurchaseItemsByIds(final Long[] piIds);
	
	Long getPurchaseItemIdOfSalesDetailsTotalSize(final SalesSearchVO salesSearchVO);
	
	List<CustomerReportVO> getTotalSalesByFormatDate(final SalesSearchVO salesSearchVO);
	
	List<CustomerReportVO> getCommissionAnalysisByStaff(final SalesSearchVO salesSearchVO);
	
	List<CustomerReportVO> getSalesAnalysisByCategory(final SalesSearchVO salesSearchVO);
	
	PurchaseItem getPIByPrepaidId(final Long prepaidId);

	Long getPurchaseItemIdOfSalesDetailsCount(final SalesSearchVO salesSearchVO, boolean calTotalSize, boolean getPurchaseOrderId);
}
