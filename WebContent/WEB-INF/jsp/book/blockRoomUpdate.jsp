<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style type="text/css">
    #blockAddContext table.time-panel tr td {
        width: auto;
        height: auto;
        margin: 0 auto;
        text-align: left;
        color: #000;
    }

    #blockAddContext table.time-panel tr td span {
        width: 32px;
        height: 32px;
        line-height: 32px;
        border-radius: 16px;
        text-align: center;
        display: inline-block;
        cursor: pointer;
    }

    #blockAddContext table.time-panel tr td span.active, #blockAddContext table.time-panel tr td span:hover {
        color: #fff;
        background-color: #5eb0c6;
        border-color: #5eb0c6;
    }

    #blockAddContext #weeksField table.time-panel tr td span {
        width: 50px;
        height: 50px;
        line-height: 50px;
        border-radius: 25px;
    }

    input[type=checkbox].check-box {
        height: 22px;
        min-width: 22px;
        line-height: 22px;
        display: inline-block;
        margin: 0 2px;
        vertical-align: middle;
    }

    .check-group {
        height: 36px;
        line-height: 36px;
        display: inline-block;
        vertical-align: middle;
    }
</style>
<div id="blockAddContext">
    <c:url var="url" value="/book/blockUpdate"/>
    <form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${block.id}"/>
        <input type="hidden" name="updateType" value="ONCE"/>
        <input type="hidden" name="repeatType" value="NONE"/>
        <input type="hidden" name="startTimeStamp" value="${blockVO.startTimeStamp}"/>
        <div id="baseStep" class="baseStep step active">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.room"/></label>
                <div class="col-lg-5">
                    ${block.room.name}
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label">
                    <spring:message code="label.type"/></label>
                <div class="col-lg-5">
                    <spring:message code="label.block.type.${block.type}"/>
                </div>
            </div>
            <c:choose>
                <c:when test="${isAllDay}">
                    <div id="startDateTimeField" class="form-group">
                        <label class="col-lg-4 control-label"><spring:message code="label.start.date.time"/></label>
                        <div class="col-lg-5">
                            <div class="input-group date form_time">
                                <input id="startAllDateTime" class="form-control" value='<fmt:formatDate value="${block.startDate}" pattern="yyyy-MM-dd"/>' readonly>
                                <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                            </div>
                        </div>
                    </div>
                    <div id="endDateTimeField" class="form-group">
                        <label class="col-lg-4 control-label"><spring:message code="label.end.date.time"/></label>
                        <div class="col-lg-5">
                            <div class="input-group date form_time">
                                <input id="endAllDateTime" class="form-control" value='<fmt:formatDate value="${block.endDate}" pattern="yyyy-MM-dd"/>' readonly>
                                <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="startDateTimeField" class="form-group">
                        <label class="col-lg-4 control-label"><spring:message code="label.start.date.time"/></label>
                        <div class="col-lg-5">
                            <div class="input-group date form_time">
                                <input id="startDateTime" name="startDateTime" class="form-control" value='<fmt:formatDate value="${block.startDate}" pattern="yyyy-MM-dd HH:mm"/>' readonly>
                                <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                            </div>
                        </div>
                    </div>
                    <div id="endDateTimeField" class="form-group">
                        <label class="col-lg-4 control-label"><spring:message code="label.end.date.time"/></label>
                        <div class="col-lg-5">
                            <div class="input-group date form_time">
                                <input id="endDateTime" name="endDateTime" class="form-control" value='<fmt:formatDate value="${block.endDate}" pattern="yyyy-MM-dd HH:mm"/>' readonly>
                                <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class='form-group'>
                <label for="remarks" class="col-lg-4 control-label">
                    <spring:message code="label.remarks"/></label>
                <div class="col-lg-5">
                    <textarea id="remarks" name="remarks">${block.remarks}</textarea>
                </div>
            </div>
        </div>
        <div id="confirmStep" class="confirmStep step"></div>

        <div class="modal-footer">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                    <button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
                    <button type="button" class="btn btn-default dialogSubmitBtn" data-skip-validate="true" id="submit">
                        <spring:message code="label.book.status.save"/>
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        // 註冊對話框的提交事件
        $('#previous', Dialog.getContext()).hide();
        $('#submit', Dialog.getContext()).hide();

        $('#next', Dialog.getContext()).click(function () {
            var form = $('form', Dialog.getContext());
            var currentStep = $('.step.active', form);
            var nextStep = currentStep.next('.step');

            <c:if test="${isAllDay}">
            var html = [];
            form.find('input[name="startDateTime"]').remove();
            form.find('input[name="endDateTime"]').remove();
            html.push(String.format('<input type="hidden" name="startDateTime" value="{0}">', $('#startAllDateTime').val() + " 00:00"));
            html.push(String.format('<input type="hidden" name="endDateTime" value="{0}">', $('#endAllDateTime').val() + " 23:59"));
            $(html.join('')).appendTo(form);
            </c:if>
            if (currentStep.hasClass('baseStep')) {
                $.post('<c:url value="/book/blockTherapistUpdateConfirm"/>', $('form', Dialog.getContext()).serialize(), function (res) {
                    nextStep.html(res);
                    var errorMessage = $('#error', Dialog.getContext()).val();
                    if (errorMessage) {
                        Dialog.alert({
                            title: "Error",
                            message: errorMessage
                        });
                    } else {
                        currentStep.removeClass('active');
                        nextStep.addClass('active');
                        $('#submit', Dialog.getContext()).show();
                        $('#next', Dialog.getContext()).hide();
                        $('#previous', Dialog.getContext()).show();
                    }
                });
            }
        });

        $('#previous', Dialog.getContext()).click(function () {
            $('#submit', Dialog.getContext()).hide();
            var currentStep = $('.step.active', Dialog.getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                $(this).hide();
            }
            $('#next', Dialog.getContext()).show();
        });

        <c:choose>
        <c:when test="${isAllDay}">
        $('#startAllDateTime, #endAllDateTime', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            datepicker: true,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd"/>'
        });
        </c:when>
        <c:otherwise>
        $('#startDateTime, #endDateTime', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d H:i',
            timepicker: true,
            datepicker: true,
            step: 5,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd HH:mm"/>'
        });
        </c:otherwise>
        </c:choose>


    });
</script>
