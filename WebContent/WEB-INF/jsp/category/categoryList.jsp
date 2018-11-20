<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.category.parent"/></th>
        <th><spring:message code="label.displayorder"/></th>
        <th><spring:message code="label.remarks"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <c:if test="${item.reference != 'ROOT_CATEGORY' }">
            <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
                <td>${item.name}</td>
                <td>${item.category.fullName}</td>
                <td>${item.displayOrder}</td>
                <td>${item.remarks}</td>
                <td>
                    <a data-permission="category:toEdit" href='<c:url value="/category/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.category.edit.management"/>'>
                        <i class="glyphicon glyphicon-edit"></i>
                    </a>
                    <c:if test="${item.isActive}">
	                	<a data-permission="category:remove" href='<c:url value="/category/remove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                       <i class="glyphicon glyphicon-trash"></i>
	                    </a>
                    </c:if>
                </td>
            </tr>
        </c:if>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/category/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->