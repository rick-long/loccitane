<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <%-- <th class="col-lg-2"><spring:message code="label.reference"/></th> --%>
        <th class="col-lg-3"><spring:message code="label.name"/></th>
        <%-- <th class="col-lg-21"><spring:message code="label.type"/></th> --%>
        <th class="col-lg-3"><spring:message code="label.module"/></th>
        <th class="col-lg-4"><spring:message code="label.remarks"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
            <%-- <td>${item.reference}</td> --%>
            <td>${item.name }</td>
            <%-- <td>${item.type }</td> --%>
            <td>${item.module }</td>
            <td>${item.remarks}</td>
            <td>
                <a data-permission="staff:toGroupEdit" href='<c:url value="/staff/toGroupEdit?id=${item.id}"/>' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                   data-title='<spring:message code="label.staff.group.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <c:if test="${item.module !='COMMISSION' && item.isActive}">
	                <a data-permission="staff:groupRemove" href='<c:url value="/staff/groupRemove?id=${item.id}"/>' 
	                	class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" title='<spring:message code="label.button.edit"/>'
                       data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.remove"/>'>
	                     <i class="glyphicon glyphicon-trash"></i>
	                </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/staff/groupList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->