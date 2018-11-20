<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>
<style type="text/css">
    .week .input-group {
        width: 113px;
    }
</style>
<div class="row new-row-width week">
<hr>
    <label><spring:message code="label.staff.schedule.from"/> <fmt:formatDate value="${scheduleWeekVO.startDate}" pattern="yyyy-MM-dd"/> <spring:message code="label.staff.schedule.to"/>
        <fmt:formatDate value="${scheduleWeekVO.endDate}" pattern="yyyy-MM-dd"/>
    </label>
    <div class="schedule_warning">
        <spring:message code="label.staff.schedule.warning.msg"/>
        <fmt:formatDate value="${scheduleWeekVO.startDate}" pattern="yyyy-MM-dd"/>
    </div>
    <table class="table schedule_table">
        <thead>
        <tr>
            <th></th>
            <th></th>
            <th colspan="2"><spring:message code="label.staff.work.hours"/></th>
            <th colspan="2"><spring:message code="label.block.type.LUNCH"/></th>
            <th>&nbsp;</th>
        </tr>
        <tr>
            <th></th>
            <th></th>
            <th><spring:message code="label.start.time"/></th>
            <th><spring:message code="label.end.time"/></th>
            <th><spring:message code="label.start.time"/></th>
            <th><spring:message code="label.end.time"/></th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scheduleWeekVO.scheduleDayVOList}" var="item" varStatus="idx">
            <tr>
                <td>
                    <input name="scheduleDayVOList[${idx.index}].date" type="hidden" value='<fmt:formatDate value="${item.date}" pattern="yyyyMMdd"/>'>
                    <input name="scheduleDayVOList[${idx.index}].name" type="hidden" value="${item.name}">
                        ${item.name}
                </td>
                <td>
                    <select name="scheduleDayVOList[${idx.index}].type" class="selectpicker form-control typeSelect ${idx.first? 'first' : ''}">
                        <option value="Workday" ${item.type eq 'Workday' ? 'selected' : ''}><spring:message code="label.staff.schedule.workday"/></option>
                        <option value="Day Off" ${item.type eq 'Day Off' ? 'selected' : ''}><spring:message code="label.staff.schedule.dayoff"/></option>
                    </select>
                </td>
                <c:set var='workHours' value='${item.scheduleTimeVOMap.get("Work Hours")}'/>
                <c:set var="dayOff" value="${item.scheduleTimeVOMap.get('Lunch')}"/>
                <td>
                    <div class="input-group">
                        <input name="scheduleDayVOList[${idx.index}].scheduleTimeVOMap['Work Hours'].startTime" class="form-control time workHoursStart" value='${workHours.startTime}' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </td>
                <td>
                    <div class="input-group">
                        <input name="scheduleDayVOList[${idx.index}].scheduleTimeVOMap['Work Hours'].endTime" class="form-control time workHoursEnd" value='${workHours.endTime}' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </td>
                <td>
                    <div class="input-group">
                        <input name="scheduleDayVOList[${idx.index}].scheduleTimeVOMap['Lunch'].startTime" class="form-control time dayOffStart" value='${dayOff.startTime}' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </td>
                <td>
                    <div class="input-group">
                        <input name="scheduleDayVOList[${idx.index}].scheduleTimeVOMap['Lunch'].endTime" class="form-control time dayOffEnd" value='${dayOff.endTime}' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </td>
                <td>
                    <input type="button" class="btn copyBtn btn-xs ${idx.first? 'hide' : ''}" value='<spring:message code="label.staff.schedule.copy.prev.day"/>'>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('.time', getContext()).datetimepicker({
            format: 'H:i',
            timepicker: true,
            step: 30,
            datepicker: false
        });

        $('.typeSelect', getContext()).change(function () {
            var $this = $(this);
            var parentTr = $this.parents('tr');
            if ("Workday" == $this.val()) {
                parentTr.find('.input-group').removeClass('hide');
                parentTr.find('.copyBtn').removeClass('hide day-off');
            } else {
                parentTr.find('.input-group').addClass('hide');
                parentTr.find('.copyBtn').addClass('hide day-off');
            }
            if ($this.hasClass('first')) {
                parentTr.find('.copyBtn').addClass('hide');
            }
        }).trigger('change');

        $('.copyBtn', getContext()).click(function () {
            var $this = $(this);
            var parentTr = $this.parents('tr');
            var previousCopyBtn = parentTr.prevAll('tr').find('input.copyBtn:not(.day-off)').last();
            if (previousCopyBtn.length > 0) {
                var inputs = previousCopyBtn.parents('tr').find('input:visible').not(previousCopyBtn);
                inputs.each(function () {
                    var $self = $(this);
                    if ($self.val()) {
                        var inputClass = "." + $self.attr('class').replace(/ /g, '.');
                        $(inputClass, parentTr).val($self.val());
                    }
                });
            }
        });
    });
</script>
 