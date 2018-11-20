<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:url var="url" value="/member/userGroupAdd"/>
<form:form modelAttribute="userGroupVO" method="post" class="form-horizontal" action='${url }'>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.name"/>*</label>
        <div class="col-lg-5">
            <form:input path="name" id="name" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.remarks"/></label>
        <div class="col-lg-5">
            <form:input path="remarks" id="remarks" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label"><spring:message code="label.username"/></label>
        <div class="col-lg-5">
            <div class="input-group">
                <input class="form-control" placeholder="Search User"/>
                    <span id="searchUserBtn" class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
            </div>
            <select multiple="multiple" id="userIds" name="userIds" class="selectpicker form-control">
            </select>
        </div>
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
        
        var userIds = $('#userIds', Dialog.getContext());
        var selectableHeader = "<div class='custom-header'><spring:message code="label.commission.selectable.items"/> </div>";
        var selectionHeader = "<div class='custom-header'><spring:message code="label.commission.selection.items"/> </div>";
        var options = {
            selectableHeader: selectableHeader,
            selectionHeader: selectionHeader
        };
        userIds.multiSelect(options);

        $('#searchUserBtn', Dialog.getContext()).click(function () {
            $.post('<c:url value="/member/selectUserJson"/>', {userName: $(this).siblings('input').val()}, function (res) {
                if (!res) {
                    return;
                }
                var selectedOptions = userIds.find('option:selected');
                userIds.find("option").not(selectedOptions).remove();
                var html = [];
                var selectedIds = [];
                $.each(selectedOptions, function () {
                    selectedIds.push($(this).attr('value'));
                });

                $.each(res, function (index, item) {
                    if ($.inArray(item.value + '', selectedIds) == -1) {
                        html.push('<option value="' + item.value + '">' + item.label + '</option>');
                    }
                });
                $(html.join('')).appendTo(userIds);
                userIds.multiSelect('refresh');
            });
        });

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
        })
    });
</script>
 