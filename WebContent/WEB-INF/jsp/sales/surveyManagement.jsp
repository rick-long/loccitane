<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<div class="management" id="survey-search">
	<h3>
		SURVEY SEARCH
	</h3>
	<c:url var="url" value="/survey/list"/>
	<form:form modelAttribute="surveyListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">	
   <div class="row">
      <div class="col-sm-12">
        
	  <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12">
          <label class="control-label"><spring:message code="label.from.date"/></label>
          <div class="input-group date form_time">
		    <input id="fromDate" name="fromDate" class="form-control fromDate" value='<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>' size="16">
		 <span class="input-group-addon" id="fromDateSpan"><span class="glyphicon glyphicon-time"></span></span>
		    </div>
	       </div>
          </div>
        
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
	         <select id="shopId" name="shopId" class="selectpicker form-control">
	         <option value=""><spring:message code="label.option.select.single"/></option>
	          <c:forEach items="${shopList}" var="item">
	         <option value="${item.id}" <c:if test="${currentShop.id == item.id}">selected</c:if>>${item.name}</option>
	         </c:forEach>
	       </select>
	            </div>
            </div>
        
        </div>
        
<%--<div class="col-sm-12">
        
  		<div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.shop"/></label>
	         <form:select id="shopId" path="shopId" class="selectpicker form-control">
	         <form:option value=""><spring:message code="label.option.select.single"/></form:option>
	          <c:forEach items="${shopList}" var="item">
	         <form:option value="${item.id}">${item.name}</form:option>
	         </c:forEach>
	       </form:select>
	            </div>
            </div>
            
            </div>--%>
            
            
            
          <div class="col-sm-12">
      		<div class="col-lg-3 col-sm-6">
           <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
	        <i class="glyphicon glyphicon-search"></i>
	       <spring:message code="label.button.search"/>
               	</a></div>
       </div>
       </div>
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
    
  //shop
	$('#shopId',Dialog.getContext()).change(function () {
		$.post('<c:url value="/staff/staffSelectList"/>',{shopId:$('#shopId',Dialog.getContext()).val(),showAll:false},function (res) {
			$('#staffId').html(res);
        });
    }).trigger('change');
  
</script>