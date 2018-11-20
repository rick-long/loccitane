<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<div class="management container"> 
  <div class="row">
     <h1 class="text-h1-white"><spring:message code="left.navbar.report.prepaid.outstanding"/></h1>
  </div>
</div>
<div class="management container" id="sales-details">
	<c:url var="url" value="/report/listPrepaidOutstanding"/>
	<form:form modelAttribute="salesSearchVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">	
    <div class="row">
    <div class="col-sm-12">
      	<div class="col-lg-3 col-sm-6">
          	<div class="form-group col-md-12">  
	     		<label class="control-label"><spring:message code="label.from.date"/></label>
	            <div class="input-group date form_time">
		        	<input id="fromDate" name="fromDate" class="form-control fromDate" 
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		            <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </div>
	     </div>
	     <div class="col-lg-3 col-sm-6">
          	<div class="form-group col-md-12">
          		<label class="control-label"><spring:message code="label.shop"/></label>
               	<select id="shopId" path="shopId" class="selectpicker form-control">
               		<option value=""><spring:message code="label.option.select.single"/></option>
                   	<c:forEach items="${shopList}" var="item">
                       <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
                   	</c:forEach>
               </select>
           </div>
        </div>
       </div>
     <div class="col-sm-12">   
  		<div class="col-lg-3 col-sm-6">
          	<div class="form-group col-md-12">
				<a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default" >
	            	<i class="glyphicon glyphicon-search"></i>
	                <spring:message code="label.button.search"/>
               	</a>
			</div>
		</div>
      	<div class="col-lg-3 col-sm-6">
          	<div class="form-group col-md-12">
          		<a  href="javascript:;" onclick='exportSearchForm(this,"<c:url value="/report/prepaidOutstandingExport?"/>");' class="btn btn-default exportBtn" >
	            	<i class="glyphicon glyphicon-download"></i> Export
               	</a>
	        </div>
	     </div> 
	</div>
    
  	</div>
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
    	
    });
    
  //异步加载export
	function exportSearchForm(obj,url) {
	    var pDiv = $(obj).parents(".management");
	    var form = pDiv.find("#commonSearchForm");
		$('.exportBtn').attr("href",url+ form.serialize()); 
	}
</script>