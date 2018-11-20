<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.channel"/></th>
        <th><spring:message code="label.subject"/></th>
        <th><spring:message code="label.type"/></th>
        <th><spring:message code="label.send.time"/></th>
        <th><spring:message code="label.send.amount"/></th>
        <th><spring:message code="label.remarks"/></th>
        <th><spring:message code="label.status"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.mktChannel.name}</td>
            <td>${item.subject}</td>
            <td>${item.type}</td>
            <td><fmt:formatDate value="${item.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${item.sendAmount}</td>
            <td>${item.remarks}</td>
            <td>${item.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/marketing/mktMailShotList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->