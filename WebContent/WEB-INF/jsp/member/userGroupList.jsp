<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.reference"/></th>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.remarks"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${item.isActive == 'false'}">deleted_table</c:if>'>
            <td>${item.reference}</td>
            <td>${item.name }</td>
            <td>${item.remarks}</td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="member:toUserGroupEdit" href='<c:url value="/member/toUserGroupEdit?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                   data-title='<spring:message code="label.user.group.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <a data-permission="member:toUserGroupEdit" href='<c:url value="/member/remove"/>?id=${item.id}' title="Remove" class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
                   <i class="glyphicon glyphicon-trash"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/member/userGroupList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->