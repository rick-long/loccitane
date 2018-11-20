<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div class="management">
<div class="memberDetailsImport">
   <div class="salesforce-logo"><img src="<c:url value='/resources/img/ims-weblogo.png'/>"/></div>
   <div id="demo_data_list">
		
		</div>
<ul>
	<li class="step1">
		<b class="btitle"><i><spring:message code="label.salesforce.STEP1"/>:</i></b> Reset the last modified date for 5 DEMO records in the SSL database.
		<br/>
		<div style="margin-left:78px;">
			<table border="0" cellspacing="0" cellpadding="0" class="management_table" width="90%">
	 		<tbody>
		     	<tr>
		     		<td>
		     			<div class="input-group date form_time">
				     		<input id="updateDate" name="updateDate"
					    		value='' size="18">
					        <span class="" id="updateDateSpan"><span class="glyphicon glyphicon-time"></span></span>
				        </div>
					    <span><a  href="javascript:;" onclick='resetDB();' class="btn btn-default" ><spring:message code="label.button.reset"/></a></span>
					</td>
			    </tr>
			</tbody>
			</table>
		</div>
		
	</li>
	</br>
	<li class="step2">
	  <b class="btitle"><i><spring:message code="label.salesforce.STEP2"/>: </i></b> Manually delete 5 DEMO records in SF Account and Contact Objects.</li>
	</br>	
		
	<li>
		<b class="btitle"><i><spring:message code="label.salesforce.STEP3"/>:</i></b> Import member details of 5 DEMO records.
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
	<li  class="step4">
	  <b class="btitle"><i><spring:message code="label.salesforce.STEP4"/>:</i></b> Test data update from SF to SSL.</li>
</ul>	
</div>	
</div>	
<script type="text/javascript">
function resetDB(){
	var updateDate = $('#updateDate').val();
	$.ajax({
	    url: '<c:url value="/sfDataDEMO/resetDB?updateDate="/>'+updateDate,
	    type: 'POST',
	    async: false,
	    success: function (res) {
	    	BootstrapDialog.alert(res.message);
	    	$.post('<c:url value="/sfDataDEMO/memberDetailsDataList"/>',function (res) {
	    		$('#demo_data_list').html(res);
            });
	    },
	    cache: false,
	    contentType: false,
	    processData: false
	}); 
	
}
$(document).ready(function () {
	 $.post('<c:url value="/sfDataDEMO/memberDetailsDataList"/>',function (res) {
 		$('#demo_data_list').html(res);
     });
	 
	 $('#updateDate').datetimepicker({
		 format: 'Y-m-d',
         timepicker: false,
         startDate: "1970-01-01"
     });
     $('#updateDateSpan').click(function () {
         var input = $(this).siblings('input').trigger('focus');
     });
     
     $('#importBtn', Dialog.getContext()).click(function () {
       	var putNum = $('#putNum').val();
       
       	var operationEnumVal = $('input[name="operationEnum"]:checked').val();
           var $btn = $(this).button('loading');
           $.post('<c:url value="/sfDataDEMO/importDataV2?putNum="/>' + putNum+"&isDEMO=1", {millis: 3000}, function (res) {
           		BootstrapDialog.alert(res.alertErrorMsg);
           		$btn.button('reset');
           		$.post('<c:url value="/sfDataDEMO/memberDetailsDataList"/>',function (res) {
    	    		$('#demo_data_list').html(res);
                });
           }).always(function () {
               console.info('error');
               $btn.button('reset');
           });
       });
   });
    
function downloadLog(url){
	$('.downloadLogBtn').attr("href",url); 
}

</script>