<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.reference"/></th>
        <th><spring:message code="label.default.price"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.label2}</td>
            <td>${item.product.reference }</td>
            <td>${item.originalPrice}</td>
            <td class="hide">
                <input type="text" class="form-control quantity" data-product-option-id="${item.id}" data-price="${item.originalPrice}">
            </td>
            <td class="hide total"></td>
            <td>
                <button class="btn btn-primary selectPOBtn" data-product-option-id="${item.id}"><spring:message code="label.select"/></button>
                <button class="btn btn-warning removePOBtn hide" data-product-option-id="${item.id}"><spring:message code="label.remove"/></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/po/quickSearchForInventoryList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->