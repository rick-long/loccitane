<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:url var="url" value="/book/bookItemEdit"/>
<form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <input type="hidden" name="bookItemId" value="${bookItemEditVO.bookItemId}"/>
    <input type="hidden" id="appointmentTimeStamp" name="appointmentTimeStamp" value="${bookItemEditVO.appointmentTimeStamp}"/>
    <input type="hidden" name="roomId" value="${bookItemEditVO.roomId}"/>
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.appointment.time"/></label>
            <div class="col-lg-5">
                <div class="input-group">
                    <fmt:formatDate value="${bookItemEditVO.appointmentTime}" pattern="yyyy-MM-dd HH:mm"/>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.room"/></label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    ${bookItemEditVO.room.name}
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.treatment"/></label>
            <div class="col-lg-5">
                ${bookItemEditVO.bookItem.productOption.label3}
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.therapist"/></label>
            <div class="col-lg-5">
                <c:choose>
                    <c:when test="${bookItemEditVO.availableTherapists.size() gt 0}">
                        <select id="therapistId" name="therapistId" class="selectpicker form-control">
                            <c:forEach items="${bookItemEditVO.availableTherapists}" var="item">
                                <option value="${item.id}"
                                        <c:if test="${item.id eq bookItemEditVO.therapist.id}">selected</c:if>>${item.fullName}</option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise><spring:message code="label.book.no.therapists.available"/> </c:otherwise>
                </c:choose>
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

<script type="text/javascript">
    $(document).ready(function () {
        
        var form = $('form', Dialog.getContext());


        // 註冊對話框的提交事件
        var previousBtn = $('#previous', Dialog.getContext());
        var nextBtn = $('#next', Dialog.getContext());
        var submitBtn = $('#submit', Dialog.getContext());
        previousBtn.hide();
        submitBtn.hide();
        <c:if test="${bookItemEditVO.availableTherapists.size() eq 0}">
        nextBtn.hide();
        </c:if>

        nextBtn.click(function () {
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');

            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = form.data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }
                $.post('<c:url value="/book/bookItemEditConfirm"/>', form.serialize(), function (res) {
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
                        submitBtn.show();
                        nextBtn.hide();
                        previousBtn.show();
                    }
                });
            }
        });

        previousBtn.click(function () {
            submitBtn.hide();
            var currentStep = $('.step.active', Dialog.getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                previousBtn.hide();
                nextBtn.show();
            } else {
                nextBtn.show();
            }
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
    });
</script>
