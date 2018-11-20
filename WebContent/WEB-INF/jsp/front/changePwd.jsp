<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/front" %>

<t:layout>
<div class="profile-box">
<div class="title_for_all" style="width:100%;"><h2><spring:message code="label.change.password"/> </h2></div>
<c:url var="url" value="/front/changePwd"/>
<form id="defaultForm" method="post" class="form-horizontal" action='${url}' data-form-token="${TokenUtil.get(pageContext)}">
    <input type="hidden" name="userId" value="${changePasswordVO.userId}">
    <input type="hidden" name="checkPassword" value="${changePasswordVO.checkPassword}">
    <c:if test="${changePasswordVO.checkPassword}">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.old.password"/></label>
            <div class="col-lg-5">
                <input type="password" name="oldPassword" id="oldPassword" class="form-control"/>
            </div>
        </div>
    </c:if>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.new.password"/> </label>
        <div class="col-lg-5">
            <input type="password" name="password" id="password" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.confirmPassword"/></label>
        <div class="col-lg-5">
            <input type="password" name="confirmPassword" id="confirmPassword" class="form-control"/>
        </div>
    </div>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button id="submit" type="button" class="btn btn-primary">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        
        $('#defaultForm').bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                password: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        stringLength: {
                            min: 6,
                            max: 18,
                            message: '<spring:message code="label.errors.password.length"/> 6-18'
                        }
                    }
                },
                confirmPassword: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        stringLength: {
                            min: 6,
                            max: 18,
                            message: '<spring:message code="label.errors.password.length"/> 6-18'
                        },
                        identical: {//相同
                            field: 'password', //需要进行比较的input name值
                            message: '<spring:message code="label.errors.password.not.same"/>'
                        }
                    }
                }
            }
        });
        $('#submit').click(function () {
        	var form=$('#defaultForm');
        	var bootstrapValidator = form.data('bootstrapValidator');
        	
            if (bootstrapValidator) {
                // 自定义验证
                if (typeof bootstrapValidator.options.customValidate === 'function' && !bootstrapValidator.options.customValidate()) {
                    return;
                }
                bootstrapValidator.validate(); // 验证
                var result = bootstrapValidator.isValid(); // 取出结果
                if (!result) {
                    return;
                }
            }
            $.ajax({
    	        url: '<c:url value="/front/changePwd"/>',
    	        type: "POST",
    	        dataType: "json",
    	        data: form.serialize(),
    	        success: function(json) {
    	        	switch (json.statusCode) {
    		            case 200:
    		            	window.location.href='<c:url value="/front/index"/>';
    		            	break;
    		            case 300:
    		            	 if (json.errorFields.length > 0) {
    		                     var fieldErrors = '';
    		                     $.each(json.errorFields, function (index, item) {
    		                         var fieldName = item.fieldName;
    		                         var errorMessage = item.errorMessage;
    		                         fieldErrors += fieldName +" : "+errorMessage + "<br/>";
    		                     });
    		                     
    		                     if (fieldErrors) {
    		                    	 $('#error_msg').html(fieldErrors);
    		                     }
    		                 }
    		                 if (form) {
    		                     form.data('form-token', json.form_token);  // 重新设置token的值
    		                 }
    		            	break;
    		            default:
    		                console.info("status code error:" + json.statusCode);
    		            break;
    	        	}
    	        }
    	    });
        });
    });
</script>
</t:layout>
