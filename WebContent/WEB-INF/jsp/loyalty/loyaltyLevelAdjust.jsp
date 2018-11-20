<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/loyalty/adjustLoyaltyLevel"/>
<form:form modelAttribute="userLoyaltyLevelVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
	Client : ${user.fullName}</br>
	Current Diva: ${user.currentLoyaltyLevel.name}
	
	<form:hidden path="memberId" id="memberId" value="${user.id}"></form:hidden>
	<div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.adjsut.loyalty.level"/>*</label>
        <div class="col-lg-5">
            <form:select path="loyaltyLevelId" id="loyaltyLevelId" class="selectpicker form-control">
            	<c:forEach var="ll" items="${llList }">
	            	<form:option value="${ll.id }">${ll.name }</form:option>
	            </c:forEach>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.loyalty.level.month.limit"/>*</label>
        <div class="col-lg-5">
            <form:input path="monthLimit" id="monthLimit" class="form-control"/>
        </div>
    </div>
     <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.ll.send.notificaton.email"/></label>
        <div class="col-lg-5">
            <form:select class="selectpicker form-control" path="sendNotificationEmail" id="sendNotificationEmail">
                <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                <form:option value="false"><spring:message code="label.option.no"/></form:option>
            </form:select>
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
        
        $('form', Dialog.getContext()).bootstrapValidator({
            message: '<spring:message code="label.errors.is.not.valid"/>',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
            	username: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
                        }
                    }
                },
                monthLimit: {
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
        });
    });
</script>
