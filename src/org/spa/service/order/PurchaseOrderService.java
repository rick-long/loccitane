package org.spa.service.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.product.Category;
import org.spa.model.product.ProductOption;
import org.spa.model.user.User;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.OrderItemVO;
import org.spa.vo.sales.OrderVO;
import org.spa.vo.user.ClientViewVO;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface PurchaseOrderService extends BaseDao<PurchaseOrder>{
	// this is for item single discount
	public OrderVO checkOrderItem(OrderVO orderVO,int idxId,HttpSession httpSession);
	
	// this is for item target discount or bundle discount
	public OrderVO checkBundleOrder(OrderVO orderVO,int idxId,HttpSession httpSession);
	
	public Long savePurchaseOrder(OrderVO orderVO);
	
	public void removePurchaseOrder(Long id);
	
	public void reviewSpaPointsAndLoyaltyLevel(PurchaseOrder order,Double earnPoints,String earnChannel,String action,String prepaidType, Boolean isMember);
	
	public void getSuitablePackages(OrderItemVO orderItemVO,Long memberId,ProductOption po,Boolean usingCashPackage);
	
	public Double[] calculateTotalSalesAndCommissionByCategory(User staff,Date currentDate,Category category);
	
	public List<ClientViewVO> getSalesAnalysisByClientAndShop(Map parameters);
	
	public List<PurchaseOrder> getSalesHistoryList(Map parameters);
	
	public List getSpendingSummaryList(Map parameters);
	
	public Double getTotalAmountByProdType(Date startDate, Date endDate, Long shopId, Long companyId, String status, String prodType);
	
	public Double getTotalRevenueByFilters(Date startDate,Date endDate,Long shopId,Long companyId);
	
	public Double getTotalServiceByFilters(Date startDate,Date endDate,Long shopId,Long companyId);
	
	public Double getTotalPackageByFilters(Date startDate,Date endDate,Long shopId,Long companyId);
	
	public Double getTotalRetailByFilters(Date startDate,Date endDate,Long shopId,Long companyId);
	
	public List<PurchaseOrder> getPurchaseOrderByIdsOfSalesDetails(SalesSearchVO salesSearchVO);
	
	public Map<String,Object> getPaymentAmountAndTotalRevenueAndGrossRevenueByOrderList(List<PurchaseOrder> orderList,Long staffId);
	public Map<String,Object> getPaymentAmountAndTotalRevenueAndGrossRevenueByVO(SalesSearchVO salesSearchVO);
	
	public Long getCountOrdersByFilters(String fromDate,String endDate,String orderStatus,Long userId);
	
	public void runFTCJourney(Long userId,Long marketingCampaignId);
	public void sendThankYouEmail(PurchaseOrder purchaseOrder);
	
	public Long getCountMembersFromOrdersByFilters(SalesSearchVO salesSearchVO);
	
	public PurchaseOrder saveAppsPurchaseOrder(Long bookingId,Boolean paid);
	public PurchaseOrder saveAppsPurchaseOrder2(Long bookingId);
	public void handelStatusAfterPaid(Long bookingId);
	
}
