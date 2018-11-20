<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
    <style>


    </style>
<div class="management" id="member-search">
	<h3><spring:message code="label.member.search.management"/>  <a href='<c:url value="/member/toAdd"/>' class="btn btn-primary form-page" data-draggable="true" 
                	data-title='<spring:message code="label.member.add.management"/>'><i class="glyphicon glyphicon-plus"></i><spring:message code="label.button.add"/></a></h3>
	<c:url var="url" value="/member/list" />
	<form:form modelAttribute="memberListVO" id="commonSearchForm" method="post" class="form-inline" action="${url}">
     <div class="row">
      <div class="col-sm-12">
      <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.name"/> / <spring:message code="label.phone"/></label>
	       <form:input path="username" id="username" class="form-control"/>
	        </div></div>
     <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.email"/></label>
	      <form:input path="email" id="email" class="form-control"/>
	        </div></div>
            
         <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.home.shop"/></label>
          <select name="shopId" class="selectpicker form-control">
               		<option value=""><spring:message code="label.option.select.all"/></option>
                    <c:forEach items="${shopList}" var="shop">
                        <option value="${shop.id}" <c:if test="${currentShop.id == shop.id}">selected</c:if>>${shop.name}</option>
                    </c:forEach>
                </select>
            </div></div>
            
          <%--<div class="col-lg-3 col-sm-6">--%>
          <%--<div class="form-group col-md-12"><label class="control-label"><spring:message code="label.loyalty.level"/></label>--%>
               	<%--<form:select path="loyaltyLevelId" class="selectpicker form-control">--%>
               		<%--<form:option value=""><spring:message code="label.option.select.all"/></form:option>--%>
                    <%--<c:forEach items="${loyaltyLevelList}" var="ll">--%>
                        <%--<form:option value="${ll.id}">${ll.name}</form:option>--%>
                    <%--</c:forEach>--%>
                <%--</form:select>--%>
            <%--</div></div>--%>
        <div class="col-lg-3 col-sm-6">
          <div class="form-group col-md-12"><label class="control-label"><spring:message code="label.usergroups"/> </label>
          <form:select path="userGroupId" class="selectpicker form-control">
               		<form:option value=""><spring:message code="label.option.select.all"/></form:option>
                    <c:forEach items="${userGroupList}" var="ug">
                        <form:option value="${ug.id}">${ug.name}</form:option>
                    </c:forEach>
                </form:select>
            </div></div>
          <div class="col-lg-3 col-sm-6">
              <div class="form-group col-md-12"><label class="control-label"><spring:message
                      code="label.enabled"/></label>
                  <form:select class="selectpicker form-control" path="enabled" id="enabled">
                      <form:option value="true"><spring:message code="label.option.yes"/></form:option>
                      <form:option value=""><spring:message code="label.option.select.all"/></form:option>
                      <form:option value="false"><spring:message code="label.option.no"/></form:option>
                  </form:select>
              </div>
          </div>
          <div class="col-lg-12 ">
              <div class=" col-md-3 col-sm-6 form-group-btn"><a href="javascript:;" onclick="commonSearchForm(this);"
                                                   class="btn btn-default search-btn"><i
                      class="glyphicon glyphicon-search"></i>
                  <spring:message code="label.button.search"/>
              </a>
              </div>

              <div class=" col-md-3  col-sm-6 form-group-btn" style="margin-left:7px"><a data-permission="member:export"
                                                   href="javascript:;"
                                                   onclick='exportSearchForm(this,"<c:url value="/member/export?"/>");'
                                                   class="btn btn-default exportBtn"><i
                      class="glyphicon glyphicon-download"></i>
                  <spring:message code="label.button.export"/>
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


    //异步加载export
    function exportSearchForm(obj,url) {
        var pDiv = $(obj).parents(".management");
        var form = pDiv.find("#commonSearchForm");
        $('.exportBtn').attr("href",url+ form.serialize());
    }
</script>