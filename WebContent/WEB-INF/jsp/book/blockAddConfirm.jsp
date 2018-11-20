<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%--<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
    <div class="col-lg-5">
        ${shop.name}
    </div>
</div>--%>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
    <div class="col-lg-5">
        ${therapist.displayName}
        
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${blockVO.startTime}" pattern="yyyy-MM-dd HH:mm"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${blockVO.endTime}" pattern="yyyy-MM-dd HH:mm"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.type"/></label>
    <div class="col-lg-5">
        ${blockVO.type}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
    <div class="col-lg-5">
        ${blockVO.remarks}
    </div>
</div>
<c:if test="${not empty error}">
    <input id="error" class="hidden" value="${error}"/>
</c:if>