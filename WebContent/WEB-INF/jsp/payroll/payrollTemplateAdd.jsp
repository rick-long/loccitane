<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div>
    <form method="post" class="form-horizontal" enctype="multipart/form-data">
        <div id="baseStep" class="baseStep step active">
            <div class="form-group">
                <label for="name" class="col-lg-4 control-label"><spring:message code="label.name"/></label>
                <div class="col-lg-5">
                    <input type="text" id="name" name="name" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="description" class="col-lg-4 control-label">
                    <spring:message code="label.description"/></label>
                <div class="col-lg-5">
                    <textarea id="description" name="description" class="form-control"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="content" class="col-lg-4 control-label"><spring:message code="label.content"/></label>
                <div class="col-lg-5">
                    <textarea id="content" name="content"></textarea>
                </div>
            </div>

            <div class="row new-row-width">
                <label><spring:message code="label.payroll.attribute.key"/></label>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th><spring:message code="label.reference"/></th>
                        <th><spring:message code="label.name"/></th>
                        <th><spring:message code="label.description"/></th>
                        <th><spring:message code="label.displayorder"/></th>
                        <th>
                            <button type="button" id="addRow" class="btn btn-default"><spring:message code="label.addRow"/></button>
                        </th>
                    </tr>
                    </thead>
                    <tbody id="body">
                    <tr>
                        <td>
                            <input type="text" class="form-control reference"/>
                        </td>
                        <td>
                            <input type="text" class="form-control name"/>
                        </td>
                        <td>
                            <input type="text" class="form-control description"/>
                        </td>
                        <td>
                            <input type="text" class="form-control displayOrder"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-warning removePOBtn hide"><spring:message code="label.remove"/></button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="confirmStep" class="confirmStep step"></div>
    </form>
</div>
<div class="modal-footer">
    <div class="bootstrap-dialog-footer">
        <div class="bootstrap-dialog-footer-buttons">
            <button class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
            <button class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
            <button class="btn btn-default" id="submit"><spring:message code="label.button.save"/></button>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        var form = $('form', Dialog.getContext());

        // 註冊對話框的提交事件
        var previousBtn = $('#previous', Dialog.getContext());
        var nextBtn = $('#next', Dialog.getContext());
        var submitBtn = $('#submit', Dialog.getContext());
        previousBtn.hide();
        submitBtn.hide();

        /*// 添加一行
        $('#addRow', Dialog.getContext()).click(function () {
            var html = $('#body', Dialog.getContext().find('tr:first').clone()); // 克隆第一行的数据
            console.log(html);
            html.find('button.removePOBtn').removeClass('hide').click(function () {
                $(this).parents('tr').remove();
            });
            html.find('.form-control').val('');
            html.appendTo($('#body', Dialog.getContext()));
        });*/
        // 添加一行
        $('#addRow', Dialog.getContext()).click(function () {
            var html = $($('#body', Dialog.getContext()).find('tr:first').clone()); // 克隆第一行的数据
            html.find('button.removePOBtn').removeClass('hide').click(function () {
                $(this).parents('tr').remove();
            });
            html.find('.form-control').val('');
            html.appendTo($('#body', Dialog.getContext()));
        });

        nextBtn.click(function () {
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');

            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = form.data('bootstrapValidator');
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }

                // 验证 payroll attribute key
                var inputFlag = $('input.reference', Dialog.getContext()).filter(function () {
                    return !this.value;
                }).length;
                if (inputFlag > 0) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="lable.bonus.please.input.reference"/> '
                    });
                    return;
                }

                inputFlag = $('input.name', Dialog.getContext()).filter(function () {
                    return !this.value;
                }).length;

                if (inputFlag > 0) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="lable.bonus.please.input.name"/> '
                    });
                    return;
                }
                // reference 重复判断
                var inputValues = [];
                var referenceInput = $('input.reference', Dialog.getContext());
                inputFlag = false;
                referenceInput.each(function (index, item) {
                    var val = $(item).val();
                    if ($.inArray(val, inputValues) > -1) {
                        inputFlag = true;
                        return false;
                    } else {
                        inputValues.push(val);
                    }
                });
                if (inputFlag) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="lable.bonus.duplicate.input.reference"/> '
                    });
                    return;
                }

                form.find('input[name^="payrollAttributeKeyVO"]').remove();
                // 添加参数
                var html = [];
                var trs = $('#body', Dialog.getContext()).find("tr");
                trs.each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="payrollAttributeKeyVO[{0}].reference" value="{1}"/>', index, $item.find('.reference').val()));
                    html.push(String.format('<input type="hidden" name="payrollAttributeKeyVO[{0}].name" value="{1}"/>', index, $item.find('.name').val()));
                    html.push(String.format('<input type="hidden" name="payrollAttributeKeyVO[{0}].description" value="{1}"/>', index, $item.find('.description').val()));
                    html.push(String.format('<input type="hidden" name="payrollAttributeKeyVO[{0}].displayOrder" value="{1}"/>', index, $item.find('.displayOrder').val()));
                });

                $(html.join('')).appendTo(form);
                var formData = new FormData(form[0]);
                $.ajax({
                    url: '<c:url value="/payroll/payrollTemplateConfirm"/>',
                    type: 'POST',
                    data: formData,
                    async: false,
                    success: function (res) {
                        nextStep.html(res);
                        currentStep.removeClass('active');
                        nextStep.addClass('active');
                        submitBtn.show();
                        nextBtn.hide();
                        previousBtn.show();
                    },
                    cache: false,
                    contentType: false,
                    processData: false
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

        submitBtn.click(function () {
            submitBtn.button('loading');
            var formData = new FormData(form[0]);
            $.ajax({
                url: '<c:url value="/payroll/payrollTemplateSave"/>',
                type: 'POST',
                data: formData,
                async: false,
                success: function (res) {
                    if (res['statusCode'] === 200) {
                        Dialog.get().close();
                        /* $('a.search-btn', Dialog.getParentContext()).trigger('click'); // 触发重新查询事件 */
                    }
                },
                complete: function() {
                    submitBtn.button('reset');
                },
                cache: false,
                contentType: false,
                processData: false
            });
        });

        $('form', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
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
