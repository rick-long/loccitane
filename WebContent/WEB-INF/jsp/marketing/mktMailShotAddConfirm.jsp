<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<%--<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.send.amount"/></label>
    <div class="col-lg-5">
        ${mktMailShotVO.userGroupIds}
    </div>
</div>--%>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.channel"/></label>
    <div class="col-lg-5">
        ${mktMailShotVO.channel.name}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.subject"/></label>
    <div class="col-lg-5">
        ${mktMailShotVO.subject}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.attachment"/></label>
    <label class="col-lg-5">
        <c:if test="${mktMailShotVO.attachment ne null && mktMailShotVO.attachment.size > 0}">
            ${mktMailShotVO.attachment.originalFilename}
        </c:if>
    </label>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.content"/></label>
    <div class="col-lg-5">
        ${mktMailShotVO.content}
    </div>
</div>
