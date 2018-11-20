<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<table class="table table-striped table-hover">
	<thead>
		 <tr>
		 	 <th><spring:message code="label.home.shop"/></th>
		 	 <th><spring:message code="label.loyalty.level"/></th>
		     <th><spring:message code="label.username"/></th>
		     <th><spring:message code="label.firstname"/></th>
		     <th><spring:message code="label.lastname"/></th>
		     <th><spring:message code="label.email"/></th>
		     <th><spring:message code="label.phone"/></th>
		     <th><spring:message code="label.gender"/></th>
		     <th><spring:message code="label.dateofbirth"/></th>
		     <th><spring:message code="label.enabled"/></th>
		 </tr>
	</thead>
	<tbody>
        <c:forEach items="${page.list}" var="item">
            <tr data-id="${item.id}">
            	<td>${item.shop.name }</td>
            	<td>${item.currentLoyaltyLevel.name}</td>
                <td>${item.username}</td>
                <td>${item.firstName }</td>
                <td>${item.lastName}</td>
                <td>${item.email }</td>
                <td>
                	<c:forEach items="${item.phones}" var="phone">
	           			<b><spring:message code="label.phone.${phone.type}"/></b> : ${phone.phoneNumberHiddenPart}</br>
	           		</c:forEach>
                </td>
                <td>
                	<c:if test="${not empty item.gender}"><spring:message code="label.gender.${item.gender}"/></c:if>
                	<c:if test="${empty item.gender}">N/A</c:if>
                </td>
                <td><fmt:formatDate value="${item.dateOfBirth}" pattern="yyyy-MM-dd"/></td>
                <td><spring:message code="label.${item.enabled}"/></td>
            </tr>
        </c:forEach>
	</tbody>
</table>
<!-- page start  -->
<c:set var="pageUrl" value="${pageContext.request.contextPath}/member/advanceList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end  -->