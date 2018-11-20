<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div class="management" id="loyalty-search">
    <h3>
    	<spring:message code="label.award.redemption.search.management"/>
    	<a href='<c:url value="/awardRedemption/toAdd"/>' class="btn btn-primary dialog" data-draggable="true" data-title='<spring:message code="label.award.redemption.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/awardRedemption/list"/>
    <form:form modelAttribute="awardRedemptionListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
    <div class="row">
       <div class="col-sm-12">
       
       <div class="col-lg-3 col-sm-6">
            <div class="form-group col-md-12">
            <label class="control-label"><spring:message code="label.name"/></label>
             <form:input path="name" id="name" class="form-control"/>
            </div>
            </div>
             <div class="col-lg-3 col-sm-6">
            <div class="form-group col-md-12">
            <label class="control-label"><spring:message code="label.isactive"/></label>
                <form:select class="selectpicker form-control" path="isActive" id="isActive">
                	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                    <form:option value="false"><spring:message code="label.option.no"/></form:option>
                </form:select>
            </div>
             
             </div>
       </div>
        <div class="col-sm-12">
            <div class="col-lg-3 col-sm-6">
            <div class="form-group col-md-12">
               <a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
                    <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/></a>
            </div>
            </div>
             </div>
             
       </div>
 
    </form:form>
    <div id="pageList">
    </div>
</div>