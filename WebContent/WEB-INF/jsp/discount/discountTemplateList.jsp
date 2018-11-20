<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.description"/></th>
        <th><spring:message code="label.attributes"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.name}</td>
            <td>${item.description}</td>
            <td>
            	<c:forEach items="${item.discountAttributeKeys}" var="dak">
                    <div>- ${dak.name} : ${dak.description}</div>
                </c:forEach>
            </td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="discount:discountTemplateView" href='<c:url value="/discount/discountTemplateView?id=${item.id}"/>' title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.view"/>'>
                    <i class="glyphicon glyphicon-eye-open"></i>
                </a>
                <a data-permission="discount:discountTemplateToEdit" href='<c:url value="/discount/discountTemplateToEdit?id=${item.id}"/>' title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn" data-width="680" data-title='<spring:message code="label.button.edit"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/discount/discountTemplateList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->