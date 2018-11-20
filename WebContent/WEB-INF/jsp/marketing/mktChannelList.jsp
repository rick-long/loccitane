<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.reference"/></th>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.isactive"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.reference}</td>
            <td>${item.name }</td>
            <td><spring:message code="label.${item.isActive}"/></td>
            <td>
                <a data-permission="marketing:toMktChannelEdit" href='<c:url value="/marketing/toMktChannelEdit?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                   data-title='<spring:message code="label.mkt.channel.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/marketing/mktChannelList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->