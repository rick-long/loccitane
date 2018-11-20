<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<div class="management" id="sales-details">
	<h3>
		<spring:message code="left.navbar.report.salesdetails"/> <spring:message code="label.report"/>
	</h3>
	<c:url var="url" value="/report/listSalesDetails"/>
	<form:form modelAttribute="salesSearchVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">	
    	<table border="0" cellspacing="0" cellpadding="0" class="management_table">
	  	<tr>
	   <%--  <td class="col-lg-3">
	    	<label class="control-label"><spring:message code="label.client"/></label>
	        <div class="col-lg-5">
                <div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
                     <input type="hidden" name="memberId" id="memberId" class="form-control"/>
                     <input type="hidden" name="username" id="username" class="form-control"/>
                     <input type="text" name="fullName" id="fullName" class="form-control quick-search" readonly/>
                     <span class="input-group-addon">
                         <span class="glyphicon glyphicon-search"></span>
                     </span>
                 </div>
	        </div>
		</td> --%>
	    <td class="col-lg-3">   
	     	<label class="control-label"><spring:message code="label.from.date"/></label>
	        <div class="col-lg-5">
	            <div class="input-group date form_time">
		        	<input id="fromDate" name="fromDate" class="form-control fromDate" 
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		            <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </div>
	    </td>
	    <td class="col-lg-3">
	    	<label class="control-label"><spring:message code="label.to.date"/></label>
	        <div class="col-lg-5">
	            <div class="input-group date form_time">
		        	<input id="toDate" name="toDate" class="form-control toDate" 
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		            <span class="input-group-addon" id="toDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </div>
	   	</td>
       	<%-- <td class="col-lg-3"><label class="control-label"><spring:message code="label.shop"/></label>
           <div class="col-lg-5">
               <form:select id="shopId" path="shopId" class="selectpicker form-control" style="width:164px!important;">
               	<form:option value=""><spring:message code="label.option.select.single"/></form:option>
                   <c:forEach items="${shopList}" var="item">
                       <form:option value="${item.id}">${item.name}</form:option>
                   </c:forEach>
               </form:select>
           </div>
        </td> --%>
  		</tr>
    	<%-- <tr>
            <td>
	            <label class="control-label">Product Type</label>
	            <div class="col-lg-5">
	                <form:select class="selectpicker form-control" path="rootCategoryId" id="rootCategoryId">
	                 	<form:option value="1">All</form:option>
	                    <c:forEach var="subCategory" items="${subCategoryList }">
	                        <form:option value="${subCategory.id }">${subCategory.name }</form:option>
	                    </c:forEach>
	                    <form:option value="1000000">Prepaid</form:option>
	                </form:select>
	            </div>
      		</td>
      		<td>
	      		<div class="categoryTreeMenu">
					<label class="control-label">Category</label>
				    <div class="col-lg-5">
				       <div class="select-category" data-selectable="category,product,option" data-root-id="2" ></div>
					</div>
			    </div>
			</td>
            <td class="col-lg-3">
           		<label class="control-label">Staff</label>
	            <div class="col-lg-5">
	                <form:select id="staffId" path="staffId" class="selectpicker form-control" style="width:164px!important;">
	                	<form:option value=""><spring:message code="label.option.select.single"/></form:option>
	                </form:select>
	            </div>
	       	</td>
           	<td class="col-lg-3">
           		<label class="control-label">Payment Method</label>
	            <div class="col-lg-5">
	                <form:select id="paymentMethodId" path="paymentMethodId" class="selectpicker form-control" style="width:164px!important;">
	                	<form:option value=""><spring:message code="label.option.select.single"/></form:option>
	                    <c:forEach items="${paymentMethodList}" var="item">
	                        <form:option value="${item.id}">${item.name}</form:option>
	                    </c:forEach>
	                </form:select>
	            </div>
	       	</td>
 		</tr> --%>
  		<tr>
			<td class="col-lg-3">
				<a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
	            	<i class="glyphicon glyphicon-search"></i>
	                <spring:message code="label.button.search"/>
               	</a>
			</td> 
      		<td class="col-lg-3">&nbsp;</td>
      		<td class="col-lg-3">&nbsp;</td>
      		<td class="col-lg-3">&nbsp;</td>   
  		</tr>

		</table>
	</form:form>
	<div id="pageList">
		
	</div>
</div>
<script type="text/javascript">
    $(function () {
    	$('#fromDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('.input-group-addon').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
        
        $('#toDate').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('#toDateSpan').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });
     	//rootCategoryId
    	$('#rootCategoryId').change(function () {
    		var $val=$(this).val();
    		$('.select-category').data('root-id',$val);
    		$('.select-category').selectCategory({});
        }).trigger('change');
      
    	$('.select-category').selectCategory({});
    });
    
    $('#shopId').change(function () {
		$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),showAll:false},function (res) {
			$('#staffId').html(res);
        });
    }).trigger('change');
  
</script>