<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/push/edit"/>
<c:url var="url" value="/push/add"/>
<form:form  id="defaultForm" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.message.title"/></label>
        <div class="col-lg-5">
            <input name="title" id="title" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.message.label"/> *</label>
        <div class="col-lg-5">
            <textarea id="label" name="label" class="form-control"></textarea>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.promotion.image.url"/></label>

        <div class="col-lg-5">
            <input name="imageUrl" id="imageUrl" class="form-control" placeholder="http://"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.promotion.url"/></label>

        <div class="col-lg-5">
            <input name="url" id="url" class="form-control"placeholder="http://"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.delivery.date"/></label>

        <div class="col-lg-5">
            <SELECT ID="sendType" name="sendType" onclick="select(this)">
                <option value="sendNow"> <spring:message code="label.push.send.now"/> </option>

                <option value="sendLater"><spring:message code="label.push.send.later"/> </option>
            </SELECT>
        </div>
    </div>

    <div class="form-group" id="sendDateDiv">
        <label class="col-lg-4 control-label"><spring:message code="label.push.send.date"/></label>
        <div class="col-lg-5">
            <div class="input-group date form_time" >
                <input id="sendDate" name="sendDate" class="form-control sendDate"
                       value='' size="16" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-time" ></span></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.push.mobile.devices"/></label>

        <div class="col-lg-5">
            <SELECT ID="facilityType" name="facilityType" >
                <option value="android"> <spring:message code="label.push.android"/> </option>
                <option value="ios"><spring:message code="label.push.ios"/> </option>
            </SELECT>
        </div>
    </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-info dialogResetBtn">
                    <spring:message code="label.push.save.draft"/>
                </button>
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.push.post.now"/>
                </button>
            </div>
        </div>
    </div>
</form:form>
<script type="text/javascript">
    $(document).ready(function () {
        $("#sendDateDiv").hide();
        $('#sendDate', Dialog.getContext()).datetimepicker({
            format: 'Y-m-d H:i',
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
                }
            }
        })
    });
    function select(obj){
        var sendType= obj.value;
        if (sendType=="sendLater"){
            $("#sendDateDiv").show();
        }else{
            $("#sendDateDiv").hide();
        }

    }
</script>
