<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
	
	
                    <c:url var="url" value="/staffAttrKey/edit" />
                    <form:form modelAttribute="staffAttrKeyEditVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
                       <div class="form-group">
                            <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
                            <div class="col-lg-5">
                               <form:input path="name" id="name" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-4 control-label"><spring:message code="label.default.value"/></label>
                            <div class="col-lg-5">
                               <form:input path="defaultValue" id="defaultValue" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-4 control-label"><spring:message code="label.uiType"/></label>
                            <div class="col-lg-5">
                               <form:select path="uiType" id="uiType" class="selectpicker form-control">
						     		<form:option value="text"><spring:message code="label.uiType.text"/></form:option>
									<form:option value="select-single"><spring:message code="label.uiType.selectsingle"/></form:option>
									<form:option value="checkbox-multi"><spring:message code="label.uiType.checkboxmulti"/></form:option>
									<form:option value="radio-horizontal"><spring:message code="label.uiType.radiohorizontal"/></form:option>
									<form:option value="date"><spring:message code="label.uiType.date"/></form:option>
									<form:option value="file"><spring:message code="label.uiType.file"/></form:option>
									<form:option value="label"><spring:message code="label.uiType.label"/><</form:option>
									<form:option value="text-area"><spring:message code="label.uiType.textarea"/></form:option>
									<form:option value="text-area-rich"><spring:message code="label.uiType.textarearich"/></form:option>
						   		</form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-4 control-label"><spring:message code="label.initOption"/></label>
                            <div class="col-lg-5">
                               <form:textarea path="initOption" id="initOption" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-4 control-label"><spring:message code="label.displayorder"/></label>
                            <div class="col-lg-5">
                               <form:input path="displayOrder" id="displayOrder" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
							<label class="col-lg-4 control-label"><spring:message code="label.is.for.payroll"/></label>
						    <div class="col-lg-5">
						       	<form:select path="isForPayroll" id="isForPayroll" class="selectpicker form-control">
						   			<form:option value="true"><spring:message code="label.option.yes"/></form:option>
						       		<form:option value="false"><spring:message code="label.option.no"/></form:option>
						   		</form:select>
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
					                <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
					                    <spring:message code="label.button.submit"/>
					                </button>
					                <button type="button" class="btn btn-info dialogResetBtn" id="resetBtn">
					                    <spring:message code="label.button.reset"/>
					                </button>
					                <form:hidden path="id" id="id" />
					            </div>
					        </div>
					    </div>
                    </form:form>
                    
<script type="text/javascript">
$(document).ready(function() {
	
    $('#defaultForm',Dialog.getContext()).bootstrapValidator({
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
            displayOrder: {
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
    })
});

</script>
 