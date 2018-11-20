<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.message"/></th>
        <th><spring:message code="label.status"/></th>
        <th><spring:message code="label.delivery.date"/></th>
		<th><spring:message code="label.platform"/></th>
        <th><spring:message code="label.operating"/></th>
    </tr>
    </thead>
    <tbody>
	    <c:forEach items="${page.list}" var="item">
	        <tr data-id="${item.id}">
	            <td>${item.title}</td>
	            <td>${item.status}</td>
	            <td><fmt:formatDate value="${item.sendDate}" pattern="yyyy-MM-dd"/></td>
	            <td>${item.sendType}</td>
	            <td>
	                <a data-permission="shop:toEdit" href='<c:url value="/push/toEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-title='<spring:message code="label.push.edit.management"/>'>
	                    <i class="glyphicon glyphicon-edit"></i>
	                </a>
	                <c:if test="${item.isActive}">
		                <a data-permission="brand:remove" href='<c:url value="/push/remove"/>?id=${item.id}' title="Remove" class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                       <i class="glyphicon glyphicon-trash"></i>
	                    </a>
                    </c:if>
	            </td>
	        </tr>
	    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/push/list" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->