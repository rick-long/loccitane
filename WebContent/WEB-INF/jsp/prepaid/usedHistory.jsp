<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover sx-td">
	<thead>
	<tr>
		<th><spring:message code="label.date" /></th>
		<th><spring:message code="label.order.ref" /></th>
		<th><spring:message code="label.prepaid.product.name" /></th>
		<th><spring:message code="label.client" /></th>
		<th><spring:message code="label.prepaid.used.transaction.ref" /></th>
		<th><spring:message code="label.prepaid.therapist.s" /></th>
		<th><spring:message code="label.account" /></th>
		<th><spring:message code="label.prepaid.used.in.shop"/></th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${payments}" var="item" varStatus="idx">
		<tr data-id="${item.id}">
			<td><fmt:formatDate value="${item.purchaseOrder.purchaseDate}" pattern="yyyy-MM-dd"/></td>
			<td>${item.purchaseOrder.reference}</td>
			<td>${item.purchaseItem.productOption.product.category.name }/${item.purchaseItem.productOption.product.name }</td>
			<td>${item.purchaseOrder.user.fullName}</td>
			<td>${item.redeemPrepaidTopUpTransaction.topUpReference}</td>
			<td>${item.purchaseItem.therapistAndCommission}</td>
			<td><spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="0.00" value="${item.amount}" /></td>
			<td>${item.purchaseOrder.shop.name }</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
