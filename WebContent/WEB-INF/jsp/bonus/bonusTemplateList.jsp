<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.attribute.keys"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.name}</td>
            <td>
            	<c:if test="${! empty item.bonusAttributeKeys}">
            		<c:forEach items="${item.bonusAttributeKeys}" var="keys">
            			${keys.name }  >>  ${keys.description } </br>
            		</c:forEach>
            	</c:if>
            </td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="bonus:bonusTemplateView" href='<c:url value="/bonus/bonusTemplateView?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.view"/>'>
                    <i class="glyphicon glyphicon-eye-open"></i>
                </a>
                <a data-permission="bonus:bonusTemplateToEdit" href='<c:url value="/bonus/bonusTemplateToEdit?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.edit"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/bonus/bonusTemplateList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->