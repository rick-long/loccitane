<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/bonus/bonusRuleSave"/>
<form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
	<input type="hidden" name="id" value="${bonusRule.id}">
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label for="bonusTemplateId" class="col-lg-4 control-label">
                <spring:message code="label.template"/></label>
            <div class="col-lg-5">
             	${bonusRule.bonusTemplate.name}
                <input type="hidden" name="bonusTemplateId" value="${bonusRule.bonusTemplate.id}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.description"/></label>
            <div class="col-lg-5">
                <textarea id="description" name="description" class="form-control">${bonusRule.description}</textarea>
            </div>
        </div>
    </div>
    <div id="bonusRuleAddAttributeStep" class="bonusRuleAddAttributeStep step"></div>
    <div id="confirmStep" class="confirmStep step"></div>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/> </button>
                <button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/> </button>
                <button type="button" class="btn btn-default dialogSubmitBtn" data-skip-validate="true" id="submit"><spring:message code="label.book.status.save"/> </button>
            </div>
        </div>
    </div>
</form>


<script type="text/javascript">
    $(document).ready(function () {
        
        var form = $('form', Dialog.getContext());
        var html;

        // 註冊對話框的提交事件
        var previousBtn = $('#previous', Dialog.getContext());
        var nextBtn = $('#next', Dialog.getContext());
        var submitBtn = $('#submit', Dialog.getContext());
        previousBtn.hide();
        submitBtn.hide();
		
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

                // 添加參數

                $(html.join('')).appendTo(form);
                $.post('<c:url value="/bonus/bonusRuleAddAttribute"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    previousBtn.show();
                });
            } else if (currentStep.hasClass('bonusRuleAddAttributeStep')) {
                form.find('input[name^="bonusAttributeVOs"]').remove();
                html = [];
                var attributeKeys = $('input.attributeKeys', Dialog.getContext()).filter(function () {
                    return this.value;
                });
                attributeKeys.each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="bonusAttributeVOs[{0}].bonusAttributeKeyId" value="{1}"/>', index, $item.data('attribute-key-id')));
                    html.push(String.format('<input type="hidden" name="bonusAttributeVOs[{0}].reference" value="{1}"/>', index, $item.data('reference')));
                    html.push(String.format('<input type="hidden" name="bonusAttributeVOs[{0}].name" value="{1}"/>', index, $item.data('name')));
                    html.push(String.format('<input type="hidden" name="bonusAttributeVOs[{0}].description" value="{1}"/>', index, $item.data('description')));
                    html.push(String.format('<input type="hidden" name="bonusAttributeVOs[{0}].value" value="{1}"/>', index, $item.val()));
                });
                $(html.join('')).appendTo(form);

                $.post('<c:url value="/bonus/bonusRuleConfirm"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    submitBtn.show();
                    nextBtn.hide();
                    previousBtn.show();
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
                bonusTemplateId: {
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
