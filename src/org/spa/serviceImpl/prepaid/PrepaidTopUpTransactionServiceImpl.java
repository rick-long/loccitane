package org.spa.serviceImpl.prepaid;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.dao.prepaid.PrepaidDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.service.prepaid.PrepaidTopUpTransactionService;
import org.spa.utils.DateUtil;
import org.spa.vo.report.PrepaidAnalysisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class PrepaidTopUpTransactionServiceImpl extends BaseDaoHibernate<PrepaidTopUpTransaction> implements PrepaidTopUpTransactionService{

	@Autowired
	public PrepaidDao prepaidDao;
	
	@Override
	public List<PrepaidTopUpTransaction> getActivePrepaidTopUpTransactionsByPrepaid(Prepaid prepaid) {
		DetachedCriteria dc = DetachedCriteria.forClass(PrepaidTopUpTransaction.class);
		dc.add(Restrictions.eq("isActive", true));
		dc.add(Restrictions.eq("prepaid.id", prepaid.getId()));
		
		dc.add(Restrictions.ge("expiryDate", DateUtil.getLastMinuts(new Date())));
		dc.add(Restrictions.gt("remainValue",0d));
		
		dc.addOrder(Order.asc("topUpDate"));
		List<PrepaidTopUpTransaction> ptts=list(dc);
		return ptts;
	}

	@Override
	public List<PrepaidTopUpTransaction> getClientPrepaidTopUpTransactionView(Map parameters) {
		Date startDate = (Date)parameters.get("startDate");
		Date endDate = (Date)parameters.get("endDate");
		Long companyId = (Long)parameters.get("companyId");
		Long userId = (Long)parameters.get("userId");
		DetachedCriteria idCriteria = DetachedCriteria.forClass(PrepaidTopUpTransaction.class);
		idCriteria.add(Restrictions.eq("company.id", companyId));
		idCriteria.add(Restrictions.eq("isActive", true));
		if (startDate!=null){
			idCriteria.add(Restrictions.ge("topUpDate",DateUtil.getFirstMinuts(startDate)));
		}	if (endDate!=null){
			idCriteria.add(Restrictions.ge("topUpDate",DateUtil.getFirstMinuts(startDate)));
		}
	    idCriteria.createAlias("prepaid", "prepaid");
	    idCriteria.add(Restrictions.eq("prepaid.user.id", userId));
	    idCriteria.addOrder(Order.desc("topUpDate"));
	    
	    List<PrepaidTopUpTransaction> pttlist=list(idCriteria);
		return pttlist;
	}

	@Override
	public Map  getPrepaidOutstandingMap(String finishDate, Long shopId, Long companyId) {
		Map returnMap = new HashMap();
		
		List<PrepaidAnalysisVO> volist= new ArrayList<PrepaidAnalysisVO>();
		List<PrepaidAnalysisVO> prepaidOutstandingSummaryList = new ArrayList<PrepaidAnalysisVO>();
		// get all active ptt by finish date or shop or company
		List<PrepaidTopUpTransaction> pttList = (List)prepaidDao.getPrepaidTopUpTransactions(finishDate, shopId, companyId);
		
		//
		Map<Long, PrepaidAnalysisVO> usedPrepaidMap = prepaidDao.getPrepaidOutstandingUsedByHql(finishDate, shopId, companyId);
		
		double cash_package_outstanding_amt=0;
		double treatment_package_outstanding_amt=0;
		double cash_voucher_outstanding_amt=0;
		double treatment_voucher_outstanding_amt=0;
		
		int cash_package_outstanding_count=0;
		int treatment_package_outstanding_count=0;
		int cash_voucher_outstanding_count=0;
		int treatment_voucher_outstanding_count=0;
		if (pttList!=null && pttList.size()>0) {
			for(PrepaidTopUpTransaction ptt : pttList){
				PrepaidAnalysisVO prepaidAnalysisVO = (PrepaidAnalysisVO)usedPrepaidMap.get(ptt.getId());
				if (prepaidAnalysisVO == null) {
					prepaidAnalysisVO = new PrepaidAnalysisVO();
					prepaidAnalysisVO.setPtt(ptt);
					prepaidAnalysisVO.setPrepaidType(ptt.getPrepaidType());
					prepaidAnalysisVO.setAmount(new Double(0));
				}
				volist.add(prepaidAnalysisVO);
				
				String prepaidType = ptt.getPrepaidType();
				if(CommonConstant.PREPAID_TYPE_CASH_PACKAGE.equals(prepaidType)){
					cash_package_outstanding_amt += prepaidAnalysisVO.getUnusedAmount();
					cash_package_outstanding_count++;
				}else if(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE.equals(prepaidType)){
					treatment_package_outstanding_amt += prepaidAnalysisVO.getUnusedAmount();
					treatment_package_outstanding_count++;
				}else if(CommonConstant.PREPAID_TYPE_CASH_VOUCHER.equals(prepaidType)){
					cash_voucher_outstanding_amt += prepaidAnalysisVO.getUnusedAmount();
					cash_voucher_outstanding_count++;
				}else if(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER.equals(prepaidType)){					//
					treatment_voucher_outstanding_amt += prepaidAnalysisVO.getUnusedAmount();
					treatment_voucher_outstanding_count++;
				}
			}
		}
		
		PrepaidAnalysisVO prepaidAnalysisVO1 = new PrepaidAnalysisVO();
		prepaidAnalysisVO1.setPrepaidType(CommonConstant.PREPAID_TYPE_CASH_PACKAGE);
		prepaidAnalysisVO1.setAmount(new Double(cash_package_outstanding_amt));
		prepaidAnalysisVO1.setCount(new Integer(cash_package_outstanding_count));
		
		PrepaidAnalysisVO prepaidAnalysisVO2 = new PrepaidAnalysisVO();
		prepaidAnalysisVO2.setPrepaidType(CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE);
		prepaidAnalysisVO2.setAmount(new Double(treatment_package_outstanding_amt));
		prepaidAnalysisVO2.setCount(new Integer(treatment_package_outstanding_count));

		PrepaidAnalysisVO prepaidAnalysisVO3 = new PrepaidAnalysisVO();
		prepaidAnalysisVO3.setPrepaidType(CommonConstant.PREPAID_TYPE_CASH_VOUCHER);
		prepaidAnalysisVO3.setAmount(new Double(cash_voucher_outstanding_amt));
		prepaidAnalysisVO3.setCount(new Integer(cash_voucher_outstanding_count));
		
		PrepaidAnalysisVO prepaidAnalysisVO4 = new PrepaidAnalysisVO();
		prepaidAnalysisVO4.setPrepaidType(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER);
		prepaidAnalysisVO4.setAmount(new Double(treatment_voucher_outstanding_amt));
		prepaidAnalysisVO4.setCount(new Integer(treatment_voucher_outstanding_count));
		
		prepaidOutstandingSummaryList.add(prepaidAnalysisVO1);
		prepaidOutstandingSummaryList.add(prepaidAnalysisVO2);
		prepaidOutstandingSummaryList.add(prepaidAnalysisVO3);
		prepaidOutstandingSummaryList.add(prepaidAnalysisVO4);
		
		returnMap.put("prepaidOutstandingSummaryList", prepaidOutstandingSummaryList);
		returnMap.put("prepaidAnalysisVOList", volist);
		
		return returnMap;
	}

	@Override
	public List<PrepaidTopUpTransaction> getExpiringPrepaidsByFilters(Date startDate) {
		
		Calendar calendar = new GregorianCalendar();
	    calendar.setTime(startDate);
	    calendar.add(5, 1);
//	      calendar.add(2, Integer.parseInt(timeToExpiryToDay));
	    
		try {
			List<PrepaidTopUpTransaction> results = prepaidDao.getExpiringPrepaidsByFilters(DateUtil.dateToString(startDate,"yyyy-MM-dd HH:mm:ss"),
					DateUtil.dateToString(DateUtil.getLastMinuts(calendar.getTime()),"yyyy-MM-dd HH:mm:ss"), CommonConstant.PREPAID_TYPE_CASH_PACKAGE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
