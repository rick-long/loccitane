<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/inventory/transferAdd"/>
<form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal">
    <input type="hidden" name="inventoryId" value="${inventoryTransferVO.inventoryId}"/>
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.product.option"/>*</label>
            <div class="col-lg-5">
                <div>${inventoryTransferVO.inventory.productOption.label6}</div>
                <c:forEach items="${inventoryTransferVO.inventory.inventoryWarehouses}" var="item">
                    <div>${item.shop.name} : ${item.qty}</div>
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.from.shop"/>*</label>
            <div class="col-lg-5">
                <select id="fromShopId" name="fromShopId" class="selectpicker form-control">
                    <c:forEach items="${shopList}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.to.shop"/>*</label>
            <div class="col-lg-5">
                <select id="toShopId" name="toShopId" class="selectpicker form-control">
                    <c:forEach items="${shopList}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.entry.date"/>*</label>
            <div class="col-lg-5">
                <div class="input-group date form_time">
                    <input id="entryDate" name="entryDate" class="form-control" value='<fmt:formatDate value="${inventoryTransferVO.entryDate}" pattern="yyyy-MM-dd"/>' size="16" readonly>
                    <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.qty"/>*</label>
            <div class="col-lg-5">
                <select id="qtyList" name="qtyList" class="selectpicker form-control">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="-1"><spring:message code="label.custom.qty"/></option>
                </select>
            </div>
        </div>
        <div id="qtyGroup" class="form-group hide">
            <label class="col-lg-4 control-label"><spring:message code="label.custom.qty"/></label>
            <div class="col-lg-5">
                <input id="qty" name="qty" class="form-control" value="1"/>
            </div>
        </div>
    </div>
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
        var html, postParameter;

        // 註冊對話框的提交事件
        var previousBtn = $('#previous', Dialog.getContext());
        var nextBtn = $('#next', Dialog.getContext());
        var submitBtn = $('#submit', Dialog.getContext());
        previousBtn.hide();
        submitBtn.hide();

        $('#qtyList', Dialog.getContext()).change(function () {
            var val = $(this).val();
            if (val == -1) {
                $('#qtyGroup', Dialog.getContext()).removeClass('hide');
            } else {
                $('#qtyGroup', Dialog.getContext()).addClass('hide');
                $('#qty', Dialog.getContext()).val(val);
            }
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
                var fromShopId = $('#fromShopId', Dialog.getContext()).val();
                var toShopId = $('#toShopId', Dialog.getContext()).val();
                if (fromShopId == toShopId) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="label.inventory.two.shops.are.the.same"/>'
                    });
                    return;
                }

                // 验证库存是否足够
                $.post('<c:url value="/inventory/transferConfirm"/>', form.serialize(), function (res) {
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    submitBtn.show();
                    nextBtn.hide();
                    previousBtn.show();
                    nextStep.html(res);
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

        $('#entryDate', Dialog.getContext()).datetimepicker({
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
                fromShopId: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                toShopId: {
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
                qtyList: {
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
    });
</script>
