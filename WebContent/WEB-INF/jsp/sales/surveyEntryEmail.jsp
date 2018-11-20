<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<c:url var="url" value="/survey/send"/>
<form id="defaultForm" method="post" class="form-horizontal" action='${url }'>
	<input type="hidden" name="orderSurveyId" id="orderSurveyId" value="${orderSurveyId}" class="form-control"/>
	<div class="form-group">
		<label class="col-lg-4 control-label">Email Address</label>
		<div class="col-lg-5">
				<input type="text" name="emailAddress" id="emailAddress" class="form-control" value=""/>
		</div>
	</div>
	<div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
                    <spring:message code="label.sales.send"/>
                </button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
    	
        $('#defaultForm', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
            	emailAddress: {
                	message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        emailAddress: {
                            message: '<spring:message code="label.errors.email.correct.address"/>'
                        }
                    }
                },
                captcha: {
                    validators: {}
                }
            }
        });
    });
</script>