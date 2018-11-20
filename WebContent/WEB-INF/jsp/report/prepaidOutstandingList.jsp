<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<h3 class="text-h3"><spring:message code="label.prepaid.outstanding.value.summary"/> </h3>
<div class="ibox-content">
	<table class="table table table-striped Value-Summary">
		<thead>
		<tr>
			<th><spring:message code="label.prepaid.type"/> </th>
			<th><spring:message code="label.count"/> </th>
			<th><spring:message code="label.prepaid.value"/> </th>
		</tr>
		</thead>
		<tbody>
		<c:if test="${prepaidOutstandingSummaryList !=null }">
			<c:set var="totalCount" value="0"/>
			<c:set var="totalAmount" value="0"/>
			<c:forEach items="${prepaidOutstandingSummaryList}" var="summaryObject">

				<tr>
					<td>${summaryObject.prepaidType}</td>
					<td>
						<fmt:formatNumber type="number" pattern="0.00" value="${summaryObject.count}"/>
						<c:set var="totalCount" value="${totalCount + summaryObject.count }"/>
					</td>
					<td>
						<fmt:formatNumber type="number" pattern="0.00" value="${summaryObject.amount}"/>
						<c:set var="totalAmount" value="${totalAmount + summaryObject.amount }"/>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td><spring:message code="label.total"/> </td>
			<td><b>${totalCount }</b></td>
			<td><b><fmt:formatNumber type="number" pattern="0.00" value="${totalAmount}"/></b></td>
		</tr>
		</tbody>
	</table>
</div>
<br/>
<h3 class="text-h3"><spring:message code="label.prepaid.outstanding.value.breakdown"/> </h3>
<div class="ibox-content">
	<table class="table table-striped Breakdown">
		<thead>
		<tr>
			<th><spring:message code="label.purchase.date"/> </th>
			<th><spring:message code="left.navbar.shop"/> </th>
			<th><spring:message code="label.prepaid.type"/> </th>
			<th><spring:message code="label.reference"/> </th>
			<th><spring:message code="label.name"/> </th>
			<th><spring:message code="label.client"/> </th>
			<th><spring:message code="label.initial.unit"/> </th>
			<th><spring:message code="label.remain.unit"/> </th>
			<th><spring:message code="label.outstanding.amount"/> </th>
			<th><spring:message code="label.valid.till"/> </th>
		</tr>
		</thead>
		<tbody>
		<c:set var="totalUnUsedAmount" value="0"/>
		<c:if test="${prepaidAnalysisVOList !=null }">
			<c:forEach items="${prepaidAnalysisVOList}" var="item">
				<tr>
					<td><fmt:formatDate value="${item.ptt.topUpDate}" pattern="yyyy-MM-dd"/></td>
					<td>${item.ptt.prepaid.shop.name}</td>
					<td>
							${item.prepaidType}
					</td>
					<td>${item.ptt.topUpReference}/${item.ptt.prepaid.reference}</td>
					<td>${item.ptt.description}</td>
					<td>${item.ptt.prepaid.user.fullName}</td>
					<td>
						<fmt:formatNumber type="number" pattern="0.00" value="${item.ptt.topUpInitValue}"/>
					</td>
					<td>
						<fmt:formatNumber type="number" pattern="0.00" value="${item.ptt.remainValue}"/>
					</td>
					<td>
						<spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="0.00" value="${item.unusedAmount}"/>
						<c:set var="totalUnUsedAmount" value="${totalUnUsedAmount + item.unusedAmount }"/>

					</td>
					<td>
						<fmt:formatDate value="${item.ptt.expiryDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="0.00" value="${totalUnUsedAmount}"/></td>
				<td></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<table class="table invoice-total">
		<tr>
			<td align="right" colspan="6"><font color="#ff0000"><spring:message code="label.total.commission"/> : <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="0.00" value="${totalCommission}" /></font></td>
			<td align="right" colspan="2"><font color="#ff0000"><strong><spring:message code="label.sales.totalamount"/> : <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="0.00" value="${totalAmount}" /></strong></font></td>
		</tr>
		<c:if test="${!empty paymentAmount}">
			<tr>
				<td align="right" colspan="7">
					<c:forEach items="${paymentAmount}" var="p" varStatus="pidx">
						<font color="#888888">${p.key}: <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="0.00" value="${p.value}"/></font>
						<c:if test="${!pidx.last }">/</c:if>
					</c:forEach>
				</td>
			</tr>
		</c:if>
	</table>
</div>
<!-- page start  -->
<c:set var="pageUrl"
	   value="${pageContext.request.contextPath}/report/listSalesDetails" scope="request" />
<c:import url="/WEB-INF/jsp/common/pagination.jsp" />
<!-- page end  -->