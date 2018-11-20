<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-5 control-label"><spring:message code="label.shop"/></label>
        <div class="col-lg-6 topmargin">
            ${book.shop.name}
        </div>
    </div>
    <c:set var="guest" value="${book.guest}"/>
    <c:choose>
        <c:when test="${guest eq null}">
            <div class="form-group">
                <label class="col-lg-5 control-label"><spring:message code="label.fullName"/></label>
                <div class="col-lg-6 topmargin">
                        ${book.user.fullName}
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="form-group">
                <label class="col-lg-5 control-label"><spring:message code="label.firstname"/></label>
                <div class="col-lg-6">
                        ${guest.firstName}
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-5 control-label"><spring:message code="label.lastname"/></label>
                <div class="col-lg-6">
                        ${guest.lastName}
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-5 control-label"><spring:message code="label.email"/></label>
                <div class="col-lg-6">
                        ${guest.email}
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-5 control-label"><spring:message code="label.mobile.phone"/></label>
                <div class="col-lg-6">
                        ${guest.mobilePhone}
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-5 control-label"><spring:message code="label.country"/></label>
                <div class="col-lg-6">
                        ${guest.country}
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="form-group">
        <label class="col-lg-5 control-label"><spring:message code="label.appointment.time"/></label>
        <div class="col-lg-6 topmargin">
            <fmt:formatDate value="${book.appointmentTime}" pattern="yyyy-MM-dd"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-5 control-label"><spring:message code="label.guest.amount"/></label>
        <div class="col-lg-6 topmargin">
            ${book.guestAmount}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-5 control-label"><spring:message code="label.remarks"/></label>
        <div class="col-lg-6 topmargin">
            ${book.remarks}
        </div>
    </div>
    <%--<div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.walkin"/></label>
        <div class="col-lg-5 topmargin">
            ${book.walkIn != null && book.walkIn ? 'true': "false"}
        </div>
    </div>--%>
    <div class="form-group">
        <label class="col-lg-5 control-label"><spring:message code="label.pregnancy"/></label>
        <div class="col-lg-6 topmargin">
            ${book.pregnancy != null && book.pregnancy ? 'true': "false"}
        </div>
    </div>
    <div class="pageList">
        <table id="treatmentTable" class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="label.start.time"/></th>
                <th><spring:message code="label.treatment"/></th>
                <th><spring:message code="label.therapist"/></th>
                <th><spring:message code="label.room"/></th>
                <th><spring:message code="label.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${book.bookItems}" var="item">
                <tr>
                    <td><fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/></td>
                    <td>${item.productOption.label3}</td>
                    <td>
                        <c:forEach items="${item.bookItemTherapists}" var="therapist" varStatus="status">
                            ${therapist.user.displayName} / ${therapist.onRequest}<c:if test="${not status.last}"><br/></c:if>
                        </c:forEach>
                    </td>
                    <td>${item.room.name}</td>
                    <td><spring:message code="label.book.status.${item.status}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>

