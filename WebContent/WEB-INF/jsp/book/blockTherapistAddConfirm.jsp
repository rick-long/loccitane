<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
    <div class="col-lg-5">
        ${therapist.displayName}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.type"/></label>
    <div class="col-lg-5">
        ${blockVO.type}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.repeat"/></label>
    <div class="col-lg-5">
        <spring:message code="label.block.repeat.${blockVO.repeatType}"/>
    </div>
</div>
<c:choose>
    <c:when test="${'NONE' eq blockVO.repeatType}">
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
    </c:when>
    <c:otherwise>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.repeat.start.date"/></label>
            <div class="col-lg-5">
                <fmt:formatDate value="${blockVO.repeatStartDate}" pattern="yyyy-MM-dd"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.repeat.end.date"/></label>
            <div class="col-lg-5">
                <fmt:formatDate value="${blockVO.repeatEndDate}" pattern="yyyy-MM-dd"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
            <div class="col-lg-5">
                    ${blockVO.repeatStartTime}
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
            <div class="col-lg-5">
                    ${blockVO.repeatEndTime}
            </div>
        </div>
        <c:if test="${not empty blockVO.months}">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.months"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <c:forEach items="${blockVO.months}" var="item">
                                    <span class="active">${item}</span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty blockVO.weeks}">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.weeks"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <c:forEach items="${blockVO.weeks}" var="item">
                                    <span class="active"><spring:message code="label.week.${item}"/></span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty blockVO.days}">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.days"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <c:forEach items="${blockVO.days}" var="item">
                                    <span class="active">${item}</span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </c:if>
    </c:otherwise>
</c:choose>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
    <div class="col-lg-5">
        ${blockVO.remarks}
    </div>
</div>
<c:if test="${not empty error}">
    <input id="error" class="hidden" value="${error}"/>
</c:if>