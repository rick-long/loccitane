<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th class="col-lg-1"><spring:message code="label.shop"/></th>
        <th class="col-lg-2"><spring:message code="label.brand"/> </th>
        <th class="col-lg-2"><spring:message code="label.product"/></th>
        <th class="col-lg-1"><spring:message code="label.qty"/></th>
        <th class="col-lg-1"><spring:message code="label.direction"/></th>
        <th class="col-lg-2"><spring:message code="label.transaction.type"/></th>
        <th class="col-lg-1"><spring:message code="label.entry.date"/></th>
        <th class="col-lg-2"><spring:message code="label.remarks"/></th>
        <th class="col-lg-1"><spring:message code="label.isactive"/></th>
        <th class="col-lg-1"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.shop.name}</td>
            <td>${item.inventory.productOption.product.brand.name}</td>
            <td>${item.inventory.productOption.label6}</td>
            <td>${item.qty}</td>
            <td>${item.direction}</td>
            <td><spring:message code="label.inventory.transaction.type.${item.transactionType}"/></td>
            <td><fmt:formatDate value="${item.entryDate}" pattern="yyyy-MM-dd"/></td>
            <td>${item.remarks}</td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>


            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/inventory/transactionList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->