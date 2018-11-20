<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.type"/></th>
        <th><spring:message code="label.subject"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td><spring:message code="label.marketing.email.template.type.${item.type}"/></td>
            <td>${item.subject}</td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <c:if test="${ item.type ne 'EMAIL_HEADER' || item.type ne 'EMAIL_END'}">
                    <a data-permission="marketing:toMktEmailTemplateEdit" href='<c:url value="/marketing/toMktEmailTemplateEdit?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                       data-title='<spring:message code="label.mkt.email.template.edit.management"/>' data-width="750">
                        <i class="glyphicon glyphicon-edit"></i>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/marketing/mktEmailTemplateList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->