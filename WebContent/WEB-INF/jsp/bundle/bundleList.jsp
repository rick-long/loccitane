<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th><spring:message code="label.bundle.code"/></th>
    	<th><spring:message code="label.bundle.name"/> </th>
        <th><spring:message code="label.start.date"/></th>
        <th><spring:message code="label.end.date"/></th>
        <th><spring:message code="label.shop"/></th>
        <th><spring:message code="label.description"/> </th>
        <th><spring:message code="label.product.option"/> <spring:message code="label.bundle.group"/></th>
        <th><spring:message code="label.bundle.amount"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
        	<td>${item.code}</td>
            <td>${item.name}</td>
            <td><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"/></td>
            <td>
                <c:forEach items="${item.shops}" var="shop">
                    <div>${shop.name}</div>
                </c:forEach>
            </td>

            <td>${item.description}</td>
            <td>
                <c:forEach items="${item.bundleDetails}" var="map">
                    <div>
                    	<spring:message code="label.bundle.group"/> ${map.key+1}:
	                    <c:forEach items="${map.value}" var="po">
	                    	${po.label33 }/
	                    </c:forEach>
                    </div>
                </c:forEach>
            </td>
            <td> <spring:message code="label.currency.default"/><fmt:formatNumber type="number" pattern="#,#00.0#" value="${item.bundleAmount}"/></td>
            <td>
                <a data-permission="bundle:bundleEdit" href='<c:url value="/bundle/bundleEdit"/>?id=${item.id}' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-reload-btn=".search-btn" data-title='<spring:message code="label.bundle.edit"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
                <c:if test="${item.isActive}">
                    <a data-permission="bundle:bundleRemove" href='<c:url value="/bundle/bundleRemove"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
                        <i class="glyphicon glyphicon-trash"></i>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/bundle/bundleList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->