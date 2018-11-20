<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.appointment.time"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${bookItemEditVO.appointmentTime}" pattern="yyyy-MM-dd HH:mm"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.room"/></label>
    <div class="col-lg-5">
        ${bookItemEditVO.room.name}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.treatment"/></label>
    <div class="col-lg-5">
        ${bookItemEditVO.bookItem.productOption.label3}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
    <div class="col-lg-5">
        ${bookItemEditVO.therapist.displayName}
    </div>
</div>
<c:if test="${not empty error}">
    <input id="error" class="hidden" value="${error}"/>
</c:if>