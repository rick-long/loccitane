<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<div class="management" id="order-search">
<ul id="myTab" class="nav nav-tabs">
	<li class="active"><a href="#home" data-toggle="tab">Dashboard</a></li>
	<li ><a onclick=javascript:window.location.href="/?loadingUrl=/review/listingToView" data-toggle="tab" >Listing</a></li>
</ul>
	<c:url var="url" value="/review/evaluation"/>
	<form:form modelAttribute="salesSearchVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
  <input type="hidden" name="reviewDate" id="reviewDate" value="year">
   <div class="row">
      <div class="col-sm-12">
      
       <div class="col-lg-5 col-sm-12">
         <div class="form-group col-md-12">
			   <label class="control-label"><spring:message code="label.sales.reportingperiod"/> ：</label><br/>
			   <button type="button" class="btn btn-primary "
					   data-toggle="button" onclick="butSelected(this); setReviewDate('year');commonSearchForm(this);" >Past year
			   </button> <button type="button" class="btn btn-primary"
								 data-toggle="button"onclick="butSelected(this); setReviewDate('6months');commonSearchForm(this);" > Past 6months
		   </button> <button type="button" class="btn btn-primary"
							 data-toggle="button" onclick="butSelected(this); setReviewDate('30Days');commonSearchForm(this);">Past 30 Days
		   </button> <button type="button" class="btn btn-primary active" data-toggle="button" onclick="butSelected(this); setReviewDate('7Days');commonSearchForm(this);"> Past 7 Days
		   </button>
		   </div>
          </div>
        
	  <div class="col-lg-2 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.from.date"/></label>
          <div class="input-group date form_time">
		    <input id="fromDate" name="fromDate" class="form-control fromDate" value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		 <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		    </div>
	       </div>
          </div>
            
	  <div class="col-lg-2 col-sm-6">
          <div class="form-group col-md-12">
	    	<label class="control-label"><spring:message code="label.to.date"/></label>
	        <div class="input-group date form_time">
		      <input id="toDate" name="toDate" class="form-control toDate" value='<fmt:formatDate value="${toDate}" pattern="yyyy-MM-dd"/>' size="16">
		   <span class="input-group-addon" id="toDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		        </div>
	        </div>
	   	</div>
		  <div class="col-lg-2 col-sm-6">
			
			<%--	  <div class="col-lg-3 col-sm-6">--%>
					  <div class="form-group col-md-12">
                      <br/><a href="javascript:;" onclick="setReviewDate('');commonSearchForm(this);" class="btn btn-default search-btn" >
						  <i class="glyphicon glyphicon-search"></i>
						  <spring:message code="label.button.search"/>
					  </a></div>
				  <%--</div>--%>
			
		  </div>
  <%--     	<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.reference"/></label><form:input path="reference" id="reference" class="form-control"/>
	        </div>
	    </div>--%>
        </div>
		  
         
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

    function setReviewDate(reviewDate) {
       
	    $("#reviewDate").val(reviewDate);
    }

    function butSelected (current) {
        $("#fromDate").val('');
        $("#toDate").val('');
        $(current).toggleClass("active");
        $(current).siblings().removeClass("active");
    }
	
	
$("button.btn.btn-primary").click(function(){
    $("#fromDate").val('');
    $("#toDate").val('');
    $(this).toggleClass("active");
    $(this).siblings().removeClass("active");
})

</script>