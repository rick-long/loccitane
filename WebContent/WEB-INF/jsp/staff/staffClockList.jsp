<%--
  Created by IntelliJ IDEA.
  User: rick
  Date: 2018/8/30
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="label.dateTime"/></th>
        <th><spring:message code="label.shopName"/></th>
        <th><spring:message code="label.clockType"/></th>
        <th><spring:message code="label.staff"/></th>
        <th><spring:message code="label.address"/></th>
        <th align="center"><spring:message code="label.clock.duration"/></th>
        <th align="center"><spring:message code="label.clock.ot"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}">
            <td>${item.date}</td>
            <td>${item.shopName}</td>
            <td>

                <c:if test="${item.type == 0}">
                    <spring:message code="label.option.clock.in"/>
                </c:if>
                <c:if test="${item.type == 1}">
                    <spring:message code="label.option.clock.out"/>
                </c:if>
            </td>
            <td>${item.name}</td>
            <td>${item.address.addressExtention}</td>
            <td>${item.duration}</td>
            <td>${item.ot}</td>
            <td>	
                 <a data-permission="attendance:toEdit" href='<c:url value="/staff/toEditClock?id=${item.id}"/>' title='<spring:message code="label.button.edit"/>' class="btn btn-primary dialog btn-edit" data-draggable="true"
					data-title='<spring:message code="left.navbar.attendance.clock"/>' data-width="880">
					<i class="glyphicon glyphicon-edit"></i>
				</a>
                <a data-permission="attendance:remove" href='<c:url value="/staff/deleteClock"/>?id=${item.id}' title='<spring:message code="label.button.remove"/>' class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn" data-title='<spring:message code="label.dialog.confirm"/>' data-message='<spring:message code="label.sure.to.deldete"/>'>
                    <i class="glyphicon glyphicon-trash"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- page start -->
<c:url var="pageUrl" value='/staff/staffClockList' scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->