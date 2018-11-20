<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped ${nullClass}">
	<thead>
		<tr>
			<th><spring:message code="label.date" /></th>
			<%-- <th><spring:message code="label.shop" /></th> --%>
			<th><spring:message code="label.payroll.staff" /></th>
			<th width="18%"><spring:message code="label.total.commission" /></th>
			<th width="13%"><spring:message code="label.total.bonus" /></th>
			<th><spring:message code="label.payroll.standard.salary" /></th>
			<th><spring:message code="label.payroll.total.additional.BF.MPF" /></th>
			<th><spring:message code="label.payroll.leave.pays" /></th>
			<th><spring:message code="label.payroll.is.used.GM" /></th>
			<th><spring:message code="label.payroll.final.salary.before.contribution" /></th>
			<th><spring:message code="label.payroll.contribution" /></th>
			<th><spring:message code="label.payroll.total.additional.af.mpf" /></th>
			<th><spring:message code="label.payroll.final.salary" /></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${page !=null }">
			<c:forEach items="${page.list}" var="item">
				<tr data-id="${item.id}">
					<td style="font-size:11px!important;">${item.payrollDate}</td>
					<%-- <td>${item.staff.shop.name}</td> --%>
					<td  style="font-size:11px!important;">${item.staff.displayName}</td>
					<td>
						<fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.totalCommission}" /></br>
						<c:forEach items="${item.staffPayrollCategoryStatisticses }" var="spcs">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr>
							    	<td style="font-size:9px!important;text-align:left;">${spcs.bonusType} : </td>
							    	<td style="font-size:9px!important;text-align:right;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spcs.salesStatistics}" /> :</td>
							    	<td style="font-size:9px!important;text-align:right;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spcs.commissionStatistics}" /></td>
							  </tr>
							</table>
						</c:forEach>
						<br/>
					</td>
					<td>
						<fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.totalBonus}" /></br>
						<c:forEach items="${item.staffPayrollCategoryStatisticses }" var="spcs">
							<c:if test="${spcs.bonusStatistics !=null || spcs.bonusStatistics !=0}">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  		<tr>
									    <td style="font-size:9px!important;text-align:left;">${spcs.bonusType } : </td>
									    <td style="font-size:9px!important;text-align:right;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spcs.bonusStatistics}" /></td>
							    	</tr>
							    </table>
							</c:if>
						</c:forEach>
					</td>
					<td style="font-size:11px!important;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.standardSalary}" /></td>
					<td style="font-size:11px!important;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.totalAdditionalBfMpf}" /></td>
					<td style="font-size:11px!important;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.leavePays}" /></td>
					<td style="font-size:9px!important;">
						<c:if test="${item.isUsedGm}">Y</br> (<fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.guaranteedMinimum}" />)</c:if>
						<c:if test="${!item.isUsedGm}">N</c:if>
					</td>
					<td style="font-size:11px!important;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.finalSalaryBeforeContribution}" /></td>
					<td style="font-size:11px!important;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.contribution}" /></td>
					<td style="font-size:11px!important;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.totalAdditionalAfMpf}" /></td>
					<td style="font-size:11px!important;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.finalSalary}" /></td>
					<td style="font-size:11px!important;">
						<a data-permission="payroll:toEdit" href='<c:url value="/payroll/regenerate?id=${item.id}"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn"
								data-title='<spring:message code="label.dialog.confirm"/>' data-message='Are you sure to re-generate ?'>
	                         <i class="glyphicon glyphicon-refresh"></i>
	                    </a>
						<a data-permission="payroll:toEdit" href='<c:url value="/payroll/toEdit?id=${item.id}"/>' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.edit"/>'>
	                        <i class="glyphicon glyphicon-edit btn-edit"></i>
	                    </a>
						<a data-permission="payroll:toEdit" href='<c:url value="/payroll/print?payrollId=${item.id}"/>' title='<spring:message code="label.button.print"/>' class="btn btn-primary btn-edit">
							<i class="glyphicon glyphicon-print"></i>
						</a>
					</td>
				</tr>
			</c:forEach>
          </c:if>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/payroll/list" scope="request" />
<c:import url="/WEB-INF/jsp/common/pagination.jsp" />
<!-- page end  -->