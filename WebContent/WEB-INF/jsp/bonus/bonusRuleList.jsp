<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th width="14%"><spring:message code="label.template.name"/></th>
        <th width="25%"><spring:message code="label.description"/></th>
        <th><spring:message code="label.attributes"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th width="8%"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.bonusTemplate.name}</td>
            <td>${item.description}</td>
            <td>
            	<c:if test="${! empty item.bonusAttributes }">
            		<c:forEach items="${item.bonusAttributes}" var="ca">
            			${ca.bonusAttributeKey.name } >> ${ca.value }
            		</c:forEach>
            	</c:if>
            </td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="bonus:bonusRuleView" href='<c:url value="/bonus/bonusRuleView?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.view"/>'>
                    <i class="glyphicon glyphicon-eye-open"></i>
                </a>
                <a data-permission="bonus:bonusRuleToEdit" href='<c:url value="/bonus/bonusRuleToEdit?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.edit"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/bonus/bonusRuleList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->