<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/category/add"/>
<form:form modelAttribute="categoryAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
        <div class="col-lg-5">
            <form:input path="name" id="name" class="form-control"/>
        </div>
    </div>
	<div class="form-group categoryTreeMenu">
		<label class="col-lg-4 control-label"><spring:message code="label.category"/>*</label>
	    <div class="col-lg-5">
	       	<div class="select-category" data-selectable="category" data-root-id="-1"></div>
		</div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.displayorder"/></label>
        <div class="col-lg-5">
            <form:input path="displayOrder" id="displayOrder" class="form-control"/>
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
        $('#defaultForm', Dialog.getContext()).bootstrapValidator({
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
                }
            }
        })
        
        $('#rootCategoryId',Dialog.getContext()).change(function () {
    		var $val=$(this).val();
    		$('.select-category',Dialog.getContext()).data('root-id',$val);
    		$('.select-category',Dialog.getContext()).selectCategory({});
        }).trigger('change');
      
    	$('.select-category',Dialog.getContext()).selectCategory({});
    });
</script>

 