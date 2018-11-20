<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>

<h3 style="color:white;">
    <spring:message code="label.staff.schedule"/>
</h3>
<c:url var="url" value="/staff/schedule"/>
<div class="error">${error}</div>
<form id="defaultForm" data-forward="staffListMenu" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url}'>
    <input type="hidden" id="id" name="id" value="${staff.id}">
    <div class="form-group">
        <label class="col-lg-2 control-label"><spring:message code="label.display.name"/>:</label>
        <div class="col-lg-5">
            <input type="hidden" name="staffId" value="${staff.id}">
            ${staff.displayName}
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label"><spring:message code="label.home.shop"/>:</label>
        <div class="col-lg-5">
            <select id="shopId" name="shopId" class="selectpicker form-control">
                <c:forEach items="${staff.staffHomeShops}" var="item">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label"><spring:message code="label.schedule.week"/>:</label>
        <div class="col-lg-5">
            <select id="weekStartId" name="weekStartMillis" class="selectpicker form-control">
                <c:forEach items="${weeks}" var="item">
                    <option value="${item.value}" ${item.selected ? 'selected' : ''}>${item.label}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div id="week"></div>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary formPageSubmit doubleConfirm" id="dlgSubmit">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
        function loadSchedule(shopId, millisWeekStart) {
            $.post('<c:url value="/staff/toScheduleWeek"/>', {
                staffId:${staff.id},
                shopId: shopId,
                millisWeekStart: millisWeekStart
            }, function (res) {
                $('#week', getContext()).html(res);
            });
        }

        $('#shopId', getContext()).change(function () {
            loadSchedule($(this).val(), $('#weekStartId', getContext()).val());
        }).trigger('change');

        $('#weekStartId', getContext()).change(function () {
            loadSchedule($('#shopId', getContext()).val(), $(this).val());
        });
    });
</script>
 