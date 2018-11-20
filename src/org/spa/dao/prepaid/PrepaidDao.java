package org.spa.dao.prepaid;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.vo.report.PrepaidAnalysisVO;


public interface PrepaidDao {

	List<PrepaidTopUpTransaction> getPrepaidTopUpTransactionByIds(final Long[] pttIds);
	
	List<Prepaid> getSuitablePackagesByFilter(final Long memberId, final Long productOptionId, final String prepaidSuitableOption, final String prodType,final Boolean usingCashPackage);
	
	List<PrepaidTopUpTransaction> getPrepaidTopUpTransactions(final String finishDate, final Long shopId, final Long companyId);
	
	Map<Long, PrepaidAnalysisVO> getPrepaidOutstandingUsedByHql(final String finishDate, final Long shopId, final Long companyId);
	
	List<PrepaidTopUpTransaction> getExpiringPrepaidsByFilters(final String fromDate, final String endDate, final String prepaidType);
	
	Double sumRemainValue(final String fromDate, final String prepaidType, final Long userId);
	
	Set<Long> getUserIdsForPackageExpiryJourney(String prepaidType, String fromDate, String toDate, Integer remainUnits);
	
	Set<Long> getUserIdsForPackageEngagementJourney(final String fromDateTrans,final String toDateTrans,final String fromDateNoTrans,final String toDateNoTrans,final String type);
	
	public Set<Long> getMemberIdsByExpiryPrepaid(final String fromDate,final String toDate,final String prepaidType,final Boolean hasRemaining);

    List<Prepaid> getPrepaidByMemberId(Long member, Long companyId);
}
