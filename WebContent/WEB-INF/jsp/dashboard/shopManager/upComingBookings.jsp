<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="col-lg-6 col-sm-12">
    <div class="table_show table-responsive">
        <div class="upcomingbooks_title"><spring:message code="label.upcomming.bookings"/></div>
        <c:set var="totalBookItems" value="${bookItemList.size()}"/>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th><spring:message code="label.appointment.time"/></th>
                <th><spring:message code="label.shop"/></th>
                <th><spring:message code="label.client"/></th>
                <%--<th><spring:message code="label.loyalty.level"/></th>--%>
                <th><spring:message code="label.therapist"/></th>
                <th><spring:message code="label.treatment"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bookItemList}" var="item">
                <c:set var="user" value="${item.book.user}"/>

                <tr data-id="${item.id}">
                    <td>
                        <fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/>
                        ~
                        <fmt:formatDate value="${item.appointmentEndTime}" pattern="HH:mm"/>
                    </td>
                    <td>${item.book.shop.name}</td>
                    <td>${user.fullName}</td>
                        <%--<td>${user.currentLoyaltyLevel.name}</td>--%>
                    <td>
                        <c:forEach items="${item.bookItemTherapists}" var="itemTherapist" varStatus="status">
                            <div>${itemTherapist.user.displayName}</div>
                        </c:forEach>
                    </td>
                    <td>${item.productOption.label3}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="total-style-homepage"><spring:message code="label.shop.manager.today.total"/> : ${ totalBookItems ne null ? totalBookItems : 0}</div>
    </div>
</div>
<div class="col-lg-6 col-sm-12">
    <div class="table_show table-responsive">
        <div class="upcomingbooks_title"><spring:message code="label.book.waiting.list"/> </div>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th><spring:message code="label.appointment.time"/></th>
                    <th><spring:message code="label.shop"/></th>
                    <th><spring:message code="label.client"/></th>
                    <%--<th><spring:message code="label.loyalty.level"/></th>--%>
                    <th><spring:message code="label.therapist"/></th>
                    <th><spring:message code="label.treatment"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${waitingList}" var="item">
                    <c:set var="user" value="${item.book.user}"/>
                    <tr data-id="${item.id}">
                        <td>
                            <fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/>
                            ~
                            <fmt:formatDate value="${item.appointmentEndTime}" pattern="HH:mm"/>
                        </td>
                        <td>${item.book.shop.name}</td>
                        <td>${user.fullName}</td>
                            <%--<td>${user.currentLoyaltyLevel.name}</td>--%>
                        <td>
                            <c:forEach items="${item.bookItemTherapists}" var="itemTherapist" varStatus="status">
                                <div>${itemTherapist.user.displayName}</div>
                            </c:forEach>
                        </td>
                        <td>${item.productOption.label3}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="upcomingbooks_tips"></div>
        </div>
    </div>