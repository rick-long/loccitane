<%@page import="com.spa.constant.CommonConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:url var="url" value="/shop/add"/>
<form:form modelAttribute="shopAddVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
        <div class="col-lg-5">
            <form:input path="name" id="name" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.reference"/>*</label>
        <div class="col-lg-5">
            <form:input path="reference" id="reference" class="form-control"/>
        </div>
    </div>
     <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.prefix"/>*</label>
        <div class="col-lg-5">
            <form:input path="prefix" id="prefix" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.email"/>*</label>
        <div class="col-lg-5">
            <form:input path="email" id="email" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.isonline"/></label>
        <div class="col-lg-5">
            <form:select class="selectpicker form-control" path="isOnline" id="isOnline">
                <form:option value="false"><spring:message code="label.option.no"/></form:option>
                <form:option value="true"><spring:message code="label.option.yes"/></form:option>
            </form:select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.isonline.booking"/></label>
        <div class="col-lg-5">
            <form:select class="selectpicker form-control" path="showOnlineBooking" id="showOnlineBooking">
                <form:option value="false"><spring:message code="label.option.no"/></form:option>
                <form:option value="true"><spring:message code="label.option.yes"/></form:option>
            </form:select>
        </div>
    </div>
    <%-- <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.type"/></label>
        <div class="col-lg-5">
            <form:select class="selectpicker form-control" path="outSourceTemplateId" id="outSourceTemplateId">
                <form:option value=""><spring:message code="label.shop.type.normal"/></form:option>
                <c:forEach var="obj" items="${outSourceTemplates }">
                	<form:option value="${obj.id }">${obj.name }</form:option>
                </c:forEach>
            </form:select>
        </div>
    </div> --%>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
        <div class="col-lg-5">
            <form:textarea path="remarks" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.business.phone"/>*</label>
        <div class="col-lg-5">
            <form:input path="businessPhone" id="businessPhone" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.address"/>*</label>
        <div class="col-lg-5">
            <form:input path="address" id="address" class="form-control"/>
        </div>
    </div>
    <fieldset>
        <legend></legend>
        <table class="opening-hours">
            <tr>
                <td><spring:message code="label.shop.dayofweek"/></td>
                <td>
                	<spring:message code="label.shop.open.time"/>
                </td>
                <td>
                	<spring:message code="label.shop.close.time"/>
                </td>
                <td>
                  <spring:message code="label.shop.allow.online.booking"/>
                </td>
            </tr>
            <c:forEach items="${weeks}" var="week" varStatus="idx">
                <tr>
                    <td>
                        <spring:message code="label.week.${week}"/>
                        <form:hidden path="openingHoursList[${idx.index }].dayOfWeek" value="${week}"/>
                        <form:hidden path="openingHoursList[${idx.index }].id"/>
                    </td>
                    <td>
                    <div class="input-group date form_time">
	                    <input name="openingHoursList[${idx.index }].openTime" class="form-control openTime" value='<%=CommonConstant.OPENING_HOUR_DEFAULT%>' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span></div>
	                </td>
	                <td>
                    <div class="input-group date form_time">
	                    <input name="openingHoursList[${idx.index }].closeTime" class="form-control closeTime" value='<%=CommonConstant.CLOSE_HOUR_DEFAULT%>' readonly>
                        <span class="input-group-addon time-toggle"><span class="glyphicon glyphicon-time"></span></span></div>
	                </td>
                    <td >
                            <input name="openingHoursList[${idx.index }].isOnlineBooking" type="checkbox" value="true">
                    </td>
                </tr>
            </c:forEach>
        </table>
    </fieldset>

    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-info dialogResetBtn" id="resetBtn">
                    <spring:message code="label.button.reset"/>
                </button>
                <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form:form>
<script type="text/javascript">
    $(document).ready(function () {
    	
    	$('.openTime, .closeTime', Dialog.getContext()).datetimepicker({
            format: 'H:i',
            timepicker: true,
            datepicker: false,
            step: 5
        });
    	
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
                },
                prefix: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
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
                },
                businessPhone: {
                	message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                        	message: '<spring:message code="label.errors.is.required"/>'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: 'Should be a number.'
                        }
                    }
                },
                address: {
                    message: '<spring:message code="label.errors.is.not.valid"/>',
                    validators: {
                        notEmpty: {
                            message: '<spring:message code="label.errors.is.required"/>'
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
