<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style>
    #bundleAddForm .ms-container {
        width: 350px;
    }
	.select-group{
		margin-left:150px;
	}
    .p-lr-0{
    padding-left:0;
    paddng-right:0;
    }
</style>

<c:url var="url" value="/bundle/bundleSave"/>
<form id="bundleAddForm" action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.bundle.code"/>*</label>
            <div class="col-lg-5">
                <input id="code" name="code" class="form-control"></input>
            </div>
        </div>

        <div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.bundle.name"/>*</label>
            <div class="col-lg-5">
                <input id="name" name="name" class="form-control"></input>
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
            <label class="col-lg-4 control-label"><spring:message code="label.start.date"/>*</label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="startTime" name="startTime" class="form-control startTime" value='<fmt:formatDate value="${discountRuleVO.startTime}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.end.date"/>*</label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="endTime" name="endTime" class="form-control endTime" value='<fmt:formatDate value="${discountRuleVO.endTime}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="shopId" class="col-lg-4 control-label"><spring:message code="label.shop"/>*</label>
            <div class="col-lg-5">

                <c:forEach items="${shopList}" var="item">
                    <div class="col-lg-12 p-lr-0">

                        <div class="col-lg-10 p-lr-0">${item.name}</div> <input type="checkbox"  class="col-lg-2" name="shopIds" value="${item.id}" <c:if test="${currentShop.id == item.id}">checked="checked"</c:if>>
                    <%--<option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>--%>

                    </div>
                </c:forEach>
            </div>
        </div>

        
        
		<div class="form-group">
            <label for="description" class="col-lg-4 control-label">
                <spring:message code="label.bundle.amount"/>*</label>
            <div class="col-lg-5">
                <input id="bundleAmount" name="bundleAmount" class="form-control"></input>
            </div>
        </div>

        <div class="form-group">
            <table class="table table-striped" id="bundleAddTable">
                <tbody>
                	<c:forEach var="idx"  begin="0" end="${bundleItemsMax}">
                    <tr>
                        <td>
                            <div class="col-lg-5 select-group">
                            	<spring:message code="label.bundle.group"/>&nbsp;&nbsp;${idx+1}
                                <div class="input-group">
                                    <input class="form-control" placeholder='<spring:message code="label.select.treatment"/>'/>
                                    <span class="input-group-addon searchProductOptionBtn${idx}">
                                        <span class="glyphicon glyphicon-search"></span>
                                    </span>
                                </div>
                                <select multiple="multiple" id="productOptionMultiSelect${idx}" class="selectpicker form-control">
                                </select>
                            </div>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
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
        // 註冊對話框的提交事件
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
     	var selectableHeader = "<div class='custom-header'><spring:message code='label.commission.selectable.items'/></div>";
        var selectionHeader = "<div class='custom-header'><spring:message code='label.commission.selection.items'/></div>";
        var options = {
            selectableHeader: selectableHeader,
            selectionHeader: selectionHeader
        };
        $("#bundleAddTable tbody", Dialog.getContext()).find('tr').each(function(index, item) {
        	 var $this = $('#productOptionMultiSelect'+index);
        	 $this.multiSelect(options);
        });
        $("#bundleAddTable tbody", Dialog.getContext()).find('tr').each(function(index, item) {
        	var $this = $('.searchProductOptionBtn'+index);
        	$this.click(function () {
	            $.post('<c:url value="/po/selectOptionJson"/>', {productName: $this.siblings('input').val(),prodType:'CA-TREATMENT'}, function (res) {
	                if (!res) {
	                    return;
	                }
	                var productOptionMultiSelect = $('#productOptionMultiSelect'+index)
	                var selectedOptions = productOptionMultiSelect.find('option:selected');
	                productOptionMultiSelect.find("option").not(selectedOptions).remove();
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
	
	                $(html.join('')).appendTo(productOptionMultiSelect);
	                productOptionMultiSelect.multiSelect('refresh');
	            });
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
                form.find('input[name^="bundleItems"]').remove();
             	// product option
             	$("#bundleAddTable tbody", Dialog.getContext()).find('tr').each(function(index, item) {
             		var productOptionMultiSelect = $('#productOptionMultiSelect'+index)
             		var productOptionIds = new Array();
             		productOptionMultiSelect.find('option:selected').each(function (index, item) {
                    	var $item = $(item);
                    	productOptionIds[index] = $item.attr('value');
                	});
                	html.push(String.format('<input type="hidden" name="bundleItems[{0}].group" value="{1}"/>', index, index));
                	html.push(String.format('<input type="hidden" name="bundleItems[{0}].productOptionIds" value="{1}"/>', index, productOptionIds));
             	});
                $(html.join('')).appendTo(form);
                
                $.post('<c:url value="/bundle/bundleConfirm"/>', form.serialize(), function (res) {
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
                name: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                code: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                bundleAmount: {
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
