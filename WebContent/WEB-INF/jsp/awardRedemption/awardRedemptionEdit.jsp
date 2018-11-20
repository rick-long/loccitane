<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/awardRedemption/edit"/>
<form:form modelAttribute="awardRedemptionAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/> *</label>
        <div class="col-lg-5">
            <form:input path="name" id="name" class="form-control"/>
        </div>
    </div>
	<div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.description"/></label>
        <div class="col-lg-5">
             <form:textarea path="description" id="description" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.redeem.type"/></label>
        <div class="col-lg-5">
             <form:select path="redeemType" id="redeemType" class="selectpicker form-control">
                 <form:option value="VOUCHER_CASH"><spring:message code="label.redeem.type.VOUCHER_CASH"/></form:option>
                 <form:option value="VOUCHER_TREATMENT"><spring:message code="label.redeem.type.VOUCHER_TREATMENT"/></form:option>
             <%--    <form:option value="GOODS"><spring:message code="label.redeem.type.GOODS"/></form:option>--%>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.redeem.channel"/></label>
        <div class="col-lg-5">
            <form:select path="redeemChannel" id="redeemChannel" class="selectpicker form-control">
                <form:option value="MEMBER_REDEEM"><spring:message code="label.redeem.channel.MEMBER_REDEEM"/></form:option>
      <%--          <form:option value="THANKYOU_REDEEM"><spring:message code="label.redeem.channel.THANKYOU_REDEEM"/></form:option>--%>
            </form:select>
        </div>
    </div>
    <div class="form-group categoryTreeMenu">
		<label class="col-lg-4 control-label"><spring:message code="label.product.tree"/></label>
		<div class="col-lg-5">
			<div class="select-category" data-selectable="option" data-root-id="2" data-product-option-id="${awardRedemptionAddVO.productOptionId }"></div>
		</div>
	</div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.redeem.required.lp"/> *</label>
        <div class="col-lg-5">
            <form:input path="requiredLp" id="requiredLp" class="form-control" />
        </div>
    </div>
<%--    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.redeem.required.amount"/></label>
        <div class="col-lg-5">
            <form:input path="requiredAmount" id="requiredAmount" class="form-control"/>
        </div>
    </div>--%>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.award.be.worth"/></label>
        <div class="col-lg-5">
            <form:input path="beWorth" id="beWorth" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.start.date" /> *</label>
        <div class="col-lg-5">
            <div class="input-group date form_time">
	        	<input id=startDate name="startDate" class="form-control startDate" 
	        		value="${awardRedemptionAddVO.startDate}" size="16">
	            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	        </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.end.date"/>*</label>
        <div class="col-lg-5">
            <div class="input-group date form_time">
	        	<input id="endDate" name="endDate" class="form-control endDate" 
	        		value="${awardRedemptionAddVO.endDate}" size="16">
	            <span class="input-group-addon" id="endDateSpan"><span class="glyphicon glyphicon-time"></span></span>
	        </div>
        </div>
    </div>
    <%--<div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.redeem.valid.location.shops"/></label>
        <div class="col-lg-5">
             <c:forEach items="${shopList}" var="shop">
                 <label class="radio-inline"><form:checkbox path="shopIds" value="${shop.id }"/> ${shop.name}</label>
             </c:forEach>
        </div>
    </div>--%>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.terms.and.conditions"/></label>
        <div class="col-lg-5">
            <textarea id="validAt" name="validAt" class="form-control">${awardRedemptionAddVO.validAt}</textarea>
        </div>
    </div>
    <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.isactive"/></label>
            <div class="col-lg-5">
                <form:select class="selectpicker form-control" path="isActive" id="isActive">
                    <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
                </form:select>
            </div>
        </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <%--<button type="button" class="btn btn-info dialogResetBtn">                     <spring:message code="label.button.reset"/>                 </button>--%>
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.button.submit"/>
                </button>
                <form:hidden path="id" id="id" class="form-control"/>
            </div>
        </div>
    </div>
</form:form>

<script type="text/javascript">

    $(document).ready(function () {

        var redeemType=$('#redeemType', Dialog.getContext());
        var categoryTreeMenu=$('.categoryTreeMenu', Dialog.getContext());
        if($("#redeemType option:selected").val()=="VOUCHER_CASH"){
            $(categoryTreeMenu).hide();
        }
        $(redeemType).change(function(){
            if($("#redeemType option:selected").val()=="VOUCHER_CASH"){
                $(categoryTreeMenu).hide();
                var categoryId=$('#categoryId', Dialog.getContext());
                var productId=$('#productId', Dialog.getContext());
                var productOptionId=$('#productOptionId', Dialog.getContext());
                $(categoryId).val("");
                $(productId).val("");
                $(productOptionId).val("");
            }

            if($("#redeemType option:selected").val()=="VOUCHER_TREATMENT"){
                $(categoryTreeMenu).show();
            }

        })
        $('#startDate', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('.input-group-addon', Dialog.getContext()).click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });

        $('#endDate', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#endDateSpan', Dialog.getContext()).click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
        
        $('form', Dialog.getContext()).bootstrapValidator({
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
                requiredLp: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                        	regexp: /^[0-9]+(.[0-9]{0,2})?$/,
                            message: '<spring:message code="label.int.regexp"/>'
                        }
                    }
                },
                startDate: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                endDate: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });
        
        $('.select-category', Dialog.getContext()).selectCategory({});
    });
</script>
