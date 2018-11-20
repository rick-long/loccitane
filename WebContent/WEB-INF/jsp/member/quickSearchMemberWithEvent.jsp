<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="member-quicksearch">
    <c:url var="url" value="/member/quicksearchlist"/>
    <form:form modelAttribute="memberListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.client"/></label>
            <div class="col-lg-8">
                <div class="input-group">
                    <form:input path="username" id="username" class="form-control"/>
                    <span class="input-group-addon" onclick="commonSearchForm(this);">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
            </div>
        </div>
    </form:form>
    <div id="pageList">

    </div>
</div>

<script type="text/javascript">
    function setClientVal(usernameVal, idVal,fullNameVal) {
        var parent = Dialog.getParentContext();
        var username = $('#username', parent).attr("value", usernameVal);
        var memberId = $('#memberId', parent).attr("value", idVal);
        var fullName = $('#fullName', parent).attr("value", usernameVal+"-"+fullNameVal);
        Dialog.get().close(); // 关闭对话框
        fullName.trigger('input'); // 更新状态
        
        $.each($('.suitabledPackages',parent), function () {
			var obj=$(this);
			var poId=obj.data('product-option-id');
			if(poId ==''){
				poId=$('#productOptionId',$('#goodsItemForm')).val();
			}
			$.post('<c:url value="/prepaid/suitabledPackagesSelect"/>',{memberId:idVal,productOptionId:poId},function (res) {
				obj.html(res);
	        });
			obj.on("change",function(){
	    		var suitabledPackagesVal=obj.val();
	    		if(suitabledPackagesVal == ''){
	    			obj.parent().next().children().removeAttr('readonly');
	    		}else{
	    			obj.parent().next().children().attr('readonly','true');
	    		}
	        });
        });
    }
</script>
