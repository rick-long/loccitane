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
  	<div class="form-group">
      	<label class="col-lg-3 control-label">Member Excel File</label>
        <div class="col-lg-5"> 	 <input type="file" id="importFile" name="importFile" accept=".xls"  />             </div>
         	<div class="col-lg-3"> <a href="javascript:;" onclick="importMember();" class="btn btn-default" >
	     	 	<spring:message code="label.button.import"/>
	     	 </a>
             </div>

  	</div>
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
 