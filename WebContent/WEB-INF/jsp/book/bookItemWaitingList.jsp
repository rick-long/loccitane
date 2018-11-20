<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div><spring:message code="label.book.waiting.list"/> </div>
<div class="list-group">
    <c:forEach items="${bookItemList}" var="item">
        <div class="list-group-item waiting-box"
             data-book-item-id="${item.id}"
             data-duration="${item.duration}"
             data-children = "${item.resourceSize}" >
            <p class="list-group-item-text">
            <div>${item.productOption.label3}</div>
            <div><fmt:formatDate value="${item.appointmentTime}" pattern="yyyy-MM-dd HH:mm"/></div>
            <div>${item.book.user.fullName}</div>
            <div>${item.therapistNames}</div>
            <div>Requested:	${item.requestedOfFirstTherapist}</div>
            <div>Booked by:	${item.createdBy}</div>
            <%--<div>${item.room.name}</div>--%>
            </p>
        </div>
    </c:forEach>
</div>