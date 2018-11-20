<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<div class="management">
<h3>Member Import</h3>
<form:form data-form-token="${TokenUtil.get(pageContext)}" modelAttribute="importDemoVO" id="defaultForm" enctype="multipart/form-data" method="post" class="form-horizontal" action='${url }'>
 	
   	<%-- <div class="form-group">
      	<label class="col-lg-4 control-label"><spring:message code="label.home.shop"/></label>
      	<div class="col-lg-5">
         	<form:select class="selectpicker form-control" path="shopId" id="shopId">
				<c:forEach var="s" items="${shopList }">
					<form:option value="${s.id }">${s.name }</form:option>
				</c:forEach>
			</form:select>
      	</div>
  	</div> --%>
    <table border="0" cellspacing="0" cellpadding="0" class="management_table">
     <tbody>
     <tr>
       <td><label class="control-label" style="padding-left:20px;"><spring:message code="label.member.excel.file"/> </label></td>
        <td> <div class="col-lg-12"> <input type="file" id="importFile" name="importFile" accept=".xls"  /> </div></td>
         	<td><a href="javascript:;" onclick="importMember();" class="btn btn-default" >
	     	 	<spring:message code="label.button.import"/>
	     	 </a>
    </td>
    <td></td>
    </tr>
    </tbody>
    </table>
</form:form>
 </div>
<script type="text/javascript">


$(document).ready(function() {
	 
	 $(":file", Dialog.getContext()).filestyle({
         buttonText: '',
         placeholder: 'Choose File'
     });
	 
	 
});

var form = $('form', Dialog.getContext());
function importMember(){
	 var formData = new FormData(form[0]);
	 $.ajax({
        url: '<c:url value="/member/doImport"/>',
        type: 'POST',
        data: formData,
        async: false,
        success: function (res) {
            alert(res.message);
        },
        cache: false,
        contentType: false,
        processData: false
    }); 
}
</script>
 