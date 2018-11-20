<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/changePassword"/>
<form id="defaultForm" method="post" class="form-horizontal" action='${url}'>
    <input type="hidden" name="userId" value="${changePasswordVO.userId}">
    <input type="hidden" name="checkPassword" value="${changePasswordVO.checkPassword}">
    <c:choose>
        <c:when test="${changePasswordVO.checkPassword}">
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.old.password"/></label>
                <div class="col-lg-5">
                    <input type="password" name="oldPassword" id="oldPassword" class="form-control"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="form-group">
                <label class="col-lg-4 control-label"><spring:message code="label.send.password.by.email"/></label>
                <div class="col-lg-5">
                    <input type="checkbox" name="sendPasswordByEmail" id="sendPasswordByEmail" class="check-box" checked/>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="password-group">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.new.password"/></label>
            <div class="col-lg-5">
                <input type="password" name="password" id="password" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.confirm.password"/></label>
            <div class="col-lg-5">
                <input type="password" name="confirmPassword" id="confirmPassword" class="form-control"/>
            </div>
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
</form>

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
                oldPassword: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                }
            }
        });

        <c:choose>
        <c:when test="${changePasswordVO.checkPassword}">
            $(".password-group").removeClass("hide");
        </c:when>
        <c:otherwise>
            $(".password-group").addClass("hide");
            $("#sendPasswordByEmail").click(function () {
                if ($(this).is(":checked")) {
                    $(".password-group").addClass("hide");
                } else {
                    $(".password-group").removeClass("hide");
                }
            });
        </c:otherwise>
        </c:choose>

    });
</script>
