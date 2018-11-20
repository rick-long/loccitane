<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div id="consentFormAddContext">
    <c:url var="addUrl" value="/consentForm/edit"/>
    <form:form modelAttribute="consentFormVO" data-form-token="${TokenUtil.get(pageContext)}" id="defaultForm" action="${addUrl}" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
            <div class="col-lg-5">
                <form:input path="name" id="name" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
            <div class="col-lg-5">
                <form:textarea path="remarks" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.isactive"/>*</label>
            <div class="col-lg-5">
                <form:select class="selectpicker form-control" path="active" id="isActive">
                    <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
                </form:select>
            </div>
        </div>
       <%-- <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.isactive"/></label>
            <select name="active">
                <div class="col-lg-5">
                    <option value="true" class="form-control" <c:if test="${consentFormVO.active == true}">selected</c:if>>Active</option>
                    <option value="false" class="form-control" <c:if test="${consentFormVO.active == false}">selected</c:if>>No Active</option>
                </div>
            </select>
        </div>--%>
        
        <div class="modal-footer">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
                        <spring:message code="label.button.submit"/>
                    </button>
                </div>
            </div>
        </div>
    </form:form>
</div>
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
                }
            }
        });
    });
</script>
