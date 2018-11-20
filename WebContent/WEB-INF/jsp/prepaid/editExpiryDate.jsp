<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<p color="#888888"><spring:message code="label.prepaid.name"/>: ${prepaid.name}</p>
<p color="#888888"><spring:message code="label.prepaid.reference"/> : ${prepaid.reference}</p>
<p color="#888888"><spring:message code="label.client"/>: ${prepaid.user.fullName}</p>

<form:form modelAttribute="prepaidSimpleVO" id="defaultForm" method="post" class="form-horizontal" action='${url }'>
   			
   			<input type="hidden" name="ptId" id="ptId" class="form-control" value="${ptId}"/>
		    <div class="form-group">
		        <label class="col-lg-4 control-label"><spring:message code="label.prepaid.expiry.date"/>*</label>
		        <div class="col-lg-5">
		            <div class="input-group date form_time">
			        	<input id="expiryDate" name="expiryDate" class="form-control expiryDate" 
			        		value='<fmt:formatDate value="${expiryDate}" pattern="yyyy-MM-dd"/>' size="16">
			            <span class="input-group-addon" id="expiryDateSpan"><span class="glyphicon glyphicon-time"></span></span>
			        </div>
		        </div>
		    </div>
			   
	    	 <div class="modal-footer">
        		<div class="bootstrap-dialog-footer">
	            <div class="bootstrap-dialog-footer-buttons">
	                <button type="button" class="btn btn-primary" id="submit">
	                    <spring:message code="label.button.submit"/>
	                </button>
	            </div>
	        </div>
    	</div>
</form:form>
    	
<script type="text/javascript">
	
    $(document).ready(function () {
    	
	    $('#expiryDate', Dialog.getContext()).datetimepicker({
	        format: 'Y-m-d',
	        timepicker: false
	    });
	    $('#expiryDateSpan', Dialog.getContext()).click(function () {
	        var input = $(this).siblings('input').trigger('focus');
	    });
	    var submitBtn = $('#submit', Dialog.getContext());
	    submitBtn.click(function () {
	    	var form = $('#defaultForm', Dialog.getContext());
	    	$.ajax({
                url: '<c:url value="/prepaid/editExpiryDate"/>',
                type: "POST",
                dataType: "text",
                data: form.serialize(),
                success: function(response) {
                 	Dialog.get().close(); // 关闭对话框
                 	setTimeout(function(){
                 		
                 		Dialog.get().close();
                 		$('a.search-btn').trigger('click'); // 触发重新查询事件 */
                 	},500);
                }
             });
    	});
	});
</script>