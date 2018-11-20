<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.subject"/></label>
    <div class="col-lg-5">
        <input type="text" name="subject" id="subject" class="form-control"/>
    </div>
</div>
<div class="form-group">
    <label class="col-lg-4 control-label"><spring:message code="label.attachment"/></label>
    <div class="col-lg-5">
        <input type="file" id="attachment" name="attachment" accept=".*"/>
    </div>
</div>

<%--<div class="form-group">
    <label class="col-lg-1 control-label"><spring:message code="label.content"/></label>
    <div class="col-lg-11">
        <textarea name="content" id="mktMailShotAddContent" cols="100" rows="8" style="width:400px;height:200px;"></textarea>
    </div>
</div>--%>

<div class="row new-row-width">
    <label><spring:message code="label.content"/></label>
    <textarea name="content" id="mktMailShotAddContentEditor" style="width:700px;height:300px;"></textarea>
</div>

<script type="text/javascript">
    $(function(){
        
        $(":file", Dialog.getContext()).filestyle({
            buttonText: '',
            placeholder: 'Choose File'
        });
    });
    var mktMailShotAddContentEditor = KindEditor.create('#mktMailShotAddContentEditor');
</script>