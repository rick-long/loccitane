<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <%-- <th><spring:message code="label.reference"/></th> --%>
        <th><spring:message code="label.belongs.category"/></th>
        <th><spring:message code="label.uiType"/></th>
        <th><spring:message code="label.initOption"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.name}</td>
            <%-- <td>${item.reference }</td> --%>
            <td>
                <c:forEach items="${item.categories}" var="cat">
                    ${cat.name}
                </c:forEach>
            </td>
            <td>
                <c:set var="uitype">
                    ${fn:replace(item.uiType, "-", "")}
                </c:set>
                <spring:message code="label.uiType.${uitype}"/>
            </td>
            <td>${item.initOption}</td>
            <td>
                <a data-permission="pdkey:toEdit" href='<c:url value="/pdkey/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.pdkey.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/pdkey/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->