<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped">
	<thead>
		 <tr>
		     <th><spring:message code="label.username"/></th>
		     <th><spring:message code="label.fullName"/></th>
		     <th><spring:message code="label.email"/></th>
		     <th><spring:message code="label.phone"/></th>
		     <th><spring:message code="label.loyalty.level"/></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}">
                <td><a  class="search-name" href="javascript:;" onclick="setClientVal('${item.username}','${item.id}','${item.fullNameEscape}')">${item.username}</a></td>
                <td>${item.fullName }</td>
                <td>${item.email }</td>
                <td> 
                	<c:forEach items="${item.phones}" var="phone">
                    	${phone.phoneNumberHiddenPart} </br>
                	</c:forEach>
                </td>
                <td>${item.currentLoyaltyLevel.name}</td>
            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/member/quicksearchlist" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->