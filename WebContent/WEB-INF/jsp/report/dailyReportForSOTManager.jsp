<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div class="management" id="sales-details">
	<h3>
		<spring:message code="label.sot.manager.daily.report"/>
	</h3>
	<c:url var="url" value="/report/listDailyReportForSOTManager"/>
	<form:form modelAttribute="salesSearchVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">	
     <div class="row">
      <div class="col-sm-12">
      <%-- <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
           <form:select id="shopId" path="shopId" class="selectpicker form-control">
               	<form:option value=""><spring:message code="label.option.select.single"/></form:option>
                   <c:forEach items="${shopList}" var="item">
                       <form:option value="${item.id}">${item.name}</form:option>
                   </c:forEach>
               </form:select>
           </div>
        </div> --%>
	  <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.from.date"/></label>
	         <div class="input-group date form_time">
		        	<input id="fromDate" name="fromDate" class="form-control fromDate" 
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		        	<span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        	
		        	<input type ="hidden" id="toDate" name="toDate" class="form-control toDate" 
		        		value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		           
		        </div>
	        </div>
	    </div>
       </div> 
       <div class="col-sm-12">    
 		<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
	            	<i class="glyphicon glyphicon-search"></i>
	                <spring:message code="label.button.search"/>
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
</script>