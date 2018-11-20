<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th class="col-lg-4"><spring:message code="label.product"/></th>
        <th class="col-lg-2"><spring:message code="label.brand"/> </th>
        <th class="col-lg-2"><spring:message code="label.shop"/></th>
        <th><spring:message code="label.qty"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.productOption.label6}</td>
            <td>${item.productOption.product.brand.name}</td>
            <td>
                <c:set var="total" value="0"/>
                <c:forEach items="${item.inventoryWarehouses}" var="warehouse">
                <ul class="inventory_shop">
                    <li>${warehouse.shop.name} : <span style="font-color:#F00;font-weight:bold;text-align:right;">${warehouse.qty}</span></li>
                    </ul>
                    <c:set var="total" value="${total + warehouse.qty}"/>
                </c:forEach>
            </td>
            <td>${total}</td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="inventory:transferToAdd" href='<c:url value="/inventory/transferToAdd?inventoryId=${item.id}"/>' title='<spring:message code="label.button.transfer"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.transfer"/>'>
                    <i class="glyphicon glyphicon-transfer"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/inventory/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->