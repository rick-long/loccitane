<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th class="col-lg-1"><spring:message code="label.name"/></th>
        <th><spring:message code="label.email"/></th>
        <th><spring:message code="label.contactTel"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.name }</td>
            <td>${item.email}</td>
            <td>${item.telNum}</td>
            <td>
                <a href='<c:url value="/member/toFamilyEdit?id=${item.id}"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
                   data-title='<spring:message code="label.family.edit.management"/>'>
                    <i class="glyphicon glyphicon-edit"></i></a>
                <a data-permission="member:groupDelete" href='<c:url value="/member/familyDelete?id=${item.id}"/>'
                   class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn"
                   data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.remove"/>'>
                    <i class="glyphicon glyphicon-remove"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/member/familyList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->