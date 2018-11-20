<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:if test="${not empty error}">
    <input id="error" class="hidden" value="${error}"/>
</c:if>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
    <div class="col-lg-5">
        <c:if test="${member !=null}">${member.fullName}</c:if>
        <c:if test="${member ==null}"></c:if>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
    <div class="col-lg-5">
        <c:if test="${shop !=null}">${shop.name}</c:if>
        <c:if test="${shop ==null}"></c:if>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
    <div class="col-lg-5">
        <c:if test="${therapist !=null}">${therapist.displayName}</c:if>
        <c:if test="${therapist ==null}">Any</c:if>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.treatment.tree"/></label>
    <div class="col-lg-5">
        <c:if test="${po !=null}">${po.label33}</c:if>
        <c:if test="${po ==null}"></c:if>
    </div>
</div>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.repeat"/></label>
    <div class="col-lg-5">
        <spring:message code="label.book.batch.repeat.${bookBatchVO.repeatType}"/>
    </div>
</div>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.repeat.start.date"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${bookBatchVO.repeatStartDate}" pattern="yyyy-MM-dd"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.repeat.end.date"/></label>
    <div class="col-lg-5">
        <fmt:formatDate value="${bookBatchVO.repeatEndDate}" pattern="yyyy-MM-dd"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
    <div class="col-lg-5">
            ${bookBatchVO.repeatStartTime}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
    <div class="col-lg-5">
            ${bookBatchVO.repeatEndTime}
    </div>
</div>
<c:if test="${not empty bookBatchVO.months}">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.months"/></label>
        <div class="col-lg-5">
            <table class="time-panel">
                <tr>
                    <td>
                        <c:forEach items="${bookBatchVO.months}" var="item">
                            <span class="active">${item}</span>
                        </c:forEach>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</c:if>
<c:if test="${not empty bookBatchVO.weeks}">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.weeks"/></label>
        <div class="col-lg-5">
            <table class="time-panel">
                <tr>
                    <td>
                        <c:forEach items="${bookBatchVO.weeks}" var="item">
                            <span class="active"><spring:message code="label.repeat.booking.week.${item}"/></span>
                        </c:forEach>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</c:if>
<c:if test="${not empty bookBatchVO.days}">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.days"/></label>
        <div class="col-lg-5">
            <table class="time-panel">
                <tr>
                    <td>
                        <c:forEach items="${bookBatchVO.days}" var="item">
                            <span class="active">${item}</span>
                        </c:forEach>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</c:if>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
    <div class="col-lg-5">
        ${bookBatchVO.remarks}
    </div>
</div>