<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
    <div class="col-lg-5 topmargin">
        ${bookQuickVO.therapist.displayName}
        <c:if test="${bookQuickVO.doubleBooking !=null && bookQuickVO.doubleBooking}">
        	<input type="hidden" name="startTimeStamp" value="${bookQuickVO.startTimeStamp}"/>
        </c:if>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.appointment.time"/></label>
    <div class="col-lg-5 topmargin">
        <fmt:formatDate value="${bookQuickVO.startAppointmentTime}" pattern="yyyy-MM-dd HH:mm"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
    <div class="col-lg-5 topmargin">
        ${member.fullName}
    </div>
</div>
<c:if test="${member.accountType eq 'GUEST'}">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.firstname"/></label>
        <div class="col-lg-5">
                ${bookQuickVO.firstName}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.lastname"/></label>
        <div class="col-lg-5">
                ${bookQuickVO.lastName}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.email"/></label>
        <div class="col-lg-5">
                ${bookQuickVO.email}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.mobile.phone"/></label>
        <div class="col-lg-5">
                ${bookQuickVO.mobilePhone}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.country"/></label>
        <div class="col-lg-5">
                ${bookQuickVO.country}
        </div>
    </div>
</c:if>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.treatment"/></label>
    <div class="col-lg-5 topmargin">
        ${bookQuickVO.productOption.label3}
    </div>
</div>
<%--<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.walkin"/></label>
    <div class="col-lg-5 topmargin">
        ${bookQuickVO.walkIn != null && bookQuickVO.walkIn ? 'true': "false"}
    </div>
</div>--%>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.pregnancy"/></label>
    <div class="col-lg-5 topmargin">
        ${bookQuickVO.pregnancy != null && bookQuickVO.pregnancy ? 'true': "false"}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
    <div class="col-lg-5 topmargin">
        ${bookQuickVO.remarks}
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label">Requested</label>
    <div class="col-lg-5 topmargin">
        ${bookQuickVO.requested != null && bookQuickVO.requested ? 'true': "false"}
    </div>
</div>
<c:choose>
    <c:when test="${bookQuickVO.assignRoom ne null}">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.room"/></label>
            <div class="col-lg-5 topmargin">
                    ${bookQuickVO.assignRoom.name}
                <input type="hidden" name="roomId" value="${bookQuickVO.assignRoom.id}">
                <input type="hidden" name="status" value="${bookQuickVO.status}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.status"/></label>
            <div class="col-lg-5 topmargin">
                <spring:message code="label.book.status.${bookQuickVO.status}"/>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.room"/></label>
            <div class="col-lg-5 topmargin">
                <b><font color="red"><spring:message code="label.room.not.available"/></font></b> 
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.status"/></label>
            <div class="col-lg-5 topmargin">
                 <b><font color="red"><spring:message code="label.book.status.WAITING"/></font></b>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<script type="text/javascript">
   <c:if test="${not empty error}">
    Dialog.alert({
        title: "<spring:message code="lable.error"/>",
        message: "${error}",
        callback: function () {
            $('#previous', Dialog.getContext()).trigger('click');
        }
    });
    </c:if>
</script>
