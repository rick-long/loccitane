<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form id="addTransactionFrom" class="form-horizontal">
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.shop"/>*</label>
            <div class="col-lg-5">
                <select id="shopId" name="shopId" class="selectpicker form-control">
                    <c:forEach items="${shopList}" var="item">
                        <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.entry.date"/>*</label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="entryDate" name="entryDate" class="form-control" value='<fmt:formatDate value="${inventoryTransactionVO.entryDate}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
        <div class="form-group categoryTreeMenu">
			<label class="col-lg-4 control-label"><spring:message code="label.product.option"/>*</label>
			<div class="col-lg-5">
				<div class="select-category" data-selectable="option" data-root-id="3"></div>
				 <input id="productName" type="hidden" value="">
			</div>
		</div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.qty"/>*</label>
            <div class="col-lg-5">
                <input id="qty" name="qty" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.transaction.type"/>*</label>
            <div class="col-lg-5">
                <select id="transactionType" name="transactionType" class="selectpicker form-control">
                    <option value="">
                        <spring:message code="label.option.select.single"/>
                    </option>
                    <option value="NEW_STOCK">
                        <spring:message code="label.inventory.transaction.type.NEW_STOCK"/>
                    </option>
                    <option value="ADJUSTMENT_ADD">
                        <spring:message code="label.inventory.transaction.type.ADJUSTMENT_ADD"/>
                    </option>
                    <option value="ADJUSTMENT_MINUS">
                        <spring:message code="label.inventory.transaction.type.ADJUSTMENT_MINUS"/>
                    </option>
                    <option value="SALE"><spring:message code="label.inventory.transaction.type.SALE"/>
                    </option>
                    <option value="RETURNED">
                        <spring:message code="label.inventory.transaction.type.RETURNED"/>
                    </option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
            <div class="col-lg-5">
                <textarea id="remarks" class="form-control"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"></label>
            <div class="col-lg-5">
                <button id="addTransaction" class="btn btn-default">
                    <spring:message code="label.button.add"/></button>
            </div>
        </div>

        <div class="row new-row-width">
            <label><spring:message code="label.inventorytransactionadded"/> </label>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="label.shop"/></th>
                    <th><spring:message code="label.entry.date"/></th>
                    <th><spring:message code="label.product"/></th>
                    <th><spring:message code="label.transaction.type"/></th>
                    <th><spring:message code="label.qty"/></th>
                    <th><spring:message code="label.remarks"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody id="body">

                </tbody>
            </table>
        </div>
    </div>
    <div id="confirmStep" class="confirmStep step"></div>
</form>

<c:url var="url" value="/inventory/transactionAdd"/>
<form id="submitForm" action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post">
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-default" id="previous">Previous</button>
                <button type="button" class="btn btn-default" id="next">Next</button>
                <button type="button" class="btn btn-default" id="waiting">Waiting</button>
                <button type="button" class="btn btn-default dialogSubmitBtn" data-skip-validate="true" id="submit">
                    Save
                </button>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        
        var addTransactionForm = $('#addTransactionFrom', Dialog.getContext());
        var submitForm = $('#submitForm', Dialog.getContext());
        var html, postParameter;

        // 註冊對話框的提交事件
        var previousBtn = $('#previous', Dialog.getContext());
        var nextBtn = $('#next', Dialog.getContext());
        var waitingBtn = $('#waiting', Dialog.getContext());
        var submitBtn = $('#submit', Dialog.getContext());
        previousBtn.hide();
        waitingBtn.hide();
        submitBtn.hide();

        $('#addTransaction', Dialog.getContext()).click(function () {
            var bootstrapValidator = addTransactionForm.data('bootstrapValidator');
            bootstrapValidator.validate(); // 验证
            var result = bootstrapValidator.isValid(); // 取出结果
            if (!result) {
                return;
            }
            // add transaction
            html = [];
            html.push('<tr>');
            // shop
            var shopOption = $('#shopId', Dialog.getContext()).find('option:selected');
            html.push(String.format('<td>{0}</td>', shopOption.text()));
            // entry date
            var entryDate = $('#entryDate', Dialog.getContext()).val();
            html.push(String.format('<td>{0}</td>', entryDate));
            // product option
            var productOptionId = $('#productOptionId', Dialog.getContext()).val();
            var productName = $('#productName', Dialog.getContext()).val();
            html.push(String.format('<td>{0}</td>', productName));
            // transaction type
            var transactionTypeOption = $('#transactionType', Dialog.getContext()).find('option:selected');
            html.push(String.format('<td>{0}</td>', transactionTypeOption.text()));
            // qty
            var qty = $('#qty', Dialog.getContext()).val();
            html.push(String.format('<td>{0}</td>', qty));
            // remarks
            var remarks = $('#remarks', Dialog.getContext()).val() || '';
            html.push(String.format('<td>{0}</td>', remarks));
            // removeBtn
            html.push('<td><button class="btn btn-warning removePOBtn"><spring:message code="label.remove"/> </button></td>');
            // data
            var dataInput = '<input type="hidden" data-shop-id="{0}" data-entry-date="{1}" data-product-option-id="{2}" data-transaction-type="{3}" data-qty="{4}" data-remarks="{5}"/>';
            html.push(String.format(dataInput, shopOption.val(), entryDate, productOptionId, transactionTypeOption.val(), qty, remarks));
            html.push('</tr>');

            var $html = $(html.join(''));
            // 注册删除事件
            $html.find('.removePOBtn').click(function () {
                $(this).parents('tr').remove();
            });
            $html.appendTo($('#body', Dialog.getContext()));
        });

        nextBtn.click(function () {
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');

            if (currentStep.hasClass('baseStep')) {
                var dataInputs = $('#body', Dialog.getContext()).find('input');
                if (dataInputs.length === 0) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="label.inventory.please.add.transaction"/> '
                    });
                    return;
                }

                // 动态添加treatment参数
                submitForm.find('input[name^="inventoryTransactionVOs"]').remove();
                html = [];
                dataInputs.each(function (index, item) {
                    var $item = $(item);
                    html.push(String.format('<input type="hidden" name="inventoryTransactionVOs[{0}].shopId" value="{1}"/>', index, $item.data('shop-id')));
                    html.push(String.format('<input type="hidden" name="inventoryTransactionVOs[{0}].productOptionId" value="{1}"/>', index, $item.data('product-option-id')));
                    html.push(String.format('<input type="hidden" name="inventoryTransactionVOs[{0}].transactionType" value="{1}"/>', index, $item.data('transaction-type')));
                    html.push(String.format('<input type="hidden" name="inventoryTransactionVOs[{0}].entryDate" value="{1}"/>', index, $item.data('entry-date')));
                    html.push(String.format('<input type="hidden" name="inventoryTransactionVOs[{0}].qty" value="{1}"/>', index, $item.data('qty')));
                    html.push(String.format('<input type="hidden" name="inventoryTransactionVOs[{0}].remarks" value="{1}"/>', index, $item.data('remarks')));
                });

                $(html.join('')).appendTo(submitForm);
                $.post('<c:url value="/inventory/transactionConfirm"/>', submitForm.serialize(), function (res) {
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
                waitingBtn.hide();
            } else {
                nextBtn.show();
            }
        });

        $('#entryDate', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });

        addTransactionForm.bootstrapValidator({
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
                entryDate: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                transactionType: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                productName: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                qty: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        integer: {
                            message: '<spring:message code="label.errors.is.not.integer"/>'
                        }
                    }
                }
            }
        });
        
        $('.select-category', Dialog.getContext()).selectCategory({
        	callback: function (context) {
                // 注册变更事件
            },
            click: function() {
      			var productOptionId=$('#productOptionId',Dialog.getContext()).val();
        		$.ajax({
                    url: '<c:url value="/po/rerurnPoName?poId="/>'+productOptionId,
                    type: "POST",
                    dataType: "text",
                    success: function(response) {
                    	$('#productName',Dialog.getContext()).val(response);
                    }
                });
            }
        });
    });
</script>
