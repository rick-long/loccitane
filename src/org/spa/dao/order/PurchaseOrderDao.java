package org.spa.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.spa.model.order.PurchaseOrder;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.user.ClientViewVO;

public interface PurchaseOrderDao {
	
	public List<PurchaseOrder> getPurchaseOrdersByIds(final Long[] piIds);

	public List<ClientViewVO> getSalesAnalysisByClientAndShop(final Map parameters);
	
	public Double getTotalRevenueByFilters(final Date startDate,final Date endDate,final Long shopId,final Long companyId);
	
	public Double getTotalPackageByFilters(final Date startDate,final Date endDate,final Long shopId,final Long companyId);
	
	public Double getTotalAmountByProdType(final Date startDate,final Date endDate,final Long shopId,final Long companyId,final String status,final String prodType);
	
	public Long getCountOrdersByFilters(final String fromDate,final String endDate,final String orderStatus,final Long userId);
	
	public Long getCountMembersFromOrdersByFilters(final SalesSearchVO salesSearchVO);
}
