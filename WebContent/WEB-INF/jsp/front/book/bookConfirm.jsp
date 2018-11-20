<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/front/common/jspInit.jsp" %>
<div class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.shop"/></label>
        <div class="col-lg-5">
            ${frontBookVO.shop.name}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label"><spring:message code="label.appointment.time"/></label>
        <div class="col-lg-5">
            <fmt:formatDate value="${frontBookVO.appointmentDate}" pattern="yyyy-MM-dd"/>
        </div>
    </div>
</div>
<div class="row">
    <table id="treatmentTable" class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="label.start.time"/></th>
            <th><spring:message code="label.treatment"/></th>
            <th><spring:message code="label.therapist"/></th>
            <th><spring:message code="label.therapist.on.request"/></th>
            <th><spring:message code="label.room"/></th>
            <th><spring:message code="label.status"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${frontBookVO.frontBookItemVOs}" var="item">
            <tr>
                <td><fmt:formatDate value="${item.appointmentTime}" pattern="HH:mm"/></td>
                <td>${item.productOption.label3}</td>
                <td>${item.therapist.displayName}</td>
                <td>${item.onRequest}</td>
                <td>${item.room.name}</td>
                <td><spring:message code="label.book.status.${item.status}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
