<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <%-- <th><spring:message code="label.reference"/></th> --%>
        <th><spring:message code="label.name"/></th>
        <%-- <th><spring:message code="label.type"/></th> --%>
        <th><spring:message code="label.module"/></th>
        <th><spring:message code="label.remarks"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item">
        <tr data-id="${item.id}" class='<c:if test="${!item.isActive}">deleted_table</c:if>'>
            <%-- <td>${item.reference}</td> --%>
            <td>${item.name }</td>
            <%-- <td>${item.type }</td> --%>
            <td>${item.module }</td>
            <td>${item.remarks}</td>
           <td>
               <%--<a href='<c:url value="/member/groupUserList?userGroupId=${item.id}"/>' title='<spring:message code="label.button.view"/>' class="btn btn-primary form-page btn-edit" data-width="680" data-title='<spring:message code="label.button.view"/>'>--%>
                   <%--<i class="glyphicon glyphicon-eye-open"></i>--%>
               <%--</a>--%>
               <a data-permission="member:toGroupEdit" href='<c:url value="/member/toGroupEdit?id=${item.id}"/>'
                  class="btn btn-primary dialog btn-edit" data-draggable="true"
                  title='<spring:message code="label.button.edit"/>'
                  data-title='<spring:message code="label.member.group.edit.management"/>'>
                   <i class="glyphicon glyphicon-edit"></i>
               </a>
               <c:if test="${item.isActive}">
	               <a data-permission="member:groupRemove" href='<c:url value="/member/groupRemove?id=${item.id}"/>'
	                  class="btn btn-primary dialog dialog-confirm btn-edit" data-reload-btn="a.search-btn"
	                  data-title='<spring:message code="label.dialog.confirm"/>'
                      title='<spring:message code="label.button.remove"/>'
	                  data-message='<spring:message code="label.sure.to.remove"/>'>
	                   <i class="glyphicon glyphicon-trash"></i>
	               </a>
	           </c:if>
           </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/member/groupList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->