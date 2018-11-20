<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/marketing/mktEmailTemplateEdit"/>
<form:form modelAttribute="mktEmailTemplateVO" id="defaultForm" method="post" class="form-horizontal" action='${url}'>
    <form:hidden path="id"/>
    <form:hidden path="type"/>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.subject"/></label>
        <div class="col-lg-5">
            <form:input path="subject" id="subject" class="form-control"/>
        </div>
    </div>
    <%--<div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.content"/></label>
        <div class="col-lg-5">
            <form:textarea path="content" id="content" class="form-control"/>
        </div>
    </div>--%>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.isactive"/></label>
        <div class="col-lg-5">
            <form:select class="selectpicker form-control" path="isActive" id="isActive">
                <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                <form:option value="false"><spring:message code="label.option.no"/></form:option>
            </form:select>
        </div>
    </div>
    <div class="row new-row-width">
        <label><spring:message code="label.content"/></label>
        <form:textarea path="content" id="mktEmailTemplateEditEditor" class="form-control" style="width:700px;height:300px;"/>
    </div>

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

<script type="text/javascript">
    $(document).ready(function () {
        
        var form = $('#defaultForm', Dialog.getContext());
        form.bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                subject: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });


        var mktEmailTemplateEditEditor = KindEditor.create('#mktEmailTemplateEditEditor', {
            resizeType : 1
        });

        // 提交前的事件处理
        form.data('beforeSubmitEvent', function(){
            mktEmailTemplateEditEditor.sync();
        });
    });
</script>