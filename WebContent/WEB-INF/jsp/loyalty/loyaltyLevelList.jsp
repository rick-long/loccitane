<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.reference"/></th>
        <th><spring:message code="label.loyalty.level.rank"/></th>
        <th><spring:message code="label.loyalty.level.required.points"/></th>
        <th><spring:message code="label.loyalty.discountrequiredspapoints"/> </th>
        <th><spring:message code="label.loyalty.level.month.limit"/></th>
        <th><spring:message code="label.loyalty.discountmonthlimit"/> </th>
        <th><spring:message code="label.remarks"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
            <td>${item.name}</td>
            <td>${item.reference }</td>
            <td>${item.rank}</td>
            <td>${item.requiredSpaPoints}</td>
            <td>${item.discountRequiredSpaPoints}</td>
            <td>${item.monthLimit}</td>
            <td>${item.discountMonthLimit}</td>
            <td>${item.remarks}</td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="loyalty:toEdit" href='<c:url value="/loyalty/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.loyalty.level.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <c:if test="${item.isActive}">
	                <a data-permission="loyalty:remove" href='<c:url value="/loyalty/remove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                   <i class="glyphicon glyphicon-trash"></i>
	                </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/loyalty/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->