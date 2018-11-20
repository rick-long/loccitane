<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<h3 class='text-h3-white'>
    <spring:message code="label.member.group.view"/>
</h3>
<div class="table_version_layout">
<form class="form-horizontal">
<div class="container">
<div class="row">
<div class="col-sm-12">
<div class="col-lg-3 col-sm-6">
    <div class="form-group col-md-12">
        <label class="control-label"><spring:message code="label.module"/></label>
         &nbsp;${userGroup.module}
        
    </div>
    </div>
    <div class="col-lg-3 col-sm-6">
    <div class="form-group col-md-12">
        <label class="control-label"><spring:message code="label.name"/></label>
        &nbsp;${userGroup.name}
       
    </div>
    </div>
    
    <div class="col-lg-3 col-sm-6">
   <spring:message code="label.member.total.users"/>: ${userGroup.users.size()}
    </div></div>
  </div>
  </div>
</form>
 <div class="pageList">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th><spring:message code="label.home.shop"/></th>
                <th><spring:message code="label.username"/></th>
                <th><spring:message code="label.fullName"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userGroup.users}" var="item">
                <tr data-id="${item.id}">
                    <td>${item.shop.name }</td>
                    <td>${item.username}</td>
                    <td>${item.fullName }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

