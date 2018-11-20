<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<style>
    .form-group {
        display: block;
    }

    .form-group.form-group-inactive {
        display: none;
    }
</style>

<div class="management">
<div class="memberDetailsImport">
   <div class="salesforce-logo"><img src="<c:url value='/resources/img/ims-weblogo.png'/>"/></div>
<ul>
<li>
	<b class="btitle"><i><spring:message code="label.button.import.operation"/>: </i></b>
	<label class="radio-inline">
	    <input type="radio" name="operationEnum" value="upsert"  checked/><spring:message code="label.upsert"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <input type="radio" name="operationEnum" value="insert" /><spring:message code="label.insert"/>
	</label>
</li>
<li class="step1 form-group">
	<b class="btitle"><i><spring:message code="label.salesforce.STEP1"/>:</i></b> Reset the last modified date for all records in the SSL database.
	<br/><span style="margin-left:78px;"><a  href="javascript:;" onclick='resetDB();' class="btn btn-default" >
       	<spring:message code="label.button.reset"/>
    </a></span>
</li>
</br>
<li class="step2 form-group">
  <b class="btitle"><i><spring:message code="label.salesforce.STEP2"/>: </i></b> Manually delete all records in SF Account and Contact Objects.</li>
</br>	
		
<li>
	<div class="step3 form-group"><b class="btitle"><i><spring:message code="label.salesforce.STEP3"/>:</i></b> Import member details.</div>
	<div class="step5 form-group form-group-inactive"><b class="btitle"><i>STEP1:</i></b> Import member details.</div>
	</br><div style="margin-left:78px;"><table border="0" cellspacing="0" cellpadding="0" class="management_table" width="90%">
 		<tbody>
	     	<tr>
	     		<td>
		     		<input type="text" id="putNum"/>
					<!-- <a  href="javascript:;" onclick='importData();' class="btn btn-default" >
				       	Import
				    </a> -->
				    <button type="button" id="importBtn" class="btn btn-default"><spring:message code="label.button.import"/></button>
				</td>
		    </tr>
		    <tr><td><a  href="javascript:;" onclick='downloadLog("<c:url value="/salesforceData/downloadLog"/>");' class="btn btn-default downloadLogBtn" ><spring:message code="label.salesforce.download.log"/></a></td></tr>
	    </tbody>
	</table></div>
</li>
</br>	
<li  class="step4 form-group">
  <b class="btitle"><i><spring:message code="label.salesforce.STEP4"/>:</i></b> Test data update from SF to SSL.</li>
 </ul>	
</div>	
</div>	
<script type="text/javascript">
function resetDB(){
	$.ajax({
	    url: '<c:url value="/salesforceData/resetDB"/>',
	    type: 'POST',
	    async: false,
	    success: function (res) {
	    	BootstrapDialog.alert(res.message);
	    },
	    cache: false,
	    contentType: false,
	    processData: false
	}); 
}
 $(function () {
        
        $('#importBtn', Dialog.getContext()).click(function () {
        	var putNum = $('#putNum').val();
        	var operationEnumVal = $('input[name="operationEnum"]:checked').val();
            var $btn = $(this).button('loading');
            $.post('<c:url value="/salesforceData/importDataV2?putNum="/>' + putNum+'&operationEnum='+operationEnumVal, {millis: 3000}, function (res) {
            	BootstrapDialog.alert(res.alertErrorMsg);
            	$btn.button('reset');
            	
            }).always(function () {
                console.info('error');
                $btn.button('reset');
            });
        });
        $('input[type=radio][name=operationEnum]').change(function() {
            if (this.value == 'upsert') {
            	if ($('.step5').hasClass('form-group-inactive')){}else{$('.step5').addClass('form-group-inactive');}
            	if ($('.step1').hasClass('form-group-inactive')) {$('.step1').removeClass('form-group-inactive');}
            	if ($('.step2').hasClass('form-group-inactive')) {$('.step2').removeClass('form-group-inactive');}
            	if ($('.step3').hasClass('form-group-inactive')) {$('.step3').removeClass('form-group-inactive');}
            	if ($('.step4').hasClass('form-group-inactive')) {$('.step4').removeClass('form-group-inactive');}
            }
            else if (this.value == 'insert') {
            	if ($('.step5').hasClass('form-group-inactive')) {$('.step5').removeClass('form-group-inactive');}
            	if ($('.step1').hasClass('form-group-inactive')){}else{$('.step1').addClass('form-group-inactive');}
            	if ($('.step2').hasClass('form-group-inactive')){}else{$('.step2').addClass('form-group-inactive');}
            	if ($('.step3').hasClass('form-group-inactive')){}else{$('.step3').addClass('form-group-inactive');}
            	if ($('.step4').hasClass('form-group-inactive')){}else{$('.step4').addClass('form-group-inactive');}
            }
        });
    });
    
function downloadLog(url){
	$('.downloadLogBtn').attr("href",url); 
}

</script>