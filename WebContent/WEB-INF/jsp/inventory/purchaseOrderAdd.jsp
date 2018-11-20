<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3><spring:message code="label.inventory.purchase.order.add.management"/></h3>
    <div class="level2_content_container">
        <c:set var="isEdit" value="${inventoryPurchaseOrderVO.state eq 'EDIT'}"/>
        <c:url var="purchaseOrderAdd" value="/inventory/purchaseOrderAdd"/>
        <form:form data-forward="inventoryPurchaseOrderListMenu" modelAttribute="inventoryPurchaseOrderVO"
                   class="form-horizontal" id="saveReportForm">
            <input type="hidden" name="token" value="${inventoryFromToken}">
            <input type="hidden" name="id" value="${inventoryPurchaseOrderVO.id}"/>
            <input type="hidden" name="state" value="${inventoryPurchaseOrderVO.state}"/>
            <div id="baseStep" class="baseStep step active">
                <div class="form-group">
                    <label class="col-lg-2 control-label"><spring:message code="label.supplier"/>*</label>
                    <div class="col-lg-5">
                        <form:select path="supplierId" id="supplierId" cssClass="selectpicker form-control">
                            <c:forEach items="${supplierList}" var="item">
                                <form:option value="${item.id}">${item.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label"><spring:message code="label.purchase.date"/>*</label>
                    <div class="col-lg-5">
                        <div class="input-group date form_time">
                            <input id="date" name="date" class="form-control appointmentDate"
                                   value='<fmt:formatDate value="${inventoryPurchaseOrderVO.date}" pattern="yyyy-MM-dd"/>'
                                   size="16" readonly>
                            <span class="input-group-addon time-toggle"><span
                                    class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label"><spring:message code="label.delivery.date"/>*</label>
                    <div class="col-lg-5">
                        <div class="input-group date form_time">
                            <input id="expectedDeliveryDate" name="expectedDeliveryDate"
                                   class="form-control appointmentDate"
                                   value='<fmt:formatDate value="${inventoryPurchaseOrderVO.expectedDeliveryDate}" pattern="yyyy-MM-dd"/>'
                                   size="16" readonly>
                            <span class="input-group-addon time-toggle"><span
                                    class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label"><spring:message code="label.shop"/>*</label>
                    <div class="col-lg-5">
                        <select multiple="multiple" id="shopMultiSelect" name="shopList"
                                class="selectpicker form-control">
                            <c:forEach items="${shopList}" var="item">
                                <option value="${item.id}"
                                        <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label"><spring:message code="label.remarks"/></label>
                    <div class="col-lg-5">
                        <form:textarea id="remark" path="remarks" class="form-control"/>
                    </div>
                </div>
            </div>
            <div id="selectProductStep" class="selectProductStep step"></div>
            <div id="confirmStep" class="confirmStep step"></div>
            <div class="modal-footer button_left">
                <div class="bootstrap-dialog-footer">
                    <div class="bootstrap-dialog-footer-buttons">
                        <button type="button" class="btn btn-default" id="previous"><spring:message
                                code="label.book.status.previous"/></button>
                        <button type="button" class="btn btn-default" id="next"><spring:message
                                code="label.book.status.next"/></button>
                        <button type="button" class="btn btn-default formPageSubmit" data-skip-validate="true"
                                id="submit" onclick="save()">
                            <spring:message code="label.book.status.save"/>
                        </button>
                    </div>
                </div>
            </div>
        </form:form>

        <%-- 妯℃澘 --%>
        <%--<table id="template" class="table table-striped hide">
            <tr>
                <td>
                    <div class="select-category" data-selectable="option" data-root-id="3"></div>
                </td>

                <td class="col-lg-1">
                    <input class="quantity form-control" type="text"/>
                </td>
                <td>
                    <button type="button" class="addItem">+</button>
                    <button type="button" class="removeItem hide">-</button>
                </td>
            </tr>
        </table>--%>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var form;
        $('#shopMultiSelect', getContext()).multiSelect({});
        // 瑷诲唺灏嶈┍妗嗙殑鎻愪氦浜嬩欢
        var previousBtn = $('#previous', getContext());
        var nextBtn = $('#next', getContext());
        var submitBtn = $('#submit', getContext());
        previousBtn.hide();
        submitBtn.hide();

        nextBtn.click(function () {
            form = $('form', getContext());
            var currentStep = $('.step.active', getContext());
            var nextStep = currentStep.next('.step');
            if (currentStep.hasClass('baseStep')) {
                var bootstrapValidator = form.data('bootstrapValidator');
                bootstrapValidator.resetForm(); // 閲嶇疆formData
                bootstrapValidator.validate(); // 楠岃瘉
                var result = bootstrapValidator.isValid(); // 鍙栧嚭缁撴灉
                if (!result) {
                    return;
                }

                // 鍔ㄦ€佹坊鍔爐reatment鍙傛暟
                /*form.find('input[name$=".productOptionId"]').remove();
                 form.find('input[name$=".qty"]').remove();
                 html = '';*/

                /* quantityInputs.each(function (index, item) {
                 var $item = $(item);
                 html += '<input type="hidden" name="inventoryPurchaseOrderItemVOs[' + index + '].qty" value="' + $item.val() + '">';
                 html += '<input type="hidden" name="inventoryPurchaseOrderItemVOs[' + index + '].productOptionId" value="' + $item.data('product-option-id') + '">';
                 });*/
                /*$(html).appendTo(form);*/
                $.post('<c:url value="/inventory/toPurchaseOrderAddSelectProduct"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    //submitBtn.show();
                    //nextBtn.hide();
                    previousBtn.show();
                });
            } else if (currentStep.hasClass('selectProductStep')) {
                form = $('form', getContext());
                // 楠岃瘉bookItem
                 if ($('select[name="productOptionId"]:not(:last)', form).filter(function () {
                    return !this.value;
                }).length > 0) {
                    Dialog.alert({
                        title: "<spring:message code="lable.error"/> ",
                        message: "<spring:message code="label.book.please.select.treatment"/> "
                    });
                    return;
                }
                if ($('.quantity:not(:last)', form).filter(function () {
                    return !this.value;
                }).length > 0) {
                    Dialog.alert({
                        title: "<spring:message code="lable.error"/>",
                        message: "<spring:message code="label.inventory.please.input.quantity"/> "
                    });
                    return;
                }
                // 缁勮parameter
                var html = [];
                $("#productOptionBody", getContext()).find('tr').each(function (index) {
                    var $this = $(this);
                    html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderItemVOs[{0}].productOptionId" value="{1}"/>', index, $this.find('select[name="productOptionId"]').val()));
                    html.push(String.format('<input type="hidden" name="inventoryPurchaseOrderItemVOs[{0}].qty" value="{1}"/>', index, $this.find('.quantity').val()));
                });
                $('input[name*=inventoryPurchaseOrderItemVOs]', getContext()).remove();
                $(html.join(',')).appendTo(form);
                $.post('<c:url value="/inventory/purchaseOrderConfirm"/>', form.serialize(), function (res) {
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
            var currentStep = $('.step.active', getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                previousBtn.hide();
                nextBtn.show();
            } else {
                nextBtn.show();
            }
        });

        $('#expectedDeliveryDate, #date', getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });

        $('form', getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                supplierId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                date: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                expectedDeliveryDate: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                shopIds: {
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

    function save() {
        $.ajax({
            cache: true,
            type: "POST",
            url: '<c:url value="/inventory/purchaseOrderAdd"/>',
            data: $('#saveReportForm').serialize(),// saveReportForm
            async: false,
            error: function (request) {
                BootstrapDialog.show({
                    message: '<spring:message code="lable.submit.error"/> '
                });
            },
            success: function (data) {
                BootstrapDialog.show({
                    message: data.message
                });
                setTimeout(function () {
                    window.location.reload();//刷新当前页面.
                }, 1000);

            }
        });
    }
</script>
