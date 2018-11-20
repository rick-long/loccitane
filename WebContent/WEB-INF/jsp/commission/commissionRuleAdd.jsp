<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/commission/commissionRuleSave"/>
<form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label for="commissionTemplateId" class="col-lg-4 control-label">
                <spring:message code="label.template"/>*</label>
            <div class="col-lg-5">
                <select id="commissionTemplateId" name="commissionTemplateId" class="selectpicker form-control">
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.description"/>*</label>
            <div class="col-lg-5">
                <textarea id="description" name="description" class="form-control"></textarea>
            </div>
        </div>
        <%-- <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
            <div class="col-lg-5">
                <ul class="ztree categoryProductTree" style="width:400px; overflow:auto;"></ul>
            </div>
        </div> --%>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
            <div class="col-lg-5">
                <div class="input-group">
                    <input class="form-control" placeholder="Search Category"/>
                    <span id="searchCategoryBtn" class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
                <select multiple="multiple" id="categoryMultiSelect" class="selectpicker form-control">
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.product"/></label>
            <div class="col-lg-5">
                <div class="input-group">
                    <input class="form-control" placeholder="Search Product"/>
                    <span id="searchProductBtn" class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
                <select multiple="multiple" id="productMultiSelect" class="selectpicker form-control">
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.staff.group"/>*</label>
            <div class="col-lg-5">
                <div class="input-group">
                    <input class="form-control" placeholder="Search Staff Group"/>
                    <span id="searchUserGroupBtn" class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                    <input type="hidden" id="userGroupType" value="${userGroupType}">
                    <input type="hidden" id="userGroupModule" value="${userGroupModule}">
                </div>
                <select multiple="multiple" id="userGroupMultiSelect" class="selectpicker form-control">
                </select>
            </div>
        </div>

       <%-- <div class="form-group">
            <label class="col-lg-3 control-label"><spring:message code="label.commission.caltarget"/> </label>
            <div class="col-lg-5">
                <input type="checkbox" name="calTarget" id="calTarget"/>
            </div>
        </div>--%>

    </div>
    <div id="commissionRuleAddAttributeStep" class="commissionRuleAddAttributeStep step"></div>
    <div id="confirmStep" class="confirmStep step"></div>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/></button>
                <button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/></button>
                <button type="button" class="btn btn-default dialogSubmitBtn" data-skip-validate="true" id="submit"><spring:message code="label.book.status.save"/></button>
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

        // commission template
        $.post('<c:url value="/commission/commissionTemplateSelectList"/>', {shopId: $(this).val()}, function (res) {
            $('#commissionTemplateId', Dialog.getContext()).html(res);
        });

        // 初始化 multi select 組件
        var userGroupMultiSelect = $('#userGroupMultiSelect', Dialog.getContext());
        var categoryMultiSelect = $('#categoryMultiSelect', Dialog.getContext());
        var productMultiSelect = $('#productMultiSelect', Dialog.getContext());
        var selectableHeader = "<div class='custom-header'>Selectable items</div>";
        var selectionHeader = "<div class='custom-header'>Selection items</div>";
        var options = {
            selectableHeader: selectableHeader,
            selectionHeader: selectionHeader
        };
        productMultiSelect.multiSelect(options);
        userGroupMultiSelect.multiSelect(options);
        categoryMultiSelect.multiSelect(options);

        $('#searchProductBtn', Dialog.getContext()).click(function () {
            $.post('<c:url value="/product/selectOptionJson"/>', {productName: $(this).siblings('input').val()}, function (res) {
                if (!res) {
                    return;
                }
                var selectedOptions = productMultiSelect.find('option:selected');
                productMultiSelect.find("option").not(selectedOptions).remove();
                var html = [];
                var selectedIds = [];
                $.each(selectedOptions, function () {
                    selectedIds.push($(this).attr('value'));
                });

                $.each(res, function (index, item) {
                    if ($.inArray(item.value + '', selectedIds) == -1) {
                        html.push('<option value="' + item.value + '">' + item.label + '</option>');
                    }
                });

                $(html.join('')).appendTo(productMultiSelect);
                productMultiSelect.multiSelect('refresh');
            });
        });
        
        $('#searchUserGroupBtn', Dialog.getContext()).click(function () {
            $.post('<c:url value="/member/selectUserGroupJson"/>', 
            		{name: $(this).siblings('input').val(),type: $('#userGroupType').val(),module: $('#userGroupModule').val()}, 
            		function (res) {
                if (!res) {
                    return;
                }
                var selectedOptions = userGroupMultiSelect.find('option:selected');
                userGroupMultiSelect.find("option").not(selectedOptions).remove();
                var html = [];
                var selectedIds = [];
                $.each(selectedOptions, function () {
                    selectedIds.push($(this).attr('value'));
                });

                $.each(res, function (index, item) {
                    if ($.inArray(item.value + '', selectedIds) == -1) {
                        html.push('<option value="' + item.value + '">' + item.label + '</option>');
                    }
                });

                $(html.join('')).appendTo(userGroupMultiSelect);
                userGroupMultiSelect.multiSelect('refresh');
            });
        });

        $('#searchCategoryBtn', Dialog.getContext()).click(function () {
            $.post('<c:url value="/category/selectOptionJson"/>', {displayName: $(this).siblings('input').val()}, function (res) {
                if (!res) {
                    return;
                }
                var selectedOptions = categoryMultiSelect.find('option:selected');
                categoryMultiSelect.find("option").not(selectedOptions).remove();
                var html = [];
                var selectedIds = [];
                $.each(selectedOptions, function () {
                    selectedIds.push($(this).attr('value'));
                });

                $.each(res, function (index, item) {
                    if ($.inArray(item.value + '', selectedIds) == -1) {
                        html.push('<option value="' + item.value + '">' + item.label + '</option>');
                    }
                });

                $(html.join('')).appendTo(categoryMultiSelect);
                categoryMultiSelect.multiSelect('refresh');
            });
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

                // 添加參數
                html = [];
                form.find('input[name^="categoryVOs"]').remove();
                form.find('input[name^="userGroupVOs"]').remove();
                form.find('input[name^="productVOs"]').remove();
                // category
                categoryMultiSelect.find('option:selected').each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="categoryVOs[{0}].value" value="{1}"/>', index, $item.attr('value')));
                    html.push(String.format('<input type="hidden" name="categoryVOs[{0}].label" value="{1}"/>', index, $item.html()));
                });
                // userGroup
                userGroupMultiSelect.find('option:selected').each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="userGroupVOs[{0}].value" value="{1}"/>', index, $item.attr('value')));
                    html.push(String.format('<input type="hidden" name="userGroupVOs[{0}].label" value="{1}"/>', index, $item.html()));
                });

             	// product option
                productMultiSelect.find('option:selected').each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="productVOs[{0}].value" value="{1}"/>', index, $item.attr('value')));
                    html.push(String.format('<input type="hidden" name="productVOs[{0}].label" value="{1}"/>', index, $item.html()));
                });
                $(html.join('')).appendTo(form);
                $.post('<c:url value="/commission/commissionRuleAddAttribute"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    previousBtn.show();
                });
            } else if (currentStep.hasClass('commissionRuleAddAttributeStep')) {
                form.find('input[name^="commissionAttributeVOs"]').remove();
                html = [];
                var attributeKeys = $('input.attributeKeys', Dialog.getContext()).filter(function () {
                    return this.value;
                });
                attributeKeys.each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="commissionAttributeVOs[{0}].commissionAttributeKeyId" value="{1}"/>', index, $item.data('attribute-key-id')));
                    html.push(String.format('<input type="hidden" name="commissionAttributeVOs[{0}].reference" value="{1}"/>', index, $item.data('reference')));
                    html.push(String.format('<input type="hidden" name="commissionAttributeVOs[{0}].name" value="{1}"/>', index, $item.data('name')));
                    html.push(String.format('<input type="hidden" name="commissionAttributeVOs[{0}].description" value="{1}"/>', index, $item.data('description')));
                    html.push(String.format('<input type="hidden" name="commissionAttributeVOs[{0}].value" value="{1}"/>', index, $item.val()));
                });
                $(html.join('')).appendTo(form);

                $.post('<c:url value="/commission/commissionRuleConfirm"/>', form.serialize(), function (res) {
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
                commissionTemplateId: {
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
