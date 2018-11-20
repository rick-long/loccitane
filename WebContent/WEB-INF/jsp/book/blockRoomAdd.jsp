<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:url var="url" value="/book/blockAdd"/>
<div id="blockRoomAddContext">
    <form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
        <input type="hidden" name="shopId" value="${shop.id}" />
        <input type="hidden" name="repeatType" value="NONE" />
        <div id="baseStep" class="baseStep step active">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.room"/></label>
                <div class="col-lg-5">
                    <select id="roomId" name="roomId" class="selectpicker form-control">
                        <c:forEach items="${shop.rooms}" var="item">
                            <option value="${item.id}" <c:if test="${item.id eq blockVO.roomId}">selected</c:if>>${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
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
                <label class="col-lg-4 control-label"><spring:message code="label.start.time"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="startDateTime" class="form-control" value='<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd HH:mm"/>' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div id="endDateTimeField" class="form-group hide">
                <label class="col-lg-4 control-label"><spring:message code="label.end.time"/></label>
                <div class="col-lg-5">
                    <div class="input-group date form_time">
                        <input id="endDateTime" class="form-control" value='<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd HH:mm"/>' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="type" class="col-lg-4 control-label">
                    <spring:message code="label.type"/></label>
                <div class="col-lg-5">
                    <select id="type" name="type" class="selectpicker form-control">
                        <option value="MAINTENANCE"><spring:message code="label.block.type.MAINTENANCE"/></option>
                        <option value="TRAINING"><spring:message code="label.block.type.TRAINING"/></option>
                    </select>
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
        $('#previous', Dialog.getContext()).hide();
        $('#submit', Dialog.getContext()).hide();

        $('#next', Dialog.getContext()).click(function () {
            var form = $('form', Dialog.getContext());
            var currentStep = $('.step.active', form);
            var nextStep = currentStep.next('.step');

            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = form.data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }

                form.find('input[name="startDateTime"]').remove();
                form.find('input[name="endDateTime"]').remove();
                var html = [];
                if($("#allDayCheckBox", form).is(":checked")) {
                    html.push(String.format('<input type="hidden" name="startDateTime" value="{0}">', $('#startAllDay', form).val() + " 00:00"));
                    html.push(String.format('<input type="hidden" name="endDateTime" value="{0}">', $('#endAllDay', form).val() + " 23:59"));
                } else {
                    html.push(String.format('<input type="hidden" name="startDateTime" value="{0}">', $('#startDateTime', form).val()));
                    html.push(String.format('<input type="hidden" name="endDateTime" value="{0}">', $('#endDateTime', form).val()));
                }
                $(html.join('')).appendTo(form);
                $.post('<c:url value="/book/blockRoomAddConfirm"/>', form.serialize(), function (res) {
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

        $('#startAllDay, #endAllDay', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            datepicker: true,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd"/>'
        });

        $('#startDateTime, #endDateTime', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d H:i',
            timepicker: true,
            datepicker: true,
            step: 5,
            value: '<fmt:formatDate value="${blockVO.startDateTime}" pattern="yyyy-MM-dd HH:mm"/>'
        });

        $('form', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                roomId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                startDateTime: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                endDateTime: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });
    });
</script>
