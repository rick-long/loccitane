<%@page import="com.spa.constant.CommonConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/staff/editClock"/>
<form id="defaultForm" id="" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url }'>
    	<input type="hidden" name="id" id="id" value="${staffInOrOut.id}"/>
      	<div class="form-group">
       		<label class="col-lg-4 control-label"><spring:message code="label.dateTime"/></label>
      		<div class="col-lg-5">
		        <div class="input-group date form_time">
		            <input id="dateTime" name="dateTime" class="form-control" value='<fmt:formatDate value="${staffInOrOut.dateTime}" pattern="yyyy-MM-dd"/>' size="16" readonly>
		            <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	    	</div>
    	</div>

    	<div class="form-group">
	        <label class="col-lg-4 control-label"><spring:message code="label.type"/></label>
	        <div class="col-lg-5">
	            <select name="type" class="selectpicker form-control" id="type">
	                <option value="0" <c:if test="${staffInOrOut.type == 0}">selected</c:if> ><spring:message code="label.option.clock.in"/></option>
	                <option value="1" <c:if test="${staffInOrOut.type == 1}">selected</c:if> ><spring:message code="label.option.clock.out"/></option>
	            </select>
	        </div>
    	</div>

	    <div class="modal-footer">
	        <div class="bootstrap-dialog-footer">
	            <div class="bootstrap-dialog-footer-buttons">
	                <button data-permission="attendance:edit" type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
	                    <spring:message code="label.button.submit"/>
	                </button>
	            </div>
	        </div>
	    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {

        $('#defaultForm',getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                date: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp:{
                            regexp:/^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/,
                            message:"should be yyyy-MM-dd HH:mm:ss "
                        }
                    }
                },
                type: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: 'Should be a number.'
                        }
                    }
                },
                captcha: {
                    validators: {}
                }

            }
        });

        $('#dateTime', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d H:i',
            timepicker: true,
            datepicker: true,
            step: 5,
            value: '<fmt:formatDate value="${staffInOrOut.dateTime}" pattern="yyyy-MM-dd HH:mm"/>'
        });
    });
</script>







