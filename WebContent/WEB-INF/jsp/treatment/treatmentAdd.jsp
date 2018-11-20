<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style type="text/css">
    .step {
        display: none;
    }

    .step.active {
        display: block;
    }
</style>
<form:form modelAttribute="productAddVO" id="masterForm" method="post" class="form-horizontal" action=''>
    <form:hidden path="brandId"/>
    <div id="baseStep" class="baseStep step active">
        <fieldset>
            <legend><spring:message code="label.treatment.details"/></legend>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
                <div class="col-lg-5">
                    <form:input path="name" id="name" class="form-control"/>
                </div>
            </div>
		   	<div class="form-group">
		        <label class="col-lg-4 control-label"> <spring:message code="label.show.online"/></label>
		        <div class="col-lg-5">
		            <form:select class="selectpicker form-control" path="showOnline" id="showOnline">
		                <form:option value="false"><spring:message code="label.option.no"/></form:option>
		                <form:option value="true"><spring:message code="label.option.yes"/></form:option>
		            </form:select>
		        </div>
		    </div>
                <%-- <div class="form-group">
                    <label class="col-lg-4 control-label"><spring:message code="label.brand"/></label>
                    <div class="col-lg-5">
                        <form:select class="selectpicker form-control" path="brandId" id="brandId">
                            <c:forEach var="brand" items="${brandList }">
                                <form:option value="${brand.id }">${brand.name }</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div> --%>
            <div class="form-group categoryTreeMenu">
                <label class="col-lg-4 control-label"><spring:message code="label.category.tree"/>*</label>
                <div class="col-lg-5">
                    <div class="select-category" data-selectable="category" data-root-id="2"></div>
                </div>
            </div>
        </fieldset>
    </div>
    <div id="poStep" class='poStep step'></div>
    <div id="confirmStep" class="confirmStep step"></div>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                <button class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
                <button class="btn btn-default" id="submit"><spring:message code="label.button.save"/></button>
            </div>
        </div>
    </div>

</form:form>

<script type="text/javascript">
    $(function () {
        $('#masterForm', Dialog.getContext()).bootstrapValidator({
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

        // 註冊對話框的提交事件
        $('#previous', Dialog.getContext()).hide();
        $('#submit', Dialog.getContext()).hide();

        $('#next', Dialog.getContext()).click(function () {
            var form = $('form', Dialog.getContext());
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

                var categoryId=$('#categoryId',Dialog.getContext()).val();
                if (categoryId =='') {
                    Dialog.alert('<spring:message code="label.treatment.category.add.msg"/>');
                    return;
                }
                
                $.post('<c:url value="/treatment/addPdkeyAndPokey"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    $('#previous', Dialog.getContext()).show();
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                });

            } else if (currentStep.hasClass('poStep')) {
                // 验证treatment
                var productionsInputs = $('#productOptionAddTable', Dialog.getContext()).find('tr');
                if (productionsInputs.length === 0) {
                    Dialog.alert('<spring:message code="label.treatment.add.msg"/>');
                    return;
                }

                // 删除之前的参�?
                form.find('input[name^="poItemList"]').remove();
                var html = [], itemIndex = 0, keyIndex = 0;

                // 添加input数据
                productionsInputs.each(function () {
                    var $this = $(this);
                    var inputs = $this.find('input.pokInput');
                    keyIndex = 0; // 每次�?开�?
                    inputs.each(function () {
                        var $input = $(this);
                        if ($input) {
                            // 添加参数
                            // key
                            html.push('<input type="hidden" name="poItemList[' + itemIndex + '].poValues[' + keyIndex + '].key" value="' + $input.data('pok-id') + '"/>');
                            // value
                            html.push('<input type="hidden" name="poItemList[' + itemIndex + '].poValues[' + keyIndex + '].value" value="' + $input.val() + '"/>');
                            keyIndex++;
                        }
                    });
                    itemIndex++;
                });

                $(html.join('')).appendTo(form);
                $.post('<c:url value="/treatment/treatmentConfirm"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    $('#submit', Dialog.getContext()).show();
                    $('#next', Dialog.getContext()).hide();
                });
            }
        });

        $('#previous', Dialog.getContext()).click(function () {
            var currentStep = $('.step.active', Dialog.getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                $('#previous', Dialog.getContext()).hide();
            } else if (previousStep.hasClass('poStep')) {
                $('#next', Dialog.getContext()).show();
                $('#submit', Dialog.getContext()).hide();
            }
        });

        $('#submit', Dialog.getContext()).click(function () {
            var form = $('form', Dialog.getContext());
            $.post('<c:url value="/treatment/add"/>', form.serialize(), function (res) {
                if (res['statusCode'] === 200) {
                    Dialog.get().close();
                    Dialog.success('<spring:message code="label.add.successfully"/>', function () {
                        $('a.search-btn', getContext()).trigger('click'); // 触发重新查询事件
                    });
                }
            });
        });

        $('.select-category', Dialog.getContext()).selectCategory({});
    });
</script>