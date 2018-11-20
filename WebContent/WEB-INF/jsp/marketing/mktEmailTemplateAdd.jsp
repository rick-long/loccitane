<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/marketing/mktEmailTemplateAdd"/>
<form:form modelAttribute="mktEmailTemplateVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.type"/></label>
        <div class="col-lg-5">
            <form:select path="type" id="type" class="selectpicker form-control">
                <form:option value="NEWSLETTER"><spring:message code="label.marketing.email.template.type.NEWSLETTER"/></form:option>
                <form:option value="BIRTHDAY"><spring:message code="label.marketing.email.template.type.BIRTHDAY"/></form:option>
                <form:option value="REGISTRATION"><spring:message code="label.marketing.email.template.type.REGISTRATION"/></form:option>
                <form:option value="THANK_YOU_EMAIL"><spring:message code="label.marketing.email.template.type.THANK_YOU_EMAIL"/></form:option>
                <form:option value="THANK_YOU_REDEEM"><spring:message code="label.marketing.email.template.type.THANK_YOU_REDEEM"/></form:option>
                <form:option value="RESET_PASSWORD"><spring:message code="label.marketing.email.template.type.RESET_PASSWORD"/></form:option>
                <form:option value="LOYALTY_LEVEL_UPGRADE"><spring:message code="label.marketing.email.template.type.LOYALTY_LEVEL_UPGRADE"/></form:option>
                <form:option value="LOYALTY_LEVEL_DOWNGRADE"><spring:message code="label.marketing.email.template.type.LOYALTY_LEVEL_DOWNGRADE"/></form:option>
                <form:option value="LOYALTY_LEVEL_EXPIRY"><spring:message code="label.marketing.email.template.type.LOYALTY_LEVEL_EXPIRY"/></form:option>
                <form:option value="INVENTORY"><spring:message code="label.marketing.email.template.type.INVENTORY"/></form:option>
                <form:option value="EMAIL_HEADER"><spring:message code="label.marketing.email.template.type.EMAIL_HEADER"/></form:option>
                <form:option value="EMAIL_FOOTER"><spring:message code="label.marketing.email.template.type.EMAIL_FOOTER"/></form:option>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.subject"/></label>
        <div class="col-lg-5">
            <form:input path="subject" id="subject" class="form-control"/>
        </div>
    </div>
    <div class="row new-row-width">
        <label><spring:message code="label.content"/></label>
        <form:textarea path="content" id="mktEmailTemplateAddEditor" class="form-control" style="width:700px;height:300px;"/>
    </div>

    <%-- <div class="form-group">
         <label class="col-lg-4 control-label"><spring:message code="label.content"/></label>
         <div class="col-lg-5">
             <form:textarea path="content" id="mktEmailTemplateAddEditor" />
         </div>
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

        var mktEmailTemplateAddEditor = KindEditor.create('#mktEmailTemplateAddEditor');

        // 提交前的事件处理
        form.data('beforeSubmitEvent', function(){
            mktEmailTemplateAddEditor.sync();
        });
    });
</script>
 