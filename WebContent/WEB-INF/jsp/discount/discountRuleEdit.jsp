<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style>
    #discountRuleEditForm .ms-container {
        width: 350px;
    }
</style>
<c:url var="url" value="/discount/discountRuleSave"/>
<form id="discountRuleEditForm" action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" modelAttribute="discountRuleVO" class="form-horizontal">
    <input type="hidden" name="id" value="${discountRule.id}">
    <div id="baseStep" class="baseStep step active">

        <div class="form-group">
            <label class="col-lg-4 control-label">
                <spring:message code="label.template"/>*</label>
            <div class="col-lg-5">
                ${discountRule.discountTemplate.name}
                <input type="hidden" name="discountTemplateId" value="${discountRule.discountTemplate.id}"/>
            </div>
        </div>

        <div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.code"/>*</label>
            <div class="col-lg-5">
                <input id="code" name="code" class="form-control" value="${discountRule.code}"></input>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.start.time"/>*</label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="startTime" name="startTime" class="form-control appointmentDate" value='<fmt:formatDate value="${discountRule.startTime}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.end.time"/>*</label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="endTime" name="endTime" class="form-control appointmentDate" value='<fmt:formatDate value="${discountRule.endTime}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.shop"/>*</label>
            <div class="col-lg-5">
                <select id="shopId" name="shopIds" class="selectpicker form-control" multiple>
                    <c:forEach items="${shopList}" var="item">
                        <option value="${item.id}" ${discountRule.shops.contains(item)? 'selected':''}>${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.description"/>*</label>
            <div class="col-lg-5">
                <textarea id="description" name="description" class="form-control">${discountRule.description}</textarea>
            </div>
        </div>
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
                    <c:forEach items="${discountRule.categories}" var="item">
                        <option value="${item.id}" selected>${item.name}</option>
                    </c:forEach>
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
                    <c:forEach items="${discountRule.products}" var="item">
                        <option value="${item.id}" selected>${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.user.group"/></label>
            <div class="col-lg-5">
                <div class="input-group">
                    <input class="form-control" placeholder="Search Member Group"/>
                    <span id="searchUserGroupBtn" class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                    <input type="hidden" id="userGroupType" value="MEMBER">
                    <input type="hidden" id="userGroupModule" value="DISCOUNT">
                </div>
                <select multiple="multiple" id="userGroupMultiSelect" class="selectpicker form-control">
                    <c:forEach items="${discountRule.userGroups}" var="item">
                        <option value="${item.id}" selected>${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
 		<div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.loyaltygroup"/> </label>
            <div class="col-lg-5">
                <div class="input-group">
                    <input class="form-control" placeholder="Search Loyalty Group"/>
                    <span id="searchLoyaltyGroupBtn" class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
                <select multiple="multiple" id="loyaltyGroupMultiSelect" class="selectpicker form-control">
                    <c:forEach items="${discountRule.loyaltyGroups}" var="item">
                        <option value="${item.id}" selected>${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div id="discountRuleAddAttributeStep" class="discountRuleAddAttributeStep step"></div>
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
        $(":file", Dialog.getContext()).filestyle({
            buttonText: '',
            placeholder: 'Choose File'
        });

        // 注册时间
        $('#startTime, #endTime', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });

        // 初始化 multi select 組件
        var productMultiSelect = $('#productMultiSelect', Dialog.getContext());
        var categoryMultiSelect = $('#categoryMultiSelect', Dialog.getContext());
        var userGroupMultiSelect = $('#userGroupMultiSelect', Dialog.getContext());
        var loyaltyGroupMultiSelect = $('#loyaltyGroupMultiSelect', Dialog.getContext());
        
        var selectableHeader = "<div class='custom-header'>Selectable items</div>";
        var selectionHeader = "<div class='custom-header'>Selection items</div>";
        var options = {
            selectableHeader: selectableHeader,
            selectionHeader: selectionHeader
        };
        productMultiSelect.multiSelect(options);
        categoryMultiSelect.multiSelect(options);
        userGroupMultiSelect.multiSelect(options);
        loyaltyGroupMultiSelect.multiSelect(options);
        
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

        $('#searchUserGroupBtn', Dialog.getContext()).click(function () {
            userGroupMultiSelect = $('#userGroupMultiSelect', Dialog.getContext());
            $.post('<c:url value="/member/selectUserGroupJson"/>',
                {name: $(this).siblings('input').val(),type: 'MEMBER',module: 'DISCOUNT'},
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

        $('#searchLoyaltyGroupBtn', Dialog.getContext()).click(function () {
            loyaltyGroupMultiSelect = $('#loyaltyGroupMultiSelect', Dialog.getContext());
            $.post('<c:url value="/loyalty/selectLoyaltyLevelJson"/>',
                {name: $(this).siblings('input').val()},
                function (res) {
                    if (!res) {
                        return;
                    }
                    var selectedOptions = loyaltyGroupMultiSelect.find('option:selected');
                    loyaltyGroupMultiSelect.find("option").not(selectedOptions).remove();
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

                    $(html.join('')).appendTo(loyaltyGroupMultiSelect);
                    loyaltyGroupMultiSelect.multiSelect('refresh');
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
                form.find('input[name^="productVOs"]').remove();
                form.find('input[name^="userGroupIds"]').remove();
                // category
                categoryMultiSelect.find('option:selected').each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="categoryVOs[{0}].value" value="{1}"/>', index, $item.attr('value')));
                    html.push(String.format('<input type="hidden" name="categoryVOs[{0}].label" value="{1}"/>', index, $item.html()));
                });
                // product option
                productMultiSelect.find('option:selected').each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="productVOs[{0}].value" value="{1}"/>', index, $item.attr('value')));
                    html.push(String.format('<input type="hidden" name="productVOs[{0}].label" value="{1}"/>', index, $item.html()));
                });
                // usergroup
                userGroupMultiSelect.find('option:selected').each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="userGroupIds[{0}].value" value="{1}"/>', index, $item.attr('value')));
                    html.push(String.format('<input type="hidden" name="userGroupIds[{0}].label" value="{1}"/>', index, $item.html()));
                });

             	// loyalty group
                loyaltyGroupMultiSelect.find('option:selected').each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="loyaltyGroupIds[{0}].value" value="{1}"/>', index, $item.attr('value')));
                    html.push(String.format('<input type="hidden" name="loyaltyGroupIds[{0}].label" value="{1}"/>', index, $item.html()));
                });
             
                $(html.join('')).appendTo(form);
                $.post('<c:url value="/discount/discountRuleAddAttribute"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    previousBtn.show();
                });
            } else if (currentStep.hasClass('discountRuleAddAttributeStep')) {
                form.find('input[name^="discountAttributeVOs"]').remove();
                html = [];
                var attributeKeys = $('input.attributeKeys', Dialog.getContext()).filter(function () {
                    return this.value;
                });
                console.info(attributeKeys);
                attributeKeys.each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="discountAttributeVOs[{0}].discountAttributeKeyId" value="{1}"/>', index, $item.data('attribute-key-id')));
                    html.push(String.format('<input type="hidden" name="discountAttributeVOs[{0}].reference" value="{1}"/>', index, $item.data('reference')));
                    html.push(String.format('<input type="hidden" name="discountAttributeVOs[{0}].name" value="{1}"/>', index, $item.data('name')));
                    html.push(String.format('<input type="hidden" name="discountAttributeVOs[{0}].description" value="{1}"/>', index, $item.data('description')));
                    html.push(String.format('<input type="hidden" name="discountAttributeVOs[{0}].value" value="{1}"/>', index, $item.val()));
                });
                $(html.join('')).appendTo(form);

                $.post('<c:url value="/discount/discountRuleConfirm"/>', form.serialize(), function (res) {
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
                shopId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                discountTemplateId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                startTime: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                endTime: {
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
