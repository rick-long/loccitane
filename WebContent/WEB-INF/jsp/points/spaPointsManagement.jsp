<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div class="management" id="order-search">
	<h3>
    	<spring:message code="label.spa.points.search.management"/>
    	<a href='<c:url value="/sp/toAdjust"/>' class="btn btn-primary dialog" data-draggable="true" data-title='<spring:message code="label.spa.points.adjust.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.adjust"/>
        </a>
    </h3>
	<c:url var="url" value="/sp/list"/>
	<form:form modelAttribute="spaPointsListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">	
     <table border="0" cellspacing="0" cellpadding="0" class="management_table">
     <tbody>
     <tr>
       <td> <label class="control-label"><spring:message code="label.client"/></label>
	        <div class="col-lg-5">
                <div class="input-group dialog" data-url='<c:url value="/member/quicksearch"/>' data-title='<spring:message code="label.client.quick.search"/>'>
                     <input name="memberId" id="memberId" class="form-control" type="hidden"/>
                     <input name="username" id="username" class="form-control quick-search" readonly/>
                     <span class="input-group-addon">
                         <span class="glyphicon glyphicon-search"></span>
                     </span>
                 </div>
	        </div>
	  </td>
       <td>
	        <label class="control-label"><spring:message code="label.from.date"/></label>
	        <div class="col-lg-5">
	            <div class="input-group date form_time">
		        	<input id=fromDate name="fromDate" class="form-control fromDate" 
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		            <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </div>
	    </td>
       <td>
	        <label class="control-label"><spring:message code="label.to.date"/></label>
	        <div class="col-lg-5">
	            <div class="input-group date form_time">
		        	<input id=toDate name="toDate" class="form-control toDate" 
		        		value="" size="16">
		            <span class="input-group-addon" id="toDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </div>
	   </td>
 <td></td>
        </tr>
        <tr>
       <td><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
                	<i class="glyphicon glyphicon-search"></i>
                	<spring:message code="label.button.search"/>
                </a>
		 </td>
         <td></td>
        <td></td>
        <td></td>
       </tr>
       </tbody>
       </table>

	</form:form>
	<div id="pageList">
		
	</div>
</div>

<script type="text/javascript">
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
</script>