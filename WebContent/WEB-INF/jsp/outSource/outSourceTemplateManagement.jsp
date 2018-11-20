<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management">
    <h3>
        <spring:message code="label.outSource.template.search.management"/>
        <a data-permission="outSource:outSourceTemplateToAdd" href='<c:url value="/outSource/outSourceTemplateToAdd"/>' class="btn btn-primary dialog" data-width="680" data-reload-btn="a.search-btn" data-title='<spring:message code="label.outSource.template.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/outSource/outSourceTemplateList"/>
    <form:form id="commonSearchForm" modelAttribute="outSourceTemplateListVO" method="post" class="form-inline" action="${url}">
	<div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.name"/></label>
			   <form:input id="name" path="name" cssClass="form-control"/>
			            </div>
			    	</div>
		<div class="col-lg-12 col-sm-6">
          <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
			                    <i class="glyphicon glyphicon-search"></i><spring:message code="label.button.search"/></a></div>
       </div>
       </div>
       </div>
    </form:form>
    <div id="pageList">
    </div>
</div>