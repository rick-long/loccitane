<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/pokey/add"/>
<form:form modelAttribute="poKeyAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/></label>
        <div class="col-lg-5">
            <form:input path="name" id="name" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.reference"/></label>
        <div class="col-lg-5">
            <form:input path="reference" id="reference" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.category"/></label>
        <div class="col-lg-5">
            <div class="select-category" data-selectable="category"></div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.default.value"/></label>
        <div class="col-lg-5">
            <form:textarea path="defaultValue" id="defaultValue" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.uiType"/></label>
        <div class="col-lg-5">
            <form:select path="uiType" id="uiType" class="selectpicker form-control">
                <option value="text"><spring:message code="label.uiType.text"/></option>
                <option value="text-area"><spring:message code="label.uiType.textarea"/></option>
                <%-- <option value="select-single"><spring:message code="label.uiType.selectsingle"/></option>
                <option value="checkbox-multi"><spring:message code="label.uiType.checkboxmulti"/></option>
                <option value="radio-horizontal"><spring:message code="label.uiType.radiohorizontal"/></option>
                <option value="radio-vertical"><spring:message code="label.uiType.radiovertical"/></option>
                <option value="date"><spring:message code="label.uiType.date"/></option>
                <option value="file"><spring:message code="label.uiType.file"/></option>
                <option value="label"><spring:message code="label.uiType.label"/></option>
                <option value="text-area-rich"><spring:message code="label.uiType.textarearich"/></option> --%>
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
        <label class="col-lg-4 control-label"><spring:message code="label.unit"/></label>
        <div class="col-lg-5">
            <form:select path="unit" id="unit" class="selectpicker form-control">
                <form:option value=""><spring:message code="label.option.select.single"/></form:option>
                <form:option value="mins"><spring:message code="label.unit.mins"/></form:option>
                <form:option value="percent"><spring:message code="label.unit.percent"/></form:option>
                <form:option value="currency"><spring:message code="label.unit.currency"/></form:option>
                <form:option value="HKcurrency"><spring:message code="label.unit.HKcurrency"/></form:option>
                <form:option value="ml"><spring:message code="label.unit.ml"/></form:option>
                <form:option value="g"><spring:message code="label.unit.g"/></form:option>
                <form:option value="kg"><spring:message code="label.unit.kg"/></form:option>
                <form:option value="NA"><spring:message code="label.option.select.single"/></form:option>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.show"/></label>
        <div class="col-lg-5">
            <form:select path="labelShow" id="labelShow" class="selectpicker form-control">
                <form:option value="true">Yes</form:option>
                <form:option value="false">No</form:option>
            </form:select>
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
       
        // Generate a simple captcha
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
                },
                reference: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });
        
        $(function () {
            $('.select-category', Dialog.getContext()).selectCategory({});
        });
    });
</script>
