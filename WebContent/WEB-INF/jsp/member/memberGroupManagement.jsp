<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<div class="management" id="member-group-add">
    <h3><spring:message code="label.member.group.search.management"/>
        <a data-permission="member:toGroupAdd" href='<c:url value="/member/toGroupAdd"/>' class="btn btn-primary dialog"
           data-draggable="true"
           data-title='<spring:message code="label.member.group.add.management"/>'>
            <i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/>
        </a>
    </h3>
    <c:url var="url" value="/member/groupList"/>
    <form:form modelAttribute="userGroupListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
       <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.name"/></label>
          <form:input path="name" id="name" class="form-control"/>
                    </div>
                </div>
                
          <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.module"/></label>           <form:select class="selectpicker form-control" path="module" id="module">
                            <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                            <c:forEach var="m" items="${moduleList }">
                                <form:option value="${m}">${m}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
              <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.isactive"/></label>
		     <form:select class="selectpicker form-control" path="isActive" id="isActive">
		                	<form:option value="true"><spring:message code="label.option.yes"/></form:option>
		                	<form:option value="false"><spring:message code="label.option.no"/></form:option>
		                    <form:option value=""><spring:message code="label.option.select.all"/></form:option>
		                </form:select>
		            </div>
		      	</div>
           <div class="col-lg-12 col-sm-6">
                <div class="form-group col-md-12"><a href="javascript:;" onclick="commonSearchForm(this);" class="btn btn-default search-btn">
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