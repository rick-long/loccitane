<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/supplier/add"/>
<form  id="defaultForm" method="post" data-form-token="${TokenUtil.get(pageContext)}" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
        <div class="col-lg-5">
            <input type="text" name="name" id="name" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.contactPerson"/>*</label>
        <div class="col-lg-5">
            <input type="text" name="contactPerson" id="contactPerson" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.contactEmail"/>*</label>
        <div class="col-lg-5">
            <input type="text" name="contactEmail" id="contactEmail" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.contactTel"/>*</label>
        <div class="col-lg-5">
            <input type="text" name="contactTel" id="contactTel" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.product"/>*</label>
        <div class="col-lg-5">
            <div class="input-group">
                <input class="form-control" placeholder="Search Product"/>
                <span id="searchProductBtn" class="input-group-addon">
                    <span class="glyphicon glyphicon-search"></span>
                </span>
            </div>
            <select multiple="multiple" id="productMultiSelect" name="productIds" class="selectpicker form-control">
            </select>
        </div>
    </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
    	
        var productMultiSelect = $('#productMultiSelect', Dialog.getContext());
        var selectableHeader = "<div class='custom-header'>Available Item(s)</div>";
        var selectionHeader = "<div class='custom-header'>Selected Item(s)</div>";
        var options = {
            selectableHeader : selectableHeader,
            selectionHeader : selectionHeader
        };
        productMultiSelect.multiSelect(options);
        
        $('#searchProductBtn', Dialog.getContext()).click(function () {
            $.post('<c:url value="/product/selectOptionJson"/>', {productName: $(this).siblings('input').val(),categoryId:3}, function (res) {
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
        
        $('#defaultForm', Dialog.getContext()).bootstrapValidator({
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
                },
                contactTel: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        })
    });
</script>