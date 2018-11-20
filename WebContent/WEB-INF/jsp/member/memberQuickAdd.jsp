<%@ page import="org.joda.time.DateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>

<c:url var="url" value="/member/add"/>
<form:form data-form-token="${TokenUtil.get(pageContext)}" modelAttribute="memberAddVO" id="defaultForm" method="post"
           class="form-horizontal" action='${url }'>
    <input type="hidden" name="dateOfBirth" value="<%=new DateTime().toString("yyyy-MM-dd") %>">
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.firstname"/> *</label>
        <div class="col-lg-5">
            <form:input path="firstName" id="firstName" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.lastname"/> *</label>
        <div class="col-lg-5">
            <form:input path="lastName" id="lastName" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.email"/> *</label>
        <div class="col-lg-5">
            <form:input path="email" id="email" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.mobile.phone"/> *</label>
        <div class="col-lg-5">
            <form:input path="mobilePhone" id="mobilePhone" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.gender"/> *</label>
        <div class="col-lg-5">
            <label class="radio-inline"> <form:radiobutton path="gender" value="FEMALE"/> <spring:message
                    code="label.gender.FEMALE"/></label>
            <label class="radio-inline"> <form:radiobutton path="gender" value="MALE"/> <spring:message
                    code="label.gender.MALE"/></label>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.home.shop"/> *</label>
        <div class="col-lg-5">
            <form:select class="selectpicker form-control" path="shopId" id="shopId">
                <c:forEach var="s" items="${shopList }">
                    <form:option value="${s.id }" selected="${s.id eq memberAddVO.shopId ? 'selected' : ''}">
                        ${s.name}
                    </form:option>
                </c:forEach>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.district"/> *</label>
        <div class="col-lg-5 district-select">
            <ex:tree tree="${districtData}" name="addressVO.district" selectable="1,2,3"/>
        </div>
    </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary" id="dlgSubmit">
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
                firstName: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                lastName: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                mobilePhone: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        mobilePhone: {
                            message: '<spring:message code="label.errors.is.not.mobile.phone"/>'
                        }
                    }
                },
                email: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        },
                        emailAddress: {
                            message: '<spring:message code="label.errors.email.correct.address"/>'
                        }
                    }
                }
            }
        });

        $(".district-select", Dialog.getContext()).multiMenu();

        $("#dlgSubmit", Dialog.getContext()).click(function () {
            var form = $('#defaultForm', Dialog.getContext());
            $.post('<c:url value="/member/quickAdd"/>', form.serialize(), function (json) {
                switch (json.statusCode) {
                    case 200:
                        var callback = form.data('callback');
                        Dialog.get().close(); // 关闭对话框
                        Dialog.success(json.message, function () {
                            // 回填信息
                            
                            console.info(json);
                            $('#memberId', Dialog.getContext()).val(json.otherMessages.id);
                            $('#username', Dialog.getContext()).val(json.otherMessages.username);
                            $('#fullName', Dialog.getContext()).val(json.otherMessages.fullName);
                        });
                        break;
                    case 300:
                        // 验证错误处理
                        if (json.errorFields.length > 0) {
                            var fieldErrors = '';
                            $.each(json.errorFields, function (index, item) {
                                var fieldName = item.fieldName;
                                var errorMessage = item.errorMessage;
                                fieldErrors += fieldName + ": " + errorMessage + "<br/>";
                            });
                            if (fieldErrors) {
                                Dialog.alert({
                                    title: "<spring:message code="lable.error"/> ",
                                    message: fieldErrors
                                });
                            }
                        } else if (json.alertErrorMsg) {
                            // 其他弹窗错误
                            Dialog.alert({
                                title: "<spring:message code="lable.error"/>",
                                message: json.alertErrorMsg
                            });
                        }
                        if (form) {
                            form.data('form-token', json.form_token);  // 重新设置token的值
                        }
                        break;
                    case 301:
                        Dialog.alert({
                            title: "<spring:message code="lable.timeout"/> ",
                            message: json.message,
                            callback: function () {
                                window.location.href = json.forward;
                            }
                        });
                        break;
                    case 401:
                        Dialog.alert({
                            title: "<spring:message code="label.book.access.denied"/> ",
                            message: json.message
                        });
                        break;
                    default:
                        console.info("status code error:" + json.statusCode);
                        break;
                }
            }, 'json');
        });
    });
</script>
 