<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div id="bookManagement" class="management">
<div class="orderDetails_management">
    <div class="contentdetail">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
            <div class="col-lg-5 topmargin">
                ${shop.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.fullName"/></label>
            <div class="col-lg-5 topmargin">
                ${member.fullName}
            </div>
        </div>
        <c:if test="${member.accountType eq 'GUEST'}">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.firstname"/></label>
                <div class="col-lg-5">
                        ${bookVO.firstName}
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.lastname"/></label>
                <div class="col-lg-5">
                        ${bookVO.lastName}
                </div>
            </div>
            <%--<div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.email"/></label>
                <div class="col-lg-5">
                        ${bookVO.email}
                </div>
            </div>--%>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.mobile.phone"/></label>
                <div class="col-lg-5">
                        ${bookVO.mobilePhone}
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.country"/></label>
                <div class="col-lg-5">
                        ${bookVO.country}
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.appointment.time"/></label>
            <div class="col-lg-5 topmargin">
                <fmt:formatDate value="${bookVO.startAppointmentTime}" pattern="yyyy-MM-dd"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
            <div class="col-lg-5 topmargin">
                ${bookVO.remarks}
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label"><spring:message code="label.walkin"/></label>
            <div class="col-lg-5 topmargin">
                ${book.walkIn != null && book.walkIn ? 'true': "false"}
            </div>
        </div>
    </div>
</div>
<div id="pageList">

    <table id="treatmentTable" class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="label.start.time"/></th>
            <th><spring:message code="label.end.time"/></th>
            <th><spring:message code="label.treatment"/></th>
            <th><spring:message code="label.therapist"/> / <spring:message code="label.therapist.on.request"/></th>
            <th><spring:message code="label.room"/></th>
            <th><spring:message code="label.status"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookVO.bookItemVOs}" var="item">
            <tr>
                <td><fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/></td>
                <td><fmt:formatDate value="${item.endAppointmentTime}" pattern="HH:mm"/></td>
                <td>${item.productOption.label3}</td>
                <td>
                    <c:forEach items="${item.requestTherapistVOs}" var="therapist">
                        <div>
                            <c:choose>
                                <c:when test="${not empty therapist.therapist}">
                                    ${therapist.therapist.displayName} / ${therapist.onRequest}
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="label.gender.UNKNOWN"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </td>
                <td>${item.room.name}</td>
                <td><spring:message code="label.book.status.${item.status}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</div>