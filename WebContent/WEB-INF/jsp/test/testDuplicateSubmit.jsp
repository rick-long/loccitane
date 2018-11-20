<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="org.spa.utils.TokenUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3>前端防止重复form提交测试</h3>
    <form class="form-horizontal">
        <div class="modal-footer">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <button type="button" id="testBtn" class="btn btn-default">Front Test</button>
                </div>
            </div>
        </div>
    </form>

    <h3>防止重复form提交测试, 后台返回错误</h3>
    <c:url var="url" value="/test/submitTest"/>
    <form action="${url}" data-form-token="${TokenUtil.get(pageContext)}" class="form-horizontal">
        <input type="hidden" name="millis" value="3000">
        <div></div>
        <div class="modal-footer">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <button <%--data-permission="test:submitTest"--%> type="button" class="btn btn-default dialogSubmitBtn" data-skip-validate="true">Backend Test
                    </button>
                </div>
            </div>
        </div>
    </form>

    <h3>防止重复form提交测试, 后台返回成功</h3>
    <form class="form-horizontal">
        <input type="hidden" name="millis" value="3000">
        <input type="hidden" name="form_token" value="${form_token}">
        <div></div>
        <div class="modal-footer">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <button data-permission="test:submitSuccess" id="successTest" type="button" class="btn btn-default">Backend Test</button>
                </div>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    $(function () {
        
        $('#testBtn', Dialog.getContext()).click(function () {
            var $btn = $(this).button('loading');
            $.post('<c:url value="/test/longReturnTest"/>', {millis: 3000}, function (res) {
                console.info(res);
                $btn.button('reset');
            }).always(function () {
                console.info('error');
                $btn.button('reset');
            });
        });

        $('#successTest', Dialog.getContext()).click(function() {
            $.post('<c:url value="/test/submitSuccess"/>', $(this).parents('form').serialize(), function (res) {
                if(res.statusCode == 300) {
                    Dialog.alert(res.alertErrorMsg);
                } else {
                    Dialog.success(res.message);
                }
            });
        });


    });
</script>
