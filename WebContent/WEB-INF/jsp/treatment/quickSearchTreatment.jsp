<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="member-quicksearch">
    <c:url var="url" value="/treatment/quicksearchlist"/>
    <form:form modelAttribute="memberListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
            <div class="col-lg-8">
                <div class="input-group">
                    <form:input path="treatmentCode" id="treatmentCode" class="form-control"/>
                    <span class="input-group-addon search-btn" onclick="commonSearchForm(this);">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
            </div>
        </div>

        <%--<div class="form-group">
	        <label class="col-sm-2 control-label"><spring:message code="label.client"/></label>
	        <div class="col-lg-5">
	        	<form:input path="username" id="username" class="form-control"/>
	        	<a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" ><spring:message code="label.button.search"/></a>
	        </div>
	    </div>--%>
    </form:form>
    <div id="pageList">

    </div>
</div>

<script type="text/javascript">
    function setClientVal(code) {
        var parent = Dialog.getParentContext();
        var code = $('#code', parent).attr("value", code);
        Dialog.get().close(); // 关闭对话框
        code.trigger('input'); // 更新状态
        code.trigger('change');
    }
</script>
