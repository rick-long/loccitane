<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/front" %>
<t:layout title="My prepaid">
	<h2><spring:message code="front.label.package.voucher"/> </h2>
	<div id="myTabContent" class="tab-content">
    <div style="border: 1px solid ;margin-bottom: 1%">
	<table class="table table-striped" style="margin-top: 1%;margin-bottom: 1%;width: 98%">
		<thead>
		<tr>
			<th><spring:message code="label.prepaid.type" /></th>
			<th><spring:message code="label.reference" /></th>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="left.navbar.treatment"/> </th>
			<th><spring:message code="label.prepaid.bought.shop"/> </th>
			<th><spring:message code="label.prepaidValue"/> </th>
			<th><spring:message code="label.prepaid.init.value" /></th>
			<th><spring:message code="label.prepaid.remain.value" /></th>
			<th><spring:message code="label.purchase.date" /></th>
			<th><spring:message code="front.label.expiry.date"/> </th>
			<th><spring:message code="label.isactive" /></th>
			<%-- <th><spring:message code="label.remarks" /></th> --%>
		</tr>
		</thead>
			<c:forEach items="${prepaidList}" var="item">
				<tr data-id="${item.id}" class='<c:if test="${item.isActive == 'false'}">deleted_table</c:if>'>
					<td><c:set var="prepaidType">
						${fn:replace(item.prepaidType, "_", "")}
					</c:set> <spring:message code="label.prepaid.type.${prepaidType}" />
					</td>
					<td>${item.reference}</td>
					<td>${item.name}</td>
					<td>
						<c:if test="${item.prepaidType == 'TREATMENT_PACKAGE' || item.prepaidType == 'TREATMENT_VOUCHER' }">
							${item.firstPrepaidTopUpTransaction.productOption.label3}
						</c:if>
					</td>
					<td>${item.shop.name }</td>
					<td>${item.prepaidValue}</td>
					<td>${item.initValue}</td>
					<td>${item.remainValue}</td>
					<td><fmt:formatDate value="${item.firstPrepaidTopUpTransaction.topUpDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${item.firstPrepaidTopUpTransaction.expiryDate}" pattern="yyyy-MM-dd"/></td>
					<td><spring:message code="label.${item.isActive}" /></td>
					<%-- <td>${item.remarks}</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
    </div>
</t:layout>