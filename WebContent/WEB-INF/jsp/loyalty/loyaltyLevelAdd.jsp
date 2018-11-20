<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/loyalty/add"/>
<form:form modelAttribute="loyaltyLevelAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
        <div class="col-lg-5">
            <form:input path="name" id="name" class="form-control"/>
        </div>
    </div>
	<div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.reference"/>*</label>
        <div class="col-lg-5">
            <form:input path="reference" id="reference" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyalty.level.required.points"/></label>
        <div class="col-lg-5">
            <form:input path="requiredSpaPoints" id="requiredSpaPoints" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyalty.discountrequiredspapoints"/> </label>
        <div class="col-lg-5">
            <form:input path="discountRequiredSpaPoints" id="discountRequiredSpaPoints" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyalty.level.month.limit"/>*</label>
        <div class="col-lg-5">
            <form:input path="monthLimit" id="monthLimit" class="form-control"/>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyalty.discountmonthlimit"/> </label>
        <div class="col-lg-5">
            <form:input path="discountMonthLimit" id="discountMonthLimit" class="form-control"/>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyalty.level.rank"/>*</label>
        <div class="col-lg-5">
            <form:select path="rank" id="rank" class="selectpicker form-control">
            	<form:option value="1"><spring:message code="label.loyalty.level.rank"/> 1</form:option>
            	<form:option value="2"><spring:message code="label.loyalty.level.rank"/> 2</form:option>
            	<form:option value="3"><spring:message code="label.loyalty.level.rank"/> 3</form:option>
            	<form:option value="4"><spring:message code="label.loyalty.level.rank"/> 4</form:option>
            	<form:option value="5"><spring:message code="label.loyalty.level.rank"/> 5</form:option>
            	<form:option value="6"><spring:message code="label.loyalty.level.rank"/> 6</form:option>
            	<form:option value="7"><spring:message code="label.loyalty.level.rank"/> 7</form:option>
            	<form:option value="8"><spring:message code="label.loyalty.level.rank"/> 8</form:option>
            	<form:option value="9"><spring:message code="label.loyalty.level.rank"/> 9</form:option>
            	<form:option value="10"><spring:message code="label.loyalty.level.rank"/> 10</form:option>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
        <div class="col-lg-5">
            <form:textarea path="remarks" id="remarks" class="form-control"/>
        </div>
    </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <%--<button type="button" class="btn btn-info dialogResetBtn">                     <spring:message code="label.button.reset"/>                 </button>--%>
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form:form>

<script type="text/javascript">
    $(document).ready(function () {
       
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
                reference: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                requiredSpaPoints: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^\d+$/,
                            message: '<spring:message code="label.int.regexp"/>'
                        }
                    }
                },
                monthLimit: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^\d+$/,
                            message: '<spring:message code="label.int.regexp"/>'
                        }
                    }
                },
                discountRequiredSpaPoints: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^\d+$/,
                            message: '<spring:message code="label.int.regexp"/>'
                        }
                    }
                },
                discountMonthLimit: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^\d+$/,
                            message: '<spring:message code="label.int.regexp"/>'
                        }
                    }
                }
            }
        });
    });
</script>
