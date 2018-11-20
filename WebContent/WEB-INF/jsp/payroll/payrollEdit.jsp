<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

	
	<c:url var="url" value="/payroll/edit"/>
	<form  id="defaultForm" method="post" data-form-token="${TokenUtil.get(pageContext)}" class="form-horizontal" action='${url }'>
		<input type="hidden" name="id" id="id" value="${payroll.id }"/>
		<%-- <div class="form-group">
	        <label class="col-lg-4 control-label"><spring:message code="label.shop"/></label>
	        <div class="col-lg-5">
                <input type="text" class="form-control" value="${payroll.staff.shop.name }" readonly/>
	        </div>
	    </div> --%>
		<div class="form-group">
	        <label class="col-lg-4 control-label"><spring:message code="label.payroll.staff"/></label>
	        <div class="col-lg-5">
	        	<input type="hidden" name="staffId" value="${payroll.staff.id}">
                <input type="text" class="form-control" value="${payroll.staff.displayName }" readonly/>
	        </div>
	    </div>
	    <div class="form-group">
	    	<label class="col-lg-4 control-label"><spring:message code="label.payroll.template"/></label>
			<div class="col-lg-5">
			 	<input type="text" class="form-control" value="${payroll.payrollTemplate.name }" readonly/>
	    	</div>
		</div>
		 <div class="form-group">
	    	<label class="col-lg-4 control-label"><spring:message code="label.date"/></label>
			<div class="col-lg-5">
			 	<input type="text" class="form-control" name="" value="${payroll.payrollDate}" readonly/>
	    	</div>
		</div>
		<c:if test="${payroll.payrollTemplate.serviceHourKeyForPartTime }">
			<div class="form-group">
		    	<label class="col-lg-4 control-label"><spring:message code="label.payroll.serviced.hours"/></label>
				<div class="col-lg-5">
		 			<input type="text" id="serviceHours" name="serviceHours" class="form-control" value="${payroll.serviceHours }"/>
		    	</div>
			</div>
		</c:if>
	    <fieldset>
        	<div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.payroll.additional.before.MPF"/> <i class="glyphicon glyphicon-plus addBf"></i></label>
            <div class="col-lg-7">
            <table class="table" id="additionalBfTable" align="center">
            	<c:forEach items="${payroll.additionalsBfMpf}" var="spa" varStatus="spaIdx">
                <tr class="addtionalTr">
                 	<td style="padding:0 5px 5px 0;margin:0;">
                        <input type="text" name="additionalsBfMpf[${spaIdx.index }].key" class="form-control" value="${spa.label }"/>
                    </td>
                    <td style="padding:0 5px 5px 0;margin:0;">
                        <input type="text" name="additionalsBfMpf[${spaIdx.index }].value" class="form-control" value="${spa.value }"/>
                    </td>
                    <td style="padding:0 5px 5px 0;margin:0;">
                         <i class="glyphicon glyphicon-minus remove hide"></i>
                    </td>
                </tr>
               	</c:forEach>
            </table>
            </div>
            </div>
        </fieldset>
        
        <fieldset>
        	<div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.payroll.additional.after.MPF"/> <i class="glyphicon glyphicon-plus addAf"></i></label>
           <div class="col-lg-7"> <table class="table" id="additionalAfTable" align="center">
            	<c:forEach items="${payroll.additionalsAfMpf}" var="spa" varStatus="spaIdx">
                <tr class="addtionalTr">
                 	<td style="padding:0 5px 5px 0;margin:0;">
                        <input type="text" name="additionalsAfMpf[${spaIdx.index }].key" class="form-control" value="${spa.label }"/>
                    </td>
                    <td style="padding:0 5px 5px 0;margin:0;">
                        <input type="text" name="additionalsAfMpf[${spaIdx.index }].value" class="form-control" value="${spa.value }"/>
                    </td>
                    <td >
                         <i class="glyphicon glyphicon-minus remove hide"></i>
                    </td>
                </tr>
               	</c:forEach>
            </table>
            </div>
            </div>
        </fieldset>
        
		<div class="modal-footer">
	        <div class="bootstrap-dialog-footer">
	            <div class="bootstrap-dialog-footer-buttons">
	                <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
	                    <spring:message code="label.button.submit"/>
	                </button>
	                <button type="button" class="btn btn-info dialogResetBtn" id="resetBtn">
	                    <spring:message code="label.button.reset"/>
	                </button>
	            </div>
	        </div>
	    </div>
	</form>

<table id="additionalBfTemplate" class="hide">
    <tr class="addtionalTr">
     	<td style="padding:0 5px 5px 0;margin:0;">
            <input type="text" name="additionalsBfMpf[0].key" class="form-control"/>
        </td>
        <td style="padding:0 5px 5px 0;margin:0;">
            <input type="text" name="additionalsBfMpf[0].value" class="form-control"/>
        </td>
        <td style="padding:0 5px 5px 0;margin:0;">
             <i class="glyphicon glyphicon-minus remove hide"></i>
        </td>
    </tr>
</table>
<table id="additionalAfTemplate" class="hide">
    <tr class="addtionalTr">
     	<td style="padding:0 5px 5px 0;margin:0;">
            <input type="text" name="additionalsAfMpf[0].key" class="form-control"/>
        </td>
        <td style="padding:0 5px 5px 0;margin:0;">
            <input type="text" name="additionalsAfMpf[0].value" class="form-control"/>
        </td>
        <td style="padding:0 5px 5px 0;margin:0;">
             <i class="glyphicon glyphicon-minus remove hide"></i>
        </td>
    </tr>
</table>

<script type="text/javascript">
$(document).ready(function () {
    $('.addBf',Dialog.getContext()).click(function () {
        var htmlA = $('#additionalBfTemplate tr',Dialog.getContext()).clone();
        var htmlB=$('#additionalBfTable');
        htmlA.appendTo(htmlB);
        $('#additionalBfTable',Dialog.getContext()).find('.remove').each(function (index) {
            if(index !=0){
            	$(this).removeClass('hide');
            }
        });
        $('#additionalBfTable',Dialog.getContext()).find('.addtionalTr').each(function(idx){
        	$(this).find('input[name^="additionalsBfMpf"]').each(function(){
        		var inputName= $(this).attr('name');
       		 	var inpStrs=inputName.split(".");
       		 	var replaceName="additionalsBfMpf["+idx+"]."+inpStrs[1];
       			$(this).attr('name',replaceName);
        	});
   	 	});
    });
	$('#additionalBfTable',Dialog.getContext()).on('click', '.remove', function () {
    	  $(this).parents('tr').remove();
    });
	
	
	$('.addAf',Dialog.getContext()).click(function () {
        var htmlA = $('#additionalAfTemplate tr',Dialog.getContext()).clone();
        htmlA.appendTo($('#additionalAfTable'));
        $('#additionalAfTable',Dialog.getContext()).find('.remove').each(function (index) {
            if(index !=0){
            	$(this).removeClass('hide');
            }
        });
        $('#additionalAfTable',Dialog.getContext()).find('.addtionalTr').each(function(idx){
        	$(this).find('input[name^="additionalsAfMpf"]').each(function(){
        		var inputName= $(this).attr('name');
       		 	var inpStrs=inputName.split(".");
       		 	var replaceName="additionalsAfMpf["+idx+"]."+inpStrs[1];
       			$(this).attr('name',replaceName);
        	});
   	 	});
    });
	$('#additionalAfTable',Dialog.getContext()).on('click', '.remove', function () {
    	  $(this).parents('tr').remove();
    });
});

/* function formDialogBeforeSubmit(){
$('#additionalAfTable',Dialog.getContext()).find('.addtionalTr').each(function(idx){
	$(this).find('input[name^="additionalsAfMpf"]').each(function(){
		var $val= $(this).val();
		if(!/^[0-9]+(.[0-9]{0,2})?$/.test($val)){  
	        alert("Please input number in second text box for each line!"); 
	        return;
	    }  
	});
});
} */
</script>