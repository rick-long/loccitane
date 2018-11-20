<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:url var="url" value="/book/blockAdd"/>
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
    <form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
        <input type="hidden" name="shopId" value="${blockVO.shopId}" />
        <div id="baseStep" class="baseStep step active">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
                <div class="col-lg-5">
                    <select id="therapistId" name="therapistId" class="selectpicker form-control">
                        <c:forEach items="${therapistList}" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq blockVO.therapistId}">selected</c:if>>${item.displayName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="type" class="col-lg-4 control-label">
                    <spring:message code="label.type"/></label>
                <div class="col-lg-5">
                    <select id="type" name="type" class="selectpicker form-control">
                        <option value="BREAK"><spring:message code="label.block.type.BREAK"/></option>
                        <option value="ANNUAL_LEAVE"><spring:message code="label.block.type.ANNUAL_LEAVE"/></option>
                        <option value="NO_PAID_LEAVE"><spring:message code="label.block.type.NO_PAID_LEAVE"/></option>
                        <option value="SICK_LEAVE"><spring:message code="label.block.type.SICK_LEAVE"/></option>
                        <option value="MATERNITY_LEAVE"><spring:message code="label.block.type.MATERNITY_LEAVE"/></option>
                        <option value="DAY_OFF"><spring:message code="label.block.type.DAY_OFF"/></option>
                        <option value="LUNCH"><spring:message code="label.block.type.LUNCH"/></option>
                        <option value="BEFORE_WORK_DAY"><spring:message code="label.block.type.BEFORE_WORK_DAY"/></option>
                        <option value="AFTER_WORK_DAY"><spring:message code="label.block.type.AFTER_WORK_DAY"/></option>
                        <option value="RESERVED_FOR_BOOKING"><spring:message code="label.block.type.RESERVED_FOR_BOOKING"/></option>
                        <option value="OVERTIME"><spring:message code="label.block.type.OVERTIME"/></option>
                        <option value="SH_LEAVE"><spring:message code="label.block.type.SH_LEAVE"/></option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-4 control-label">
                    <spring:message code="label.repeat"/></label>
                <div class="col-lg-5">
                    <select id="repeatType" name="repeatType" class="selectpicker form-control">
                        <option value="NONE"><spring:message code="label.block.repeat.NONE"/></option>
                        <option value="EVERY_DAY"><spring:message code="label.block.repeat.EVERY_DAY"/></option>
                        <option value="EVERY_WEEK"><spring:message code="label.block.repeat.EVERY_WEEK"/></option>
                        <option value="EVERY_MONTH"><spring:message code="label.block.repeat.EVERY_MONTH"/></option>
                    </select>
                </div>
            </div>
            <div id="noneRepeatGroup">
                <div id="allDayField" class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.all.day"/></label>
                    <div class="col-lg-5">
                        <div class="check-group">
                            <input id="allDayCheckBox" type="checkbox" class="check-box" checked>
                        </div>
                    </div>
                </div>
                <div id="startAllDayField" class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.start.date"/></label>
                    <div class="col-lg-5">
                        <div class="input-group date form_time">
                            <input id="startAllDay" class="form-control" value='' readonly>
                            <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>
                <div id="endAllDayField" class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.end.date"/></label>
                    <div class="col-lg-5">
                        <div class="input-group date form_time">
                            <input id="endAllDay" class="form-control" value='' readonly>
                            <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>
                <div id="startDateTimeField" class="form-group hide">
                    <label class="col-lg-4 control-label"><spring:message code="label.start.date.time"/></label>
                    <div class="col-lg-5">
                        <div class="input-group date form_time">
                            <input id="startDateTime" class="form-control" value='' readonly>
                            <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>
                <div id="endDateTimeField" class="form-group hide">
                    <label class="col-lg-4 control-label"><spring:message code="label.end.date.time"/></label>
                    <div class="col-lg-5">
                        <div class="input-group date form_time">
                            <input id="endDateTime" class="form-control" value='' readonly>
                            <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                        </div>
                    </div>
                </div>
            </div>
            <div id="startDateField" class="form-group hide">
                <label class="col-lg-4 control-label"><spring:message code="label.repeat.start.date"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatStartDate" name="repeatStartDate" class="form-control" value='' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="endDateField" class="form-group hide">
                <label class="col-lg-4 control-label"><spring:message code="label.repeat.end.date"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatEndDate" name="repeatEndDate" class="form-control" value='' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="startTimeField" class="form-group hide">
                <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatStartTime" name="repeatStartTime" class="form-control" value='' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="endTimeField" class="form-group hide">
                <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="repeatEndTime" name="repeatEndTime" class="form-control" value='' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="monthsField" class="form-group hide">
                <label for="type" class="col-lg-4 control-label">
                    <spring:message code="label.months"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <div class="check-group">
                                    <input class="select-all check-box" type="checkbox"/>
                                    <spring:message code="label.select.all"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach begin="1" end="12" var="item">
                                    <span class="month-item" data-value="${item}">${item}</span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="weeksField" class="form-group hide">
                <label for="type" class="col-lg-4 control-label">
                    <spring:message code="label.weeks"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <div class="check-group">
                                    <input class="select-all check-box" type="checkbox"/>
                                    <spring:message code="label.select.all"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach begin="1" end="7" var="item">
                                    <span class="week-item" data-value="${item}"><spring:message code="label.week.${item}"/></span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="daysField" class="form-group hide">
                <label for="type" class="col-lg-4 control-label">
                    <spring:message code="label.days"/></label>
                <div class="col-lg-5">
                    <table class="time-panel">
                        <tr>
                            <td>
                                <div class="check-group">
                                    <input class="select-all check-box" type="checkbox"/>
                                    <spring:message code="label.select.all"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach begin="1" end="31" var="item">
                                    <span class="day-item" data-value="${item}">${item}</span>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class='form-group'>
                <label for="remarks" class="col-lg-4 control-label">
                    <spring:message code="label.remarks"/></label>
                <div class="col-lg-5">
                    <textarea id="remarks" name="remarks"></textarea>
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
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');

            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = $('form', Dialog.getContext()).data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }

                // 生成参数
                var html = [];
                var repeatType = $("#repeatType", Dialog.getContext()).val();
                if(repeatType == 'NONE') {
                    $('form', Dialog.getContext()).find('input[name="startDateTime"]').remove();
                    $('form', Dialog.getContext()).find('input[name="endDateTime"]').remove();
                    if($("#allDayCheckBox", Dialog.getContext()).is(":checked")) {
                        html.push(String.format('<input type="hidden" name="startDateTime" value="{0}">', $('#startAllDay').val() + " 00:00"));
                        html.push(String.format('<input type="hidden" name="endDateTime" value="{0}">', $('#endAllDay').val() + " 23:59"));
                    } else {
                        html.push(String.format('<input type="hidden" name="startDateTime" value="{0}">', $('#startDateTime').val()));
                        html.push(String.format('<input type="hidden" name="endDateTime" value="{0}">', $('#endDateTime').val()));
                    }
                } else {
                    $('form', Dialog.getContext()).find('input[name="days"]').remove();
                    $('form', Dialog.getContext()).find('input[name="weeks"]').remove();
                    $('form', Dialog.getContext()).find('input[name="months"]').remove();

                    $('.day-item.active', Dialog.getContext()).each(function(){
                        html.push(String.format('<input type="hidden" name="days" value="{0}">',  $(this).data('value')));
                    });
                    $('.week-item.active', Dialog.getContext()).each(function(){
                        html.push(String.format('<input type="hidden" name="weeks" value="{0}">',  $(this).data('value')));
                    });
                    $('.month-item.active', Dialog.getContext()).each(function(){
                        html.push(String.format('<input type="hidden" name="months" value="{0}">',  $(this).data('value')));
                    });
                }

                $(html.join('')).appendTo($('form', Dialog.getContext()));
                $.post('<c:url value="/book/blockTherapistAddConfirm"/>', $('form', Dialog.getContext()).serialize(), function (res) {
                    nextStep.html(res);
                    var errorMessage = $('#error', Dialog.getContext()).val();
                    if (errorMessage) {
                        Dialog.alert({
                            title: "<spring:message code="lable.error"/> ",
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

        $("#type", Dialog.getContext()).change(function () {
            var repeatType = $('#repeatType', Dialog.getContext());
            if ($(this).val() != 'BREAK') {
                repeatType.val('NONE').trigger('change');
                repeatType.parents('.form-group').addClass('hide');
            } else {
                repeatType.parents('.form-group').removeClass('hide');
            }
        });

        $('#allDayCheckBox', Dialog.getContext()).click(function () {
            var $this = $(this);
            if ($this.is(":checked")) {
                $('#startDateTimeField, #endDateTimeField', Dialog.getContext()).addClass('hide');
                $('#startAllDayField, #endAllDayField', Dialog.getContext()).removeClass('hide');
            } else {
                $('#startDateTimeField, #endDateTimeField', Dialog.getContext()).removeClass('hide');
                $('#startAllDayField, #endAllDayField', Dialog.getContext()).addClass('hide');
            }
        });

        $('#startDateTime, #endDateTime', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d H:i',
            timepicker: true,
            datepicker: true,
            step: 5,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd HH:mm"/>'
        });

        $('#startAllDay, #endAllDay', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            datepicker: true,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd"/>'
        });

        $('#repeatStartDate, #repeatEndDate', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            datepicker: true,
            step: 5,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd"/>'
        });

        $('#repeatStartTime, #repeatEndTime', Dialog.getContext()).datetimepicker({
            format: 'H:i',
            timepicker: true,
            datepicker: false,
            step: 5,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="HH:mm"/>'
        });

        $('form', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                therapistId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });

        $('.day-item,.week-item,.month-item', Dialog.getContext()).click(function () {
            var $this = $(this);
            if ($this.hasClass('active')) {
                $this.removeClass('active');
            } else {
                $this.addClass('active');
            }
        });

        $('#repeatType', Dialog.getContext()).change(function () {
            $('.day-item,.week-item,.month-item', Dialog.getContext()).removeClass('active'); // 刪除已經選擇的
            var val = $(this).val();
            var daysField = $("#daysField", Dialog.getContext());
            var weeksField = $("#weeksField", Dialog.getContext());
            var monthsField = $("#monthsField", Dialog.getContext());
            var startTimeField = $("#startTimeField", Dialog.getContext());
            var endTimeField = $("#endTimeField", Dialog.getContext());
            var startDateField = $("#startDateField", Dialog.getContext());
            var endDateField = $("#endDateField", Dialog.getContext());
            var startDateTimeField = $("#startDateTimeField", Dialog.getContext());
            var endDateTimeField = $("#endDateTimeField", Dialog.getContext());
            var noneRepeatGroup = $("#noneRepeatGroup", Dialog.getContext());
            daysField.addClass("hide");
            weeksField.addClass("hide");
            monthsField.addClass("hide");
            startTimeField.removeClass('hide');
            endTimeField.removeClass('hide');
            startDateField.removeClass('hide');
            endDateField.removeClass('hide');
            noneRepeatGroup.addClass('hide');
            switch (val) {
                case 'EVERY_DAY':
                    daysField.removeClass("hide");
                    break;
                case 'EVERY_WEEK':
                    weeksField.removeClass("hide");
                    break;
                case 'EVERY_MONTH':
                    daysField.removeClass("hide");
                    monthsField.removeClass("hide");
                    break;
                case 'NONE':
                    startTimeField.addClass('hide');
                    endTimeField.addClass('hide');
                    startDateField.addClass('hide');
                    endDateField.addClass('hide');
                    noneRepeatGroup.removeClass('hide');
                    break;
                default:
                    break;
            }
        });

        $('.select-all', Dialog.getContext()).click(function () {
            var $this = $(this);
            if ($this.is(':checked')) {
                $this.parents('table').find('span').addClass('active');
            } else {
                $this.parents('table').find('span').removeClass('active');
            }
        });
    });
</script>
