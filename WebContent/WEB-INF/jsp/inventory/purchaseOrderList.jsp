<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.reference"/></th>
        <th><spring:message code="label.supplier"/></th>
        <th><spring:message code="label.purchase.date"/></th>
        <th><spring:message code="label.delivery.date"/></th>
        <th><spring:message code="label.total.po.amount"/></th>
        <th><spring:message code="label.received.amount"/></th>
        <th><spring:message code="label.status"/></th>
        <th><spring:message code="label.remarks"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.reference}</td>
            <td>${item.supplier.name}</td>
            <td><fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${item.expectedDeliveryDate}" pattern="yyyy-MM-dd"/></td>
            <td>${item.totalAmount}</td>
            <td>${item.receivedAmount}</td>
            <td>${item.status}</td>
            <td>${item.remarks}</td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a href='<c:url value="/inventory/purchaseOrderView?id=${item.id}"/>' title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit" data-width="680" data-title='<spring:message code="label.button.view"/>'>
                     <i class="glyphicon glyphicon-eye-open"></i>
                </a>

                <c:if test="${item.status eq 'PENDING'}">
                    <a href='<c:url value="/inventory/purchaseOrderToAdd?id=${item.id}"/>' title='<spring:message code="label.button.edit"/>' class="btn btn-primary form-page btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.edit"/>'>
                         <i class="glyphicon glyphicon-edit"></i>
                    </a>
                </c:if>
                <c:if test="${item.status eq 'PENDING' || item.status eq 'RECEIVING'}">
                    <a href='<c:url value="/inventory/purchaseOrderToShipmentAdd?inventoryPurchaseOrderId=${item.id}"/>' title='<spring:message code="label.button.receive.goods"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.receive.goods"/>'>
                        <i class="glyphicon glyphicon-save"></i>
                    </a>
                </c:if>
                <a href='<c:url value="/inventory/print?id=${item.id}"/>' title='<spring:message code="label.button.print"/>' class="btn btn-primary btn-edit"><i class="glyphicon glyphicon-print"></i></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/inventory/purchaseOrderList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->