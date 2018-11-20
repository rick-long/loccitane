<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th><spring:message code="label.description"/></th>
    	<th><spring:message code="label.usergroups"/> </th>
        <th><spring:message code="label.loyaltygroups"/> </th>
        <th><spring:message code="label.shop"/></th>
        <th><spring:message code="label.template.name"/></th>
        <th><spring:message code="label.start.time"/></th>
        <th><spring:message code="label.end.time"/></th>
        <th><spring:message code="label.categories"/> </th>
        <th><spring:message code="label.products"/> </th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
        	<td>${item.description}</td>
        	<td>
                <c:forEach items="${item.userGroups}" var="ug">
                    <div>${ug.name}</div>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${item.loyaltyGroups}" var="ll">
                    <div>${ll.name}</div>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${item.shops}" var="shop">
                    <div>${shop.name}</div>
                </c:forEach>
            </td>
            <td>${item.discountTemplate.name}</td>
            <td><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"/></td>
            <td>
                <c:forEach items="${item.categories}" var="c">
                    <div>${c.name}</div>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${item.products}" var="p">
                    <div>${p.name}</div>
                </c:forEach>
            </td>
            
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="discount:discountRuleView" href='<c:url value="/discount/discountRuleView?id=${item.id}"/>' title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.view"/>'>
                    <i class="glyphicon glyphicon-eye-open"></i>
                </a>
                <a data-permission="discount:discountRuleToEdit" href='<c:url value="/discount/discountRuleToEdit?id=${item.id}"/>' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.edit"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <c:if test="${item.isActive}">
	                <a data-permission="discount:discountRuleRemove" href='<c:url value="/discount/discountRuleRemove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
	                   <i class="glyphicon glyphicon-trash"></i>
	                </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/discount/discountRuleList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->