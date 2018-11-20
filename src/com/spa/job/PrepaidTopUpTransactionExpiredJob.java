package com.spa.job;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.service.prepaid.PrepaidService;
import org.spa.service.prepaid.PrepaidTopUpTransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.spa.constant.CommonConstant;

public class PrepaidTopUpTransactionExpiredJob extends OpenSessionQuartzJobBean{

	@Autowired
	private PrepaidTopUpTransactionService prepaidTopUpTransactionService;
	@Autowired
	private PrepaidService prepaidService;
	
	
	public PrepaidTopUpTransactionExpiredJob() {
	}
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println("---- PrepaidTopUpTransactionExpiredJob---start ----");
		Date expiryDate = new Date();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrepaidTopUpTransaction.class);
		dc.add(Restrictions.le("expiryDate", expiryDate));
		dc.add(Restrictions.eq("isActive", true));
		
		List<PrepaidTopUpTransaction> pttList = prepaidTopUpTransactionService.list(dc);
		if(pttList !=null && pttList.size()>0){
			for(PrepaidTopUpTransaction ptt : pttList){
				
				ptt.setIsActive(false);
				ptt.setLastUpdated(expiryDate);
				ptt.setLastUpdatedBy("By System Auto");
				ptt.setRemarks(ptt.getRemarks() !=null ? ptt.getRemarks(): ""+" It disabled because of expired!");
				prepaidTopUpTransactionService.saveOrUpdate(ptt);
				
				// handle prepaid
				Prepaid prepaid =ptt.getPrepaid();
				
//				DetachedCriteria unactivedc = DetachedCriteria.forClass(PrepaidTopUpTransaction.class);
//				unactivedc.add(Restrictions.le("prepaid.id", prepaid.getId()));
//				unactivedc.add(Restrictions.eq("isActive", false));
//				List<PrepaidTopUpTransaction> unactiveList = prepaidTopUpTransactionService.list(unactivedc);
//				if(unactiveList !=null && unactiveList.size()>0 && unactiveList.size() == prepaid.getPrepaidTopUpTransactions().size()){
//					prepaid.setIsActive(false);
//					prepaidService.saveOrUpdate(prepaid);
//				}
				if(!prepaid.isIsActive()){
					continue;
				}
				if(ptt.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_CASH_VOUCHER) || ptt.getPrepaidType().equals(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER)){
					prepaid.setIsActive(false);
					prepaid.setLastUpdated(expiryDate);
					prepaid.setLastUpdatedBy("By System Auto");
					prepaid.setRemarks(prepaid.getRemarks() !=null ? prepaid.getRemarks(): ""+" It disabled because of expired!");
					prepaidService.saveOrUpdate(prepaid);
				}
			}
		}
	}

}
