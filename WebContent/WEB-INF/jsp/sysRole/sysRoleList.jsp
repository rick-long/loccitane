<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.reference"/></th>
        <th><spring:message code="label.name"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.reference}</td>
            <td>${item.name }</td>
            <td>
                <a data-permission="sysRole:sysRoleEdit" href='<c:url value="/sysRole/sysRoleEdit?id=${item.id}"/>' title='<spring:message code="label.sys.role.edit.management"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                   data-title='<spring:message code="label.sys.role.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <a data-permission="sysRole:sysRolePermissionAssign" href='<c:url value="/sysRole/sysRolePermissionAssign?id=${item.id}"/>' title='<spring:message code="label.sys.role.permission.assign"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                   data-title='<spring:message code="label.sys.role.permission.assign"/>' data-width="700">
                    <i class="glyphicon glyphicon-pencil"></i><!--Permission assign-->
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/sysRole/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->