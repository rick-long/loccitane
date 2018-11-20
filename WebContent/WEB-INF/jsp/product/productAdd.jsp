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
<form:form modelAttribute="productAddVO" id="masterForm" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action=''>
    <div id="baseStep" class="baseStep step active">
        <fieldset>
            <legend><spring:message code="label.product.details"/></legend>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.brand"/>*</label>
                <div class="col-lg-5">
                    <form:select class="selectpicker form-control" path="brandId" id="brandId">
                        <c:forEach var="brand" items="${brandList }">
                            <form:option value="${brand.id }">${brand.name }</form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="form-group categoryTreeMenu">
				<label class="col-lg-4 control-label"><spring:message code="label.category.tree"/>*</label>
			    <div class="col-lg-5">
			       	<div class="select-category" data-selectable="category" data-root-id="3"></div>
				</div>
		    </div>
		    <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
                <div class="col-lg-5">
                    <form:input path="name" id="name" class="form-control"/>
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
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');
            var form = $('form', Dialog.getContext());
            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = form.data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }
                var categoryId=$('#categoryId', Dialog.getContext()).val();
               
                if (categoryId =='') {
                    Dialog.alert('Please select category!');
                    return;
                }
                $.post('<c:url value="/product/addPdkeyAndPokey"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    $('#previous', Dialog.getContext()).show();
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                });

            } else if (currentStep.hasClass('poStep')) {
                // 验证treatment
                var productionsInputs = $('#productOptionAddTable', Dialog.getContext()).find('tr');
                if (productionsInputs.length === 0) {
                    Dialog.alert('<spring:message code="label.product.please.add.product.option"/> ');
                    return;
                }

                // 删除之前的参数
                form.find('input[name^="poItemList"]').remove();
                var html = [], itemIndex = 0, keyIndex = 0;

                // 添加input数据
                productionsInputs.each(function () {
                    var $this = $(this);
                    var inputs = $this.find('input.pokInput');
                    keyIndex = 0; // 每次从0开始
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
                $.post('<c:url value="/product/productConfirm"/>', form.serialize(), function (res) {
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
            $.post('<c:url value="/product/add"/>', form.serialize(), function (json) {
                if (json['statusCode'] === 200) {
                    Dialog.get().close();
                    Dialog.success(json.message, function () {
                        $('a.search-btn', getContext()).trigger('click'); // 触发重新查询事件
                    });
                }else if (json['statusCode'] === 300) {
                    Dialog.alert(json.message);
                }
            }, 'json');
        });
        
        $('.select-category', Dialog.getContext()).selectCategory({});
    });
</script>