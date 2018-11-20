<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<html>
<head>
    <link href='<c:url value="/resources/css/base/print.css"/>' rel="stylesheet">
    <title>Print Inventory Purchase Order</title>
</head>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.company"/>:</label>
        <label class="col-lg-5">
            ${inventoryPurchaseOrder.company.name}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.supplier"/>:</label>
        <label class="col-lg-5">
            ${inventoryPurchaseOrder.supplier.name}
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.date"/>:</label>
        <label class="col-lg-5">
            <fmt:formatDate value="${inventoryPurchaseOrder.date}" pattern="yyyy-MM-dd"/>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.delivery.date"/>:</label>
        <label class="col-lg-5">
            <fmt:formatDate value="${inventoryPurchaseOrder.expectedDeliveryDate}" pattern="yyyy-MM-dd"/>
        </label>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.remarks"/>:</label>
        <label class="col-lg-5">
            ${inventoryPurchaseOrder.remarks}
        </label>
    </div>
    <label>Purchase Order Item Details:</label>
    <div class="row">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.name"/></th>
                <th><spring:message code="label.reference"/></th>
                <th><spring:message code="label.default.price"/></th>
                <th><spring:message code="label.qty"/></th>
                <th><spring:message code="label.total"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${inventoryPurchaseOrder.inventoryPurchaseItems}" var="item">
                <tr>
                    <td>${item.productOption.product.category.category.name}/${item.productOption.product.category.name}/${item.productOption.product.name}</td>
                    <td>${item.productOption.reference}</td>
                    <td>${item.productOption.originalPrice}</td>
                    <td>${item.qty}</td>
                    <td>${item.total}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>
