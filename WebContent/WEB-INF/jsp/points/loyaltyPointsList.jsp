<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:if test="${remainPoints !=null }">
	<h4><spring:message code="label.client"/> '${client }' <spring:message code="lable.has"/> ${remainPoints} <spring:message code="label.loyalty.points.not.expired"/> </h4>
</c:if>
<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.client"/></th>
        <th><spring:message code="label.purchase.date"/></th>
        <th><spring:message code="label.expiry.date"/></th>
        <th><spring:message code="label.earn.points"/></th>
        <th><spring:message code="label.redeemed.points"/></th>
        <th><spring:message code="label.earn.channel"/></th>
        <th><spring:message code="label.order.ref"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.user.firstName} ${item.user.lastName}</td>
            <td><fmt:formatDate value="${item.earnDate}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${item.expiryDate}" pattern="yyyy-MM-dd"/></td>
            <td>${item.earnPoints}</td>
            <td>${item.redeemedPoints}</td>
            <td>${item.earnChannel}</td>
            <td>
	            <c:if test="${item.purchaseOrder !=null }">
	            	${item.purchaseOrder.reference}
	            </c:if>
	            <c:if test="${item.purchaseOrder ==null }">
	            	<spring:message code="label.gender.UNKNOWN"/>
	            </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/lp/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->