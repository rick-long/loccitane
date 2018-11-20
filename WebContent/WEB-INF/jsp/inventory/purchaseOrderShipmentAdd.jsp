<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:url var="addUrl" value="/inventory/purchaseOrderShipmentAdd"/>
<form action="${addUrl}" method="post" data-form-token="${TokenUtil.get(pageContext)}" class="form-horizontal">
    <input type="hidden" name="inventoryPurchaseOrderId" value="${inventoryPurchaseOrderShipmentVO.inventoryPurchaseOrderId}"/>
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.delivery.no"/>*</label>
            <div class="col-lg-5">
                <input id="deliveryNumber" name="deliveryNumber" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.delivery.date"/>*</label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="deliveryDate" name="deliveryDate" class="form-control appointmentDate" value='<fmt:formatDate value="${inventoryPurchaseOrderShipmentVO.deliveryDate}" pattern="yyyy-MM-dd"/>' size="16" readonly/>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
        <div class="row new-row-width">
            <label><spring:message code="label.products"/> *</label>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="label.name"/></th>
                    <th><spring:message code="label.reference"/></th>
                    <th><spring:message code="label.default.price"/></th>
                    <th><spring:message code="label.received.qty"/> / <spring:message code="label.total.qty"/></th>
                    <th><spring:message code="label.qty"/></th>
                </tr>
                </thead>
                <tbody id="productOptionBody">
                <c:forEach items="${inventoryPurchaseOrder.inventoryPurchaseItems}" var="item">
                    <tr>
                        <td>${item.productOption.label6}</td>
                        <td>${item.productOption.product.reference }</td>
                        <td>${item.cost}</td>
                        <td> ${item.receivedAmount} / ${item.qty}</td>
                        <td>
                            <input type="text" class="form-control quantity" data-product-option-id="${item.productOption.id}" value="${item.qty - item.receivedAmount}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div id="assignStep" class="assignStep step"></div>
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
        var html = '', inputs;

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
                var quantityInputs = currentStep.find('input.quantity');

                var quantityFlag = false;
                quantityInputs.each(function () {
                    if ($(this).val()) {
                        quantityFlag = $.isNumeric($(this).val());
                        if (!quantityFlag) {
                            return false;
                        }
                    }
                });
                if (!quantityFlag) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="label.inventory.please.input.quantity"/> '
                    });
                    return;
                }

                var bootstrapValidator = form.data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 重置formData
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }

                // 动态添加treatment参数
                form.find('input[name^="inventoryPurchaseOrderShipmentItemVOs"]').remove();
                html = [];
                var index = 0;
                quantityInputs.each(function (i, item) {
                    var $item = $(item);
                    if ($item.val() > 0) {
                        html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderShipmentItemVOs[{0}].qty" value="{1}">', index, $item.val()));
                        html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderShipmentItemVOs[{0}].productOptionId" value="{1}">', index, $item.data('product-option-id')));
                        index++;
                    }
                });
                $(html.join('')).appendTo(form);
                $.post('<c:url value="/inventory/purchaseOrderShipmentAssign"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    previousBtn.show();
                });
            } else if (currentStep.hasClass('assignStep')) {
                // 是否分配完成
                var shopTotalQty = $('.shop-total-quantity', Dialog.getContext());
                var assignFlag = true;
                $.each(shopTotalQty, function (index, item) {
                    var $item = $(item);
                    assignFlag = $item.data('total-qty') == $item.data('current-qty');
                    if (!assignFlag) {
                        return false;
                    }
                });
                if (!assignFlag) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="label.inventory.assign.quantity.error"/> '
                    });
                    return;
                }

                // 提交参数
                html = [];
                form.find('input[name^="inventoryPurchaseOrderShipmentItemVOs"]').remove();
                var shipmentItem = $('.shipment-item', Dialog.getContext());
                shipmentItem.each(function (index, item) {
                    var $item = $(item);
                    var productOptionId = $item.data('product-option-id');
                    html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderShipmentItemVOs[{0}].qty" value="{1}">', index, $item.data('qty')));
                    html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderShipmentItemVOs[{0}].productOptionId" value="{1}">', index, productOptionId));

                    var shopQty = $item.find('input.shop-quantity');
                    var shopIndex = 0;
                    shopQty.each(function (i, shop) {
                        var $shop = $(shop);
                        var val = $shop.val();
                        if (val > 0) {
                            html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderShipmentItemVOs[{0}].inventoryTransactionVOs[{1}].shopId" value="{2}">', index, shopIndex, $shop.data('shop-id')));
                            html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderShipmentItemVOs[{0}].inventoryTransactionVOs[{1}].productOptionId" value="{2}">', index, shopIndex, productOptionId));
                            html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderShipmentItemVOs[{0}].inventoryTransactionVOs[{1}].qty" value="{2}">', index, shopIndex, $shop.val()));
                            shopIndex++;
                        }
                    });
                });
                $(html.join('')).appendTo(form);
                $.post('<c:url value="/inventory/purchaseOrderShipmentAddConfirm"/>', form.serialize(), function (res) {
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

        $('#deliveryDate', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });

        $('form', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                deliveryNumber: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                deliveryDate: {
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
