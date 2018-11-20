<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/lp/adjust"/>
<form:form modelAttribute="pointsAdjustVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
	<div class="form-group">
	   	<label class="col-lg-4 control-label"><spring:message code="label.client"/>*</label>
		<div class="col-lg-5">
			<div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
                 <input name="memberId" id="memberId" class="form-control" type="hidden" value="${pointsAdjustVO.memberId }"/>
                 <input name="username" id="username" class="form-control quick-search" value="${pointsAdjustVO.username }" readonly/>
                 <span class="input-group-addon">
                     <span class="glyphicon glyphicon-search"></span>
                 </span>
             </div>
	   	</div>
	</div>
	<div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.adjust.action"/>*</label>
        <div class="col-lg-5">
            <form:select path="action" id="action" class="selectpicker form-control">
            	<form:option value="PLUS"><spring:message code="label.adjust.action.plus"/></form:option>
            	<form:option value="MINUS"><spring:message code="label.adjust.action.minus"/></form:option>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.adjust.points"/>*</label>
        <div class="col-lg-5">
            <form:input path="adjustPoints" id="adjustPoints" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyalty.level.month.limit"/>*</label>
        <div class="col-lg-5">
            <form:input path="monthLimit" id="monthLimit" class="form-control"/>
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
            	username: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }, 
                adjustPoints: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^\+?[1-9][0-9]*$/,
                            message: '<spring:message code="label.int.butnot0.regexp"/>'
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
                            regexp: /^\+?[1-9][0-9]*$/,
                            message: '<spring:message code="label.int.butnot0.regexp"/>'
                        }
                    }
                }
            }
        });
    });
</script>
