<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.room"/></label>
    <div class="col-lg-5">
        ${block.room.name}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.type"/></label>
    <div class="col-lg-5">
        <spring:message code="label.block.type.${block.type}"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.start.date.time"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd HH:mm"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.end.date.time"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${blockVO.endDateTime}" pattern="yyyy-MM-dd HH:mm"/>
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