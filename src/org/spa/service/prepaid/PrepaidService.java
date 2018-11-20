package org.spa.service.prepaid;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.spa.jxlsBean.importDemo.PrepaidImportGiftBean;
import org.spa.dao.base.BaseDao;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.prepaid.PrepaidAddVO;
import org.spa.vo.sales.OrderItemVO;

import com.spa.jxlsBean.importDemo.PrepaidImportJxlsBean;

/**
 * Created by Ivy on 2016/04/11.
 */
public interface PrepaidService extends BaseDao<Prepaid>{
	//
    Double getCalCommissionRateForPrepaid(PrepaidAddVO prepaidAddVO);
	
	void savePrepaid(PrepaidAddVO prepaidAddVO);
	
	Prepaid returnPrepaid(PrepaidAddVO prepaidAddVO);
	void savePrepaidTopUpTransaction(Prepaid prepaid, PrepaidAddVO prepaidAddVO, Boolean isTopUp);
	
	void deletePrepaid(Long prepaidId);
	
	void deletePrepaidTopUpTransaction(Long prepaidTopUpTransactionId);
	
	Set<Prepaid> getSuitablePackagesByFilter(Long memberId, OrderItemVO orderItemVO, String prepaidSuitableOption,Boolean usingCashPackage);
	
	//check out
    void reviewPrepaidWhenPaidByPrepaid(OrderItemVO orderItemVO, Long prepaidId, Long purchaseItemId);
	
	// just for 验证
    void checkingPrepaidWhenPaidByPrepaid(Long prepaidId, OrderItemVO orderItemVO, HttpSession httpSession);
	
	void sendVoucherNotificationEmail(PrepaidTopUpTransaction ptt, HttpServletRequest request);
	void sendVoucherConfirmEmail(PrepaidTopUpTransaction ptt, HttpServletRequest request);
	
	void sendVoucherNotificationEmail(Long prepaidId, HttpServletRequest request);

	Set<Long>getUserIdsForPackageExpiryJourney(String prepaidType, Integer days, Integer remainUnits);
	Set<Long>getUserIdsForPackageEngagementJourney(Date from ,String subCode,String type);
	
	public Set<Long> getMemberIdsByExpiryPrepaid(String fromDate,String toDate,String prepaidType,Boolean hasRemaining);

	List<Prepaid> getPrepaidById(Long memberId, Long companyId);
	
	Boolean checkWhetherHasExpiringPackage(Long memberId);

	List<PrepaidImportJxlsBean> saveImportPrepaid(ImportDemoVO importDemoVO);

    List<PrepaidImportGiftBean> importPrepaidGift(ImportDemoVO importDemoVO);
}
