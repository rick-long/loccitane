<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div id="content">
<div class="management" id="staff-search">
	
	<h3><spring:message code="label.service.settings"/></h3>
	<c:url var="url" value="/staff/saveServiceSettings" />
	<form id="defaultForm" data-forward="staffListMenu" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url }'>
		<div id="baseStep" class="baseStep step active ">
		<input type="hidden" id="staffId" name="id" value="${staff.id }">
		<div class="form-group">
		    <label class="col-lg-2 control-label"><spring:message code="label.category"/></label>
		    <div id="categoryTreeId" class="col-lg-5">
		
		    </div>
		</div>
		
	   <c:forEach items="${staff.staffTreatmentses}" var="item">
	    	<c:if test="${not empty item.category}">
	        	<input name="categoryIds" value="${item.category.id}" type="hidden">
	    	</c:if>
	    	<c:if test="${not empty item.product}">
	        	<input name="productIds" value="${item.product.id}" type="hidden">
	    	</c:if>
		</c:forEach>
		</div>
		<div id="confirmStep" class="confirmStep step"></div>
		<div class="modal-footer">
	       <div class="bootstrap-dialog-footer">
	           <div class="bootstrap-dialog-footer-buttons">
	               <!-- <button type="button" class="btn btn-default" id="previous">Previous</button>
                   <button type="button" class="btn btn-default" id="next">Next</button> -->
	               <button type="button" class="btn btn-primary formPageSubmit" id="dlgSubmit">
	                   <spring:message code="label.button.submit"/>
	               </button>
	           </div>
	       </div>
	   </div>
	</form>
</div>

</div>
<script type="text/javascript">

    $(document).ready(function () {
   		$('#categoryTreeId', getContext()).load('<c:url value="/category/categoryProductTree"/>', {
   			dataUrl: '<c:url value="/category/getSimpleCategoryNodes?staffId=${staff.id}"/>'
  	    });
    });
</script>
