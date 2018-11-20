<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
	<style>
	.btn-primary{
	background:#ffcb00;
	}
	</style>
<div class="management" id="staff-search">
	<h3><spring:message code="label.staff.search.management"/>
        <a href='<c:url value="/staff/toAdd"/>' class="btn btn-primary form-page" data-draggable="true" data-title='<spring:message code="label.staff.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
	<c:url var="url" value="/staff/list" />
	<form:form modelAttribute="staffListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
    <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.home.shop"/></label>
	      <select class="selectpicker form-control" name="homeShopId" id="homeShopId" >
	        		<option value=""><spring:message code="label.option.select.all"/></option>
	        	 	<c:forEach items="${shopList}" var="shop">
	        			<option value="${shop.id }" <c:if test="${currentShop.id == shop.id}">selected</c:if>>${shop.name }</option>
	        		</c:forEach>
	      		</select>
	        </div>
	    </div>
        
       <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.staff.display.name"/>/<spring:message code="label.phone"/></label>
	     <form:input path="username" id="username" class="form-control"/>
	        </div>
	    </div>
        
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"> <label class="control-label"><spring:message code="label.staff.role.name"/></label>
	        	<form:select class="selectpicker form-control" path="sysRoleId" id="sysRoleId" >
	        		<form:option value=""><spring:message code="label.option.select.all"/></form:option>
	        	 	<c:forEach items="${sysRoleList}" var="role">
	        			<form:option value="${role.id }">${role.name }</form:option>
	        		</c:forEach>
	      		</form:select>
	        </div>
	 </div>
     
     <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.email"/></label>
	    <form:input path="email" id="email" class="form-control"/>
	        </div>
</div> 

    <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label>
		 <form:select class="selectpicker form-control" path="enabled" id="enabled">
	        		<form:option value="true"><spring:message code="label.option.yes"/></form:option>
	        		<form:option value=""><spring:message code="label.option.select.all"/></form:option>
	          		<form:option value="false"><spring:message code="label.option.no"/></form:option>
	      		</form:select>
			</div>
	    </div>
        
        <div class="col-lg-12 col-sm-6">
            <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn" >
		     	 	<i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/>
		     	 </a>
		 </div>
       </div>
       </div>
       </div>
	 
	</form:form>
	<div id="pageList">
		
	</div>
</div>