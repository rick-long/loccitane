<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="row new-row-width">
    <table class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="label.shop"/></th>
            <th><spring:message code="label.entry.date"/></th>
            <th><spring:message code="label.product"/></th>
            <th><spring:message code="label.transaction.type"/></th>
            <th><spring:message code="label.qty"/></th>
            <th><spring:message code="label.remarks"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${inventoryTransactionVOs}" var="item">
            <tr>
                <td>${item.shop.name}</td>
                <td><fmt:formatDate value="${item.entryDate}" pattern="yyyy-MM-dd"/></td>
                <td>${item.productOption.label6}</td>
                <td>${item.transactionType}</td>
                <td>${item.qty}</td>
                <td>${item.remarks}</td>
                <td>&nbsp;</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
