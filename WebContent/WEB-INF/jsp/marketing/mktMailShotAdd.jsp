<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<form method="post" class="form-horizontal" enctype="multipart/form-data">
    <div id="baseStep" class="baseStep step active">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.channel"/></label>
            <div class="col-lg-5">
                <select name="channelId" id="channelId" class="selectpicker form-control">
                    <c:forEach items="${channelList}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.user.group"/></label>
            <div class="col-lg-5">
                <div class="input-group">
                    <input class="form-control" placeholder="Search User Group"/>
                    <span id="userGroupSearchBtn" class="input-group-addon">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
                <select name="userGroupIds" multiple="multiple" id="userGroupMultiSelect" class="selectpicker form-control">
                </select>
            </div>
        </div>
    </div>
    <div id="mktMailShotAddContentStep" class="mktMailShotAddContentStep step"></div>
    <div id="confirmStep" class="confirmStep step"></div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-default" id="previous"><spring:message code="label.book.status.previous"/> </button>
                <button type="button" class="btn btn-default" id="next"><spring:message code="label.book.status.next"/> </button>
                <button type="button" class="btn btn-default" id="submit">
                    <spring:message code="label.book.status.save"/>
                </button>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        
        var form = $('form', Dialog.getContext());
        var html;

        // 註冊對話框的提交事件
        var previousBtn = $('#previous', Dialog.getContext());
        var nextBtn = $('#next', Dialog.getContext());
        var submitBtn = $('#submit', Dialog.getContext());
        previousBtn.hide();
        submitBtn.hide();

        // 初始化 multi select 組件
        var userGroupMultiSelect = $('#userGroupMultiSelect', Dialog.getContext());

        var selectableHeader = "<div class='custom-header'><spring:message code="label.commission.selectable.items"/> </div>";
        var selectionHeader = "<div class='custom-header'><spring:message code="label.commission.selection.items"/> </div>";
        var options = {
            selectableHeader: selectableHeader,
            selectionHeader: selectionHeader
        };
        userGroupMultiSelect.multiSelect(options);
        $('#userGroupSearchBtn', Dialog.getContext()).click(function () {
            $.post('<c:url value="/member/selectUserGroupJson"/>', {name: $(this).siblings('input').val()}, function (res) {
                if (!res) {
                    return;
                }
                var selectedOptions = userGroupMultiSelect.find('option:selected');
                userGroupMultiSelect.find("option").not(selectedOptions).remove();
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
                $(html.join('')).appendTo(userGroupMultiSelect);
                userGroupMultiSelect.multiSelect('refresh');
            });
        });

        nextBtn.click(function () {
            var currentStep = $('.step.active', Dialog.getContext());
            var nextStep = currentStep.next('.step');
            if (currentStep.hasClass('baseStep')) {
                if(!$('#channelId', Dialog.getContext()).val()) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/> ',
                        message: '<spring:message code="label.marketing.long.message"/> '
                    });
                    return;
                }
                var selectedOptions = userGroupMultiSelect.find('option:selected');
                if(selectedOptions.length == 0) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="label.marketing.long.message1"/> '
                    });
                    return;
                }

                $.post('<c:url value="/marketing/mktMailShotAddContent"/>', form.serialize(), function (res) {
                    nextStep.html(res);
                    currentStep.removeClass('active');
                    nextStep.addClass('active');
                    previousBtn.show();
                });
            } else if (currentStep.hasClass('mktMailShotAddContentStep')) {
                if(!$('#subject', Dialog.getContext()).val()) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="label.marketing.long.message2"/> '
                    });
                    return;
                }
                // 同步数据后可以直接取得textarea的value
                mktMailShotAddContentEditor.sync();
                if(!$('#mktMailShotAddContentEditor', Dialog.getContext()).val()) {
                    Dialog.alert({
                        title: '<spring:message code="lable.error"/>',
                        message: '<spring:message code="label.marketing.long.message3"/> '
                    });
                    return;
                }

                $.ajax({
                    url: '<c:url value="/marketing/mktMailShotAddConfirm"/>',
                    type: 'POST',
                    data: new FormData(form[0]),
                    async: false,
                    success: function (res) {
                        nextStep.html(res);
                        currentStep.removeClass('active');
                        nextStep.addClass('active');
                        submitBtn.show();
                        nextBtn.hide();
                        previousBtn.show();
                    },
                    cache: false,
                    contentType: false,
                    processData: false
                });

            }
        });

        previousBtn.click(function () {
            submitBtn.hide();
            var currentStep = $('.step.active', Dialog.getContext());
            var previousStep = currentStep.removeClass('active').prev('.step').addClass('active');
            if (previousStep.hasClass('baseStep')) {
                previousBtn.hide();
                nextBtn.show();
            } else {
                nextBtn.show();
            }
        });

        submitBtn.click(function () {
            submitBtn.button('loading');
            var formData = new FormData(form[0]);
            formData.append("form_token", '${TokenUtil.get(pageContext)}');
            $.ajax({
                url: '<c:url value="/marketing/mktMailShotAdd"/>',
                type: 'POST',
                data: formData,
                async: false,
                success: function (res) {
                    serviceValidateCallBack(res, form);
                },
                complete: function() {
                    submitBtn.button('reset');
                },
                cache: false,
                contentType: false,
                processData: false
            });
        });
    });
</script>
 