<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.belongs.category"/></th>
        <th><spring:message code="label.unit"/></th>
        <th><spring:message code="label.default.value"/></th>
        <th><spring:message code="label.type"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.name}</td>
            <td>
                <c:forEach items="${item.categories}" var="cat">
                    ${cat.name}</br>
                </c:forEach>
            </td>
            <td>
             	<spring:message code="label.unit.${item.unit}"/>
            </td>
            <td>${item.defaultValue}</td>
            <td><spring:message code="label.uiType.${item.uiType}"/></td>
            <td>
                <a data-permission="pokey:toEdit" href='<c:url value="/pokey/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.pokey.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/pokey/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->